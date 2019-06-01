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
	
	LockCommand lc = new LockCommand(this);
	UnLockCommand uc = new UnLockCommand(this);
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
	JButton modBut;
	JButton delBut;
	// JButton clearBut;
	JButton LockBut;
	private int state_lock=0;
	
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

	CalendarPanelFactory cpf;
	CalendarPanel cp;
	public static Scheduler build(String userName, Connectivity mainConnection, CalendarDataManager cdm, String theme) {
		return new Scheduler(userName, mainConnection, cdm, theme);
	}
	
	Scheduler(String userName, Connectivity mainConnection, CalendarDataManager cdm, String theme) {
		super();
		connection = mainConnection;
		username = userName;

		data = cdm;
		mainFrame = new JFrame("Scheduler");
		cpf = new CalendarPanelFactory(username, connection, data, mainFrame);
		cp = cpf.makePanel(theme);
		//cp = cpf.makePanel("default");
		start();
	}
	public void lock() {
		state_lock=1;
		LockBut.addActionListener(uc);//command변
		LockBut.setText("unlock");
		LockBut.removeActionListener(lc);
		
	}
	public void unlock() {
		state_lock=0;
		LockBut.addActionListener(lc);
		LockBut.setText("lock");
		LockBut.removeActionListener(uc);
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
				if(state_lock==1)return;
				new ScheduleList(cp, data, username);
				bottomInfo.setText(addMsg);
			}
		});
		modBut = new JButton("Modify");
		modBut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(state_lock==1)return;
				String D_file;
				int n =List.getSelectedRow();
				if(n==-1) {
					System.out.println("error");
					return;
				}
				data.setFile();
				String PATH = "ListData/" + username +"/"+data.curDate;
				D_file=model.getValueAt(n, 0).toString();
				new Meeting(PATH, D_file);
				bottomInfo.setText("modify");
			}
		});
		LockBut = new JButton();
		LockBut.setText("Lock");
		LockBut.addActionListener(lc);
		
		
		
		

		ListPanel.setLayout(new BorderLayout());
		ListPanel.add(selectedDate, BorderLayout.NORTH);
		ListPanel.add(List, BorderLayout.CENTER);
		scrollPane = new JScrollPane(List);
		ListPanel.add(scrollPane, BorderLayout.EAST);
		ListPanel.add(memoSubPanel, BorderLayout.SOUTH);
		memoSubPanel.add(addBut);
		// memoSubPanel.add(saveBut);
		memoSubPanel.add(modBut);
		memoSubPanel.add(delBut);
		memoSubPanel.add(LockBut);
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
		//JPanel cardPanel = new JPanel().setLayout(new CardLayout());
		mainFrame.add(frameSubPanelWest, BorderLayout.WEST);
		mainFrame.add(frameSubPanelEast, BorderLayout.CENTER);
		mainFrame.add(frameBottomPanel, BorderLayout.SOUTH);
		mainFrame.setVisible(true);
		cp.themeBut.addActionListener(new ThemeChanger());
		cp.focusToday();
		data.setFile();
		cp.readSchedule();
		ThreadControl threadCnl = new ThreadControl(data, infoClock, bottomInfo);
		threadCnl.start();
	}
	
	private class FileButListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			if(state_lock==1)return;
			String D_file;
			data.setFile();
			String PATH = "ListData/" + username +"/"+data.curDate;
			if (e.getSource() == delBut) {
				int n =List.getSelectedRow();
				if(n>=0&&n<List.getRowCount()) {
					D_file=PATH+"/"+model.getValueAt(n, 0).toString()+".txt";
					System.out.println(D_file);
					File f = new File(D_file);
					if (f.exists()) {
						model.removeRow(n);
						f.delete();
						List.setModel(model);
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
	
	private class ThemeChanger implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			int dialogResult = JOptionPane.showConfirmDialog(null, "Change?");
			if(dialogResult==JOptionPane.YES_OPTION) {
				if(cp.curTheme.equals("default")) new Scheduler(username, connection, data, "inverse");
				else new Scheduler(username, connection, data, "default");
				mainFrame.dispose();
			}
		}
	}
}
