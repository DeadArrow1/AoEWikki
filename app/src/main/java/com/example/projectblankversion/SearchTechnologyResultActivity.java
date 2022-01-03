package com.example.projectblankversion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import static com.example.projectblankversion.MainActivity.CivFileName;
import static com.example.projectblankversion.MainActivity.TechFileName;
import static com.example.projectblankversion.MainActivity.UnitFileName;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SearchTechnologyResultActivity extends AppCompatActivity {

    String name;
    String description;
    String expansion;
    String age;
    String building;
    String cost="";
    double buildTime;
    String AppliesTo="";

    int i;


    TextView TextViewTechName;
    TextView TextViewTechDescription;
    TextView TextViewTechExpansion;
    TextView TextViewTechAge;
    TextView TextViewTechBuilding;
    TextView TextViewTechCost;
    TextView TextViewTechBuildTime;
    TextView TextViewTechApply;
    int food=0;
    int wood=0;
    int gold=0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_technology_result);
        String value = getIntent().getStringExtra("responseData");

        try {

            //rozkrajeni JSON objektu na nam vyhovujici stringi pomoci struktury
            JSONObject TechInformationJSON = new JSONObject(value);

            if(value.contains("name")) {
                name = TechInformationJSON.getString("name");
                TextViewTechName = (TextView) findViewById(R.id.TextViewTechName);
                TextViewTechName.setText(name);
            }

            if(value.contains("description")) {
                description = TechInformationJSON.getString("description");
                TextViewTechDescription = (TextView) findViewById(R.id.TextViewTechDescription);
                TextViewTechDescription.setText(description);
            }

            if(value.contains("expansion")) {
                expansion = TechInformationJSON.getString("expansion");
                TextViewTechExpansion = (TextView) findViewById(R.id.TextViewTechExpansion);
                TextViewTechExpansion.setText("Expansion: "+expansion);
            }

            if(value.contains("age")) {
                age = TechInformationJSON.getString("age");
                TextViewTechAge = (TextView) findViewById(R.id.TextViewTechAge);
                TextViewTechAge.setText("Age: " + age);
            }

            if(value.contains("develops_in")) {
                building = TechInformationJSON.getString("develops_in");//potreba dalsi pozadavek na API

                String[] SplitUrl = building.split("/");
                building = SplitUrl[SplitUrl.length - 1];
                building = building.replace("_", " ");
                building = building.substring(0, 1).toUpperCase() + building.substring(1);

                TextViewTechBuilding = (TextView) findViewById(R.id.TextViewTechBuilding);
                TextViewTechBuilding.setText("Build in: " + building);
            }

            if(value.contains("cost")) {
                JSONObject costJSON = TechInformationJSON.getJSONObject("cost");
                cost = cost.concat("Cost: \n");
                if (value.contains("Food")) {
                    food = costJSON.getInt("Food");
                    cost = cost.concat("    Food: " + food + "\n");
                }
                if (value.contains("Wood")) {
                    wood = costJSON.getInt("Wood");
                    cost = cost.concat("    Wood: " + wood + "\n");
                }
                if (value.contains("Gold")) {
                    gold = costJSON.getInt("Gold");
                    cost = cost.concat("    Gold:   " + gold + " ");
                }
                TextViewTechCost = (TextView) findViewById(R.id.TextViewTechCost);
                TextViewTechCost.setText(cost);
            }

            if(value.contains("build_time")) {
                buildTime = TechInformationJSON.getDouble("build_time");
                TextViewTechBuildTime = (TextView) findViewById(R.id.TextViewTechBuildTime);
                TextViewTechBuildTime.setText("Build time: " + buildTime);
            }

            if(value.contains("applies_to")) {
                JSONArray armorBonusJSONArray = TechInformationJSON.optJSONArray("applies_to");

                String[] AppliesToArray = toStringArray(armorBonusJSONArray);
                for (i = 0; i < AppliesToArray.length; i++) {
                    if (AppliesTo.isEmpty()) AppliesTo = AppliesTo.concat("Applies to: \n"+AppliesToArray[i]);
                    else AppliesTo = AppliesTo.concat("\n"+ AppliesToArray[i]);
                }

                String[] SplitUrl = AppliesTo.split("/");
                AppliesTo = SplitUrl[SplitUrl.length - 1];
                AppliesTo = AppliesTo.replace("_", " ");
                AppliesTo = AppliesTo.substring(0, 1).toUpperCase() + AppliesTo.substring(1);

                TextViewTechApply = (TextView) findViewById(R.id.TextViewTechApply);
                TextViewTechApply.setText("Applies to:"+AppliesTo);
            }



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
    public void openSearchTechActivity(View v)
    {
        Intent intent = new Intent(this,SearchTechnologyActivity.class);
        startActivity(intent);

    }
}