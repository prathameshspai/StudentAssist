package com.example.roomimplement.NOTES;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.roomimplement.GPA.CgpaActivity;
import com.example.roomimplement.GPA.CgpaManActivity;
import com.example.roomimplement.R;
import com.example.roomimplement.NOTES.models.Notes;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NotesTakerActivity extends AppCompatActivity {
    EditText editText_title, edittext_notes;
    ImageView imageview_save;
    Notes notes;

    Boolean isOldNote=false;
    //When get notes from intent, it means it is old note

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_taker);

        editText_title=findViewById(R.id.editText_title);
        edittext_notes=findViewById(R.id.edittext_notes);
        imageview_save=findViewById(R.id.imageview_save);

        getSupportActionBar().setTitle(Html.fromHtml("Add note"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        notes = new Notes();
        try {
            //Set notes as it is as u click on cardview.
            notes = (Notes) getIntent().getSerializableExtra("old_note");
            editText_title.setText(notes.getTitle());
            edittext_notes.setText(notes.getNotes());
            isOldNote=true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        //to SAVE in DB



    }

    public void savenote() {
        String title= editText_title.getText().toString();
        String description = edittext_notes.getText().toString();

        if(description.isEmpty()){
            Toast.makeText(NotesTakerActivity.this, "Please add some notes",Toast.LENGTH_SHORT).show();
            return;
        }

        SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy");
        Date date = new Date();

        if(!isOldNote){
            notes = new Notes(); //this notes will now contain all the things typed by user.
        }


        notes.setTitle(title);
        notes.setNotes(description);
        notes.setDate(formatter.format(date));

        Intent intent = new Intent();
        intent.putExtra("note", notes); //to use putExtra, Notes should extend Serializable; RECIEVE IN MAINACTIVITY.
        setResult(Activity.RESULT_OK,intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item2:
                savenote();
                return true;

            case android.R.id.home:
                onBackPressed();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}