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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.tns.espapp.AppConstraint;
import com.tns.espapp.R;
import com.tns.espapp.adapter.CheckListAdapterListview;
import com.tns.espapp.adapter.ChecklistAdapter;
import com.tns.espapp.database.ChecklistData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */

public class CheckListFragment extends Fragment {
    ChecklistAdapter checklistAdapter;
    private SimpleDateFormat dateFormatter;
    private Calendar cal;
    private int day;
    private int month;
    private int year;
    private List<ChecklistData> check_list = new ArrayList<>();
    private RecyclerView recyclerView;
    private ListView listView;

    String name[] = {"one", "two", "three", "four", "five", "six", "seven", "eight"};
    String[] form = {"Form1", "Form2", "Form3", "Form4", "Form5", "Form6", "Form7", "Form8", "Form9", "Form10"};


    static String frg_name;
    static int getFrg_postion;

    public static CheckListFragment newInstance_CheckListFragment(int index, String value) {
        CheckListFragment f = new CheckListFragment();
        Bundle args = new Bundle();
        getFrg_postion = args.getInt("index", index);
        frg_name = args.getString("value", value);
        f.setArguments(args);
        return f;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_check_list, container, false);
        TextView frg_from = (TextView) v.findViewById(R.id.frg_from);


        frg_from.setText(frg_name + " " + getFrg_postion);


        cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        cal.set(year, month, day);


        initLinearBind(v);
        loadJSONFromAsset();
        Spinner spin = (Spinner) v.findViewById(R.id.spinner1);
        //spin.setOnItemSelectedListener(this);


        ArrayAdapter aa = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, form);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);

        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        checklistAdapter = new ChecklistAdapter(check_list, new ChecklistAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ChecklistData item, int pos) {
                Toast.makeText(getActivity(), item.getName() + "," + pos, Toast.LENGTH_LONG).show();
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        // recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(checklistAdapter);
        checklistAdapter.notifyDataSetChanged();

     /*   listView =(ListView)v.findViewById(R.id.listview);

      CheckListAdapterListview lstadapter = new CheckListAdapterListview(getActivity(), R.layout.checklist_data_adapter, check_list, new CheckListAdapterListview.CustomOnItemClickListener() {
          @Override
          public void onItemClick(ChecklistData item, int pos) {
              Toast.makeText(getActivity(),item.getName(),Toast.LENGTH_LONG).show();
          }
      });
        listView.setAdapter(lstadapter);*/

        //  prepareMovieData();

        getOKHTTP();

        return v;
    }


    public void initLinearBind(View view) {

        LinearLayout li1 = (LinearLayout) view.findViewById(R.id.table_main);

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

        LinearLayout li1 = (LinearLayout) v.findViewById(R.id.table_main);

        TableRow tbrow0 = new TableRow(getActivity());
        tbrow0.setPadding(10, 15, 15, 15);

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

            tbrow.setPadding(10, 15, 15, 15);
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

    private void setdate(final EditText edt) {
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


  /*  private void prepareMovieData() {

        ChecklistData movie = new ChecklistData("Mad Max: Fury Road", "Action & Adventure", "2015", 0);
        check_list.add(movie);

        movie = new ChecklistData("Inside Out", "Animation, Kids & Family", "2015", 1);
        check_list.add(movie);

          movie = new ChecklistData("Star Wars: Episode VII - The Force Awakens", "Action", "2015",2);
        check_list.add(movie);

        movie = new ChecklistData("Shaun the Sheep", "Animation", "2015",3);
        check_list.add(movie);

        movie = new ChecklistData("The Martian", "Science Fiction & Fantasy", "2015",4);
        check_list.add(movie);

        movie = new ChecklistData("Mission: Impossible Rogue Nation", "Action", "2015",5);
        check_list.add(movie);

        movie = new ChecklistData("Up", "Animation", "2009",6);
        check_list.add(movie);

        movie = new ChecklistData("Star Trek", "Science Fiction", "2009",7);
        check_list.add(movie);

        movie = new ChecklistData("The LEGO Movie", "Animation", "2014",8);
        check_list.add(movie);



        checklistAdapter.notifyDataSetChanged();
    }*/


    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("JSONFile.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

            parseJSON(json);

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


    public void parseJSON(String jsonObject) {

        try {
            JSONObject obj = new JSONObject(jsonObject);
            JSONArray m_jArry = obj.getJSONArray("formules");
            ArrayList<HashMap<String, String>> formList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> m_li;

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                Log.d("Details-->", jo_inside.getString("formule"));
                String formula_value = jo_inside.getString("formule");
                String url_value = jo_inside.getString("url");

                //Add your values in your `ArrayList` as below:
                m_li = new HashMap<String, String>();
                m_li.put("formule", formula_value);
                m_li.put("url", url_value);

                ChecklistData checklistData = new ChecklistData(formula_value, url_value, "2015", 0);
                check_list.add(checklistData);
                //  checklistAdapter.notifyDataSetChanged();

                formList.add(m_li);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void sendFCMPush() {

        final String Legacy_SERVER_KEY = "AIzaSyBM-G98OFEuJ7CcDMbwk4M3Z4oWEJ4Vcj4";
        String msg = "this is test message,.,,.,.";
        String title = "my title";
        String token = "caAXHgnPD2c:APA91bEkcbZFSMpMsDIEzjem7FHvzxvy6g0Argkk0uyOWr0UxxVgT4vaEbONfAEgXfAOAdJBrpclX-j0GqaJYWgYjJnjm79Mhk3GhKI5G14PAs9mLDtJ8uzXLgJ8UZ7zWXmvle2uAXWN";

        JSONObject obj = null;
        JSONObject objData = null;
        JSONObject dataobjData = null;

        try {
            obj = new JSONObject();
            objData = new JSONObject();

            objData.put("body", msg);
            objData.put("title", title);
            objData.put("sound", "default");
            objData.put("icon", "icon_name"); //   icon_name image must be there in drawable
            objData.put("tag", token);
            objData.put("priority", "high");

            dataobjData = new JSONObject();
            dataobjData.put("text", msg);
            dataobjData.put("title", title);

            obj.put("to", token);
            //obj.put("priority", "high");

            obj.put("notification", objData);
            obj.put("data", dataobjData);
            Log.e("!_@rj@_@@_PASS:>", obj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(com.android.volley.Request.Method.POST, AppConstraint.FCM_PUSH_URL, obj,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("!_@@_SUCESS", response + "");
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("!_@@_Errors--", error + "");
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "key=" + Legacy_SERVER_KEY);
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        int socketTimeout = 1000 * 60;// 60 seconds
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsObjRequest.setRetryPolicy(policy);
        requestQueue.add(jsObjRequest);
    }

    private void getOKHTTP() {

       // String mStringUrl = "http://www.yoururl.com";

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, "{\"DatabaseName\": \"TNS_HR\",\"ServerName\": \"bkp-server\",\"UserId\": \"bkp-sanjay\",\"Password\": \"tnssoft\",\"spName\" :\"USP_GetDeviceSetting\",\"loginID\": \"16865\",\"loginPassword\": \"deepak16865\",\"Deviceid\": \"111223543232233\"}");


        HttpUrl.Builder urlBuilder = HttpUrl.parse(AppConstraint.VERIFYLOGINURL).newBuilder();
        urlBuilder.addQueryParameter("DatabaseName", "TNS_HR");
        urlBuilder.addQueryParameter("ServerName", "bkp-server");
        urlBuilder.addQueryParameter("UserId", "bkp-sanjay");
        urlBuilder.addQueryParameter("Password", "tnssoft");
        urlBuilder.addQueryParameter("spName", "USP_GetDeviceSetting");
        urlBuilder.addQueryParameter("loginID", "16865");
        urlBuilder.addQueryParameter("loginPassword", "deepak16865");
        urlBuilder.addQueryParameter("Deviceid", "111223543232233");

        String url = urlBuilder.build().toString();

        RequestBody formBody = new FormBody.Builder()

       .add("DatabaseName", "TNS_HR")
        .add("ServerName", "bkp-server")
        .add("UserId", "bkp-sanjay")
       .add("Password", "tnssoft")
        .add("spName", "USP_GetDeviceSetting")
        .add("loginID", "16865")
      .add("loginPassword", "deepak16865")
         .add("Deviceid", "111223543232233")

                .build();


        OkHttpClient client = new OkHttpClient();

        final Request request = new Request.Builder().url(AppConstraint.VERIFYLOGINURL).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //do failure stuff
                Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String s = response.toString();
                //  ............................................................
                Toast.makeText(getActivity(),s,Toast.LENGTH_LONG).show();
                //  ............................................................
            }


        });
    }

}

