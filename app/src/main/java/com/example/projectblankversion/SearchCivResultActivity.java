package com.example.projectblankversion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class SearchCivResultActivity extends AppCompatActivity {
    String name;
    String expansion;
    String armyType;

    String uniqueTech;
    String teamBonus;
    String civilizationBonus;
    int i;

    String unitsConnectingString="";
    String techConnectingString="";
    String civBonusesConnectingString="";

    TextView TextViewCivName;
    TextView TextViewCivExpansion;
    TextView TextViewCivArmyType;
    TextView TextViewCivUniqueUnit;
    TextView TextViewCivUniqueTech;
    TextView TextViewCivTeamBonus;
    TextView TextViewCivCivilizationBonus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_civ_result);
        String value = getIntent().getStringExtra("responseData");

        try {

            JSONObject civInformationJSON = new JSONObject(value);

            //rozkrajeni JSON objektu na nam vyhovujici stringi pomoci struktury
            name = civInformationJSON.getString("name");
            expansion = civInformationJSON.getString("expansion");
            armyType = civInformationJSON.getString("army_type");




            JSONArray soldiersJSONArray = civInformationJSON.getJSONArray("unique_unit");
            JSONArray technologiesJSONArray = civInformationJSON.getJSONArray("unique_tech");
            JSONArray civilizationBonusJSONArray = civInformationJSON.getJSONArray("civilization_bonus");

            String[] soldiersArray = toStringArray(soldiersJSONArray);

            for (i = 0; i < soldiersArray.length; i++) {

                String uniqueUnitURL = soldiersArray[i];

                RequestQueue unitQueue = Volley.newRequestQueue(this);
                String url = uniqueUnitURL;

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        unitQueue.stop();
                        try {
                            JSONObject unit = new JSONObject(response);
                            if(unitsConnectingString.equals("")) {
                                unitsConnectingString = unitsConnectingString.concat(" " + unit.getString("name"));
                            }
                            else
                                {
                                    unitsConnectingString = unitsConnectingString.concat(", " + unit.getString("name"));
                                }
                            TextViewCivUniqueUnit = (TextView) findViewById(R.id.TextViewCivUniqueUnit);
                            TextViewCivUniqueUnit.setText("Special units: " + unitsConnectingString);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


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
                unitQueue.add(stringRequest);
            }

            String[] technologiesArray = toStringArray(technologiesJSONArray);
            for (i = 0; i < technologiesArray.length; i++) {

                String uniqueTechURL = technologiesArray[i];

                RequestQueue techQueue = Volley.newRequestQueue(this);
                String url = uniqueTechURL;

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        techQueue.stop();
                        try {
                            JSONObject tech = new JSONObject(response);
                            if(techConnectingString.equals("")) {
                                techConnectingString = techConnectingString.concat(" " + tech.getString("name"));
                            }
                            else
                            {
                                techConnectingString = techConnectingString.concat(", " + tech.getString("name"));
                            }
                            TextViewCivUniqueTech = (TextView) findViewById(R.id.TextViewCivUniqueTech);
                            TextViewCivUniqueTech.setText(TextViewCivUniqueTech.getText() + techConnectingString);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


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
                techQueue.add(stringRequest);
            }

            String[] civilizationBonusArray = toStringArray(civilizationBonusJSONArray);
            for (i = 0; i < civilizationBonusArray.length; i++) {
                if(civBonusesConnectingString.equals(""))
                {
                    civBonusesConnectingString = civBonusesConnectingString.concat(" " + civilizationBonusArray[i]);
                }
                else
                    {
                    civBonusesConnectingString = civBonusesConnectingString.concat("\n" + civilizationBonusArray[i]);
                    }

            }
            TextViewCivCivilizationBonus = (TextView) findViewById(R.id.TextViewCivCivilizationBonus);
            TextViewCivCivilizationBonus.setText(TextViewCivCivilizationBonus.getText() + civBonusesConnectingString);

            uniqueTech = civInformationJSON.getString("unique_tech");
            teamBonus = civInformationJSON.getString("team_bonus");
            civilizationBonus = civInformationJSON.getString("civilization_bonus");

            TextViewCivName = (TextView) findViewById(R.id.TextViewCivName);
            TextViewCivName.setText(name);

            TextViewCivExpansion = (TextView) findViewById(R.id.TextViewCivExpansion);
            TextViewCivExpansion.setText(TextViewCivExpansion.getText()+" " + expansion);

            TextViewCivArmyType = (TextView) findViewById(R.id.TextViewCivArmyType);
            TextViewCivArmyType.setText(TextViewCivArmyType.getText()+" " + armyType);

            TextViewCivTeamBonus = (TextView) findViewById(R.id.TextViewCivTeamBonus);
            TextViewCivTeamBonus.setText( TextViewCivTeamBonus.getText()+" " + teamBonus);

        } catch (JSONException e) {
            e.printStackTrace();
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

    public void openMainActivity(View v)
    {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);

    }
    public void openSearchCivActivity(View v)
    {
        Intent intent = new Intent(this,SearchCivilizationActivity.class);
        startActivity(intent);

    }
}
