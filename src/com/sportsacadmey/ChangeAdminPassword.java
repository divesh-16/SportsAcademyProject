package com.sportsacadmey;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class ChangeAdminPassword extends JFrame implements ActionListener
{
	JLabel l1,l2,l3,l4;
	JTextField tf1,tf5;
	JPasswordField tf2,tf3,tf4;
	JButton b1,b2;
	
	Connection con=null;
	PreparedStatement ps=null;
	ResultSet rs=null;
	
	ChangeAdminPassword()
	{
		setLayout(null);
		
		l1=new JLabel("Enter your ID-NO");
		l1.setSize(150,30);
		l1.setLocation(100,50);
		add(l1);
		
		tf1=new JTextField();
		tf1.setSize(200,30);
		tf1.setLocation(250,50);
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
		
		
		l2=new JLabel("Enter Old Password");
		l2.setSize(150,30);
		l2.setLocation(100,100);
		add(l2);
		
		tf2=new JPasswordField();
		tf2.setSize(200,30);
		tf2.setLocation(250,100);
		add(tf2);
		
		l3=new JLabel("Enter New Password");
		l3.setSize(150,30);
		l3.setLocation(100,150);
		add(l3);
		
		tf3=new JPasswordField();
		tf3.setSize(200,30);
		tf3.setLocation(250,150);
		add(tf3);
		
		l4=new JLabel("Confirm Password");
		l4.setSize(150,30);
		l4.setLocation(100,200);
		add(l4);
		
		tf4=new JPasswordField();
		tf4.setSize(200,30);
		tf4.setLocation(250,200);
		add(tf4);
		
		
		b1=new JButton("Change");
		b1.setSize(150,30);
		b1.setLocation(200,300);
		b1.addActionListener(this);
		add(b1);
		
		b2=new JButton("Cancel");
		b2.setSize(150,30);
		b2.setLocation(400,300);
		b2.addActionListener(this);
		add(b2);
		
		tf5=new JTextField();
		tf5.setSize(400,30);
		tf5.setLocation(200,400);
		tf5.setEditable(false);
		tf5.setBackground(Color.WHITE);
		add(tf5);
		
		
		setTitle("Change Admin Password");
		setSize(900,800);
		setLocation(400,50);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	

	public void actionPerformed(ActionEvent ae) 
	{
		try
		{
			String s=ae.getActionCommand();
			
			Class.forName("org.postgresql.Driver");
			con= DriverManager.getConnection("jdbc:postgresql://localhost/collegedb", "root", "root@123");
			
			if(s.equals("Change"))
			{
				try
				{
					String id=tf1.getText();
					String oldP=tf2.getText();
					String newP=tf3.getText();
					String confirmP=tf4.getText();
					
					if(id.equals("") || oldP.equals("") || newP.equals("") || confirmP.equals(""))
					{
						tf5.setText("Please enter all values !");
					}
					else
					{
						int aid=Integer.parseInt(id);
						
						ps=con.prepareStatement("select * from admin where id=? and password=?");
						ps.setInt(1,aid);
						ps.setString(2,oldP);
						
						rs=ps.executeQuery();
						
						if(rs.next())
						{
							if(newP.equals(confirmP))
							{
								ps=con.prepareStatement("update admin set password=? where id=?");
								ps.setString(1, confirmP);
								ps.setInt(2,aid);
								
								int status=ps.executeUpdate();
								if(status==0)
								{
									tf5.setText("Unable to update");
								}
								else
								{
									tf5.setText("Password Changed Successfully!");
									tf1.setText("");
									tf2.setText("");
									tf3.setText("");
									tf4.setText("");
									tf1.requestFocus();
								}
							}
							else
							{
								tf4.setText("");
								tf4.requestFocus();
								tf5.setText("Please Confirm your Password");
							}
						}
						else
						{
							tf5.setText("Incorrect ID-Password,Please Check your and Password !");
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
			
			
			
			if(s.equals("Cancel"))
			{
				tf5.setText("");
				tf1.setText("");
				tf1.requestFocus();
				tf2.setText("");
				tf3.setText("");
				tf4.setText("");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
