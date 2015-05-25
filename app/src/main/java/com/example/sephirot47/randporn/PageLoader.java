package com.example.sephirot47.randporn;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;

import org.apache.http.Header;
import org.apache.http.HttpConnection;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.net.HttpURLConnection;
import java.net.URL;

public class PageLoader extends AsyncTask<String, Void, String>
{
    private static final String PAGE_PORNHUB_BASE = "http://www.pornhub.com";
    public static final String[] PAGE_PORNHUB = {PAGE_PORNHUB_BASE + "/random"};
    public static final String TITLE_EXTRA = "Title";
    public static final String CATEGORIES_EXTRA = "Categories";
    public static final String URL_EXTRA = "URL";

    private static String redirectUrl = "";

    @Override
    protected String doInBackground(String... url)
    {
        if(url.length <= 0) return "";

        HttpGet httpGet = new HttpGet(url[0]);
        Log.d("TAG",url[0]);
        DefaultHttpClient httpClientReceive = new DefaultHttpClient();
        try
        {
            HttpResponse response = httpClientReceive.execute(httpGet);
            HttpEntity entity = response.getEntity();

            HttpURLConnection con = (HttpURLConnection) (new URL(url[0]).openConnection());
            con.setInstanceFollowRedirects(false);
            con.connect();
            int responseCode = con.getResponseCode();
            String location = con.getHeaderField("Location");
            redirectUrl = PAGE_PORNHUB_BASE + location;

            String responseText = EntityUtils.toString(entity, "UTF-8");
            return responseText;
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
        ArrayList<String> categories = PageParser.getCategories(pageSource);

        Intent intent = new Intent(MainFragment.fragment.getActivity(), ResultActivity.class);
        intent.putExtra(TITLE_EXTRA, title);
        String categoriesExtra = "";
        for(String category : categories) categoriesExtra += category + ";";
        intent.putExtra(CATEGORIES_EXTRA, categoriesExtra);
        intent.putExtra(URL_EXTRA, redirectUrl);
        MainFragment.fragment.startActivity(intent);
    }
}
