package com.example.projectblankversion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class SearchTechnologyActivity extends AppCompatActivity {

    EditText SearchedTechID;
    EditText SearchedTechName;
    TextView ErrorWindow;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_unit);

        SearchedTechID = (EditText) findViewById(R.id.UnitID);
        SearchedTechName =(EditText) findViewById(R.id.UnitName);
        ErrorWindow = (TextView) findViewById(R.id.ErrorWindow);
    }


    public void openSearchResultActivity(String value)
    {
        Intent i = new Intent(getApplicationContext(), SearchTechnologyResultActivity.class);
        i.putExtra("responseData",value);
        startActivity(i);
    }


    public void getResponseByID(View v) {
        String input = SearchedTechID.getText().toString();
        if (SearchedTechID.getText().toString().equals(""))
        {
            ErrorWindow.setText("ID must be a number from 1 to 140");
        }
        else if(tryParseInt(input)<1 || tryParseInt(input)>140)
        {
            ErrorWindow.setText("ID must be a number from 1 to 140");
        }
        else
        {
            ErrorWindow.setText("");
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "https://age-of-empires-2-api.herokuapp.com/api/v1/technology/" + SearchedTechID.getText().toString();

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

        String url = "https://age-of-empires-2-api.herokuapp.com/api/v1/technology/" + SearchedTechName.getText().toString().replace(" ","_");

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

                        ErrorWindow.setText("Name does not match with any technology");
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