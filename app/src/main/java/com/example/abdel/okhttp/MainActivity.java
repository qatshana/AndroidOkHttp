package com.example.abdel.okhttp;

import android.net.Uri;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.R.attr.tag;

public class MainActivity extends AppCompatActivity {

    //String myurl="http://www.judeventures.com/data2.php";
    String myurl = "https://learnwebcode.github.io/json-example/animals-1.json";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        new AsyncTask<Void, Void, String>() {


            @Override
            protected String doInBackground(Void... params) {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        //.url("https://api.github.com/users/ashokslsk")
                        //.url("http://www.judeventures.com/data2.php")
                        .url(myurl)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    //Log.d("AbdelAbdel","doInBackgroun");

                    //Log.d("AbdelAbdel","doInBackground() called with "+"parm = ["+response.body().string()+"]");
                    String auth2 = response.body().string();

                    //JSONObject json = new JSONObject(auth2);
                    //JSONArray jsonArray = json.optJSONArray(auth2);
                    //
                    //
                    //  final String result = new json.getString("name");

                    Log.d("AbdelAbdel", "doInBackground() called with" + auth2);

                    return auth2;


                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String auth2) {
                super.onPostExecute(auth2);
                //...
                Toast.makeText(getApplicationContext(),
                        "String retrived:" + auth2, Toast.LENGTH_LONG).show();
                TextView tv1 = (TextView) findViewById(R.id.tv1);
                TextView tv2 = (TextView) findViewById(R.id.tv2);
                tv2.setText("This is my");
                //tv1.setText(auth2);

                String txt="item 0 is";

                try {
                    JSONArray jsonarray = new JSONArray(auth2);
                    int len=jsonarray.length();
                    String [] txtArray=new String [len];

                    for (int i=0;i<jsonarray.length();i++){
                        JSONObject obj=jsonarray.getJSONObject(i);
                        String name=obj.getString("name");
                        txtArray[i]=name;
                        txt+=name;
                        txt+="" + "  item "+i+"is ";
                        tv2.setText(txt);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }


        }.execute();


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
