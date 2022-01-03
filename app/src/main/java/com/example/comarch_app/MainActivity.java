package com.example.comarch_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private List<String> dateList = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();
    private List<String> urlList = new ArrayList<>();
    private List<String> imageUrlList = new ArrayList<>();
    private List<Articles> articlesList;
    private Button nextBtn, backBtn;
    private TextView pageTxt;
    int positionlist = 0;
    private ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicialize();
        if (articlesList == null) {
            new DataDownloader().execute();
        }
        pageTxt.setText(String.valueOf((positionlist + 1)));
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (articlesList != null) {
                    int listsize = articlesList.size() / 20 - 1;
                    if (positionlist <= listsize)
                        click(true);
                }
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (positionlist > 0)
                    click(false);
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        if ((dialog != null) && dialog.isShowing())
            dialog.dismiss();
        dialog = null;
    }

    private void initRecylerView() {
        RecyclerView recyclerView = findViewById(R.id.recylerview);
        RecyclerAdapter recyclerAdapter;
        if (articlesList != null) {
            clearList();
            getList();
        }
        recyclerAdapter = new RecyclerAdapter(titleList, dateList, urlList, imageUrlList, this);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void inicialize() {
        nextBtn = findViewById(R.id.nextButton);
        backBtn = findViewById(R.id.backButton);
        pageTxt = findViewById(R.id.pageTextView);
    }

    private List<Articles> getArticlesList() {
        Call<List<Articles>> call = new RetrofitClient().getApi().getArticles();
        call.enqueue(new Callback<List<Articles>>() {
            @Override
            public void onResponse(Call<List<Articles>> call, Response<List<Articles>> response) {
                if (response.isSuccessful())
                    articlesList = response.body();
            }

            @Override
            public void onFailure(Call<List<Articles>> call, Throwable t) {
            }
        });
        return articlesList;
    }

    private void getList() {
        int size;
        if (articlesList.size() > positionlist * 20 + 19)
            size = positionlist * 20 + 19;
        else
            size = articlesList.size() - 1;
        for (int i = (positionlist * 20); i <= size; i++) {
            titleList.add(articlesList.get(i).title);
            dateList.add(articlesList.get(i).publishedAt);
            urlList.add(articlesList.get(i).url);
            imageUrlList.add(articlesList.get(i).imageURL);
        }
    }

    private void click(Boolean increase) {
        if (increase)
            positionlist++;
        else
            positionlist--;
        initRecylerView();
        pageTxt.setText(String.valueOf((positionlist + 1)));
    }

    private void clearList() {
        titleList.clear();
        dateList.clear();
        urlList.clear();
        imageUrlList.clear();
    }

    private void dismissdialog(ProgressDialog dialog) {
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (dialog != null && dialog.isShowing())
                    dialog.dismiss();
                initRecylerView();
            }
        };
        handler.postDelayed(runnable, 2000);
    }

    class DataDownloader extends AsyncTask<Void, Void, List<Articles>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setTitle("Loading...");
            dialog.setMessage("Waiting!");
            dialog.show();
        }

        @Override
        protected List<Articles> doInBackground(Void... voids) {
            articlesList = getArticlesList();
            return articlesList;
        }

        @Override
        protected void onPostExecute(List<Articles> articles) {
            super.onPostExecute(articles);
            dismissdialog(dialog);
        }
    }
}