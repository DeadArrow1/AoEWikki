package com.example.projectblankversion;

import androidx.appcompat.app.AppCompatActivity;


import static com.example.projectblankversion.MainActivity.CivFileName;
import static com.example.projectblankversion.MainActivity.TechFileName;
import static com.example.projectblankversion.MainActivity.UnitFileName;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;


public class SearchCivilizationActivity extends AppCompatActivity {
    EditText SearchedCivID;
    EditText SearchedCivName;
    TextView ErrorWindow;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_civilization);

        SearchedCivID = (EditText) findViewById(R.id.CivID);
        SearchedCivName=(EditText) findViewById(R.id.CivName);
        ErrorWindow = (TextView) findViewById(R.id.ErrorWindow);
    }

    JSONObject InformationJSON;

    public void getResponseByID(View v) {
        try {
            String FullInfo = load(CivFileName);
            InformationJSON = new JSONObject(FullInfo);
            JSONArray BasicInformationJSONArray = InformationJSON.getJSONArray("civilizations");
            String[] BasicInformationArray = toStringArray(BasicInformationJSONArray);


            String input = SearchedCivID.getText().toString();

            if (SearchedCivID.getText().toString().equals("")) {
                ErrorWindow.setText("ID must be a number from 1 to "+BasicInformationArray.length);
            } else if (tryParseInt(input) < 1 || tryParseInt(input) >BasicInformationArray.length) {
                ErrorWindow.setText("ID must be a number from 1 to "+BasicInformationArray.length);
            } else {
                int inputInt = tryParseInt(input);
                ErrorWindow.setText("");


                for (int i = 0; i <= BasicInformationArray.length; i++) {

                    if (i + 1 == inputInt) {
                        JSONObject Civ = new JSONObject(BasicInformationArray[i]);
                        openSearchResultActivity(Civ.toString());
                        return;
                    }
                }

            }}catch(JSONException e){
                e.printStackTrace();
            }
        }



    public void getResponseByName(View v) {

        String input = SearchedCivName.getText().toString();
        String FullInfo = load(CivFileName);

        ErrorWindow.setText("");

        try {
            JSONObject InformationJSON = new JSONObject(FullInfo);
            JSONArray BasicInformationJSONArray = InformationJSON.getJSONArray("civilizations");
            String[] BasicInformationArray = toStringArray(BasicInformationJSONArray);
            for(int i=0; i<BasicInformationArray.length;i++) {
                JSONObject Civ = new JSONObject(BasicInformationArray[i]);
                if ((input.equals(Civ.getString("name")))) {
                    openSearchResultActivity(Civ.toString());
                    break;

                }
                else if(i==BasicInformationArray.length-1)
                {
                    ErrorWindow.setText("Name not found.");
                    return;
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }



        }
    //}

    public int tryParseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public void openSearchResultActivity(String value)
    {
        Intent i = new Intent(getApplicationContext(), SearchCivResultActivity.class);
        i.putExtra("responseData",value);
        startActivity(i);
    }

    public static String[] toStringArray(JSONArray array) {
        if (array == null)
            return null;
        String[] arr = new String[array.length()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = array.optString(i);
        }
        return arr;
    }

    public String load(String filename){
        FileInputStream fis = null;
        String text="";
        try {

            fis = openFileInput(filename);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line="";
            while ((line = br.readLine())!=null)
            {
                sb.append(line);
            }
            text=sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fis != null)
            {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return text;
        }
    }
    public void openSearchActivity(View v)
    {
        Intent intent = new Intent(this,SearchActivity.class);
        startActivity(intent);

    }



}