package fr.utt.if26.projet.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.RoomDatabase;
import android.os.AsyncTask;

import java.util.List;

import fr.utt.if26.projet.RoomDb.CacheRoomDatabase;
import fr.utt.if26.projet.RoomDb.UserRoomDatabase;
import fr.utt.if26.projet.dao.CacheDAO;
import fr.utt.if26.projet.dao.UserDAO;
import fr.utt.if26.projet.model.Cache;
import fr.utt.if26.projet.model.User;

public class CacheRepository {

    private CacheDAO mCacheDao;
    private LiveData<List<Cache>> mAllCache;
    private Cache mCache;



    public CacheRepository(Application application) {
        CacheRoomDatabase db = CacheRoomDatabase.getInstance(application);
        mCacheDao = db.cacheDao();
        mAllCache = mCacheDao.getAllCache();
    }

    public LiveData<List<Cache>> getAllCache() {
        return mAllCache;
    }

    public LiveData<List<Cache>> findCacheByType(int typeKey1, int typeKey2, int typeKey3) {
        return mCacheDao.findCacheByType(typeKey1,typeKey2,typeKey3 );
    }


    public Cache getCache(int myId) {
        return mCache;
    }

    public void insert (Cache cache) {
        new insertAsyncTask(mCacheDao).execute(cache);
    }



    private static class insertAsyncTask extends AsyncTask<Cache, Void, Void> {

        private CacheDAO mAsyncTaskDao;

        insertAsyncTask(CacheDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Cache... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }


    }






