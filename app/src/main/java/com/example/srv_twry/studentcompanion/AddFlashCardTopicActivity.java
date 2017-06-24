package com.example.srv_twry.studentcompanion;

import android.content.ContentValues;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.srv_twry.studentcompanion.Database.DatabaseContract;

public class AddFlashCardTopicActivity extends AppCompatActivity {

    private int mPriority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_flash_card_topic);
        setTitle(getResources().getString(R.string.add_flash_card_topics));

        //Handling Up navigation
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize to highest mPriority by default (mPriority = 1)
        ((RadioButton) findViewById(R.id.radButton1)).setChecked(true);
        mPriority = 1;
    }

    /**
     * onClickAddTask is called when the "ADD" button is clicked.
     * It retrieves user input and inserts that new task data into the underlying database.
     */
    public void onClickAddTask(View view) {
        //We don't want empty topics in the database hence this check
        String input = ((EditText) findViewById(R.id.editTextTaskDescription)).getText().toString();
        if (input.length() == 0) {
            Toast.makeText(getBaseContext(),"Please enter the topic name !",Toast.LENGTH_SHORT).show();
            return;
        }else if (input.length() >35){
            Toast.makeText(getBaseContext(),"Topic shouldn't have more than 35 characters !",Toast.LENGTH_LONG).show();
            return;
        }


        ContentValues contentValues = new ContentValues();

        contentValues.put(DatabaseContract.FlashCardsTopicsEntry.FLASH_CARDS_TOPIC_NAME, input);
        contentValues.put(DatabaseContract.FlashCardsTopicsEntry.FLASH_CARDS_TOPIC_PRIORITY, mPriority);

        Uri uri = getContentResolver().insert(DatabaseContract.FlashCardsTopicsEntry.CONTENT_URI_FLASH_CARDS_TOPICS, contentValues);


        if(uri != null) {
            Toast.makeText(getBaseContext(),"Created the topic successfully",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getBaseContext(),"Unable to create the topic",Toast.LENGTH_SHORT).show();
        }

        //Returning to the FlashCardsHomeActivity
        finish();

    }


    /**
     * onPrioritySelected is called whenever a priority button is clicked.
     * It changes the value of mPriority based on the selected button.
     */
    public void onPrioritySelected(View view) {
        if (((RadioButton) findViewById(R.id.radButton1)).isChecked()) {
            mPriority = 1;
        } else if (((RadioButton) findViewById(R.id.radButton2)).isChecked()) {
            mPriority = 2;
        } else if (((RadioButton) findViewById(R.id.radButton3)).isChecked()) {
            mPriority = 3;
        }
    }
}
