package com.example.projecteve.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.projecteve.R;
import com.example.projecteve.models.Site;

import java.util.List;

public class SitesAdapter extends BaseAdapter {

    private Context context;
    private List<Site> siteList;

    public SitesAdapter(Context context, List<Site> siteList) {
        this.context = context;
        this.siteList = siteList;
    }

    @Override
    public int getCount() {
        return siteList.size();
    }

    @Override
    public Object getItem(int position) {
        return siteList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.site_item, parent, false);
        }

        TextView siteName = convertView.findViewById(R.id.tv_site_name);
        TextView siteLocation = convertView.findViewById(R.id.tv_site_location);
        TextView numEmployees = convertView.findViewById(R.id.tv_num_employees);

        Site site = siteList.get(position);
        siteName.setText(site.getName());
        siteLocation.setText(site.getLocation());
        numEmployees.setText(site.getNumEmployees() + " Employees");

        return convertView;
    }
}

