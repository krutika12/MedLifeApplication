package com.namita.Repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.namita.Common.NetworkConnectivity;
import com.namita.DataService.GetDataService;
import com.namita.DataService.RetrofitClientInstance;
import com.namita.Model.Articles;
import com.namita.medlifeapplication.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticleRepository {

    private MutableLiveData<List<Articles>> allArticles;
    Application application;
    MutableLiveData<String> msg;
    GetDataService service;

    public ArticleRepository(Application application) {
        this.application = application;
        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        allArticles = new MutableLiveData<>();
        msg = new MutableLiveData<>();
    }

    public MutableLiveData<List<Articles>> getAllArticles(){
        if(NetworkConnectivity.isNetworkAvailable(application)) {
            service.getArticles().enqueue(new Callback<ArrayList<Articles>>() {
                @Override
                public void onResponse(Call<ArrayList<Articles>> call, Response<ArrayList<Articles>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        allArticles.setValue(response.body());
                    } else {
                        allArticles.setValue(null);
                        msg.postValue(application.getString(R.string.api_error));
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<Articles>> call, Throwable t) {
                    allArticles.setValue(null);
                    msg.postValue(application.getString(R.string.api_error));
                    Log.e("t",t+"");
                }
            });
        }else{
            allArticles.setValue(null);
            msg.postValue(application.getString(R.string.internet_error));
        }
        return allArticles;
    }

    public LiveData<String> getMessage(){
        return msg;
    }
}
