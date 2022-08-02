package com.example.gpa.GPA;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
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

public class CgpaManActivity extends AppCompatActivity {

    LinearLayout layoutListman;
    Button buttonAddMan;
    Button buttonSubmitMan;
    Double sum;
    EditText reqcgpa;
    EditText remsem;
    TextView gp_results;
    int pyq;
    TextView edit_sgpaa;
    ArrayList<CGPA> markList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cgpa_man);
        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg));
        getSupportActionBar().setTitle(Html.fromHtml("<small>CGPA Manager</small>"));
        getSupportActionBar().setSubtitle(Html.fromHtml("<small>Finds the minimum SGPA to get the desired CGPA</small>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        android.graphics.drawable.Drawable background = CgpaManActivity.this.getResources().getDrawable(R.drawable.bg);
        getWindow().setBackgroundDrawable(background);

        layoutListman=findViewById(R.id.layout_list_man);
        buttonAddMan= findViewById(R.id.button_add);
        buttonSubmitMan=findViewById(R.id.button_submit_list_man);
        gp_results=findViewById(R.id.gp_result);

        reqcgpa=findViewById(R.id.reqCgpa);
        remsem=findViewById(R.id.remsem);

        buttonAddMan.setOnClickListener(v -> addView());
        buttonSubmitMan.setOnClickListener(v -> {
            if(!remsem.getText().toString().equals("") || !reqcgpa.getText().toString().equals("") ) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(layoutListman.getWindowToken(), 0);
                if (checkIfValidAndRead()) {
                    sum = 0.0;
//                        Toast.makeText(MainActivity.this,"Good!",Toast.LENGTH_SHORT).show();
                    gp_results.setVisibility(View.VISIBLE);
                    getMyCGPA();

                }
            }
            else
            {
                Toast.makeText(CgpaManActivity.this,"Enter the details!",Toast.LENGTH_SHORT).show();
            }

        });

    }

    private void getMyCGPA() {
        int x = markList.size();
        Double sgpa[] = new Double[x + 1];

        for (int i = 0; i < markList.size(); i++) {
            sgpa[i] = Double.parseDouble(markList.get(i).getSgpa());
            sum += sgpa[i];
        }

        Double xxx= (Double.parseDouble(reqcgpa.getText().toString()) * ((Double.parseDouble(remsem.getText().toString()))+markList.size()) - sum)/(Double.parseDouble(remsem.getText().toString()));
        xxx= round(xxx,2);

        String yyy=String.valueOf(xxx);
        String qqq=(String.valueOf(Integer.parseInt(remsem.getText().toString())));
        String ggg="Score atleast "+yyy+" SGPA in all "+qqq+" semesters";
        gp_results.setText(ggg);

    }

    private void addView() {
        View marksview=getLayoutInflater().inflate(R.layout.cgpa_manage_row,null,false);
        layoutListman.addView(marksview);

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
        layoutListman.addView(marksview);

    }

    private void removeView(View view){
        layoutListman.removeView(view);
    }

    private boolean checkIfValidAndRead() {
        markList.clear();
        boolean result = true;
        for (int i = 0; i < layoutListman.getChildCount(); i++) {
            View marksview = layoutListman.getChildAt(i);

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
            Toast.makeText(this, "Please click on Add+ button to add SGPAs of previous semesters",Toast.LENGTH_SHORT).show();

            gp_results.setVisibility(View.GONE);
        }else if(!result){
            Toast.makeText(this, "Enter all details correctly!",Toast.LENGTH_SHORT).show();

            gp_results.setVisibility(View.GONE);
        }

        return result;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){


            case android.R.id.home:
                onBackPressed();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

//    'John' ,'B' ,'Smith', 123456789,  '731 Fondren, Houston, TX' ,'M' ,30000 ,333445555 ,5, '1965-01-09'
//    'Franklin', 'T' ,'Wong', 333445555,  '638 Voss, Houston, TX' ,'M' ,40000 ,888665555 ,5, '1955-12-08'
//    'Alicia' ,'J' ,'Zelaya', 999887777, '3321 Castle, Spring, TX' , 'F'  ,25000 ,987654321 ,4 ,'1968-01-19'
//    'Jennifer', 'S' ,'Wallace', 987664321,  '291 Berry, Bellaire, TX', 'F' ,43000 ,888665555 ,4 ,'1941-05-20'
//    'Ramesh' ,'K' ,'Narayan', 666884444, '975 Firs Oak, Humble, TX', 'M' ,38000, 333445555 ,5, '1962-09-15'
//    'Joyce' ,'A' ,'English', 453453453,'5631 Rice, Houston, TX', 'F' ,25000 ,333445555 ,5 ,'1972-07-31'
//    'Ahmed' ,'V' ,'Jabber', 987907987,  '980 Dallas, Houston, TX' ,'M' ,25000 ,987654321 ,4 ,'1964-03-29'
//    'James' ,'E' ,'Borg', 888655555,  '450 Stone, Houston, TX', 'M', 55000 ,null, 1, '1937-11-10'
//
//
//    'Research', 5, 333445666, '19198-05-22'
//    'Administration', 4, 987854321 ,'1936-01-01'
//    'Headquarters', 1, 888866566, '1981-08-19'
//
//    'ProductX', 1, 'Bellaire' ,5
//    'ProductY' ,2, 'Sugarland' ,5
//    'ProductZ' ,3, 'Houston' ,5
//    'Computerization', 10, 'Stafford' ,4
//    'Reorganization' ,20, 'Houston' ,1
//    'Newbenefits' ,30, 'Stafford' ,4
//
//333445555, 'Alice', 'F' ,'1986-04-05' ,'DAUGHTER'
//333445555 ,'Theodore', 'M' ,'1983-10-25', 'SON'
//333445555, 'Joy', 'F' ,'1958-05-03', 'SPOUSE'
//987654321, 'Abner', 'M' ,'1942-02-28' ,'SPOUSE'
//123456789, 'Michael', 'M' ,'1988-01-04' ,'SON'
//123456789, 'Alice', 'F' ,'1988-12-30', 'DAUGHTER'
//123456789, 'Elizabeth', 'F' ,'1967-05-05', 'SPOUSE'


}