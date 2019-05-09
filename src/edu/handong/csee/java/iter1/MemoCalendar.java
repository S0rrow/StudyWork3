package edu.handong.csee.java.iter1;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.*;

public class MemoCalendar extends CalendarDataManager { // GUI of CalendarDataManager + Memo + Clock
   // frame component and layout
   JFrame mainFrame;
    //   ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("icon.png")));
   ImageIcon icon = new ImageIcon();

   JPanel calOpPanel;
   JButton todayBut;
   JLabel todayLab;
   JButton lYearBut;
   JButton lMonBut;
   JLabel curMMYYYYLab;
   JButton nMonBut;
   JButton nYearBut;
   ListenForCalOpButtons lForCalOpButtons = new ListenForCalOpButtons();

   JPanel calPanel;
   JButton weekDaysName[];
   JButton dateButs[][] = new JButton[6][7];
   listenForDateButs lForDateButs = new listenForDateButs();

   JPanel infoPanel;
   JLabel infoClock;

   //JPanel memoPanel;
   JLabel selectedDate;
   //JTextArea memoArea;
   //JScrollPane memoAreaSP;
   //JPanel memoSubPanel;
   JButton saveBut;
   JButton delBut;
   JButton clearBut;

   JPanel frameBottomPanel;
   JLabel bottomInfo = new JLabel("Welcome to Memo Calendar!");
   // constant, message
   final String WEEK_DAY_NAME[] = { "SUN", "MON", "TUE", "WED", "THR", "FRI", "SAT" };
   final String title = "Memocalendar ver 1.0";
   final String SaveButMsg1 = "Saved in MemoData folder.";
   final String SaveButMsg2 = "Please write the memo first.";
   final String SaveButMsg3 = "<html><font color=red>ERROR : Failed to write file</html>";
   final String DelButMsg1 = "Memo deleted.";
   final String DelButMsg2 = "Not written or already deleted memo.";
   final String DelButMsg3 = "<html><font color=red>ERROR : Failed to delete file</html>";
   final String ClrButMsg1 = "Cleared the memo.";


   public MemoCalendar() { // Aligned by component. Blank lines between each panel

      mainFrame = new JFrame(title);
      mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      mainFrame.setSize(1000, 800);
      mainFrame.setLocationRelativeTo(null);
      mainFrame.setResizable(false);
      mainFrame.setIconImage(icon.getImage());
      try {
         UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");// apply LookAndFeel Windows
         SwingUtilities.updateComponentTreeUI(mainFrame);
      } catch (Exception e) {
         bottomInfo.setText("ERROR : LookAndFeel setting failed");
      }

      calOpPanel = new JPanel();
      todayBut = new JButton("Today");
      todayBut.setToolTipText("Today");
      todayBut.addActionListener(lForCalOpButtons);
      todayLab = new JLabel(today.get(Calendar.MONTH) + 1 + "/" + today.get(Calendar.DAY_OF_MONTH) + "/"
            + today.get(Calendar.YEAR));
      lYearBut = new JButton("<<");
      lYearBut.setToolTipText("Previous Year");
      lYearBut.addActionListener(lForCalOpButtons);
      lMonBut = new JButton("<");
      lMonBut.setToolTipText("Previous Month");
      lMonBut.addActionListener(lForCalOpButtons);
      curMMYYYYLab = new JLabel("<html><table width=100><tr><th><font size=5>" + ((calMonth + 1) < 10 ? "&nbsp;" : "")
            + (calMonth + 1) + " / " + calYear + "</th></tr></table></html>");
      nMonBut = new JButton(">");
      nMonBut.setToolTipText("Next Month");
      nMonBut.addActionListener(lForCalOpButtons);
      nYearBut = new JButton(">>");
      nYearBut.setToolTipText("Next Year");
      nYearBut.addActionListener(lForCalOpButtons);
      calOpPanel.setLayout(new GridBagLayout());
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
      calOpGC.gridwidth = 3;
      calOpGC.gridx = 2;
      calOpGC.gridy = 1;
      calOpPanel.add(todayLab, calOpGC);
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

      calPanel = new JPanel();
      weekDaysName = new JButton[7];
      for (int i = 0; i < CAL_WIDTH; i++) {
         weekDaysName[i] = new JButton(WEEK_DAY_NAME[i]);
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
      for (int i = 0; i < CAL_HEIGHT; i++) {
         for (int j = 0; j < CAL_WIDTH; j++) {
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
      showCal(); // 달력을 표시

      infoPanel = new JPanel();
      infoPanel.setLayout(new BorderLayout());
      infoClock = new JLabel("", SwingConstants.RIGHT);
      infoClock.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
      infoPanel.add(infoClock, BorderLayout.NORTH);
      selectedDate = new JLabel("<Html><font size=3>" + (today.get(Calendar.MONTH) + 1) + "/"
            + today.get(Calendar.DAY_OF_MONTH) + "/" + today.get(Calendar.YEAR) + "&nbsp;(Today)</html>",
            SwingConstants.LEFT);
      selectedDate.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));

      //memoPanel = new JPanel();
      //memoPanel.setBorder(BorderFactory.createTitledBorder("Memo"));
      //memoArea = new JTextArea();
      //memoArea.setLineWrap(true);
      //memoArea.setWrapStyleWord(true);
      //memoAreaSP = new JScrollPane(memoArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            //JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
      //readMemo();

      //memoSubPanel = new JPanel();
      saveBut = new JButton("Save");
      saveBut.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent arg0) {
          /* try {
               File f = new File("MemoData");
               if (!f.isDirectory())
                  f.mkdir();

               String memo = memoArea.getText();
               if (memo.length() > 0) {
                  BufferedWriter out = new BufferedWriter(
                        new FileWriter("MemoData/" + calYear + ((calMonth + 1) < 10 ? "0" : "") + (calMonth + 1)
                              + (calDayOfMon < 10 ? "0" : "") + calDayOfMon + ".txt"));
                  String str = memoArea.getText();
                  out.write(str);
                  out.close();
                  bottomInfo.setText(calYear + ((calMonth + 1) < 10 ? "0" : "") + (calMonth + 1)
                        + (calDayOfMon < 10 ? "0" : "") + calDayOfMon + ".txt" + SaveButMsg1);
               } else
                  bottomInfo.setText(SaveButMsg2);
            } catch (IOException e) {
               bottomInfo.setText(SaveButMsg3);
            }
            */
            showCal();
         }
      });
      delBut = new JButton("Delete");
      delBut.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            //memoArea.setText("");
            File f = new File("MemoData/" + calYear + ((calMonth + 1) < 10 ? "0" : "") + (calMonth + 1)
                  + (calDayOfMon < 10 ? "0" : "") + calDayOfMon + ".txt");
            if (f.exists()) {
               f.delete();
               showCal();
               bottomInfo.setText(DelButMsg1);
            } else
               bottomInfo.setText(DelButMsg2);
         }
      });
      clearBut = new JButton("Clear");
      clearBut.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent arg0) {
            //memoArea.setText(null);
            bottomInfo.setText(ClrButMsg1);
         }
      });
      //memoSubPanel.add(saveBut);
      //memoSubPanel.add(delBut);
      //memoSubPanel.add(clearBut);
      //memoPanel.setLayout(new BorderLayout());
      //memoPanel.add(selectedDate, BorderLayout.NORTH);
      //memoPanel.add(memoAreaSP, BorderLayout.CENTER);
      //memoPanel.add(memoSubPanel, BorderLayout.SOUTH);

      // arrange calOpPanel, calPanel at frameSubPanelWest
      JPanel frameSubPanelWest = new JPanel();
      Dimension calOpPanelSize = calOpPanel.getPreferredSize();
      calOpPanelSize.height = 90;
      calOpPanel.setPreferredSize(calOpPanelSize);
      frameSubPanelWest.setLayout(new BorderLayout());
      frameSubPanelWest.add(calOpPanel, BorderLayout.NORTH);
      frameSubPanelWest.add(calPanel, BorderLayout.CENTER);

      // arrange infoPanel, memoPanel at frameSubPanelEast에 배치
      JPanel frameSubPanelEast = new JPanel();
      Dimension infoPanelSize = infoPanel.getPreferredSize();
      infoPanelSize.height = 65;
      infoPanel.setPreferredSize(infoPanelSize);
      frameSubPanelEast.setLayout(new BorderLayout());
      frameSubPanelEast.add(infoPanel, BorderLayout.NORTH);
      //frameSubPanelEast.add(memoPanel, BorderLayout.CENTER);

      Dimension frameSubPanelWestSize = frameSubPanelWest.getPreferredSize();
      frameSubPanelWestSize.width = 1000;
      frameSubPanelWest.setPreferredSize(frameSubPanelWestSize);

      // late added bottom Panel..
      frameBottomPanel = new JPanel();
      frameBottomPanel.add(bottomInfo);

      // arrange whole layout at the frame
      mainFrame.setLayout(new BorderLayout());
      mainFrame.add(frameSubPanelWest, BorderLayout.WEST);
      mainFrame.add(frameSubPanelEast, BorderLayout.CENTER);
      mainFrame.add(frameBottomPanel, BorderLayout.SOUTH);
      mainFrame.setVisible(true);

      focusToday(); // focus on current date (must be arranged after mainFrame.setVisible(true))

      // activate Thread(clock, bottomMsg deletion after a time)
      ThreadControl threadCnl = new ThreadControl();
      threadCnl.start();
   }

   private void focusToday() {
      if (today.get(Calendar.DAY_OF_WEEK) == 1)
         dateButs[today.get(Calendar.WEEK_OF_MONTH)][today.get(Calendar.DAY_OF_WEEK) - 1].requestFocusInWindow();
      else
         dateButs[today.get(Calendar.WEEK_OF_MONTH) - 1][today.get(Calendar.DAY_OF_WEEK) - 1].requestFocusInWindow();
   }

   /*private void readMemo() {
      try {
         File f = new File("MemoData/" + calYear + ((calMonth + 1) < 10 ? "0" : "") + (calMonth + 1)
               + (calDayOfMon < 10 ? "0" : "") + calDayOfMon + ".txt");
         if (f.exists()) {
            BufferedReader in = new BufferedReader(
                  new FileReader("MemoData/" + calYear + ((calMonth + 1) < 10 ? "0" : "") + (calMonth + 1)
                        + (calDayOfMon < 10 ? "0" : "") + calDayOfMon + ".txt"));
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

   private void showCal() {
      for (int i = 0; i < CAL_HEIGHT; i++) {
         for (int j = 0; j < CAL_WIDTH; j++) {
            String fontColor = "black";
            if (j == 0)
               fontColor = "red";
            else if (j == 6)
               fontColor = "blue";

            File f = new File("MemoData/" + calYear + ((calMonth + 1) < 10 ? "0" : "") + (calMonth + 1)
                  + (calDates[i][j] < 10 ? "0" : "") + calDates[i][j] + ".txt");
            if (f.exists()) {
               dateButs[i][j]
                     .setText("<html><font color=" + fontColor + ">" + calDates[i][j] + "</font></html>");
            } else
               dateButs[i][j].setText("<html><font color=" + fontColor + ">" + calDates[i][j] + "</font></html>");

            JLabel todayMark = new JLabel("<html><font color=green>*</html>");
            dateButs[i][j].removeAll();
            if (calMonth == today.get(Calendar.MONTH) && calYear == today.get(Calendar.YEAR)
                  && calDates[i][j] == today.get(Calendar.DAY_OF_MONTH)) {
               dateButs[i][j].add(todayMark);
               dateButs[i][j].setToolTipText("Today");
            }

            if (calDates[i][j] == 0)
               dateButs[i][j].setVisible(false);
            else
               dateButs[i][j].setVisible(true);
         }
      }
   }

   private class ListenForCalOpButtons implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         if (e.getSource() == todayBut) {
            setToday();
            lForDateButs.actionPerformed(e);
            focusToday();
         } else if (e.getSource() == lYearBut)
            moveMonth(-12);
         else if (e.getSource() == lMonBut)
            moveMonth(-1);
         else if (e.getSource() == nMonBut)
            moveMonth(1);
         else if (e.getSource() == nYearBut)
            moveMonth(12);

         curMMYYYYLab.setText("<html><table width=100><tr><th><font size=5>" + ((calMonth + 1) < 10 ? "&nbsp;" : "")
               + (calMonth + 1) + " / " + calYear + "</th></tr></table></html>");
         showCal();
      }
   }

   private class listenForDateButs implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         int k = 0, l = 0;
         for (int i = 0; i < CAL_HEIGHT; i++) {
            for (int j = 0; j < CAL_WIDTH; j++) {
               if (e.getSource() == dateButs[i][j]) {
                  k = i;
                  l = j;
               }
            }
         }

         if (!(k == 0 && l == 0))
            calDayOfMon = calDates[k][l]; // to activate actionPerformed when press today button

         cal = new GregorianCalendar(calYear, calMonth, calDayOfMon);

         String dDayString = new String();
         int dDay = ((int) ((cal.getTimeInMillis() - today.getTimeInMillis()) / 1000 / 60 / 60 / 24));
         if (dDay == 0 && (cal.get(Calendar.YEAR) == today.get(Calendar.YEAR))
               && (cal.get(Calendar.MONTH) == today.get(Calendar.MONTH))
               && (cal.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH)))
            dDayString = "Today";
         else if (dDay >= 0)
            dDayString = "D-" + (dDay + 1);
         else if (dDay < 0)
            dDayString = "D+" + (dDay) * (-1);

         selectedDate.setText("<Html><font size=3>" + (calMonth + 1) + "/" + calDayOfMon + "/" + calYear + "&nbsp;("
               + dDayString + ")</html>");

         //readMemo();
      }
   }

   private class ThreadControl extends Thread {
      public void run() {
         boolean msgCntFlag = false;
         int num = 0;
         String curStr = new String();
         while (true) {
            try {
               today = Calendar.getInstance();
               String amPm = (today.get(Calendar.AM_PM) == 0 ? "AM" : "PM");
               String hour;
               if (today.get(Calendar.HOUR) == 0)
                  hour = "12";
               else if (today.get(Calendar.HOUR) == 12)
                  hour = " 0";
               else
                  hour = (today.get(Calendar.HOUR) < 10 ? " " : "") + today.get(Calendar.HOUR);
               String min = (today.get(Calendar.MINUTE) < 10 ? "0" : "") + today.get(Calendar.MINUTE);
               String sec = (today.get(Calendar.SECOND) < 10 ? "0" : "") + today.get(Calendar.SECOND);
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
}