package fr.utt.if26.projet.RoomDb;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.concurrent.Executors;

import fr.utt.if26.projet.dao.CacheDAO;
import fr.utt.if26.projet.dao.UserDAO;
import fr.utt.if26.projet.model.Cache;
import fr.utt.if26.projet.model.User;

@Database(entities = {Cache.class}, version = 1)
public abstract class CacheRoomDatabase extends RoomDatabase {

    public abstract CacheDAO cacheDao();

    private static volatile CacheRoomDatabase INSTANCE;

    public static CacheRoomDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (CacheRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = buildDatabase(context);
                }
            }
        }
        return INSTANCE;
    }


    private static CacheRoomDatabase buildDatabase(final Context context) {
        return Room.databaseBuilder(context,
                CacheRoomDatabase.class,
                "cache_database")
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                            @Override
                            public void run() {
                                Cache cache = new Cache(48.853,2.35,1,1 ,1, 30,1 ,"au dessus de l'arbre", "Une bien belle cache");
                                Cache cache2 = new Cache(-110,37.0042454,2,1 ,2, 10,1 ,"Rien", "C'est magique");
                                Cache cache3 = new Cache(40.730610,-73.935242,3,1 ,3, 16,1 ,"Tout en haut", "la merveille du monde");
                                Cache cache4 = new Cache(48.636,-1.5114,2,1 ,2, 15,1 ,"A droite du banc", "C'est top");
                                Cache cache5 = new Cache(12.496366,41.902784,2,3 ,3, 30,1 ,"A gauche du container", "un coffre extra");
                                Cache cache6 = new Cache(45,2,3,2 ,3, 25,1 ,"Tout en haut", "la merveille du monde");
                                Cache cache7 = new Cache(45,-2,1,3 ,1, 2,1 ,"A droite du banc", "C'est top");
                                Cache cache8 = new Cache(50,3,1,1 ,3, 14,1 ,"A gauche du container", "incroyable");
                                getInstance(context).cacheDao().insert(cache);
                                getInstance(context).cacheDao().insert(cache2);
                                getInstance(context).cacheDao().insert(cache3);
                                getInstance(context).cacheDao().insert(cache4);
                                getInstance(context).cacheDao().insert(cache5);
                                getInstance(context).cacheDao().insert(cache6);
                                getInstance(context).cacheDao().insert(cache7);
                                getInstance(context).cacheDao().insert(cache8);
                            }
                        });
                    }
                })
                .build();
    }

}
