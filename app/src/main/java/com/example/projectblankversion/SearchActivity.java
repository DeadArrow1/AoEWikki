package com.example.projectblankversion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SearchActivity extends AppCompatActivity {

    private Button UnitButton;
    private Button CivilizationButton;
    private Button TechnologyButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        UnitButton = (Button) findViewById(R.id.SearchForUnit);
        UnitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openSearchForUnitActivity();
            }
        });

        CivilizationButton = (Button) findViewById(R.id.SearchForCivilization);
        CivilizationButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openSearchForCivilizationActivity();
            }
        });

        TechnologyButton = (Button) findViewById(R.id.SearchForTechnology);
        TechnologyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openSearchForTechnologyActivity();
            }
        });
    }


    public void openSearchForUnitActivity()
    {
        Intent intent = new Intent(this,SearchUnitActivity.class);
        startActivity(intent);

    }

    public void openMainActivity(View v)
    {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);

    }
    public void openSearchForTechnologyActivity()
    {
        Intent intent = new Intent(this,SearchTechnologyActivity.class);
        startActivity(intent);

    }
    public void openSearchForCivilizationActivity()
    {
        Intent intent = new Intent(this,SearchCivilizationActivity.class);
        startActivity(intent);

    }
}