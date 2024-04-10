#ifndef __taslib_h_included__
#define __taslib_h_included__

#include "taslib_shared.h"

#ifdef __cplusplus
#include <string>
#include <map>
extern "C"
{
#endif

#include <time.h>
#include <netinet/in.h>
#include <stdbool.h>



/**
 * \brief Initialize TAS within an application.
 *
 *
 * \return 0 on success; nonzero on failure. See \ref taslib.h for all possible error codes.
 * \ingroup TasManagement
 */
TAS_RESULT TAS_API enivywfhvacr(
    TAS_CLIENT_INFO *ClientInfo, /**< Information on the client requesting the session. */
    void            *Reserved,   /**< Reserved. Must be NULL. */
    int              InitFlags,  /**< Initialization flags -- use TAS_INIT_OPTIONS constants. */
    int              Region      /**< The region -- use TAS_REGION constants. */
);

/**
 * \brief Initialize TAS within an application giving a callback function for exception handling.
 *
 *
 * \return 0 on success; nonzero on failure. See \ref taslib_defs.h for all possible error codes.
 * \ingroup TasManagement
 */
TAS_RESULT TAS_API yjuhsextntik(
    TAS_CLIENT_INFO *ClientInfo, /**< Information on the client requesting the session. */
    void            *Reserved,   /**< Reserved. Must be NULL. */
    int              InitFlags,  /**< Initialization flags -- use TAS_INIT_OPTIONS constants. */
    TAS_EXCEPTION_CALLBACK ExceptionCallback, /**< Exception callback function */
    int              Region      /**< The region -- use TAS_REGION constants. */
);

/**
 * \brief Initialize TAS within an application giving an array of callbacks.
 *
 * Using this method, TAS can be initialized with multiple callbacks.
 * Currently we support callbacks for:
 * 1) Exception handling - will be called when exception signal occurred
 * 2) Overlay attack detection (used only in android) - will be called when an overlay attack was detected.
 * 	  NOTE: Overlay check will be preformed if 'checkOverlay' API function is called, also without
 * 	  defining the OverlayCallback.
 *
 * \return 0 on success; nonzero on failure. See \ref taslib_defs.h for all possible error codes.
 * \ingroup TasManagement
 */
TAS_RESULT TAS_API vopinfgrsjvt(
    TAS_CLIENT_INFO *ClientInfo, /**< Information on the client requesting the session. */
    void            *Reserved,   /**< Reserved. Must be NULL. */
    int              InitFlags,  /**< Initialization flags -- use TAS_INIT_OPTIONS constants. */
    TasCallback *callbackArray,  /**< Array of callbacks */
    int callbackArraySize,       /**< Size of the array of callbacks */
    int              Region      /**< The region -- use TAS_REGION constants. */
);

/**
 * \brief Give TAS a chance to perform background operations.
 *
 * This call notifies TAS it should check and possibly initiate background operations such as
 * Trusteer configuration update and risk score calculation.
 * The call doesn't wait for background operations to complete.
 *
 * \return 0 on success; nonzero on failure. See \ref taslib_defs.h for all possible error codes.
 * \ingroup TasManagement
 */
TAS_RESULT TAS_API noseiyquczar(void);

/**
 * \brief Check for the status of background operations.
 *
 * This call waits for Timeout milliseconds or until all TAS in-flight background tasks are
 * completed.
 * The return value of this function may indicate a timeout error if there are still background
 * tasks running at timeout.
 *
 * By passing a timeout of 0 and observing the return value a client can use this call to
 * query whether or not there are any active background operations.
 *
 * \return 0 on success; nonzero on failure. See \ref taslib_defs.h for all possible error codes.
 * \ingroup TasManagement
 */
TAS_RESULT TAS_API eqyegiydqipl(
    int Timeout /**< Timeout in milliseconds to wait for background ops termination */
);

/**
 * \brief Finalize TAS within an application.
 *
 * \return 0 on success; nonzero on failure. See \ref taslib_defs.h for all possible error codes.
 * \ingroup TasManagement
 */
TAS_RESULT TAS_API ethozrbixxsb(void);

////////////////////////////////////////////// Object Access //////////////////////////////////////////////

/**
 * \defgroup ObjectAccess Generic TAS object access functions
 */

/**
 * \brief Get the value of a scalar string property from a TAS object
 *
 * Retrieves the value of a scalar string property from a TAS object, given the property name.
 *
 * Value is retrieved into a caller-provided buffer, with a caller-provided length. In addition
 * the function returns the length of data actually copied to the buffer.
 *
 * The function returns success even if the buffer provided is too small to contain the whole
 * value -- but updates \ref InOutValueLen accordingly.
 *
 * By passing NULL in OutValue, you can retrieve the required length of the buffer.
 *
 * \return 0 on success; nonzero on failure. See \ref taslib_defs.h for all possible error codes.
 * \ingroup ObjectAccess
 */
TAS_RESULT TAS_API vbntbzotvamv(
    TAS_OBJECT  Object,       /**< Object to fetch property from */
    const char *PropertyName, /**< Name of property to fetch */
    char       *OutValue,     /**< [out] Target buffer, or NULL to fetch length */
    size_t     *InOutValueLen /**< [inout] On entry, length of provided buffer. On exit length of copied value.   */
);

/**
 * \brief Get the count of elements in a collection property from a TAS object
 *
 * Retrieves the count of elements in a collection property from a TAS object, given the property name.
 *
 * \return 0 on success; nonzero on failure. See \ref taslib_defs.h for all possible error codes.
 * \ingroup ObjectAccess
 */
TAS_RESULT TAS_API hinjnkfuduaj(
    TAS_OBJECT  Object,       /**< Object to fetch property from */
    const char *PropertyName, /**< Name of property to fetch count of */
    int        *OutCount      /**< Count of elements in property */
);

/**
 * \brief Get the value of an item in a collection property of strings from a TAS object
 *
 * Retrieves the value of a single string item from a collection property of a TAS object,
 * given the property name and the item index within the collection.
 *
 * Value is retrieved into a caller-provided buffer, with a caller-provided length. In addition
 * the function returns the length of data actually copied to the buffer.
 *
 * The function returns success even if the buffer provided is too small to contain the whole
 * value -- but updates \ref InOutValueLen accordingly.
 *
 * By passing NULL in OutValue, you can retrieve the required length of the buffer.
 *
 * \return 0 on success; nonzero on failure. See \ref taslib_defs.h for all possible error codes.
 * \ingroup ObjectAccess
 */
TAS_RESULT TAS_API yagojgrokpmy(
    TAS_OBJECT  Object,       /**< Object to fetch property from */
    const char *PropertyName, /**< Name of property to fetch */
    int         Index,        /**< Index (0-based) of item within collection to fetch. */
    char       *OutValue,     /**< [out] Target buffer, or NULL to fetch length */
    size_t     *InOutValueLen /**< [inout] On entry, length of provided buffer. On exit length of copied value.   */
);

/**
 * \brief Get the value of an item in a collection property of objects from a TAS object
 *
 * Retrieves the value of a single object item from a collection property of a TAS object,
 * given the property name and the item index within the collection.
 *
 * \return 0 on success; nonzero on failure. See \ref taslib_defs.h for all possible error codes.
 * \ingroup ObjectAccess
 */
TAS_RESULT TAS_API shkzangletak(
    TAS_OBJECT  Object,       /**< Object to fetch property from */
    const char *PropertyName, /**< Name of property to fetch */
    int Index,                /**< Index (0-based) of item within collection to fetch. */
    TAS_OBJECT *OutValue      /**< [out] Handle to returned object */
);

////////////////////////////////////////////// Device Risk Assessment //////////////////////////////////////////////

/**
 * \defgroup DeviceRiskAssessment Device Risk Assessment
 */

/**
 * \brief Get a risk assessment object.
 *
 * Retrieves a risk assessment object which can then be queried for attributes such as
 * risk items and overall risk score.
 * The returned object should be freed using \ref TasDraReleaseRiskAssessment.
 * \return 0 on success; nonzero on failure. See \ref taslib_defs.h for all possible error codes.
 * \ingroup DeviceRiskAssessment
 */
TAS_RESULT TAS_API aisfbalowtrv(
    TAS_OBJECT *Object /**< [out] Returned object handle */
);

/**
 * \brief Device risk assessment recalculation options mask.
 *
 * \ingroup DeviceRiskAssessment
 */
typedef int TAS_DRA_CALC_OPTIONS;

#define TAS_DRA_NO_OPT 0 /**< \brief No calculation options */
#define TAS_DRA_FORCE_RECALC 1 /**< \brief Force recalculation of device risk assessment data, regardless of expiration dates */
#define TAS_DRA_FAST_RECALC 2  /**< \brief Only recalculate 'fast' items; this excludes slow malware scanning processes */


//TAS_INIT_OPTIONS extends
#define TAS_INIT_MANUAL_BG_OPS                  2  /**< \brief Manual background operations. If omitted, use Autonomous mode */
#define TAS_INIT_NO_SERVICE                    64  /**< \brief When using Autonomous mode only:
When set Android service will not be initiated */


/**
 * \brief Force recalculation of risk assessment data.
 *
 * Normally risk score recalculation occurs at the discretion of the TAS background
 * processing facility. This function allows the client application to explicitly request
 * that TAS updates out-of-date risk score items.
 *
 * Either a complete recalculation of all items (whether expired or not), or just
 * expired items can be requested. In addition, a fast recalc option allows the client
 * to specify that only fast-calculating items should be updated.
 *
 * \return 0 on success; nonzero on failure. See \ref taslib_defs.h for all possible error codes.
 *
 * \ingroup DeviceRiskAssessment
 */
TAS_RESULT TAS_API punubdzdnobj(
    TAS_DRA_CALC_OPTIONS Options /**< Recalculation options. A combination of one or more \ref TAS_DRA_CALC_OPTIONS values */
);

/**
 * \brief Release a risk assessment object.
 *
 * Releases a risk assessment object obtained by \ref TasDraGetRiskAssessment.
 *
 * \return 0 on success; nonzero on failure. See \ref taslib_defs.h for all possible error codes.
 * \ingroup DeviceRiskAssessment
 */
TAS_RESULT TAS_API fwdtijtfagce(
    TAS_OBJECT Object /**< Object handle to release */
);

/**
 * \brief Get count of risk items in assessment object.
 *
 * Retrieves the count of risk items in a risk assessment object. These are the items participating
 * in calculating to overall risk assessment score.
 *
 * \return 0 on success; nonzero on failure. See \ref taslib_defs.h for all possible error codes.
 * \ingroup DeviceRiskAssessment
 */
TAS_RESULT TAS_API loqzepnlevrc(
    TAS_OBJECT  Object, /**< Risk assessment object to query */
    int        *Result  /**< [out] Count of risk score items */
);

/**
 * \brief Provides information on a risk score item.
 *
 * \ingroup DeviceRiskAssessment
 * \struct TAS_DRA_ITEM_INFO
 */
typedef struct tagTAS_DRA_ITEM_INFO {
    char       ItemName[128];                               /**< Name of item */
    int        ItemValue;                                   /**< Value of risk score item */
    int        ItemValueTag;                                /**< A tag for the item value; this is a symbolic meaning of the value.
                                                                 -1 if the value was based on a continuous scale. **/
    int        ItemError;                                   /**< Error during last calculation*/
    time_t     LastCalculated;                              /**< Last calculation time of risk score item */
    char       AdditionalData[ADDITIONAL_DATA_MAX_LENGTH+1];/**< Additional information regarding the item. */
    TAS_OBJECT ItemObject;                                  /**< A TAS object handle providing additional information on the
                                                                 item; this depends on the specific item type. */
} TAS_DRA_ITEM_INFO;

/**
 * \brief Get individual risk score item information by index.
 *
 * Retrieves a risk score item summary given an index to the risk score item.
 *
 * \return 0 on success; nonzero on failure. See \ref taslib_defs.h for all possible error codes.
 * \ingroup DeviceRiskAssessment
 */
TAS_RESULT TAS_API lecplkykmsdt(
    TAS_OBJECT         Object,        /**< Risk assessment object to query */
    int                RiskItemIndex, /**< Index of risk item to return */
    TAS_DRA_ITEM_INFO *RiskItemInfo   /**< [out] Returned risk item info */
);

/**
 * \brief Get individual risk score item information by item name.
 *
 * Retrieves a risk score item summary given the item's name. See the TAS_DRA_ITEM_xxx constants
 * for predefined names.
 *
 * \return 0 on success; nonzero on failure. See \ref taslib_defs.h for all possible error codes.
 * \ingroup DeviceRiskAssessment
 */
TAS_RESULT TAS_API ltjfzlqkzfjb(
    TAS_OBJECT         Object,       /**< Risk assessment object to query */
    const char        *RiskItemName, /**< NULL terminated name of item */
    TAS_DRA_ITEM_INFO *RiskItemInfo  /**< [out] Returned risk item info */
);

/**
 * \brief Get the most recent time when risk assessment was recalculated.
 *
 * Retrieves the latest LastCalculated value for risk score items.
 *
 * \return 0 on success; nonzero on failure. See \ref taslib_defs.h for all possible error codes.
 * \ingroup DeviceRiskAssessment
 */
TAS_RESULT TAS_API qlybecnujqky(
    time_t *Time
);

/**
 * \brief Get string representing the Device Risk Assessment
 *
 * \return 0 on success; nonzero on failure. See \ref taslib_defs.h for all possible error codes.
 * \ingroup DeviceRiskAssessment
 */
TAS_RESULT TAS_API owtlmjqerkvz(
    TAS_OBJECT  Object,            /**< Risk assessment object to query */
    char       *DraString,         /**< [out] Device Risk Assessment as string */
    size_t      *DraStringLength   /**< [inout] On entry, length of provided buffer. On exit length of copied value.   */
);

////////////////////////////////////////////// Antipharming //////////////////////////////////////////////

/**
 * \defgroup AntiPharming Anti-pharming protection
 */

/**
 * \brief Enable/disable antipharming protection.
 *
 * This call allows to temporarily enable or disable antipharming protection.
 * Calls are reference counted, so multiple calls to enable should be coupled with the same amount of calls to disable.
 * By default antipharming protection is turned on after Tas initialization.
 *
 * Antipharming can't be enabled if not configured in the manifest.
 *
 * Antipharming may not be available on all platforms.
 *
 * \return 0 on success; nonzero on failure. See \ref taslib_defs.h for all possible error codes.
 * \ingroup AntiPharming
*/
TAS_RESULT TAS_API hfdfckihyhfk(
    int Enable /**< Enable (nonzero)/disable (zero) antipharming */
);

/**
 * \brief Returns non-zero if anti-pharming is active, and zero otherwise.
 *
 * \return non-zero if anti-pharming is active, and zero otherwise.
 * \ingroup AntiPharming
*/
int TAS_API rzygfkjnegyf(void);


/**
 * \brief Custom antipharming policy callback
 *
 * This is a callback for implementing a custom antipharming policy by the client. When configured through the
 * security manifest, TAS will invoke this callback to consult about the validate of name resolutions.
 *
 * The callback may be called in two contexts; one is before resolution starts, to determine if a host is at all covered by the policy,
 * and a second time to evaluate resolution result and decide if it should be fixed.
 *
 * When called before resolution starts, the \ref ResolvedAddress array is NULL. The callback should then return true to indicate the host
 * is protected, false otherwise. When called after resolution happens, the callback should return true to indicate that the resolved address
 * is valid, or false otherwise.
 *
 * \return  Policy result. See above.
 * \ingroup AntiPharming
  */
typedef bool (*TAS_ANTIPHARMING_POLICY_CALLBACK)(
    const char      *HostName,             /**< Resolved host name */
    struct sockaddr *ResolvedAddress,      /**< If invoked pre-resolve, NULL. If invoked post-resolve, array of resolution result addresses to evaluate */
    size_t           ResolvedAddressCount, /**< Count of elements in ResolvedAddress */
    void            *Context,              /**< The Context used when registering the callback using \ref TasApharmRegisterPolicyCallback */
    void           **FixHint               /**< Reserved. Must be NULL. */
);

/**
 * \brief event registration callback
 *
 * This is a callback for implementing a custom additional policy for handling events.
 * Registering an event callback will cause the callback to be activated when an event will occure.
 *
 * \return  true -  if event callback was registered
 * \ingroup AntiPharming
  */
typedef bool (*TAS_EVENT_REGISTRATION_CALLBACK)(
    const char *EventName /** <name of event object that has arrived. */
);


/**
 * \brief Register a custom policy callback.
 *
 * This call registers a custom antipharming policy callback. The callback is registered with a specific name
 * that identifies it when referred from the manifest.
 *
 * \return 0 on success; nonzero on failure. See \ref taslib_defs.h for all possible error codes.
 * \ingroup AntiPharming
*/
TAS_RESULT TAS_API mbcnqvsjruze(
    const char                      *CallbackName, /**< Name of callback to register */
    TAS_ANTIPHARMING_POLICY_CALLBACK Callback,     /**< Pointer to registered callback function */
    void                            *Context       /**< A user-defined context pointer to be passed as-is to the callback. Can be NULL. */
);

/**
 * \brief Register a custom event receiver callback.
 *
 * This call registers a custom event receiver callback.
 * Only one callback can be defined,
 * registering another callback will override the previous registered callback
 *
 * \return 0 on success; nonzero on failure. See \ref taslib_defs.h for all possible error codes.
 * \ingroup AntiPharming
*/
TAS_RESULT TAS_API TasRegisterEventReceiverCallback(
    TAS_EVENT_REGISTRATION_CALLBACK Callback, /**< Pointer to registered callback function */
    void                           *Context   /**< A user-defined context pointer to be passed as-is to the callback. Can be NULL. */
);

/**
 * \brief Validate an SSL certificate chain for a host.
 *
 * This function checks an SSL certificate chain for a specified host, and returns a flag indicating whether
 * the SSL certificate chain is valid for the specified host.
 * SSL validation is subject to policy (including blacklist, whitelist) defined by the TAS configuration and manifest.
 *
 * \return 0 on success; nonzero on failure. See \ref taslib_defs.h for all possible error codes.
 * \ingroup AntiPharming
*/
TAS_RESULT TAS_API rcslfitabsfp(
    const char *SerializedCertChain,     /**< Serialized certificate chain (see developer's guide) */
    size_t      SerializedCertChainSize, /**< Size of serialized certificate chain */
    const char *Host,                    /**< Connected host for which certificate is provided */
    int        *Result                   /**< [out] SSL validation result; 0 for valid. Nonzero for invalid. */
);

////////////////////////////////////////////// Info Query //////////////////////////////////////////////

/**
 * \defgroup TasInfo Information query
 */

/**
 * \brief Provides information on TAS library current version
 *
 * \ingroup TasInfo
 * \struct TAS_VERSION_INFO
 */
typedef struct tagTAS_VERSION_INFO {
    int    Major;           /**< Library version major component */
    int    Minor;           /**< Library version minor component */
    int    Patch;           /**< Library patch number */
    int    Build;           /**< Library build number */
    int    ApiLevel;        /**< Library API level */
    int    ConfVer;         /**< Library configuration number */
    time_t ConfLastUpdated; /**< Last time library configuration has been updated (in days) */
} TAS_VERSION_INFO;

/**
 * \brief Get current library version
 *
 * \return 0 on success; nonzero on failure. See \ref taslib_defs.h for all possible error codes.
 * \ingroup TasInfo
 */
TAS_RESULT TAS_API qtbbkqxjdxrk(
    TAS_VERSION_INFO *VersionInfo /**< [out] Returned version info */
);

/**
 * \brief Gets the device key
 *
 * Retrieves a NULL terminated device key string.
 *
 * If the given buffer to accommodate the device key is too small an error it returned.
 *
 * By passing NULL in \ref DeviceKey, you can retrieve the total required length of the buffer.
 *
 * \return 0 on success; nonzero on failure. See \ref taslib_defs.h for all possible error codes.
 * \ingroup TasInfo
 */
TAS_RESULT TAS_API bpsclufpgorv(
    char   *DeviceKey,  /**< [out] Target buffer, or NULL to fetch length. If not NULL, on success will contain the NULL-terminated device key. */
    size_t *InOutKeyLen /**< [inout] On entry, length of provided buffer. On exit length of copied value.   */
);

/**
 * \brief Gets the device key
 *
 * Retrieves a secure device key.
 *
 * If the given buffer to accommodate the device key is too small an error it returned.
 *
 * By passing NULL in \ref DeviceKey, you can retrieve the total required length of the buffer.
 *
 * \return 0 on success; nonzero on failure. See \ref taslib_defs.h for all possible error codes.
 * \ingroup TasInfo
 */
TAS_RESULT TAS_API nwkhgpfkzzeu(
        char   * DeviceKey, 	/**< [inout] pointer to the target buffer, or NULL in order to get the buffer size */
        size_t * InOutKeyLen	/**< [inout] On entry, length of provided buffer. On exit length of the return buffer data. */
);

/**
 * \brief Logger callback function type
 *
 * \ingroup TasInfo
 */
typedef void(*TAS_LOGGER_CALLBACK)(int Severity, const char *Authority, const char *Location, const char *Message, void *Context);

/**
 * \brief Set Logger callback function
 *
 * \return 0 on success; nonzero on failure. See \ref taslib_defs.h for all possible error codes.
 * \ingroup TasInfo
 */
TAS_RESULT TAS_API ksnoyfrakprq(
    TAS_LOGGER_CALLBACK LoggerCallback, /**< Callback function */
    void               *Context         /**< Context for callback function */
);

/***************** TEMPORARY ADDITIONS START *********************/
TAS_RESULT TAS_API zqnvljmgwyct(void);
TAS_RESULT TAS_API fdnbihlvxeub(void);
/***************** TEMPORARY ADDITIONS END *********************/

////////////////////////////////////////////// TAS Pinpoint ATO //////////////////////////////////////////////
/**
 * \defgroup PinpointATO pinpoint ATO
 *
 * \deprecated PinpointATO functions are deprecated. RiskAssessment functions should be used if it is licensed
 */

/**
 * \brief Creates new pinpoint ATO session object.
 *
 * Sets the bank session id.
 * Must call TasAtoDestroySession in order to release that object.
 *
 * \return 0 on success; nonzero on failure. See \ref taslib_defs.h for all possible error codes.
 *
 * \ingroup PinpointATO
 */
TAS_RESULT TAS_API duiwmziftvxi(
		TAS_OBJECT* obj, 			/**< [out] pointer to the new allocated session object */
		const char* bank_session_id	/**< [in] session ID string */
);

/**
 * \brief set the user ID for a given session.
 *
 * \return 0 on success; nonzero on failure. See \ref taslib_defs.h for all possible error codes.
 *
 * \ingroup PinpointATO
 *
 * \deprecated Use TasSetUserId instead
 */
TAS_RESULT TAS_API bpejnbqyrwga(
		TAS_OBJECT obj,  	/**< [in] the session object */
		const char* userId 	/**< [in] user ID string */
);

/**
 * \brief add new key/value pair to the session's map.
 *
 * \return 0 on success; nonzero on failure. See \ref taslib_defs.h for all possible error codes.
 *
 * \ingroup PinpointATO
 */
TAS_RESULT TAS_API nlkvotbatmvf(
		TAS_OBJECT obj, 	/**< [in] the session object */
		const char* key, 	/**< [in] key string to add */
		const char* value	/**< [in] value string to add */
);

/**
 * \brief remove a record of a given key from the session's map.
 *
 * \return 0 on success; nonzero on failure. See \ref taslib_defs.h for all possible error codes.
 *
 * \ingroup PinpointATO
 */
TAS_RESULT TAS_API tzrfwrmlivoz(
		TAS_OBJECT obj, /**< [in] the session object */
		const char* key	/**< [in] key string to remove */
);

/**
 * \brief generates the session's snapshot payload.
 *
 * this method should be called from non UI thread since it might take some time.
 * first call with blob=NULL (second parameter) will return the blob size as the length value (fourth parameter).
 * second call will return the full blob content.
 *
 * \return 0 on success; nonzero on failure. See \ref taslib_defs.h for all possible error codes.
 *
 * \ingroup PinpointATO
 */
TAS_RESULT TAS_API mtqxrboachsa(
		TAS_OBJECT obj, 	/**< [in] the session object */
		char* blob, 		/**< [inout] pointer to the blob array, or NULL in order to get the blob size */
		size_t* InOutBlobLength	/**< [inout] On entry, length of provided buffer. On exit length of the return blob data. */
);

/**
 * \brief Destroy previously created session
 *
 * \return 0 on success; nonzero on failure. See \ref taslib_defs.h for all possible error codes.
 *
 * \ingroup PinpointATO
 */
TAS_RESULT TAS_API oakupggdjirc(
		TAS_OBJECT obj	/**< [in] the session object */
);

////////////////////////////////////////////// TAS RiskAssessment ATO //////////////////////////////////////////////
/**
 * \defgroup RiskAssessment Risk Assessment
 */

/**
 * \brief TAS activity data handle.
 *
 * \ingroup RiskAssessment
 */
typedef void *TAS_RA_ACTIVITY_DATA;


/**
* \brief Create and start Pinpoint risk assessment session and set it with the bank session ID
*
* Sets the bank session ID and fills the session_obj pointer with the session object
* You must call TasRaDestroySession to release the session object before creating a new one
*
* \return 0 on success; nonzero on failure. See \ref taslib_defs.h for all possible error codes.
*
* \ingroup RiskAssessment
*/
TAS_RESULT TAS_API nvhmyxnbfbae(
    TAS_OBJECT* session_obj,                /**< [out] pointer to the new allocated session object */
    const char* bank_session_id             /**< [in] session ID string */
);

/**
* \brief Destroy previously created Pinpoint risk assessment session and release its resources
*
* \return 0 on success; nonzero on failure. See \ref taslib_defs.h for all possible error codes.
*
* \ingroup RiskAssessment
*/
TAS_RESULT TAS_API satrbxulvbjd(
        TAS_OBJECT obj	/**< [in] the session object created in TasRaCreateSession*/
);

/**
* \brief Create activity data object which can be filled with keys and values with \ref TasRaActivityAddData
*
* Must call TasRaDestroyActivityData to release that object
*
* \return 0 on success; nonzero on failure. See \ref taslib_defs.h for all possible error codes.
*
* \ingroup RiskAssessment
*/
TAS_RESULT TAS_API vxkydhpcavse (
    TAS_RA_ACTIVITY_DATA *activity_data        /**< [out] pointer to the new allocated activity data object */
);

/**
* \brief Add key-value pairs to the activity data object
*
* If the key already exists, its value is overwritten with the new data
*
* \return 0 on success; nonzero on failure. See \ref taslib_defs.h for all possible error codes.
*
* \ingroup RiskAssessment
*/
TAS_RESULT TAS_API eacryapfmwxn (
    TAS_RA_ACTIVITY_DATA activity_data,      /**< [in] activity data object */
    const char* key,                        /**< [in] key string to add */
    const char* value                       /**< [in] value string to add */
);

/**
* \brief Release activity data object and its resources
*
* \return 0 on success; nonzero on failure. See \ref taslib_defs.h for all possible error codes.
*
* \ingroup RiskAssessment
*/
TAS_RESULT TAS_API iuroesmcocul (
    TAS_RA_ACTIVITY_DATA activity_data         /**< [in] previously allocated activity data object */
);

/**
* \brief Notify Pinpoint risk assessment servers with the user activity
*
* Wait for session data to be collected and update servers with user activity and related data passed in activity data object
*
* \return 0 on success; nonzero on failure. See \ref taslib_defs.h for all possible error codes.
*
* \ingroup RiskAssessment
*/
TAS_RESULT TAS_API ocimriurppsr (
    TAS_OBJECT session_obj,                 /**< [in] the session object created in TasRaCreateSession */
    const char* user_activity,              /**< [in] one of the predefined user activities */
    TAS_RA_ACTIVITY_DATA activity_data,     /**< [in] additional activity data (optional) */
    int timeout                             /**< [in] timeout in milliseconds to wait for the function to return */
);

/**
* \brief Notify Pinpoint risk assessment servers with the user activity and get the risk assessment
*
* Wait for session data to be collected and update servers with user activity and related data passed in activity data object
*
* Upon success return, the risk_assessment structure is filled with the server's response
*
* \return 0 on success; nonzero on failure. See \ref taslib_defs.h for all possible error codes.
*
* \ingroup RiskAssessment
*/
TAS_RESULT TAS_API boxtywmpzgos (
    TAS_OBJECT session_obj,                 /**< [in] the session object created in TasRaCreateSession*/
    const char* user_activity,              /**< [in] one of the predefined user activities */
    TAS_RA_ACTIVITY_DATA activity_data,     /**< [in] additional activity data (optional) */
    TAS_RA_RISK_ASSESSMENT *risk_assessment,/**< [out] returned risk assessment structure */
	int timeout                             /**< [in] timeout in milliseconds to wait for the function to return */
);


////////////////////////////////////////////// Behaviourmetrics //////////////////////////////////////////////
/**
 * \defgroup Behaviourmetrics  Behaviourmetrics
 */


/**
 * \brief Behavioral score response structure
 * \ingroup Behaviourmetrics
 */
typedef struct tagTAS_BEHAVE_SCORE {
    int  behavioralScore;                                 /**< The  Behavioral Score */
    int  confidenceScore;                                 /**< The Confidence Score */
    char additionalInfo[ADDITIONAL_DATA_MAX_LENGTH + 1];   /**< Additional information string */
} TAS_BEHAVE_SCORE;

/**
 * \brief Notify behaviourmetrics servers with the last behaviourmetrics data, if exists and get the score assessment
 *
 * PUID is required as a prior condition
 *
 * Upon success return the Behavioral Score and confidence
 *
 * \return 0 on success; nonzero on failure. See \ref taslib_defs.h for all possible error codes.
 *
 * \ingroup Behaviourmetrics
 */
TAS_RESULT TAS_API ntrdzhjsgnat (
        TAS_BEHAVE_SCORE *behave_score,           /**< [out] behave score data */
		int timeout                               /**< [in] timeout for the function to finish */
);

#ifdef __cplusplus
}
#endif

#define TasInitialize(clientInfo, reserved, initFlags, region) enivywfhvacr(clientInfo, reserved, initFlags, region)
#define TasInitializeWithCallback(clientInfo, reserved, initFlags, exceptionCallback, region) yjuhsextntik(clientInfo, reserved, initFlags, exceptionCallback, region)
#define TasInitializeWithCallbacks(clientInfo, reserved, initFlags, callbackArray, callbackArraySize, region) vopinfgrsjvt(clientInfo, reserved, initFlags, callbackArray, callbackArraySize, region)
#define TasStartBackgroundOps() noseiyquczar()
#define TasWaitForBackgroundOps(timeout) eqyegiydqipl(timeout)
#define TasFinalize() ethozrbixxsb()
#define TasObGetScalarStringProperty(object, propertyName, outValue, inOutValueLen) vbntbzotvamv(object, propertyName, outValue, inOutValueLen)
#define TasObGetCollectionPropertyCount(object, propertyName, outCount) hinjnkfuduaj(object, propertyName, outCount)
#define TasObGetCollectionStringPropertyItem(object, propertyName, index, outValue, inOutValueLen) yagojgrokpmy(object, propertyName, index, outValue, inOutValueLen)
#define TasObGetCollectionObjectPropertyItem(object, propertyName, index, outValue) shkzangletak(object, propertyName, index, outValue)
#define TasDraGetRiskAssessment(object) aisfbalowtrv(object)
#define TasDraRecalcRiskAssessment(options) punubdzdnobj(options)
#define TasDraReleaseRiskAssessment(object) fwdtijtfagce(object)
#define TasDraGetRiskItemCount(object, result) loqzepnlevrc(object, result)
#define TasDraGetRiskAssessmentItemByIndex(object, riskItemIndex, riskItemInfo) lecplkykmsdt(object, riskItemIndex, riskItemInfo)
#define TasDraGetRiskAssessmentItemByName(object, riskItemName, riskItemInfo) ltjfzlqkzfjb(object, riskItemName, riskItemInfo)
#define TasDraGetLastRiskAssessmentUpdateTime(time) qlybecnujqky(time)
#define TasDraGetDraString(object, draString, draStringLength) owtlmjqerkvz(object, draString, draStringLength)
#define TasApharmEnable(enable) hfdfckihyhfk(enable)
#define TasApharmIsEnabled() rzygfkjnegyf()
#define TasApharmRegisterPolicyCallback(callbackName, callback, context) mbcnqvsjruze(callbackName, callback, context)
#define TasApharmValidateSslCert(serializedCertChain, serializedCertChainSize, host, result) rcslfitabsfp(serializedCertChain, serializedCertChainSize, host, result)
#define TasGetCurrentVersion(versionInfo) qtbbkqxjdxrk(versionInfo)
#define TasGetDeviceKey(deviceKey, inOutKeyLen) bpsclufpgorv(deviceKey, inOutKeyLen)
#define TasGetSecuredDeviceKey(deviceKey, inOutKeyLen) nwkhgpfkzzeu(deviceKey, inOutKeyLen)
#define TasSetLoggerCallback(loggerCallback, context) ksnoyfrakprq(loggerCallback, context)
#define TasTempCheckForUpdates() zqnvljmgwyct()
#define TasTempDeleteConfiguration() fdnbihlvxeub()
#define TasAtoCreateSession(obj, bank_session_id) duiwmziftvxi(obj, bank_session_id)
#define TasAtoSetUserId(obj, userId) bpejnbqyrwga(obj, userId)
#define TasAtoSetAuxiliary(obj, key, value) nlkvotbatmvf(obj, key, value)
#define TasAtoRemoveAuxiliary(obj, key) tzrfwrmlivoz(obj, key)
#define TasAtoGetCommunicationPayload(obj, blob, inOutBlobLength) mtqxrboachsa(obj, blob, inOutBlobLength)
#define TasAtoDestroySession(obj) oakupggdjirc(obj)
#define TasRaCreateSession(session_obj, bank_session_id) nvhmyxnbfbae(session_obj, bank_session_id)
#define TasRaDestroySession(obj) satrbxulvbjd(obj)
#define TasRaCreateActivityData(activity_data) vxkydhpcavse(activity_data)
#define TasRaActivityAddData(activity_data, key, value) eacryapfmwxn(activity_data, key, value)
#define TasRaDestroyActivityData(activity_data) iuroesmcocul(activity_data)
#define TasRaNotifyUserActivity(session_obj, user_activity, activity_data, timeout) ocimriurppsr(session_obj, user_activity, activity_data, timeout)
#define TasRaGetRiskAssessment(session_obj, user_activity, activity_data, risk_assessment, timeout) boxtywmpzgos(session_obj, user_activity, activity_data, risk_assessment, timeout)
#define TasBehaveGetScore(behave_score, timeout) ntrdzhjsgnat(behave_score, timeout)

#endif /* __taslib_h_included__ */
