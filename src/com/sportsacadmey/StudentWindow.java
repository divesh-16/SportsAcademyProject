package com.sportsacadmey;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class StudentWindow extends JFrame implements ActionListener
{
	JLabel l1,l2,l3,l4;
	JButton b1,b2,b3,b4;
	JTextArea ta;
	JScrollPane sp;
	
	private int sid;
	private String password;
	
	
	Connection con=null;
	PreparedStatement ps=null;
	ResultSet rs=null;
	
	
	StudentWindow(int sid,String password)
	{
		this.sid=sid;
		this.password=password;
		setLayout(null);
		
		l1=new JLabel("Batch Timinigs : ");
		l1.setSize(100,30);
		l1.setLocation(50,30);
		add(l1);
		
		l2=new JLabel("Cricket : 4-5 pm");
		l2.setSize(100,30);
		l2.setLocation(150,30);
		add(l2);
		
		l3=new JLabel("Hockey : 5-6 pm");
		l3.setSize(100,30);
		l3.setLocation(250,30);
		add(l3);
		
		l4=new JLabel("Football : 6-7 pm");
		l4.setSize(100,30);
		l4.setLocation(350,30);
		add(l4);
		
		ta=new JTextArea();
		ta.setSize(600,200);
		ta.setLocation(150,200);
		ta.setEditable(false);
		sp= new JScrollPane(ta);
		sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		sp.setBounds(150,200,600,200);
		sp.getViewport().setBackground(Color.WHITE);
		sp.getViewport().add(ta);
		add(sp);
		
		
		b1=new JButton("My Details");
		b1.setSize(150,30);
		b1.setLocation(50,500);
		b1.addActionListener(this);
		add(b1);
		
		b2=new JButton("Coach Name");
		b2.setSize(150,30);
		b2.setLocation(250,500);
		b2.addActionListener(this);
		add(b2);
		
		b3=new JButton("Sports Name");
		b3.setSize(150,30);
		b3.setLocation(450,500);
		b3.addActionListener(this);
		add(b3);
		
		b4=new JButton("Change Password");
		b4.setSize(150,30);
		b4.setLocation(650,500);
		b4.addActionListener(this);
		add(b4);
		
		
		setTitle("Student Window");
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
			
			
			
			if(s.equals("My Details"))
			{
				
				ps=con.prepareStatement("select * from student where sid=?");
				ps.setInt(1,sid);
				
				rs=ps.executeQuery();
				
				ta.setText("SID\tNAME\t\tPHONE.NO\t\tADDRESS\n");
				ta.append("---\t----\t\t---------\t\t-------\n");
				if(rs.next())
				{
					ta.append(rs.getInt("sid")+"\t"+rs.getString("name")+"\t\t"+rs.getString("phoneno")+"\t\t"+rs.getString("address")+"\n");
				
				}
				else
				{
					ta.setText("SID\tNAME\t\tPHONE.NO\t\tADDRESS\n");
					ta.append("No data Found !");
				}
			}
			
			if(s.equals("Coach Name"))
			{
				ps=con.prepareStatement("select coach.name from student,coach,games where student.sid=games.sid and coach.cid=games.cid and student.sid=?");
				ps.setInt(1,sid);
				
				rs=ps.executeQuery();
				
				ta.setText("Coach Name\n");
				ta.append("-----------\n");
				if(rs.next())
				{
					ta.append(rs.getString("name"));
				
				}
				else
				{
					ta.setText("Coach Name\n");
					ta.append("No data Found !");
				}
			}
			
			if(s.equals("Sports Name"))
			{
				ps=con.prepareStatement(" select game from student,coach,games where student.sid=games.sid and coach.cid=games.cid and student.sid=?");
				ps.setInt(1,sid);
				
				rs=ps.executeQuery();
				
				ta.setText("Game Name\n");
				ta.append("-----------\n");
				if(rs.next())
				{
					ta.append(rs.getString("game"));
				
				}
				else
				{
					ta.setText("Game Name\n");
					ta.append("No data Found !");
				}
			}
			
			if(s.equals("Change Password"))
			{
				JOptionPane.showMessageDialog(new ChangeStudentPassword(),"Directed to change Password");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
