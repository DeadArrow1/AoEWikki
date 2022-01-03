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

import java.util.Locale;

public class SearchUnitResultActivity extends AppCompatActivity {
    String name;
    String description;
    String expansion;
    String age;
    String building;
    String cost="";
    double buildTime;
    double reloadTime;
    double attackDelay;
    double movementRate;
    int lineOfSight;
    int hitPoints;
    String rangeSTRING;
    int attack;
    String armor;
    String accuracy;
    String attackBonus="";
    int searchRadius;
    double blastRadius;
    String armorBonus="";


    int i;


    TextView TextViewUnitName;
    TextView TextViewUnitDescription;
    TextView TextViewUnitExpansion;
    TextView TextViewUnitAge;
    TextView TextViewUnitBuilding;
    TextView TextViewUnitCost;
    TextView TextViewUnitBuildTime;
    TextView TextViewUnitReloadTime;
    TextView TextViewUnitAtackDelay;
    TextView TextViewUnitMovementRate;
    TextView TextViewUnitLineOfSight;
    TextView TextViewUnitHitPoints;
    TextView TextViewUnitRange;
    TextView TextViewUnitAttack;
    TextView TextViewUnitArmor;
    TextView TextViewUnitAccuracy;

    TextView TextViewUnitAttackBonus;
    TextView TextViewUnitSearchRadius;
    TextView TextViewUnitBlastRadius;
    TextView TextViewUnitArmorBonus;
    int food=0;
    int wood=0;
    int gold=0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_unit_result);
        String value = getIntent().getStringExtra("responseData");

        try {

            //rozkrajeni JSON objektu na nam vyhovujici stringi pomoci struktury
            JSONObject UnitInformationJSON = new JSONObject(value);

            if(value.contains("name")) {
                name = UnitInformationJSON.getString("name");
                TextViewUnitName = (TextView) findViewById(R.id.TextViewUnitName);
                TextViewUnitName.setText(name);
            }

            if(value.contains("description")) {
                description = UnitInformationJSON.getString("description");
                TextViewUnitDescription = (TextView) findViewById(R.id.TextViewUnitDescription);
                TextViewUnitDescription.setText(description);
            }

            if(value.contains("expansion")) {
                expansion = UnitInformationJSON.getString("expansion");
                TextViewUnitExpansion = (TextView) findViewById(R.id.TextViewUnitExpansion);
                TextViewUnitExpansion.setText("Expansion: "+expansion);
            }

            if(value.contains("age")) {
                age = UnitInformationJSON.getString("age");
                TextViewUnitAge = (TextView) findViewById(R.id.TextViewUnitAge);
                TextViewUnitAge.setText("Age: " + age);
            }

                if(value.contains("created_in")) {
                    building = UnitInformationJSON.getString("created_in");//potreba dalsi pozadavek na API
                    RequestQueue unitQueue = Volley.newRequestQueue(this);
                    String url = building;

                    String[]SplitUrl= building.split("/");
                    building = SplitUrl[SplitUrl.length-1];
                    building = building.replace("_"," ");
                    building = building.substring(0,1).toUpperCase()+building.substring(1);

                    TextViewUnitBuilding = (TextView) findViewById(R.id.TextViewUnitBuilding);
                    TextViewUnitBuilding.setText("Build in: " + building);

                    /*StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            unitQueue.stop();
                            try {

                                JSONArray buildingJSONArray = new JSONArray(response);
                                String [] buildingStringArray = toStringArray(buildingJSONArray);
                                JSONObject buildingJSON = new JSONObject(buildingStringArray[0]);


                                building = buildingJSON.getString("name");
                                TextViewUnitBuilding = (TextView) findViewById(R.id.TextViewUnitBuilding);
                                TextViewUnitBuilding.setText("Build in: " + building);


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
                    unitQueue.add(stringRequest);*/
                }


                    if(value.contains("cost")) {
                        JSONObject costJSON = UnitInformationJSON.getJSONObject("cost");
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
                        TextViewUnitCost = (TextView) findViewById(R.id.TextViewUnitCost);
                        TextViewUnitCost.setText(cost);
                    }

                        if(value.contains("build_time")) {
                            buildTime = UnitInformationJSON.getDouble("build_time");
                            TextViewUnitBuildTime = (TextView) findViewById(R.id.TextViewBuildTime);
                            TextViewUnitBuildTime.setText("Build time: " + buildTime);
                        }


                            if(value.contains("reload_time")) {
                                reloadTime = UnitInformationJSON.getDouble("reload_time");
                                TextViewUnitReloadTime = (TextView) findViewById(R.id.TextViewReloadTime);
                                TextViewUnitReloadTime.setText("Reload time: " + reloadTime);
                            }

                                if(value.contains("attack_delay")) {
                                    attackDelay = UnitInformationJSON.getDouble("attack_delay");
                                    TextViewUnitAtackDelay = (TextView) findViewById(R.id.TextViewAttackDelay);
                                    TextViewUnitAtackDelay.setText("Attack delay: " + attackDelay);
                                }


                                    if(value.contains("movement_rate")) {
                                        movementRate = UnitInformationJSON.getDouble("movement_rate");
                                        TextViewUnitMovementRate = (TextView) findViewById(R.id.TextViewMovementRate);
                                        TextViewUnitMovementRate.setText("Movement rate: " + movementRate);
                                    }

                                        if(value.contains("line_of_sight")) {
                                            lineOfSight = UnitInformationJSON.getInt("line_of_sight");
                                            TextViewUnitLineOfSight = (TextView) findViewById(R.id.TextViewLineOfSight);
                                            TextViewUnitLineOfSight.setText("Line of sight: " + lineOfSight);
                                        }


                                            if(value.contains("hit_points")) {
                                                hitPoints = UnitInformationJSON.getInt("hit_points");
                                                TextViewUnitHitPoints = (TextView) findViewById(R.id.TextViewHitPoints);
                                                TextViewUnitHitPoints.setText("Hit points: " + hitPoints);
                                            }


            if(value.contains("range")) {
                rangeSTRING = UnitInformationJSON.optString("range");
                TextViewUnitRange = (TextView) findViewById(R.id.TextViewRange);
                TextViewUnitRange.setText("Range: " + rangeSTRING);
            }

/////////////////////////////////////////
                if(value.contains("attack")) {
                    attack = UnitInformationJSON.getInt("attack");
                    TextViewUnitAttack = (TextView) findViewById(R.id.TextViewAttack);
                    TextViewUnitAttack.setText("Attack: " + attack);
                }

                    if(value.contains("armor")) {

                        armor = UnitInformationJSON.getString("armor");
                        TextViewUnitArmor = (TextView) findViewById(R.id.TextViewArmor);
                        TextViewUnitArmor.setText("Armor: " + armor);
                    }

                        if(value.contains("accuracy")) {

                            accuracy = UnitInformationJSON.getString("accuracy");
                            TextViewUnitAccuracy = (TextView) findViewById(R.id.TextViewAccuracy);
                            TextViewUnitAccuracy.setText("Accuracy: " + accuracy);
                        }

            if(value.contains("attack_bonus")) {
                JSONArray attackBonusJSONArray = UnitInformationJSON.optJSONArray("attack_bonus");

                String[] attackBonusArray = toStringArray(attackBonusJSONArray);
                for (i = 0; i < attackBonusArray.length; i++) {
                    if (attackBonus.isEmpty())
                        attackBonus = attackBonus.concat("Attack Bonus: \n" +attackBonusArray[i]);
                    else attackBonus = attackBonus.concat("\n" + attackBonusArray[i]);
                }
                TextViewUnitAttackBonus = (TextView) findViewById(R.id.TextViewAttackBonus);
                TextViewUnitAttackBonus.setText(attackBonus);
            }


            //TextView TextViewUnitArmorBonus;

            if(value.contains("search_radius")) {
                searchRadius = UnitInformationJSON.getInt("search_radius");
                TextViewUnitSearchRadius = (TextView) findViewById(R.id.TextViewSearchRadius);
                TextViewUnitSearchRadius.setText("Search radius: " + searchRadius);
            }

            if(value.contains("blast_radius")) {
                blastRadius = UnitInformationJSON.getDouble("blast_radius");
                TextViewUnitBlastRadius = (TextView) findViewById(R.id.TextViewBlastRadius);
                TextViewUnitBlastRadius.setText("Blast radius: " + blastRadius);
            }

            if(value.contains("armor_bonus")) {
                JSONArray armorBonusJSONArray = UnitInformationJSON.optJSONArray("armor_bonus");

                String[] armorBonusArray = toStringArray(armorBonusJSONArray);
                for (i = 0; i < armorBonusArray.length; i++) {
                    if (armorBonus.isEmpty()) armorBonus = armorBonus.concat("Armor bonus: \n"+armorBonusArray[i]);
                    else armorBonus = armorBonus.concat("\n"+ armorBonusArray[i]);
                }
                TextViewUnitArmorBonus = (TextView) findViewById(R.id.TextViewArmorBonus);
                TextViewUnitArmorBonus.setText(armorBonus);
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

    public void openSearchUnitActivity(View v)
    {
        Intent intent = new Intent(this,SearchUnitActivity.class);
        startActivity(intent);

    }
}

