package edu.handong.csee.java.iter1;

import java.io.File;
import java.util.Iterator;

public class FileList implements Aggregate{
	private File[] files;
	private int last =0;
	public FileList(File[] filelist) {
		this.files=filelist;
	}
	public FileList(int maxsize) {
		this.files = new File[maxsize];
	}
	public File getFileAt(int index) {
		return files[index];
	}
	public void appendFile(File file) {
		this.files[last]= file;
		last++;
	}
	public int getfilecount() {
		return last;
	}
	public File[] getFileList() {
		return files;
	}
	@Override
	public Iterator iterator() {
		// TODO Auto-generated method stub
		return new FileListIterator(this);
	}
	
}


