package com.example.roomimplement.QR;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.roomimplement.R;

public class ScanActivity extends AppCompatActivity {

    CardView scanbtn;
            Button copybtn, browzbtn;
    public static EditText scantext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg));
    getSupportActionBar().setTitle(Html.fromHtml("QR Scanner"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        android.graphics.drawable.Drawable background = ScanActivity.this.getResources().getDrawable(R.drawable.bg);
        getWindow().setBackgroundDrawable(background);

        scantext=(EditText) findViewById(R.id.scantext);
        scanbtn=findViewById(R.id.scanbtn);
        copybtn=(Button) findViewById(R.id.copybtn);
        browzbtn=(Button) findViewById(R.id.browzbtn);

        scanbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),scannerView.class));
            }
        });

        copybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Copied text!", scantext.getText());
                clipboard.setPrimaryClip(clip);

                Toast.makeText(ScanActivity.this, "Copied text!", Toast.LENGTH_SHORT).show();

            }
        });

        browzbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(scantext.getText().toString()));
                    startActivity(i);
                }
                catch (Exception e){
                    String str="https://www.google.com/search?q="+scantext.getText().toString()+"&rlz=1C5CHFA_enIN978IN978&oq=modi&aqs=chrome.0.69i59j46i39j0i20i131i263i433i512j0i131i433j0i67j69i65l3.660j0j7&sourceid=chrome&ie=UTF-8";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(str));
                    startActivity(i);
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.qrscannermenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);

    }
}