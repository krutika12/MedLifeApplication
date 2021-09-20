package com.namita.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.namita.Common.DownloadFileFromURL;
import com.namita.Model.Articles;
import com.namita.medlifeapplication.R;
import com.namita.medlifeapplication.databinding.ActivityArticleDetailBinding;
import com.squareup.picasso.Picasso;

public class ArticleDetailActivity extends AppCompatActivity {

    Articles articles;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_article_detail);
        //Custom ActionBar
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
//        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));
        ImageView btnBack = getSupportActionBar().getCustomView().findViewById(R.id.btn_back);
        TextView txtTitle = getSupportActionBar().getCustomView().findViewById(R.id.title_text);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ActivityArticleDetailBinding activityArticleDetailBinding = DataBindingUtil.setContentView(ArticleDetailActivity.this, R.layout.activity_article_detail);

        if(getIntent().hasExtra("data")){
            articles = (Articles) getIntent().getSerializableExtra("data");
        }

        if(articles.getTitle()==null && articles.getTitle().isEmpty()){
            activityArticleDetailBinding.setTitle("NA");
            txtTitle.setText("Article");
        }else{
            activityArticleDetailBinding.setTitle(articles.getTitle());
            txtTitle.setText(articles.getTitle());
        }
        if(articles.getNewsSite()==null && articles.getNewsSite().isEmpty()){
            activityArticleDetailBinding.setNewsSite("NA");
        }else{
            activityArticleDetailBinding.setNewsSite(articles.getNewsSite());
        }
        if(articles.getPublishedAt()==null && articles.getPublishedAt().isEmpty()){
            activityArticleDetailBinding.setDate("NA");
        }else{
            activityArticleDetailBinding.setDate(articles.getPublishedAt());
        }
        if(articles.getSummary()==null && articles.getSummary().isEmpty()){
            activityArticleDetailBinding.setDesc("NA");
        }else{
            activityArticleDetailBinding.setDesc(articles.getSummary());
        }
        if(articles.getImageUrl()==null && articles.getImageUrl().isEmpty()){
            Picasso.get()
                    .load(R.drawable.logo)
                    .into(activityArticleDetailBinding.articleImg);
        }else{
            Picasso.get()
                    .load(articles.getImageUrl())
                    .error(R.drawable.logo)
                    .placeholder(R.drawable.logo)
                    .into(activityArticleDetailBinding.articleImg);
        }
        activityArticleDetailBinding.knowMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse(articles.getUrl()));
                startActivity(viewIntent);
            }
        });
        activityArticleDetailBinding.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkPermission()) {
                    requestPermission();
                } else {
                    DownloadFileFromURL downloadFileFromURL = new DownloadFileFromURL(ArticleDetailActivity.this, articles.getTitle(),articles.getImageUrl());
                    downloadFileFromURL.execute(articles.getImageUrl());
                }
            }
        });
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, 200);
    }

    private boolean checkPermission() {

        //read write storage permission is used for catlogue downloading

        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        int result2 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

        return result1 == PackageManager.PERMISSION_GRANTED
                && result2 == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 200:
                if (grantResults.length > 0) {
                    boolean read_external_storage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean write_external_storage = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (read_external_storage && write_external_storage) {
                        DownloadFileFromURL downloadFileFromURL = new DownloadFileFromURL(ArticleDetailActivity.this, articles.getTitle(),articles.getImageUrl());
                        downloadFileFromURL.execute(articles.getImageUrl());
                    } else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE) || shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                                ActivityCompat.requestPermissions(ArticleDetailActivity.this,
                                        new String[]{
                                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                                        },
                                        200);
                            } else {

                                alertDialog = new android.app.AlertDialog.Builder(ArticleDetailActivity.this)
                                        .setTitle(R.string.app_name)
                                        .setMessage("Please enable permission.")
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                                Uri uri = Uri.fromParts("package", getPackageName(), null);
                                                intent.setData(uri);
                                                startActivityForResult(intent, 200);
                                                dialog.dismiss();
                                            }
                                        }).show();
                            }
                        }
                    }
                }
                break;
        }
    }
}