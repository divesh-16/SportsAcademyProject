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

public class DeleteStudent extends JFrame implements ActionListener 
{
	JLabel l1,l2;
	JTextField tf1,tf2,tf3;
	JButton b1,b2;
	JTextArea ta;
	JScrollPane sp;
	
	Connection con=null;
	PreparedStatement ps=null;
	ResultSet rs=null;
	
	DeleteStudent()
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
		add(tf3);
		
		
		ta=new JTextArea();
		ta.setSize(550,350);
		ta.setLocation(200,300);
		ta.setEditable(false);
		sp= new JScrollPane(ta);
		sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		sp.setBounds(180,300,600,350);
		sp.getViewport().setBackground(Color.WHITE);
		sp.getViewport().add(ta);
		add(sp);
		
		
		
		setTitle("Delete Student");
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
					
					int sid=Integer.parseInt(tf2.getText());
					
					ps=con.prepareStatement("delete from student where sid=?");
					ps.setInt(1,sid);
					
					int status=ps.executeUpdate();
					
					if(status==0)
					{
						tf3.setText("Record Not Found !");
						tf1.requestFocus();
						tf1.setText("");
					}
					else
					{
						tf3.setText("Record deleted Successfully !");
						tf1.setText("");
						tf1.requestFocus();
					}
				}
				catch(NumberFormatException nfe)
				{
					tf3.setText("ID-NO is not given, Please enter ID-NO to delete !");
					tf2.requestFocus();
				}
			}
			
			if(s.equals("Search"))
			{
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
			
						ps=con.prepareStatement("select * from student where name=?");
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
			
			
			
			
			con.close();
	
			
			
		}
		catch(Exception e)
		{
			
			e.printStackTrace();
		}
		
		
	}
}
