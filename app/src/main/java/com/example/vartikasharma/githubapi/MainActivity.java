package com.example.vartikasharma.githubapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    public String URL = "https://api.github.com/repos/rails/rails/commits";
    private ListAdapter listAdapter;
    private List<CommitItem> commitItemList;

    @BindView(R.id.commit_list)
    ListView commitList;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        commitItemList = new ArrayList<>();
        fetchDataFromUrl();
    }

    private void fetchDataFromUrl() {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(URL)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String myResponse = response.body().string();

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONArray jsonArray = new JSONArray(myResponse);
                            progressBar.setVisibility(View.INVISIBLE);
                            Log.i(LOG_TAG, "jsonArray, " + jsonArray);
                            for (int i = 0 ; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                Log.i(LOG_TAG, "jsonObject, " + jsonObject);
                                String commitId = (String) jsonObject.get("sha");
                                Log.i(LOG_TAG, "commitId, " + commitId);
                                String imageUrl = (String) jsonObject.getJSONObject("author").get("avatar_url");
                                Log.i(LOG_TAG, "imageUrl, " + imageUrl);
                                JSONObject commitJson = jsonObject.getJSONObject("commit");
                                String commitMessage = (String) commitJson.get("message");
                                Log.i(LOG_TAG, "commitMessage, " + commitMessage);
                                String personName = (String) commitJson.getJSONObject("author").get("name");
                                Log.i(LOG_TAG, "personName, " + personName);
                                CommitItem commitItem = new CommitItem(imageUrl, personName, commitId, commitMessage);
                                commitItemList.add(commitItem);
                            }
                                listAdapter = new ListAdapter(MainActivity.this, commitItemList);
                                commitList.setAdapter(listAdapter);
                                //  String imageUrl = (String) jsonObject.getJSONObject("author").get("avatar_url");
                          /*  Log.i(LOG_TAG, "imageURl, " + imageUrl);
                            String commitId = (String) jsonObject.get("sha");
                            Log.i(LOG_TAG, "commitId, " + commitId);
                            JSONObject commitJson = jsonObject.getJSONObject("commit");
                            String commitMessage = (String) commitJson.get("message");
                            Log.i(LOG_TAG, "commitMessage, " + commitMessage);
                            String personName = (String) commitJson.getJSONObject("author").get("name");
                            Log.i(LOG_TAG, "personName, " + personName);*/

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });
    }
}
