package com.example.roomimplement.GPA;

public class Mark2 {
    public String coursename;
    public String creditno;
    public String marksno;
    public String lossno;
    public int gpno;

    public Mark2() {
    }

    public Mark2(String coursename, String creditno, String marksno, String lossno, int gpno) {
        this.coursename = coursename;
        this.creditno = creditno;
        this.marksno = marksno;
        this.lossno = lossno;
        this.gpno = gpno;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public String getCreditno() {
        return creditno;
    }

    public void setCreditno(String creditno) {
        this.creditno = creditno;
    }

    public String getMarksno() {
        return marksno;
    }

    public void setMarksno(String marksno) {
        this.marksno = marksno;
    }

    public String getLossno() {
        return lossno;
    }

    public void setLossno(String lossno) {
        this.lossno = lossno;
    }

    public int getGpno() {
        return gpno;
    }

    public void setGpno(int gpno) {
        this.gpno = gpno;
    }
}
