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
    String uniqueUnits;
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
            name = civInformationJSON.getString("name");
            expansion = civInformationJSON.getString("expansion");
            armyType = civInformationJSON.getString("army_type");

            //rozkrajeni JSON objektu na nam vyhovujici stringi pomoci struktury
            JSONArray soldiersJSONArray = civInformationJSON.getJSONArray("unique_unit");
            JSONArray technologiesJSONArray = civInformationJSON.getJSONArray("unique_tech");
            JSONArray civilizationBonusJSONArray = civInformationJSON.getJSONArray("civilization_bonus");

            String[] soldiersArray = toStringArray(soldiersJSONArray);
            if(soldiersArray.length!=0) {
                for (i = 0; i < soldiersArray.length; i++) {


                    uniqueUnits = soldiersArray[i];//potreba dalsi pozadavek na API

                    String[] SplitUrl = uniqueUnits.split("/");
                    uniqueUnits = SplitUrl[SplitUrl.length - 1];
                    uniqueUnits = uniqueUnits.replace("_", " ");
                    uniqueUnits = uniqueUnits.substring(0, 1).toUpperCase() + uniqueUnits.substring(1);
                    if (unitsConnectingString.isEmpty()) {
                        unitsConnectingString = "Special units: " + uniqueUnits;
                    } else {
                        unitsConnectingString = unitsConnectingString.concat(", " + uniqueUnits);
                    }

                }
            }

            TextViewCivUniqueUnit = (TextView) findViewById(R.id.TextViewCivUniqueUnit);
            TextViewCivUniqueUnit.setText(unitsConnectingString);


            String[] technologiesArray = toStringArray(technologiesJSONArray);
            if(technologiesArray.length!=0){
            for (i = 0; i < technologiesArray.length; i++){
                uniqueTech = technologiesArray[i];//potreba dalsi pozadavek na API

            String[] SplitUrl = uniqueTech.split("/");
            uniqueTech = SplitUrl[SplitUrl.length - 1];
            uniqueTech = uniqueTech.replace("_", " ");
            uniqueTech = uniqueTech.substring(0, 1).toUpperCase() + uniqueTech.substring(1);
            if(techConnectingString.isEmpty())
            {
                techConnectingString="Special tech: "+uniqueTech;
            }
            else{
                techConnectingString=techConnectingString.concat(", "+uniqueTech);
            }
            }
            }

            TextViewCivUniqueTech = (TextView) findViewById(R.id.TextViewCivUniqueTech);
            TextViewCivUniqueTech.setText(techConnectingString);

            String[] civilizationBonusArray = toStringArray(civilizationBonusJSONArray);
            if(civilizationBonusArray.length!=0) {
                for (i = 0; i < civilizationBonusArray.length; i++) {
                    if (civBonusesConnectingString.equals("")) {
                        civBonusesConnectingString = civBonusesConnectingString.concat(" " + civilizationBonusArray[i]);
                    } else {
                        civBonusesConnectingString = civBonusesConnectingString.concat("\n" + civilizationBonusArray[i]);
                    }

                }
                TextViewCivCivilizationBonus = (TextView) findViewById(R.id.TextViewCivCivilizationBonus);
                TextViewCivCivilizationBonus.setText(TextViewCivCivilizationBonus.getText() + civBonusesConnectingString);
            }
            /*uniqueTech = civInformationJSON.getString("unique_tech");*/
            teamBonus = civInformationJSON.getString("team_bonus");
            /*civilizationBonus = civInformationJSON.getString("civilization_bonus");*/

            TextViewCivName = (TextView) findViewById(R.id.TextViewCivName);
            TextViewCivName.setText(name);

            TextViewCivExpansion = (TextView) findViewById(R.id.TextViewCivExpansion);
            TextViewCivExpansion.setText(TextViewCivExpansion.getText() + " " + expansion);

            TextViewCivArmyType = (TextView) findViewById(R.id.TextViewCivArmyType);
            TextViewCivArmyType.setText(TextViewCivArmyType.getText() + " " + armyType);

            TextViewCivTeamBonus = (TextView) findViewById(R.id.TextViewCivTeamBonus);
            TextViewCivTeamBonus.setText(TextViewCivTeamBonus.getText() + " " + teamBonus);

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
