package fr.utt.if26.projet.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.RoomDatabase;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import fr.utt.if26.projet.R;
import fr.utt.if26.projet.RoomDb.CacheRoomDatabase;
import fr.utt.if26.projet.RoomDb.UserRoomDatabase;
import fr.utt.if26.projet.dao.CacheDAO;
import fr.utt.if26.projet.dao.UserDAO;
import fr.utt.if26.projet.model.Cache;
import fr.utt.if26.projet.model.User;
import fr.utt.if26.projet.view.CacheAdapter;
import fr.utt.if26.projet.view.CacheViewModel;

public class ViewCacheActivity extends AppCompatActivity {

    private int cacheId;
    private Cache cache;
    private User user;

    private TextView ownerTextView;
    private TextView typeTextView;
    private TextView difficultyTextView;
    private TextView terrainTextView;
    private TextView sizeTextView;
    private TextView hintTextView;
    private TextView descriptionTextView;

    private Button cacheFoundButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cache);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle(getString(R.string.view_cache));
        setSupportActionBar(myToolbar);

        ownerTextView = findViewById(R.id.ownerTextView);
        typeTextView = findViewById(R.id.typeTextView);
        difficultyTextView = findViewById(R.id.difficultyTextView);
        terrainTextView = findViewById(R.id.terrainTextView);
        hintTextView = findViewById(R.id.hintTextView);
        sizeTextView = findViewById(R.id.sizeTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);

        cacheFoundButton = findViewById(R.id.cacheFoundButton);

        cacheId = getIntent().getIntExtra("CacheId", 0);

        new Thread(new Runnable() {
            public void run() {
                final CacheDAO cacheDAO = CacheRoomDatabase.getInstance(ViewCacheActivity.this).cacheDao();
                UserDAO userDAO = UserRoomDatabase.getInstance(ViewCacheActivity.this).userDao();

                cache = cacheDAO.findCacheById(cacheId);

                user = userDAO.findUserById(cache.getOwner());

                Log.d("VC", "run: " + cache.toString());

                Log.d("VC", "run: " + user.toString());

                ArrayList<Cache> cacheArrayList = new ArrayList<Cache>();
                cacheArrayList.add(cache);

                String userName = user.getUserName();
                CacheAdapter adapter = new CacheAdapter(cacheArrayList,getApplicationContext(), userName);
                ListView listView = (ListView) findViewById(R.id.cacheListView);
                listView.setAdapter(adapter);
                cacheFoundButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new Thread(new Runnable() {
                            public void run() {
                                cacheDAO.deleteCacheById(cacheId);
                                Intent i = new Intent(getBaseContext(), MapsActivity.class);
                                startActivity(i);

                            }
                        }).start();



                    }
                });

            }
        }).start();


    }
}
