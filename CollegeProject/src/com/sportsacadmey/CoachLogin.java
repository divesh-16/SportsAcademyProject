package com.sportsacadmey;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class CoachLogin extends JFrame implements ActionListener
{
	JLabel l1,l2;
	JTextField tf1;
	JPasswordField tf2;
	JButton b1,b2;
	Connection con=null;
	PreparedStatement ps=null;
	ResultSet rs=null;
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	CoachLogin()
	{
		setLayout(null);
		
		l1 = new JLabel("Enter ID-NO : ");
		l1.setSize(100,30);
		l1.setLocation(30,30);
		add(l1);
		
		tf1=new JTextField();
		tf1.setSize(200,30);
		tf1.setLocation(150,30);
		add(tf1);
		tf1.addKeyListener(new KeyAdapter()
		{
			public void keyTyped(KeyEvent ke)
			{
				char ch=ke.getKeyChar();
				if(Character.isDigit(ch))
				{
					return;
				}
				ke.setKeyChar('\b');
			}
		});
		
		l2 = new JLabel("Password : ");
		l2.setSize(100,30);
		l2.setLocation(30,80);
		add(l2);
		
		tf2=new JPasswordField();
		tf2.setSize(200,30);
		tf2.setLocation(150,80);
		add(tf2);
		
		b1=new JButton("LOGIN");
		b1.setSize(100,30);
		b1.setLocation(60,130);
		b1.addActionListener(this);
		add(b1);
		
		b2=new JButton("CANCEL");
		b2.setSize(100,30);
		b2.setLocation(200,130);
		b2.addActionListener(this);
		add(b2);
		
		setTitle("Coach Login Screen ");
		setSize(900,800);
		setLocation(400,50);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		try
		{	
			Class.forName("org.postgresql.Driver");
			con=DriverManager.getConnection("jdbc:postgresql://localhost/collegedb","root","root@123");
			
			String s =(String)ae.getActionCommand();
			
			if(s.equals("LOGIN"))
			{
				String id = tf1.getText();
				String password = tf2.getText();
				
				if(id.equals("") || password.equals(""))
				{
					JOptionPane.showMessageDialog(null,"Please Enter all Values");
					tf1.setText("");
					tf2.setText("");
					tf1.requestFocus();
				}
				else
				{
					int cid=Integer.parseInt(id);
					
					ps = con.prepareStatement(" select * from coach where cid=? and password=?");
					ps.setInt(1,cid);
					ps.setString(2,password);
					
					rs=ps.executeQuery();
					
					if(rs.next())
					{
						JOptionPane.showMessageDialog(new CoachWindow(cid,password),"Login Successful !");
						tf1.setText("");
						tf2.setText("");
					}
					else
					{
						JOptionPane.showMessageDialog(null,"Login Failed - Please Enter Valid Username and Password !");
						tf1.setText("");
						tf2.setText("");
						tf1.requestFocus();
					}
				}
				
				
				
			}
			
			if(s.equals("CANCEL"))
			{
				JOptionPane.showMessageDialog(new HomeScreen(),"Login Cancelled !");
			}
			
			con.close();
			rs.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
}
