package edu.handong.csee.java.sw3;

import java.io.*;
import java.awt.*;
import java.awt.event.*;

class Memo extends WindowAdapter implements ActionListener{
 private Frame f;
 private TextArea pad;

 private FileDialog loadDl;
 private FileDialog saveDl;

 private MenuBar mb;
 private Menu file;
 private MenuItem newItem;
 private MenuItem open;
 private MenuItem save;
 private MenuItem saveAs;
 private MenuItem setPage;
 private MenuItem print;
 private MenuItem exit;
 private Menu edit;
 private MenuItem cancel;
 private MenuItem cut;
 private MenuItem copy;
 private MenuItem paste;
 private MenuItem delete;
 private MenuItem search;
 private MenuItem searchNext;
 private MenuItem change;
 private MenuItem move;
 private MenuItem selectAll;
 private MenuItem time;
 private Menu form;
 private MenuItem nextln;
 private MenuItem font;
 private Menu view;
 private MenuItem statusBar;
 private Menu help;
 private MenuItem helpMenu;
 private MenuItem info;

 private Dimension dim1;
 private Dimension dim2;
 private int xpos;
 private int ypos;

 private File ffile;
 private PrintWriter out;
 private BufferedReader in;
 private String filePath;
 private String str;
 private String loadedStr;
 private String saveStr;

 public Memo(){
  this.f = new Frame("���� ���� - �޸���");
  this.pad = new TextArea("", 1, 1, pad.SCROLLBARS_BOTH);

  this.loadDl = new FileDialog(f, "����", loadDl.LOAD);
  this.saveDl = new FileDialog(f, "�ٸ� �̸����� ����", saveDl.SAVE);

  this.mb = new MenuBar();
  this.file = new Menu("����");
  this.newItem = new MenuItem("���� �����");
  this.open = new MenuItem("����");
  this.save = new MenuItem("����");
  this.saveAs = new MenuItem("�ٸ� �̸����� ����");
  this.setPage = new MenuItem("������ ����");
  this.print = new MenuItem("�μ�");
  this.exit = new MenuItem("������");
  this.edit = new Menu("����");
  this.cancel = new MenuItem("���� ���");
  this.cut = new MenuItem("�߶󳻱�");
  this.copy = new MenuItem("����");
  this.paste = new MenuItem("�ٿ��ֱ�");
  this.delete = new MenuItem("����");
  this.search = new MenuItem("ã��");
  this.searchNext = new MenuItem("���� ã��");
  this.change = new MenuItem("�ٲٱ�");
  this.move = new MenuItem("�̵�");
  this.selectAll = new MenuItem("��� ����");
  this.time = new MenuItem("�ð�/��¥");
  this.form = new Menu("����");
  this.nextln = new MenuItem("�ڵ� �� �ٲ�");
  this.font = new MenuItem("�۲�");
  this.view = new Menu("����");
  this.statusBar = new MenuItem("����ǥ����");
  this.help = new Menu("����");
  this.helpMenu = new MenuItem("���� �׸�");
  this.info = new MenuItem("�޸��� ����");

  f.setSize(600, 400);
  this.dim1 = Toolkit.getDefaultToolkit().getScreenSize();
  this.dim2 = f.getSize();
  this.xpos = (int)(dim1.getWidth() / 2 - dim2.getWidth() / 2);
  this.ypos = (int)(dim1.getHeight() / 2 - dim2.getHeight() / 2);

  init();
  start();
  f.setLocation(xpos, ypos);
  f.setVisible(true);

  this.out = null;
  this.in = null;
 }

 public void init(){
  //set TextArea
  pad.setFont(new Font("Fixedsys", Font.PLAIN, 15));
  f.add(pad);

  //set MenuBar
  f.setMenuBar(mb);
  mb.add(file);
  file.add(newItem);
  file.add(open);
  file.add(save);
  file.add(saveAs);
  file.addSeparator();
  file.add(setPage);
  file.add(print);
  file.addSeparator();
  file.add(exit);
  mb.add(edit);
  edit.add(cancel);
  edit.addSeparator();
  edit.add(cut);
  edit.add(copy);
  edit.add(paste);
  edit.add(delete);
  edit.addSeparator();
  edit.add(search);
  edit.add(searchNext);
  edit.add(change);
  edit.add(move);
  edit.addSeparator();
  edit.add(selectAll);
  edit.add(time);
  mb.add(form);
  form.add(nextln);
  form.add(font);
  mb.add(view);
  view.add(statusBar);
  mb.add(help);
  help.add(helpMenu);
  help.addSeparator();
  help.add(info);
 }

 public void start(){
  f.addWindowListener(this);
  newItem.addActionListener(this);
  open.addActionListener(this);
  save.addActionListener(this);
  saveAs.addActionListener(this);
  exit.addActionListener(this);
 }

 public void windowClosing(WindowEvent we){
  f.dispose();
 }

 public void actionPerformed(ActionEvent ae){
  if(ae.getSource() == newItem){
   pad.setText("");
   f.setTitle("���� ���� - �޸���");
  }else if(ae.getSource() == open){
   loadDl.setVisible(true);
   filePath = loadDl.getDirectory() + loadDl.getFile();
   loadFromFile();
   pad.setText(saveStr);
   saveStr = "";
   f.setTitle(filePath + " - �޸���");
  }else if(ae.getSource() == save){
   saveDl.setVisible(true);
   filePath = saveDl.getDirectory() + saveDl.getFile();
   str = pad.getText();
   saveToFile();
   f.setTitle(filePath + " - �޸���");
  }else if(ae.getSource() == saveAs){
   saveDl.setVisible(true);
   filePath = saveDl.getDirectory() + saveDl.getFile();
   str = pad.getText();
   saveToFile();
   f.setTitle(filePath + " - �޸���");
  }else if(ae.getSource() == exit){
   f.dispose();
  }
 }

 //File I/O
 public void saveToFile(){
  ffile = new File(filePath);

  try{
   out = new PrintWriter(new BufferedWriter(new FileWriter(ffile)));
   out.write(str);
   out.close();
  }catch(Exception e){
   e.printStackTrace();
  }
 }

 public void loadFromFile(){
  ffile = new File(filePath);
  saveStr = "";

  try{
   in = new BufferedReader(new FileReader(ffile));

   while((loadedStr = in.readLine()) != null){
    saveStr += loadedStr + "\n";
   }

   in.close();
  }catch(Exception e){
   e.printStackTrace();
  }
 }
}