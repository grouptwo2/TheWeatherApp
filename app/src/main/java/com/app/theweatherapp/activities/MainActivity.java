package com.app.theweatherapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.theweatherapp.R;
import com.app.theweatherapp.api.ApiResourceCity;
import com.app.theweatherapp.util.Cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {
    private List<ApiResourceCity> cities = null;
    private RecyclerView recyclerView;
    private CityAdapter adapter;
    private View fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CitySearchActivity.class);
                startActivityForResult(intent, CitySearchActivity.INTENT_GET_CITY);
            }
        });

        cities = Cache.get(this, Cache.Key.SAVED_CITIES, new ArrayList<ApiResourceCity>());
        setUpViews();

    }

    private void setUpViews() {

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        adapter = new CityAdapter(this, cities);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);


        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                updateEmptyView();
            }
        });

        updateEmptyView();

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                final int swipedPosition = viewHolder.getAdapterPosition();
                final ApiResourceCity city = cities.remove(swipedPosition);
                adapter.notifyItemRemoved(swipedPosition);
                Cache.save(MainActivity.this, Cache.Key.SAVED_CITIES, cities);
                Snackbar snackbar = Snackbar.make(fab, "City Removed", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cities.add(swipedPosition, city);
                        adapter.notifyItemInserted(swipedPosition);
                        Cache.save(MainActivity.this, Cache.Key.SAVED_CITIES, cities);
                    }
                });
                snackbar.show();
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void updateEmptyView() {
        View emptyView = findViewById(R.id.emptyView);
        if (cities.isEmpty()) {
            emptyView.setVisibility(View.VISIBLE);
        } else {
            emptyView.setVisibility(View.GONE);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CitySearchActivity.INTENT_GET_CITY && resultCode == RESULT_OK) {
            ApiResourceCity city = (ApiResourceCity) data.getSerializableExtra(CitySearchActivity.RESULT_CITY);
            if (cities.contains(city)) {
                return;
            }
            cities.add(city);
            Collections.sort(cities);
            Cache.save(this, Cache.Key.SAVED_CITIES, cities);
            adapter.notifyDataSetChanged();
        }
    }

    private static class CityAdapter extends RecyclerView.Adapter<CityViewHolder> {
        private List<ApiResourceCity> cities;
        private Context context;


        public CityAdapter(Context context, List<ApiResourceCity> cities) {
            this.context = context;
            this.cities = cities;
        }

        @Override
        public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new CityViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_city, null));
        }


        @Override
        public void onBindViewHolder(CityViewHolder holder, int position) {
            ApiResourceCity city = cities.get(position);
            holder.countryTextView.setText(city.getCountry());
            holder.cityNameTextView.setText(city.getName());
            holder.baseView.setTag(city);
            holder.baseView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ApiResourceCity city1 = (ApiResourceCity) view.getTag();
                    Intent intent = new Intent(context, CityWeatherForecastDetailActivity.class);
                    intent.putExtra(CityWeatherForecastDetailActivity.SELECTED_CITY, city1);
                    context.startActivity(intent);
                }
            });
        }


        @Override
        public int getItemCount() {
            return cities.size();
        }
    }

    private static class CityViewHolder extends RecyclerView.ViewHolder {
        private TextView cityNameTextView;
        private TextView countryTextView;
        private View baseView;

        public CityViewHolder(View itemView) {
            super(itemView);
            cityNameTextView = (TextView) itemView.findViewById(R.id.cityName);
            countryTextView = (TextView) itemView.findViewById(R.id.country);
            baseView = itemView.findViewById(R.id.baseView);
        }
    }
}
