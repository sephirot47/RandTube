package com.example.sephirot47.randtube;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ResultFragment extends Fragment
{
    private static TextView titleText, urlText;
    private static ImageView thumbnail;

    public ResultFragment()
    {
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_result, container, false);

        titleText = (TextView) rootView.findViewById(R.id.text_title);
        urlText = (TextView) rootView.findViewById(R.id.text_url);
        thumbnail = (ImageView) rootView.findViewById(R.id.thumbnail_video);

        Button randomButton = (Button) rootView.findViewById(R.id.button_get_random_video_results);
        randomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                new YoutubeVideoInfoDownloader().execute();
                try
                {
                    Class c = Class.forName("com.example.sephirot47.randtube.ResultFragment");
                    YoutubeVideoInfoDownloader.callback =
                            c.getDeclaredMethod("onVideoInfoDownloaded");
                }
                catch (NoSuchMethodException e) {  e.printStackTrace(); }
                catch (ClassNotFoundException e2) {  e2.printStackTrace(); }
            }
        });

        onVideoInfoDownloaded();

        return rootView;
    }

    public static void onVideoInfoDownloaded()
    {
        VideoInformation info = YoutubeVideoInfoDownloader.lastResult;
        titleText.setText( Html.fromHtml(info.title).toString() );
        urlText.setText(info.url);
        if(thumbnail != null) thumbnail.setImageBitmap(info.thumbnail);
    }
}
