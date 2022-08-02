package com.example.roomimplement;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.roomimplement.GPA.MainActivity;
import com.example.roomimplement.NOTES.NotesActivity;
import com.example.roomimplement.POMO.PomoActivity;
import com.example.roomimplement.QR.ScanActivity;

public class SelectActivity extends AppCompatActivity {
    CardView gpacardview, qrcardview, notecardview, pomocardview, taskcardview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        getSupportActionBar().setTitle(Html.fromHtml("<small>   STUDENT ASSISTANT</small>"));
        getSupportActionBar().setSubtitle(Html.fromHtml("<small>   Engineering made easy..!</small>"));
        getSupportActionBar().hide();

        gpacardview = findViewById(R.id.card_viewgpa);
        qrcardview=findViewById(R.id.card_viewqr);
        notecardview=findViewById(R.id.card_viewnotes);
        pomocardview=findViewById(R.id.card_viewpomo);
        taskcardview=findViewById(R.id.card_viewtodo);

        gpacardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        taskcardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectActivity.this, TaskActivity.class);
                startActivity(intent);
            }
        });

        qrcardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectActivity.this, ScanActivity.class);
                startActivity(intent);
            }
        });

        notecardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectActivity.this, NotesActivity.class);
                startActivity(intent);
            }
        });

        pomocardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectActivity.this, PomoActivity.class);
                startActivity(intent);
            }
        });
    }
}