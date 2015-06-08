package com.example.sephirot47.randtube;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.sephirot47.randtube.data.FavouritesContract;

public class FavouritesFragment extends  Fragment
{
    private ListView listView;
    private FavouritesListAdapter adapter;

    public FavouritesFragment()
    {
        adapter = new FavouritesListAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        adapter.clear();
        View rootView = inflater.inflate(R.layout.fragment_favourites, container, false);
        listView = (ListView) rootView.findViewById(R.id.list_view_favourites);
        listView.setAdapter(adapter);

        Cursor c = getActivity().getContentResolver().query(
                FavouritesContract.FavouriteEntry.CONTENT_URI, null, null, null, null);
        c.moveToFirst();
        while(c.moveToNext())
        {
            VideoInformation vi = new VideoInformation();
            vi.title = c.getString(1);
            vi.url = c.getString(0);

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap thumbnail = BitmapFactory.decodeFile(c.getString(2), options);
            vi.thumbnail = thumbnail;

            adapter.add(vi);
        }

        return rootView;
    }
}
