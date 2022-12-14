package com.example.hcc.admin;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hcc.R;

import java.text.DecimalFormat;
import java.util.List;

public class Parent_Adapter extends RecyclerView.Adapter<Parent_Adapter.MyViewHolder> {

    private Context context;
    private List<Parent_Item> item;

    public Parent_Adapter(Context context, List<Parent_Item> item) {
        this.context = context;
        this.item = item;
    }

    @Override
    public Parent_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.admin_parent_list, viewGroup, false);
        return new Parent_Adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Parent_Adapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.fullname.setText(item.get(i).getLastname() + ", "+item.get(i).getFirstname());
        myViewHolder.username.setText(item.get(i).getUsername());
        myViewHolder.studentname.setText(item.get(i).getStudentname());
        myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent details = new Intent(context, Parent_Details.class);
                details.putExtra("id",item.get(i).getId());
                details.putExtra("firstname",item.get(i).getFirstname());
                details.putExtra("lastname",item.get(i).getLastname());
                details.putExtra("student_id",item.get(i).getStudent_id());
                details.putExtra("username",item.get(i).getUsername());
                details.putExtra("password",item.get(i).getPassword());
                details.putExtra("email",item.get(i).getEmail());
                details.putExtra("studentname",item.get(i).getStudentname());
                context.startActivity(details);
            }
        });
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView fullname;
        TextView username;
        TextView studentname;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            fullname = (TextView) itemView.findViewById(R.id.fullname);
            username = (TextView) itemView.findViewById(R.id.username);
            studentname = (TextView) itemView.findViewById(R.id.studentname);
            cardView = (CardView) itemView.findViewById(R.id.parent_items);
        }
    }
}
