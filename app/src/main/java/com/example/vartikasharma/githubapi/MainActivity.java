package com.example.vartikasharma.githubapi;

import android.app.SearchManager;
import android.content.Context;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

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
    @BindView(R.id.commit_list)
    ListView commitList;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    private ListAdapter listAdapter;
    private List<CommitItem> commitItemList;

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
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String commitId = (String) jsonObject.get("sha");
                                String imageUrl = (String) jsonObject.getJSONObject("author").get("avatar_url");
                                JSONObject commitJson = jsonObject.getJSONObject("commit");
                                String commitMessage = (String) commitJson.get("message");
                                String personName = (String) commitJson.getJSONObject("author").get("name");
                                CommitItem commitItem = new CommitItem(imageUrl, personName, commitId, commitMessage);
                                commitItemList.add(commitItem);
                            }
                            listAdapter = new ListAdapter(MainActivity.this, commitItemList);
                            commitList.setAdapter(listAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search_menu, menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchItem = menu.findItem(R.id.search);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        if (searchView != null) {
            searchView.setOnQueryTextListener((SearchView.OnQueryTextListener) this);
        }
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
       // searchView.setOnQueryTextListener(this);
        return true;
    }
}
