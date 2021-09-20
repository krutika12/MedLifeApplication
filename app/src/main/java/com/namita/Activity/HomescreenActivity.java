package com.namita.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.namita.Adapter.ArticleListAdapter;
import com.namita.Common.Constants;
import com.namita.ViewModel.ArticlesViewModel;
import com.namita.medlifeapplication.R;

public class HomescreenActivity extends AppCompatActivity {

    ArticlesViewModel articlesViewModel;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.article_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(HomescreenActivity.this));
        recyclerView.setHasFixedSize(true);

        ArticleListAdapter articleListAdapter = new ArticleListAdapter(HomescreenActivity.this);
        recyclerView.setAdapter(articleListAdapter);

        progressBar.setVisibility(View.VISIBLE);
        articlesViewModel = new ViewModelProvider(HomescreenActivity.this).get(ArticlesViewModel.class);

        articlesViewModel.getArticles().observe(this, articles -> {
            progressBar.setVisibility(View.GONE);
            articleListAdapter.setArticles(articles);
        });

        articlesViewModel.getMessage().observe(this,s -> {
            progressBar.setVisibility(View.GONE);
            Constants.showAlertDialog(HomescreenActivity.this, s);
        });
    }
}