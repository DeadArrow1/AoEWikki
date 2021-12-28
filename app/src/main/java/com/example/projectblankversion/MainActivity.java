package com.example.projectblankversion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.print.pdf.PrintedPdfDocument;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    final static String CivFileName = "CivFile.txt";
    final static String UnitFileName = "UnitFile.txt";
    final static String TechFileName = "TechFile.txt";

    private Button BrowseButton;
    private Button SearchButton;
    private Button DownloadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BrowseButton = (Button) findViewById(R.id.MainActivityButtonBrowse);

        BrowseButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openBrowseActivity();
            }
        });

        SearchButton = (Button) findViewById(R.id.MainActivityButtonSearch);
        SearchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openSearchActivity();
            }
        });









///test, jestli se uz data netahaly, pokud ano, už znovu netaháme informace z api
        File file = new File ("/data/data/com.example.projectblankversion/files/CivFile.txt");

        if(isNetworkAvailable()&&!file.exists()&&isFirstTime()) {
            AutomaticDownloadData();
        }
        else if(isFirstTime())
        {
            Toast.makeText(this, "All data are already downloaded", Toast.LENGTH_LONG).show();
        }
    }

    public void DownloadData(View v) {
        if (isNetworkAvailable()) {
            Toast.makeText(this, "Downloading data from API. Please wait a moment...", Toast.LENGTH_LONG).show();

            RequestQueue CivsQueue = Volley.newRequestQueue(this);
            String url = "https://age-of-empires-2-api.herokuapp.com/api/v1/civilizations";

            StringRequest stringCivRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    CivsQueue.stop();
                    saveToFile(CivFileName, response);
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {


                        }
                    });
            CivsQueue.add(stringCivRequest);

            ////retrieve current/updated unit information if possible
            RequestQueue UnitsQueue = Volley.newRequestQueue(this);
            String urlUnits = "https://age-of-empires-2-api.herokuapp.com/api/v1/units";

            StringRequest stringUnitsRequest = new StringRequest(Request.Method.GET, urlUnits, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    UnitsQueue.stop();
                    saveToFile(UnitFileName, response);
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {


                        }
                    });
            UnitsQueue.add(stringUnitsRequest);

            ////retrieve current/updated technologies information if possible
            RequestQueue TechQueue = Volley.newRequestQueue(this);
            String urlTech = "https://age-of-empires-2-api.herokuapp.com/api/v1/technologies";

            StringRequest stringTechRequest = new StringRequest(Request.Method.GET, urlTech, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    TechQueue.stop();
                    saveToFile(TechFileName, response);
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    });
            TechQueue.add(stringTechRequest);
        } else {
            Toast.makeText(this, "No internet connection detected", Toast.LENGTH_LONG).show();
        }
    }

    public void AutomaticDownloadData(){

        RequestQueue CivsQueue = Volley.newRequestQueue(this);
        String url = "https://age-of-empires-2-api.herokuapp.com/api/v1/civilizations";

        StringRequest stringCivRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                CivsQueue.stop();
                saveToFile(CivFileName, response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                    }
                });
        CivsQueue.add(stringCivRequest);

        ////retrieve current/updated unit information if possible
        RequestQueue UnitsQueue = Volley.newRequestQueue(this);
        String urlUnits = "https://age-of-empires-2-api.herokuapp.com/api/v1/units";

        StringRequest stringUnitsRequest = new StringRequest(Request.Method.GET, urlUnits, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                UnitsQueue.stop();
                saveToFile(UnitFileName, response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                    }
                });
        UnitsQueue.add(stringUnitsRequest);

        ////retrieve current/updated technologies information if possible
        RequestQueue TechQueue = Volley.newRequestQueue(this);
        String urlTech = "https://age-of-empires-2-api.herokuapp.com/api/v1/technologies";

        StringRequest stringTechRequest = new StringRequest(Request.Method.GET, urlTech, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                TechQueue.stop();
                saveToFile(TechFileName, response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        TechQueue.add(stringTechRequest);

    }
    public void openBrowseActivity()
    {
        Intent intent = new Intent(this,BrowseActivity.class);
        startActivity(intent);

    }

    public void openSearchActivity()
    {
        Intent intent = new Intent(this,SearchActivity.class);
        startActivity(intent);

    }



    public void saveToFile(String filename,String stringToWrite){
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(filename, MODE_PRIVATE);
            fos.write(stringToWrite.getBytes());
            if(filename.contains("Civ")) {
                Toast.makeText(this, "Civilizations downloaded", Toast.LENGTH_SHORT).show();
            }
            if(filename.contains("Tech")) {
                Toast.makeText(this, "Technologies downloaded", Toast.LENGTH_SHORT).show();
            }
            if(filename.contains("Unit")) {
                Toast.makeText(this, "Units downloaded", Toast.LENGTH_SHORT).show();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fos!=null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            };
        }

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private boolean isFirstTime()
    {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        boolean ranBefore = preferences.getBoolean("RanBefore", false);
        if (!ranBefore) {
            // first time
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("RanBefore", true);
            editor.commit();
        }
        return !ranBefore;
    }

}