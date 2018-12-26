package fr.utt.if26.projet.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import fr.utt.if26.projet.RoomDb.UserRoomDatabase;
import fr.utt.if26.projet.dao.UserDAO;
import fr.utt.if26.projet.model.User;

public class UserRepository {

    private UserDAO mUserDao;
    private LiveData<List<User>> mAllUsers;

    public UserRepository(Application application) {
        UserRoomDatabase db = UserRoomDatabase.getInstance(application);
        mUserDao = db.userDao();
        mAllUsers = mUserDao.getAllUsers();

    }

    public LiveData<List<User>> getAllUsers() {
        return mAllUsers;
    }


    public void insert (User user) {
        new insertAsyncTask(mUserDao).execute(user);
    }

    private static class insertAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDAO mAsyncTaskDao;

        insertAsyncTask(UserDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final User... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}

