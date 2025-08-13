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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.postgresql.util.PSQLException;

public class AddCoach extends JFrame implements ActionListener
{
	JLabel l1,l2,l3,l4,l5;
	JTextField tf1,tf2,tf3,tf4,tf5,tf6;
	JButton b;
	
	Connection con=null;
	PreparedStatement ps=null;
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	AddCoach()
	{
		setLayout(null);
		
		
		l2=new JLabel("Enter Coach Name");
		l2.setSize(300,30);
		l2.setLocation(30,100);
		add(l2);
		
		tf2=new JTextField();
		tf2.setSize(200,30);
		tf2.setLocation(350,100);
		add(tf2);
		tf2.addKeyListener(new KeyAdapter()
		{
			public void keyTyped(KeyEvent ke)
			{
				char ch=ke.getKeyChar();
				if(Character.isLetter(ch) || ch==' ')
				{
					return;
				}
				ke.setKeyChar('\b');
			}
		});
		
		l3=new JLabel("Enter Coach Phone.NO");
		l3.setSize(300,30);
		l3.setLocation(30,150);
		add(l3);
		
		tf3=new JTextField();
		tf3.setSize(200,30);
		tf3.setLocation(350,150);
		add(tf3);
		tf3.addKeyListener(new KeyAdapter()
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
		
		l4=new JLabel("Enter Coach Address");
		l4.setSize(300,30);
		l4.setLocation(30,200);
		add(l4);
		
		tf4=new JTextField();
		tf4.setSize(200,30);
		tf4.setLocation(350,200);
		add(tf4);
		
		
		l5=new JLabel("Enter Salary");
		l5.setSize(300,30);
		l5.setLocation(30,250);
		add(l5);
		
		tf5=new JTextField();
		tf5.setSize(200,30);
		tf5.setLocation(350,250);
		add(tf5);
		tf5.addKeyListener(new KeyAdapter()
		{
			public void keyTyped(KeyEvent ke)
			{
				char ch=ke.getKeyChar();
				if(Character.isDigit(ch))
				{
					return;
				}
				
				if(ch=='.')
				{
					String s=tf5.getText();
					if(s.contains("."))
					{
						ke.setKeyChar('\b');
						return;
					}
					
					return;
				}
				
				ke.setKeyChar('\b');
			}
		});
		
		
		tf6=new JTextField();
		tf6.setSize(500,30);
		tf6.setLocation(250,450);
		tf6.setEditable(false);
		add(tf6);
		
		
		b=new JButton("ADD");
		b.setSize(100,30);
		b.setLocation(400,350);
		b.addActionListener(this);
		add(b);
		
	
		
		setTitle("Add Coach");
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
			con = DriverManager.getConnection("jdbc:postgresql://localhost/collegedb", "root", "root@123");
			
			String s=(String)ae.getActionCommand();
			
			if(s.equals("ADD"))
			{
				try
				{		
					
					String nm=tf2.getText();
					String phno=tf3.getText();
					String adres=tf4.getText();
					String salary = tf5.getText();
					Double sal=Double.parseDouble(salary);
		
					if(nm.equals("")|| phno.equals("") || adres.equals("") || salary.equals(""))
					{
						tf5.setText("All values are not Given, Please Enter All values");
					}
					else if(phno.length()<10)
					{
						tf6.setText("Phone.NO have less than 10 Numbers, Please enter valid number !");
						tf3.setText("");
						tf3.requestFocus();
					}
					else if(phno.length()>10)
					{
						tf6.setText("Phone.NO have more than 10 Numbers, Please enter valid number !");
						tf3.setText("");
						tf3.requestFocus();
					}
					else
					{
						ps=con.prepareStatement("insert into coach values(default,?,?,?,?,default);");
						ps.setString(1, nm);
						ps.setString(2,phno);
						ps.setDouble(3,sal);
						ps.setString(4,adres);
						
						
						int status=ps.executeUpdate();
						if(status==0)
						{
							tf6.setText("Unable to Insert!");
							tf2.setText("");
							tf3.setText("");
							tf4.setText("");
							tf5.setText("");
							tf1.requestFocus();
						}
						else
						{
							tf6.setText("Record Inserted Successfully!");
							tf2.setText("");
							tf3.setText("");
							tf4.setText("");
							tf5.setText("");
							tf2.requestFocus();
						}
					}
					
				}
				catch(NumberFormatException nfe)
				{
					tf6.setText("All values are not Given, Please Enter All values");
					tf2.requestFocus();
					
				}
				catch(PSQLException psql)
				{
					tf6.setText("Salary Should be greater than 0");
					tf5.setText("");
					tf5.requestFocus();
				}
				
				
			}
			
			
			
			
			
			con.close();
			
		}
		catch(Exception e)
		{
			
			e.printStackTrace();
		}
		
	}
}
