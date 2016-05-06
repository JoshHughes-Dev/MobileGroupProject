package com.mobilegroupproject.studentorganiser.adapters;

import android.content.Context;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.TextView;

import com.mobilegroupproject.studentorganiser.R;

import java.util.ArrayList;



public class ViewPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private final ArrayList<Fragment> fragmentList = new ArrayList<>();
    private final ArrayList<String> fragmentTitleList = new ArrayList<>();


    public ViewPagerAdapter(FragmentManager manager, Context context) {
        super(manager);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    public void addFrag(Fragment fragment, String title) {
        fragmentList.add(fragment);
        fragmentTitleList.add(title);
    }

    public View getTabView(int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_pager_tab_item, null);

        TextView tabItemName = (TextView) view.findViewById(R.id.view_pager_tab_item_name);

        tabItemName.setText(fragmentTitleList.get(position));
        if(Build.VERSION.SDK_INT < 23 ){
            tabItemName.setTextColor(context.getResources().getColor(android.R.color.background_light));
        }
        else {
            tabItemName.setTextColor(context.getResources().getColor(android.R.color.background_light, null));
        }

        return view;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitleList.get(position);
    }

}

