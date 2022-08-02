package com.example.gpa.GPA;

import java.io.Serializable;

public class Mark implements Serializable {

    public String course;
    public String gp;
    public String marks;

    public Mark() {
    }

    public Mark(String course, String gp, String marks) {
        this.course = course;
        this.gp = gp;
        this.marks = marks;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getGp() {
        return gp;
    }

    public void setGp(String gp) {
        this.gp = gp;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }
}
