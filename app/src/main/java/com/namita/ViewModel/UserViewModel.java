package com.namita.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.namita.Database.User;
import com.namita.Repository.UserRepository;

import java.util.List;

public class UserViewModel extends AndroidViewModel {

    private UserRepository userRepository;

    private LiveData<List<User>> users;
    private LiveData<User> userLiveData;


    public UserViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
        users = userRepository.getUsers();
    }

    public LiveData<List<User>> getUsers(){
        return users;
    }

    public void insert(User user){
        userRepository.insert(user);
    }

    public void getUserBg(String mobileNo){
        userRepository.getUserBg(mobileNo);
    }

    public LiveData<User> getUser(){
//        if(deptDataList == null){
//            deptDataList = addTicketRepo.getDepartments();
//        }
        userLiveData = userRepository.getUser();
        return userLiveData;
    }
}
