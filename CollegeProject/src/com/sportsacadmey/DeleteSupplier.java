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

public class DeleteSupplier extends JFrame implements ActionListener
{
	JLabel l1,l2;
	JTextField tf1,tf2,tf3;
	JButton b1,b2;
	JTextArea ta;
	JScrollPane sp;
	
	Connection con=null;
	PreparedStatement ps=null;
	ResultSet rs=null;
	
	DeleteSupplier()
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
		
		
		l2=new JLabel("Enter ID to Delete: ");
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
		tf3.setBackground(Color.WHITE);
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
		
		
		
		setTitle("Delete Coach");
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
			
			
			if(s.equals("Search"))
			{
				try
				{
					ta.setText("SPID\tAID\t\tNAME\t\tADDRESS\n");
					tf3.setText("");
					String name=tf1.getText();
					if(name.equals(""))
					{
						tf3.setText("Please enter all values");
					}
					else
					{
						ps=con.prepareStatement("select * from supplier where name=?");
						ps.setString(1,name);
						
						rs=ps.executeQuery();
						
						if(rs.next())
						{
							ta.setText("SPID\tAID\t\tNAME\t\tADDRESS\n");
							ta.append("---\t----\t\t----------\t\t-------\n");
							ta.append(rs.getInt("spid")+"\t"+rs.getInt("aid")+"\t\t"+rs.getString("name")+"\t\t"+rs.getString("address")+"\n");
							while(rs.next())
							{
								ta.append(rs.getInt("spid")+"\t"+rs.getInt("aid")+"\t\t"+rs.getString("name")+"\t\t"+rs.getString("address")+"\n");
							}
							
						}
						else
						{
							ta.setText("SPID\tAID\t\tNAME\t\tADDRESS\n");
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
			
			
			if(s.equals("Delete"))
			{
				try
				{
					String id = tf2.getText();
					if(id.equals(""))
					{
						tf3.setText("Please enter all values");
					}
					else
					{
						int spid=Integer.parseInt(id);
						
						ps=con.prepareStatement("delete from supplier where spid=?");
						ps.setInt(1,spid);
						
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
							tf2.setText("");
							tf1.setText("");
						}
					}
				}
				catch(NumberFormatException nfe)
				{
					tf3.setText("Please enter Name to search !");
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
