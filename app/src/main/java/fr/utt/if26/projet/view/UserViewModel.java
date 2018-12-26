package fr.utt.if26.projet.view;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import fr.utt.if26.projet.model.User;
import fr.utt.if26.projet.repository.UserRepository;


public class UserViewModel extends AndroidViewModel {

        private UserRepository mRepository;

        private LiveData<List<User>> mAllUsers;

        public UserViewModel (Application application) {
            super(application);
            mRepository = new UserRepository(application);
            mAllUsers = mRepository.getAllUsers();
        }

        public LiveData<List<User>> getAllUsers() { return mAllUsers; }

        public void insert(User user) { mRepository.insert(user); }
}
