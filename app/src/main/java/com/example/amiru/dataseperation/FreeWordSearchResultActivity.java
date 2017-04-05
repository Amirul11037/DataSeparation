package com.example.amiru.dataseperation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amiru.dataseperation.Constant.DetailsConstant;
import com.example.amiru.dataseperation.Constant.PlaceDetailsConstant;
import com.example.amiru.dataseperation.FreeWordAdapter.RecycleViewAdapter_Restaurant_Search_Results;
import com.example.amiru.dataseperation.Parser.DataParserSearchPlace;
import com.example.amiru.dataseperation.RetriveUrl.DownloadUrl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FreeWordSearchResultActivity extends AppCompatActivity implements View.OnClickListener{

    private GridLayoutManager lLayout;
    private EditText FreeWordSearchResults;
    public RecyclerView rView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_word_search_result);

        PlaceDetailsConstant detailsConstant = new PlaceDetailsConstant();
        Intent i = getIntent();
        String search = i.getStringExtra("search");

        String[] strings = new String[1];
        detailsConstant.urlTextSearch = getUrlSearch(search);
        strings[0] = detailsConstant.urlTextSearch;

//        new FetchJSonSearch().execute(strings);
        new DBProc().execute();

        FreeWordSearchResults = (EditText) findViewById(R.id.et_search_restaurant);


        rView = (RecyclerView) findViewById(R.id.restaurant_recycleview);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_search_restaurant:
                Intent intent = new Intent(this, FreeWordSearchResultActivity.class);
                intent.putExtra("search", FreeWordSearchResults.getText().toString());
                startActivity(intent);
                finish();
                break;
        }
    }

    private class DBProc extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... params) {
            Log.d("Process", "Background Process");
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            try {

                Log.d("Process", "Process Start");
                StrictMode.ThreadPolicy policy =
                        new StrictMode.ThreadPolicy.Builder().permitAll().build();

                StrictMode.setThreadPolicy(policy);

                Class.forName(DetailsConstant.MySQLDriver);
                Connection con = DriverManager.getConnection(DetailsConstant.url, DetailsConstant.user, DetailsConstant.password);

                Statement stmt = con.createStatement();

                ResultSet resultSet = stmt.executeQuery("select company_address,company_name FROM company_list");

                ResultSetMetaData rsmd = resultSet.getMetaData();
                List<String> columns = new ArrayList<String>(rsmd.getColumnCount());
                for(int i = 1; i <= rsmd.getColumnCount(); i++){
                    columns.add(rsmd.getColumnName(i));
                }
                List<HashMap<String,String>> data = new ArrayList<>();

                while(resultSet.next()){
                    HashMap<String,String> row = new HashMap<String, String>(columns.size());
                    for(String col : columns) {
                        row.put(col, resultSet.getString(col));
                    }
                    data.add(row);
                    Log.d("Data Complete", data.toString());
                }
                ArrayList<PlaceDetailsConstant> searchList =
                        new ArrayList<PlaceDetailsConstant>();

                PlaceDetailsConstant detailsConstant = new PlaceDetailsConstant();

                int i = 0;
                while (i < data.size()){

                    HashMap<String, String> googlePlace = data.get(i);

//                    Log.d("Data Retrieve", String.valueOf(googlePlace));
//                    Log.d("onPostExcute", "Entered into Showing Location");

                    detailsConstant.setPlace_name(googlePlace.get("company_name"));
                    detailsConstant.setPlace_address(googlePlace.get("company_address"));

//                    Log.d("Count", String.valueOf(i));
                    searchList.add(detailsConstant);
//                    Log.d("Adding", "Adding Complete");
                    Log.d("SearchList Check", String.valueOf(searchList));

                    i++;
                }
//                for (int i = 0; i < data.size(); i++) {
//
//                    HashMap<String, String> googlePlace = data.get(i);
//
//                    Log.d("onPostExcute", "Entered into Showing Location");
//
//                    detailsConstant.setPlace_name(googlePlace.get("company_name"));
//                    detailsConstant.setPlace_address(googlePlace.get("company_address"));
//
//                        searchList.add(detailsConstant);
//                 }
//                    Log.d("Data Size", String.valueOf(data.size()) + " " +data.get(i));
//                    Log.d("Place?" , String.valueOf(googlePlace));
//                    Log.d("Data List", googlePlace.get("company_name"));
                lLayout = new GridLayoutManager(FreeWordSearchResultActivity.this, 3);
                rView.setHasFixedSize(true);
                rView.setLayoutManager(lLayout);

                RecycleViewAdapter_Restaurant_Search_Results rcAdapter =
                        new RecycleViewAdapter_Restaurant_Search_Results
                                (FreeWordSearchResultActivity.this, searchList);

                rView.setAdapter(rcAdapter);

            } catch (Exception e) {
                Toast.makeText(FreeWordSearchResultActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void testDB() {


        try {

            Log.d("Process", "Process Start");
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);

            Class.forName(DetailsConstant.MySQLDriver);
            Connection con = DriverManager.getConnection(DetailsConstant.url, DetailsConstant.user, DetailsConstant.password);

            Statement stmt = con.createStatement();

            ResultSet resultSet = stmt.executeQuery("select company_address,company_name FROM company_list");

//            String results = "ok";
            ResultSetMetaData rsmd = resultSet.getMetaData();
            List<String> columns = new ArrayList<String>(rsmd.getColumnCount());
            for(int i = 1; i <= rsmd.getColumnCount(); i++){
                columns.add(rsmd.getColumnName(i));
            }
            List<HashMap<String,String>> data = new ArrayList<>();
            while(resultSet.next()){
                HashMap<String,String> row = new HashMap<String, String>(columns.size());
                for(String col : columns) {
                    row.put(col, resultSet.getString(col));
                }
                data.add(row);
            }
            ArrayList<PlaceDetailsConstant> searchList =
                    new ArrayList<PlaceDetailsConstant>();

            PlaceDetailsConstant detailsConstant = new PlaceDetailsConstant();

            for (int i = 0; i < data.size(); i++) {

                HashMap<String, String> googlePlace = data.get(i);

                Log.d("onPostExcute", "Entered into Showing Location");

                detailsConstant.place_name = googlePlace.get("company_name");
                detailsConstant.place_address = googlePlace.get("company_address");


                searchList.add(detailsConstant);
                Log.d("SearchList Check", String.valueOf(searchList));
            }

            lLayout = new GridLayoutManager(FreeWordSearchResultActivity.this, 3);
            rView.setHasFixedSize(true);
            rView.setLayoutManager(lLayout);

            RecycleViewAdapter_Restaurant_Search_Results rcAdapter =
                    new RecycleViewAdapter_Restaurant_Search_Results
                            (FreeWordSearchResultActivity.this, searchList);

            rView.setAdapter(rcAdapter);

        } catch (Exception e) {

        }
    }

    public class FetchJSonSearch extends AsyncTask<String, String, String> {

        // private String url = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=
        // Rawang&key=AIzaSyBpRPVS-k9I0QYrkbVymhnh0QKfgZF_YVg";
        private String SearchGooglePlacesData;

        @Override
        protected String doInBackground(String... params) {
            try {
                Log.d("GetSearchPlaces Data", "doInBackground entered");
//                PlaceDetailsConstant detailsConstant = new PlaceDetailsConstant();
                String url = params[0];
                DownloadUrl downloadUrl = new DownloadUrl();
                SearchGooglePlacesData = downloadUrl.readUrl(url);
                Log.d("GooglePlacesReadTask", "doInBackground Exit");
            } catch (Exception e) {
                Log.d("GooglePlacesReadTask", e.toString());
            }
            return SearchGooglePlacesData;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d("GooglePlacesReadTask", "onPostExecute Entered");
            List<HashMap<String, String>> SearchPlacesList = null;
            DataParserSearchPlace dataParserSearchPlace = new DataParserSearchPlace();
            SearchPlacesList = dataParserSearchPlace.parse(result);

            ArrayList<PlaceDetailsConstant> searchList =
                    new ArrayList<PlaceDetailsConstant>();

            try {
                for (int i = 0; i < SearchPlacesList.size(); i++) {

                    HashMap<String, String> googlePlace = SearchPlacesList.get(i);

                    PlaceDetailsConstant detailsConstant = new PlaceDetailsConstant();

                    Log.d("onPostExcute", "Entered into Showing Location");

                    detailsConstant.place_id = googlePlace.get("id");
                    detailsConstant.place_name = googlePlace.get("place_name");
                    detailsConstant.place_address = googlePlace.get("formatted_address");

                    searchList.add(detailsConstant);
                    Log.d("SearchList Check", String.valueOf(searchList));

                    Toast.makeText(FreeWordSearchResultActivity.this, detailsConstant.place_address, Toast.LENGTH_SHORT).show();
                }

                lLayout = new GridLayoutManager(FreeWordSearchResultActivity.this, 3);
                rView.setHasFixedSize(true);
                rView.setLayoutManager(lLayout);

                RecycleViewAdapter_Restaurant_Search_Results rcAdapter =
                        new RecycleViewAdapter_Restaurant_Search_Results
                                (FreeWordSearchResultActivity.this, searchList);

                rView.setAdapter(rcAdapter);
            } catch (Exception ex) {
                throw ex;
            }
            Log.d("GooglePlacesReadTask", "onPostExecute Exit");
        }
    }


    public static interface ClickListener{
        public void onClick(View view, int position);
        public void onLongClick(View view, int position);
    }



    private String getUrlSearch(String search) {

//        String newsearch = search.replaceAll("\\s+", "+");
        StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/textsearch/json?");
        googlePlacesUrl.append("query=" + search);
        googlePlacesUrl.append("&key=" + "AIzaSyBpRPVS-k9I0QYrkbVymhnh0QKfgZF_YVg");
        Log.d("Searching Url", googlePlacesUrl.toString());
        return (googlePlacesUrl.toString());
    }

    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{

        private ClickListener clicklistener;
        private GestureDetector gestureDetector;

        public RecyclerTouchListener(Context context, final RecyclerView recycleView, final ClickListener clicklistener){

            this.clicklistener=clicklistener;
            gestureDetector=new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child=recycleView.findChildViewUnder(e.getX(),e.getY());
                    if(child!=null && clicklistener!=null){
                        clicklistener.onLongClick(child,recycleView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child=rv.findChildViewUnder(e.getX(),e.getY());
            if(child!=null && clicklistener!=null && gestureDetector.onTouchEvent(e)){
                clicklistener.onClick(child,rv.getChildAdapterPosition(child));
            }

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}
//    }}