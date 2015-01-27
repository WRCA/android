package info.jiangchuan.wrca.rest;

/**
 * Created by jiangchuan on 1/25/15.
 */
public class RestConst {
    /******** rest api ***********/
    // account system
    public static final String PATH_verificationCode = "/account/verificationCode";
    public static final String PATH_account_register = "/account/register";
    public static final String PATH_account_login = "/account/login";
    public static final String PATH_account_password = "/account/password";

    // events
    public static final String PATH_EVENTS = "/events";



    /********* domain name ***********/
    public static final String DOMAIN_WillowRidge = "http://api.jiangchuan.info/v1";
    //public static String DOMAIN_WillowRidge = "http://jiangchuan.info/php/index.php";

    public static final String TAG_HandlerManager = "Response Hanndler";

    /* response json content*/
    public static final String RES_JSON_STATUS = "status";
    public static final String RES_JSON_MESSAGE = "message";
    public static final String RES_JSON_TOKEN = "authToken";

    /* status code */
    public static final int INT_STATUS_200 = 200;
    public static final int INT_STATUS_400 = 400;

    /**************** request params **************/

    // account system
    public static final String REQ_PARAM_EMAIL = "email";
    public static final String REQ_PARAM_PASS = "password";
    public static final String REQ_PARAM_VERICODE = "verificationCode";
    public static final String REQ_PARAM_TOKEN = "authToken";

    // events
    public static final String REQ_PARAM_RANGE = "range";
    public static final String REQ_PARAM_OFFSET = "offset";
    public static final String REQ_PARAM_LIMIT = "limit";
    // range
    public static final String RANGE_ALL = "all";
    public static final String RANGE_WEEK = "week";
    public static final String RANGE_MONTH = "month";
}
