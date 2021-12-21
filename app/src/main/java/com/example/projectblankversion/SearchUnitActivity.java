package com.example.projectblankversion;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.content.Intent;
import android.os.Bundle;

import android.widget.EditText;
import android.widget.TextView;


public class SearchUnitActivity extends AppCompatActivity {
    EditText SearchedUnitID;
    EditText SearchedUnitName;
    TextView ErrorWindow;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_unit);

        SearchedUnitID = (EditText) findViewById(R.id.UnitID);
        SearchedUnitName =(EditText) findViewById(R.id.UnitName);
        ErrorWindow = (TextView) findViewById(R.id.ErrorWindow);
    }


    public void openSearchResultActivity(String value)
    {
        Intent i = new Intent(getApplicationContext(), SearchUnitResultActivity.class);
        i.putExtra("responseData",value);
        startActivity(i);
    }


    public void getResponseByID(View v) {
        String input = SearchedUnitID.getText().toString();
        if (SearchedUnitID.getText().toString().equals(""))
        {
            ErrorWindow.setText("ID must be a number from 1 to 32");
        }
        else if(tryParseInt(input)<1 || tryParseInt(input)>104)
        {
            ErrorWindow.setText("ID must be a number from 1 to 32");
        }
        else
        {
            ErrorWindow.setText("");
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "https://age-of-empires-2-api.herokuapp.com/api/v1/unit/" + SearchedUnitID.getText().toString();

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    queue.stop();
                    openSearchResultActivity(response);
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            String errorvar = error.toString();
                            if (errorvar.equals("com.android.volley.ClientError")) {

                            } else {

                            }
                        }
                    });
            queue.add(stringRequest);

        }
    }

    public void getResponseByName(View v) {

        RequestQueue queue = Volley.newRequestQueue(this);

        String url = "https://age-of-empires-2-api.herokuapp.com/api/v1/unit/" + SearchedUnitName.getText().toString().replace(" ","_");

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                queue.stop();
                openSearchResultActivity(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        ErrorWindow.setText("Name does not match with any unit");
                    }
                });
        queue.add(stringRequest);

    }
    //}

    public int tryParseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

}