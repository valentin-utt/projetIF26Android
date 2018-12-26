package fr.utt.if26.projet.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import fr.utt.if26.projet.R;

public class FilterActivity extends AppCompatActivity {

    private CheckBox mBoxCheckBox;
    private CheckBox mCaseCheckBox;
    private CheckBox mTreasureCheckBox;

    private Button mConfirmButton;

    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        mBoxCheckBox = findViewById(R.id.boxCheckBox);
        mCaseCheckBox = findViewById(R.id.caseCheckBox);
        mTreasureCheckBox = findViewById(R.id.treasureCheckBox);
        mConfirmButton = findViewById(R.id.confirmFilterButton);

        sharedPref = this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        boolean isBoxChecked = getBooleanFromPreferences(getString(R.string.box_filter_key) );
        boolean isCaseChecked = getBooleanFromPreferences(getString(R.string.case_filter_key) );
        boolean isTreasureChecked = getBooleanFromPreferences(getString(R.string.treasure_filter_key) );

        mBoxCheckBox.setChecked(isBoxChecked);
        mCaseCheckBox.setChecked(isCaseChecked);
        mTreasureCheckBox.setChecked(isTreasureChecked);

        //mBoxCheckBox.setChecked(sharedPref.getBoolean(getString(R.string.box_filter_key), true));
        //mCaseCheckBox.setChecked(sharedPref.getBoolean(getString(R.string.case_filter_key), true));
        //mTreasureCheckBox.setChecked(sharedPref.getBoolean(getString(R.string.case_filter_key), true));




        mBoxCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    FilterActivity.this.putBooleanInPreferences(b, getString(R.string.box_filter_key));
            }
        });

        mCaseCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                FilterActivity.this.putBooleanInPreferences(b, getString(R.string.case_filter_key));
            }
        });

        mTreasureCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                FilterActivity.this.putBooleanInPreferences(b, getString(R.string.treasure_filter_key));
            }
        });

        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), MapsActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getBaseContext(), MapsActivity.class);
        startActivity(i);
    }

    public void putBooleanInPreferences(boolean isChecked,String key){
        SharedPreferences sharedPreferences = this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, isChecked);
        editor.commit();
    }

    public boolean getBooleanFromPreferences(String key){
        SharedPreferences sharedPreferences = this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        Boolean isChecked = sharedPreferences.getBoolean(key, true);
        return isChecked;
    }




}
