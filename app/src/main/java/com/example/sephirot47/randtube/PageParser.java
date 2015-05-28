package com.example.sephirot47.randtube;

import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

public class PageParser
{
    private static final String RANDOM_WORD_BEGIN = "<span class='crux'>";
    private static final String RANDOM_WORD_END = "</span>";
    private static final String TITLE_SECTION_BEGIN = "<div class=\"yt-lockup-content\">";
    private static final String TITLE_SECTION_BEGIN_2 = "<ol";
    private static final String TITLE_SECTION_BEGIN_3 = "<h3 class=\"yt-lockup-title\">";
    private static final String TITLE_BEGIN = "title=\"";
    private static final String TITLE_END = "\"";
    private static final String TITLE_SECTION_END = "</div>";

    public static String getTitle(String pageSource)
    {
        String title = "";
        pageSource = pageSource.substring( pageSource.indexOf(TITLE_SECTION_BEGIN) );
        pageSource = pageSource.substring( pageSource.indexOf(TITLE_SECTION_BEGIN_2) );
        pageSource = pageSource.substring( pageSource.indexOf(TITLE_SECTION_BEGIN_3) );
       // pageSource = pageSource.substring(0, pageSource.indexOf(TITLE_SECTION_END) );
        Log.d("TAG", "src: " + pageSource);
        int begin = pageSource.indexOf(TITLE_BEGIN) + TITLE_BEGIN.length();
        Log.d("TAG", "Begin: " + begin);
        int end = pageSource.substring(begin).indexOf(TITLE_END) + begin;
        Log.d("TAG", "End: " + end);
        if(begin < 0 || end < 0) return "Random video";
        else return pageSource.substring(begin, end);
    }

    public static ArrayList<String> getRandomWords(String pageSource)
    {
        ArrayList<String> words = new ArrayList<String>();
        int numWords = new Random().nextInt(3) + 1;
        for(int i = 0; i < numWords; ++i)
        {
            int begin = pageSource.indexOf(RANDOM_WORD_BEGIN);
            int end = pageSource.substring(begin).indexOf(RANDOM_WORD_END) + begin;
            if (begin < 0 || end < 0) break;
            words.add(pageSource.substring(begin + RANDOM_WORD_BEGIN.length(), end));
            pageSource = pageSource.substring(end);
        }
        return words;
    }
}
