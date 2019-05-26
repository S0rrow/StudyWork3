package edu.handong.csee.java.iter1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.*;

public abstract class CalendarPanel { 
	public String username;
	Connectivity connection;
	CalendarDataManager data;

	JPanel calPanel;
	JButton weekDaysName[];
	JButton dateButs[][] = new JButton[6][7];

	JPanel calOpPanel;
	JButton todayBut;
	JButton userInfo;
	JButton logout;
	JLabel todayLab;
	JButton lYearBut;
	JButton lMonBut;
	JLabel curMMYYYYLab;
	JButton nMonBut;
	JButton nYearBut;
	UserListener ul = new UserListener();
	ListenForCalOpButtons lForCalOpButtons = new ListenForCalOpButtons();
	listenForDateButs lForDateButs = new listenForDateButs();

	public JPanel frameSubPanelWest;
	
	JFrame mainFrame;
	JTextArea memoArea;
	JLabel selectedDate;

	CalendarPanel(String userName, Connectivity mainConnection, CalendarDataManager cdm, JFrame frame) {
		super();
		mainFrame = frame;
		username = userName;
		connection = mainConnection;
		data = cdm;
		calOpPanel = new JPanel();
		todayBut = new JButton("Today");
		todayBut.setToolTipText("Today");
		todayBut.addActionListener(lForCalOpButtons);
		todayLab = new JLabel(data.today.get(Calendar.MONTH) + 1 + "/" + data.today.get(Calendar.DAY_OF_MONTH) + "/"
				+ data.today.get(Calendar.YEAR));
		userInfo = new JButton(username);
		userInfo.setToolTipText("Open account info");
		userInfo.addActionListener(ul);
		logout = new JButton("log out");
		logout.setToolTipText("Log out to first frame");
		logout.addActionListener(ul);
		lYearBut = new JButton("<<");
		lYearBut.setToolTipText("Previous Year");
		lYearBut.addActionListener(lForCalOpButtons);
		lMonBut = new JButton("<");
		lMonBut.setToolTipText("Previous Month");
		lMonBut.addActionListener(lForCalOpButtons);
		curMMYYYYLab = new JLabel(
				"<html><table width=100><tr><th><font size=5>" + ((data.calMonth + 1) < 10 ? "&nbsp;" : "")
						+ (data.calMonth + 1) + " / " + data.calYear + "</th></tr></table></html>");
		nMonBut = new JButton(">");
		nMonBut.setToolTipText("Next Month");
		nMonBut.addActionListener(lForCalOpButtons);
		nYearBut = new JButton(">>");
		nYearBut.setToolTipText("Next Year");
		nYearBut.addActionListener(lForCalOpButtons);
		calOpPanel.setLayout(new GridBagLayout());

		selectedDate = new JLabel("<Html><font size=3>" + (data.today.get(Calendar.MONTH) + 1) + "/"
				+ data.today.get(Calendar.DAY_OF_MONTH) + "/" + data.today.get(Calendar.YEAR) + "&nbsp;(Today)</html>",
				SwingConstants.LEFT);
		selectedDate.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
		assignGrid();

		calPanel = new JPanel();
		weekDaysName = new JButton[7];
		for (int i = 0; i < CalendarDataManager.CAL_WIDTH; i++) {
			weekDaysName[i] = new JButton(Scheduler.WEEK_DAY_NAME[i]);
			weekDaysName[i].setBorderPainted(false);
			weekDaysName[i].setContentAreaFilled(false);
			weekDaysName[i].setForeground(Color.WHITE);
			if (i == 0)
				weekDaysName[i].setBackground(new Color(200, 50, 50));
			else if (i == 6)
				weekDaysName[i].setBackground(new Color(50, 100, 200));
			else
				weekDaysName[i].setBackground(new Color(150, 150, 150));
			weekDaysName[i].setOpaque(true);
			weekDaysName[i].setFocusPainted(false);
			calPanel.add(weekDaysName[i]);
		}

		for (int i = 0; i < CalendarDataManager.CAL_HEIGHT; i++) {
			for (int j = 0; j < CalendarDataManager.CAL_WIDTH; j++) {
				dateButs[i][j] = new JButton();
				dateButs[i][j].setBorderPainted(false);
				dateButs[i][j].setContentAreaFilled(false);
				dateButs[i][j].setBackground(Color.WHITE);
				dateButs[i][j].setOpaque(true);
				dateButs[i][j].addActionListener(lForDateButs);
				calPanel.add(dateButs[i][j]);
			}
		}
		calPanel.setLayout(new GridLayout(0, 7, 2, 2));
		calPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
		showCal();

		frameSubPanelWest = new JPanel();
		Dimension calOpPanelSize = calOpPanel.getPreferredSize();
		calOpPanelSize.height = 90;
		calOpPanel.setPreferredSize(calOpPanelSize);
		frameSubPanelWest.setLayout(new BorderLayout());
		frameSubPanelWest.add(calOpPanel, BorderLayout.NORTH);
		frameSubPanelWest.add(calPanel, BorderLayout.CENTER);
	}

	private void assignGrid() {
		GridBagConstraints calOpGC = new GridBagConstraints();
		calOpGC.gridx = 1;
		calOpGC.gridy = 1;
		calOpGC.gridwidth = 2;
		calOpGC.gridheight = 1;
		calOpGC.weightx = 1;
		calOpGC.weighty = 1;
		calOpGC.insets = new Insets(5, 5, 0, 0);
		calOpGC.anchor = GridBagConstraints.WEST;
		calOpGC.fill = GridBagConstraints.NONE;
		calOpPanel.add(todayBut, calOpGC);
		
		calOpGC.gridx = 2;
		calOpGC.gridy = 1;
		calOpGC.gridwidth = 3;
		calOpGC.weighty = 1;
		calOpPanel.add(todayLab, calOpGC);
		
		calOpGC.weightx = 1;
		calOpGC.weighty = 1;
		calOpGC.fill = GridBagConstraints.NONE;
		calOpGC.gridwidth = 1;
		calOpGC.gridx = 4;
		calOpGC.gridy = 1;
		calOpPanel.add(userInfo, calOpGC);
		
		calOpGC.gridwidth = 1;
		calOpGC.gridx = 5;
		calOpGC.gridy = 1;
		calOpPanel.add(logout, calOpGC);
		calOpGC.insets = new Insets(5, 5, 0, 0);
		calOpGC.anchor = GridBagConstraints.CENTER;
		calOpGC.gridwidth = 1;
		calOpGC.gridx = 1;
		calOpGC.gridy = 2;
		calOpPanel.add(lYearBut, calOpGC);
		calOpGC.gridwidth = 1;
		calOpGC.gridx = 2;
		calOpGC.gridy = 2;
		calOpPanel.add(lMonBut, calOpGC);
		calOpGC.gridwidth = 2;
		calOpGC.gridx = 3;
		calOpGC.gridy = 2;
		calOpPanel.add(curMMYYYYLab, calOpGC);
		calOpGC.gridwidth = 1;
		calOpGC.gridx = 5;
		calOpGC.gridy = 2;
		calOpPanel.add(nMonBut, calOpGC);
		calOpGC.gridwidth = 1;
		calOpGC.gridx = 6;
		calOpGC.gridy = 2;
		calOpPanel.add(nYearBut, calOpGC);
	}

	public void focusToday() {
		if (data.today.get(Calendar.DAY_OF_WEEK) == 1)
			dateButs[data.today.get(Calendar.WEEK_OF_MONTH)][data.today.get(Calendar.DAY_OF_WEEK) - 1]
					.requestFocusInWindow();
		else
			dateButs[data.today.get(Calendar.WEEK_OF_MONTH) - 1][data.today.get(Calendar.DAY_OF_WEEK) - 1]
					.requestFocusInWindow();
	}

	public void showCal() {
		for (int i = 0; i < CalendarDataManager.CAL_HEIGHT; i++) {
			for (int j = 0; j < CalendarDataManager.CAL_WIDTH; j++) {
				String fontColor = "black";
				if (j == 0)
					fontColor = "red";
				else if (j == 6)
					fontColor = "blue";
				data.setFile();
				File f = new File("ListData/" + data.calYear + ((data.calMonth + 1) < 10 ? "0" : "")
						+ (data.calMonth + 1) + (data.calDates[i][j] < 10 ? "0" : "") + data.calDates[i][j] + ".txt");
				if (f.exists()) {
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
	
	abstract public void readSchedule();

/*
	private void readMemo() {
		try {
			File f = new File("MemoData/" + data.calYear + ((data.calMonth + 1) < 10 ? "0" : "") + (data.calMonth + 1)
					+ (data.calDayOfMon < 10 ? "0" : "") + data.calDayOfMon + ".txt");
			if (f.exists()) {
				BufferedReader in = new BufferedReader(new FileReader(
						"MemoData/" + data.calYear + ((data.calMonth + 1) < 10 ? "0" : "") + (data.calMonth + 1)
								+ (data.calDayOfMon < 10 ? "0" : "") + data.calDayOfMon + ".txt"));
				String memoAreaText = new String();
				while (true) {
					String tempStr = in.readLine();
					if (tempStr == null)
						break;
					memoAreaText = memoAreaText + tempStr + System.getProperty("line.separator");
				}
				memoArea.setText(memoAreaText);
				in.close();
			} else
				memoArea.setText("");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/

	private class ListenForCalOpButtons implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == todayBut) {
				data.setToday();
				lForDateButs.actionPerformed(e);
				focusToday();
			} else if (e.getSource() == lYearBut)
				data.moveMonth(-12);
			else if (e.getSource() == lMonBut)
				data.moveMonth(-1);
			else if (e.getSource() == nMonBut)
				data.moveMonth(1);
			else if (e.getSource() == nYearBut)
				data.moveMonth(12);

			curMMYYYYLab
					.setText("<html><table width=100><tr><th><font size=5>" + ((data.calMonth + 1) < 10 ? "&nbsp;" : "")
							+ (data.calMonth + 1) + " / " + data.calYear + "</th></tr></table></html>");
			showCal();
		}
	}

	private class listenForDateButs implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int k = 0, l = 0;
			for (int i = 0; i < CalendarDataManager.CAL_HEIGHT; i++) {
				for (int j = 0; j < CalendarDataManager.CAL_WIDTH; j++) {
					if (e.getSource() == dateButs[i][j]) {
						k = i;
						l = j;
					}
				}
			}

			if (!(k == 0 && l == 0))
				data.calDayOfMon = data.calDates[k][l]; // to activate actionPerformed when press today button

			data.cal = new GregorianCalendar(data.calYear, data.calMonth, data.calDayOfMon);

			String dDayString = new String();
			int dDay = ((int) ((data.cal.getTimeInMillis() - data.today.getTimeInMillis()) / 1000 / 60 / 60 / 24));
			if (dDay == 0 && (data.cal.get(Calendar.YEAR) == data.today.get(Calendar.YEAR))
					&& (data.cal.get(Calendar.MONTH) == data.today.get(Calendar.MONTH))
					&& (data.cal.get(Calendar.DAY_OF_MONTH) == data.today.get(Calendar.DAY_OF_MONTH)))
				dDayString = "Today";
			else if (dDay >= 0)
				dDayString = "D-" + (dDay + 1);
			else if (dDay < 0)
				dDayString = "D+" + (dDay) * (-1);

			selectedDate.setText("<Html><font size=3>" + (data.calMonth + 1) + "/" + data.calDayOfMon + "/"
					+ data.calYear + "&nbsp;(" + dDayString + ")</html>");

			readSchedule();
		}
	}
	
	private class UserListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == userInfo) {
				//System.out.println("calling account info...");
				new AccountInfoFrame(username, connection, data);
				mainFrame.dispose();
			}
			else if(e.getSource() == logout) {
				//System.out.println("logging out...");
				InitialFrame newMainFrame = new InitialFrame(connection, data);
				newMainFrame.initiateFrame();
				mainFrame.dispose();
			}
		}
	}

}
