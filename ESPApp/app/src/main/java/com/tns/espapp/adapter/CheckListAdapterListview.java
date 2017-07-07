package com.tns.espapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tns.espapp.AppConstraint;
import com.tns.espapp.R;
import com.tns.espapp.activity.HomeActivity;
import com.tns.espapp.database.ChecklistData;
import com.tns.espapp.database.TaxiFormData;
import com.tns.espapp.fragment.MapWebViewFragment;
import com.tns.espapp.fragment.TaxiFormRecordFragment;
import com.tns.espapp.service.SendLatiLongiServerIntentService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TNS on 07-Jul-17.
 */

public   class  CheckListAdapterListview extends ArrayAdapter {

     Context context;
        ChecklistData checklistData;
        int deepColor = Color.parseColor("#FFFFFF");
        int deepColor2 = Color.parseColor("#DCDCDC");
        //  int deepColor3 = Color.parseColor("#B58EBF");
        private int[] colors = new int[]{deepColor, deepColor2};
        private List<ChecklistData> searchlist = null;

            private CustomOnItemClickListener customOnItemClickListener;


        public CheckListAdapterListview(Context context, int resource, List<ChecklistData> latLongDatas, CustomOnItemClickListener customOnItemClickListener) {
            super(context, resource, latLongDatas);
            this.context = context;
            this.searchlist = latLongDatas;
            this.customOnItemClickListener = customOnItemClickListener;

        }

        private class ViewHolder{

            public TextView name,dob,reporting_head,age;

        }

        @NonNull
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            checklistData = searchlist.get(position);
          ViewHolder viewHolder = new ViewHolder();

            if (convertView == null) {

                LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.checklist_data_adapter, parent, false);
                //int colorPos = position % colors.length;
                // convertView.setBackgroundColor(colors[colorPos]);

               viewHolder. name = (TextView) convertView.findViewById(R.id.name);
                viewHolder. dob = (TextView) convertView.findViewById(R.id.dob);
                viewHolder.reporting_head = (TextView) convertView.findViewById(R.id.rp_head);
                viewHolder. age = (TextView) convertView.findViewById(R.id.age);

                convertView.setTag(viewHolder);

            }else{


                viewHolder = (ViewHolder) convertView.getTag();


            }

            viewHolder. name.setText(checklistData.getName());
            viewHolder.  dob.setText(checklistData.getDob());
            viewHolder.  reporting_head.setText(checklistData.getReportinghead());
            viewHolder. age.setText(checklistData.getAge()+"");






            viewHolder. name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checklistData = searchlist.get(position);
              customOnItemClickListener.onItemClick(checklistData,position);



                }
            });

            /*convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checklistData = searchlist.get(position);
                    customOnItemClickListener.onItemClick(checklistData,position);
                }
            });*/




            return convertView;
        }


    public static interface CustomOnItemClickListener {

        void onItemClick(ChecklistData item, int pos);

    }



    }

