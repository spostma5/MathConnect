package com.example.kyoukasuigetsu.mathconnect;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Pair;
import android.widget.Toast;

import com.example.kyoukasuigetsu.mathconnect.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;

/**
 * Created by Kyoukasuigetsu on 21/02/2015.
 */

class EndpointsConnectToRoomTask extends AsyncTask<Pair<Context, String>, Void, String> {
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
        String friend = "null";
        try {
            friend = params[0].second.split(";=;")[1];
        }
        catch(Exception e) {
            //DO NOTHING
        }

        try {
            return myApiService.userJoinRoom(user,friend).execute().getRoomAll();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        Room room = new Room(result.split(";=;")[0],result.split(";=;")[1],context);

        if(!result.split(";=;")[1].equals("INVALID")) {
            Toast.makeText(context, "Connected", Toast.LENGTH_LONG).show();

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                //DO NOTHING
            }

            Intent intent = new Intent(context,ConnectActivity.class);
            intent.putExtra(ConnectActivity.ROOM,room.getRoomRev());
            context.startActivity(intent);
        }
        else {
            Toast.makeText(context, "Room not found", Toast.LENGTH_LONG).show();
        }
    }
}