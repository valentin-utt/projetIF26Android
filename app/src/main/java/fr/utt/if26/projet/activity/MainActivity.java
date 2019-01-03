package fr.utt.if26.projet.activity;

import android.arch.lifecycle.ViewModel;
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
import android.widget.Toast;

import java.util.concurrent.Executors;

import fr.utt.if26.projet.R;
import fr.utt.if26.projet.view.UserViewModel;
import fr.utt.if26.projet.model.User;

public class MainActivity extends AppCompatActivity {

    private Button mapButton;
    private Button signInButton;

    public static final int LOGIN_ACTIVITY_REQUEST_CODE = 1;

    private UserViewModel mUserViewModel;
    private Boolean isConnnected;
    private int connectedId;

    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        sharedPref = this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        isConnnected = sharedPref.getBoolean(getString(R.string.login_status), false);




        //get UI elements

        mapButton = findViewById(R.id.mapButton);
        signInButton = findViewById(R.id.signInButton);

        checkLoginState();

        //set onClickEvents

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), MapsActivity.class);
                startActivity(i);
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnnected){
                    setStatusLogedOff();
                }
                else{
                    Intent i = new Intent(getBaseContext(), LoginActivity.class);
                    startActivityForResult(i, LOGIN_ACTIVITY_REQUEST_CODE);
                }
            }
        });

        // Get a new or existing ViewModel from the ViewModelProvider.
        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOGIN_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            setStatusLogedIn();
        }
        isConnnected = sharedPref.getBoolean(getString(R.string.login_status), false);

    }

    public void setStatusLogedIn(){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(getString(R.string.login_status), true);
        editor.commit();
        signInButton.setText(R.string.disconect);
        isConnnected = sharedPref.getBoolean(getString(R.string.login_status), true);
        connectedId = sharedPref.getInt(getString(R.string.loged_id), 0);
    }

    public void setStatusLogedOff(){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(getString(R.string.login_status), false);
        editor.commit();
        signInButton.setText(R.string.login_button);
        isConnnected = sharedPref.getBoolean(getString(R.string.login_status), false);;
    }

    public void checkLoginState(){
        if (isConnnected){
            signInButton.setText(R.string.disconect);
        }
        else{
            signInButton.setText(R.string.login_button);
        }
    }
}

