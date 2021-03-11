package com.example.project_trading;

import android.os.AsyncTask;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

class Pars1 {

    public static String getResponseFromURL(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();
        try {

            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int status = connection.getResponseCode();
            System.out.println("Status  ->  "+status);

            if (status > 299) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            }else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            }
            while ((line = reader.readLine()) != null){
                responseContent.append(line);
            }
            reader.close();
            return  responseContent.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            connection.disconnect();
        }
        return null;
    }

    public static URL generateURL(String key) {
        URL url = null;
        try {
            url = new URL("https://finnhub.io/api/v1/search?q=apple&token="+key);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

}


