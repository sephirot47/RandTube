package com.example.sephirot47.randtube;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class YoutubeVideoInfoDownloader extends AsyncTask<Void, Void, Void>
{
    public static final String YOUTUBE_PAGE = "https://www.youtube.com/";
    public static final String YOUTUBE_QUERY_PARAMETERS = "results?filters=video&lclk=video&search_query=";
    public static final String YOUTUBE_QUERY_URL = YOUTUBE_PAGE + YOUTUBE_QUERY_PARAMETERS;
    private static final String RANDOM_WORD_PAGE = "https://www.randomlists.com/random-words";
    public static final String TITLE_EXTRA = "Title";
    public static final String URL_EXTRA = "URL";

    public static VideoInformation lastResult = null;
    public static Method callback = null;

    @Override
    protected Void doInBackground(Void... nada)
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

            String url = YOUTUBE_QUERY_URL + query;
            Log.d("TAG", "url: " + url);
            httpGet = new HttpGet(url);
            response = httpClientReceive.execute(httpGet);
            entity = response.getEntity();

            pageSource = EntityUtils.toString(entity, "UTF-8");
            Log.d("TAG", pageSource);

            lastResult = new VideoInformation();
            lastResult.title = PageParser.getTitle(pageSource);
            lastResult.url = PageParser.getVideoUrl(pageSource);
            String thumbnailUrl = PageParser.getThumbnailUrl(pageSource);
            lastResult.thumbnail = downloadBitmap(thumbnailUrl);
        }
        catch (Exception e){ e.printStackTrace(); lastResult = new VideoInformation(); }
        return null;
    }

    private Bitmap downloadBitmap(String url)
    {
        Bitmap bmp = null;
        try
        {
            InputStream in = new java.net.URL(url).openStream();
            bmp = BitmapFactory.decodeStream(in);
        }
        catch (Exception e)
        {
            Log.e("TAG", "Error downloading the thumbnail. (url: " + url + ")");
            e.printStackTrace();
            return null;
        }
        return bmp;
    }

    @Override
    protected void onProgressUpdate(Void... progress)
    {
    }

    @Override
    protected void onPostExecute(Void params)
    {
        try { callback.invoke(null, null); }
        catch (IllegalAccessException e) {  e.printStackTrace(); }
        catch (InvocationTargetException e) { e.printStackTrace(); }
    }
}
