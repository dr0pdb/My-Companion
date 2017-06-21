package com.example.srv_twry.studentcompanion.Utilities;

import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;

import com.example.srv_twry.studentcompanion.R;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

/**
 * Created by srv_twry on 21/6/17.
 */

public class DatabaseUtilites {

    // Helper method to set the start time and date of the contest in intended order
    public static String getStartTimeText(Date startTime) {
        SpannableString originalFormatTime = new SpannableString(startTime.toString());
        String finalStartTime;

        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(originalFormatTime,0,3);
        spannableStringBuilder.setSpan(new RelativeSizeSpan(3f),0,3,0);
        spannableStringBuilder.append("\n");
        spannableStringBuilder.append(originalFormatTime,4,10);
        spannableStringBuilder.setSpan(new RelativeSizeSpan(1.5f),4,10,0);
        spannableStringBuilder.append("\n");
        spannableStringBuilder.append(originalFormatTime,11,16);
        finalStartTime = spannableStringBuilder.toString();

        return finalStartTime;
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
                    returnId= R.mipmap.topcoder_cover;
                    break;
                case "www.codechef.com":
                    returnId=R.mipmap.codechef_cover;
                    break;
                case "www.hackerrank.com":
                    returnId = R.mipmap.hackerrank_cover;
                    break;
                case "www.hackerearth.com":
                    returnId = R.mipmap.hackerearth_cover;
                    break;
                case "codeforces.com":
                    returnId = R.mipmap.codeforces_cover;
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
