package edu.handong.csee.java.iter1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;

public class CalendarPanelAddition extends CalendarPanel {

	CalendarPanelAddition(String userName, Connectivity mainConnection, CalendarDataManager cdm, JFrame frame) {
		super(userName, mainConnection, cdm, frame);
		// TODO Auto-generated constructor stub
	}

	public void readSchedule() {
		//try {
			
			data.setFile();
			
			String Path ="ListData/" + username + "/" + data.calYear + ((data.calMonth + 1) < 10 ? "0" : "") + (data.calMonth + 1)
				       + (data.calDayOfMon < 10 ? "0" : "") +data.calDayOfMon;
			File f = new File(Path);
			//System.out.println(Path);
			Scheduler.model.setNumRows(0);
			if (f.exists()) {
				//data.setFile();
				//BufferedReader in = new BufferedReader(new FileReader(Path));
				File []fileList=f.listFiles();
				for(File tempFile : fileList) {
					  if(tempFile.isFile()) {
					    //String tempPath=tempFile.getParent();
					    String tempFileName=tempFile.getName();
					   // BufferedReader in = new BufferedReader(new FileReader(tempFile+"/"+tempFileName));
					  //  System.out.println("Path="+tempPath);
					    //System.out.println("FileName="+tempFileName);
					    String arr[]= new String[1];
					    arr[0]=tempFileName;
					    Scheduler.model.addRow(arr);
					    
					    /*** Do something withd tempPath and temp FileName ^^; ***/
					  }
					}
			}
				
					
				
				//String ListName1 = new String();
/*
				while (true) {
					String tempStr = in.readLine();
					if (tempStr == null)
						break;

					ListName1 = ListName1 + tempStr + System.getProperty("line.separator");
					System.out.println("ListName1 = "+ListName1);
				}
				in.close();
				String arr[] = new String[1];
				arr[0] = ListName1;
				Scheduler.model.addRow(arr);
				*/
			//} else {
/*
				int rows = Scheduler.model.getRowCount();
				for (int i = 0; i < rows; i++)
					Scheduler.model.removeRow(i);
*/
		//	}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

	}

}
