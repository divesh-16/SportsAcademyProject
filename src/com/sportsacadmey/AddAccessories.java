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

public class AddAccessories extends JFrame implements ActionListener
{
	JLabel l1,l2,l3,l4;
	JTextField tf1,tf2,tf3,tf4,tf5;
	JButton b;
	
	Connection con=null;
	PreparedStatement ps=null;
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	AddAccessories()
	{
		setLayout(null);
		
		
		l2=new JLabel("Enter Accessories Name");
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
		
		l3=new JLabel("Enter Accessories Stock");
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
		
		
		l4=new JLabel("Enter Price");
		l4.setSize(300,30);
		l4.setLocation(30,200);
		add(l4);
		
		tf4=new JTextField();
		tf4.setSize(200,30);
		tf4.setLocation(350,200);
		add(tf4);
		tf4.addKeyListener(new KeyAdapter()
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
					String s=tf4.getText();
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
		
		
		tf5=new JTextField();
		tf5.setSize(500,30);
		tf5.setLocation(250,450);
		tf5.setEditable(false);
		add(tf5);
		
		
		b=new JButton("ADD");
		b.setSize(100,30);
		b.setLocation(400,300);
		b.addActionListener(this);
		add(b);
		
	
		
		setTitle("Add Accessories");
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
					String stock=tf3.getText();
					String p=tf4.getText();
					
					if(nm.equals("") || stock.equals("") || p.equals(""))
					{
						tf5.setText("All values are not given, Please give all value !");
						tf2.requestFocus();
					}
					else
					{
						int stk=Integer.parseInt(stock);
						double price=Double.parseDouble(p);
						
						ps=con.prepareStatement("insert into accessories values(default,?,?,?);");
						ps.setString(1,nm);
						ps.setInt(2,stk);
						ps.setDouble(3,price);
						
						int status=ps.executeUpdate();
						if(status==0)
						{
							tf5.setText("Unable to Insert!");
							tf2.setText("");
							tf3.setText("");
							tf4.setText("");
							tf2.requestFocus();
							
						}
						else
						{
							tf5.setText("Record Inserted Successfully!");
							tf2.setText("");
							tf3.setText("");
							tf4.setText("");
							tf2.requestFocus();
							
						}
					}
					
					
					
					
				}
				catch(NumberFormatException nfe)
				{
					tf5.setText("All values are not Given, Please Enter All values");
					tf2.requestFocus();
				}
				catch(PSQLException psql)
				{
					tf5.setText("Amount Should be greater than 0");
					tf4.setText("");
					tf4.requestFocus();
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
