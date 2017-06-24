package com.example.srv_twry.studentcompanion.POJOs;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by srv_twry on 24/6/17.
 * Class to represent a topic for the flash card in order to categorise the cards.
 * Example : AndroidDevelopmentCards, NumberTheoryCards etc.
 */

public class FlashCardTopic implements Parcelable {

    private String name;
    private int priority;           //priority will range from 1-3 with 1 being highest. Topics will be sorted by priority.

    public FlashCardTopic(String name,int priority){
        this.name = name;
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    public String getName() {
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.priority);
    }

    protected FlashCardTopic(Parcel in) {
        this.name = in.readString();
        this.priority = in.readInt();
    }

    public static final Parcelable.Creator<FlashCardTopic> CREATOR = new Parcelable.Creator<FlashCardTopic>() {
        @Override
        public FlashCardTopic createFromParcel(Parcel source) {
            return new FlashCardTopic(source);
        }

        @Override
        public FlashCardTopic[] newArray(int size) {
            return new FlashCardTopic[size];
        }
    };
}
