package fr.utt.if26.projet.activity;

import android.app.Activity;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    private Button quitButton;
    private Menu mainMenu;

    public static final int LOGIN_ACTIVITY_REQUEST_CODE = 1;

    private UserViewModel mUserViewModel;
    private Boolean AppFirstRun;
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

        AppFirstRun = sharedPref.getBoolean(getString(R.string.app_first_run), true);



        Log.d("MAIN", "onCreate: login_status " + Boolean.toString(sharedPref.getBoolean(getString(R.string.login_status), false)) );


        //get UI elements

        mapButton = findViewById(R.id.mapButton);
        signInButton = findViewById(R.id.signInButton);
        quitButton = findViewById(R.id.quitAppButton);


        if (AppFirstRun){
            setStatusLogedOff();
            setAppFirstRun(false);
            Intent i = new Intent(getBaseContext(), LegalActivity.class);
            startActivity(i);
        }

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
                if (sharedPref.getBoolean(getString(R.string.login_status), false)){
                    closeOptionsMenu();
                    setStatusLogedOff();
                    finish();
                    startActivity(getIntent());
                }
                else{
                    if(sharedPref.getBoolean(getString(R.string.RGPD_acceptKey),false))
                    {
                        Intent i = new Intent(getBaseContext(), LoginActivity.class);
                        startActivityForResult(i, LOGIN_ACTIVITY_REQUEST_CODE);
                    }
                    else{
                        Toast.makeText(MainActivity.this, getString(R.string.RGPD_must_confirm), Toast.LENGTH_LONG ).show();
                    }
                }
            }
        });

        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAndRemoveTask();
            }
        });

        // Get a new or existing ViewModel from the ViewModelProvider.
        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        mainMenu = menu;
        inflater.inflate(R.menu.menu2, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.show_legal :
                Intent i = new Intent(this, LegalActivity.class);
                startActivity(i);
                return true;
            case R.id.sign_in_or_register :
                signInButton.performClick();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOGIN_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            setStatusLogedIn();
            Log.d("MAIN", "onActivityResult: LOGED_IN");
            //isConnnected = sharedPref.getBoolean(getString(R.string.login_status), false);
        }


    }

    public void setStatusLogedIn(){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(getString(R.string.login_status), true);
        editor.commit();
        signInButton.setText(R.string.disconect);
        connectedId = sharedPref.getInt(getString(R.string.loged_id), 0);
        Log.d("MAIN", "SetStatus: LOGED_IN");
    }

    public void setStatusLogedOff(){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(getString(R.string.login_status), false);
        editor.commit();
        signInButton.setText(R.string.login_button);
        mainMenu.findItem(R.id.sign_in_or_register).setTitle(R.string.login_button);
        Log.d("MAIN", "SetStatus: LOGED_OFF");
    }

    public void setAppFirstRun(boolean boolvalue){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(getString(R.string.app_first_run), boolvalue);
        editor.commit();
        AppFirstRun = sharedPref.getBoolean(getString(R.string.login_status), boolvalue);
    }

    public void onBackPressed() {
        finishAndRemoveTask();
    }

    public boolean onPrepareOptionsMenu(Menu menu) {

        mainMenu = menu;
        if (sharedPref.getBoolean(getString(R.string.login_status), false)) {
            mainMenu.findItem(R.id.sign_in_or_register).setTitle(R.string.disconect);
        }
        else {
            mainMenu.findItem(R.id.sign_in_or_register).setTitle(R.string.login_button);
        }
        return true;
    }

    public void checkLoginState(){
        if (sharedPref.getBoolean(getString(R.string.login_status), false)){
            signInButton.setText(R.string.disconect);
            Log.d("MAIN", "checkLoginState: Disconnect");
        }
        else{
            signInButton.setText(R.string.login_button);

            Log.d("MAIN", "checkLoginState: Register");
        }
    }
}

