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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gpa.R;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class GpaManageActivity extends AppCompatActivity {

    LinearLayout layoutList_next;
    private EditText totMarksnext;
    private Button submitnext;
    private Button see_but;
    private TextView course_next;
    private TextView credit_next;
    private TextView score_next;
    private View markview;
    private TextView loss_next;
    private TextView documentation;
    private TextView textdet;
    private TextView gp_res;
    Double credsum;
    Double avg;
    private Spinner spinner;
    private TextView gpres_next;
    private String prevtot;
    String check_gpa;
    private int g=0;
    Double sum;
    private int jk=0;
    ArrayList<Mark> marksList = new ArrayList<>();
    List<String> gpList = new ArrayList<>();
    private int kj=1;

    List<String> checking = new ArrayList<>();
    List<String> spinlastlist = new ArrayList<>();
    List<String> subjectlist = new ArrayList<>();
    List<Double> creditlist = new ArrayList<>();
    List<Double> marlist = new ArrayList<>();
    List<Double> losslist = new ArrayList<>();
    ArrayList<Mark2> mark2List=new ArrayList<>();
    String prevgpa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpa_manage);
        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg));
        getSupportActionBar().setTitle(Html.fromHtml("<small>GPA Manager</small>"));
        getSupportActionBar().setSubtitle(Html.fromHtml("<small>Helps in maintaining or improving your GPA</small>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        android.graphics.drawable.Drawable background = GpaManageActivity.this.getResources().getDrawable(R.drawable.bg);
        getWindow().setBackgroundDrawable(background);

        marksList = (ArrayList<Mark>) getIntent().getExtras().getSerializable("list");
        prevtot =  getIntent().getExtras().getString("prevtot");
        prevgpa = getIntent().getExtras().getString("heregpa");
        gpres_next=findViewById(R.id.gp_result_next);
        documentation=findViewById(R.id.documentation);

        textdet=(TextView)findViewById(R.id.textdet) ;
        layoutList_next=findViewById(R.id.layout_list_next);

        checking.add(0,"");
        checking.add(1,"");

        totMarksnext=(EditText)findViewById(R.id.totalmarks_next) ;
        String abcd=totMarksnext.getText().toString();
        submitnext=findViewById(R.id.button_submit_next);
        submitnext.setOnClickListener(v -> {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(layoutList_next.getWindowToken(), 0);
                if(!totMarksnext.getText().toString().equals("")) {

                        checking.add(jk,totMarksnext.getText().toString());
                        jk++;
                        if(jk==1)
                            jk=0;
                documentation.setVisibility(View.VISIBLE);
                        sum=0.0;
                        credsum=0.0;
                    int count = layoutList_next.getChildCount();
                    for(int i=0;i<count;i++) {
                        LinearLayout l = (LinearLayout) layoutList_next.getChildAt(i);
                        FrameLayout ed = (FrameLayout) l.getChildAt(4);
                        Spinner edd = (Spinner) ed.getChildAt(0);
                        spinlastlist.add((String) edd.getSelectedItem());
                    }
//                        Toast.makeText(MainActivity.this,"Good!",Toast.LENGTH_SHORT).show();
                        layoutList_next.removeAllViews();
                        textdet.setVisibility(View.VISIBLE);

                            if (checking.get(0).equals(checking.get(1))) {
                                checking.add(1, "");

                                layoutList_next.removeAllViews();
                                correctGPA(spinlastlist);
                                spinlastlist.removeAll(spinlastlist);//sethere
                            }

                            else if(checking.get(1).equals("")) {
                                layoutList_next.removeAllViews();
                                getMyGPA();
                            }
                            else {
                                layoutList_next.removeAllViews();
                                getMyGPA();
                            }

                }
                else{
                    Toast.makeText(GpaManageActivity.this,"Enter total marks",Toast.LENGTH_SHORT).show();
                }
        });


        gpList.add("4");
        gpList.add("6");
        gpList.add("7");
        gpList.add("8");
        gpList.add("9");
        gpList.add("10");
        gpList.add("0");
    }

    private void getMyGPA() {
        int x=marksList.size();
        String y=String.valueOf(x);

//        gpres_next.setText(y);
//        gp_results.setText(y);

        Double gps=0.0,gs=0.0,gpa;
        int poss[]=new int[x+1];
        int poss2[]=new int[x+1];
        double m[]=new double[x+1];
        double a[]=new double[x+1];
        double s[]=new double[x+1];
        double per[]=new double[x+1];
        double losses[]=new double[x+1];
        int gp[]=new int[x+1];
        String sub[]=new String[x+1];
        Double next_marks[]=new Double[x+1];
        Double max=Double.parseDouble(totMarksnext.getText().toString());

        for(int i=0;i<marksList.size();i++) {
            sub[i] = marksList.get(i).getCourse();
            a[i] = Double.parseDouble(marksList.get(i).getGp());
            m[i] = Double.parseDouble(marksList.get(i).getMarks());

            s[i] = m[i] * 100 / Double.parseDouble(prevtot);

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


            if (gp[i] == 10) {
                per[i] = 90;
                poss[i] = 5;
            }
            if (gp[i] == 9) {
                per[i] = 80;
                poss[i] = 4;
            }
            if (gp[i] == 8) {
                per[i] = 70;
                poss[i] = 3;
            }
            if (gp[i] == 7) {
                per[i] = 60;
                poss[i] = 2;
            }
            if (gp[i] == 6) {
                per[i] = 50;
                poss[i] = 1;
            }
            if (gp[i] == 4) {
                per[i] = 40;
                poss[i] = 0;
            }
            if (gp[i] < 4){
                per[i] = 40;
            poss[i] = 0;
        }


            next_marks[i]=per[i]*(Double.parseDouble(prevtot)+max)/100 - m[i];
            losses[i] = max-next_marks[i];

            next_marks[i]=round(next_marks[i],1);
            losses[i]=round(losses[i],1);

            addView(sub[i], marksList.get(i).getGp(),next_marks[i],losses[i],poss[i]);

            poss2[i]=poss[i];
            g++;
            gps+=(gp[i]*a[i]);

            String thison="Grade point average (GPA): " + prevgpa;
             check_gpa = prevgpa;
            gpres_next.setVisibility(View.VISIBLE);
            gpres_next.setText(thison);



//            if(spinner.getSelectedItemPosition()!=poss[i]){
//                layoutList_next.getChildAt(0).gette;
//            }
        }

    }

    public void correctGPA(List<String> spinlastlist){
        int x=mark2List.size();
        int gpn[]=new int[x+1];
        int posna[]=new int[x+1];
        Double max=Double.parseDouble(totMarksnext.getText().toString());
        double ma[]=new double[x+1];
        double next_marksn[]=new double[x+1];
        double pern[]=new double[x+1];
        double ai[]=new double[x+1];
        double lossesn[]=new double[x+1];
     //   double s[]=new double[x+1];
     //   double per[]=new double[x+1];
     //   double losses[]=new double[x+1];
     //   int gp[]=new int[x+1];
        String subj[]=new String[x+1];

        for(int i=0;i<marksList.size();i++){
            gpn[i]=Integer.parseInt(spinlastlist.get(i));
            ma[i]=Double.parseDouble(marksList.get(i).getMarks());
            subj[i]=mark2List.get(i).getCoursename();
            ai[i]=Double.parseDouble(mark2List.get(i).getCreditno());//CREDIT

             sum += gpn[i]*ai[i];
             credsum+=ai[i];
             avg= sum/credsum;
             avg=round(avg,2);

            String hgj= String.valueOf(avg);
            String laston= "New Grade point average (GPA): " + hgj;
            gpres_next.setText(laston);








            if (gpn[i] == 10) {
                pern[i] = 90;
            }
            if (gpn[i] == 9) {
                pern[i] = 80;

            }
            if (gpn[i] == 8) {
                pern[i] = 70;

            }
            if (gpn[i] == 7) {
                pern[i] = 60;

            }
            if (gpn[i] == 6) {
                pern[i] = 50;

            }
            if (gpn[i] == 4) {
                pern[i] = 40;

            }
            if (gpn[i] < 4){
                pern[i] = 40;

            }

            next_marksn[i]=pern[i]*(Double.parseDouble(prevtot)+max)/100 - ma[i];
            lossesn[i] = max-next_marksn[i];

            if(gpn[i]==4)
                posna[i]=0;
            if(gpn[i]==6)
                posna[i]=1;
            if(gpn[i]==7)
                posna[i]=2;
            if(gpn[i]==8)
                posna[i]=3;
            if(gpn[i]==9)
                posna[i]=4;
            if(gpn[i]==10)
                posna[i]=5;
            if(gpn[i]==0)
                posna[i]=6;


            next_marksn[i]=round(next_marksn[i],2);
            lossesn[i]=round(lossesn[i],2);
            addView(subj[i],String.valueOf(ai[i]),next_marksn[i],lossesn[i],posna[i]);



        }
    }


    private void addView(String s, String gp, Double next_mark,Double losses, int poss){
        markview=getLayoutInflater().inflate(R.layout.gp_manage_row,null,false);
        layoutList_next.addView(markview);

        course_next = (TextView) markview.findViewById(R.id.text_course);
        credit_next = (TextView) markview.findViewById(R.id.text_credits);
        score_next = (TextView) markview.findViewById(R.id.text_nextscore);
        loss_next = (TextView) markview.findViewById(R.id.loss);
        spinner= (Spinner)markview.findViewById(R.id.spinner_gp);

        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,gpList);
        spinner.setAdapter(arrayAdapter);
        spinner.animate();
        spinner.setDropDownWidth(235);
        spinner.setPopupBackgroundResource(R.drawable.round_grey);

        course_next.setText(s);
        credit_next.setText(gp);
        String mark_next=next_mark.toString();
        score_next.setText(mark_next);

        String losss= losses.toString();
        loss_next.setText(losss);

        if(losses<0){
            loss_next.setText("NP");
            score_next.setText("NP");
        }

        if(next_mark<0){
            loss_next.setText("NP");
            score_next.setText("NP");
        }
        spinner.setSelection(poss);

        int a=Integer.parseInt((String) spinner.getSelectedItem());


        Mark2 mark2 = new Mark2();
        mark2.setCoursename(course_next.getText().toString());
        mark2.setCreditno(credit_next.getText().toString());
        mark2.setMarksno(score_next.getText().toString());
        mark2.setLossno(loss_next.getText().toString());
        mark2.setGpno(a);
        mark2List.add(mark2);


        if(markview.getParent() != null) {
            ((ViewGroup)markview.getParent()).removeView(markview); // <- fix
        }


        layoutList_next.addView(markview);
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);

    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}