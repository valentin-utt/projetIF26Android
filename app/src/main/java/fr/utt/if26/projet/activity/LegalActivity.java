package fr.utt.if26.projet.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import fr.utt.if26.projet.R;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class LegalActivity extends AppCompatActivity {

    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    private TextView mLegalTV;

    private Button mOKButton;

    private CheckBox mRGPDCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legal);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);


        sharedPref = this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        mRGPDCheckBox = findViewById(R.id.RGPDcheckBox);
        mOKButton = findViewById(R.id.buttonOK);
        mLegalTV = findViewById(R.id.textViewRGPD);
        //mLegalTV.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);


        boolean isBoxChecked = getBooleanFromPreferences(getString(R.string.RGPD_acceptKey) );
        mRGPDCheckBox.setChecked(isBoxChecked);

        mRGPDCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                LegalActivity.this.putBooleanInPreferences(b, getString(R.string.RGPD_acceptKey));
            }
        });

        mOKButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), MainActivity.class);
                startActivity(i);
            }
        });


    }

    public void putBooleanInPreferences(boolean isChecked,String key){
        SharedPreferences sharedPreferences = this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, isChecked);
        editor.commit();
    }

    public boolean getBooleanFromPreferences(String key){
        SharedPreferences sharedPreferences = this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        Boolean isChecked = sharedPreferences.getBoolean(key, false);
        return isChecked;
    }


}
