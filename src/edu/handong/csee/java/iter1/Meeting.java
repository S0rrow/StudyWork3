package edu.handong.csee.java.iter1;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class Meeting extends JFrame {
	

	/**
	 * Create the frame.
	 */
	public Meeting(String PATH, String filename) {
		
		setTitle("Meeting");
		
		JPanel contentPane = new JPanel();
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("Meeting Name : ");
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel(filename);
		panel.add(lblNewLabel_1);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		
		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2);
		
		JTextArea txtrContents = new JTextArea();
		try {
			BufferedReader in = new BufferedReader(new FileReader(PATH+"/"+filename+".txt"));
			String text =null;
			try {
				while((text=in.readLine())!=null)
		            txtrContents.append(text+"\n"); 
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		//txtrContents.setText("Contents");
		panel_2.add(txtrContents);
		txtrContents.setColumns(30);
		txtrContents.setRows(10);
		
		JPanel panel_3 = new JPanel();
		contentPane.add(panel_3, BorderLayout.SOUTH);
		
		JButton btnNewButton = new JButton("SAVE");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					BufferedWriter out = new BufferedWriter(new FileWriter(PATH+"/"+filename+".txt"));
					out.write(txtrContents.getText());
					out.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				dispose();
			}
		});
		panel_3.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("ADD FILE");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		panel_3.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("SHARE");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		panel_3.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("CLOSE");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		panel_3.add(btnNewButton_3);
		setResizable(false);
		setVisible(true);
	}

}
