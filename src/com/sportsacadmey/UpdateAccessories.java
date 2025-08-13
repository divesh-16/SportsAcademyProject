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

public class UpdateAccessories extends JFrame implements ActionListener
{
	JLabel l1,l2,l3,l4;
	JTextField tf1,tf2,tf3,tf4,tf5;
	JButton b1,b2,b3,b4;
	
	Connection con=null;
	PreparedStatement ps=null;
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	UpdateAccessories()
	{
		setLayout(null);
		
		
		l1=new JLabel("Enter Accessories ID-No");
		l1.setSize(300,30);
		l1.setLocation(30,50);
		add(l1);
		
		tf1=new JTextField();
		tf1.setSize(200,30);
		tf1.setLocation(350,50);
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
		
		l3=new JLabel("Enter Stock");
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
		
		
		
		tf4=new JTextField();
		tf4.setSize(500,30);
		tf4.setLocation(250,400);
		tf4.setEditable(false);
		add(tf4);
		
		
		b1=new JButton("Update Name");
		b1.setSize(150,30);
		b1.setLocation(70,300);
		b1.addActionListener(this);
		add(b1);
		
		b2=new JButton("Update Stock");
		b2.setSize(150,30);
		b2.setLocation(270,300);
		b2.addActionListener(this);
		add(b2);
		
		b3=new JButton("Update ALL");
		b3.setSize(150,30);
		b3.setLocation(470,300);
		b3.addActionListener(this);
		add(b3);
		
	
		
		setTitle("Update Student");
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
			
			if(s.equals("Update Name"))
			{
				try
				{	
					int aid=Integer.parseInt(tf1.getText());
					String nm=tf2.getText();
					if(nm.equals(""))
					{
						tf4.setText("Please Enter Name to Update");
						tf2.requestFocus();
					}
					else
					{
						ps=con.prepareStatement("update accessories set name=? where aid=?");
						ps.setString(1,nm);
						ps.setInt(2,aid);
						
						int status=ps.executeUpdate();
						if(status==0)
						{
							tf4.setText("Unable to Update, Record not Found !");
							tf1.setText("");
							tf2.setText("");
							tf1.requestFocus();
						}
						else
						{
							tf4.setText("Record Updated Successfully!");
							tf1.setText("");
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
			
			
			if(s.equals("Update Stock"))
			{
				try
				{
					int aid=Integer.parseInt(tf1.getText());
					String stk=tf3.getText();
					if(stk.equals(""))
					{
						tf4.setText("Please enter Stock !");
						tf3.requestFocus();
						
					}
					else
					{
						int stock=Integer.parseInt(stk);
						ps=con.prepareStatement("update accessories set stock=? where aid=?");
						ps.setInt(1,stock);
						ps.setInt(2,aid);
						
						int status=ps.executeUpdate();
						if(status==0)
						{
							tf4.setText("Unable to Update, Record not Found !");
							tf1.setText("");
							tf3.setText("");
							tf1.requestFocus();
						}
						else
						{
							tf4.setText("Record Updated Successfully !");
							tf1.setText("");
							tf3.setText("");
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
			
				
			if(s.equals("Update ALL"))
			{
				try
				{
					int aid=Integer.parseInt(tf1.getText());
					String nm=tf2.getText();
					String stk=tf3.getText();
			
					
					if(nm.equals("")|| stk.equals(""))
					{
						tf4.setText("All values are not Given, Please Enter All values");
						tf2.requestFocus();
					}
					else
					{
						int stock = Integer.parseInt(stk);
						ps=con.prepareStatement("update accessories set name=?,stock=? where aid=?");
						ps.setString(1,nm);
						ps.setInt(2,stock);
						ps.setInt(3,aid);
						
						int status=ps.executeUpdate();
						if(status==0)
						{
							tf4.setText("Unable to update,Record not found!");
							tf1.setText("");
							tf2.setText("");
							tf3.setText("");
							tf1.requestFocus();
						}
						else
						{
							tf4.setText("Record updated successfully!");
							tf1.setText("");
							tf2.setText("");
							tf3.setText("");
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
			
			
			
			
			con.close();
			ps.close();
			
		}
		catch(Exception e)
		{
			
			e.printStackTrace();
		}
		
	}
}
