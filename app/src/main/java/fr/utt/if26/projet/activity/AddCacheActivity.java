package fr.utt.if26.projet.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import fr.utt.if26.projet.R;
import fr.utt.if26.projet.model.Cache;
import fr.utt.if26.projet.model.User;
import fr.utt.if26.projet.view.CacheViewModel;

public class AddCacheActivity extends AppCompatActivity {

    public static final String EXTRA_ADD_CACHE = "com.example.android.cacheaddsql.ADD_CACHE";

    private CacheViewModel mCacheViewModel;

    private SharedPreferences sharedPref;

    private RadioGroup difficultyRadioGroup;
    private RadioGroup typeRadioGroup;
    private RadioGroup terrainRadioGroup;
    private EditText sizeEditText;
    private EditText hintEditText;
    private EditText descriptionEditText;
    private Button addCacheButton;
    private Double lon;
    private Double lat;
    private int type;
    private int difficulty;
    private int terrain;
    private int size;
    private String hint;
    private String description;
    private int owner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cache);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        //get intent extras

        Intent intent = getIntent();
        lon = intent.getDoubleExtra("lon",0);
        lat = intent.getDoubleExtra("lat",0);

        //get UI elements

        difficultyRadioGroup = findViewById(R.id.difficultyRadioGroup);
        typeRadioGroup = findViewById(R.id.typeRadioGroup);
        terrainRadioGroup = findViewById(R.id.terrainRadioGroup);
        //sizeEditText = findViewById(R.id.sizeEditText);
        hintEditText = findViewById(R.id.hintEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        addCacheButton = findViewById(R.id.addCacheButton);
        size=0;

        SeekBar seekBar = (SeekBar)findViewById(R.id.seekBar);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                size = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        sharedPref = this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        //set onClickEvents

        addCacheButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (difficultyRadioGroup.getCheckedRadioButtonId()){
                    case R.id.easyRadioButton:
                        difficulty = 1;
                        break;
                    case R.id.mediumRadioButton:
                        difficulty = 2;
                        break;
                    case R.id.hardRadioButton:
                        difficulty = 3;
                        break;
                }

                switch (typeRadioGroup.getCheckedRadioButtonId()){
                    case R.id.boxRadioButton:
                        type = 1;
                        break;
                    case R.id.caseRadioButton:
                        type = 2;
                        break;
                    case R.id.treasureRadioButton3:
                        type = 3;
                        break;
                }

                switch (terrainRadioGroup.getCheckedRadioButtonId()){
                    case R.id.concreteRadioButton:
                        terrain = 1;
                        break;
                    case R.id.sandRadioButton:
                        terrain = 2;
                        break;
                    case R.id.dirtRadioButton:
                        terrain = 3;
                        break;
                }

                //size = Integer.valueOf(sizeEditText.getText().toString());
                hint = hintEditText.getText().toString();
                description = descriptionEditText.getText().toString();

                owner = sharedPref.getInt(getString(R.string.loged_id), -1);

                Log.d("VC", "onClick: " + owner);

                Cache cache = new Cache(lat,lon, type, difficulty, terrain, size, owner, hint, description);

                Intent addCacheIntent = new Intent(getBaseContext(), MapsActivity.class);
                Bundle bundle = new Bundle();

                bundle.putSerializable("CACHE_BUNDLE", cache);
                addCacheIntent.putExtra(EXTRA_ADD_CACHE, cache);
                setResult(RESULT_OK, addCacheIntent);
                mCacheViewModel = ViewModelProviders.of(AddCacheActivity.this).get(CacheViewModel.class);
                mCacheViewModel.insert(cache);
                startActivity(addCacheIntent);


            }
        });
    }


}
