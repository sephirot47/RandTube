package com.example.sephirot47.randtube;

import java.util.ArrayList;
import java.util.Random;

public class PageParser
{
    private static final String RANDOM_WORD_BEGIN = "<span class='crux'>";
    private static final String RANDOM_WORD_END = "</span>";
    private static final String TITLE_SECTION_BEGIN = "<div class=\"yt-lockup-content\">";
    private static final String TITLE_SECTION_BEGIN_2 = "<h3 class=\"yt-lockup-title\"><a href=";
    private static final String TITLE_BEGIN = "title=\"";
    private static final String TITLE_END = "\"";
    private static final String URL_VIDEO_BEGIN = "href=\"";
    private static final String URL_VIDEO_END = "\"";
    private static final String TITLE_SECTION_END = "</div>";
    private static final String THUMBNAIL_SECTION_BEGIN = "<div class=\"yt-thumb video-thumb\"";
    private static final String THUMBNAIL_BEGIN = "<img src=\"";
    private static final String THUMBNAIL_END   = "\"";

    private static String getTitleSection(String pageSource)
    {
        try
        {
            int i =  pageSource.indexOf(TITLE_SECTION_BEGIN);
            if(i < 0) return "";
            String src = pageSource.substring(i);
            i =  src.indexOf(TITLE_SECTION_BEGIN_2);
            if(i >= 0) src = src.substring(i);
            //i =  src.indexOf(TITLE_SECTION_BEGIN_3);
           // if(i >= 0)  src = src.substring( src.indexOf(i) );
            return src;
        }
        catch(Exception e) { e.printStackTrace(); return ""; }
    }

    public static String getTitle(String pageSource)
    {
        try
        {
            String src = getTitleSection(pageSource);
            int begin = src.indexOf(TITLE_BEGIN) + TITLE_BEGIN.length();
            int end = src.substring(begin).indexOf(TITLE_END) + begin;
            return src.substring(begin, end);
        }
        catch(Exception e) { e.printStackTrace(); return "Unknown title"; }
    }

    public static String getVideoUrl(String pageSource)
    {
        try
        {
            String src = getTitleSection(pageSource);
            int begin = src.indexOf(URL_VIDEO_BEGIN) + URL_VIDEO_BEGIN.length();
            int end = src.substring(begin).indexOf(URL_VIDEO_END) + begin;
            return YoutubeVideoInfoDownloader.YOUTUBE_PAGE + src.substring(begin + 1, end);
        }
        catch(Exception e) { e.printStackTrace(); return "Unknown url"; }
    }

    public static ArrayList<String> getRandomWords(String pageSource)
    {
        ArrayList<String> words = new ArrayList<String>();
        try {
            int numWords = new Random().nextInt(3) + 1;
            String src = pageSource;
            for (int i = 0; i < numWords; ++i) {
                int begin = src.indexOf(RANDOM_WORD_BEGIN);
                int end = src.substring(begin).indexOf(RANDOM_WORD_END) + begin;
                if (begin < 0 || end < 0) break;
                words.add(src.substring(begin + RANDOM_WORD_BEGIN.length(), end));
                src = src.substring(end);
            }
        }
        catch(Exception e) { e.printStackTrace(); }
        return words;
    }

    public static String getThumbnailUrl(String pageSource)
    {
        try
        {
            String src =
                    pageSource.substring(pageSource.indexOf(THUMBNAIL_SECTION_BEGIN) + THUMBNAIL_SECTION_BEGIN.length());
            int begin = src.indexOf(THUMBNAIL_BEGIN) + THUMBNAIL_BEGIN.length();
            if(begin < 0) return null;
            int end = src.substring(begin).indexOf(THUMBNAIL_END);
            if(end < 0) return null;
            else return "http:" + src.substring(begin, end) + "default.jpg";
        }
        catch(Exception e) { e.printStackTrace(); return null; }
    }
}
