package edu.handong.csee.java.iter1;

import java.util.Calendar;


class Originator {
//    private String state;
    private Calendar date;
   /* lots of memory consumptive private data that is not necessary to define the
    * state and should thus not be saved. Hence the small memento object. */
    public void setState(Calendar date) {
       // System.out.println("Originator: Setting state to " + date);
        this.date = date;
    }

    public Memento save() {
        //System.out.println("Originator: Saving to Memento.");
        return new Memento(date);
    }
    
    public Calendar restore(Memento m) {
        date = m.getState();
        //System.out.println("Originator: State after restoring from Memento: " + date);
        return date;
    }    
}