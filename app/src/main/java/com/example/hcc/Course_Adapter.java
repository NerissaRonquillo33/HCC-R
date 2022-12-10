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

public class Course_Adapter extends RecyclerView.Adapter<Course_Adapter.MyViewHolder> {

    private Context context;
    private List<Course_Item> item;

    public Course_Adapter(Context context, List<Course_Item> item) {
        this.context = context;
        this.item = item;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.course_list, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.course_description.setText(item.get(i).getCourse_description());
        myViewHolder.course.setText(item.get(i).getCourse());
        myViewHolder.days.setText("Days: " + item.get(i).getDays());
        myViewHolder.time.setText("Time: " + item.get(i).getTime());
        myViewHolder.room.setText("Room: " + item.get(i).getRoom());
        myViewHolder.prof.setText(item.get(i).getProf());
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView course_description;
        TextView course;
        TextView days;
        TextView time;
        TextView room;
        TextView prof;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            course_description = (TextView) itemView.findViewById(R.id.course_description);
            course = (TextView) itemView.findViewById(R.id.course);
            days = (TextView) itemView.findViewById(R.id.days);
            time = (TextView) itemView.findViewById(R.id.time);
            room = (TextView) itemView.findViewById(R.id.room);
            prof = (TextView) itemView.findViewById(R.id.prof);
        }
    }
}
