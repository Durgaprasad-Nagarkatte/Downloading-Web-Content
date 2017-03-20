package com.company.durgaprasad.downloadingwebpages;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    //AsyncTask enables proper and easy use of the UI thread. This class allows you to perform background operations and publish results on the UI thread without having to manipulate threads and/or handlers.
    public class DownloadTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try{
                //storing the url
                url = new URL(urls[0]);
                //to open the url connection with the Internet
                urlConnection = (HttpURLConnection) url.openConnection();
                //Reading the stream of data sent from the web
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();
                //to read the reader and store it in the result string
                while(data != -1){
                    char current  = (char)data;
                    result += current;
                    data = reader.read();
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
            return result;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String result = null;

        DownloadTask task = new DownloadTask();
        try{
            result = task.execute("https://snulinks.snu.edu.in/").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Log.v("Web Page is : ", result);
    }
}
