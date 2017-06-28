package com.example.srv_twry.studentcompanion.Utilities;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;

import com.example.srv_twry.studentcompanion.R;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

/**
 * Created by srv_twry on 21/6/17.
 * The class that contains some helper methods to work with the database.
 */

public class DatabaseUtilites {

    //Helper method to return the start time in the desired format for the contest list.
    public static SpannableString getStartTimeTextContestList(Date startTime){
        String startTimeString = startTime.toString();
        String modifiedString;

        String sb = startTimeString.substring(0, 10) +
                "\n" +
                startTimeString.substring(11, 16) +
                " Local";
        modifiedString = sb;

        SpannableString returningSpannableString = new SpannableString(modifiedString);
        returningSpannableString.setSpan(new RelativeSizeSpan(1.0f), 0, 10,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        returningSpannableString.setSpan(new RelativeSizeSpan(0.75f), 11, 22,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return returningSpannableString;
    }

    // Helper method to set the start time and date of the contest in intended order for the details fragment.
    public static SpannableString getStartTimeTextDetailsFragment(Date startTime) {

        String originalString = startTime.toString();
        String modifiedString;

        modifiedString = originalString.substring(0,3) +
                "\n" + originalString.substring(4,10) +
                "\n" + originalString.substring(11,16) + " Local";

        SpannableString returningSpannableString = new SpannableString(modifiedString);
        returningSpannableString.setSpan(new RelativeSizeSpan(1.50f), 0, 3,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        returningSpannableString.setSpan(new ForegroundColorSpan(Color.BLACK), 0, 3,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        returningSpannableString.setSpan(new RelativeSizeSpan(1.25f), 4, 10,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        //returningSpannableString.setSpan(new ForegroundColorSpan(Color.RED), 4, 10,
        //        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        returningSpannableString.setSpan(new RelativeSizeSpan(1.0f), 11, 22,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return returningSpannableString;
    }

    //Helper method to get the Cover image of the Contest
    public static int getCoverImage(String url) {
        URL urlPlatform;
        int returnId;
        try{
            urlPlatform = new URL(url);
            String platformString = urlPlatform.getHost();

            switch (platformString){
                case "www.topcoder.com":
                    returnId= R.drawable.topcoder_cover;
                    break;
                case "www.codechef.com":
                    returnId=R.drawable.codechef_cover;
                    break;
                case "www.hackerrank.com":
                    returnId = R.drawable.hackerrank_cover;
                    break;
                case "www.hackerearth.com":
                    returnId = R.drawable.hackerearth_cover;
                    break;
                case "codeforces.com":
                    returnId = R.drawable.codeforces_cover;
                    break;
                default:
                    returnId = R.mipmap.ic_code;
            }
        }catch (MalformedURLException e){
            e.printStackTrace();
            returnId = R.mipmap.ic_code;
        }
        return returnId;
    }
}
