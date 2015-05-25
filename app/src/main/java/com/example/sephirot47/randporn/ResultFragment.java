package com.example.sephirot47.randporn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ResultFragment extends Fragment
{
    private TextView titleText, categoriesText, urlText;
    private String title = "";
    private ArrayList<String > categories;
    private String url = "";

    public ResultFragment()
    {
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Intent i = getActivity().getIntent();
        if(i != null)
        {
            title = i.getStringExtra(PageLoader.TITLE_EXTRA);
            categories = new ArrayList<String>();
            String[] categoriesArray =  i.getStringExtra(PageLoader.CATEGORIES_EXTRA).split(";");
            for(String category : categoriesArray)
                categories.add(category);
            url = i.getStringExtra(PageLoader.URL_EXTRA);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_result, container, false);

        titleText = (TextView) rootView.findViewById(R.id.text_title);
        categoriesText = (TextView) rootView.findViewById(R.id.text_categories);
        urlText = (TextView)rootView.findViewById(R.id.text_url);

        titleText.setText(title);

        String catStr = "";
        for(int i = 0; i < categories.size(); ++i)
            catStr += categories.get(i) + (i < categories.size() - 1 ? ", " : "");
        categoriesText.setText(catStr);

        urlText.setText(url);
        return rootView;
    }
}
