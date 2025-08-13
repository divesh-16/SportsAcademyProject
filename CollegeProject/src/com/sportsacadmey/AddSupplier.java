package com.sportsacadmey;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class AddSupplier extends JFrame implements ActionListener
{
	JLabel l1,l2,l3;
	JTextField tf1,tf2,tf3,tf4;
	JButton b1,b2,b3;
	
	Connection con=null;
	PreparedStatement ps=null;
	
	AddSupplier()
	{
		setLayout(null);
		
		
		l1=new JLabel("Enter Accessories ID ");
		l1.setSize(150,30);
		l1.setLocation(50,50);
		add(l1);
		
		tf1=new JTextField();
		tf1.setSize(200,30);
		tf1.setLocation(300,50);
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
		
		
		l2=new JLabel("Enter Supplier Name");
		l2.setSize(150,30);
		l2.setLocation(50,100);
		add(l2);
		
		tf2=new JTextField();
		tf2.setSize(200,30);
		tf2.setLocation(300,100);
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
		
		l3=new JLabel("Enter Supplier Address");
		l3.setSize(150,30);
		l3.setLocation(50,150);
		add(l3);
		
		tf3=new JTextField();
		tf3.setSize(200,30);
		tf3.setLocation(300,150);
		add(tf3);
		
		
		b1=new JButton("ADD");
		b1.setSize(150,30);
		b1.setLocation(200,350);
		b1.addActionListener(this);
		add(b1);
		
		b2=new JButton("CANCEL");
		b2.setSize(150,30);
		b2.setLocation(400,350);
		b2.addActionListener(this);
		add(b2);
		
		
		tf4=new JTextField();
		tf4.setSize(400,30);
		tf4.setLocation(200,500);
		tf4.setEditable(false);
		tf4.setBackground(Color.WHITE);
		add(tf4);
		
		
		
		
		setTitle("Add Suppliers");
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
			
			String s = (String)ae.getActionCommand();
			
			if(s.equals("ADD"))
			{
				try
				{
					String id = tf1.getText();
					String name = tf2.getText();
					String address=tf3.getText();
					
					if(name.equals("")|| id.equals("") || address.equals(""))
					{
						tf4.setText("All values are not Given, Please Enter All values");
					}
					else
					{
						int aid=Integer.parseInt(id);
						
						ps=con.prepareStatement("insert into supplier values(default,?,?,?)");
						ps.setInt(1,aid);
						ps.setString(2,name);
						ps.setString(3,address);
						
						int status=ps.executeUpdate();
						if(status==0)
						{
							tf4.setText("Unable to Insert!");
							tf1.setText("");
							tf3.setText("");
							tf2.setText("");
							tf1.requestFocus();
						}
						else
						{
							tf4.setText("Record Inserted Successfully!");
							tf1.setText("");
							tf3.setText("");
							tf2.setText("");
							tf1.requestFocus();
						}
						
						
						
					}
					
				}
				catch(NumberFormatException nfe)
				{
					tf4.setText("All values are not Given, Please Enter All values");
					tf1.requestFocus();
				}
			}
			
			if(s.equals("CANCEL"))
			{
				tf1.setText("");
				tf3.setText("");
				tf2.setText("");
				tf1.requestFocus();
				dispose();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
}
