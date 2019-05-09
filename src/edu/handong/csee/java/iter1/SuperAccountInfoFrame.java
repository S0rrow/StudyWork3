package edu.handong.csee.java.iter1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.*;

@SuppressWarnings("serial")
public class SuperAccountInfoFrame extends JFrame{
	private Connectivity connection;
	SuperAccountInfoFrame(String username, Connectivity mainConnection){
		//System.out.println("Initiated new SignInSuperFrame.");
		setTitle("SIGNED IN");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(100,100);
		connection = mainConnection;
		connection.Connect();
		//현재 로그인 된 관리자 계정의 정보를 표시
		Vector<String> superAccountInfo = connection.getAccountInfo(username);
		JPanel accountInfoContainer = new JPanel(new GridLayout(1,9));
		JButton edit = new JButton("edit");
		edit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				connection.close();
				new SuperAccountEditFrame(username, username, connection);
				dispose();
			}
		});
		JButton drop = new JButton("delete");
		drop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int dialogResult = JOptionPane.showConfirmDialog(null, "DELETE this account from the database?");
				if(dialogResult==JOptionPane.YES_OPTION) {
					//System.out.println("deleting account...");
					connection.stateDataManip("DELETE FROM account_info WHERE username='"+username+"';");
					//System.out.println("account deleted from database!");
					connection.close();
					InitialFrame newMainFrame = new InitialFrame(connection);
					newMainFrame.initiateFrame();
					dispose();
				}
			}
		});
		accountInfoContainer.add(new JLabel(superAccountInfo.elementAt(0)));
		accountInfoContainer.add(new JLabel(superAccountInfo.elementAt(1)));
		accountInfoContainer.add(new JLabel(superAccountInfo.elementAt(2)));
		accountInfoContainer.add(new JLabel(superAccountInfo.elementAt(3)));
		accountInfoContainer.add(new JLabel(superAccountInfo.elementAt(4)));
		accountInfoContainer.add(new JLabel(superAccountInfo.elementAt(5)));
		accountInfoContainer.add(new JLabel(superAccountInfo.elementAt(6)));
		accountInfoContainer.add(edit);
		accountInfoContainer.add(drop);
		for(Component item:accountInfoContainer.getComponents()) {
			((JComponent) item).setOpaque(true);
			item.setBackground(Color.WHITE);
		}
		
		//현재 데이터베이스에 있는 모든 유저 정보 표시
		JPanel mainContainer = new JPanel();
		synchronize(mainContainer, username);
		//System.out.println("table added.");
		
		//추가 기능 표시
		JPanel downContainer = new JPanel(new GridLayout(1, 3));
		JButton insert = new JButton("Insert");
		insert.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int dialogResult = JOptionPane.showConfirmDialog(null, "Insert new account?");
				if(dialogResult==JOptionPane.YES_OPTION) {
					connection.close();
					new SuperAccountRegistrationFrame(username, connection);
					dispose();
				}
			}
		});
		JButton logOut = new JButton("Log out");
		logOut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int dialogResult = JOptionPane.showConfirmDialog(null, "Log out?");
				if(dialogResult==JOptionPane.YES_OPTION) {
					InitialFrame newMainFrame = new InitialFrame(connection);
					newMainFrame.initiateFrame();
					connection.close();
					dispose();
				}
			}
		});
		JButton quit = new JButton("Quit");
		quit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int dialogResult = JOptionPane.showConfirmDialog(null, "Quit?");
				if(dialogResult==JOptionPane.YES_OPTION) {
					connection.close();
					dispose();
				}
			}
		});
		downContainer.add(insert);
		downContainer.add(logOut);
		downContainer.add(quit);
		
		add(accountInfoContainer, BorderLayout.NORTH);
		add(mainContainer, BorderLayout.CENTER);
		add(downContainer, BorderLayout.SOUTH);
		setPreferredSize(new Dimension(800,600));
		pack();
		setVisible(true);
	}
	
	private void synchronize(JPanel mainContainer, String superAccount) {
		Vector<Vector<String>> databaseTable = new Vector<Vector<String>>();
		try {
			String query = "SELECT * FROM account_info;";
			//System.out.println(query);
			connection.state(query);
			while(connection.resultSet.next()) {
				Vector<String> rowData = new Vector<String>();
				rowData.add(connection.resultSet.getString("username"));
				rowData.add(connection.resultSet.getString("password"));
				rowData.add(connection.resultSet.getString("gender"));
				rowData.add(connection.resultSet.getString("email"));
				rowData.add(connection.resultSet.getString("department"));
				rowData.add(connection.resultSet.getString("studentid"));
				rowData.add(connection.resultSet.getString("super"));
				databaseTable.add(rowData);
			}
		} catch(Exception e) {
			e.getMessage();
		}
		int numRow = databaseTable.size();
		mainContainer.setLayout(new GridLayout(numRow+1, 1));
		JPanel indexContainer = new JPanel(new GridLayout(1, 9));
		indexContainer.add(new JLabel("USERNAME"));
		indexContainer.add(new JLabel("PASSWORD"));
		indexContainer.add(new JLabel("GENDER"));
		indexContainer.add(new JLabel("EMAIL"));
		indexContainer.add(new JLabel("DEPARTMENT"));
		indexContainer.add(new JLabel("STUDENTID"));
		indexContainer.add(new JLabel("SUPER"));
		indexContainer.add(new JLabel("EDIT"));
		indexContainer.add(new JLabel("DELETE"));
		mainContainer.add(indexContainer);
		for(int i = 0; i < numRow; i++) {
			Vector<String> colData = databaseTable.elementAt(i);
			JPanel columnContainer = new JPanel(new GridLayout(1,9));
			JLabel username = new JLabel(colData.elementAt(0));
			JLabel password = new JLabel(colData.elementAt(1));
			JLabel gender = new JLabel(colData.elementAt(2));
			JLabel email = new JLabel(colData.elementAt(3));
			JLabel department = new JLabel(colData.elementAt(4));
			JLabel studentid = new JLabel(colData.elementAt(5));
			JLabel isSuper = new JLabel(colData.elementAt(6));
			JButton edit = new JButton("edit");
			edit.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					connection.close();
					new SuperAccountEditFrame(username.getText(), superAccount, connection);
					dispose();
				}
			});
			JButton drop = new JButton("delete");
			drop.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int dialogResult = JOptionPane.showConfirmDialog(null, "DELETE this account from the database?");
					if(dialogResult==JOptionPane.YES_OPTION) {
						//System.out.println("deleting account...");
						connection.stateDataManip("DELETE FROM account_info WHERE username='"+username.getText()+"';");
						//System.out.println("account deleted from database!");
						connection.close();
						new SuperAccountInfoFrame(superAccount, connection);
						dispose();
					}
				}
			});
			if(username.getText().equals(superAccount)) {
				edit.setEnabled(false);
				drop.setEnabled(false);
			}
			columnContainer.add(username);
			columnContainer.add(password);
			columnContainer.add(gender);
			columnContainer.add(email);
			columnContainer.add(department);
			columnContainer.add(studentid);
			columnContainer.add(isSuper);
			columnContainer.add(edit);
			columnContainer.add(drop);
			mainContainer.add(columnContainer);
		}
	}
}
