package com.tns.espapp.fragment;


import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

import com.tns.espapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class CheckListFragment extends Fragment {
    private SimpleDateFormat dateFormatter;
    private Calendar cal;
    private int day;
    private int month;
    private int year;

    String name[] = {"one","two","three","four","five","six","seven","eight"};
    String[] form = { "Form1", "Form2", "Form3", "Form4", "Form5", "Form1", "Form2", "Form3", "Form4", "Form5" };
    static String frg_name;
    static   int getFrg_postion;

    public static CheckListFragment newInstance_CheckListFragment(int index, String value) {
        CheckListFragment f = new CheckListFragment();
        Bundle args = new Bundle();
        getFrg_postion= args.getInt("index", index);
        frg_name= args.getString("value", value);
        f.setArguments(args);
        return f;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_check_list, container, false);
      TextView  frg_from =(TextView)v.findViewById(R.id.frg_from) ;
        frg_from.setText(frg_name +" " + getFrg_postion);


        cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        cal.set(year, month, day);

        initLinearBind(v);

        Spinner spin = (Spinner)v. findViewById(R.id.spinner1);
        //spin.setOnItemSelectedListener(this);


        ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,form);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spin.setAdapter(aa);
        return v;
    }


    public void initLinearBind(View view){

        LinearLayout li1 =(LinearLayout)view.findViewById(R.id.table_main);

        for (int i = 0; i < name.length; i++) {

            LinearLayout LL2 = new LinearLayout(getActivity());
            // LL2.setBackgroundColor(Color.CYAN);
            LL2.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams LLParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

            //  LL2.setWeightSum(1f);
            LL2.setLayoutParams(LLParams);


            TextView tv2 = new TextView(getActivity());
            LinearLayout.LayoutParams tvParams_2 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
            tv2.setLayoutParams(tvParams_2);
            tv2.setText(name[i]);
            tv2.setTextSize(10);
            tv2.setTextColor(Color.BLACK);

            EditText etv2 = new EditText(getActivity());
            LinearLayout.LayoutParams edtParams_3 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 2f);
            etv2.setLayoutParams(edtParams_3);
            etv2.setHint(name[i]);
            etv2.setTextSize(10);
            etv2.setTextColor(Color.BLACK);

            LinearLayout LL3 = new LinearLayout(getActivity());
            // LL2.setBackgroundColor(Color.CYAN);
            LL3.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams LLParams3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

            // LL3.setWeightSum(2f);
            LL3.setLayoutParams(LLParams3);

            TextView tv3 = new TextView(getActivity());
            LinearLayout.LayoutParams tvParams_3 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
            tv3.setLayoutParams(tvParams_3);
            tv3.setText(form[i]);
            tv3.setTextSize(10);
            tv3.setTextColor(Color.BLACK);


            final EditText etv3 = new EditText(getActivity());
            LinearLayout.LayoutParams edtParams_4 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 2f);

            etv3.setLayoutParams(edtParams_4);
            etv3.setHint(form[i]);
            etv3.setTextSize(10);
            etv3.setTextColor(Color.BLACK);
            etv3.setText(dateFormatter.format(cal.getTime()));
            etv3.setFocusable(false);



            LL2.addView(tv2);
            LL2.addView(etv2);

            LL3.addView(tv3);
            LL3.addView(etv3);


            // RelativeLayout rl=((RelativeLayout) findViewById(R.id.screenRL));
            li1.addView(LL2);
            li1.addView(LL3);

            etv3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setdate(etv3);
                }
            });

        }



    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void init(View v) {


      // TableLayout stk = (TableLayout)v. findViewById(R.id.table_main);

        LinearLayout li1 =(LinearLayout)v.findViewById(R.id.table_main);

        TableRow tbrow0 = new TableRow(getActivity());
        tbrow0.setPadding(10,15,15,15);

        TextView tv0 = new TextView(getActivity());
        tv0.setText(" Sl.No ");
        tv0.setTextSize(20);
        tv0.setTextColor(Color.BLACK);
        tbrow0.addView(tv0);

        TextView tv1 = new TextView(getActivity());
        tv1.setText(" Name ");
        tv1.setTextSize(20);
        tv1.setTextColor(Color.BLACK);
        tbrow0.addView(tv1);

        TextView tv2 = new TextView(getActivity());
        tv2.setText(" Field ");
        tv2.setTextSize(20);
        tv2.setTextColor(Color.BLACK);
        tbrow0.addView(tv2);

        li1.addView(tbrow0);


        for (int i = 0; i < name.length; i++) {
            TableRow tbrow = new TableRow(getActivity());
            TextView t1v = new TextView(getActivity());

            ShapeDrawable shape = new ShapeDrawable(new RectShape());
            shape.getPaint().setColor(Color.RED);
            shape.getPaint().setStyle(Paint.Style.STROKE);
            shape.getPaint().setStrokeWidth(3);

            tbrow.setPadding(10,15,15,15);
            // Assign the created border to EditText widget
           // tbrow.setBackground(shape);
            t1v.setText("" + i);
            t1v.setTextColor(Color.BLACK);
            t1v.setGravity(Gravity.CENTER);

            tbrow.addView(t1v);
            TextView t2v = new TextView(getActivity());
            t2v.setText(name[i] + i);
            t2v.setTextColor(Color.BLACK);
            t2v.setGravity(Gravity.CENTER);
            tbrow.addView(t2v);

            TableRow.LayoutParams lparams = new TableRow.LayoutParams(400, ViewGroup.LayoutParams.WRAP_CONTENT);
            EditText edT_v = new EditText(getActivity());
            edT_v.setLayoutParams(lparams);
            edT_v.setBackground(shape);

            edT_v.setTextColor(Color.BLACK);
            edT_v.setGravity(Gravity.CENTER);


            tbrow.addView(edT_v);
            li1.addView(tbrow);
        }

    }

    private void setdate(final EditText edt ) {
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // Display Selected date in textbox
                   /*   addmeeting_edt_selectdate.setText(dayOfMonth + "-"
                              + dateFormatter.format (monthOfYear + 1) + "-" + year);*/
                        cal.set(year, monthOfYear, dayOfMonth);

                        edt.setText(dateFormatter.format(cal.getTime()));

                    }
                }, year, month, day);
        dpd.show();
    }

}
