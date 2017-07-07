package com.tns.espapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tns.espapp.R;
import com.tns.espapp.database.ChecklistData;

import java.util.List;

/**
 * Created by TNS on 23-Jun-17.
 */

public class ChecklistAdapter  extends RecyclerView.Adapter<ChecklistAdapter.MyViewHolder> {

    private List<ChecklistData> checkList;

   OnItemClickListener onItemClickListener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,dob,reporting_head,age;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            dob = (TextView) view.findViewById(R.id.dob);
            reporting_head = (TextView) view.findViewById(R.id.rp_head);
            age = (TextView) view.findViewById(R.id.age);
        }


        public void bind(final ChecklistData checklistData, final OnItemClickListener listener, final int pos) {
            name.setText(checklistData.getName());
            dob.setText(checklistData.getDob());
            reporting_head.setText(checklistData.getReportinghead());
            age.setText(checklistData.getAge()+"");


            name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Position: "+ checklistData.getName(), Toast.LENGTH_LONG).show();
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override public void onClick(View v) {

                    listener.onItemClick(checklistData,pos);

                }

            });

        }

    }


    public ChecklistAdapter(List<ChecklistData> checkList, OnItemClickListener onItemClickListener) {
        this.checkList = checkList;
        this.onItemClickListener = onItemClickListener;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.checklist_data_adapter, parent, false);

        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind(checkList.get(position),onItemClickListener,position);


    }

    @Override
    public int getItemCount() {
        return checkList.size();
    }

    public static interface OnItemClickListener {

        void onItemClick(ChecklistData item, int pos);

    }
}
