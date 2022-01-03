package com.example.comarch_app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.Holder> {
    private final List<String> imageUrlList;
    private final List<String> dateList;
    private final List<String> titleList;
    private final List<String> urlList;
    private final Context context;

    public RecyclerAdapter(List<String> titleList, List<String> dateList, List<String> urlList, List<String> imageUrlList, Context context) {
        this.dateList = dateList;
        this.titleList = titleList;
        this.urlList = urlList;
        this.imageUrlList = imageUrlList;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_layout, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int position) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        try {
            Date date=simpleDateFormat.parse(dateList.get(position));
            simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy, HH:mm");
            holder.textViewDate.setText(simpleDateFormat.format(date));
        }catch (IllegalArgumentException | ParseException e){
            holder.textViewDate.setText("-");
        }
        holder.textViewTitle.setText(titleList.get(position));
        Glide.with(context).load(imageUrlList.get(position)).into(holder.imageView);
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.webwiew_layout, null);
                WebView webView = view.findViewById(R.id.webView);
                webView.setWebViewClient(new WebViewClient());
                webView.loadUrl(urlList.get(position));
                builder.setView(view).setCancelable(true).setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return titleList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        RelativeLayout relativeLayout;
        TextView textViewTitle, textViewDate;
        ImageView imageView;

        public Holder(@NonNull View itemView) {
            super(itemView);
            relativeLayout = itemView.findViewById(R.id.itemRelativeLayout);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }

}
