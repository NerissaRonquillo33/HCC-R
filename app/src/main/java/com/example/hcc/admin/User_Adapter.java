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

public class User_Adapter extends RecyclerView.Adapter<User_Adapter.MyViewHolder> {

    private Context context;
    private List<User_Item> item;

    public User_Adapter(Context context, List<User_Item> item) {
        this.context = context;
        this.item = item;
    }

    @Override
    public User_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.admin_user_list, viewGroup, false);
        return new User_Adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull User_Adapter.MyViewHolder myViewHolder, int i) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        decimalFormat.setGroupingUsed(true);
        decimalFormat.setGroupingSize(3);
        myViewHolder.fullname.setText(item.get(i).getLastname() + ", " + item.get(i).getFirstname());
        myViewHolder.username.setText(item.get(i).getUsername());
        myViewHolder.role.setText(item.get(i).getRole());
        myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent details = new Intent(context, User_Details.class);
                details.putExtra("idStudent",item.get(i).getId());
                details.putExtra("idUser",item.get(i).getId2());
                details.putExtra("studentid",item.get(i).getStudentid());
                details.putExtra("firstname",item.get(i).getFirstname());
                details.putExtra("lastname",item.get(i).getLastname());
                details.putExtra("address",item.get(i).getAddress());
                details.putExtra("contact",item.get(i).getContact());
                details.putExtra("birthday",item.get(i).getBirthday());
                details.putExtra("email",item.get(i).getEmail());
                details.putExtra("course",item.get(i).getCourse());
                details.putExtra("year",item.get(i).getYear());
                details.putExtra("section",item.get(i).getSection());
                details.putExtra("role",item.get(i).getRole());
                details.putExtra("username",item.get(i).getUsername());
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
        TextView role;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            fullname = (TextView) itemView.findViewById(R.id.fullname);
            username = (TextView) itemView.findViewById(R.id.username);
            role = (TextView) itemView.findViewById(R.id.role);
            cardView = (CardView) itemView.findViewById(R.id.user_items);
        }
    }
}
