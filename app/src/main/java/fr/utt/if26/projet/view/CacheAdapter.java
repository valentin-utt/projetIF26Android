package fr.utt.if26.projet.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import fr.utt.if26.projet.R;
import fr.utt.if26.projet.model.Cache;
import fr.utt.if26.projet.model.User;

public class CacheAdapter extends BaseAdapter {

    private String displayedDifficulty;
    private String displayedTerrain;
    private String displayedType;

    private ArrayList<Cache> dataSet;
    private String userName;
    Context mContext;

    public CacheAdapter(ArrayList<Cache> data, Context context, String userName ) {
        this.dataSet = data;
        this.mContext = context;
        this.userName = userName;
    }



    @Override
    public int getCount() {
        return dataSet.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(mContext).inflate(R.layout.cache_layout, parent, false);


        TextView ownerTextView = (TextView) convertView.findViewById(R.id.ownerTextView) ;
        TextView difficultyTextView = (TextView) convertView.findViewById(R.id.difficultyTextView);
        TextView terrainTextView = (TextView) convertView.findViewById(R.id.terrainTextView);
        TextView typeTextView = (TextView) convertView.findViewById(R.id.typeTextView);
        TextView sizeTextView = (TextView) convertView.findViewById(R.id.sizeTextView);
        TextView hintTextView = (TextView) convertView.findViewById(R.id.hintTextView);
        TextView descriptionTextView =(TextView) convertView.findViewById(R.id.descriptionTextView);


        switch (dataSet.get(position).getDifficulty()){
            case 1:
                displayedDifficulty = mContext.getString(R.string.easy);
                break;
            case 2:
                displayedDifficulty =  mContext.getString(R.string.medium);
                break;
            case 3:
                displayedDifficulty = mContext.getString(R.string.hard);
                break;
        }
        switch (dataSet.get(position).getTerrain()){
            case 1:
                displayedTerrain = mContext.getString(R.string.terrain_concrete);
                break;
            case 2:
                displayedTerrain =  mContext.getString(R.string.terrain_dirt);
                break;
            case 3:
                displayedTerrain = mContext.getString(R.string.terrain_sand);
                break;
        }
        switch (dataSet.get(position).getType()){
            case 1:
                displayedType = mContext.getString(R.string.type_box);
                break;
            case 2:
                displayedType =  mContext.getString(R.string.type_case);
                break;
            case 3:
                displayedType = mContext.getString(R.string.type_treasure);
                break;
        }

        ownerTextView.setText(userName);
        difficultyTextView.setText(displayedDifficulty);
        terrainTextView.setText(displayedTerrain);
        typeTextView.setText(displayedType);
        sizeTextView.setText(String.valueOf(dataSet.get(position).getSize()));
        hintTextView.setText(String.valueOf(dataSet.get(position).getHint()));
        descriptionTextView.setText(String.valueOf(dataSet.get(position).getDescription()));


        return convertView;
    }

    // View lookup cache
    private static class ViewHolder {
        TextView ownerTextView;
        TextView difficultyTextView;
        TextView terrainTextView;
        TextView typeTextView;
        TextView sizeTextView;
        TextView hintTextView;
        TextView descriptionTextView;
    }



}