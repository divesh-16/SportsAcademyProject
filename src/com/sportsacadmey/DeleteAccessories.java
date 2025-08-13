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

public class DeleteAccessories extends JFrame implements ActionListener 
{
	JLabel l1,l2;
	JTextField tf1,tf2,tf3;
	JButton b1,b2;
	JTextArea ta;
	JScrollPane sp;
	
	Connection con=null;
	PreparedStatement ps=null;
	ResultSet rs=null;
	
	DeleteAccessories()
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
		l2.setLocation(50,90);
		add(l2);
		
		tf2=new JTextField();
		tf2.setSize(150,30);
		tf2.setLocation(200,90);
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
		
		
		
		
		b1=new JButton("Delete");
		b1.setSize(150,30);
		b1.setLocation(100,200);
		b1.addActionListener(this);
		add(b1);
		
		b1=new JButton("Search");
		b1.setSize(150,30);
		b1.setLocation(300,200);
		b1.addActionListener(this);
		add(b1);
		
		
		
		tf3=new JTextField();
		tf3.setSize(300,30);
		tf3.setLocation(500,70);
		tf3.setEditable(false);
		add(tf3);
		
		
		
		ta=new JTextArea();
		ta.setSize(600,350);
		ta.setLocation(180,300);
		ta.setEditable(false);
		sp= new JScrollPane(ta);
		sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		sp.setBounds(180,300,600,350);
		sp.getViewport().setBackground(Color.WHITE);
		sp.getViewport().add(ta);
		add(sp);
		
		
		setTitle("Delete Accessories");
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
			
			if(s.equals("Delete"))
			{
				try
				{	
					
					int cid=Integer.parseInt(tf2.getText());
					
					ps=con.prepareStatement("delete from accessories where aid=?");
					ps.setInt(1,cid);
					
					int status=ps.executeUpdate();
					
					if(status==0)
					{
						tf3.setText("Record Not Found !");
						tf2.setText("");
						tf2.requestFocus();
					}
					else
					{
						tf3.setText("Record deleted Successfully !");
						tf1.requestFocus();
						tf1.setText("");
					}
				}
				catch(NumberFormatException nfe)
				{
					tf3.setText("All values are not Given, Please Enter All values");
				}
			}
			
			if(s.equals("Search"))
			{
				ta.setText("AID\tNAME\t\tSTOCK\tPRICE\n");
				tf3.setText("");
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
						
						ps=con.prepareStatement("select * from accessories where name=?");
						ps.setString(1,nm);
						
						rs=ps.executeQuery();
						
						if(rs.next())
						{
							ta.setText("AID\tNAME\t\tSTOCK\tPRICE\n");
							ta.append("---\t----\t\t---------\t-------\n");
							ta.append(rs.getInt("aid")+"\t"+rs.getString("name")+"\t\t"+rs.getInt("stock")+"\t"+rs.getDouble("price")+"\n");
							while(rs.next())
							{
								ta.append(rs.getInt("aid")+"\t"+rs.getString("name")+"\t\t"+rs.getInt("stock")+"\t"+rs.getDouble("price")+"\n");
							}
							
						}
						else
						{
							ta.setText("AID\tNAME\t\tSTOCK\tPRICE\n");
							tf3.setText("Record not Found !");
							tf1.requestFocus();
						}

					}
									}
				catch(NumberFormatException nfe)
				{
					tf3.setText("Please enter Name to search !");
					tf1.requestFocus();
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
