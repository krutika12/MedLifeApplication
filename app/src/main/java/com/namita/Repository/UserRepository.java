package com.namita.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.namita.Database.MedLifeDatabase;
import com.namita.Database.User;
import com.namita.Database.UserDAO;

import java.util.List;

public class UserRepository {

    private UserDAO userDAO;
    private LiveData<List<User>> allUsers;
    private MutableLiveData<User> user;

    public UserRepository(Application application){
        MedLifeDatabase db = MedLifeDatabase.getDatabase(application);
        userDAO = db.userDAO();
        allUsers = userDAO.getAllUsers();
        user = new MutableLiveData<>();
    }

    public LiveData<List<User>> getUsers(){
        MedLifeDatabase.databaseWriteExecutor.execute(() -> {
            allUsers = userDAO.getAllUsers();
        });
        return allUsers;
    }

    public void insert(User user){
        MedLifeDatabase.databaseWriteExecutor.execute(() -> {
            userDAO.insert(user);
        });
    }

    public void getUserBg(String mobileNo){
        MedLifeDatabase.databaseWriteExecutor.execute(()->{
//            user = userDAO.getUser(mobileNo);
            user.postValue(userDAO.getUser(mobileNo));
        });
    }

    public MutableLiveData<User> getUser(){
        return user;
    }

}
