package com.example.sephirot47.randporn;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MainFragment extends Fragment
{
    public static MainFragment fragment;
    private Button buttonGetVideo;

    public MainFragment()
    {
        fragment = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        buttonGetVideo = (Button) rootView.findViewById(R.id.button_get_random_video);
        buttonGetVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                new PageLoader().execute();
            }
        });

        return rootView;
    }
}
