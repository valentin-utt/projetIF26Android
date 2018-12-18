package fr.utt.if26.projet.view;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.List;

import fr.utt.if26.projet.model.Cache;
import fr.utt.if26.projet.model.User;
import fr.utt.if26.projet.repository.CacheRepository;
import fr.utt.if26.projet.repository.UserRepository;


public class CacheViewModel extends AndroidViewModel {

        private CacheRepository mRepository;

        private LiveData<List<Cache>> mAllCache;
        private MutableLiveData<List<Cache>> searchResults;
        private Cache mCache;

        public CacheViewModel(Application application) {
            super(application);
            mRepository = new CacheRepository(application);
            mAllCache = mRepository.getAllCache();
        }

        public LiveData<List<Cache>> getAllCache() { return mAllCache; }

        public LiveData<List<Cache>> findCacheByType(int key1, int key2, int key3) { return mRepository.findCacheByType(key1,key2,key3); }

        public Cache getCache(int myId) { return mCache; }

        public void insert(Cache cache) { mRepository.insert(cache); }

}
