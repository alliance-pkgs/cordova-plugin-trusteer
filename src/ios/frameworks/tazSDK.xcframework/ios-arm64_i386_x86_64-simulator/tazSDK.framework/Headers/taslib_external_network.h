
#ifndef taslib_external_network_h
#define taslib_external_network_h

#ifdef __cplusplus
/**
 * \defgroup ExternalNet External network communication
 */

/**
 * \brief External network communication callback function input structure
 * \ingroup ExternalNet
 */
typedef struct tagTAS_EXTERNAL_NET_CALLBACK_INPUTS {
/**
 * \brief External network communication callback function http version options
 * \ingroup ExternalNet
 */
typedef enum {
    VER_1_0 = 0, /**<http version 1.0 */
    VER_1_1 = 1 /**<http version 1.1 */
} HTTP_VERSION;

private:
    std::string url; /**< The url for sending requests and events */
    std::string method; /**< The method in upper case, e.g.: "GET","POST","PUT","HEAD" */
    std::string body; /**< The body of the request in case of "PUT" or "POST", or empty string for no body */
    std::map<std::string,std::string> headers; /**< Map of request headers, or empty map for no headers */
    int connectionTimeout; /**< Timeout for the http connection setup */
    int requestTimeout; /**< Timeout for the http request */
    bool useAutoRedirect; /**< True if need to redirect requests automatically, otherwise False */
    HTTP_VERSION httpVersion; /**< The http version, one of the options from the HTTP_VERSION enum */
    bool useClientCertificate; /**< True if need to add client certificate to the request, otherwise False*/
    
public:
    tagTAS_EXTERNAL_NET_CALLBACK_INPUTS(std::string url_, std::string method_, std::string body_, std::map<std::string,std::string> headers_, int connectionTimeout_, int requestTimeout_, bool useAutoRedirect_, HTTP_VERSION httpVersion_, bool useClientCertificate_){
        url = url_;
        method = method_;
        body = body_;
        headers = headers_;
        connectionTimeout = connectionTimeout_;
        requestTimeout = requestTimeout_;
        useAutoRedirect = useAutoRedirect_;
        httpVersion = httpVersion_;
        useClientCertificate = useClientCertificate_;
    }

    tagTAS_EXTERNAL_NET_CALLBACK_INPUTS(std::string url_, std::string method_, std::string body_, std::map<std::string,std::string> headers_, int connectionTimeout_, int requestTimeout_, bool useAutoRedirect_, int httpVersion_, bool useClientCertificate_){
        url = url_;
        method = method_;
        body = body_;
        headers = headers_;
        connectionTimeout = connectionTimeout_;
        requestTimeout = requestTimeout_;
        useAutoRedirect = useAutoRedirect_;
        httpVersion = HTTP_VERSION(httpVersion_);
        useClientCertificate = useClientCertificate_;
    }

    const std::string getUrl() const {return url;}
    const std::string getMethod() const {return method;}
    const std::string getBody() const {return body;}
    const std::map<std::string,std::string> getHeaders() const {return headers;}
    const int getConnectionTimeout() const {return connectionTimeout;}
    const int getRequestTimeout() const {return requestTimeout;}
    const bool getUseAutoRedirect() const {return useAutoRedirect;}
    const HTTP_VERSION getHttpVersion() const {return httpVersion;}
    const int getHttpVersionNum() const {return httpVersion;}
    const bool getUseClientCertificate() const {return useClientCertificate;}

} TAS_EXTERNAL_NET_CALLBACK_INPUTS;

/**
 * \brief External network communication callback function output structure
 * \ingroup ExternalNet
 */

typedef struct tagTAS_EXTERNAL_NET_CALLBACK_OUTPUTS {

/**
 * \brief External network communication callback function internal error code options
 * \ingroup ExternalNet
 */
typedef enum {
    ERR_NONE = 0, /**<no errors */
    TIMEOUT = 1, /**<timeout exception/timeout response */
    GENERAL_ERR = 2 /**<other error/exception */
} INTERNAL_ERROR_CODE;

private:
    std::string body; /**< The readable input stream of the response, or empty for no body */
    std::string errorBody; /**< The readable error stream the server returns if the connection failed, or empty for no errors from the server  */
    std::map<std::string,std::string> headers; /**< Map of response readable headers, or empty map for no headers.
                                                If a key has more than one value, put one value as a concatenated string that contains all the values separated by "," (without "," at the end)  */
    int httpCode; /**< The http code of the response */
    INTERNAL_ERROR_CODE internalErrorCode; /**< Any internal error/exception during the http connection, one of the options from the INTERNAL_ERROR_CODE enum */
    std::string internalErrorStr; /**< Your informative string with the internal error description if occurred, or empty for no errors */
    
public:
    std::string getBody() {return body;}
    std::string getErrorBody() {return errorBody;}
    std::map<std::string,std::string> getHeaders() {return headers;}
    int getHttpCode() {return httpCode;}
    INTERNAL_ERROR_CODE getInternalErrorCode() {return internalErrorCode;}
    int getInternalErrorCodeNum() {return internalErrorCode;}
    std::string getInternalErrorStr() {return internalErrorStr;}
    void setBody(std::string body_) {body = body_;}
    void setErrorBody(std::string errorBody_) {errorBody = errorBody_;}
    void setHeader(std::string key, std::string value) {headers.insert(std::pair<std::string,std::string>(key,value));}
    void setHeaders(std::map<std::string,std::string> headers_) {headers = headers_;}
    void setHttpCode(int httpCode_) {httpCode = httpCode_;}
    void setInternalErrorCode(INTERNAL_ERROR_CODE internalErrorCode_) {internalErrorCode = internalErrorCode_;}
    void setInternalErrorStr(std::string internalErrorStr_) {internalErrorStr = internalErrorStr_;}
    INTERNAL_ERROR_CODE convertIntToInternalErrorCode(int num) {
        switch(num) {
            case 0:
                return ERR_NONE;
            case 1:
                return TIMEOUT;
            case 2:
                return GENERAL_ERR;
            default:
                return GENERAL_ERR;
        }
    }
    
} TAS_EXTERNAL_NET_CALLBACK_OUTPUTS;


/**
 * \brief External network communication callback function
 *
 * This is a callback for sending http requests and receiving http responses.
 * It allows the app to control the communication from and into the sdk.
 *
 * The callback is called when the code sends requests or events.
 *
 * \ingroup ExternalNet
 */
typedef void (*TAS_EXTERNAL_NET_CALLBACK)(
    const TAS_EXTERNAL_NET_CALLBACK_INPUTS& inputs, /**< The input structure with all the inputs */
    TAS_EXTERNAL_NET_CALLBACK_OUTPUTS& outputs /**< Allocated empty output structure to fill in all the outputs --> Don't allocate a new one */
);

#endif

#endif /* taslib_external_network_h */
