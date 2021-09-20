package com.namita.Database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class MedLifeDatabase extends RoomDatabase {

    public abstract UserDAO userDAO();

    private static volatile MedLifeDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static MedLifeDatabase getDatabase(final Context context){

        if(INSTANCE == null){
            synchronized (MedLifeDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                MedLifeDatabase.class, "user_database")
                               .build();
                }
            }
        }

        return INSTANCE;
    }

//    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){
//
//        @Override
//        public void onCreate(@NonNull SupportSQLiteDatabase db) {
//            super.onCreate(db);
//            databaseWriteExecutor.execute(()->{
//                UserDAO userDAO = INSTANCE.userDAO();
//                userDAO.deleteAll();
//            });
//        }
//    }
}
