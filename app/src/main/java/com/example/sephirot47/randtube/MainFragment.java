package com.example.sephirot47.randtube;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class MainFragment extends Fragment
{
    public static MainFragment fragment;
    private ImageButton buttonGetVideo;

    public MainFragment()
    {
        fragment = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        buttonGetVideo = (ImageButton) rootView.findViewById(R.id.button_get_random_video);
        buttonGetVideo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                new YoutubeVideoInfoDownloader().execute();
                try
                {
                    Class c = Class.forName("com.example.sephirot47.randtube.MainFragment");
                    YoutubeVideoInfoDownloader.callback =
                            c.getDeclaredMethod("onVideoInfoDownloaded");
                }
                catch (NoSuchMethodException e) {  e.printStackTrace(); }
                catch (ClassNotFoundException e2) {  e2.printStackTrace(); }
            }
        });

        return rootView;
    }

    public static void onVideoInfoDownloaded()
    {
        VideoInformation info = YoutubeVideoInfoDownloader.lastResult;
        Intent intent = new Intent(MainFragment.fragment.getActivity(), ResultActivity.class);
        fragment.startActivity(intent);
    }
}
