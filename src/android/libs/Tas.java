package com.trusteer.tas.phonegap;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.util.Base64;
import android.util.Log;
import android.content.Context;

import com.trusteer.tas.TAS_BEHAVE_SCORE;
import com.trusteer.tas.TAS_RA_ACTIVITY_DATA;
import com.trusteer.tas.TAS_RA_ACTIVITY_DATA_REF;
import com.trusteer.tas.TAS_CLIENT_INFO;
import com.trusteer.tas.TAS_DRA_ITEM_INFO;
import com.trusteer.tas.TAS_INT_REF;
import com.trusteer.tas.TAS_LONG_REF;
import com.trusteer.tas.TAS_OBJECT;
import com.trusteer.tas.TAS_OBJECT_REF;
import com.trusteer.tas.TAS_RA_RISK_ASSESSMENT;
import com.trusteer.tas.TAS_STATUS_INFO;
import com.trusteer.tas.TAS_STRING_REF;
import com.trusteer.tas.TAS_VERSION_INFO;
import com.trusteer.tas.atas;
import com.trusteer.tas.tas;
import com.trusteer.tas.tasWired;


public class Tas extends CordovaPlugin
{
	ArrayList<Object> _tasObjs;

	private static final int INT_DEFAULT_VALUE = -1;
	private static final String STRING_DEFAULT_VALUE = null;
	private static final boolean BOOL_DEFAULT_VALUE = false;

	public Tas()
	{
		this._tasObjs = new ArrayList<Object>();
	}
	
	private void ThreadRun(final Tas TasObject, final Method method, final JSONArray args, final CallbackContext callbackContext)
	{
    	cordova.getThreadPool().execute(new Runnable() {
            public void run() {
            	try
				{
            		method.invoke(TasObject, callbackContext, args);
				} catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
    	});
	}

	private Context getContext()
	{
		return (Context)this.cordova.getActivity();
	}
	
    @Override
    public boolean execute(String action, final JSONArray args, final CallbackContext callbackContext) throws JSONException
    {
    	//try to get methods that should run in a thread
    	boolean ret = false;
    	
    	@SuppressWarnings("rawtypes")
		Class[] parameterTypes = new Class[2];
    	parameterTypes[0] = CallbackContext.class;
    	parameterTypes[1] = JSONArray.class;
    	Method method = null;
		try
		{
			method = Tas.class.getMethod(action, parameterTypes);
			this.ThreadRun(this, method, args, callbackContext);
			ret = true;
		}
		catch (NoSuchMethodException e1)
		{
			//try to get methods that should run in the main thread
	    	parameterTypes = new Class[3];
	    	parameterTypes[0] = CallbackContext.class;
	    	parameterTypes[1] = JSONArray.class;
	    	parameterTypes[2] = Boolean.class;
			try
			{
				method = Tas.class.getMethod(action, parameterTypes);
				try
				{
					method.invoke(this, callbackContext, args, true);
					ret = true;
				} catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (NoSuchMethodException e2)
			{
			}

		}
		return ret;
    }

    private Map<Integer,Object> getCallbacksMap (final CallbackContext context, ArrayList<Integer> cbsArray, boolean useExceptionCallback)
    {
        atas.ExceptionCallbackInterface cbExcp = new atas.ExceptionCallbackInterface()
        {

            @Override
            public void ExceptionNotice(final String message)
            {
                Tas.this.cordova.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONArray params = new JSONArray();
                        params.put(atas.TAS_EXCEPTION_CALLBACK_KEY);
                        params.put(message);
                        PluginResult signalResult = new PluginResult(PluginResult.Status.ERROR, params);
                        signalResult.setKeepCallback(true);
                        context.sendPluginResult(signalResult);
                    }
                });
            }
        };

        atas.OverlayFoundCallbackInterface overlayCallback = new atas.OverlayFoundCallbackInterface()
        {
            @Override
            public void OverlayFoundCallback(final String pkg_name, final String malware_name, final String app_name)
            {
                Tas.this.cordova.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONArray params = new JSONArray();
                        params.put(atas.TAS_OVERLAY_CALLBACK_KEY);
                        params.put(pkg_name);
						params.put(malware_name);
						params.put(app_name);
                        PluginResult signalResult = new PluginResult(PluginResult.Status.ERROR, params);
                        signalResult.setKeepCallback(true);
                        context.sendPluginResult(signalResult);
                    }
                });
            }
        };

		atas.AccessibilityDetectedCallbackInterface accessibilityCallback = new atas.AccessibilityDetectedCallbackInterface()
        {
            @Override
            public void AccessibilityDetectedCallback()
            {
                Tas.this.cordova.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONArray params = new JSONArray();
                        params.put(atas.TAS_ACCESSIBILITY_CALLBACK_KEY);
                        PluginResult signalResult = new PluginResult(PluginResult.Status.ERROR, params);
                        signalResult.setKeepCallback(true);
                        context.sendPluginResult(signalResult);
                    }
                });
            }
        };

        Map<Integer,Object> callbacks = new HashMap<Integer,Object>();
        if (cbsArray.contains(atas.TAS_EXCEPTION_CALLBACK_KEY) || useExceptionCallback)
            callbacks.put(atas.TAS_EXCEPTION_CALLBACK_KEY, cbExcp);
        if (cbsArray.contains(atas.TAS_OVERLAY_CALLBACK_KEY))
            callbacks.put(atas.TAS_OVERLAY_CALLBACK_KEY, overlayCallback);
		if (cbsArray.contains(atas.TAS_ACCESSIBILITY_CALLBACK_KEY))
            callbacks.put(atas.TAS_ACCESSIBILITY_CALLBACK_KEY, accessibilityCallback);
        return callbacks;
    }

    public void TasInitialize(CallbackContext callbackContext, JSONArray args)  throws Exception
    {
    	int result = tas.TAS_RESULT_WRONG_ARGUMENTS;
    	if (args.length() == 6)
    	{

			int initOptions = INT_DEFAULT_VALUE;
			String vendorId = STRING_DEFAULT_VALUE;
			String clientId = STRING_DEFAULT_VALUE;
			String comment = STRING_DEFAULT_VALUE;
			String clientKey = STRING_DEFAULT_VALUE;
            int region = INT_DEFAULT_VALUE;

			try {
				initOptions = args.getInt(0);
				vendorId = args.get(1) instanceof String ? args.getString(1) : vendorId;
				clientId = args.get(2) instanceof String ? args.getString(2) : clientId;
				comment = args.get(3) instanceof String ? args.getString(3) : comment;
				clientKey = args.get(4) instanceof String ? args.getString(4) : clientKey;
                region = args.getInt(5);
			} catch(JSONException e) {

			}
	    	
	    	TAS_CLIENT_INFO clientInfo = new TAS_CLIENT_INFO();
	    	clientInfo.setVendorId(vendorId);
	    	clientInfo.setClientId(clientId);
	    	clientInfo.setComment(comment);
	    	clientInfo.setClientKey(clientKey);
	    	
	    	result = atas.TasInitialize(getContext(), clientInfo, initOptions, region);
    	}
    	
    	if (result == tas.TAS_RESULT_SUCCESS || result == tas.TAS_RESULT_ALREADY_INITIALIZED) {
    		callbackContext.success(result);
    	}
    	else
    		callbackContext.error(result);
    }

    public void TasInitializeWithCallback(CallbackContext callbackContext, JSONArray args)  throws Exception
    {
    	int result = tas.TAS_RESULT_WRONG_ARGUMENTS;
		PluginResult pluginResult = null;
    	if (args.length() == 7)
    	{
			int initOptions = INT_DEFAULT_VALUE;
			String vendorId = STRING_DEFAULT_VALUE;
			String clientId = STRING_DEFAULT_VALUE;
			String comment = STRING_DEFAULT_VALUE;
			String clientKey  = STRING_DEFAULT_VALUE;
			boolean useCallback = BOOL_DEFAULT_VALUE;
            int region = INT_DEFAULT_VALUE;

			try {
				initOptions = args.getInt(0);
				vendorId = args.get(1) instanceof String ? args.getString(1) : vendorId;
				clientId = args.get(2) instanceof String ? args.getString(2) : clientId;
				comment = args.get(3) instanceof String ? args.getString(3) : comment;
				clientKey  = args.get(4) instanceof String ? args.getString(4) : clientKey;
				useCallback = args.getBoolean(5);
                region = args.getInt(6);
			} catch(JSONException e) {

			}

	    	TAS_CLIENT_INFO clientInfo = new TAS_CLIENT_INFO();
	    	clientInfo.setVendorId(vendorId);
	    	clientInfo.setClientId(clientId);
	    	clientInfo.setComment(comment);
	    	clientInfo.setClientKey(clientKey);
	    	
            ArrayList<Integer> cbIds = new ArrayList<Integer>();
            Map<Integer,Object> callbacks = getCallbacksMap(callbackContext, cbIds, useCallback);
            atas.ExceptionCallbackInterface cbException = (atas.ExceptionCallbackInterface)callbacks.get(atas.TAS_EXCEPTION_CALLBACK_KEY);
	    	result = atas.TasInitializeWithCallback(getContext(), clientInfo, initOptions, cbException, region);
			pluginResult = new PluginResult(PluginResult.Status.OK, result);
			pluginResult.setKeepCallback(true);
		}
    	
    	if (result == tas.TAS_RESULT_SUCCESS)
    		callbackContext.sendPluginResult(pluginResult);
    	else
    		callbackContext.error(result);
    }

    public void TasInitializeWithCallbacks(CallbackContext callbackContext, JSONArray args)  throws Exception
    {
        int result = tas.TAS_RESULT_WRONG_ARGUMENTS;
        PluginResult pluginResult = null;
        if (args.length() == 7)
        {
			int initOptions = INT_DEFAULT_VALUE;
			String vendorId = STRING_DEFAULT_VALUE;
			String clientId = STRING_DEFAULT_VALUE;
			String comment = STRING_DEFAULT_VALUE;
			String clientKey  = STRING_DEFAULT_VALUE;
			JSONArray callbacksIds = new JSONArray();
            int region = INT_DEFAULT_VALUE;

			try {
				initOptions = args.getInt(0);
				vendorId = args.get(1) instanceof String ? args.getString(1) : vendorId;
				clientId = args.get(2) instanceof String ? args.getString(2) : clientId;
				comment = args.get(3) instanceof String ? args.getString(3) : comment;
				clientKey  = args.get(4) instanceof String ? args.getString(4) : clientKey;
				callbacksIds = args.getJSONArray(5);	
                region = args.getInt(6);
			} catch(JSONException e) {

			}
            
            TAS_CLIENT_INFO clientInfo = new TAS_CLIENT_INFO();
            clientInfo.setVendorId(vendorId);
            clientInfo.setClientId(clientId);
            clientInfo.setComment(comment);
            clientInfo.setClientKey(clientKey);

            ArrayList<Integer> cbIds = new ArrayList<Integer>();
            for(int i=0; i<callbacksIds.length(); i++)
                cbIds.add(callbacksIds.getInt(i));
            Map<Integer,Object> callbacks = getCallbacksMap(callbackContext, cbIds, false);
            result = atas.TasInitializeWithCallbacks(getContext(), clientInfo, initOptions, callbacks, region);
            pluginResult = new PluginResult(PluginResult.Status.OK, result);
            pluginResult.setKeepCallback(true);
        }

        if (result == tas.TAS_RESULT_SUCCESS)
            callbackContext.sendPluginResult(pluginResult);
        else
            callbackContext.error(result);
    }

    public void TasSetUserId(CallbackContext callbackContext, JSONArray args)  throws Exception
    {
        int result = tas.TAS_RESULT_WRONG_ARGUMENTS;

        if (args.length() == 1)
        {
			String userId = STRING_DEFAULT_VALUE;

			try {
				userId = args.get(0) instanceof String ? args.getString(0) : null;
			} catch(JSONException e) {

			}
            
            result = tas.TasSetUserId(userId);
        }

        if (result == tas.TAS_RESULT_SUCCESS)
            callbackContext.success(result);
        else
            callbackContext.error(result);
    }

    public void TasFinalize(CallbackContext callbackContext, JSONArray args)
    {
    	Log.d("TASPhonegap", "Calling TasFinalize");
    	int result = tas.TasFinalize();
    	Log.d("TASPhonegap", "TasFinalize result:" +result);
    	if (result == tas.TAS_RESULT_SUCCESS)
    		callbackContext.success(result);
    	else
    		callbackContext.error(result);
    }

    public void TasTempCheckForUpdates(CallbackContext callbackContext, JSONArray args)
    {
    	int result = tas.TasTempCheckForUpdates();
    	
    	if (result == tas.TAS_RESULT_SUCCESS)
    		callbackContext.success(result);
    	else
    		callbackContext.error(result);
    }

    public void TasTempDeleteConfiguration(CallbackContext callbackContext, JSONArray args)
    {
    	int result = tas.TasTempDeleteConfiguration();
    	
    	if (result == tas.TAS_RESULT_SUCCESS)
    		callbackContext.success(result);
    	else
    		callbackContext.error(result);
    }

    public void TasGetDeviceKey(CallbackContext callbackContext, JSONArray args)
    {
		TAS_STRING_REF deviceKey = new TAS_STRING_REF();
		TAS_LONG_REF deviceKeySize = new TAS_LONG_REF();
		int result = tas.TasGetDeviceKey(null, deviceKeySize);
		if (result == tas.TAS_RESULT_SUCCESS)
		{
			tas.TasGetDeviceKey(deviceKey, deviceKeySize);
    		callbackContext.success(deviceKey.get_value());
		}
    	else
    		callbackContext.error(result);
    }

    public void TasGetSecuredDeviceKey(CallbackContext callbackContext, JSONArray args)
    {
		TAS_STRING_REF deviceKey = new TAS_STRING_REF();
		TAS_LONG_REF deviceKeySize = new TAS_LONG_REF();
		int result = tas.TasGetSecuredDeviceKey(null, deviceKeySize);
		if (result == tas.TAS_RESULT_SUCCESS)
		{
			tas.TasGetSecuredDeviceKey(deviceKey, deviceKeySize);
    		callbackContext.success(deviceKey.get_value());
		}
    	else
    		callbackContext.error(result);
    }

    public void TasGetCurrentVersion(CallbackContext callbackContext, JSONArray args) throws JSONException
    {
		TAS_VERSION_INFO verInfo = new TAS_VERSION_INFO();
		int result = tas.TasGetCurrentVersion(verInfo);
		if (result == tas.TAS_RESULT_SUCCESS)
		{
			JSONObject info = new JSONObject();
			try
			{
				info.put("major", verInfo.getMajor());
				info.put("minor", verInfo.getMinor());
				info.put("build", verInfo.getBuild());
				info.put("apiLevel", verInfo.getApiLevel());
				info.put("confVer", verInfo.getConfVer());
				info.put("lastUpdated", verInfo.getConfLastUpdated());
			}
			catch(Exception ex)
			{
			}
			callbackContext.success(info);
		}
    	else
    		callbackContext.error(result);
    }

    public void TasDraGetRiskAssessment(CallbackContext callbackContext, JSONArray args)  throws Exception
    {
		TAS_OBJECT_REF engine = new TAS_OBJECT_REF();
		int result = tas.TasDraGetRiskAssessment(engine);
    	
    	if (result == tas.TAS_RESULT_SUCCESS)
    	{
    		int index = this._tasObjs.size();
    		this._tasObjs.add(engine);
    		callbackContext.success(index);
    	}
    	else
    		callbackContext.error(result);
    }

    public void TasDraReleaseRiskAssessment(CallbackContext callbackContext, JSONArray args)  throws Exception
    {
    	int result = tas.TAS_RESULT_WRONG_ARGUMENTS;
    	if (args.length() == 1)
    	{
			int tasObjIndex = INT_DEFAULT_VALUE;
			
			try {
				tasObjIndex = args.getInt(0);
			} catch(JSONException e) {

			}
			
			TAS_OBJECT_REF engine = (TAS_OBJECT_REF)this._tasObjs.get(tasObjIndex);
    		if (engine != null)
    		{
    			if ((result = tas.TasDraReleaseRiskAssessment(engine.get_value())) == tas.TAS_RESULT_SUCCESS)
    				this._tasObjs.set(tasObjIndex, null);
    		}
    	}
    	
    	if (result == tas.TAS_RESULT_SUCCESS)
    		callbackContext.success(result);
    	else
    		callbackContext.error(result);
    }

    public void TasDraGetRiskItemCount(CallbackContext callbackContext, JSONArray args)  throws Exception
    {
    	int result = tas.TAS_RESULT_WRONG_ARGUMENTS;
		TAS_INT_REF count = new TAS_INT_REF();
    	if (args.length() == 1)
    	{
    		int tasObjIndex = INT_DEFAULT_VALUE;
			
			try {
				tasObjIndex = args.getInt(0);
			} catch(JSONException e) {

			}

    		TAS_OBJECT_REF engine = (TAS_OBJECT_REF)this._tasObjs.get(tasObjIndex);
    		result = tas.TasDraGetRiskItemCount(engine.get_value(), count);
    	}
    	
    	if (result == tas.TAS_RESULT_SUCCESS)
    		callbackContext.success(count.get_value());
    	else
    		callbackContext.error(result);
    }

    public void TasDraGetRiskAssessmentItemByIndex(CallbackContext callbackContext, JSONArray args)  throws Exception
    {
    	int result = tas.TAS_RESULT_WRONG_ARGUMENTS;
		JSONObject itemInfo = new JSONObject();
    	if (args.length() == 2)
    	{
			int tasObjIndex = INT_DEFAULT_VALUE;
			int index = INT_DEFAULT_VALUE;
			
			try {
				tasObjIndex = args.getInt(0);
				index = args.getInt(1);
			} catch(JSONException e) {

			}

    		TAS_OBJECT_REF engine = (TAS_OBJECT_REF)this._tasObjs.get(tasObjIndex);
    		TAS_DRA_ITEM_INFO item = new TAS_DRA_ITEM_INFO();
    		
    		if ((result = tas.TasDraGetRiskAssessmentItemByIndex(engine.get_value(), index, item)) == tas.TAS_RESULT_SUCCESS)
    		{
    			itemInfo.put("name", item.getItemName());
    			itemInfo.put("value", item.getItemValue());
    			itemInfo.put("last_time", item.getLastCalculated());
    			itemInfo.put("additional_data", item.getAdditionalData());
    			itemInfo.put("error", item.getItemError());
				int item_index = this._tasObjs.size();
				this._tasObjs.add(item.getItemObject());
				itemInfo.put("item_object", item_index);
    		}
    	}
    	
    	if (result == tas.TAS_RESULT_SUCCESS)
    		callbackContext.success(itemInfo);
    	else
    		callbackContext.error(result);
    }

    public void TasDraGetRiskAssessmentItemByName(CallbackContext callbackContext, JSONArray args)  throws Exception
    {
    	int result = tas.TAS_RESULT_WRONG_ARGUMENTS;
		JSONObject itemInfo = new JSONObject();
    	if (args.length() == 2)
    	{

    		int tasObjIndex = INT_DEFAULT_VALUE;
			String name = STRING_DEFAULT_VALUE;

			try {
				tasObjIndex = args.getInt(0);
				name = args.get(1) instanceof String ? args.getString(1) : name;
			} catch(JSONException e) {

			}
    		
			TAS_OBJECT_REF engine = (TAS_OBJECT_REF)this._tasObjs.get(tasObjIndex);
    		TAS_DRA_ITEM_INFO item = new TAS_DRA_ITEM_INFO();
    		
    		if ((result = tas.TasDraGetRiskAssessmentItemByName(engine.get_value(), name, item)) == tas.TAS_RESULT_SUCCESS)
    		{
    			itemInfo.put("name", item.getItemName());
    			itemInfo.put("value", item.getItemValue());
    			itemInfo.put("last_time", item.getLastCalculated());
    			itemInfo.put("additional_data", item.getAdditionalData());
				itemInfo.put("error", item.getItemError());
				int item_index = this._tasObjs.size();
				this._tasObjs.add(item.getItemObject());
				itemInfo.put("item_object", item_index);
    		}
    	}
    	
    	if (result == tas.TAS_RESULT_SUCCESS)
    		callbackContext.success(itemInfo);
    	else
    		callbackContext.error(result);
    }

    public void TasDraRecalcRiskAssessment(CallbackContext callbackContext, JSONArray args)  throws Exception
    {
    	int result = tas.TAS_RESULT_WRONG_ARGUMENTS;
    	if (args.length() == 1)
    	{
			int recalcOption = INT_DEFAULT_VALUE;

			try{
				recalcOption = args.getInt(0);
			} catch(JSONException e) {

			}
    		
    		result = tas.TasDraRecalcRiskAssessment(recalcOption);
    	}
    	
    	if (result == tas.TAS_RESULT_SUCCESS)
    		callbackContext.success(result);
    	else
    		callbackContext.error(result);
    }

    public void TasDraGetDraString(CallbackContext callbackContext, JSONArray args)  throws Exception
    {
    	int result = tas.TAS_RESULT_WRONG_ARGUMENTS;
		TAS_STRING_REF draStr = new TAS_STRING_REF();
		TAS_LONG_REF draStrSize = new TAS_LONG_REF();
    	if (args.length() == 1)
    	{
			int tasObjIndex = INT_DEFAULT_VALUE;

			try {
				tasObjIndex = args.getInt(0);
			} catch(JSONException e) {

			}
    		
    		TAS_OBJECT_REF engine = (TAS_OBJECT_REF)this._tasObjs.get(tasObjIndex);
    		if ((result = tas.TasDraGetDraString(engine.get_value(), null, draStrSize)) == tas.TAS_RESULT_SUCCESS)
    		{
    			result = tas.TasDraGetDraString(engine.get_value(), draStr, draStrSize);
    		}
    	}
    	
    	if (result == tas.TAS_RESULT_SUCCESS)
    		callbackContext.success(draStr.get_value());
    	else
    		callbackContext.error(result);
    }

    public void TasObGetScalarStringProperty(CallbackContext callbackContext, JSONArray args)  throws Exception
    {
    	int result = tas.TAS_RESULT_WRONG_ARGUMENTS;
		TAS_STRING_REF propertyValue = new TAS_STRING_REF();
    	if (args.length() == 2)
    	{
			int tasObjIndex = INT_DEFAULT_VALUE;
			String propertyName = STRING_DEFAULT_VALUE;

			try {
				tasObjIndex = args.getInt(0);
				propertyName = args.get(1) instanceof String ? args.getString(1) : propertyName;
			} catch(JSONException e) {

			}
			
    		TAS_OBJECT_REF obj_ref = (TAS_OBJECT_REF)this._tasObjs.get(tasObjIndex);
    		
    		result = tas.TasObGetScalarStringProperty(obj_ref.get_value(), propertyName, propertyValue);
    	}
    	
    	if (result == tas.TAS_RESULT_SUCCESS)
    		callbackContext.success(propertyValue.get_value());
    	else
    		callbackContext.error(result);
    }

    public void TasObGetCollectionPropertyCount(CallbackContext callbackContext, JSONArray args)  throws Exception
    {
    	int result = tas.TAS_RESULT_WRONG_ARGUMENTS;
		TAS_INT_REF count = new TAS_INT_REF();
    	if (args.length() == 2)
    	{
			int tasObjIndex = INT_DEFAULT_VALUE;
			String propertyName = STRING_DEFAULT_VALUE;

			try {
				tasObjIndex = args.getInt(0);
				propertyName = args.get(1) instanceof String ? args.getString(1) : propertyName;
			} catch(JSONException e) {

			}

			TAS_OBJECT obj = (TAS_OBJECT)this._tasObjs.get(tasObjIndex);
    		
    		result = tas.TasObGetCollectionPropertyCount(obj, propertyName, count);
    	}
    	
    	if (result == tas.TAS_RESULT_SUCCESS)
    		callbackContext.success(count.get_value());
    	else
    		callbackContext.error(result);
    }

    public void TasObGetCollectionStringPropertyItem(CallbackContext callbackContext, JSONArray args)  throws Exception
    {
    	int result = tas.TAS_RESULT_WRONG_ARGUMENTS;
		TAS_STRING_REF propertyValue = new TAS_STRING_REF();
    	if (args.length() == 3)
    	{
			int tasObjIndex = INT_DEFAULT_VALUE;
			String propertyName = STRING_DEFAULT_VALUE;
			int index = INT_DEFAULT_VALUE;
			
			try {
				tasObjIndex = args.getInt(0);
				propertyName = args.get(1) instanceof String ? args.getString(1) : propertyName;
				index = args.getInt(2);
			} catch(JSONException e) {

			}

			TAS_OBJECT_REF engine = (TAS_OBJECT_REF)this._tasObjs.get(tasObjIndex);
    		
    		result = tas.TasObGetCollectionStringPropertyItem(engine.get_value(), propertyName, index, propertyValue);
    	}
    	
    	if (result == tas.TAS_RESULT_SUCCESS)
    		callbackContext.success(propertyValue.get_value());
    	else
    		callbackContext.error(result);
    }

    public void TasObGetCollectionObjectPropertyItem(CallbackContext callbackContext, JSONArray args)  throws Exception
    {
    	int result = tas.TAS_RESULT_WRONG_ARGUMENTS;
		TAS_OBJECT_REF tasObjChild = new TAS_OBJECT_REF();
    	if (args.length() == 3)
    	{
    		int tasObjIndex = INT_DEFAULT_VALUE;
			String propertyName = STRING_DEFAULT_VALUE;
			int index = INT_DEFAULT_VALUE;
			
			try {
				tasObjIndex = args.getInt(0);
				propertyName = args.get(1) instanceof String ? args.getString(1) : propertyName;
				index = args.getInt(2);
			} catch(JSONException e) {

			}

			TAS_OBJECT obj = (TAS_OBJECT)this._tasObjs.get(tasObjIndex);
    		
    		if ((result = tas.TasObGetCollectionObjectPropertyItem(obj, propertyName, index, tasObjChild)) == tas.TAS_RESULT_SUCCESS)
    		{
    			int childIndex = this._tasObjs.size();
    			this._tasObjs.add(tasObjChild);
        		callbackContext.success(childIndex);
    		}
    	}
    	else
    		callbackContext.error(result);
    }

    public void TasStartBackgroundOps(CallbackContext callbackContext, JSONArray args, Boolean runOnMainThread)
    {
    	int result = tas.TasStartBackgroundOps();
    	if (result == tas.TAS_RESULT_SUCCESS)
    		callbackContext.success(result);
    	else
    		callbackContext.error(result);
    }

    public void TasWaitForBackgroundOps(CallbackContext callbackContext, JSONArray args, Boolean runOnMainThread)  throws Exception
    {
    	int result = tas.TAS_RESULT_WRONG_ARGUMENTS;
    	if (args.length() == 1)
    	{
			int timeout = INT_DEFAULT_VALUE;

			try {
				timeout = args.getInt(0);
			} catch(JSONException e) {

			}
    		
    		result = tas.TasWaitForBackgroundOps(timeout);
		}
    	if (result == tas.TAS_RESULT_SUCCESS)
    		callbackContext.success(result);
    	else
    		callbackContext.error(result);
    }

    public void TasAtoCreateSession(CallbackContext callbackContext, JSONArray args)  throws Exception
    {
    	int result = tas.TAS_RESULT_WRONG_ARGUMENTS;
		int tasObjIndex = this._tasObjs.size();

		if (args.length() == 1)
    	{
			String bankSessionId = STRING_DEFAULT_VALUE;

			try {
				bankSessionId = args.get(0) instanceof String ? args.getString(0) : bankSessionId;
			} catch(JSONException e) {

			}
    		
    		TAS_OBJECT_REF tasObj = new TAS_OBJECT_REF();
    		if ((result = tas.TasAtoCreateSession(tasObj, bankSessionId)) == tas.TAS_RESULT_SUCCESS)
    		{
        		this._tasObjs.add(tasObj);
    		}
    	}
		
    	if (result == tas.TAS_RESULT_SUCCESS)
    		callbackContext.success(tasObjIndex);
    	else
    		callbackContext.error(result);
    }

    public void TasAtoSetUserId(CallbackContext callbackContext, JSONArray args)  throws Exception
    {
    	int result = tas.TAS_RESULT_WRONG_ARGUMENTS;

		if (args.length() == 2)
    	{
			TAS_OBJECT tasObj = null;
			String userId = STRING_DEFAULT_VALUE;

			try {
				if (!args.isNull(0))
    				tasObj = ((TAS_OBJECT_REF)this._tasObjs.get(args.getInt(0))).get_value();
    			userId = args.get(1) instanceof String ? args.getString(1) : userId;
			} catch(JSONException e) {

			}
    		
    		result = tas.TasAtoSetUserId(tasObj, userId);
    	}
		
    	if (result == tas.TAS_RESULT_SUCCESS)
    		callbackContext.success(result);
    	else
    		callbackContext.error(result);
    }

    public void TasAtoSetAuxiliary(CallbackContext callbackContext, JSONArray args)  throws Exception
    {
    	int result = tas.TAS_RESULT_WRONG_ARGUMENTS;

		if (args.length() == 3)
    	{
			TAS_OBJECT tasObj = null;
			String key = STRING_DEFAULT_VALUE;
			String val = STRING_DEFAULT_VALUE;
			
			try {
				if (!args.isNull(0))
    				tasObj = ((TAS_OBJECT_REF)this._tasObjs.get(args.getInt(0))).get_value();
				key = args.get(1) instanceof String ? args.getString(1) : key;
				val = args.get(2) instanceof String ? args.getString(2) : val;
			} catch (JSONException e) {

			}
    		
    		result = tas.TasAtoSetAuxiliary(tasObj, key, val);
    	}
		
    	if (result == tas.TAS_RESULT_SUCCESS)
    		callbackContext.success(result);
    	else
    		callbackContext.error(result);
    }

    public void TasAtoRemoveAuxiliary(CallbackContext callbackContext, JSONArray args)  throws Exception
    {
    	int result = tas.TAS_RESULT_WRONG_ARGUMENTS;

		if (args.length() == 2)
    	{
			TAS_OBJECT tasObj = null;
			String key = STRING_DEFAULT_VALUE;

			try {
				if (!args.isNull(0))
    				tasObj = ((TAS_OBJECT_REF)this._tasObjs.get(args.getInt(0))).get_value();
    			key = args.get(1) instanceof String ? args.getString(1) : key;
			} catch(JSONException e) {

			}
    		
    		result = tas.TasAtoRemoveAuxiliary(tasObj, key);
    	}
		
    	if (result == tas.TAS_RESULT_SUCCESS)
    		callbackContext.success(result);
    	else
    		callbackContext.error(result);
    }

    public void TasAtoGetCommunicationPayload(CallbackContext callbackContext, JSONArray args)  throws Exception
    {
    	int result = tas.TAS_RESULT_WRONG_ARGUMENTS;
		byte[] payload = null;

		if (args.length() == 1)
    	{
			TAS_OBJECT tasObj = null;

			try {
				if (!args.isNull(0))
    				tasObj = ((TAS_OBJECT_REF)this._tasObjs.get(args.getInt(0))).get_value();
			} catch(JSONException e) {

			}
			
    		TAS_LONG_REF length = new TAS_LONG_REF();
    		if ((result = tas.TasAtoGetCommunicationPayload(tasObj, null, length)) == tas.TAS_RESULT_SUCCESS)
    		{
    			payload = new byte[(int) length.get_value()];
    			result = tas.TasAtoGetCommunicationPayload(tasObj, payload, length);
    		}
    	}
		
    	if (result == tas.TAS_RESULT_SUCCESS)
    		callbackContext.success(payload);
    	else
    		callbackContext.error(result);
    }

    public void TasAtoDestroySession(CallbackContext callbackContext, JSONArray args)  throws Exception
    {
    	int result = tas.TAS_RESULT_WRONG_ARGUMENTS;

		if (args.length() == 1)
    	{
			TAS_OBJECT tasObj = null;

			try {
				if (!args.isNull(0))
    				tasObj = ((TAS_OBJECT_REF)this._tasObjs.get(args.getInt(0))).get_value();
			} catch(JSONException e) {

			}
    		
			result = tas.TasAtoDestroySession(tasObj);
    	}
		
    	if (result == tas.TAS_RESULT_SUCCESS)
    		callbackContext.success(result);
    	else
    		callbackContext.error(result);
    }

	public void TasSetLoggerCallback(final CallbackContext callbackContext, JSONArray args)  throws Exception
	{
		int result = tas.TAS_RESULT_WRONG_ARGUMENTS;

		if (args.length() == 2)
		{
			atas.LoggerCallbackInterface cb = null;
			Object ctxt = null;
			try {
				cb = (atas.LoggerCallbackInterface)args.get(0);
				ctxt = args.get(1);
			} catch(JSONException e) {
			}

			atas.LoggerCallbackInterface cbLogger = new atas.LoggerCallbackInterface()
			{
				@Override
				public void LoggerCallback(final int severity, final String authority, final String location, final String msg, final Object callback_ctxt) {
					Tas.this.cordova.getActivity().runOnUiThread(new Runnable() {
						@Override
						public void run() {
							JSONArray params = new JSONArray();
							params.put(severity);
							params.put(authority);
							params.put(location);
							params.put(msg);
							params.put(callback_ctxt);
							PluginResult signalResult = new PluginResult(PluginResult.Status.ERROR, params);
							signalResult.setKeepCallback(true);
							callbackContext.sendPluginResult(signalResult);
						}
					});
				}
			};
			result = atas.TasSetLoggerCallback(getContext(), cb, ctxt);
		}

		if (result == tas.TAS_RESULT_SUCCESS)
			callbackContext.success(result);
		else
			callbackContext.error(result);
	}

    public void TasApharmEnable(CallbackContext callbackContext, JSONArray args)  throws Exception
    {
    	int result = tas.TAS_RESULT_WRONG_ARGUMENTS;

		if (args.length() == 1)
    	{
			int enabled = INT_DEFAULT_VALUE;

			try {
				enabled = args.getBoolean(0) ? 1 : 0;
			} catch(JSONException e) {

			}
    		
			result = tas.TasApharmEnable(enabled);
    	}
		
    	if (result == tas.TAS_RESULT_SUCCESS)
    		callbackContext.success(result);
    	else
    		callbackContext.error(result);
    }

    public void TasApharmIsEnabled(CallbackContext callbackContext, JSONArray args)  throws Exception
    {
   		callbackContext.success(tas.TasApharmIsEnabled());
    }

    public void TasApharmRegisterPolicyCallback(CallbackContext callbackContext, JSONArray args)  throws Exception
    {
		callbackContext.error(tas.TAS_RESULT_INTERNAL_ERROR);
    }

    public void TasApharmValidateSslCert(CallbackContext callbackContext, JSONArray args)  throws Exception
    {
    	int result = tas.TAS_RESULT_WRONG_ARGUMENTS;
    	TAS_INT_REF valid = new TAS_INT_REF();

		if (args.length() == 3)
    	{
			byte[] certChain = null;
    		int certChainLength = INT_DEFAULT_VALUE;
			String host = STRING_DEFAULT_VALUE;
			
			try {
				certChain = Base64.decode((String)args.get(0), Base64.DEFAULT);
				certChainLength = args.getInt(1);
				host = args.get(2) instanceof String ? args.getString(2) : host;
			} catch(JSONException e) {

			}

			result = tas.TasApharmValidateSslCert(certChain, certChainLength, host, valid);
    	}
		
    	if (result == tas.TAS_RESULT_SUCCESS)
    		callbackContext.success(valid.get_value());
    	else
    		callbackContext.error(result);
    }

	public void TasSetPUID(CallbackContext callbackContext, JSONArray args)  throws Exception
	{
		int result = tas.TAS_RESULT_WRONG_ARGUMENTS;

		if (args.length() == 1)
		{
			String userId = STRING_DEFAULT_VALUE;
			
			try {
				userId = args.get(0) instanceof String ? args.getString(0) : userId;
			} catch(JSONException e) {

			}
			
			result = tas.TasSetPUID(userId);
		}

		if (result == tas.TAS_RESULT_SUCCESS)
			callbackContext.success(result);
		else
			callbackContext.error(result);
	}

	public void TasRaCreateSession(CallbackContext callbackContext, JSONArray args)  throws Exception
	{
		int result = tas.TAS_RESULT_WRONG_ARGUMENTS;
		int tasObjIndex = this._tasObjs.size();

		if (args.length() == 1)
		{
			String bankSessionId = STRING_DEFAULT_VALUE;
			
			try {
				bankSessionId = args.get(0) instanceof String ? args.getString(0) : bankSessionId;
			} catch(JSONException e) {

			}
			
			
			TAS_OBJECT_REF sessionObj = new TAS_OBJECT_REF();
			if ((result = tas.TasRaCreateSession(sessionObj, bankSessionId)) == tas.TAS_RESULT_SUCCESS)
			{
				this._tasObjs.add(sessionObj);
			}
		}

		if (result == tas.TAS_RESULT_SUCCESS)
			callbackContext.success(tasObjIndex);
		else
			callbackContext.error(result);
	}

	public void TasRaDestroySession(CallbackContext callbackContext, JSONArray args)  throws Exception
	{
		int result = tas.TAS_RESULT_WRONG_ARGUMENTS;

		if (args.length() == 1)
		{
			TAS_OBJECT tasObj = null;
			
			try {
				if (!args.isNull(0))
					tasObj = ((TAS_OBJECT_REF)this._tasObjs.get(args.getInt(0))).get_value();
			} catch(JSONException e) {

			}
			
			result = tas.TasRaDestroySession(tasObj);
		}

		if (result == tas.TAS_RESULT_SUCCESS)
			callbackContext.success(result);
		else
			callbackContext.error(result);
	}

	public void TasRaCreateActivityData(CallbackContext callbackContext, JSONArray args)  throws Exception
	{
		int result = tas.TAS_RESULT_WRONG_ARGUMENTS;
		int activityObjIndex = this._tasObjs.size();

		if (args.length() == 0)
		{
			TAS_RA_ACTIVITY_DATA_REF activityObj = new TAS_RA_ACTIVITY_DATA_REF();
			if ((result = tas.TasRaCreateActivityData(activityObj)) == tas.TAS_RESULT_SUCCESS)
			{
				this._tasObjs.add(activityObj);
			}
		}

		if (result == tas.TAS_RESULT_SUCCESS)
			callbackContext.success(activityObjIndex);
		else
			callbackContext.error(result);
	}

	public void TasRaActivityAddData(CallbackContext callbackContext, JSONArray args)  throws Exception
	{
		int result = tas.TAS_RESULT_WRONG_ARGUMENTS;

		if (args.length() == 3)
		{
			TAS_RA_ACTIVITY_DATA activityObj = null;
			String key = STRING_DEFAULT_VALUE;
			String val = STRING_DEFAULT_VALUE;

			try {
				if (!args.isNull(0))
					activityObj = ((TAS_RA_ACTIVITY_DATA_REF)this._tasObjs.get(args.getInt(0))).get_value();
				key = args.get(1) instanceof String ? args.getString(1) : key;
				val = args.get(2) instanceof String ? args.getString(2) : val;
			} catch(JSONException e) {

			}
			
			result = tas.TasRaActivityAddData(activityObj, key, val);
		}

		if (result == tas.TAS_RESULT_SUCCESS)
			callbackContext.success(result);
		else
			callbackContext.error(result);
	}

	public void TasRaDestroyActivityData(CallbackContext callbackContext, JSONArray args)  throws Exception
	{
		int result = tas.TAS_RESULT_WRONG_ARGUMENTS;

		if (args.length() == 1)
		{
			TAS_RA_ACTIVITY_DATA activityObj = null;

			try {
				if (!args.isNull(0))
					activityObj = ((TAS_RA_ACTIVITY_DATA_REF)this._tasObjs.get(args.getInt(0))).get_value();
			} catch(JSONException e) {

			}
			
			result = tas.TasRaDestroyActivityData(activityObj);
		}

		if (result == tas.TAS_RESULT_SUCCESS)
			callbackContext.success(result);
		else
			callbackContext.error(result);
	}

	public void TasRaNotifyUserActivity(CallbackContext callbackContext, JSONArray args)  throws Exception
	{
		int result = tas.TAS_RESULT_WRONG_ARGUMENTS;

		if (args.length() == 4)
		{
			TAS_OBJECT sessionObj = null;
			TAS_RA_ACTIVITY_DATA activityObj = null;
			String user_activity = STRING_DEFAULT_VALUE;
			int timeout = INT_DEFAULT_VALUE;

			try {
				if (!args.isNull(0))
					sessionObj = ((TAS_OBJECT_REF)this._tasObjs.get(args.getInt(0))).get_value();
				user_activity = args.get(1) instanceof String ? args.getString(1) : user_activity;
				if (!args.isNull(2))
					activityObj = ((TAS_RA_ACTIVITY_DATA_REF)this._tasObjs.get(args.getInt(2))).get_value();
				timeout = args.getInt(3);
			} catch(JSONException e) {

			}
			
			result = tas.TasRaNotifyUserActivity(sessionObj, user_activity, activityObj, timeout);
		}

		if (result == tas.TAS_RESULT_SUCCESS)
			callbackContext.success(result);
		else
			callbackContext.error(result);
	}

	public void TasRaGetRiskAssessment(CallbackContext callbackContext, JSONArray args)  throws Exception
	{
		int result = tas.TAS_RESULT_WRONG_ARGUMENTS;
		JSONObject riskAssessmentInfo = new JSONObject();

		if (args.length() == 4)
		{
			TAS_OBJECT sessionObj = null;
			TAS_RA_ACTIVITY_DATA activityObj = null;
			String user_activity = STRING_DEFAULT_VALUE;
			int timeout = INT_DEFAULT_VALUE;

			try {
				if (!args.isNull(0))
					sessionObj = ((TAS_OBJECT_REF)this._tasObjs.get(args.getInt(0))).get_value();
				user_activity = args.get(1) instanceof String ? args.getString(1) : user_activity;
				if (!args.isNull(2))
					activityObj = ((TAS_RA_ACTIVITY_DATA_REF)this._tasObjs.get(args.getInt(2))).get_value();
				timeout = args.getInt(3);
			} catch(JSONException e) {

			}

			TAS_RA_RISK_ASSESSMENT ra = new TAS_RA_RISK_ASSESSMENT();
			if ((result = tas.TasRaGetRiskAssessment(sessionObj, user_activity, activityObj, ra, timeout)) == tas.TAS_RESULT_SUCCESS)
			{
				riskAssessmentInfo.put("recommendation", ra.getRecommendation());
				riskAssessmentInfo.put("resolution_id", ra.getResolution_id());
				riskAssessmentInfo.put("reason_id", ra.getReason_id());
				riskAssessmentInfo.put("reason", ra.getReason());
				riskAssessmentInfo.put("risk_score", ra.getRisk_score());
			}
		}

		if (result == tas.TAS_RESULT_SUCCESS)
			callbackContext.success(riskAssessmentInfo);
		else
			callbackContext.error(result);
	}

	public void TasBehaveGetScore(CallbackContext callbackContext, JSONArray args)  throws Exception
	{
		int result = tas.TAS_RESULT_WRONG_ARGUMENTS;
		JSONObject behave_score_obj = new JSONObject();

		if (args.length() == 1)
		{
			int timeout = INT_DEFAULT_VALUE;

			try {
				timeout = args.getInt(0);
			} catch(JSONException e) {

			}

			TAS_BEHAVE_SCORE behave_score = new TAS_BEHAVE_SCORE();
			if ((result = tas.TasBehaveGetScore(behave_score, timeout)) == tas.TAS_RESULT_SUCCESS)
			{
				behave_score_obj.put("behavioral_score", behave_score.getBehavioralScore());
				behave_score_obj.put("confidence_score", behave_score.getConfidenceScore());
				behave_score_obj.put("additional_info", behave_score.getAdditionalInfo());
			}
		}

		if (result == tas.TAS_RESULT_SUCCESS)
			callbackContext.success(behave_score_obj);
		else
			callbackContext.error(result);
	}

	public void CheckOverlay(CallbackContext callbackContext, JSONArray args)
	{
		atas.CheckOverlay(getContext());
		callbackContext.success();
	}

	//region Wired

	public void TasStart(CallbackContext callbackContext, JSONArray args)  throws Exception
    {
        int result = tas.TAS_RESULT_WRONG_ARGUMENTS;
        PluginResult pluginResult = null;
        if (args.length() == 8)
        {
			int initOptions = INT_DEFAULT_VALUE;
            String vendorId = STRING_DEFAULT_VALUE;
            String clientId = STRING_DEFAULT_VALUE;
            String comment = STRING_DEFAULT_VALUE;
			String clientKey  = STRING_DEFAULT_VALUE;
			String bankSessionId = STRING_DEFAULT_VALUE;
            JSONArray callbacksIds = new JSONArray();
            int region = INT_DEFAULT_VALUE;

			try {
				initOptions = args.getInt(0);
				vendorId = args.get(1) instanceof String ? args.getString(1) : vendorId;
				clientId = args.get(2) instanceof String ? args.getString(2) : clientId;
				comment = args.get(3) instanceof String ? args.getString(3) : comment;
				clientKey  = args.get(4) instanceof String ? args.getString(4) : clientKey;
				bankSessionId = args.get(5) instanceof String ? args.getString(5) : bankSessionId;
				callbacksIds = args.getJSONArray(6);
                region = args.getInt(7);
			} catch(JSONException e) {

			}
            
            TAS_CLIENT_INFO clientInfo = new TAS_CLIENT_INFO();
            clientInfo.setVendorId(vendorId);
            clientInfo.setClientId(clientId);
            clientInfo.setComment(comment);
            clientInfo.setClientKey(clientKey);

            final Context context = this.cordova.getActivity().getApplicationContext();
            ArrayList<Integer> cbIds = new ArrayList<Integer>();
            for(int i=0; i<callbacksIds.length(); i++)
                cbIds.add(callbacksIds.getInt(i));
			Map<Integer,Object> callbacks = getCallbacksMap(callbackContext, cbIds, false);
			int callbacks_len = (callbacks != null && callbacks.size() > 0) ? callbacks.size() : 0;
			result = tasWired.TasStart(context, clientInfo, initOptions, callbacks, bankSessionId, region);
            pluginResult = new PluginResult(PluginResult.Status.OK, result);
            pluginResult.setKeepCallback(true);
        }

        if (result == tas.TAS_RESULT_SUCCESS)
            callbackContext.sendPluginResult(pluginResult);
        else
            callbackContext.error(result);
	}
	
	public void TasStop(CallbackContext callbackContext, JSONArray args)
    {
    	int result = tasWired.TasStop();
    	if (result == tas.TAS_RESULT_SUCCESS)
    		callbackContext.success(result);
    	else
    		callbackContext.error(result);
	}

	public void TasResetSession(CallbackContext callbackContext, JSONArray args)  throws Exception
    {
        int result = tas.TAS_RESULT_WRONG_ARGUMENTS;

        if (args.length() == 1)
        {
			String sessionId = STRING_DEFAULT_VALUE;
			
			try {
				sessionId = args.get(0) instanceof String ? args.getString(0) : sessionId;
			} catch(JSONException e) {

			}
			
			result = tasWired.TasResetSession(sessionId);
        }

        if (result == tas.TAS_RESULT_SUCCESS)
            callbackContext.success(result);
        else
            callbackContext.error(result);
    }

	public void TasRaGetStatus(CallbackContext callbackContext, JSONArray args)  throws Exception
	{
		int result = tas.TAS_RESULT_WRONG_ARGUMENTS;

		TAS_STATUS_INFO tsi  = new TAS_STATUS_INFO();
		JSONObject STATUS_INFO = new JSONObject();

		if( args.length() == 0 &&  (result = tasWired.TasRaGetStatus(tsi)) == tas.TAS_RESULT_SUCCESS)
		{
			STATUS_INFO.put("lastPinpointResponse",tsi.getLastPinpointResponse());
			STATUS_INFO.put("state",tsi.getState());
		}

		if (result == tas.TAS_RESULT_SUCCESS)
			callbackContext.success(STATUS_INFO);
		else
			callbackContext.error(result);
	}
	//endregion
}
