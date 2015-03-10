package com.example.kyoukasuigetsu.mathconnect;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Pair;
import android.widget.Toast;

import com.example.kyoukasuigetsu.mathconnect.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

/**
 * Created by Kyoukasuigetsu on 21/02/2015.
 */

class EndpointsFriendTask extends AsyncTask<Pair<Context, String>, Void, String> {
    private static MyApi myApiService = null;
    private Context context;

    @Override
    protected String doInBackground(Pair<Context, String>... params) {
        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://glass-cedar-860.appspot.com/_ah/api/");
          /* MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });*/
            // end options for devappserver

            myApiService = builder.build();
        }

        context = params[0].first;
        String user = params[0].second.split(";=;")[0];
        String friend = params[0].second.split(";=;")[1];
        if(friend.split("=;=")[0].isEmpty()) {
            friend = friend.substring(3);
        }

        try {
            return myApiService.addFriend(user, friend).execute().getUser();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(context, "Friend added", Toast.LENGTH_LONG).show();

        String[] parts = result.split(";=;");


        try {
            parts[4] = decompress(parts[4]);
        } catch (Exception e) {
            //DO NOTHING
        }

        //result = parts[0] + parts[1] + parts[2] + parts[3] + parts[4];

        User user = new User(result,context);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            //DO NOTHING
        }

        Intent intent = new Intent(context,FriendsActivity.class);
        intent.putExtra(LoginActivity.USER,user);
        context.startActivity(intent);
    }

    public static String decompress(String str) throws IOException {
        if (str == null || str.length() == 0) {
            return str;
        }
        System.out.println("Input String length : " + str.length());
        GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(str.getBytes("ISO-8859-1")));
        BufferedReader bf = new BufferedReader(new InputStreamReader(gis, "ISO-8859-1"));
        String outStr = "";
        String line;
        while ((line=bf.readLine())!=null) {
            outStr += line;
        }
        System.out.println("Output String lenght : " + outStr.length());
        return outStr;
    }
}