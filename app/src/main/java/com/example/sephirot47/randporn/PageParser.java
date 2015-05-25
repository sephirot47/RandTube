package com.example.sephirot47.randporn;

import android.util.Log;

import java.util.ArrayList;

public class PageParser
{
    private static final String PORNHUB_TITLE_BEGIN = "<h1 class=\"title\">";
    private static final String PORNHUB_TITLE_END = "</h1>";

    private static final String PORNHUB_CATEGORIES_BEGIN = "<div class=\"video-info-row\">";
    private static final String PORNHUB_CATEGORY_BEGIN = "'Category');\">";
    private static final String PORNHUB_CATEGORY_END = "</a>";
    private static final String PORNHUB_CATEGORIES_END = "</div>";

    public static String getTitle(String pageSource)
    {
        String title = "";
        int begin = pageSource.indexOf(PORNHUB_TITLE_BEGIN);
        int end = pageSource.indexOf(PORNHUB_TITLE_END);
        if(begin < 0 || end < 0) return "Random video";
        else return pageSource.substring(begin + PORNHUB_TITLE_BEGIN.length(), end);
    }

    public static ArrayList<String> getCategories(String pageSource)
    {
        ArrayList<String> categories = new ArrayList<String>();

        int categoriesBegin = pageSource.indexOf(PORNHUB_CATEGORIES_BEGIN);
        int categoriesEnd = pageSource.indexOf(PORNHUB_CATEGORIES_END, categoriesBegin);
        if(categoriesBegin != -1 && categoriesEnd != -1)
        {
            String categoriesChunk =
                    pageSource.substring(categoriesBegin + PORNHUB_CATEGORIES_BEGIN.length(),
                                         categoriesEnd);

            int categoryBegin =  categoriesChunk.indexOf(PORNHUB_CATEGORY_BEGIN);
            while(categoryBegin >= 0 )
            {
                int categoryEnd = categoriesChunk.indexOf(PORNHUB_CATEGORY_END, categoryBegin);
                if(categoryEnd < 0) break;
                String category =
                        categoriesChunk.substring(categoryBegin + PORNHUB_CATEGORY_BEGIN.length(),
                                categoryEnd);
                categories.add(category);
                categoriesChunk = categoriesChunk.substring(categoryEnd + PORNHUB_CATEGORY_END.length());
                categoryBegin =  categoriesChunk.indexOf(PORNHUB_CATEGORY_BEGIN);
            }
        }
        return categories;
    }
}
