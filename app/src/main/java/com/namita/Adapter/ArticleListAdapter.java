package com.namita.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.namita.Activity.ArticleDetailActivity;
import com.namita.Model.Articles;
import com.namita.medlifeapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ArticleListAdapter extends RecyclerView.Adapter<ArticleListAdapter.NoteHolder> {

    List<Articles> articles = new ArrayList<>();
    Context mContext;

    public ArticleListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_list_item, parent, false);
        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.setIsRecyclable(false);
        Articles article = articles.get(position);
        if(article.getTitle() == null || article.getTitle().isEmpty()){
            holder.txtTitle.setText("NA");
        }else {
            holder.txtTitle.setText(article.getTitle());
        }
        if(article.getNewsSite() == null || article.getNewsSite().isEmpty()){
            holder.txtNewsSite.setText("NA");
        }else {
            holder.txtNewsSite.setText(article.getNewsSite());
        }
        if(article.getPublishedAt() == null || article.getPublishedAt().isEmpty()){
            holder.txtDate.setText("Date : NA");
        }else {
            holder.txtDate.setText("Date : "+article.getPublishedAt());
        }
        if (article.getImageUrl() == null || article.getImageUrl().isEmpty()){
            Picasso.get()
                    .load(R.drawable.logo)
                    .into(holder.articleImg);
        }else {
            Picasso.get()
                    .load(article.getImageUrl())
                    .error(R.drawable.logo)
                    .placeholder(R.drawable.logo)
                    .into(holder.articleImg);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ArticleDetailActivity.class);
                intent.putExtra("data", articles.get(position));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(articles!=null && articles.size()>0){
            return articles.size();
        }
        return 0;
    }

    public void setArticles(List<Articles> articles){
        this.articles = articles;
        notifyDataSetChanged();
    }

    class NoteHolder extends RecyclerView.ViewHolder{

        TextView txtTitle, txtNewsSite, txtDate;
        ImageView articleImg;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.title);
            txtNewsSite = itemView.findViewById(R.id.news_site);
            txtDate = itemView.findViewById(R.id.date);
            articleImg = itemView.findViewById(R.id.article_img);
        }
    }
}
