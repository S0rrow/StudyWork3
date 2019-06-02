package edu.handong.csee.java.iter1;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class CalendarPanelInverse extends CalendarPanelInstance {

	public static final Color BUTTON_COLOR = Color.black;
	public static final Color BUTTON_TEXT_COLOR = Color.white;

	public CalendarPanelInverse(String userName, Connectivity mainConnection, CalendarDataManager cdm, JFrame frame,Reactor ed) {
		super(userName, mainConnection, cdm, frame,ed);
		curTheme = "inverse";
		themeBut.setText("default");
		for (int i = 0; i < CalendarDataManager.CAL_HEIGHT; i++) {
			for (int j = 0; j < CalendarDataManager.CAL_WIDTH; j++) {
				//super.dateButs[i][j].setForeground(BUTTON_TEXT_COLOR);
				super.dateButs[i][j].setBackground(BUTTON_COLOR);
			}
		}
	}
	
	@Override
	public void showCal() {
		String fontColor = "white";
		String path_date;
		for (int i = 0; i < CalendarDataManager.CAL_HEIGHT; i++) {
			for (int j = 0; j < CalendarDataManager.CAL_WIDTH; j++) {
				if (j == 0)
					fontColor = "red";
				else if (j == 6)
					fontColor = "blue";
				else fontColor = "white";
				data.setFile();
				path_date = "ListData/" + username + "/" + data.calYear + ((data.calMonth + 1) < 10 ? "0" : "") + (data.calMonth + 1) + (data.calDayOfMon < 10 ? "0" : "")+data.calDates[i][j];
				//System.out.println(path_date);
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

}
