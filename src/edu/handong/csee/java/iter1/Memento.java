package edu.handong.csee.java.iter1;

import java.util.Calendar;

class Memento {
//    private String state;
    private Calendar date;
    
    public Memento(Calendar date) {
        this.date = date;
    }

    public Calendar getState() {
        return date;
    }
}