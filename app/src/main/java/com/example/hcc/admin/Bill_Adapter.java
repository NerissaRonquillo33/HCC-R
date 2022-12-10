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

import com.example.hcc.Course_Adapter;
import com.example.hcc.Course_Item;
import com.example.hcc.R;
import com.example.hcc.Schedule_Detail;

import java.text.DecimalFormat;
import java.util.List;

public class Bill_Adapter extends RecyclerView.Adapter<Bill_Adapter.MyViewHolder> {

    private Context context;
    private List<Bill_Item> item;

    public Bill_Adapter(Context context, List<Bill_Item> item) {
        this.context = context;
        this.item = item;
    }

    @Override
    public Bill_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.admin_billing_list, viewGroup, false);
        return new Bill_Adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Bill_Adapter.MyViewHolder myViewHolder, int i) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        decimalFormat.setGroupingUsed(true);
        decimalFormat.setGroupingSize(3);
        myViewHolder.studentname.setText(item.get(i).getStudentname());
        myViewHolder.course.setText(item.get(i).getCourse());
        myViewHolder.outsbal.setText("PHP " + decimalFormat.format(Integer.parseInt(item.get(i).getBalance())));
        myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent details = new Intent(context, Bill_Details.class);
                details.putExtra("id",item.get(i).getId());
                details.putExtra("studentid",item.get(i).getStudentid());
                details.putExtra("tuitionfee",item.get(i).getTuitionfee());
                details.putExtra("learnandins",item.get(i).getLearnandins());
                details.putExtra("regfee",item.get(i).getRegfee());
                details.putExtra("compprossfee",item.get(i).getCompprossfee());
                details.putExtra("guidandcouns",item.get(i).getGuidandcouns());
                details.putExtra("schoolidfee",item.get(i).getSchoolidfee());
                details.putExtra("studenthand",item.get(i).getStudenthand());
                details.putExtra("schoolfab",item.get(i).getSchoolfab());
                details.putExtra("insurance",item.get(i).getInsurance());
                details.putExtra("totalass",item.get(i).getTotalass());
                details.putExtra("discount",item.get(i).getDiscount());
                details.putExtra("netass",item.get(i).getNetass());
                details.putExtra("cashcheckpay",item.get(i).getCashcheckpay());
                details.putExtra("balance",item.get(i).getBalance());
                details.putExtra("studentname",item.get(i).getStudentname());
                details.putExtra("course",item.get(i).getCourse());
                details.putExtra("role", "admin");
                context.startActivity(details);
            }
        });
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView studentname;
        TextView course;
        TextView outsbal;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            studentname = (TextView) itemView.findViewById(R.id.studentname);
            course = (TextView) itemView.findViewById(R.id.course);
            outsbal = (TextView) itemView.findViewById(R.id.outsbal);
            cardView = (CardView) itemView.findViewById(R.id.billing_items);
        }
    }
}
