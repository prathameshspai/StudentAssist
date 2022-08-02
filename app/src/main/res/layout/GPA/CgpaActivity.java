package com.example.gpa.GPA;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gpa.R;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class CgpaActivity extends AppCompatActivity {

    LinearLayout layoutListCgpa;
    Button buttonAddCgpa;
    Button buttonSubmitCgpa;
    Button manageCGPA;
    Double sum;
    TextView gp_results;
    int pyq;
    TextView edit_sgpaa;
    ArrayList<CGPA> markList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cgpa);

        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg));
        getSupportActionBar().setTitle(Html.fromHtml("<small>CGPA Calculator</small>"));
        getSupportActionBar().setSubtitle(Html.fromHtml("<small>Add all the previous semesters' SGPAs</small>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        android.graphics.drawable.Drawable background = CgpaActivity.this.getResources().getDrawable(R.drawable.bg);
        getWindow().setBackgroundDrawable(background);

        layoutListCgpa=findViewById(R.id.layout_list_cgpa);
        buttonAddCgpa = findViewById(R.id.button_add_cgpa);
        buttonSubmitCgpa=findViewById(R.id.button_submit_cgpa);
        gp_results=findViewById(R.id.gp_result);

        edit_sgpaa=findViewById(R.id.edit_sgpa);

        buttonAddCgpa.setOnClickListener(v -> addView());
        buttonSubmitCgpa.setOnClickListener(v -> {

            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(layoutListCgpa.getWindowToken(), 0);
            if(checkIfValidAndRead()){
                sum=0.0;
//                        Toast.makeText(MainActivity.this,"Good!",Toast.LENGTH_SHORT).show();
                gp_results.setVisibility(View.VISIBLE);
                    getMyCGPA();

            }

        });


    }

    private boolean checkIfValidAndRead() {
        markList.clear();
        boolean result = true;
        for (int i = 0; i < layoutListCgpa.getChildCount(); i++) {
            View marksview = layoutListCgpa.getChildAt(i);

            EditText editText_gp = (EditText) marksview.findViewById(R.id.edit_sgpa);


            CGPA mark = new CGPA();

            if (!editText_gp.getText().toString().equals("")) {
                mark.setSgpa(editText_gp.getText().toString());
            } else {
                result = false;
//                break;
            }


            markList.add(mark);
        }

        if(markList.size()==0){
            result=false;
            Toast.makeText(this, "Please click on Add+ button to add SGPAs of previous semesters to get CGPA",Toast.LENGTH_SHORT).show();

            gp_results.setVisibility(View.GONE);
        }else if(!result){
            Toast.makeText(this, "Enter all details correctly!",Toast.LENGTH_SHORT).show();

            gp_results.setVisibility(View.GONE);
        }

        return result;
    }

    private void getMyCGPA() {
        int x = markList.size();
        Double sgpa[] = new Double[x + 1];

        for (int i = 0; i < markList.size(); i++) {
            sgpa[i] = Double.parseDouble(markList.get(i).getSgpa());

            sum += sgpa[i];
            Double avg = sum / x;
            avg=round(avg,3);
            String xxx= String.valueOf(avg);
            String yyy= "CGPA: "+xxx;
            gp_results.setText(yyy);
        }
    }


    private void addView() {
        View marksview=getLayoutInflater().inflate(R.layout.cgpa_manage_row,null,false);
        layoutListCgpa.addView(marksview);

        EditText editText = (EditText)marksview.findViewById(R.id.edit_sgpa);
        ImageView imageclose = (ImageView) marksview.findViewById(R.id.image_removecgpa);

        if(marksview.getParent() != null) {
            ((ViewGroup)marksview.getParent()).removeView(marksview); // <- fix
        }

        imageclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeView(marksview);
            }
        });
        layoutListCgpa.addView(marksview);

    }


    private void removeView(View view){
        layoutListCgpa.removeView(view);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mancgpa_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item2:
                Intent intent = new Intent(CgpaActivity.this, CgpaManActivity.class);
                startActivity(intent);
                return true;

            case android.R.id.home:
                onBackPressed();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }


}