package com.example.projectblankversion;



import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import static com.example.projectblankversion.MainActivity.CivFileName;
import static com.example.projectblankversion.MainActivity.TechFileName;
import static com.example.projectblankversion.MainActivity.UnitFileName;


public class BrowseResultActivity extends AppCompatActivity {
    TextView BrowseNamesTextView;
    TextView BrowseIDsTextView;
    int i;
    String Names="";
    String IDs="";
    int startingID=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_result);

        String CorrectFile = getIntent().getStringExtra("fileName");
        BrowseNamesTextView = (TextView) findViewById(R.id.BrowseNamesTextView);
        BrowseIDsTextView = (TextView) findViewById(R.id.BrowseIDsTextView);


        String FullInfo=load(CorrectFile);
            if(CorrectFile.equals(CivFileName))
            try {
                JSONObject InformationJSON = new JSONObject(FullInfo);
                JSONArray BasicInformationJSONArray = InformationJSON.getJSONArray("civilizations");
                String[] BasicInformationArray = toStringArray(BasicInformationJSONArray);

                for(i=startingID;i<startingID+20&&i<BasicInformationArray.length;i++)
                {
                    JSONObject Name =new JSONObject( BasicInformationArray[i]);
                    Names=Names.concat("\n"+Name.getString("name"));
                    IDs=IDs.concat("\n"+"ID: "+(i+1));

                }
                BrowseNamesTextView.setText(Names);
                BrowseIDsTextView.setText(IDs);


            } catch (JSONException e) {
                e.printStackTrace();
            }

        else if(CorrectFile.equals(TechFileName))
            try {
                JSONObject InformationJSON = new JSONObject(FullInfo);
                JSONArray BasicInformationJSONArray = InformationJSON.getJSONArray("technologies");
                String[] BasicInformationArray = toStringArray(BasicInformationJSONArray);

                for(i=startingID;i<startingID+20&&i<BasicInformationArray.length;i++)
                {
                    JSONObject Name =new JSONObject( BasicInformationArray[i]);
                    Names=Names.concat("\n"+Name.getString("name"));
                    IDs=IDs.concat("\n"+"ID: "+(i+1));

                }
                BrowseNamesTextView.setText(Names);
                BrowseIDsTextView.setText(IDs);


            } catch (JSONException e) {
                e.printStackTrace();
            }

        else if(CorrectFile.equals(UnitFileName))
            try {
                JSONObject InformationJSON = new JSONObject(FullInfo);
                JSONArray BasicInformationJSONArray = InformationJSON.getJSONArray("units");
                String[] BasicInformationArray = toStringArray(BasicInformationJSONArray);

                for(i=startingID;i<startingID+20&&i<BasicInformationArray.length;i++)
                {
                    JSONObject Name =new JSONObject( BasicInformationArray[i]);
                    Names=Names.concat("\n"+Name.getString("name"));
                    IDs=IDs.concat("\n"+"ID: "+(i+1));

                }
                BrowseNamesTextView.setText(Names);
                BrowseIDsTextView.setText(IDs);


            } catch (JSONException e) {
                e.printStackTrace();
            }
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
    public static String[] toStringArray(JSONArray array) {
        if (array == null)
            return null;
        String[] arr = new String[array.length()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = array.optString(i);
        }
        return arr;
    }

    public void NextPage(View v)
    {
        String CorrectFile = getIntent().getStringExtra("fileName");
        BrowseNamesTextView = (TextView) findViewById(R.id.BrowseNamesTextView);
        BrowseIDsTextView = (TextView) findViewById(R.id.BrowseIDsTextView);


        String FullInfo=load(CorrectFile);
        if(CorrectFile.equals(CivFileName)) {
            changeTextView("civilizations", true);
        }
        else if(CorrectFile.equals(UnitFileName))
        {
            changeTextView("units", true);
        }
        else if(CorrectFile.equals(TechFileName))
        {
            changeTextView("technologies", true);
        }
        /*String CorrectFile = getIntent().getStringExtra("fileName");
        BrowseNamesTextView = (TextView) findViewById(R.id.BrowseNamesTextView);
        BrowseIDsTextView = (TextView) findViewById(R.id.BrowseIDsTextView);


        String FullInfo=load(CorrectFile);
        if(FullInfo.contains("civilizations"))
            try {
                startingID+=20;
                Names="";
                IDs="";
                JSONObject InformationJSON = new JSONObject(FullInfo);
                JSONArray BasicInformationJSONArray = InformationJSON.getJSONArray("civilizations");
                String[] BasicInformationArray = toStringArray(BasicInformationJSONArray);
                if(startingID>BasicInformationArray.length)
                {
                    startingID=0;
                }

                for(i=startingID;i<startingID+20&&i<BasicInformationArray.length;i++)
                {
                    JSONObject Name =new JSONObject( BasicInformationArray[i]);
                    Names=Names.concat("\n"+Name.getString("name"));
                    IDs=IDs.concat("\n"+"ID: "+(i+1));

                }
                BrowseNamesTextView.setText(Names);
                BrowseIDsTextView.setText(IDs);


            } catch (JSONException e) {
                e.printStackTrace();
            }*/
    }

    public void PreviousPage(View v)
    {
        String CorrectFile = getIntent().getStringExtra("fileName");
        BrowseNamesTextView = (TextView) findViewById(R.id.BrowseNamesTextView);
        BrowseIDsTextView = (TextView) findViewById(R.id.BrowseIDsTextView);


        String FullInfo=load(CorrectFile);
        if(CorrectFile.equals(CivFileName)) {
            changeTextView("civilizations", false);
        }
        else if(CorrectFile.equals(UnitFileName))
        {
            changeTextView("units", false);
        }
        else if(CorrectFile.equals(TechFileName))
        {
            changeTextView("technologies", false);
        }


        /*String CorrectFile = getIntent().getStringExtra("fileName");
        BrowseNamesTextView = (TextView) findViewById(R.id.BrowseNamesTextView);
        BrowseIDsTextView = (TextView) findViewById(R.id.BrowseIDsTextView);


        String FullInfo=load(CorrectFile);
        if(FullInfo.contains("civilizations"))
            try {
                startingID-=20;
                Names="";
                IDs="";
                JSONObject InformationJSON = new JSONObject(FullInfo);
                JSONArray BasicInformationJSONArray = InformationJSON.getJSONArray("civilizations");
                String[] BasicInformationArray = toStringArray(BasicInformationJSONArray);
                if(startingID<0)
                {
                    while (startingID<BasicInformationArray.length) startingID=startingID+20;
                    startingID=startingID-20;
                }

                for(i=startingID;i<startingID+20&&i<BasicInformationArray.length;i++)
                {
                    JSONObject Name =new JSONObject( BasicInformationArray[i]);
                    Names=Names.concat("\n"+Name.getString("name"));
                    IDs=IDs.concat("\n"+"ID: "+(i+1));

                }
                BrowseNamesTextView.setText(Names);
                BrowseIDsTextView.setText(IDs);


            } catch (JSONException e) {
                e.printStackTrace();
            }*/
    }

    public void changeTextView(String ContainsString,boolean nextPage)
    {
        String CorrectFile = getIntent().getStringExtra("fileName");
        BrowseNamesTextView = (TextView) findViewById(R.id.BrowseNamesTextView);
        BrowseIDsTextView = (TextView) findViewById(R.id.BrowseIDsTextView);


        String FullInfo=load(CorrectFile);
        if(FullInfo.contains(ContainsString))
            try {
                if(nextPage==true){
                startingID+=20;
                }
                else{
                    startingID-=20;
                }
                Names="";
                IDs="";
                JSONObject InformationJSON = new JSONObject(FullInfo);
                JSONArray BasicInformationJSONArray = InformationJSON.getJSONArray(ContainsString);
                String[] BasicInformationArray = toStringArray(BasicInformationJSONArray);
                if(startingID>=BasicInformationArray.length)
                {
                    startingID=0;
                }
                if(startingID<0)
                {
                    while (startingID<BasicInformationArray.length) startingID=startingID+20;
                    startingID=startingID-20;
                }

                for(i=startingID;i<startingID+20&&i<BasicInformationArray.length;i++)
                {
                    JSONObject Name =new JSONObject( BasicInformationArray[i]);
                    Names=Names.concat("\n"+Name.getString("name"));
                    IDs=IDs.concat("\n"+"ID: "+(i+1));

                }
                BrowseNamesTextView.setText(Names);
                BrowseIDsTextView.setText(IDs);


            } catch (JSONException e) {
                e.printStackTrace();
            }
    }

}