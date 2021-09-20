package com.namita.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(User user);

    @Query("delete from user_table")
    void deleteAll();

    @Query("select * from user_table")
    LiveData<List<User>> getAllUsers();

    @Query("select * from user_table where mobileNo = :mobile_no")
    User getUser(String mobile_no);
}
