package com.trevorhalvorson.activitytransitionrecyclerviewexample;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {

    private List<Production> mProductions;
    private FlixService mService;
    private Adapter mAdapter;
    private ProgressDialog mProgressDialog;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAdapter = new Adapter(mProductions, this);

        mRecyclerView = (RecyclerView) findViewById(R.id.production_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(
                new RecyclerViewItemClickListener(this, new RecyclerViewItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent detailIntent = new Intent(MainActivity.this, DetailActivity.class);
                        detailIntent.putExtra(DetailActivity.EXTRA_PARAM, mProductions.get(position));

                        Pair imagePair = new Pair<>(view.findViewById(R.id.list_item_poster_image_view), DetailActivity.IMAGE_TRANSITION_NAME);

                        ActivityOptionsCompat transitionActivityOptions =
                                ActivityOptionsCompat.makeSceneTransitionAnimation(
                                        MainActivity.this, imagePair);

                        ActivityCompat.startActivity(MainActivity.this,
                                detailIntent, transitionActivityOptions.toBundle());
                    }
                }));

        // Retrofit setup
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://netflixroulette.net")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mService = retrofit.create(FlixService.class);
    }

    private void startSearch(String query) {
        mProgressDialog = ProgressDialog.show(this, "Loading Productions",
                "Please wait...", true);

        Call<List<Production>> productions = mService.listProductions(query);
        productions.enqueue(new Callback<List<Production>>() {
            @Override
            public void onResponse(Response<List<Production>> response, Retrofit retrofit) {
                mProductions = response.body();

                mAdapter = new Adapter(mProductions, getApplicationContext());
                mRecyclerView.setAdapter(mAdapter);

                mProgressDialog.dismiss();

                // Alert the user if no results are returned from the service
                if (mProductions == null) {
                    Snackbar.make(mRecyclerView, "No results found.", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem searchItem = menu.findItem(R.id.menu_item_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint(getString(R.string.search_view_hint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.length() > 0) {
                    startSearch(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_github:
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://github.com/trevorhalvorson/activitytransitionrecyclerviewexample"));
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
