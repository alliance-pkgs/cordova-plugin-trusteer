package com.alliance.trusteer;

import android.app.Activity;
import android.util.Log;

import com.trusteer.tas.TAS_CLIENT_INFO;
import com.trusteer.tas.TAS_DRA_ITEM_INFO;
import com.trusteer.tas.TAS_INT_REF;
import com.trusteer.tas.TAS_OBJECT;
import com.trusteer.tas.TAS_OBJECT_REF;
import com.trusteer.tas.TAS_STRING_REF;
import com.trusteer.tas.atas;
import com.trusteer.tas.tas;
import com.trusteer.tas.TasDefs;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class Trusteer {
    private static final String logTag = "Trusteer";
    private static final String vendorId = "alliancefg.com";
    private static final String clientId = "alliancefg.bankingapp";
    private static final String clientKey = "YMAQAABNFUWS2LKCIVDUSTRAKBKUETCJIMQEWRKZFUWS2LJNBJGUSSKCJFVECTSCM5VXC2DLNFDTS5ZQIJAVCRKGIFAU6Q2BKE4ECTKJJFBEGZ2LINAVCRKBOJZWOM3YF4YWIZKCO5JGYM3PJ4YVE6KBBJ3GU3ZWJAVWS3TRORWE6RKDKFLE4VDBJBSUSZKLGMZXS2BWPFYECZLJNZCXS2C2NV4TEQLEOVQXKVTKGF3EY4CBOZSU42BTIZVHIVSKBJDFK2DMOVRU65KGJ54WWY2JON4FURKWOJ3TI5DDHBEGCYKNMZVTMWKKOQ4ECS3DPF4HI3TCONIEC5KXIFKXSNCUIUVUISTOF4YEQ4CDBJLE2ULHNVNHQZLRJVMVAWCEMV4WYK2RPEYTOTDNNA4FONBYKFBWEZSYGBKWENBUMQZXO22PGBYEQ5TYO5CUS6TYKFYFIT3OJFJUSV2CBJGWS4CGGJYTG3SIMMZGMU3HJRXFAOLRIJ3TQ3KLLJ4WG43OKR3U2RKQLJVG2TTTIVJWUOCUKA4WYWCTGFVFSQTZMJEG2NRRKN4XGZSCBJBGWMCLOBUUGMCVG5SEOYLJOB3FS5JSGMXTQRTLJVKHI52RPFIGIRKXORWHCWKQMVJTQUCRJM4EEU3YIJMUQ3RVGZREC2BUNZQTKS2PBJ5FCSKEIFIUCQQKFUWS2LJNIVHEIICQKVBEYSKDEBFUKWJNFUWS2LIKAMAAAAAAAAAAAAAAAAABAAAAABZGG2LOM5PWC3DMNFQW4Y3FMZT7KAIAAAAAAAAAAEAAAACPIAGQAGYAAAAGQ5DUOBZTULZPGQ3DAMRONYXHI4TVON2GKZLSFZRW63LUBYAAATKJJFFTAUKJIJAXUQ2DINYGGR2DKNYUOU2JMIZUIUKFJBAWCQ2DINXWORLHM5YUKTKJJFFWORCDINBFIY2HINJXCR2TJFRDGRCRIVEEE4KDINBFGZ3XM5TVK22BM5CUCTKJJFDEQUKZJJFW6WSJNB3GGTSBKFRUETKCO5DUG2LRI5JUSYRTIRIUKTKBKFMXORDHKFEXQ6KQMJDE6TBPNZGESQ2BM5TUCZ2JJFCTQSRTJ42VSV3SORNDEYSUIRMWC6SONBTTO2DGIRSVO5KFMQ2VUZLIGBUUY53RGRLTITCMFNNGWSLBJ5TEEMBRJRDWYRTFIZSFSTKFIJCWOYJLFM2FUMKMMRLWUSLJGBLWGMZULI3FENC2GFCVURSDJVZSWVRWMVEWWWD2HFEWC32QLJFUUS3EOVIWYSBSGFSTC5KIHFVUYL3RKJCEQ6CVMFBTQYLVIZLGGWLDOFAWGLZZFNHVAWRUIJ5DASKWIYYEM4DXJBUHUTLDMZDDANDVI4VXQ4LIM5HGQ6BXKBIUOUTBI44G2TCWKVTUIVJUMFNHE3LUO5WHCRRZOB2HIUBUNN4TGVL2KZ3DAVLEN5QU2QTEK5ITGUZROY4WC32NPFLWUVKRI5SHKNBWOZGXC2TYMZXU4SDOHFHGYRLMPFRTCUSCIZKVAZSMG5EFU4LGMZFTS3CTHBSVUTRQM5QWSNZYJNQVIYSSNJVESURSKVWFS3RRHFYGQ5KNGF4UGVTIKJSGKSJVG5QXC6KGMNQWK52LOVGHQ6KYKYYU2TD2KY3CWR3GNFZFUYRSHF3WOT3LPFMG24SXIVVUMRSRGAZGI3TEOFYHM4CBKZIU6VLZORIDO2DKN5XFOTKYOB4USTLKGE3TQ42KHFUHE2SRJJSU6L3IM5JEYOKSF43TIRKOLFKUUSCLHBGW2YZXOM3GI3KMKV3CWT2NHA3XGZSQII3SWWKMMI3VO2SYIQZHO33EOJDHGT3OG5WXQL3VPJIUC6LDPFETQ6DSIEXTIQSHNBVUIT3LGZQTAZSBPFGUWMSKHAVTON2DFNDDEUCSGQVTERLNO5HUYSKEIZMUGZCPNJFHEUKJMVDWQMKTJ42EWWSMKBLGK2LPINCSWSKHKVRDIN3CKZJUEMCPPJSTES2DMJQS6MDXO54EUUDVHFXTIMZLHFZFG42FO5UUE2TLN5ZCWRBYMVSU6SJPLBYWMODZNQ3VUUSTOU2GITLLMVDG4RCGI5TWMY3UJRLTGNDGIJDXGYLXIZCE22CLIE4DMZLHMNFUER3UGIZDG3KNJRXG6QKKNVQUGM3UKR3WYNSEKFMVGVKCMFYDMU3PKFZU2Z2III3VUWCBINME2WLUMJJVIL2RGBEEYVLGG5SXM4CBMNSEUZJSLAYW2MLEGRDVQ4JPMZKFCOCVJBFU2NKOOVFFO32EIVYWWS2CLF2WW33RKZEGK6LIJVQUKVLWLBBWIUKYLJJEI5CLGA2GOYKXKZ2GE4CIMM3DKTC2JB2EYV3NJRCXGNTTGQ4U2ZZYOY4W2SCTMU4FE22FJBTGQQ3SOM4VOVDCMMZVS6LGNZGHCV3COF5E66JPOI3GYNCGNRBUO6TQJNVXANCXPIXWWNCUGEZTK5CSORFDOWSIIV2VKTLHLEZDS6CWJFUWMT3GG43GM33FIFJXUNLLONZU43ZPF5XWOWK2MFJHITDRF5RUQ5TOOZ5GO3CMMFEXAT2BHBEECY2GNU3WESJPKV3GG42NOBIU25LSMNWFI5KIIVBU65TKMZEC64ZZO5IXCSDVNNCTIVBYMVMTKWCZMY4XCUZPGVLHAZRYLFRTQQSEI43VCSDBMNGEY2SWHBKUQ3RTLIYE443HIJBXMZSJM5EEEQ3OJJBTS3TULBJTMVRVMZIWINRTNFXG242EME2TKRKYKVWTERLQHBWWU6JYJVJTA42NOVGWQRRTLBBFSVJRN5LTGRKXIVRGCS3OMNRUU2CWIFKWUTS2IUZXQK2KNNUXU2TQJRZXSSLHLFIGQRDFJA4XGQ2SFNFUCT3WONKFMQLULBJCW5L2OZJEI2DDKZ4USSZQNVGUU5TFNI4DMNCQMRXHM4CYNBUTQ4JWIJRVSSDOIJNEESDDJFVG46KBJZEXS6BUHFMGUR2IONDEU4RWIFWUK2BSPIXXE4LOIY4WYSDJNJIVUV2BHF3E44RQMNKE24RUNQ4EMMBUGF4VKNBRGVNHUSKLJVEEGURXOV5FGUL2OJSVI4SVKBNE22RXOAVWS3LCJR2GSWKFMRYWU4SFPJAU4VLHJNDWCLZLNF3GMQZTJRNFUT3LOVXTI2CKNZ2DQUCUG52TSS2IIZCDC23NG5KES2DTNQVXG3ZYJNSEM4TMJ5CU66LMMR4GYMDRMNCWQZ2OJE4W6TCBORUFEWJUK5VXOWTENVUEYZBXOZDXAULFLB4USNZYIVRG4WLTLJRFIZSGNZETMRTPOAVWUQLHKRGWS6JVGZ2WUQKWGNCXAN2TF5ZWI6BTPJUHMZDFGBBXKZCMIVBHATTHGNIWGZBSN4ZDQN2RGNCGQZJSMJMC65DTI5FEQYLENQYVCTBXJ5SXITDFOFDVORSTGJATONSNGFDTON2OLJRDML3FJN3FM3TZN42TGMTINQYGM5CIMRWHMWSBNZUSW42LHBIXE2CKNZXG6NRPJZ2UI22RNQYE4NLRNNJXQM3MPJCVURLNLJJW6MDQJ5LHQWCJKJHDSQ2UONCTG3KOGQ4FE42QN52W64CULJBC66RTJ5XE24LJGVEHOZTNMJGTM6LTN5LDMNTQGJIE4OKOLBGDOMSHM5EUSNSUPI4C6M2TGAYW2OCTNNAWM4TZIR5DSSS2J5FXS5KWGI2XQZLPGJLVOOBSN5GTCNJQG5XGQUJTIZ3HCZ22KF2XUQLDOBJGWMDMKFATAQKHLBBG62TRJRTEQ4CTMZDUUY2RMRGTI23XM5TVMQSCM5VXC2DLNFDTS5ZQIJBHOR3HM5TVK6KCJFEUMTDKINBUEU3PO5TWOVLNIJTXG4LINNUUOOLXGBBEIQLPIJAXCQ2DIJHTI53HM5KHCTKCO5DUG2LRI5JUSYRTIRIUKTKBKFGXORDHKFEVAUKNKQ2GYMJUJNLESQ2BM5TUCQSJJFCXSQTVOJZXOTRQMFYTAUKBGI4UI32MNZCEWMSTIFEEYUBWMZAUCNZWGBLDM33VJ5VWI43WJJIFS4CZJEZHIRDQPBCGUMDUGF2TIRDSLA2WGNZWIRZVC3BWF5MGG6LZJI4GGUKMK5GUI3LXFMXUU52WMFVHMNTOMF4UCMCTMRREEZL2O4ZEKWTSNNXUIYLTK5JCWVZXM5WFM3DRPB3EMMLJGFJDINZLMRRHUZL2INDTQVSLIFVTCT3PKRTGMZKEPJDGET3BI5RVEULTHFXTIU2POVCXMTTTIQ3TS2BTLE3TQZDHKVZWCYTFFNITKMBXGNEHA6CWMVRTOY2MGU3EUM2OOBRTESDROU3SWYLRI5GGUSTDF5MHMQTHLA4FUUKTNBJXOTLFONAWWM2PLBJGWSJYJFEWKWDJOVZDIL3CKFVUE4SZO5UGSMDVNZEFU6LKNJYW2UKFLJSU6WTLF5GVM4RSJZ3HCZCJGBZFQTTULF2HGMS2JI4GW5KFOY2TI6KQJI4GI3CDNRKDQUSIPFWUGMJPGNYWCMKNGRFUQ2KSORYUQWKRNI4HG42RNFFUCOKINJCUEM3WHB3G6T2NORGVEZKLNNYFE3KII5UU43JWJRQTG43WJ54DAZCYIVVUW2CFLBNEG5DNLJRVC6SMGRRC623JJA3TE5RVHFKUU5CNMY3DG3ZWO5XHEODWGRLGURCYJFCW64L2M5EGU6TZGB5E45ZLIJTUKKZUKJBGYS3QNJVUMTTVMZBEGQSXGZLTA3LDOVLGUY3VPBXXK3ZUJNJDSYSHLBLS65CRJYZXORLKMVJGSV3MIVMTC6TLNBVXCTZLKQ2DCN3OMMYFIQSCKMYXIYJWM4YTC2DVPFJDET3THE3XMY2BGU3SWRTWHFEFA6SPNJMXSYSTK5SVSVK2OZQWC5CTGRFHAN2ZGRBFE23FIQVUUQKZII4GI23COFYUE2DGOFTFA4LBHE2ECQLLGJSHAYKXIIZWCQJYNJJVK6JZNNGDK52PMRBFE2CJIYZUI2CFK5XEIQZWGN4XKYS2KA3VQ6DDJYZGIR2EGBIUE5SCNIYXC2BWOVIVUQRYKJEUQ5COOJZVE6CVM5TDO3TCJRFVGSKFJBQUSNK2J5ETKWTNMNHFMVBQNVKWMTSKIV3DGTKPMRRWGMTHLF2TE2JLJVJHQRLTIFHXCZZVOJ5FCVLBKVREIU3ZNZCHUWBSMF2TA3KUNVFUGZRPNRHG4SKLNFKE22CEPFMXS2KDKVTTMTT2OZXGMZ3RNNSEUQ3SLA4EQWSOOIVXE4KJMFJW6SSGMZHGCRTZKZNDSU2RGY3ESZCIGZSW6ULOI44U63DQM5SXE3ZRGVNDGSKCJRIXE5TSO5DEC4BWOZQUO3DLJ5VS653WG43TGZ3RJFNG2T2RGNNESVTEKY3TI4DIFNREYN2TF5MDAVSZJE2DI2LCNVMWCQJLMZMWGY2QGVEDQ2KROIYESRKHJFTU23LYMZGGKTTPJI2DIR3WLJQVM2SYME3HGWCWK5ICW2LFIJLU2SSIOFGSW5KTGFYEU43JMRSGM43VNJWXMMDGNN2GE33OGZBDG5TBMZDWOZRTMRZWUVDWIU4WMMCFKRETGTJXKRVUUMJPIQ4UI2SVFNZTO6CCFN4HC43PNFXHSSLSMZYW4RCJJVZGOSLRLFEVKSSFLFXHQQLFLA4FQRSCNFRFQ4LUMRDXAVKBKFWW6M2VKBNFARLELIZWMRD2MZ2WW43QGJXWO5LWOBWUCU3OGBFG2R2EIYYVCRLENVHWS22FGNBHO23CI5DCWY3OOBRHUODVNVFXA4DQM5VHS3SCNNIHUZDYKVWUK6KXJJ4UGYSTGA4HASZPNVKUWQ3OIVGGO6LFPFCE4MKHGZVGEQ2XJN5FQZLSN5LEU3DUN42EGUDCOVDUUWRSIFFHOQJLNBYDMMDFJBVUO2DWF5IDGOKWMNGDANCKMFBHCU2KLJXG6NZQOFYEGNTEGFMXI3CPJJEFEMSNMFQTSQSHKFLWC6LTIJJG45ZTIF4G23THMQ4WW22SIR3UCNDSGRFWORKDJNLVIZKCN5WGE6LDMZFWWU3ONVGTKWDPF53HKRCVKJ3VGSKXF5XU6SKSJJAXAK3LG54U6NRQJNJEONBUO5KG4NBZGVDEUVS2NNKVA3LFGRTTA4SHNAYHU4LXO5BUYSLJGM3UO6LBIRKG6VJZJ4VWUMKGGFIFO6LNGNHDCMSLN5TFEVBXMI2HMVTYJZYXEY2BHFEFS6KRJNFEOTJVNJDHAZCPLB3WMVT2LFFHGUSHGU2EET2II53XUNDMK5BFK5LTNZYGCTLCGIVXA5TOOBKTEWJTOBZC6MZROEZEI4TNM5RVOM3RMJJGQ2CULBRDCZDKJRIVQMKIJZ2C66TPINLFEQLOLFKESVBSJNHE63T2JJGVU3TVGNXHGOLGNVCGWYRQG42GG2CYHFMGWZKMHFFEUTJVLAVW6M3LG5EUCTRTOVATIZDJOY4VQYTVNZUDIZTILA2HIWSHJZ5EOUDXKVDTE4ZPGI2WMWKMKBRG46CEFNUE2WLZJZYUQZK2KZFXAU2II5UEGV2LNEXXKYLLJUVWKRJSKIZW63RQF5LXQOKPMFGU2VSIMJDW442YPJCWYTKDJVDUGU3RI5JUSYRTIRIUKSSGKRCVOQSCKN5DE5TSI4VVUWDCOZJE24COOIVXGMRQNZMHUZLRKFBFIQLYJVBUK52DKFMUMS3XGRCEC2DPIZAUCUKVNJGVGYLOKQ3VKZ2UKJKS6ODVNJKFKWCGNNBXKTSWNNRUKQ2EKNKVM3TXINKTOS3DIFTUSSKBIE6T2JZKIGHY7UDJIPTPMW7QVPNLGCQ";

    // The minimum time required for having the latest value of the
    // "malware.any":
    private static final long MIN_TIME_BETWEEN_DRA_CALC = 15 * 60 * 1000L;

    private boolean sdkInitialized = false;
    private String lastDraErrStr = null;

    public Trusteer() {
    }

    public boolean initSdK(Activity appContext) {
        Log.d(logTag, "initSdk");
        if (sdkInitialized) {
            Log.d(logTag, "Trusteer already initialized");
            tas.TasStartBackgroundOps();
            return true;
        }
        // Initialize the API:
        try {
            // Set the License fields:
            TAS_CLIENT_INFO client = new TAS_CLIENT_INFO();
            client.setVendorId(vendorId);
            client.setClientId(clientId);
            client.setClientKey(clientKey);

            // Initialize the API:
            int tasRetCode = atas.TasInitialize(appContext, client, tas.TAS_INIT_NO_OPT, tas.US_REGION);
            switch (tasRetCode) {
                case tas.TAS_RESULT_ALREADY_INITIALIZED:
                    Log.d(logTag, "Trusteer already initialized");
                    sdkInitialized = true;
                    tas.TasStartBackgroundOps();
                    break;
                case tas.TAS_RESULT_SUCCESS:
                    Log.d(logTag, "Trusteer successful initialize");
                    sdkInitialized = true;
                    tas.TasStartBackgroundOps();
                    break;
                default:
                    break;
            }
        } catch (Exception ex) {
            Log.e(logTag, ex.getMessage());
            ex.printStackTrace();
        }

        return (sdkInitialized);
    }

    /**
     * Perform Risk Assessment on the Device:
     *
     * @return String array of the Risk Assessment:
     */
    public JSONObject[] calcRiskScore() {
        // The reutrned Array:
        ArrayList<JSONObject> retRiskInfoArr = new ArrayList<JSONObject>();

        // Used in Exception message to identify the DRA function:
        String draFuncNameStr = "";
        // The object, which contains the DRO:
        TAS_OBJECT_REF tasObjRef = new TAS_OBJECT_REF();
        TAS_OBJECT tasObj;

        // Initialize the string, which contains the last error message:
        lastDraErrStr = null;

        // Used for storing the return codes of the SDK's API calls:
        int tasRetCode;
        try {
            // Check if the SDK's Background Operations are busy:
            tasRetCode = tas.TasWaitForBackgroundOps(5 * 1000);
            // If active - wait for some more time - 25 seconds (which are far
            // too much):
            int waitCnt = 0;
            do {
                tasRetCode = tas.TasWaitForBackgroundOps(5 * 1000);
                Log.d(logTag, "in calcRiskScore() - Background Operations are s still active - continue waiting."
                        + waitCnt++);
            } while (tasRetCode == tas.TAS_RESULT_TIMEOUT);

            // Get the Reference to the Risk Assessment object handle:
            draFuncNameStr = "TasDraGetRiskAssessment()";
            tasRetCode = tas.TasDraGetRiskAssessment(tasObjRef);
            if (tasRetCode != tas.TAS_RESULT_SUCCESS) {
                lastDraErrStr = "TasDraGetRiskAssessment() failed; retCode: \" + tasRetCode";
                // Log.d(logTag, "calcRiskScore() -
                // TasDraGetRiskAssessment() failed; retCode: " + tasRetCode);
                return (null);
            }
            // Store the Risk Assessment Object, stored in the handle:
            tasObj = tasObjRef.get_value();

            // Get a reference to the DRA Object:
            TAS_DRA_ITEM_INFO draItemInfo = new TAS_DRA_ITEM_INFO();

            // Each Risk item is re-calculated in a different rate (e.g.
            // "os.rooted", "malware.any" - every
            // 2 hours, "network.wifi" - every 5 minutes.
            // In order to make sure that the value of a Risk Item is
            // up-to-date, we can use the value of
            // its LastCalculated field.
            // the following code checks if the value of this field indicates
            // that the value of "malware.any"
            // was calculated more than 15 minutes ago then force a recalcuation
            // of the Risk ITems:
            tasRetCode = tas.TasDraGetRiskAssessmentItemByName(tasObj, "malware.any", draItemInfo);
            if (tasRetCode != tas.TAS_RESULT_SUCCESS) {
                lastDraErrStr = "TasDraGetRiskAssessmentItemByName() failed on \"malware.any\"";
                return (null);
            }
            // Calculate the time passed since the last calculation of the Risk
            // Item:
            Date draLastCalcDate = draItemInfo.getLastCalculated();
            long timeFromLastDraCalc = System.currentTimeMillis() - draLastCalcDate.getTime();
            Log.d(logTag, "TasDraGetRiskAssessment() : After Getting Risk Score for 'malware.any' Last calc: "
                    + draLastCalcDate.getTime() + " (" + draLastCalcDate + ") Diff from now: " + timeFromLastDraCalc);
            if (timeFromLastDraCalc > MIN_TIME_BETWEEN_DRA_CALC) {
                Log.d(logTag, "trusteerWaitResult: Time passeed more than: " + MIN_TIME_BETWEEN_DRA_CALC);
                Log.d(logTag, "getRiskAssesment before TasDraRecalcRiskAssessment()");
                tas.TasDraRecalcRiskAssessment(tas.TAS_DRA_FORCE_RECALC);
                Log.d(logTag, "getRiskAssesment after TasDraRecalcRiskAssessment()");
            }

            // Get the Risk Assessment Items:
            // -------------------------------

            // First. get their #:
//			TAS_INT_REF tasIntRef = new TAS_INT_REF();
//			draFuncNameStr = "TasDraGetRiskItemCount()";
//			tasRetCode = tas.TasDraGetRiskItemCount(tasObj, tasIntRef);
//			if (tasRetCode != tas.TAS_RESULT_SUCCESS) {
//				lastDraErrStr = "TasDraGetRiskItemCount() failed; retCode: \" + tasRetCode";
//				// Log.d(logTag, "calcRiskScore() -
//				// TasDraGetRiskItemCount() failed; retCode: " + tasRetCode);
//				return (null);
//			}
//			int numRiskItems = tasIntRef.get_value();
//
//			// Fetch the Risk Items:
//			for (int i = 0; i < numRiskItems; i++) {
//				draFuncNameStr = "TasDraGetRiskItemCount() item #: " + i;
//				tasRetCode = tas.TasDraGetRiskAssessmentItemByIndex(tasObj, i, draItemInfo);
//				if (tasRetCode != tas.TAS_RESULT_SUCCESS) {
//					lastDraErrStr = "TasDraGetRiskAssessmentItemByIndex() failed on index: " + i;
//					return (null);
//				}
//				// Convert the Risk item into a string and add to the returned
//				// array list:
//				retRiskInfoArr.add(draItem2String(draItemInfo));
//				// Check if there's an additional information:
//				getMalwareAppNames(tasObj, draItemInfo);
//
//			}

            String[] riskItemsName = {"os.rooted", "os.rooted.hiders"};

            for (int i = 0; i < riskItemsName.length; i++) {
                tasRetCode = tas.TasDraGetRiskAssessmentItemByName(tasObj, riskItemsName[i], draItemInfo);
                if (tasRetCode != tas.TAS_RESULT_SUCCESS) {
                    lastDraErrStr = "TasDraGetRiskAssessmentItemByName() failed on name: " + riskItemsName[i];
                    return (null);
                }
                // Convert the Risk item into a string and add to the returned
                // array list:
                retRiskInfoArr.add(draItem2String(draItemInfo));
                // Check if there's an additional information:
                getMalwareAppNames(tasObj, draItemInfo);

                Log.d(logTag, "TasDraGetRiskAssessmentItemByName() - output: " + draItem2String(draItemInfo));
            }

            // Release the Risk Assessment object:
            draFuncNameStr = "TasDraReleaseRiskAssessment";
            tasRetCode = tas.TasDraReleaseRiskAssessment(tasObj);
            if (tasRetCode != tas.TAS_RESULT_SUCCESS) {
                lastDraErrStr = "TasDraReleaseRiskAssessment() failed; retCode: " + tasRetCode;
                return (null);
            }

        } catch (Exception ex) {
            lastDraErrStr = String.format("calcRiskScore() - Exception while calling %s; %s", draFuncNameStr,
                    ex.getMessage());
            // Log.d(logTag, String.format(lastDraErrStr);
            ex.printStackTrace();
            retRiskInfoArr = null;
        } finally {
            // output the error message:
            if (lastDraErrStr != null)
                Log.d(logTag, lastDraErrStr);
        }

        Log.d(logTag, "in calcRiskScore() - end");
        if (retRiskInfoArr == null)
            return (null);

        Log.d(logTag, "calcRiskScore() - output: " + retRiskInfoArr.toString());
        return ((JSONObject[]) retRiskInfoArr.toArray(new JSONObject[retRiskInfoArr.size()]));
    }

    /**
     * Convert the contents of Dra Item info object into a string:
     *
     * @param draItemInfo
     * @return
     */
    private JSONObject draItem2String(TAS_DRA_ITEM_INFO draItemInfo) {
        String dataFormatStr = "%1$tY-%1$tB-%1$td %1$tH:%1$tM:%1$tS.%1$tL";
        try {
            JSONObject json = new JSONObject();
            json.put("Name", draItemInfo.getItemName());
            json.put("Value", draItemInfo.getItemValue());
            json.put("ValueTag", draItemInfo.getItemValueTag());
            json.put("LastCalculated", String.format(dataFormatStr, draItemInfo.getLastCalculated()));
            json.put("AdditionalData", draItemInfo.getAdditionalData());
            return json;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get the names of the identified malware:
     *
     * @param draItemInfo - Contains the result risk score.
     * @param draDetails  - Used to return the details of the collected information
     * @return - Wrapped value of the API's return code.
     */
    private void getMalwareAppNames(TAS_OBJECT tasObj, TAS_DRA_ITEM_INFO draItemInfo) {
        int tasRetCode;
        String lastTasError = null;

        // Used in Exception message to identify the DRA function:
        String tasFuncNameStr = "";

        Log.d(logTag, "getMalwareAppNames() - Starting");

        try {
            // 1. Get number of malware infections found on device:
            TAS_INT_REF malware_count_ref = new TAS_INT_REF();
            tasRetCode = tas.TasObGetCollectionPropertyCount(draItemInfo.getItemObject(), "detected_info",
                    malware_count_ref);
            // No additional information - return:
            if (tasRetCode != tas.TAS_RESULT_SUCCESS)
                return;

            // 2. Iterate over the malware infections
            int malware_count = malware_count_ref.get_value();

            // If nothing found - leave method:
            if (malware_count == 0) {
                // draDetails.add("No Additional Risk Score details
                // available.");
                return;
            }

            for (int i = 0; i < malware_count; ++i) {
                TAS_OBJECT_REF detection_info = new TAS_OBJECT_REF();
                tasFuncNameStr = "TasObGetCollectionObjectPropertyItem()";
                tasRetCode = tas.TasObGetCollectionObjectPropertyItem(draItemInfo.getItemObject(), "detected_info", i,
                        detection_info);
                if (tasRetCode != tas.TAS_RESULT_SUCCESS) {
                    lastTasError = "TasObGetCollectionObjectPropertyItem() failed; retCode: " + tasRetCode;
                    return;
                }
                TAS_STRING_REF name = new TAS_STRING_REF();
                TAS_STRING_REF removal_cookie = new TAS_STRING_REF();
                // 3. Get the values for property name and removal_cookie
                // tas.TasObGetScalarStringProperty(detection_info.get_value(),
                // "name", name);
                tasFuncNameStr = "TasObGetScalarStringProperty()";
                tasRetCode = tas.TasObGetScalarStringProperty(detection_info.get_value(), "name", name);
                if (tasRetCode != tas.TAS_RESULT_SUCCESS) {
                    lastTasError = "TasObGetScalarStringProperty() failed; retCode: " + tasRetCode;
                    return;
                }
                tasFuncNameStr = "TasObGetScalarStringProperty()";
                tasRetCode = tas.TasObGetScalarStringProperty(detection_info.get_value(), "removal_cookie",
                        removal_cookie);
                if (tasRetCode != tas.TAS_RESULT_SUCCESS) {
                    lastTasError = "TasObGetScalarStringProperty() failed; retCode: " + tasRetCode;
                    return;
                }

                // verify items are not null
                if (name.get_value() != null && name.get_value().length() > 0 && removal_cookie.get_value() != null
                        && removal_cookie.get_value().length() > 0) {
                    // use 'name' and 'removal cookie'
                    // draDetails.add(String.format("Removal_cookie: %s",
                    // removal_cookie.get_value()));
                }
            }

        } catch (Exception ex) {
            lastTasError = String.format("getMalwareAppNames() - Exception while calling %s; %s", tasFuncNameStr,
                    ex.getMessage());
            ex.printStackTrace();
        } finally {
            // output the error message:
            if (lastTasError != null)
                Log.d(logTag, lastTasError);
        }
    }

    public boolean isSdkInitialized() {
        return (sdkInitialized);
    }

    private static boolean checkTrusteerSDK() {
        try {
            Class.forName("com.trusteer.tas.TAS_CLIENT_INFO");
            return true;
        } catch (Throwable localThrowable) {
        }
        return false;
    }
}
