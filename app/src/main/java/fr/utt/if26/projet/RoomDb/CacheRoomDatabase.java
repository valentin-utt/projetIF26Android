package fr.utt.if26.projet.RoomDb;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import fr.utt.if26.projet.dao.CacheDAO;
import fr.utt.if26.projet.dao.UserDAO;
import fr.utt.if26.projet.model.Cache;
import fr.utt.if26.projet.model.User;

@Database(entities = {Cache.class}, version = 1)
public abstract class CacheRoomDatabase extends RoomDatabase {

    public abstract CacheDAO cacheDao();

    private static volatile CacheRoomDatabase INSTANCE;

    public static CacheRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CacheRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CacheRoomDatabase.class, "cache_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
