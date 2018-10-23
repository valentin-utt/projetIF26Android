package fr.utt.if26.projet.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import fr.utt.if26.projet.R;
import fr.utt.if26.projet.view.UserViewModel;
import fr.utt.if26.projet.model.User;

public class MainActivity extends AppCompatActivity {

    private Button mapButton;
    private Button signInButton;

    public static final int NEW_USER_ACTIVITY_REQUEST_CODE = 1;

    private UserViewModel mUserViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);


        //get UI elements

        mapButton = findViewById(R.id.mapButton);
        signInButton = findViewById(R.id.signInButton);

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
                Intent i = new Intent(getBaseContext(), LoginActivity.class);
                startActivityForResult(i, NEW_USER_ACTIVITY_REQUEST_CODE);
            }
        });

        // Get a new or existing ViewModel from the ViewModelProvider.
        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_USER_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            User user = (User) data.getSerializableExtra(LoginActivity.EXTRA_REPLY);
            mUserViewModel.insert(user);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    "error",
                    Toast.LENGTH_LONG).show();
        }
    }
}

