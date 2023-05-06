package com.example.hcc;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hcc.admin.Bill_Details;

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
        myViewHolder.announcement_list_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent details = new Intent(context, Announcement_Detail.class);
                details.putExtra("id",item.get(i).getId());
                context.startActivity(details);
            }
        });
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView caption;
        TextView duration;

        LinearLayout announcement_list_item;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            caption = (TextView) itemView.findViewById(R.id.caption);
            duration = (TextView) itemView.findViewById(R.id.duration);
            announcement_list_item = (LinearLayout) itemView.findViewById(R.id.announcement_list_item);
        }
    }
}
