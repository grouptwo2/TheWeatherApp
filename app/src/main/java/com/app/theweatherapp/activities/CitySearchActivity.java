package com.app.theweatherapp.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.theweatherapp.R;
import com.app.theweatherapp.api.ApiResourceCity;
import com.app.theweatherapp.api.ApiResourceLocation;
import com.app.theweatherapp.api.ApiResourceLocationResult;
import com.app.theweatherapp.util.JSONUtil;
import com.noubug.lib.nouhttp.NouHTTP;

public class CitySearchActivity extends AppCompatActivity implements View.OnClickListener, SearchView.OnQueryTextListener, AdapterView.OnItemClickListener {
    public static final int INTENT_GET_CITY = 1;
    public static final String RESULT_CITY = "selectedCity";
    private SearchView searchView;
    private ListView listView;
    private SwipeRefreshLayout refreshLayout;
    private CityAdapter adapter;
    private ApiResourceLocationResult locationResult;
    private Handler handler = new Handler();
    private boolean shouldSearch = false;
    private boolean isSearching = false;

    private Runnable showLoaderRunnable = new Runnable() {
        @Override
        public void run() {
            refreshLayout.setRefreshing(true);
        }
    };

    private Runnable hideLoaderRunnable = new Runnable() {
        @Override
        public void run() {
            refreshLayout.setRefreshing(false);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setUpViews();
        setUpListeners();
    }

    private void setUpViews() {
        listView = (ListView) findViewById(R.id.listView);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        adapter = new CityAdapter(this, R.layout.list_item_searched_city, new String[]{});
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        listView.setEmptyView(findViewById(R.id.emptyView));
        refreshLayout.setEnabled(false);
    }

    private void setUpListeners() {

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_city_search, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchMenuItem = menu.findItem(R.id.search);
        searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnSearchClickListener(this);
        searchView.setQueryHint("Enter City Name");
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        Log.i("TAG", "CLICKED");
        search(searchView.getQuery().toString());
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        Log.i("TAG", "CLICKED");
        search(s);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

    private void search(String string) {
        if (isSearching) {
            shouldSearch = true;
            return;
        }
        if (string.isEmpty()) {
            locationResult = null;
            adapter.notifyDataSetChanged();
            return;
        }
        showLoader();
        NouHTTP.enableLogging();
        NouHTTP.with(this).get("https://twcservice.mybluemix.net/api/weather/v3/location/search")
                .addParam("query", string)
                .addParam("language", "en-US")
                .inTheFuture(new NouHTTP.Future() {
                    @Override
                    public void completed(Exception e, int statusCode, String t) {
                        adapter.notifyDataSetChanged();

                        if (shouldSearch) {
                            shouldSearch = false;
                            search(searchView.getQuery().toString());
                        } else {
                            isSearching = false;
                        }
                        hideLoader();
                        Log.i("TAG", "" + t);
                        if (e == null && statusCode == 200) {
                            locationResult = JSONUtil.fromJSON(t, ApiResourceLocationResult.class);
                            adapter.notifyDataSetChanged();
                        } else if (statusCode == 401) {
                            Toast.makeText(CitySearchActivity.this, "Invalid Credentials", Toast.LENGTH_LONG).show();
                        } else if (statusCode == 404) {
                            Toast.makeText(CitySearchActivity.this, "City Not Found", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(CitySearchActivity.this, "not found", Toast.LENGTH_LONG).show();
                        }

                        if (e != null) {
                            e.printStackTrace();
                        }
                    }
                }).nou();
    }

    @Override
    public void onBackPressed() {
        if (searchView.isIconified())
            super.onBackPressed();
        else
            searchView.setIconified(true);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        ApiResourceCity city = new ApiResourceCity();
        ApiResourceLocation resourceLocation = locationResult.getLocation();

        if (resourceLocation.getCountry() != null && resourceLocation.getCountry().length > i) {
            city.setCountry(resourceLocation.getCountry()[i]);
        } else {
            city.setCountry("N/A");
        }

        if (resourceLocation.getAddress() != null && resourceLocation.getAddress().length > i) {
            city.setName(resourceLocation.getAddress()[i]);
        } else {
            city.setName("N/A");
        }

        if (resourceLocation.getLatitude() != null && resourceLocation.getLatitude().length > i) {
            city.setLatitude(resourceLocation.getLatitude()[i]);
        } else {
            city.setLatitude(0.0D);
        }

        if (resourceLocation.getLongitude() != null && resourceLocation.getLongitude().length > i) {
            city.setLongitude(resourceLocation.getLongitude()[i]);
        } else {
            city.setLongitude(0.0D);
        }

        Intent resultIntent = new Intent();
        resultIntent.putExtra(CitySearchActivity.RESULT_CITY, city);
        setResult(RESULT_OK, resultIntent);
        finish();

    }

    private class CityAdapter extends ArrayAdapter {

        CityAdapter(Context context, int resource, String[] items) {
            super(context, resource, items);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_searched_city, null);
            }

            TextView city = (TextView) convertView.findViewById(R.id.cityName);
            TextView country = (TextView) convertView.findViewById(R.id.country);

            convertView.findViewById(R.id.arrow).setVisibility(View.GONE);

            city.setText(locationResult.getLocation().getAddress()[position]);
            country.setText(locationResult.getLocation().getCountry()[position]);

            return convertView;
        }

        @Override
        public int getCount() {
            if (locationResult == null) {
                return 0;
            }
            if (locationResult.getLocation() == null) {
                return 0;
            }
            if (locationResult.getLocation().getAddress() == null) {
                return 0;
            }
            return locationResult.getLocation().getAddress().length;
        }
    }

    private void showLoader() {
        handler.postDelayed(showLoaderRunnable, 50);
    }

    private void hideLoader() {
        handler.postDelayed(hideLoaderRunnable, 50);
    }
}
