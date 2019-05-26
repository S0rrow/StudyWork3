package edu.handong.csee.java.iter1;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class ScheduleList extends JFrame {

	private JTextField textField;
	CalendarDataManager data;
	CalendarPanel cp;
	String username;
	FileButListener f2 = new FileButListener();
	JButton add = new JButton("추가");
	JButton cancel = new JButton("취소");

	/*
	 * public static void main(String[] args) { EventQueue.invokeLater(new
	 * Runnable() { public void run() { try { ScheduleList frame = new
	 * ScheduleList(data); frame.setVisible(true); } catch (Exception e) {
	 * e.printStackTrace(); } } });
	 * 
	 * }
	 */
	/**
	 * Create the frame.
	 */
	ScheduleList(CalendarPanel CP, CalendarDataManager cdm, String userName) {
		setTitle("LIST 추가");

		// 주의, 여기서 setDefaultCloseOperation() 정의를 하지 말아야 한다
		// 정의하게 되면 새 창을 닫으면 모든 창과 프로그램이 동시에 꺼진다
		data = cdm;
		username = userName;
		cp = CP;
		JPanel NewWindowContainer = new JPanel();
		setContentPane(NewWindowContainer);
		
		JPanel SubListArea = new JPanel();
		JPanel panel = new JPanel();

		textField = new JTextField();
		panel.add(textField);
		textField.setColumns(35);

		add.addActionListener(f2);
		cancel.addActionListener(new ActionListener() {
			// 만들어진 버튼 "새 창 띄우기"에 버튼이 눌러지면 발생하는 행동을 정의
			@Override
			public void actionPerformed(ActionEvent e) {
				// setVisible(false);
				dispose();
			}

		});

		NewWindowContainer.setLayout(new BorderLayout());
		SubListArea.add(add);
		SubListArea.add(cancel);
		NewWindowContainer.add(SubListArea, BorderLayout.SOUTH);

		NewWindowContainer.add(panel, BorderLayout.CENTER);

		setSize(474, 97);
		setResizable(false);
		setVisible(true);
	}

	private class FileButListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String dirName = "ListData/"+username+"/"+data.curDate;
			String listName = dirName+"/"+ textField.getText()+".txt";
			if (e.getSource() == add) {
				try {
					data.meetingName = textField.getText();
					data.setFile();
					File f = new File(dirName);
					if (!f.isDirectory()) f.mkdirs();
					
					BufferedWriter out = new BufferedWriter(new FileWriter(listName));
					out.write("Blank");
					out.close();

					System.out.println(dirName);
					System.out.println(listName);
					// data.setFile();
					File r = new File(listName);
					if (r.exists()) {
						BufferedReader in = new BufferedReader(new FileReader(listName));
						String ListName1 = new String();

						while (true) {
							String tempStr = in.readLine();
							if (tempStr == null)
								break;
							ListName1 = ListName1 + tempStr + System.getProperty("line.separator");
						}
						in.close();
					} else {
						System.out.println("no file");
					}
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
			cp.readSchedule();
			dispose();

		}
	}

}