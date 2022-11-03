package com.example.hcc;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class Schedule_Adapter extends RecyclerView.Adapter<Schedule_Adapter.MyViewHolder> {

    private Context context;
    private List<Schedule_Item> item;

    public Schedule_Adapter(Context context, List<Schedule_Item> item) {
        this.context = context;
        this.item = item;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.schedule_list, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.title.setText(item.get(i).getTitle());
        myViewHolder.img.setImageResource(item.get(i).getThumbnail());
        myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent details = new Intent(context, Schedule_Detail.class);
                details.putExtra("title",item.get(i).getTitle());
                context.startActivity(details);
            }
        });
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView title;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.schedule_list_txt);
            img = (ImageView) itemView.findViewById(R.id.schedule_list_img);
            cardView = (CardView) itemView.findViewById(R.id.schedule_list_item);
        }
    }
}
