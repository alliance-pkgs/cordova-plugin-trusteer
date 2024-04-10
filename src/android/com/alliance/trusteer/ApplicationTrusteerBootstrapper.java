package com.alliance.trusteer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class ApplicationTrusteerBootstrapper extends CordovaPlugin {

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        System.out.println("Start trusteer");
        super.initialize(cordova, webView);
        new TrusteerAsyncTask(cordova).execute();
    }

    private static class TrusteerAsyncTask extends AsyncTask<String, Void, JSONObject> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            System.out.println("TrusteerAsyncTask.onPreExecute");
        }

        private WeakReference<CordovaInterface> cordova;

        TrusteerAsyncTask(CordovaInterface cordova) {
            this.cordova = new WeakReference<>(cordova);
        }

        @Override
        protected JSONObject doInBackground(String... strings) {
            Trusteer trusteer = new Trusteer();
            if (!trusteer.isSdkInitialized()) {
                Activity activity = this.cordova.get().getActivity();
                boolean isInit = trusteer.initSdK(activity);
                if (isInit) {
                    JSONObject[] results = trusteer.calcRiskScore();
                    for (JSONObject json : results) {
                        Log.d("Trusteer", json.toString());
                        try {
                            int value = json.getInt("Value");
                            if (value > 0) {
                                return json;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);

            if (result != null) {
                Activity activity = this.cordova.get().getActivity();
                AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(activity);
                mAlertDialog.setNegativeButton("Close", (DialogInterface dialog, int which) -> {
                    activity.finish();
                });

                mAlertDialog.setTitle("Error");
                try {
                    if(result.getString("AdditionalData") == "Root Evidence") {
                        mAlertDialog.setMessage("Your mobile device is Jailbroken or Rooted. For security purpose please ensure your mobile device is not Jailbroken or Rooted to proceed.");
                    } else {
                        mAlertDialog.setMessage("Operation failed. Please contact customer support (reason code: "
                            + result.getString("AdditionalData") + ").");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mAlertDialog.setCancelable(false).create().show();
            }

        }

    }

}
