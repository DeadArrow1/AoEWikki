package com.example.projectblankversion;

import static com.example.projectblankversion.MainActivity.CivFileName;
import static com.example.projectblankversion.MainActivity.TechFileName;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class SearchTechnologyActivity extends AppCompatActivity {

    EditText SearchedTechID;
    EditText SearchedTechName;
    TextView ErrorWindow;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_technology);

        SearchedTechID = (EditText) findViewById(R.id.TechID);
        SearchedTechName =(EditText) findViewById(R.id.TechName);
        ErrorWindow = (TextView) findViewById(R.id.ErrorWindow);
    }


    public void openSearchResultActivity(String value)
    {
        Intent i = new Intent(getApplicationContext(), SearchTechnologyResultActivity.class);
        i.putExtra("responseData",value);
        startActivity(i);
    }


    public void getResponseByID(View v) {
        try {
            String FullInfo = load(TechFileName);
            JSONObject InformationJSON = new JSONObject(FullInfo);
            JSONArray BasicInformationJSONArray = InformationJSON.getJSONArray("technologies");
            String[] BasicInformationArray = toStringArray(BasicInformationJSONArray);
        String input = SearchedTechID.getText().toString();
        if (SearchedTechID.getText().toString().equals(""))
        {
            ErrorWindow.setText("ID must be a number from 1 to "+BasicInformationArray.length);
        }
        else if(tryParseInt(input)<1 || tryParseInt(input)>BasicInformationArray.length)
        {
            ErrorWindow.setText("ID must be a number from 1 to "+BasicInformationArray.length);
        }
        else
        {
            int inputInt= tryParseInt(input);

            ErrorWindow.setText("");



                for(int i=0; i<=BasicInformationArray.length;i++)
                {
                    JSONObject Tech =new JSONObject( BasicInformationArray[i]);
                    if(i+1==inputInt){
                        openSearchResultActivity(Tech.toString());
                        break;
                    }
                }
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getResponseByName(View v) {

        String input = SearchedTechName.getText().toString();
        String FullInfo = load(TechFileName);

        ErrorWindow.setText("");

        try {
            JSONObject InformationJSON = new JSONObject(FullInfo);
            JSONArray BasicInformationJSONArray = InformationJSON.getJSONArray("technologies");
            String[] BasicInformationArray = toStringArray(BasicInformationJSONArray);
            for(int i=0; i<BasicInformationArray.length;i++) {
                JSONObject Tech = new JSONObject(BasicInformationArray[i]);
                if ((input.equals(Tech.getString("name")))) {
                    openSearchResultActivity(Tech.toString());
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