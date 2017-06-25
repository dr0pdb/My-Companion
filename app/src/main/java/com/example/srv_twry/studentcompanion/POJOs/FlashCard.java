package com.example.srv_twry.studentcompanion.POJOs;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by srv_twry on 25/6/17.
 * A simple class to represent a flash card
 */

public class FlashCard implements Parcelable {
    private String topicName;
    private String question;
    private String answer;

    public FlashCard(String topicName,String question,String answer){
        this.topicName= topicName;
        this.question = question;
        this.answer = answer;
    }

    public String getTopicName() {
        return topicName;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.topicName);
        dest.writeString(this.question);
        dest.writeString(this.answer);
    }

    protected FlashCard(Parcel in) {
        this.topicName = in.readString();
        this.question = in.readString();
        this.answer = in.readString();
    }

    public static final Parcelable.Creator<FlashCard> CREATOR = new Parcelable.Creator<FlashCard>() {
        @Override
        public FlashCard createFromParcel(Parcel source) {
            return new FlashCard(source);
        }

        @Override
        public FlashCard[] newArray(int size) {
            return new FlashCard[size];
        }
    };
}
