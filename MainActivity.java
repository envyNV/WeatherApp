package com.example.weatherapp;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.support.v7.widget.SearchView;
import android.widget.ProgressBar;
import android.widget.SimpleCursorAdapter;
import android.widget.TableLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Set;
import static android.R.layout.simple_list_item_1;

import me.relex.circleindicator.CircleIndicator;

public class MainActivity extends AppCompatActivity {
    public int num_local;
    ViewPager viewPager;
    android.support.v7.widget.SearchView.SearchAutoComplete searchAutoComplete;
    private RequestQueue myQueue;
    Set<String> new_set;
    ProgressBar progress_bar;
    CircleIndicator my_indicator;
    ArrayAdapter<String> arrayAdapter;
    private SimpleCursorAdapter mAdapter;
    PlansPagerAdapter adapter;
    SearchView searchView;
    ArrayList<String> auto_places = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);


        myQueue = Volley.newRequestQueue(this);




        num_local=1;
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        new_set = sharedPreferences.getStringSet("new_location", null);
        if(new_set!=null){
            num_local= num_local+new_set.size();

        }

        progress_bar = (ProgressBar) findViewById(R.id.ProgressBar01);
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progress_bar.setVisibility(View.GONE);
                viewPager = (ViewPager) findViewById(R.id.frameLayout);





                adapter = new PlansPagerAdapter
                        (getSupportFragmentManager(), num_local, new_set);

                viewPager.setAdapter(adapter);
                my_indicator = (CircleIndicator) findViewById(R.id.my_circle);
                my_indicator.setViewPager(viewPager);


            }
        }, 500);





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchbar, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        // Assumes current activity is the searchable activity
        //searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        // Do not iconify the widget; expand it by default

        ComponentName search_activity = new ComponentName(getBaseContext(), Main2Activity.class);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(search_activity));
        searchAutoComplete = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchAutoComplete.setBackgroundColor(Color.BLACK);
        searchAutoComplete.setTextColor(Color.WHITE);
        searchAutoComplete.setDropDownBackgroundResource(android.R.color.white);
        searchAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long id) {
                String queryString=(String)adapterView.getItemAtPosition(itemIndex);
                searchAutoComplete.setText("" + queryString);

            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String currentLoc = searchView.getQuery().toString();
                ////Log.d("currentLoc", currentLoc);
                String places_url = "http://10.25.233.110:5000/places?value="+currentLoc;
                ////Log.d("current-loc", currentLoc);
                ////Log.d("places-url", places_url);
                JsonArrayRequest auto_places_request = new JsonArrayRequest(Request.Method.GET, places_url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        ////Log.d("response", response.toString());
                        auto_places = new ArrayList<>();
                        for(int i = 0; i<5;i++){
                            try {
                                String desc = response.getJSONObject(i).getString("description");
                                auto_places.add(desc);


                                //.setAdapter(new ArrayAdapter<String>(this, simple_list_item_1, auto_places) );
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        ////Log.d("auto-places", auto_places.toString());
                        arrayAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_dropdown_item_1line, auto_places);
                        searchAutoComplete.setAdapter(arrayAdapter);
                        arrayAdapter.notifyDataSetChanged();









                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }

                );

                myQueue.add(auto_places_request);


                return false;
            }
        });

//
        return true;
    }

}



//searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                String currentLoc = searchView.getQuery().toString();
//                ////Log.d("currentLoc", currentLoc);
//                String places_url = "http://10.25.230.107:5000/places?value="+currentLoc;
//                ////Log.d("places-url", places_url);
//                JsonArrayRequest auto_places_request = new JsonArrayRequest(Request.Method.GET, places_url, null, new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        ////Log.d("response", response.toString());
//                        auto_places = new ArrayList<>();
//                        for(int i = 0; i<5;i++){
//                            try {
//                                String desc = response.getJSONObject(i).getString("description");
//                                auto_places.add(desc);
//
//                                //.setAdapter(new ArrayAdapter<String>(this, simple_list_item_1, auto_places) );
//                                ArrayAdapter<String> newsAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,auto_places);
//                                searchView.setAda
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//
//
//
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//                }
//
//                );
//
//                myQueue.add(auto_places_request);
//
//
//                return true;
//            }
//        });


//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                String currentLoc = searchView.getQuery().toString();
//                ////Log.d("currentLoc", currentLoc);
//                String places_url = "http://10.25.230.107:5000/places?value="+currentLoc;
//                ////Log.d("places-url", places_url);
//                JsonArrayRequest auto_places_request = new JsonArrayRequest(Request.Method.GET, places_url, null, new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        ////Log.d("response", response.toString());
//                        auto_places = new ArrayList<>();
//                        for(int i = 0; i<5;i++){
//                            try {
//                                String desc = response.getJSONObject(i).getString("description");
//                                auto_places.add(desc);
//
//                                //.setAdapter(new ArrayAdapter<String>(this, simple_list_item_1, auto_places) );
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//
//
//
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//                }
//
//                );
//
//                myQueue.add(auto_places_request);
//
//
//                return true;
//            }
//        });


//        String currentLocText = editText.getText().toString();
//        ////Log.d("currentLocText", currentLocText);
//        String places_url = "http://10.25.91.147:5000/places?value="+currentLocText;
//        ////Log.d("places_url", places_url);
//        JsonArrayRequest auto_places_request = new JsonArrayRequest(Request.Method.GET, places_url, null, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                ////Log.d("response1", response.toString());
//                auto_places = new ArrayList<>();
//                try {
//                    for(int i = 0;i<5;i++) {
//                        String desc = response.getJSONObject(i).getString("description");
//                        auto_places.add(desc);
//                    }
//                    editText.setAdapter(new ArrayAdapter<String>(c, simple_list_item_1, auto_places) );
//                    editText.showDropDown();
//                    editText.setOnKeyListener(new View.OnKeyListener()
//                    {
//                        public boolean onKey(View v, int keyCode, KeyEvent event)
//                        {
//                            if (event.getAction() == KeyEvent.ACTION_DOWN)
//                            {
//                                switch (keyCode)
//                                {
//                                    case KeyEvent.KEYCODE_DPAD_CENTER:
//                                    case KeyEvent.KEYCODE_ENTER:
//                                        ////Log.d("here", editText.getText().toString());
//                                        open_mytab1(editText.getText().toString());
//                                        return true;
//                                    default:
//                                        break;
//                                }
//                            }
//                            return false;
//                        }
//                    });

