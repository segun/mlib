/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.trinisoft.baselib.util;

/**
 *
 * @author trinisoftinc
 */
public class Response {
    private static int responseCode;
    private static String responseText;
    private static Response response = new Response();

    public static Response getResponse() {
        return response;
    }
    
    public static int getResponseCode() {
        return responseCode;
    }

    public static void setResponseCode(int responseCode) {
        Response.responseCode = responseCode;
    }

    public static String getResponseText() {
        return responseText;
    }

    public static void setResponseText(String responseText) {
        Response.responseText = responseText;
    }    
}
