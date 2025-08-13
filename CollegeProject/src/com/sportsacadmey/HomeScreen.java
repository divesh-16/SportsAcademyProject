package com.sportsacadmey;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class HomeScreen extends JFrame implements ActionListener
{
	JMenuBar mbr;
	JMenu login;
	JMenuItem admin,coach,student,exit;
	
	JPanel panel;
	ImageIcon icon; 
    JLabel label;
    
    Container c;
    JPanel p;
    
    JLabel l1;
    JButton b1;
    
	
	
	BufferedReader br =new BufferedReader(new InputStreamReader(System.in));
	
	HomeScreen() throws IOException
	{
		setLayout(null);
		
		
		/*panel=new JPanel();
		panel.setSize(900,800);
		panel.setLocation(400,50);
		panel
		add(panel);*/
		
	
		mbr=new JMenuBar();
		mbr.setSize(900,30);
		mbr.setLocation(0,0);
		add(mbr);
		
		login=new JMenu("Login");
		mbr.add(login);
		
		admin=new JMenuItem("Admin");
		admin.addActionListener(this);
		login.add(admin);
		
		coach=new JMenuItem("Coach");
		coach.addActionListener(this);
		login.add(coach);
		
		student=new JMenuItem("Student");
		student.addActionListener(this);
		login.add(student);
		login.addSeparator();
		
		exit=new JMenuItem("Exit");
		exit.addActionListener(this);
		login.add(exit);
		
		l1=new JLabel("Try Accessories");
		l1.setSize(300,40);
		l1.setForeground(Color.BLUE);
		l1.setLocation(350,500);
		add(l1);
		
		b1=new JButton("Accessories");
		b1.setSize(200,30);
		b1.setLocation(300,550);
		b1.addActionListener(this);
		add(b1);
		
		setTitle("Sports Academy Pvt.LTD");
		setSize(900,800);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(400,50);
			
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		String s=(String) ae.getActionCommand();
		
		if(s.equals("Admin"))
		{
			JOptionPane.showMessageDialog(new AdminLogin(),"Admin Plese Enter your ID and Password");
		}
		
		if(s.equals("Coach"))
		{
			JOptionPane.showMessageDialog(new CoachLogin(),"Coach Plese Enter your ID and Password");
		}
		
		if(s.equals("Student"))
		{
			JOptionPane.showMessageDialog(new StudentLogin(),"Student Plese Enter your ID and Password");
		}
		
		if(s.equals("Exit"))
		{
			JOptionPane.showMessageDialog(null,"Good Day !!");
			System.exit(0);
		}
		
		if(s.equals("Accessories"))
		{
			JOptionPane.showMessageDialog(new AccessoriesWindow(),"Directed to accessories");
		}
	}	
}
