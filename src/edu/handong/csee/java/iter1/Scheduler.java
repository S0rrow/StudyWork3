package edu.handong.csee.java.iter1;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Scheduler {
	// user data and db connectivity
	private String username;
	Connectivity connection;
	CalendarDataManager data;

	// Java frame components
	JFrame mainFrame;
	ImageIcon icon = new ImageIcon();
	JPanel infoPanel;
	JLabel infoClock;

	// JPanel memoPanel;
	JPanel ListPanel;
	JLabel selectedDate;
	// JTextArea memoArea;
	JScrollPane scrollPane;
	JPanel memoSubPanel;
	JButton addBut;
	// JButton saveBut;
	JButton delBut;
	// JButton clearBut;

	JPanel frameBottomPanel;
	JLabel bottomInfo = new JLabel("Welcome to Memo Calendar!");
	FileButListener fl = new FileButListener();

	final static String WEEK_DAY_NAME[] = { "SUN", "MON", "TUE", "WED", "THR", "FRI", "SAT" };
	final String title = "Memocalendar ver 1.0";
	final String DelButMsg1 = "Schedule deleted.";
	final String DelButMsg2 = "Not written or already deleted Schedule.";
	final String DelButMsg3 = "<html><font color=red>ERROR : Failed to delete file</html>";
	final String addMsg = "add";
	public static JTable List;
	static Object[][] rowData = new Object[1][1];
	static Object[] columnNames = new Object[1];

	@SuppressWarnings("serial")
	static DefaultTableModel model = new DefaultTableModel(rowData, columnNames) {
		public boolean isCellEditable(int i, int c) {
			return false;
		}
	};

	CalendarPanelAddition cp;

	Scheduler(String userName, Connectivity mainConnection, CalendarDataManager cdm) {
		super();
		connection = mainConnection;
		username = userName;

		data = cdm;
		mainFrame = new JFrame("Scheduler");
		cp = new CalendarPanelAddition(username, connection, data, mainFrame);
		
		start();
	}

	public void start() {
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(1200, 400);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setResizable(false);
		mainFrame.setIconImage(icon.getImage());
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");// apply LookAndFeel Windows
			SwingUtilities.updateComponentTreeUI(mainFrame);
		} catch (Exception e) {
			bottomInfo.setText("ERROR : LookAndFeel setting failed");
		}
		infoPanel = new JPanel();
		infoPanel.setLayout(new BorderLayout());
		infoClock = new JLabel("", SwingConstants.RIGHT);
		infoClock.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		infoPanel.add(infoClock, BorderLayout.NORTH);
		selectedDate = cp.selectedDate;

		ListPanel = new JPanel();
		ListPanel.setBorder(BorderFactory.createTitledBorder("LIST"));
		ListPanel.setLayout(new BorderLayout());

		columnNames[0] = "회의 이름";
		model = new DefaultTableModel(rowData, columnNames);
		List = new JTable(model);
		List.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		/*
		 * memoPanel = new JPanel();
		 * memoPanel.setBorder(BorderFactory.createTitledBorder("Memo")); memoArea =
		 * cp.memoArea; memoAreaSP = new JScrollPane(memoArea,
		 * JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
		 * JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); //readMemo();
		 */
		memoSubPanel = new JPanel();
		// saveBut = new JButton("Save");
		// saveBut.addActionListener(fl);
		delBut = new JButton("Delete");
		delBut.addActionListener(fl);
		// clearBut = new JButton("Clear");
		/*
		 * clearBut.addActionListener(new ActionListener() { public void
		 * actionPerformed(ActionEvent arg0) { memoArea.setText(null);
		 * bottomInfo.setText(ClrButMsg1); } });
		 */

		addBut = new JButton("Add");
		addBut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ScheduleList(cp, data, username);
				bottomInfo.setText(addMsg);
			}
		});

		ListPanel.setLayout(new BorderLayout());
		ListPanel.add(selectedDate, BorderLayout.NORTH);
		ListPanel.add(List, BorderLayout.CENTER);
		scrollPane = new JScrollPane(List);
		ListPanel.add(scrollPane, BorderLayout.EAST);
		ListPanel.add(memoSubPanel, BorderLayout.SOUTH);
		memoSubPanel.add(addBut);
		// memoSubPanel.add(saveBut);
		memoSubPanel.add(delBut);
		// memoSubPanel.add(clearBut);

		// arrange infoPanel, memoPanel at frameSubPanelEast에 배치
		JPanel frameSubPanelEast = new JPanel();

		Dimension infoPanelSize = infoPanel.getPreferredSize();
		infoPanelSize.height = 65;
		infoPanel.setPreferredSize(infoPanelSize);
		frameSubPanelEast.setLayout(new BorderLayout());
		frameSubPanelEast.add(infoPanel, BorderLayout.NORTH);
		frameSubPanelEast.add(ListPanel, BorderLayout.CENTER);

		JPanel frameSubPanelWest = cp.frameSubPanelWest;
		Dimension frameSubPanelWestSize = frameSubPanelWest.getPreferredSize();
		frameSubPanelWestSize.width = 610;
		frameSubPanelWest.setPreferredSize(frameSubPanelWestSize);

		// late added bottom Panel..
		frameBottomPanel = new JPanel();
		frameBottomPanel.add(bottomInfo);

		mainFrame.setLayout(new BorderLayout());
		mainFrame.add(frameSubPanelWest, BorderLayout.WEST);
		mainFrame.add(frameSubPanelEast, BorderLayout.CENTER);
		mainFrame.add(frameBottomPanel, BorderLayout.SOUTH);
		mainFrame.setVisible(true);

		cp.focusToday();
		data.setFile();
		cp.readSchedule();
		ThreadControl threadCnl = new ThreadControl(data, infoClock, bottomInfo);
		threadCnl.start();
	}
	
	private class FileButListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == delBut) {
				data.setFile();
				File f = new File(data.fileName);
				if (f.exists()) {
					f.delete();
					data.setFile();
					cp.readSchedule();
					cp.showCal();
					bottomInfo.setText(DelButMsg1);
				} else
					bottomInfo.setText(DelButMsg2);
			}
		}
	}
}
