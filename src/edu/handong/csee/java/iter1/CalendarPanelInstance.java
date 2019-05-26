package edu.handong.csee.java.iter1;

import java.awt.Font;
import java.io.File;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class CalendarPanelInstance extends CalendarPanel {

	CalendarPanelInstance(String userName, Connectivity mainConnection, CalendarDataManager cdm, JFrame frame) {
		super(userName, mainConnection, cdm, frame);
	}
	public void readSchedule() {
		data.setFile();
		
		String Path ="ListData/" + username + "/" + data.curDate;
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
				    System.out.println(tempFileName.substring(tempFileName.length()-4, tempFileName.length()));
				    if(!tempFileName.substring(tempFileName.length()-4, tempFileName.length()).equals(".txt")) {
				    	continue;
				    }
				  
				   // BufferedReader in = new BufferedReader(new FileReader(tempFile+"/"+tempFileName));
				  //  System.out.println("Path="+tempPath);
				    //System.out.println("FileName="+tempFileName);
				    String arr[]= new String[1];
				    arr[0]=tempFileName.substring(0,tempFileName.length()-4);
				    Scheduler.model.addRow(arr);
				  
				    /*** Do something withd tempPath and temp FileName ^^; ***/
				  }
				}
		}
	}
	
	public void showCal() {
		String path_date;
		for (int i = 0; i < CalendarDataManager.CAL_HEIGHT; i++) {
			for (int j = 0; j < CalendarDataManager.CAL_WIDTH; j++) {
				String fontColor = "black";
				if (j == 0)
					fontColor = "red";
				else if (j == 6)
					fontColor = "blue";
				data.setFile();
				path_date = "ListData/" + username + "/" + data.curDate;
				File f = new File(path_date);
				if (f.exists() && f.listFiles().length>0) {
					dateButs[i][j].setFont(
							dateButs[i][j].getFont().deriveFont(dateButs[i][j].getFont().getStyle() | Font.BOLD));
					dateButs[i][j].setText(
							"<html><b><font color=" + fontColor + ">" + data.calDates[i][j] + "</font></b></html>");
				} else {
					dateButs[i][j].setFont(
							dateButs[i][j].getFont().deriveFont(dateButs[i][j].getFont().getStyle() & ~Font.BOLD));
					dateButs[i][j]
							.setText("<html><font color=" + fontColor + ">" + data.calDates[i][j] + "</font></html>");
				}
				JLabel todayMark = new JLabel("<html><font color=green>*</html>");
				dateButs[i][j].removeAll();
				if (data.calMonth == data.today.get(Calendar.MONTH) && data.calYear == data.today.get(Calendar.YEAR)
						&& data.calDates[i][j] == data.today.get(Calendar.DAY_OF_MONTH)) {
					dateButs[i][j].add(todayMark);
					dateButs[i][j].setToolTipText("Today");
				}

				if (data.calDates[i][j] == 0)
					dateButs[i][j].setVisible(false);
				else
					dateButs[i][j].setVisible(true);
			}
		}
	}

	public void focusToday() {
		if (data.today.get(Calendar.DAY_OF_WEEK) == 1)
			dateButs[data.today.get(Calendar.WEEK_OF_MONTH)][data.today.get(Calendar.DAY_OF_WEEK) - 1]
					.requestFocusInWindow();
		else
			dateButs[data.today.get(Calendar.WEEK_OF_MONTH) - 1][data.today.get(Calendar.DAY_OF_WEEK) - 1]
					.requestFocusInWindow();
	}
}
