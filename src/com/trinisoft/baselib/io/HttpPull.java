/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinisoft.baselib.io;

import com.trinisoft.baselib.util.Echo;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;

/**
 *
 * @author trinisoftinc
 */
public class HttpPull {

    //private String userAgent = "Profile/MIDP-2.1 Configuration/CLDC-1.1";
    private String APIKey = "oalkuisnetgauyno";
    public Hashtable requestProperties;

    public HttpPull() {
        requestProperties = new Hashtable();
    }

    public String getAPIKey() {
        return APIKey;
    }

    public void setAPIKey(String APIKey) {
        this.APIKey = APIKey;
    }

    public String get(String url, String optionalParameters) throws IOException {
        if (optionalParameters != null) {
            url += "&api-key=" + APIKey + optionalParameters;
        }

        Echo.outln(url);
        HttpConnection connection = (HttpConnection) Connector.open(url);

        connection.setRequestMethod(HttpConnection.GET);
        //connection.setRequestProperty("User-Agent", userAgent);
        connection.setRequestProperty("api-key", APIKey);
        int rc = connection.getResponseCode();
        if (rc == HttpConnection.HTTP_OK) {
            InputStream is = connection.openInputStream();
            int ch;
            StringBuffer buffer = new StringBuffer();

            while ((ch = is.read()) != -1) {
                buffer.append((char) ch);
            }

            try {
                is.close();
                connection.close();
            } catch (Exception e) {
            }
            return buffer.toString();
        } else {
            try {
                connection.close();
            } catch (Exception e) {
            }
            return null;
        }
    }
    
    public String post(String url, String query, String optionalParameters) throws IOException {
        if (optionalParameters != null) {
            url += optionalParameters;
        }

        query += "&api-key=" + APIKey;
        Echo.outln(url + ", " + query.getBytes().length);
        Echo.outln(query);

        HttpConnection connection;
        connection = (HttpConnection) Connector.open(url);

        connection.setRequestMethod(HttpConnection.POST);
        //connection.setRequestProperty("User-Agent", "Profile/MIDP-2.0 Confirguration/CLDC-1.1");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        //connection.setRequestProperty("Content-length", "" + query.getBytes("UTF-8").length);
        connection.setRequestProperty("Accept-Charset", "iso-8859-1,*,utf-8");
        connection.setRequestProperty("Host", url.substring(7));
        connection.setRequestProperty("Accept-Language", "en");


        DataOutputStream os = connection.openDataOutputStream();
        byte[] request = query.getBytes();

        for (int i = 0; i < request.length; i++) {
            os.writeByte(request[i]);
        }
        //os.write(query.getBytes());
        //os.flush();
        if (connection.getResponseCode() == HttpConnection.HTTP_OK) {            
            InputStream is = connection.openInputStream();
            int ch;
            StringBuffer buffer = new StringBuffer();
            
            while ((ch = is.read()) != -1) {                
                buffer.append((char) ch);                
            }

            try {
                is.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return buffer.toString();
        } else {
            String retval = connection.getResponseMessage() + " : " + connection.getResponseCode();
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return retval;
        }
    }
}
