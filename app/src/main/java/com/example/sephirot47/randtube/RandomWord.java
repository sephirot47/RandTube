package com.example.sephirot47.randtube;

import android.app.Activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class RandomWord
{
    private static int WORDS_COUNT = 58100;

    public static ArrayList<String> getRandomWordList(int max)
    {
        ArrayList<String> result = new ArrayList<String>();
        int rand = new Random().nextInt(max) + 1;
        for(int i = 0; i < rand; ++i)
        {
            result.add( getRandomWord() );
        }
        return result;
    }

    public static String getRandomWord()
    {
        String word = "";
        int randomIndex = new Random().nextInt(WORDS_COUNT);
        BufferedReader reader;
        try {
            int i = 0;
            Activity a = MainFragment.fragment.getActivity();
            final InputStream file =
                    a.getResources().openRawResource(
                            a.getResources().getIdentifier("raw/word_list", "raw", a.getPackageName())
                    );

            reader = new BufferedReader(new InputStreamReader(file));
            String line = reader.readLine();
            while (line != null) {
                line = reader.readLine();
                if (i++ >= randomIndex) return line;
            }
        }
        catch(IOException ioe) { ioe.printStackTrace(); }
        return "";
    }
}
