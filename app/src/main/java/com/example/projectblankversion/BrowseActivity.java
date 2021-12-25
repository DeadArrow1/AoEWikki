package com.example.projectblankversion;

import static com.example.projectblankversion.MainActivity.CivFileName;
import static com.example.projectblankversion.MainActivity.TechFileName;
import static com.example.projectblankversion.MainActivity.UnitFileName;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BrowseActivity extends AppCompatActivity {
    private Button BrowseUnitButton;
    private Button BrowseCivilizationButton;
    private Button BrowseTechnologyButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);

        BrowseCivilizationButton = (Button) findViewById(R.id.BrowseCivilizationButton);
        BrowseTechnologyButton=(Button) findViewById(R.id.BrowseTechnologiesButton);
        BrowseUnitButton = (Button) findViewById(R.id.BrowseUnitsButton);
    }


    public void openBrowseCivilizationsActivity(View v)
    {
        Intent intent = new Intent(this, BrowseResultActivity.class);
        intent.putExtra("fileName",CivFileName);
        startActivity(intent);
    }

    public void openBrowseTechnologiesActivity(View v)
    {
        Intent intent = new Intent(this,BrowseResultActivity.class);
        intent.putExtra("fileName",TechFileName);
        startActivity(intent);
    }

    public void openMainActivity(View v)
    {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);

    }

    public void openBrowseUnitsActivity(View v)
    {
        Intent intent = new Intent(this,BrowseResultActivity.class);
        intent.putExtra("fileName",UnitFileName);
        startActivity(intent);
    }
}