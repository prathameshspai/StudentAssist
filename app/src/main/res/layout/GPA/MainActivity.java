package com.example.gpa.GPA;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.gpa.R;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    LinearLayout layoutList;
    Button buttonAdd;
    Button buttonSubmitList;
    CardView but_manageGPA;
    EditText totmarks;
    TextView gp_results, gp_mg_txt;
    Double thistwo;
    ArrayList<Mark> markList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
//        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        layoutList = findViewById(R.id.layout_list);
        buttonAdd = findViewById(R.id.button_add);
        but_manageGPA = findViewById(R.id.button_manageGPA);
        buttonSubmitList = findViewById(R.id.button_submit_list);
        totmarks = findViewById(R.id.totalmarks);
        gp_mg_txt = findViewById(R.id.gpa_txt_edit);

        gp_results = findViewById(R.id.gp_result);
        buttonAdd.setOnClickListener(v -> addView());

//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        getWindow().setStatusBarColor(Color.TRANSPARENT);
//        android.graphics.drawable.Drawable background = MainActivity.this.getResources().getDrawable(R.drawable.bg);
//        getWindow().setBackgroundDrawable(background);

        buttonSubmitList.setOnClickListener(v -> {
            if (checkIfValidAndRead()) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(layoutList.getWindowToken(), 0);


                if (!totmarks.getText().toString().equals("")) {
//                        Toast.makeText(MainActivity.this,"Good!",Toast.LENGTH_SHORT).show();
                    Double kk = getMyGPA();

                    resultToast(kk);
                } else {
                    Toast.makeText(MainActivity.this, "Enter total marks", Toast.LENGTH_SHORT).show();
                }
            }

        });

        but_manageGPA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkIfValidAndRead()) {
                    if (!totmarks.getText().toString().equals("")) {
//                        Toast.makeText(MainActivity.this,"Good!",Toast.LENGTH_SHORT).show();
                        Double kk = getMyGPA();

                        Intent intent = new Intent(MainActivity.this, GpaManageActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("list", markList);
                        bundle.putString("prevtot", totmarks.getText().toString());
                        bundle.putString("heregpa", thistwo.toString());
                        intent.putExtras(bundle);
                        startActivity(intent);

                    } else {
                        Toast.makeText(MainActivity.this, "Enter total marks", Toast.LENGTH_SHORT).show();
                    }
                } else {
                }


            }
        });
    }

    private double getMyGPA() {
        int x = markList.size();
        String y = String.valueOf(x);
//        gp_results.setText(y);

        Double gps = 0.0, gs = 0.0, gpa;
        double m[] = new double[x + 1];
        double a[] = new double[x + 1];
        double s[] = new double[x + 1];
        int gp[] = new int[x + 1];
        String sub[] = new String[x + 1];
        Double max = Double.parseDouble(totmarks.getText().toString());

        for (int i = 0; i < markList.size(); i++) {
            sub[i] = markList.get(i).getCourse();
            a[i] = Double.parseDouble(markList.get(i).getGp());
            m[i] = Double.parseDouble(markList.get(i).getMarks());

            s[i] = m[i] * 100 / max;

            if (s[i] <= 100 && s[i] >= 90)
                gp[i] = 10;
            if (s[i] < 90 && s[i] >= 80)
                gp[i] = 9;
            if (s[i] < 80 && s[i] >= 70)
                gp[i] = 8;
            if (s[i] < 70 && s[i] >= 60)
                gp[i] = 7;
            if (s[i] < 60 && s[i] >= 50)
                gp[i] = 6;
            if (s[i] < 50 && s[i] >= 40)
                gp[i] = 4;
            if (s[i] < 40 && s[i] >= 0)
                gp[i] = 0;

            gps += (gp[i] * a[i]);
        }

        for (int i = 0; i < markList.size(); i++)
            gs = gs + a[i];
        gpa = gps / gs;

        gp_results.setVisibility(View.VISIBLE);
        but_manageGPA.setVisibility(View.VISIBLE);

        thistwo = round(gpa, 3);
        String thisone = "Grade Point Average (GPA): " + thistwo.toString();
        String thisone1= "Obtain the scores to be obtained in the next exam to maintain " + thistwo.toString()+ " GPA!";
        gp_results.setText(thisone);
        gp_mg_txt.setText(thisone1);
        return thistwo;

    }

    private boolean checkIfValidAndRead() {
        markList.clear();
        boolean result = true;
        for (int i = 0; i < layoutList.getChildCount(); i++) {
            View marksview = layoutList.getChildAt(i);
            EditText editText_course = (EditText) marksview.findViewById(R.id.edit_course);
            EditText editText_gp = (EditText) marksview.findViewById(R.id.edit_gp);
            EditText editText_marks = (EditText) marksview.findViewById(R.id.edit_marks);

            Mark mark = new Mark();

            if (!editText_course.getText().toString().equals("")) {
                mark.setCourse(editText_course.getText().toString());
            } else {
                result = false;
//                break;
            }

            if (!editText_gp.getText().toString().equals("")) {
                mark.setGp(editText_gp.getText().toString());
            } else {
                result = false;
//                break;
            }

            if (!editText_marks.getText().toString().equals("")) {
                mark.setMarks(editText_marks.getText().toString());
            } else {
                result = false;
//                break;
            }
            markList.add(mark);
        }

        if (markList.size() == 0) {
            result = false;
            Toast.makeText(this, "Please add courses", Toast.LENGTH_SHORT).show();
            but_manageGPA.setVisibility(View.GONE);
            gp_results.setVisibility(View.GONE);
        } else if (!result) {
            Toast.makeText(this, "Enter all details correctly!", Toast.LENGTH_SHORT).show();
            but_manageGPA.setVisibility(View.GONE);
            gp_results.setVisibility(View.GONE);
        }

        return result;
    }

    private void addView() {
        View marksview = getLayoutInflater().inflate(R.layout.row_add_marks, null, false);
        layoutList.addView(marksview);
        EditText editText = (EditText) marksview.findViewById(R.id.edit_course);
        EditText editText1 = (EditText) marksview.findViewById(R.id.edit_gp);
        EditText editText2 = (EditText) marksview.findViewById(R.id.edit_marks);
        ImageView imageclose = (ImageView) marksview.findViewById(R.id.image_remove);


        if (marksview.getParent() != null) {
            ((ViewGroup) marksview.getParent()).removeView(marksview); // <- fix
        }

        imageclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeView(marksview);
            }
        });
        layoutList.addView(marksview);


    }

    private void removeView(View view) {
        layoutList.removeView(view);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }


    private void resultToast(Double gpax) {
        if (gpax >= 9.3 && gpax <= 10.0)
            Toast.makeText(MainActivity.this, "Awesome score, congrats!", Toast.LENGTH_SHORT).show();
        if (gpax >= 8.5 && gpax < 9.3)
            Toast.makeText(MainActivity.this, "Great, Keep it up!", Toast.LENGTH_SHORT).show();
        if (gpax >= 8.25 && gpax < 8.5)
            Toast.makeText(MainActivity.this, "Well done!", Toast.LENGTH_SHORT).show();
        if (gpax >= 7.25 && gpax < 8.25)
            Toast.makeText(MainActivity.this, "Nice, can do better!", Toast.LENGTH_SHORT).show();
        if (gpax >= 5 && gpax < 7.25)
            Toast.makeText(MainActivity.this, "Fair!", Toast.LENGTH_SHORT).show();
        if (gpax >= 4.0 && gpax < 5)
            Toast.makeText(MainActivity.this, "Passed the exam!", Toast.LENGTH_SHORT).show();
        if (gpax < 4.0)
            Toast.makeText(MainActivity.this, "Failed!", Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.calc_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Intent intent = new Intent(MainActivity.this, CgpaActivity.class);
                startActivity(intent);
                return true;

            case android.R.id.home:
                onBackPressed();
                return true;
        }

            return super.onOptionsItemSelected(item);

    }
}

