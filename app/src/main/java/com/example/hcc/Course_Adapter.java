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
        myViewHolder.title.setText(item.get(i).getDescription());
        myViewHolder.code.setText(item.get(i).getCode());
        myViewHolder.usy.setText("Unit: "+item.get(i).getUnit()+", Semester: "+item.get(i).getSemester()+", Year: "+item.get(i).getYear());
        myViewHolder.img.setImageResource(item.get(i).getThumbnail());
        myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent details = new Intent(context, Course_Detail.class);
                details.putExtra("code",item.get(i).getCode());
                details.putExtra("unit",item.get(i).getUnit());
                details.putExtra("semester",item.get(i).getSemester());
                details.putExtra("year",item.get(i).getYear());
                details.putExtra("title",item.get(i).getDescription());
                details.putExtra("username",item.get(i).getUsername());
                details.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
        TextView code;
        TextView usy;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.course_list_txt);
            code = (TextView) itemView.findViewById(R.id.code);
            usy = (TextView) itemView.findViewById(R.id.usy);
            img = (ImageView) itemView.findViewById(R.id.course_list_img);
            cardView = (CardView) itemView.findViewById(R.id.course_list_item);
        }
    }
}
