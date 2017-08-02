package com.tns.espapp.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.tns.espapp.R;
import com.tns.espapp.database.ChecklistData;
import com.tns.espapp.database.DatabaseHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 */
public class GetCheckListSavedFragment extends Fragment {
    String test;
    String key;

    ArrayList<String > gettotallist = new ArrayList<>();

    public static final String mypreference = "chk_test";
    List<String> valueFormlist = new ArrayList<>();

    List<String> totalList = new ArrayList<>();

    String setFormname;
    DatabaseHandler db;
    List<String> keyFormlist = new ArrayList<>();


    SharedPreferences sharedpreferences;
    public GetCheckListSavedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_get_check_list_saved, container, false);
        TextView textview =(TextView)v.findViewById(R.id.set_formane);
        db = new DatabaseHandler(getActivity());

        sharedpreferences = getActivity().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        setFormname = getArguments().getString("PARAM1");
       // getFormlist = db.getAllChecklistwithFormno(setFormname);
        textview.setText(setFormname);


        String gettext = getForm_pre(setFormname);

        if(gettext.equals("1")) {
            ArrayList<HashMap<String, String>> mapget = db.getForm(setFormname);

            int size = mapget.size();

            if (size > 0) {
                for (HashMap<String, String> map : mapget) {

                    for (Map.Entry<String, String> mapEntry : map.entrySet()) {
                         key = mapEntry.getKey();
                        String value = mapEntry.getValue();
                        valueFormlist.add(value);
                        keyFormlist.add(key);



                        //  Toast.makeText(getActivity(), value, Toast.LENGTH_LONG).show();
                    }

                }



                Set<String> primesWithoutDuplicates = new LinkedHashSet<String>(keyFormlist); // now let's clear the ArrayList so that we can copy all elements from LinkedHashSet primes.clear(); // copying elements but without any duplicates primes.addAll(primesWithoutDuplicates);

                keyFormlist.clear(); // copying elements but without any duplicates primes.addAll(primesWithoutDuplicates);
                keyFormlist.addAll(primesWithoutDuplicates);

                int keysize = keyFormlist.size();

             //   initLinearBindsave(v, getFormlist, saveFormlist);

                initLineaHorizontal_header_Bindsave(v,keyFormlist);
                  initLineaHorizontal_Bindsave(v, valueFormlist,keysize);

            } else {


            }

        }


        return v;
    }










    public void initLinearBindsave(final View view, List<String> checklistDataList,List<String> savelist) {
        int size = checklistDataList.size();

        LinearLayout li1 = (LinearLayout) view.findViewById(R.id.table_main2);
        for (int i = 0; i < checklistDataList.size(); i++) {
            LinearLayout LL3 = new LinearLayout(getActivity());
            // LL2.setBackgroundColor(Color.CYAN);
            LL3.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams LLParams3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

            // LL3.setWeightSum(2f);
            LL3.setLayoutParams(LLParams3);
            TextView tv3 = new TextView(getActivity());
            LinearLayout.LayoutParams tvParams_3 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT,1f);
            tv3.setLayoutParams(tvParams_3);
            tv3.setText(checklistDataList.get(i));
            tv3.setTextSize(16);
            tv3.setTextColor(Color.BLACK);



            final EditText etv3 = new EditText(getActivity());
            LinearLayout.LayoutParams edtParams_4 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT,2f);


            etv3.setLayoutParams(edtParams_4);
            //  etv3.setHint(checklistDataList.get(i).getName());
            etv3.setTextSize(16);
            etv3.setTextColor(Color.BLACK);
            etv3.setId( i);

            String txt = savelist.get(i).toString();
            etv3.setText(txt);
            etv3.setFocusable(false);



            // etv3.setFocusable(false);


            RadioGroup rg = new RadioGroup(getActivity());
            LinearLayout.LayoutParams rg_group_param = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 2f);
            rg.setLayoutParams(rg_group_param);

            rg.setOrientation(LinearLayout.HORIZONTAL);
            rg.setId(i );

            RadioButton rdbtn = new RadioButton(getActivity());
            rdbtn.setText("Yes " );
            rdbtn.setTextSize(16);
            rdbtn.setChecked(false);



            RadioButton rdbtn2 = new RadioButton(getActivity());
            rdbtn2.setText("No " );
            rdbtn2.setTextSize(16);

            rg.addView(rdbtn);
            rg.addView(rdbtn2);

        /*
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    // int id=group.getCheckedRadioButtonId();
                    //  RadioButton rb=(RadioButton) findViewById(id);
                    RadioButton rb=(RadioButton) view. findViewById(checkedId);
                    String radioText=rb.getText().toString();


                }
            });

            */

            LL3.addView(tv3);
            LL3.addView(etv3);
            li1.addView(LL3);


        }


    }


    public void initLineaHorizontal_header_Bindsave(final View view, List<String> checklistDataList){
        int size = checklistDataList.size();
        LinearLayout li1 = (LinearLayout) view.findViewById(R.id.table_main_header);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, checklistDataList);


        // create a gridview
        GridView gridView= new GridView(getActivity());

        gridView.setLayoutParams(new GridView.LayoutParams(GridLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT));
        gridView.setNumColumns(3);
        gridView.setHorizontalSpacing(3);
        gridView.setVerticalSpacing(3);
        gridView.setAdapter(adapter);

        // Adding the gridview to the RelativeLayout as a child
        li1.addView(gridView);

    }

    public void initLineaHorizontal_Bindsave(final View view, List<String> checklistDataList, int size) {
     //   LinearLayout li1 = (LinearLayout) view.findViewById(R.id.table_main2);
        GridView gridView =(GridView)view.findViewById(R.id.gridView_value) ;
        CustomAdapter adapter = new CustomAdapter(getActivity(), checklistDataList);

        // create a RelativeLayou

        // create a gridview
      //  GridView gridView= new GridView(getActivity());


       // gridView.setLayoutParams(new GridView.LayoutParams(GridLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT));
        gridView.setNumColumns(size);

        gridView.setAdapter(adapter);

        // Adding the gridview to the RelativeLayout as a child
      //  li1.addView(gridView);


    }



    public String getForm_pre(String key ){

        String gets =  sharedpreferences.getString(key, "");
        return gets;
    }


    public class CustomAdapter extends BaseAdapter {

       List<String> result;
        Context context;

        private  LayoutInflater inflater=null;
        public CustomAdapter(Context c, List osNameList) {
            // TODO Auto-generated constructor stub
            result=osNameList;
            context=c;
            inflater = ( LayoutInflater )context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return result.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        public class Holder
        {
            TextView os_text;

        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            Holder holder=new Holder();
            View rowView;

            rowView = inflater.inflate(R.layout.gridview_text_getchecklist_adapter, null);
            holder.os_text =(TextView) rowView.findViewById(R.id.grid_text);


            holder.os_text.setText(result.get(position));

        /*    rowView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Toast.makeText(context, "You Clicked "+result.get(position), Toast.LENGTH_SHORT).show();
                }
            });*/

            return rowView;
        }

    }


}
