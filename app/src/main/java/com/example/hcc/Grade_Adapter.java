package com.example.hcc;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class Grade_Adapter extends RecyclerView.Adapter<Grade_Adapter.MyViewHolder> {

    private Context context;
    private List<Grade_Item> item;

    public Grade_Adapter(Context context, List<Grade_Item> item) {
        this.context = context;
        this.item = item;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.grade_list, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.subject.setText(item.get(i).getSubject());
        myViewHolder.prof.setText(item.get(i).getProf());
        myViewHolder.pregrade.setText("Prelim: "+item.get(i).getPrelim()+", Midterm: "+item.get(i).getMidterm()+", Finals: "+item.get(i).getFinals());
        myViewHolder.postgrade.setText("Final Grade: "+item.get(i).getFinal_grade()+", Average: "+item.get(i).getAverage()+", Status: "+item.get(i).getStatus());
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

//        ImageView img;
        TextView subject;
        TextView prof;
        TextView pregrade;
        TextView postgrade;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            subject = (TextView) itemView.findViewById(R.id.subject);
            prof = (TextView) itemView.findViewById(R.id.prof);
            pregrade = (TextView) itemView.findViewById(R.id.pregrade);
            postgrade = (TextView) itemView.findViewById(R.id.postgrade);
//            img = (ImageView) itemView.findViewById(R.id.grade_list_img);
        }
    }
}
