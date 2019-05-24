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
	ScheduleList(CalendarPanel cp, CalendarDataManager cdm) {
		setTitle("LIST 추가");

		// 주의, 여기서 setDefaultCloseOperation() 정의를 하지 말아야 한다
		// 정의하게 되면 새 창을 닫으면 모든 창과 프로그램이 동시에 꺼진다
		data = cdm;
		JPanel NewWindowContainer = new JPanel();
		setContentPane(NewWindowContainer);
		JButton add = new JButton("추가");
		JButton cancel = new JButton("취소");
		JPanel SubListArea = new JPanel();
		JPanel panel = new JPanel();

		textField = new JTextField();
		panel.add(textField);
		textField.setColumns(35);

		add.addActionListener(new ActionListener() {
			// 만들어진 버튼 "새 창 띄우기"에 버튼이 눌러지면 발생하는 행동을 정의
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					
					
					File f = new File("ListData");
					if (!f.isDirectory())
						f.mkdir();
					// if (memo.length() > 0) {
					data.setFile();
					BufferedWriter out = new BufferedWriter(new FileWriter(data.fileName));
					String ListName = textField.getText();
					out.write(ListName);
					out.close();

					System.out.println("ok");

					data.setFile();
					File r = new File(data.fileName);
					if (r.exists()) {
						BufferedReader in = new BufferedReader(new FileReader(data.fileName));
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
					cp.readSchedule();
					dispose();
				} catch (IOException e) {
					System.out.println("error");
				}

			}

		});
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

}