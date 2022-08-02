package com.example.gpa.GPA;

import java.io.Serializable;

public class CGPA implements Serializable {
    public String sgpa;

    public CGPA() {
    }

    public CGPA(String sgpa) {
        this.sgpa = sgpa;
    }

    public String getSgpa() {
        return sgpa;
    }

    public void setSgpa(String sgpa) {
        this.sgpa = sgpa;
    }
}
