package com.techexchange.mobileapps.assignment1;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

public class Grid extends BaseAdapter {
    public Grid(ArrayList<ImageView> gridArray, int gridCOLUMN, int gridHEIGHT, Context stateContext) {
        this.gridArray = gridArray;
        this.gridCOLUMN = gridCOLUMN;
        this.gridHEIGHT = gridHEIGHT;
        this.stateContext = stateContext;
    }

    ArrayList<ImageView> gridArray = null;
    private int gridCOLUMN;
    private int gridHEIGHT;
    Context stateContext;


    @Override
    public int getCount() {
        return gridArray.size();
    }

    @Override
    public Object getItem(int position) {
        return (Object) gridArray.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView iv;

        if (convertView == null){
            iv = gridArray.get(position);
        }
        else {
            iv = (ImageView) convertView;
        }
        android.widget.AbsListView.LayoutParams params = new android.widget.AbsListView.LayoutParams(gridCOLUMN, gridHEIGHT);
        iv.setLayoutParams(params);

        return iv;
    }
}
