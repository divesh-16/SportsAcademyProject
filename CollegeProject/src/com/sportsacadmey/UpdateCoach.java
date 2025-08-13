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

public class UpdateCoach extends JFrame implements ActionListener
{
	JLabel l1,l2,l3,l4,l5;
	JTextField tf1,tf2,tf3,tf4,tf5,tf6;
	JButton b1,b2,b3,b4,b5;
	
	Connection con=null;
	PreparedStatement ps=null;
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	UpdateCoach()
	{
		setLayout(null);
		
		
		l1=new JLabel("Enter Coach ID-No");
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
		tf6.setLocation(250,400);
		tf6.setEditable(false);
		add(tf6);
		
		
		b1=new JButton("Update Name");
		b1.setSize(120,30);
		b1.setLocation(70,300);
		b1.addActionListener(this);
		add(b1);
		
		b2=new JButton("Update Phone.NO");
		b2.setSize(140,30);
		b2.setLocation(200,300);
		b2.addActionListener(this);
		add(b2);
		
		b3=new JButton("Update Address");
		b3.setSize(130,30);
		b3.setLocation(350,300);
		b3.addActionListener(this);
		add(b3);
		
		b4=new JButton("Update Salary");
		b4.setSize(120,30);
		b4.setLocation(500,300);
		b4.addActionListener(this);
		add(b4);
		
		b5=new JButton("Update ALL");
		b5.setSize(120,30);
		b5.setLocation(640,300);
		b5.addActionListener(this);
		add(b5);
		
	
		
		setTitle("Update Coach");
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
					int cid=Integer.parseInt(tf1.getText());
					String nm=tf2.getText();
					if(nm.equals(""))
					{
						tf6.setText("Please Enter Name to Update");
						tf2.requestFocus();
					}
					else
					{
						ps=con.prepareStatement("update coach set name=? where cid=?");
						ps.setString(1,nm);
						ps.setInt(2,cid);
						
						int status=ps.executeUpdate();
						if(status==0)
						{
							tf6.setText("Unable to Update, Record not Found !");
							tf1.setText("");
							tf2.setText("");
							tf1.requestFocus();
						}
						else
						{
							tf6.setText("Record Updated Successfully!");
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
			
			
			if(s.equals("Update Phone.NO"))
			{
				try
				{
					int cid=Integer.parseInt(tf1.getText());
					String phno=tf3.getText();
					if(phno.equals(""))
					{
						tf6.setText("Please enter Phone.NO !");
						tf3.requestFocus();
						
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
						ps=con.prepareStatement("update coach set phoneno=? where cid=?");
						ps.setString(1, phno);
						ps.setInt(2,cid);
						
						int status=ps.executeUpdate();
						if(status==0)
						{
							tf6.setText("Unable to Update, Record not Found !");
							tf1.setText("");
							tf3.setText("");
							tf1.requestFocus();
						}
						else
						{
							tf6.setText("Record Updated Successfully !");
							tf1.setText("");
							tf3.setText("");
							tf1.requestFocus();
						}
					}		
				
				}
				catch(NumberFormatException nfe)
				{
					tf6.setText("All values are not Given, Please Enter All values");
					tf1.requestFocus();
				}
			}
			
			
			
			
			if(s.equals("Update Address"))
			{
				try
				{
					int cid=Integer.parseInt(tf1.getText());
					String adres=tf4.getText();
					if(adres.equals(""))
					{
						tf6.setText("Please enter Address !");
						tf4.requestFocus();
						
					}
					else
					{
						ps=con.prepareStatement("update coach set address=? where cid=?");
						ps.setString(1, adres);
						ps.setInt(2,cid);
						
						int status=ps.executeUpdate();
						if(status==0)
						{
							tf6.setText("Unable to Update, Record not Found !");
							tf1.setText("");
							tf4.setText("");
							tf1.requestFocus();
						}
						else
						{
							tf6.setText("Record Updated Successfully !");
							tf1.setText("");
							tf4.setText("");
							tf1.requestFocus();
						}
					}		
				
				}
				catch(NumberFormatException nfe)
				{
					tf6.setText("All values are not Given, Please Enter All values");
					tf1.requestFocus();
				}
			}
			
			
			if(s.equals("Update Salary"))
			{
				try
				{
					int cid=Integer.parseInt(tf1.getText());
					String sal=tf5.getText();
					
					if(sal.equals(""))
					{
						tf6.setText("Please enter Salary !");
						tf5.requestFocus();
						
					}
					else
					{
						Double salary=Double.parseDouble(tf5.getText());
						ps=con.prepareStatement("update coach set salary=? where cid=?");
						ps.setDouble(1,salary);
						ps.setInt(2,cid);
						
						int status=ps.executeUpdate();
						if(status==0)
						{
							tf6.setText("Unable to Update, Record not Found !");
							tf1.setText("");
							tf5.setText("");
							tf1.requestFocus();
						}
						else
						{
							tf6.setText("Record Updated Successfully !");
							tf1.setText("");
							tf5.setText("");
							tf1.requestFocus();
						}
					}		
				
				}
				catch(NumberFormatException nfe)
				{
					tf6.setText("All values are not Given, Please Enter All values");
					tf1.requestFocus();
				}
			}
			
			
			
			if(s.equals("Update ALL"))
			{
				try
				{
					int cid=Integer.parseInt(tf1.getText());
					String nm=tf2.getText();
					String phno=tf3.getText();
					String adres=tf4.getText();
					Double salary=Double.parseDouble(tf5.getText());
					
					
					if(nm.equals("")|| phno.equals("") || adres.equals(""))
					{
						tf6.setText("All values are not Given, Please Enter All values");
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
						ps=con.prepareStatement("update coach set name=?,phoneno=?,salary=?,address=? where cid=?");
						ps.setString(1,nm);
						ps.setString(2,phno);
						ps.setDouble(3,salary);
						ps.setString(4,adres);
						ps.setInt(5,cid);
						
						int status=ps.executeUpdate();
						if(status==0)
						{
							tf6.setText("Unable to update,Record not found!");
							tf1.setText("");
							tf2.setText("");
							tf3.setText("");
							tf4.setText("");
							tf1.requestFocus();
						}
						else
						{
							tf6.setText("Record updated successfully!");
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
					tf6.setText("All values are not Given, Please Enter All values");
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
