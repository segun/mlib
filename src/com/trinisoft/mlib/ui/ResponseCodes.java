/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.trinisoft.mlib.ui;

/**
 *
 * @author trinisoftinc
 */
public class ResponseCodes {
    public static final int OK_CODE = 200;
    public static final String OK_TEXT = "OK";

    public static final int REQUEST_ERROR = 400;
    public static final String REQUEST_ERROR_TEXT = "Bad Request";

    public static final int SERVER_ERROR = 500;
    public static final String SERVER_ERROR_TEXT = "Internal Server Error";

    public static int CUSTOM_RESPONSE_CODE = 0;
    public static String CUSTOM_RESPONSE_TEXT = "";
}
