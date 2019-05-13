package edu.handong.csee.java.iter1;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

	//JPanel memoPanel;
	JPanel ListPanel;
	JLabel selectedDate;
	JTextArea memoArea;
	JScrollPane scrollPane;
	JPanel memoSubPanel;
	JButton addBut;
	JButton saveBut;
	JButton delBut;
	JButton clearBut;

	JPanel frameBottomPanel;
	JLabel bottomInfo = new JLabel("Welcome to Memo Calendar!");
	FileButListener fl = new FileButListener();

	final static String WEEK_DAY_NAME[] = { "SUN", "MON", "TUE", "WED", "THR", "FRI", "SAT" };
	final String title = "Memocalendar ver 1.0";
	final String SaveButMsg1 = "Saved in MemoData folder.";
	final String SaveButMsg2 = "Please write the memo first.";
	final String SaveButMsg3 = "<html><font color=red>ERROR : Failed to write file</html>";
	final String DelButMsg1 = "Memo deleted.";
	final String DelButMsg2 = "Not written or already deleted memo.";
	final String DelButMsg3 = "<html><font color=red>ERROR : Failed to delete file</html>";
	final String ClrButMsg1 = "Cleared the memo.";
	final String addMsg = "add";
	public static JTable List;
	 static Object[][] rowData = new Object[1][1];
	 static Object[] columnNames = new Object[1];

	static DefaultTableModel model = new DefaultTableModel(rowData,columnNames);

	CalendarPanel cp;

	Scheduler(String userName, Connectivity mainConnection, CalendarDataManager cdm) {
		super();
		connection = mainConnection;
		username = userName;
		
		data = cdm;
		mainFrame = new JFrame("Scheduler");
		cp = new CalendarPanel(username, connection, data, mainFrame);
		start();
	}

	@SuppressWarnings("serial")
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
		model = new DefaultTableModel(rowData,columnNames) {
			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		List = new JTable(model);
		List.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		
		
		
	
		
		/*memoPanel = new JPanel();
		memoPanel.setBorder(BorderFactory.createTitledBorder("Memo"));
		memoArea = cp.memoArea;
		memoAreaSP = new JScrollPane(memoArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		//readMemo();
		 *
*/		cp.readSchedule();
		memoSubPanel = new JPanel();
		saveBut = new JButton("Save");
		saveBut.addActionListener(fl);
		delBut = new JButton("Delete");
		delBut.addActionListener(fl);
		clearBut = new JButton("Clear");
		clearBut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				memoArea.setText(null);
				bottomInfo.setText(ClrButMsg1);
			}
		});
		
		addBut = new JButton("Add");
		addBut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ScheduleList(cp, data);
				
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
		memoSubPanel.add(saveBut);
		memoSubPanel.add(delBut);
		memoSubPanel.add(clearBut);
		
	  

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
		ThreadControl threadCnl = new ThreadControl(data, infoClock, bottomInfo);
		threadCnl.start();
	}
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
	}
	*/
	



	private class FileButListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == saveBut) {
				try {
					File f = new File("MemoData");
					if (!f.isDirectory())
						f.mkdir();

					String memo = memoArea.getText();
					if (memo.length() > 0) {
						BufferedWriter out = new BufferedWriter(new FileWriter(
								"MemoData/" + data.calYear + ((data.calMonth + 1) < 10 ? "0" : "") + (data.calMonth + 1)
										+ (data.calDayOfMon < 10 ? "0" : "") + data.calDayOfMon + ".txt"));
						String str = memoArea.getText();
						out.write(str);
						out.close();
						bottomInfo.setText(data.calYear + ((data.calMonth + 1) < 10 ? "0" : "") + (data.calMonth + 1)
								+ (data.calDayOfMon < 10 ? "0" : "") + data.calDayOfMon + ".txt" + SaveButMsg1);
					} else
						bottomInfo.setText(SaveButMsg2);
				} catch (IOException io) {
					bottomInfo.setText(SaveButMsg3);
				}
				cp.showCal();
			} else if (e.getSource() == delBut) {
				memoArea.setText("");
				File f = new File("MemoData/" + data.calYear + ((data.calMonth + 1) < 10 ? "0" : "")
						+ (data.calMonth + 1) + (data.calDayOfMon < 10 ? "0" : "") + data.calDayOfMon + ".txt");
				if (f.exists()) {
					f.delete();
					cp.showCal();
					bottomInfo.setText(DelButMsg1);
				} else
					bottomInfo.setText(DelButMsg2);
			}
		}
	}
}
