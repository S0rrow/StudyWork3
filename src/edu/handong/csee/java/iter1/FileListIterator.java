package edu.handong.csee.java.iter1;

import java.io.File;
import java.util.Iterator;

public class FileListIterator implements Iterator{
	private FileList filelist;
	private int index;
	private File file;
	public FileListIterator(FileList filelist) {
		this.filelist=filelist;
		this.index=0;
	}
	
	@Override
	public boolean hasNext() {
		if(index<filelist.getfilecount()) {
			return true;
		}
		else {
			return false;
		}
	}
	

	@Override
	public File next() {
		file = filelist.getFileAt(index);
		index++;
		return file;
	}
	
}