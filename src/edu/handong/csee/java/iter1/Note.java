package edu.handong.csee.java.iter1;

import javax.swing.JFrame;

public interface Note {
	
	abstract public String load(String filepath);
	abstract public String[] parse(String filepath);
	abstract public void callback(JFrame scheduler);
}
