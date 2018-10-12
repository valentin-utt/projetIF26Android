package fr.utt.if26.projet.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import fr.utt.if26.projet.R;

public class MainActivity extends AppCompatActivity {

    private Button mapButton;
    private Button signInButton;

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
                startActivity(i);
            }
        });


    }
}
