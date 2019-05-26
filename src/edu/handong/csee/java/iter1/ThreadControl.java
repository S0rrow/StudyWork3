package edu.handong.csee.java.iter1;

import java.util.Calendar;

import javax.swing.JLabel;

public class ThreadControl extends Thread {

	CalendarDataManager data;
	private JLabel infoClock;
	private JLabel bottomInfo;
	ThreadControl(CalendarDataManager cdm, JLabel infoClock, JLabel bottomInfo){
		super();
		data = cdm;
		this.infoClock = infoClock;
		this.bottomInfo = bottomInfo;
	}
	public void run() {
		boolean msgCntFlag = false;
		int num = 0;
		String curStr = new String();
		while (true) {
			try {
				data.today = Calendar.getInstance();
				String amPm = (data.today.get(Calendar.AM_PM) == 0 ? "AM" : "PM");
				String hour;
				if (data.today.get(Calendar.HOUR) == 0)
					hour = "12";
				else if (data.today.get(Calendar.HOUR) == 12)
					hour = " 0";
				else
					hour = (data.today.get(Calendar.HOUR) < 10 ? " " : "") + data.today.get(Calendar.HOUR);
				String min = (data.today.get(Calendar.MINUTE) < 10 ? "0" : "") + data.today.get(Calendar.MINUTE);
				String sec = (data.today.get(Calendar.SECOND) < 10 ? "0" : "") + data.today.get(Calendar.SECOND);
				infoClock.setText(amPm + " " + hour + ":" + min + ":" + sec);

				sleep(1000);
				String infoStr = bottomInfo.getText();

				if (infoStr != " " && (msgCntFlag == false || curStr != infoStr)) {
					num = 5;
					msgCntFlag = true;
					curStr = infoStr;
				} else if (infoStr != " " && msgCntFlag == true) {
					if (num > 0)
						num--;
					else {
						msgCntFlag = false;
						bottomInfo.setText(" ");
					}
				}
			} catch (InterruptedException e) {
				System.out.println("Thread:Error");
			}
		}
	}
}
