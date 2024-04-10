module.exports =
{
	// Tas error codes
	TAS_RESULT_SUCCESS: 0, /**< \brief Success */
	TAS_RESULT_GENERAL_ERROR: -1, /**< \brief General Error */
	TAS_RESULT_INTERNAL_ERROR: -2, /**< \brief Internal Error */
	TAS_RESULT_WRONG_ARGUMENTS: -3, /**< \brief Wrong Arguments */
	TAS_RESULT_DRA_ITEM_NOT_FOUND: -4, /**< \brief DRA Item Not Found */
	TAS_RESULT_NO_POLLING: -5, /**< \brief Tried to use polling when configured to non-polling */
	TAS_RESULT_TIMEOUT: -6, /**< \brief Timeout */
	TAS_RESULT_NOT_INITIALIZED: -7, /**< \brief A component has not been initialized or configured  */
	TAS_RESULT_UNAUTHORIZED: -8, /**< \brief Unauthorized */
	TAS_RESULT_ALREADY_INITIALIZED: -9, /**< \brief Already initialized */
	TAS_RESULT_ARCH_NOT_SUPPORTED: -10, /**< \brief Device's architechture is not supported */
	TAS_RESULT_INTERNAL_EXCEPTION: -11, /**< \brief Internal exception occured */
	TAS_RESULT_INCORRECT_SETUP: -12, /**< \brief Could not initialize: requested operating mode's requirements were not fulfilled */
	TAS_RESULT_INSUFFICIENT_PERMISSIONS: -13, /**< \brief Could not initialize: required permissions are not granted by the user */
	TAS_RESULT_MISSING_PERMISSIONS_IN_FOLDER: -14, /**< \brief Could not initialize: missing permissions in the sdk folder */
	TAS_RESULT_DISABLED_BY_CONFIGURATION: -15, /**< \brief Could not initialize: disabled by configuration */
	TAS_RESULT_NETWORK_ERROR: -16, /**< \brief Network Error */
	TAS_RESULT_CONNECTION_INTERNAL_TIMEOUT: -17, /**< \brief Connection opened but closed because of connection read/write timeout. By default the timeout is 5 seconds. The timeout value can be set in the configuration file and put under pinpoint_integration.request_timeout_ms*/
	TAS_RESULT_PINPOINT_CERTIFICATE_PROBLEM: -18, /**< \brief This error occurs when the certificate or certificate password is wrong or if the license expired*/
	TAS_RESULT_INVALID_CONFIGURATION: -19, /**< \brief Invalid Configuration*/
	TAS_RESULT_DEVICE_IS_COMPROMISED: -20, /**< \brief The device is compromised*/
	TAS_RESULT_WRONG_DEVICE_KEY_METHOD: -21, /**< \brief The wrong method for getting the device key was used */

	// TasInitialize options
	TAS_INIT_NO_OPT: 0,  /**< \brief No initialization options */
	TAS_INIT_MANUAL_BG_OPS: 2,  /**< \brief Manual background operations. If ommited, use Autonomous mode */
	TAS_INIT_DELAYED_BG_OPS: 4,  /**< \brief When using Autonomous mode only:
													force the background operations to start after the task interval time elapses.
													If omitted, operations will start immediately */
	TAS_INIT_SUPPRESS_LOGS: 8,  /**< \brief When set, info log is suppressed */
	TAS_INIT_AVOID_UPLOAD_IN_MOBILE_DATA: 16, /**< \brief When set, avoid upload files to the server in mobile date (load only via wi-fi) */
	TAS_INIT_EXTRA_DRA_DATA: 32, /**< \brief Extra dra items */
	TAS_INIT_NO_SERVICE: 64, /**< \brief When using Autonomous mode only:
                                                    When set Android service will not be initiated */
	/**< \brief TAS callbacks ids */
	TAS_EXCEPTION_CALLBACK_KEY: 1, /**< \brief Signal exception callback */
	TAS_OVERLAY_CALLBACK_KEY: 2, /**< \brief Overlay detection callback (Android only) */
	TAS_ACCESSIBILITY_CALLBACK_KEY: 4, /**< \brief Accessibility detection callback (Android only) */

	//LastPinpointResponse
	TAS_RESPONSE_SUCCESS: 0,
	TAS_RESPONSE_NONE: 1,
	TAS_RESPONSE_IN_PROGRESS: 2,
	TAS_RESPONSE_ERR_INCORRECT_SETUP: 3,
	TAS_RESPONSE_ERR_NETWORK: 4,
	TAS_RESPONSE_ERR_WRONG_ARGUMENTS: 5,
	TAS_RESPONSE_ERR_GENERAL: 6,
	TAS_RESPONSE_ERR_CONNECTION_TIMEOUT: 7,
	TAS_RESPONSE_ERR_CERTIFICATE: 8,
	TAS_RESPONSE_ERR_SSL_PINNING: 9,

	//States
	TAS_STATE_NOT_UPDATED: 10,
	TAS_STATE_PHASE_1_INCOMPLETE: 11,
	TAS_STATE_PHASE_1_COMPLETE: 12,
	TAS_STATE_ALL_COMPLETE: 13,

	//Regions
	TAS_US_REGION: 1,
	TAS_EU_REGION: 2,
	TAS_JP_REGION: 3,


	//
	// TasInitialize - Initialize Tas engine
	// params:
	//		cb_success  : success callback of format cbSuccess()
	//		cb_fail : failure callback of format cbFailure(err_code)
	//		init_option : one of the TAS_INIT_XXXX options (i.e. Tas.TAS_INIT_NO_OPT)
	//		vendor_id : string representation of the vendor (i.e. 'com.trusteer')
	//		client_id : string representation of the client (i.e. 'com.trusteer.tas.test')
	//		comment : comment to be associated with the session (i.e. 'TAS Test Application'). May be NULL
	//		client_key : client key obtained from Trusteer
	//		region : one of the TAS_XX_REGION (i.e. TAS_US_REGION)
	//
	TasInitialize: function (cb_success, cb_fail, init_option, vendor_id, client_id, comment, client_key, region) {
		cordova.exec(cb_success, cb_fail, "Tas", "TasInitialize", [init_option, vendor_id, client_id, comment, client_key, region]);
	},

	// (Deprecated)
	// TasInitializeWithCallback - Initialize TAS within an application giving a callback function for exception handling
	// params:
	//		cb_success  : success callback of format cbSuccess()
	//		cb_fail : failure callback of format cbFailure(err_code)
	//		init_option : one of the TAS_INIT_XXXX options (i.e. Tas.TAS_INIT_NO_OPT)
	//		vendor_id : string representation of the vendor (i.e. 'com.trusteer')
	//		client_id : string representation of the client (i.e. 'com.trusteer.tas.test')
	//		comment : comment to be associated with the session (i.e. 'TAS Test Application'). May be NULL
	//		client_key : client key obtained from Trusteer
	//		callback_func : callback function
	//		region : one of the TAS_XX_REGION (i.e. TAS_US_REGION)
	//
	TasInitializeWithCallback: function (cb_success, cb_fail, init_option, vendor_id, client_id, comment, client_key, callback_func, region) {
		console.log("WARNING: 'TasInitializeWithCallback' is deprecated and might be removed on future versions. Please use 'TasInitializeWithCallbacks' instead.")

		this.TasInitializeWithCallbacks(cb_success, cb_fail, init_option, vendor_id, client_id, comment, client_key, [callback_func], region)
	},

	//
	// TasInitializeWithCallbacks - Initialize TAS within an application giving a callback functions
	// params:
	//		cb_success  : success callback of format cbSuccess()
	//		cb_fail : failure callback of format cbFailure(err_code)
	//		init_option : one of the TAS_INIT_XXXX options (i.e. Tas.TAS_INIT_NO_OPT)
	//		vendor_id : string representation of the vendor (i.e. 'com.trusteer')
	//		client_id : string representation of the client (i.e. 'com.trusteer.tas.test')
	//		comment : comment to be associated with the session (i.e. 'TAS Test Application'). May be NULL
	//		client_key : client key obtained from Trusteer
	//		callbacks : callback functions object
	//		region : one of the TAS_XX_REGION (i.e. TAS_US_REGION)
	//
	TasInitializeWithCallbacks: function (cb_success, cb_fail, init_option, vendor_id, client_id, comment, client_key, callbacks, region) {
		var client_callbacks = {};
		for (var cbId in callbacks) {
			if (callbacks[cbId])
				client_callbacks[cbId] = callbacks[cbId]
		}

		function fail(resultParams) {
			if (typeof (resultParams) != 'object') {
				//If it's a simple error(int,string, etc) delegate the error back to the user callback 
				if (typeof (cb_fail) == 'function') {
					cb_fail(resultParams);
				}
				else {
					console.log("Client callback object is not a function.");
				}
			}
			else {
				//If the returned value is of a callback type, execute the callback
				var cbId = resultParams[0];

				if (client_callbacks == 0) {
					console.log("No callbacks were provided by the client.");
					return;
				}

				if (typeof (client_callbacks[cbId]) != 'function') {
					console.log("Client callback object is not a function.");
					return;
				}

				switch (cbId) {
					case Tas.TAS_EXCEPTION_CALLBACK_KEY:
						var errorMessage = resultParams[1];
						client_callbacks[cbId](errorMessage);
						break;
					case Tas.TAS_OVERLAY_CALLBACK_KEY:
						var pkg_name = resultParams[1];
						var malware_name = resultParams[2];
						var app_name = resultParams[3];
						client_callbacks[cbId](pkg_name, malware_name, app_name);
						break;
					case Tas.TAS_ACCESSIBILITY_CALLBACK_KEY:
						client_callbacks[cbId]();
						break;
					default:
						console.log("Unfamiliar callback key: " + cbId);
				}
			}
		}

		cordova.exec(cb_success, fail, "Tas", "TasInitializeWithCallbacks", [init_option, vendor_id, client_id, comment, client_key, Object.keys(client_callbacks), region]);
	},

	//
	// TasFinalize - Terminates Tas engine
	// params:
	//		cb_success  : success callback of format cbSuccess()
	//		cb_fail : failure callback of format cbFailure(err_code)
	//
	TasFinalize: function (cb_success, cb_fail) {
		cordova.exec(cb_success, cb_fail, "Tas", "TasFinalize", []);
	},

	//
	// TasTempCheckForUpdates - checks for updates
	// params:
	//		cb_success  : success callback of format cbSuccess()
	//		cb_fail : failure callback of format cbFailure(err_code)
	//
	TasTempCheckForUpdates: function (cb_success, cb_fail) {
		cordova.exec(cb_success, cb_fail, "Tas", "TasTempCheckForUpdates", []);
	},

	//
	// TasTempDeleteConfiguration - delete Tas configuration files
	// params:
	//		cb_success  : success callback of format cbSuccess()
	//		cb_fail : failure callback of format cbFailure(err_code)
	//
	TasTempDeleteConfiguration: function (cb_success, cb_fail) {
		cordova.exec(cb_success, cb_fail, "Tas", "TasTempDeleteConfiguration", []);
	},

	//
	// TasSetUserId - Set the user ID
	// params:
	//		cb_success  : success callback of format cbSuccess()
	//		cb_fail : failure callback of format cbFailure(err_code)
	//		user_id : id string of the user
	//
	TasSetUserId: function (cb_success, cb_fail, user_id) {
		cordova.exec(cb_success, cb_fail, "Tas", "TasSetUserId", [user_id]);
	},

	//
	// TasGetDeviceKey - get the device key
	// params:
	//		cb_success  : success callback of format cbSuccess(device_key)
	//		cb_fail : failure callback of format cbFailure(err_code)
	//
	// notes:
	//		device_key is string
	//
	TasGetDeviceKey: function (cb_success, cb_fail) {
		cordova.exec(cb_success, cb_fail, "Tas", "TasGetDeviceKey", []);
	},

	//
	// TasGetSecuredDeviceKey - get the device key
	// params:
	//		cb_success  : success callback of format cbSuccess(device_key)
	//		cb_fail : failure callback of format cbFailure(err_code)
	//
	// notes:
	//		device_key is string
	//
	TasGetSecuredDeviceKey: function (cb_success, cb_fail) {
		cordova.exec(cb_success, cb_fail, "Tas", "TasGetSecuredDeviceKey", []);
	},

	//
	// TasGetCurrentVersion - get the engine & configuration versions
	// params:
	//		cb_success  : success callback of format cbSuccess(version_info)
	//		cb_fail : failure callback of format cbFailure(err_code)
	//
	// notes:
	//		version_info is object which contains:
	//			major : number of engine's major version
	//			minor : number of engine's minor version
	//			build : number of engine's build version
	//			apiLevel : number of engine's api level version
	//			confVer : number of configuration version
	//			lastUpdated : date time (number of seconds since 1970) of the last update
	//
	TasGetCurrentVersion: function (cb_success, cb_fail) {
		cordova.exec(cb_success, cb_fail, "Tas", "TasGetCurrentVersion", []);
	},

	//
	// TasDraGetRiskAssessment - Get a risk assessment object
	// params:
	//		cb_success  : success callback of format cbSuccess(tas_object)
	//		cb_fail : failure callback of format cbFailure(err_code)
	//
	// notes:
	//		tas_object is a handle for native object
	//
	TasDraGetRiskAssessment: function (cb_success, cb_fail) {
		cordova.exec(cb_success, cb_fail, "Tas", "TasDraGetRiskAssessment", []);
	},

	//
	// TasDraReleaseRiskAssessment - Get a risk assessment object
	// params:
	//		cb_success  : success callback of format cbSuccess()
	//		cb_fail : failure callback of format cbFailure(err_code)
	//		tas_object : native object retrieve by previous call to TasDraGetRiskAssessment
	//
	TasDraReleaseRiskAssessment: function (cb_success, cb_fail, tas_object) {
		cordova.exec(cb_success, cb_fail, "Tas", "TasDraReleaseRiskAssessment", [tas_object]);
	},

	//
	// TasDraGetRiskItemCount - Get the number of risk assessment items
	// params:
	//		cb_success  : success callback of format cbSuccess(items_count)
	//		cb_fail : failure callback of format cbFailure(err_code)
	//		tas_object : native object retrieve by previous call to TasDraGetRiskAssessment
	//
	// notes:
	//		items_count is integer number of items
	//
	TasDraGetRiskItemCount: function (cb_success, cb_fail, tas_object) {
		cordova.exec(cb_success, cb_fail, "Tas", "TasDraGetRiskItemCount", [tas_object]);
	},

	//
	// TasDraGetRiskAssessmentItemByIndex - Get the risk assessment item by its index
	// params:
	//		cb_success  : success callback of format cbSuccess(item_info)
	//		cb_fail : failure callback of format cbFailure(err_code)
	//		tas_object : native object retrieve by previous call to TasDraGetRiskAssessment
	//		index : index of the desired risk assasment item
	//
	// notes:
	//		item_info is object which contains:
	//			name : name of the item
	//			value : score value of the item
	//			last_time : last time the item was calculated
	//			additional_data : additional information about the item as string
	//			error : item assessment error indication
	//
	TasDraGetRiskAssessmentItemByIndex: function (cb_success, cb_fail, tas_object, index) {
		cordova.exec(cb_success, cb_fail, "Tas", "TasDraGetRiskAssessmentItemByIndex", [tas_object, index]);
	},

	//
	// TasDraGetRiskAssessmentItemByName - Get the risk assessment item by its name
	// params:
	//		cb_success  : success callback of format cbSuccess(item_info)
	//		cb_fail : failure callback of format cbFailure(err_code)
	//		tas_object : native object retrieve by previous call to TasDraGetRiskAssessment
	//		name : name of the desired risk assasment item
	//
	// notes:
	//		item_info is object which contains:
	//			name : name of the item
	//			value : score value of the item
	//			last_time : last time the item was calculated
	//			additional_data : additional information about the item as string
	//			error : item assessment error indication
	//			item_object : A TAS object handle providing additional information on the item
	//
	TasDraGetRiskAssessmentItemByName: function (cb_success, cb_fail, tas_object, name) {
		cordova.exec(cb_success, cb_fail, "Tas", "TasDraGetRiskAssessmentItemByName", [tas_object, name]);
	},

	// Device Risk Assasment
	TAS_DRA_NO_OPT: 0,
	TAS_DRA_FORCE_RECALC: 1,
	TAS_DRA_FAST_RECALC: 2,

	//
	// TasDraRecalcRiskAssessment - Recalculate the risk items
	// params:
	//		cb_success  : success callback of format cbSuccess()
	//		cb_fail : failure callback of format cbFailure(err_code)
	//		option : type of the recalculation
	//
	// notes:
	//		option is a number of one of the followings:
	//          Tas.TAS_DRA_NO_OPT : No calculation options
	//			Tas.TAS_DRA_FORCE_RECALC : force recalculation of device risk assessment data, regardless of expiration dates
	//			Tas.TAS_DRA_FAST_RECALC : only recalculate 'fast' items; this excludes slow malware scanning processes
	//
	TasDraRecalcRiskAssessment: function (cb_success, cb_fail, option) {
		cordova.exec(cb_success, cb_fail, "Tas", "TasDraRecalcRiskAssessment", [option]);
	},

	//
	// TasDraGetDraString - Get string representing the Device Risk Assesment
	// params:
	//		cb_success  : success callback of format cbSuccess(dra_string)
	//		cb_fail : failure callback of format cbFailure(err_code)
	//		tas_object : native object retrieve by previous call to TasDraGetRiskAssessment
	//
	TasDraGetDraString: function (cb_success, cb_fail, tas_object) {
		cordova.exec(cb_success, cb_fail, "Tas", "TasDraGetDraString", [tas_object]);
	},

	//
	// TasObGetScalarStringProperty - Get the value of a scalar string property from a TAS object
	// params:
	//		cb_success  : success callback of format cbSuccess(dra_string)
	//		cb_fail : failure callback of format cbFailure(err_code)
	//		tas_object : native object retrieve by previous call to Tas object creator (like TasDraGetRiskAssessment)
	//		property_name : name of the property
	//
	TasObGetScalarStringProperty: function (cb_success, cb_fail, tas_object, property_name) {
		cordova.exec(cb_success, cb_fail, "Tas", "TasObGetScalarStringProperty", [tas_object, property_name]);
	},

	//
	// TasObGetCollectionPropertyCount - Get the count of elements in a collection property from a TAS object
	// params:
	//		cb_success  : success callback of format cbSuccess(propery_value)
	//		cb_fail : failure callback of format cbFailure(err_code)
	//		tas_object : native object retrieve by previous call to Tas object creator (like TasDraGetRiskAssessment)
	//		property_name : name of the property
	//
	TasObGetScalarStringProperty: function (cb_success, cb_fail, tas_object, property_name) {
		cordova.exec(cb_success, cb_fail, "Tas", "TasObGetScalarStringProperty", [tas_object, property_name]);
	},

	//
	// TasObGetCollectionPropertyCount - Get the count of elements in a collection property from a TAS object
	// params:
	//		cb_success  : success callback of format cbSuccess(elements_count)
	//		cb_fail : failure callback of format cbFailure(err_code)
	//		tas_object : native object retrieve by previous call to Tas object creator (like TasDraGetRiskAssessment)
	//		property_name : name of the property
	//
	TasObGetCollectionPropertyCount: function (cb_success, cb_fail, tas_object, property_name) {
		cordova.exec(cb_success, cb_fail, "Tas", "TasObGetCollectionPropertyCount", [tas_object, property_name]);
	},

	//
	// TasObGetCollectionStringPropertyItem - Get the value of an item in a collection property of strings from a TAS object
	// params:
	//		cb_success  : success callback of format cbSuccess(elements_value)
	//		cb_fail : failure callback of format cbFailure(err_code)
	//		tas_object : native object retrieve by previous call to Tas object creator (like TasDraGetRiskAssessment)
	//		property_name : name of the property
	//		index : index of the element in the collection
	//
	TasObGetCollectionStringPropertyItem: function (cb_success, cb_fail, tas_object, property_name, index) {
		cordova.exec(cb_success, cb_fail, "Tas", "TasObGetCollectionStringPropertyItem", [tas_object, property_name, index]);
	},

	//
	// TasObGetCollectionObjectPropertyItem - Get the value of an item in a collection property of objects from a TAS object
	// params:
	//		cb_success  : success callback of format cbSuccess(elements_value)
	//		cb_fail : failure callback of format cbFailure(err_code)
	//		tas_object : native object retrieve by previous call to Tas object creator (like TasDraGetRiskAssessment)
	//		property_name : name of the property
	//		index : index of the element in the collection
	//
	TasObGetCollectionObjectPropertyItem: function (cb_success, cb_fail, tas_object, property_name, index) {
		cordova.exec(cb_success, cb_fail, "Tas", "TasObGetCollectionObjectPropertyItem", [tas_object, property_name, index]);
	},

	//
	// TasStartBackgroundOps - Start backgroung operations
	// params:
	//		cb_success  : success callback of format cbSuccess()
	//		cb_fail : failure callback of format cbFailure(err_code)
	//
	// notes:
	//		The call doesn't wait for background operations to complete
	//
	TasStartBackgroundOps: function (cb_success, cb_fail) {
		cordova.exec(cb_success, cb_fail, "Tas", "TasStartBackgroundOps", []);
	},

	//
	// TasWaitForBackgroundOps - Give TAS a chance to perform background operations
	// params:
	//		cb_success  : success callback of format cbSuccess()
	//		cb_fail : failure callback of format cbFailure(err_code)
	//		timeout : time is miliseconds to wait
	//
	TasWaitForBackgroundOps: function (cb_success, cb_fail, timeout) {
		cordova.exec(cb_success, cb_fail, "Tas", "TasWaitForBackgroundOps", [timeout]);
	},

	//
	// TasAtoCreateSession - Creates new pinpoint ATO session object
	// params:
	//		cb_success  : success callback of format cbSuccess(tas_object)
	//		cb_fail : failure callback of format cbFailure(err_code)
	//		session_id : id string for the session
	//
	// notes:
	//		tas_object is a handle for native object
	//
	TasAtoCreateSession: function (cb_success, cb_fail, session_id) {
		cordova.exec(cb_success, cb_fail, "Tas", "TasAtoCreateSession", [session_id]);
	},

	//
	// TasAtoSetUserId - Set the user ID for a given pinpoint session
	// params:
	//		cb_success  : success callback of format cbSuccess()
	//		cb_fail : failure callback of format cbFailure(err_code)
	//		tas_object : native object retrieve by previous call to TasAtoCreateSession
	//		user_id : id string of the user
	//
	TasAtoSetUserId: function (cb_success, cb_fail, tas_obj, user_id) {
		cordova.exec(cb_success, cb_fail, "Tas", "TasAtoSetUserId", [tas_obj, user_id]);
	},

	//
	// TasAtoSetAuxiliary - Add new key/value pair to the pinpoint session's map
	// params:
	//		cb_success  : success callback of format cbSuccess()
	//		cb_fail : failure callback of format cbFailure(err_code)
	//		tas_object : native object retrieve by previous call to TasAtoCreateSession
	//		key : name of the key
	//		val : value of the key
	//
	TasAtoSetAuxiliary: function (cb_success, cb_fail, tas_obj, key, val) {
		cordova.exec(cb_success, cb_fail, "Tas", "TasAtoSetAuxiliary", [tas_obj, key, val]);
	},

	//
	// TasAtoRemoveAuxiliary - Remove new key/value pair to the pinpoint session's map
	// params:
	//		cb_success  : success callback of format cbSuccess()
	//		cb_fail : failure callback of format cbFailure(err_code)
	//		tas_object : native object retrieve by previous call to TasAtoCreateSession
	//		key : name of the key to remove
	//
	TasAtoRemoveAuxiliary: function (cb_success, cb_fail, tas_obj, key) {
		cordova.exec(cb_success, cb_fail, "Tas", "TasAtoRemoveAuxiliary", [tas_obj, key]);
	},

	//
	// TasAtoGetCommunicationPayload - Generates the session's snapshot payload
	// params:
	//		cb_success  : success callback of format cbSuccess(data)
	//		cb_fail : failure callback of format cbFailure(err_code)
	//		tas_object : native object retrieve by previous call to TasAtoCreateSession
	//
	// notes:
	//		data is an object of type ArrayBuffer
	//
	TasAtoGetCommunicationPayload: function (cb_success, cb_fail, tas_obj, data) {
		cordova.exec(cb_success, cb_fail, "Tas", "TasAtoGetCommunicationPayload", [tas_obj]);
	},

	//
	// TasAtoDestroySession - Destroy pinpoint session
	// params:
	//		cb_success  : success callback of format cbSuccess()
	//		cb_fail : failure callback of format cbFailure(err_code)
	//		tas_object : native object retrieve by previous call to TasAtoCreateSession
	//
	TasAtoDestroySession: function (cb_success, cb_fail, tas_obj) {
		cordova.exec(cb_success, cb_fail, "Tas", "TasAtoDestroySession", [tas_obj]);
	},

	//
	// TasSetLoggerCallback - Set Logger callback function
	// params:
	//		cb_success  : success callback of format cbSuccess(msg)
	//		cb_fail : failure callback of format cbFailure(err_code)
	//
	// notes:
	//		upon success, the cb_success is called repeatedly with the new log message
	//
	TasSetLoggerCallback: function (cb_success, cb_fail) {
		cordova.exec(cb_success, cb_fail, "Tas", "TasSetLoggerCallback", []);
	},

	// Tas anti pharming
	TasApharmEnable: function (cb_success, cb_fail, enable) {
		cordova.exec(cb_success, cb_fail, "Tas", "TasApharmEnable", [enable]);
	},

	TasApharmIsEnabled: function (cb_success, cb_fail) {
		cordova.exec(cb_success, cb_fail, "Tas", "TasApharmIsEnabled", []);
	},

	/*
	TasApharmRegisterPolicyCallback: function(cb_success, cb_fail)
	{
		cordova.exec(cb_success, cb_fail, "Tas", "TasApharmRegisterPolicyCallback", []);
	},
	 */

	//
	// TasApharmValidateSslCert - Validates SSL certificate
	// params:
	//		cb_success  : success callback of format cbSuccess(is_valid)
	//		cb_fail : failure callback of format cbFailure(err_code)
	//		cert_chain : SSL certificate chain of type ArrayBuffer
	//		cert_chain_len : length SSL certificate chain
	//		host : name of the host
	//
	// notes:
	//		success returns if the certificate is valid or not
	//
	TasApharmValidateSslCert: function (cb_success, cb_fail, cert_chain, cert_chain_len, host) {
		cordova.exec(cb_success, cb_fail, "Tas", "TasApharmValidateSslCert", [cert_chain, cert_chain_len, host]);
	},

	//
	// TasSetPUID - Set the permanent user ID
	// params:
	//		cb_success  : success callback of format cbSuccess()
	//		cb_fail : failure callback of format cbFailure(err_code)
	//		puid : id string of the user
	//
	TasSetPUID: function (cb_success, cb_fail, puid) {
		cordova.exec(cb_success, cb_fail, "Tas", "TasSetPUID", [puid]);
	},

	//
	// TasRaCreateSession - Create and start Pinpoint integration session
	// params:
	//		cb_success  : success callback of format cbSuccess(session_obj)
	//		cb_fail : failure callback of format cbFailure(err_code)
	//		session_id : id string for the session
	//
	// notes:
	//		session_obj is a handle for native object
	//
	TasRaCreateSession: function (cb_success, cb_fail, session_id) {
		cordova.exec(cb_success, cb_fail, "Tas", "TasRaCreateSession", [session_id]);
	},

	//
	// TasRaDestroySession - Destroy previously created session
	// params:
	//		cb_success  : success callback of format cbSuccess()
	//		cb_fail : failure callback of format cbFailure(err_code)
	//		session_obj : native object created by previous call to TasRaCreateSession
	//
	TasRaDestroySession: function (cb_success, cb_fail, session_obj) {
		cordova.exec(cb_success, cb_fail, "Tas", "TasRaDestroySession", [session_obj]);
	},

	//
	// TasRaCreateActivityData - Create activity data object
	// params:
	//		cb_success  : success callback of format cbSuccess(activity_object)
	//		cb_fail : failure callback of format cbFailure(err_code)
	//
	// notes:
	//		activity_object is a handle for native object
	//
	TasRaCreateActivityData: function (cb_success, cb_fail) {
		cordova.exec(cb_success, cb_fail, "Tas", "TasRaCreateActivityData", []);
	},

	//
	// TasRaActivityAddData - Add key-value to activity data object
	// params:
	//		cb_success  : success callback of format cbSuccess()
	//		cb_fail : failure callback of format cbFailure(err_code)
	//		activity_object : native object created by previous call to TasRaCreateActivityData
	//		key : name of the key
	//		val : value of the key
	//
	TasRaActivityAddData: function (cb_success, cb_fail, activity_object, key, val) {
		cordova.exec(cb_success, cb_fail, "Tas", "TasRaActivityAddData", [activity_object, key, val]);
	},

	//
	// TasRaDestroyActivityData - Destroy activity data object
	// params:
	//		cb_success  : success callback of format cbSuccess()
	//		cb_fail : failure callback of format cbFailure(err_code)
	//		activity_object : native object created by previous call to TasRaCreateActivityData
	//
	TasRaDestroyActivityData: function (cb_success, cb_fail, activity_object) {
		cordova.exec(cb_success, cb_fail, "Tas", "TasRaDestroyActivityData", [activity_object]);
	},

	//
	// TasRaNotifyUserActivity - Notify Pinpoint integration servers with the user activity
	// params:
	//		cb_success  : success callback of format cbSuccess()
	//		cb_fail : failure callback of format cbFailure(err_code)
	//		session_obj : native object created by previous call to TasRaCreateSession
	//		user_activity : string representing the user activity (i.e. 'login', 'transaction' ...)
	//		activity_object : native object created by previous call to TasRaCreateActivityData
	//		timeout : timeout for the function to finish
	//
	TasRaNotifyUserActivity: function (cb_success, cb_fail, session_obj, user_activity, activity_object, timeout) {
		cordova.exec(cb_success, cb_fail, "Tas", "TasRaNotifyUserActivity", [session_obj, user_activity, activity_object, timeout]);
	},

	//
	// TasRaGetRiskAssessment - Notify Pinpoint integration servers with the user activity and get the risk assessment
	// params:
	//		cb_success  : success callback of format cbSuccess(risk_assessment_object)
	//		cb_fail : failure callback of format cbFailure(err_code)
	//		session_obj : native object created by previous call to TasRaCreateSession
	//		activity_object : native object created by previous call to TasRaCreateActivityData
	//		user_activity : string representing the user activity (i.e. 'login', 'transaction' ...)
	//		timeout : timeout for the function to finish
	//
	// notes:
	//      risk_assessment_object is a dictionary with the following keys:
	//         recommendation
	//         resolution_id
	//         reason_id
	//         reason
	//         risk_score
	//
	TasRaGetRiskAssessment: function (cb_success, cb_fail, session_obj, user_activity, activity_object, timeout) {
		cordova.exec(cb_success, cb_fail, "Tas", "TasRaGetRiskAssessment", [session_obj, user_activity, activity_object, timeout]);
	},

	//
	// TasBehaveGetScore - Notify behaviourmetrics servers with the last behaviourmetrics data, if exists and get the score assessment
	// params:
	//		cb_success  : success callback of format cbSuccess()
	//		cb_fail : failure callback of format cbFailure(err_code)
	//		timeout : timeout for the function to finish
	//
	TasBehaveGetScore: function (cb_success, cb_fail, timeout) {
		cordova.exec(cb_success, cb_fail, "Tas", "TasBehaveGetScore", [timeout]);
	},

	// TasRaGetStatus - Get Pinpoint status information
	// params:
	//		cb_success  : success callback of format cbSuccess(status_info)
	//		cb_fail : failure callback of format cbFailure(err_code)
	//
	// notes:
	//      status_info is a dictionary with the following keys:
	//         lastPinpointResponse
	//         state
	//
	TasRaGetStatus: function (cb_success, cb_fail) {
		cordova.exec(cb_success, cb_fail, "Tas", "TasRaGetStatus", []);
	},

	// CheckOverlay - Check overlay
	// params:
	//		cb_success  : success callback of format cbSuccess()
	//		cb_fail : failure callback of format cbFailure(err_code)
	//
	CheckOverlay: function (cb_success, cb_fail) {
		cordova.exec(cb_success, cb_fail, "Tas", "CheckOverlay", []);
	}
}

