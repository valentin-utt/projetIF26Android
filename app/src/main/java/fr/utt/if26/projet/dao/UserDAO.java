package fr.utt.if26.projet.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import fr.utt.if26.projet.model.User;

@Dao
public interface  UserDAO {

    @Insert
    void insert(User user);

    @Query("DELETE FROM user_table")
    void deleteAll();

    @Query("SELECT * from user_table ORDER BY name ASC")
    LiveData<List<User>> getAllUsers();
}
