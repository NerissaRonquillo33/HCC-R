package com.example.hcc;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class Announcement_Adapter extends RecyclerView.Adapter<Announcement_Adapter.MyViewHolder> {

    private Context context;
    private List<Announcement_Item> item;

    public Announcement_Adapter(Context context, List<Announcement_Item> item) {
        this.context = context;
        this.item = item;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.announcement_list, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.title.setText(item.get(i).getTitle());
        myViewHolder.caption.setText(item.get(i).getCaption());
        myViewHolder.duration.setText(item.get(i).getDuration());
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView caption;
        TextView duration;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            caption = (TextView) itemView.findViewById(R.id.caption);
            duration = (TextView) itemView.findViewById(R.id.duration);
        }
    }
}
