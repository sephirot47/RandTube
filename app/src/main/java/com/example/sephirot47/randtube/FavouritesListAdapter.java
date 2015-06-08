package com.example.sephirot47.randtube;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class FavouritesListAdapter extends BaseAdapter
{
    ArrayList<VideoInformation> vis;
    public FavouritesListAdapter()
    {
        vis = new ArrayList<VideoInformation>();
    }

    @Override
    public int getCount() { return vis.size();  }

    @Override
    public Object getItem(int position) { return vis.get(position);  }

    @Override
    public long getItemId(int position) { return position; }

    public void add(VideoInformation vi) { vis.add(vi); }
    public void clear() { vis.clear(); }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View v = convertView;
        if (v == null)
        {
            LayoutInflater li;
            li = LayoutInflater.from(MainFragment.fragment.getActivity());
            v = li.inflate(R.layout.item_row_favourites, null);
        }

        VideoInformation p = vis.get(position);
        if (p != null)
        {
            TextView titleText = (TextView) v.findViewById(R.id.item_row_favourites_title);
            ImageView thumbnailView = (ImageView) v.findViewById(R.id.item_row_favourites_thumbnail);

            titleText.setText(p.title);
            thumbnailView.setImageBitmap(p.thumbnail);
        }
        return v;
    }

}