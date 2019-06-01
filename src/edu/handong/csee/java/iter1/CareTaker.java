package edu.handong.csee.java.iter1;

import java.util.ArrayList;
 
public class CareTaker {
    private int i = -1;
	private ArrayList<Memento> mementos = new ArrayList<>();
	
    public void addMemento(Memento m) {
        mementos.add(m);
        i++;
    }
    
    public void removeMemento() {
    	mementos.remove(i);
    	i--;
    }
    public Memento getMemento() {
    		return mementos.get(i);
    }
}