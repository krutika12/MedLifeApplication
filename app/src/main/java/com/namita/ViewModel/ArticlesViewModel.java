package com.namita.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.namita.Model.Articles;
import com.namita.Repository.ArticleRepository;

import java.util.List;

public class ArticlesViewModel extends AndroidViewModel {

    private ArticleRepository articleRepository;
    LiveData<List<Articles>> articles;
    LiveData<String> msg;

    public ArticlesViewModel(@NonNull Application application) {
        super(application);
        articleRepository = new ArticleRepository(application);
        articles = articleRepository.getAllArticles();
        msg = articleRepository.getMessage();
    }

    public LiveData<List<Articles>> getArticles(){
        return articles;
    }

    public LiveData<String> getMessage(){
        return msg;
    }

}
