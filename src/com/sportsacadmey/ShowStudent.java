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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ShowStudent extends JFrame implements ActionListener
{
	JTextArea ta;
	JLabel l1,l2;
	JTextField tf1,tf2,tf3;
	JButton b1,b2,b3,b4;
	JScrollPane sp;
	
	Connection con=null;
	PreparedStatement ps=null;
	ResultSet rs=null;
	
	ShowStudent()
	{
		setLayout(null);
		
		l1=new JLabel("Enter Name to Search: ");
		l1.setSize(150,30);
		l1.setLocation(50,30);
		add(l1);
		
		tf1=new JTextField();
		tf1.setSize(150,30);
		tf1.setLocation(200,30);
		add(tf1);
		tf1.addKeyListener(new KeyAdapter()
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
		
		
		l2=new JLabel("Enter ID to Search: ");
		l2.setSize(150,30);
		l2.setLocation(50,60);
		add(l2);
		
		tf2=new JTextField();
		tf2.setSize(150,30);
		tf2.setLocation(200,60);
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
		
		
		
		ta=new JTextArea();
		ta.setSize(550,350);
		ta.setLocation(200,150);
		ta.setEditable(false);
		sp= new JScrollPane(ta);
		sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		sp.setBounds(200,150,550,350);
		sp.getViewport().setBackground(Color.WHITE);
		sp.getViewport().add(ta);
		add(sp);
		
		
		b1=new JButton("Search Name");
		b1.setSize(150,30);
		b1.setLocation(100,550);
		b1.addActionListener(this);
		add(b1);
		
		b2=new JButton("Search ID");
		b2.setSize(150,30);
		b2.setLocation(300,550);
		b2.addActionListener(this);
		add(b2);
		
		b3=new JButton("Search Name & ID");
		b3.setSize(150,30);
		b3.setLocation(500,550);
		b3.addActionListener(this);
		add(b3);
		
		b4=new JButton("Show All");
		b4.setSize(150,30);
		b4.setLocation(700,550);
		b4.addActionListener(this);
		add(b4);
		
		tf3=new JTextField();
		tf3.setSize(300,30);
		tf3.setLocation(300,650);
		tf3.setEditable(false);
		tf3.setBackground(Color.WHITE);
		add(tf3);
		
		
		
		setTitle("Show Student");
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
			
			String s = ae.getActionCommand();
			
			if(s.equals("Search Name"))
			{
				tf2.setText("");
				tf3.setText("");
				ta.setText("SID\tNAME\t\tPHONE.NO\t\tADDRESS\n");
				try
				{
					String nm=tf1.getText();
					if(nm.equals(""))
					{
						tf3.setText("Please enter Name to search !");
						tf1.requestFocus();
					}
					else
					{
						ps=con.prepareStatement("select * from student where name=? order by sid ASC");
						ps.setString(1,nm);
						
						rs=ps.executeQuery();
						
						if(rs.next())
						{
							ta.setText("SID\tNAME\t\tPHONE.NO\t\tADDRESS\n");
							ta.append("---\t----\t\t---------\t\t-------\n");
							ta.append(rs.getInt("sid")+"\t"+rs.getString("name")+"\t\t"+rs.getString("phoneno")+"\t\t"+rs.getString("address")+"\n");
							while(rs.next())
							{
								ta.append(rs.getInt("sid")+"\t"+rs.getString("name")+"\t\t"+rs.getString("phoneno")+"\t\t"+rs.getString("address")+"\n");
							}
							
						}
						else
						{
							ta.setText("SID\tNAME\t\tPHONE.NO\t\tADDRESS\n");
							tf3.setText("Record not Found !");
							tf1.requestFocus();
						}
					}
					
				}
				catch(NumberFormatException nfe)
				{
					tf3.setText("Please enter data to search !");
					tf1.requestFocus();
				}
			}
			
			
			if(s.equals("Search ID"))
			{
				tf1.setText("");
				tf3.setText("");
				ta.setText("SID\tNAME\t\tPHONE.NO\t\tADDRESS\n");
				try
				{
					String id=tf2.getText();
					
					if(id.equals(""))
					{
						tf3.setText("Please enter ID-No to search !");
						tf1.requestFocus();
					}
					else
					{
						int sid=Integer.parseInt(id);

						ps=con.prepareStatement("select * from student where sid=?");
						ps.setInt(1,sid);
						
						rs=ps.executeQuery();
						
						if(rs.next())
						{
							ta.setText("SID\tNAME\t\tPHONE.NO\t\tADDRESS\n");
							ta.append("---\t----\t\t---------\t\t-------\n");
							ta.append(rs.getInt("sid")+"\t"+rs.getString("name")+"\t\t"+rs.getString("phoneno")+"\t\t"+rs.getString("address")+"\n");
							while(rs.next())
							{
								ta.append(rs.getInt("sid")+"\t"+rs.getString("name")+"\t\t"+rs.getString("phoneno")+"\t\t"+rs.getString("address")+"\n");
							}
							
						}
						else
						{
							ta.setText("SID\tNAME\t\tPHONE.NO\t\tADDRESS\n");
							tf3.setText("Record not Found !");
							tf1.requestFocus();
						}
					}
				}
				catch(NumberFormatException nfe)
				{
					tf3.setText("Please enter data to search !");
					tf1.requestFocus();
				}
			}
			
			if(s.equals("Search Name & ID"))
			{
				tf3.setText("");
				ta.setText("SID\tNAME\t\tPHONE.NO\t\tADDRESS\n");
				try
				{
					String nm=tf1.getText();
					String id=tf2.getText();
					
					if(id.equals("") || nm.equals(""))
					{
						tf3.setText("Please enter Name/ID-No to search !");
						tf1.requestFocus();
					}
					else
					{
						int sid=Integer.parseInt(id);
						ps=con.prepareStatement("select * from student where sid=? and name=?");
						ps.setInt(1,sid);
						ps.setString(2,nm);
						
						rs=ps.executeQuery();
						
						if(rs.next())
						{
							ta.setText("SID\tNAME\t\tPHONE.NO\t\tADDRESS\n");
							ta.append("---\t----\t\t---------\t\t-------\n");
							ta.append(rs.getInt("sid")+"\t"+rs.getString("name")+"\t\t"+rs.getString("phoneno")+"\t\t"+rs.getString("address")+"\n");
							while(rs.next())
							{
								ta.append(rs.getInt("sid")+"\t"+rs.getString("name")+"\t\t"+rs.getString("phoneno")+"\t\t"+rs.getString("address")+"\n");
							}
							
						}
						else
						{
							ta.setText("SID\tNAME\t\tPHONE.NO\t\tADDRESS\n");
							tf3.setText("Record not Found !");
							tf1.requestFocus();
						}

					}
									}
				catch(NumberFormatException nfe)
				{
					tf3.setText("Please enter data to search !");
					tf1.requestFocus();
				}
			}
			
			
			if(s.equals("Show All"))
			{
				tf1.setText("");
				tf2.setText("");
				tf3.setText("");
				ta.setText("SID\tNAME\t\tPHONE.NO\t\tADDRESS\n");
				try
				{
					tf1.setText("");
					tf2.setText("");
					tf3.setText("");
					tf1.requestFocus();
					
					ps=con.prepareStatement("select * from student order by sid ASC");
					
					rs=ps.executeQuery();
					
					ta.setText("SID\tNAME\t\tPHONE.NO\t\tADDRESS\n");
					ta.append("---\t----\t\t---------\t\t-------\n");
					
					if(rs.next())
					{
						ta.append(rs.getInt("sid")+"\t"+rs.getString("name")+"\t\t"+rs.getString("phoneno")+"\t\t"+rs.getString("address")+"\n");
						
						while(rs.next())
						{
							ta.append(rs.getInt("sid")+"\t"+rs.getString("name")+"\t\t"+rs.getString("phoneno")+"\t\t"+rs.getString("address")+"\n");
						}
						
					}
					else
					{
						ta.setText("SID\tNAME\t\tPHONE.NO\t\tADDRESS\n");
						tf3.setText("No Records in DataBase !");
						tf1.requestFocus();
					}
					
					
					
				}
				catch(NumberFormatException nfe)
				{
					tf3.setText("Please enter data to search !");
					tf1.requestFocus();
				}
			}
			
			
			con.close();
			ps.close();
			rs.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
