package com.example.sephirot47.randporn;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class PageLoader extends AsyncTask<Void, Void, String>
{
    private static final String YOUTUBE_PAGE = "https://www.youtube.com/results?search_query=";
    private static final String RANDOM_WORD_PAGE = "https://www.randomlists.com/random-words";
    public static final String TITLE_EXTRA = "Title";
    public static final String URL_EXTRA = "URL";

    private static String redirectUrl = "";

    @Override
    protected String doInBackground(Void... nada)
    {
        HttpGet httpGet = new HttpGet(RANDOM_WORD_PAGE);
        DefaultHttpClient httpClientReceive = new DefaultHttpClient();
        try
        {
            HttpResponse response = httpClientReceive.execute(httpGet);
            HttpEntity entity = response.getEntity();
            String pageSource = EntityUtils.toString(entity, "UTF-8");

            String query = "";
            ArrayList<String> randomWords = PageParser.getRandomWords(pageSource);
            for(int i = 0; i < randomWords.size(); ++i)
            {
                String word = randomWords.get(i);
                query += word + (i < randomWords.size() - 1 ? "+" : "");
            }

            //Ya tenemos random query, buscamos en youtube
            Log.d("TAG", "query: " + query);

            String url = YOUTUBE_PAGE + query;
            Log.d("TAG", "url: " + url);
            httpGet = new HttpGet(url);
            response = httpClientReceive.execute(httpGet);
            entity = response.getEntity();

            pageSource = EntityUtils.toString(entity, "UTF-8");
            Log.d("TAG", pageSource);

            return pageSource;
        }
        catch (Exception e){e.printStackTrace();}

        return "";
    }

    @Override
    protected void onProgressUpdate(Void... progress)
    {
    }

    @Override
    protected void onPostExecute(String pageSource)
    {
        String title = PageParser.getTitle(pageSource);
        Intent intent = new Intent(MainFragment.fragment.getActivity(), ResultActivity.class);
        intent.putExtra(TITLE_EXTRA, title);
        intent.putExtra(URL_EXTRA, redirectUrl);
        MainFragment.fragment.startActivity(intent);
    }
}
