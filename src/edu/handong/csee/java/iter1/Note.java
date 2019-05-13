package edu.handong.csee.java.iter1;

public interface Note {
	
	abstract public String load(String filepath);
	abstract public String[] parse(String filepath);
	abstract public void callback();
}
