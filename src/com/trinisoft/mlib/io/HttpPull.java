/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinisoft.mlib.io;

import com.trinisoft.mlib.util.Echo;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;

/**
 *
 * @author trinisoftinc
 */
public class HttpPull {
    private String userAgent = "Profile/MIDP-2.1 Confirguration/CLDC-1.1";
    private String APIKey = "";

    public HttpPull() {
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getAPIKey() {
        return APIKey;
    }

    public void setAPIKey(String APIKey) {
        this.APIKey = APIKey;
    }

    public String get(String url, String optionalParameters) throws IOException {
        if(optionalParameters != null) {
            url += optionalParameters;
        }
        HttpConnection connection = (HttpConnection) Connector.open(url);

        Echo.outln(url);
        connection.setRequestMethod(HttpConnection.GET);
        connection.setRequestProperty("User-Agent", userAgent);
        connection.setRequestProperty("api-key", APIKey);
        int rc = connection.getResponseCode();
        if (rc == HttpConnection.HTTP_OK) {
            InputStream is = connection.openInputStream();
            BufferedReader reader = new BufferedReader(is);
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            return buffer.toString();
        } else {
            return null;
        }
    }

    public String post(String url, String query, String optionalParameters) throws IOException {
        if(optionalParameters != null) {
            url += optionalParameters;
        }
        Echo.outln(url);        
        HttpConnection connection;
        connection = (HttpConnection) Connector.open(url);

        connection.setRequestMethod(HttpConnection.POST);
        connection.setRequestProperty("User-Agent", "Profile/MIDP-1.0 Confirguration/CLDC-1.0");
        connection.setRequestProperty("Accept_Language", "en-US");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("Content-Length", Integer.toString(query.getBytes().length));
        connection.setRequestProperty("api-key", APIKey);

        OutputStream os = connection.openDataOutputStream();
        os.write(query.getBytes());
        //os.flush();
        BufferedReader reader = new BufferedReader(connection.openDataInputStream());
        String line = "";
        StringBuffer buffer = new StringBuffer();
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
            Echo.outln(line);
        }
        String retval = buffer.toString();
        Echo.outln(retval);
        return retval;
    }
}
