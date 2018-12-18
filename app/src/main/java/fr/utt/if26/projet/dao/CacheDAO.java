package fr.utt.if26.projet.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import fr.utt.if26.projet.model.Cache;
import fr.utt.if26.projet.model.User;

@Dao
public interface CacheDAO {

    @Insert
    void insert(Cache cache);

    @Query("DELETE FROM cache_table")
    void deleteAll();

    @Query("SELECT * from cache_table ORDER BY id ASC")
    LiveData<List<Cache>> getAllCache();

    @Query("SELECT * from cache_table WHERE (type= :typeKey1 OR type= :typeKey2 OR type= :typeKey3)")
    LiveData<List<Cache>> findCacheByType(int typeKey1, int typeKey2, int typeKey3);

    @Query("SELECT * from cache_table WHERE id = :myId")
    Cache findCacheById(int myId);

    @Query("DELETE  from cache_table WHERE id = :myId")
    void deleteCacheById(int myId);


}
