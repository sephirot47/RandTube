package com.example.sephirot47.randtube;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ResultFragment extends Fragment
{
    private static TextView titleText, idleText;// keywordText = null;
    private static ImageView thumbnail;
    private static ImageButton randomButton;
    private static LinearLayout loadingLayout;
    private static RelativeLayout showLayout;

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
        thumbnail = (ImageView) rootView.findViewById(R.id.thumbnail_video);
        idleText = (TextView) rootView.findViewById(R.id.idle_text);
        //keywordText = (TextView) rootView.findViewById(R.id.text_keyword);
        showLayout = (RelativeLayout) rootView.findViewById(R.id.showModeLayout);
        loadingLayout = (LinearLayout) rootView.findViewById(R.id.loadingModeLayout);

        thumbnail.setOnClickListener(new View.OnClickListener()
        { @Override public void onClick(View view) { IntentVideo(); } });

        randomButton = (ImageButton) rootView.findViewById(R.id.button_get_random_video_results);
        randomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //String keyword = keywordText.getText().toString().replace(" ", "+");
                //keyword = TextUtils.htmlEncode(keyword);
                new YoutubeVideoInfoDownloader().execute();
                try
                {
                    Class c = Class.forName("com.example.sephirot47.randtube.ResultFragment");
                    YoutubeVideoInfoDownloader.callback =
                            c.getDeclaredMethod("onVideoInfoDownloaded");
                    goToLoadingMode();
                }
                catch (NoSuchMethodException e) {  e.printStackTrace(); }
                catch (ClassNotFoundException e2) {  e2.printStackTrace(); }
            }
        });

        goToLoadingMode();
        onVideoInfoDownloaded();
        return rootView;
    }

    private void IntentVideo()
    {
        String url = YoutubeVideoInfoDownloader.lastResult.url;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    public static void goToLoadingMode()
    {
        showLayout.setVisibility(View.INVISIBLE);
        loadingLayout.setVisibility(View.VISIBLE);
    }

    public static void goToShowMode()
    {
        showLayout.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.INVISIBLE);
    }

    public static void onVideoInfoDownloaded()
    {
        VideoInformation info = YoutubeVideoInfoDownloader.lastResult;
        titleText.setText( Html.fromHtml(info.title).toString() );
        if(thumbnail != null) thumbnail.setImageBitmap(info.thumbnail);
        goToShowMode();
    }
}
