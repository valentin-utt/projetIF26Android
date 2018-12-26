package fr.utt.if26.projet.RoomDb;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.concurrent.Executors;

import fr.utt.if26.projet.dao.UserDAO;
import fr.utt.if26.projet.model.Cache;
import fr.utt.if26.projet.model.User;

@Database(entities = {User.class}, version = 1)
public abstract class UserRoomDatabase extends RoomDatabase {

    public abstract UserDAO userDao();

    private static volatile UserRoomDatabase INSTANCE;

    public static UserRoomDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (UserRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = buildDatabase(context);

                }
            }
        }
        return INSTANCE;
    }

    private static UserRoomDatabase buildDatabase(final Context context) {
        return Room.databaseBuilder(context,
                UserRoomDatabase.class,
                "user_database")
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                            @Override
                            public void run() {
                                User user1 = new User("foo@bar.fr", "XXXX", "Jaques");
                                User user2 = new User("foo@bar.fr", "YYYY", "Oui");
                                getInstance(context).userDao().insert(user1);
                                getInstance(context).userDao().insert(user2);

                            }
                        });
                    }
                })
                .build();
    }


}
