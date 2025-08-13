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

public class UpdateSupplier extends JFrame implements ActionListener 
{	
	JLabel l1,l2,l3,l4;
	JTextField tf1,tf2,tf3,tf4,tf5;
	JButton b1,b2,b3,b4;
	
	Connection con=null;
	PreparedStatement ps=null;
	
	UpdateSupplier()
	{
		setLayout(null);
		
		l1=new JLabel("Enter Supplier ID ");
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

		l2=new JLabel("Enter Accessories ID ");
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
				if(Character.isDigit(ch))
				{
					return;
				}
				ke.setKeyChar('\b');
			}
		});

		
		l3=new JLabel("Enter Supplier Name");
		l3.setSize(150,30);
		l3.setLocation(50,150);
		add(l3);
		
		tf3=new JTextField();
		tf3.setSize(200,30);
		tf3.setLocation(300,150);
		add(tf3);
		tf3.addKeyListener(new KeyAdapter()
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
		
		l4=new JLabel("Enter Supplier Address");
		l4.setSize(150,30);
		l4.setLocation(50,200);
		add(l4);
		
		tf4=new JTextField();
		tf4.setSize(200,30);
		tf4.setLocation(300,200);
		add(tf4);
		
		tf5=new JTextField();
		tf5.setSize(400,30);
		tf5.setLocation(200,500);
		tf5.setEditable(false);
		tf5.setBackground(Color.WHITE);
		add(tf5);
		
		b1=new JButton("Update Accessories");
		b1.setSize(150,30);
		b1.setLocation(70,300);
		b1.addActionListener(this);
		add(b1);
		
		b2=new JButton("Update Name");
		b2.setSize(150,30);
		b2.setLocation(270,300);
		b2.addActionListener(this);
		add(b2);
		
		b3=new JButton("Update Address");
		b3.setSize(150,30);
		b3.setLocation(470,300);
		b3.addActionListener(this);
		add(b3);
		
		b4=new JButton("Update ALL");
		b4.setSize(150,30);
		b4.setLocation(670,300);
		b4.addActionListener(this);
		add(b4);
		
		setTitle("Update Suppliers");
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
			
			if(s.equals("Update Accessories"))
			{
				try
				{
					String id1=tf1.getText();
					String id2=tf2.getText();
					if(id1.equals("") || id2.equals(""))
					{
						tf5.setText("Please enter all values");
					}
					else
					{
						int spid=Integer.parseInt(id1);
						int aid=Integer.parseInt(id2);
						
						ps=con.prepareStatement("update supplier set aid=? where spid=?");
						ps.setInt(1,aid);
						ps.setInt(2,spid);
						
						int status=ps.executeUpdate();
						if(status==0)
						{
							tf5.setText("Unable to Update, Record not Found !");
							tf1.setText("");
							tf2.setText("");
							tf1.requestFocus();
						}
						else
						{
							tf5.setText("Record Updated Successfully!");
							tf1.setText("");
							tf2.setText("");
							tf1.requestFocus();
						}
					}
				}
				catch(NumberFormatException nfe)
				{
					tf5.setText("All values are not Given, Please Enter All values");
					tf1.requestFocus();
				}
			}
			
			if(s.equals("Update Name"))
			{
				try
				{
					String id = tf1.getText();
					String name = tf3.getText();
					
					if(id.equals("") || name.equals(""))
					{
						tf5.setText("Please enter all values");
					}
					else
					{
						int spid=Integer.parseInt(id);
						ps=con.prepareStatement("update supplier set name=? where spid=?");
						ps.setString(1,name);
						ps.setInt(2,spid);
						
						int status=ps.executeUpdate();
						if(status==0)
						{
							tf5.setText("Unable to Update, Record not Found !");
							tf1.setText("");
							tf3.setText("");
							tf1.requestFocus();
						}
						else
						{
							tf5.setText("Record Updated Successfully!");
							tf1.setText("");
							tf3.setText("");
							tf1.requestFocus();
						}
						
					}
				}
				catch(NumberFormatException nfe)
				{
					tf5.setText("All values are not Given, Please Enter All values");
					tf1.requestFocus();
				}
			}
			
			if(s.equals("Update Address"))
			{
				try
				{
					String id = tf1.getText();
					String address = tf4.getText();
					
					if(id.equals("") || address.equals(""))
					{
						tf5.setText("Please enter all values");
					}
					else
					{
						int spid=Integer.parseInt(id);
						ps=con.prepareStatement("update supplier set address=? where spid=?");
						ps.setString(1,address);
						ps.setInt(2,spid);
						
						int status=ps.executeUpdate();
						if(status==0)
						{
							tf5.setText("Unable to Update, Record not Found !");
							tf1.setText("");
							tf4.setText("");
							tf1.requestFocus();
						}
						else
						{
							tf5.setText("Record Updated Successfully!");
							tf1.setText("");
							tf4.setText("");
							tf1.requestFocus();
						}
						
					}
					
					
				}
				catch(NumberFormatException nfe)
				{
					tf5.setText("All values are not Given, Please Enter All values");
					tf1.requestFocus();
				}
			}
			
			
			if(s.equals("Update ALL"))
			{
				try
				{
					String id1=tf1.getText();
					String id2=tf2.getText();
					String name=tf3.getText();
					String address=tf4.getText();
					
					if(id1.equals("") || id2.equals("") || name.equals("") || address.equals(""))
					{
						tf5.setText("Please enter all values");
					}
					else
					{
						int spid=Integer.parseInt(id1);
						int aid=Integer.parseInt(id2);
						ps=con.prepareStatement("update supplier set aid=?,name=?,address=? where spid=?");
						ps.setInt(1,aid);
						ps.setString(2,name);
						ps.setString(3,address);
						ps.setInt(4,spid);
						
						int status=ps.executeUpdate();
						if(status==0)
						{
							tf5.setText("Unable to Update, Record not Found !");
							tf1.setText("");
							tf2.setText("");
							tf3.setText("");
							tf4.setText("");
							tf1.requestFocus();
						}
						else
						{
							tf5.setText("Record Updated Successfully!");
							tf1.setText("");
							tf2.setText("");
							tf3.setText("");
							tf4.setText("");
							tf1.requestFocus();
						}
					}
				}
				catch(NumberFormatException nfe)
				{
					tf5.setText("All values are not Given, Please Enter All values");
					tf1.requestFocus();
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
