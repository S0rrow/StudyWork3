package edu.handong.csee.java.iter1;

import java.awt.Color;

import javax.swing.JFrame;

public class CalendarPanelInverse extends CalendarPanelInstance {

	public static final Color BUTTON_COLOR = Color.black;
	public static final Color BUTTON_TEXT_COLOR = Color.white;

	public CalendarPanelInverse(String userName, Connectivity mainConnection, CalendarDataManager cdm, JFrame frame) {
		super(userName, mainConnection, cdm, frame);
		curTheme = "inverse";
		for (int i = 0; i < CalendarDataManager.CAL_HEIGHT; i++) {
			for (int j = 0; j < CalendarDataManager.CAL_WIDTH; j++) {
				super.dateButs[i][j].setForeground(BUTTON_TEXT_COLOR);
				super.dateButs[i][j].setBackground(BUTTON_COLOR);
			}
		}
	}

//	@Override
//	public void showCal() {
//
//	}

}
