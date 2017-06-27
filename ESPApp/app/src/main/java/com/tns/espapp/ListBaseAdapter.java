package com.tns.espapp;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by GARIMA on 6/22/2017.
 */

public class ListBaseAdapter extends android.widget.BaseAdapter {
    private Context context;
    private ArrayList<String> menuList;
    private LayoutInflater inflater = null;

    public ListBaseAdapter(Context context, ArrayList<String> menuList)
    {
        this.context = context;
        this.menuList = menuList;

    }
    @Override
    public int getCount() {
        int size = 0;
        if (menuList != null && menuList.size() > 0) {
            size = menuList.size();
        }
        return size;
    }

    @Override
    public Object getItem(int position) {
        if (menuList != null)
            return menuList.get(position);
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        inflater = ((Activity) context).getLayoutInflater();
        final ListBaseAdapter.MyViewHolder mViewHolder;
        String name=null;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.menu_name, parent, false);
            mViewHolder = new ListBaseAdapter.MyViewHolder();
            mViewHolder.menuName = (TextView) convertView.findViewById(R.id.menuNameTV);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ListBaseAdapter.MyViewHolder) convertView.getTag();
        }

        if (menuList != null && menuList.size() > position) {
           name =  menuList.get(position);
        }
        if (name != null) {
            mViewHolder.menuName.setText(name);
        }


        return convertView;
    }
    private class MyViewHolder {

        TextView menuName;

    }
    public void updateAdapter(ArrayList<String> menuList) {
        this.menuList = menuList;
        this.notifyDataSetChanged();
    }

}
