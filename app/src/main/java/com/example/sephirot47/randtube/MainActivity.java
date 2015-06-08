package com.example.sephirot47.randtube;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.sephirot47.randtube.data.FavouritesContract;


public class MainActivity extends ActionBarActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MainFragment())
                    .commit();
        }

        Cursor c = getContentResolver().query(FavouritesContract.FavouriteEntry.CONTENT_URI,
                                              null, null, null, null);
        c.moveToFirst();
        int i = 0;
        while(c.moveToNext())
        {
            Log.d("TAG", "Row " + (++i) + "___________");
            Log.d("TAG", "Title: " + c.getString(0));
            Log.d("TAG", "URL: " + c.getString(1));
            Log.d("TAG", "Thumbnail path: " + c.getString(2));
            Log.d("TAG", "________________________");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        else if(id == R.id.action_favourites)
        {
            Intent intent = new Intent(MainFragment.fragment.getActivity(), FavouritesActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
