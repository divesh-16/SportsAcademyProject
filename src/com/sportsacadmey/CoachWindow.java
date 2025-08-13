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
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CoachWindow extends JFrame implements ActionListener
{
	int cid;
	String password;
	
	
	JLabel l1,l2;
	JTextField tf1,tf3;
	JPasswordField tf2;
	JTextArea ta;
	JScrollPane sp;
	JButton b1,b2,b3,b4;
	
	Connection con=null;
	PreparedStatement ps=null;
	ResultSet rs=null;
	
	
	
	CoachWindow(int cid,String password)
	{
		this.cid=cid;
		this.password=password;
		
		setLayout(null);
			
		
		b1=new JButton("My Data");
		b1.setSize(100,30);
		b1.setLocation(100,170);
		b1.addActionListener(this);
		b1.setBackground(Color.BLUE);
		b1.setForeground(Color.GREEN);
		add(b1);
		
		b2=new JButton("Student Names");
		b2.setSize(120,30);
		b2.setLocation(250,170);
		b2.addActionListener(this);
		b2.setBackground(Color.BLUE);
		b2.setForeground(Color.GREEN);
		add(b2);
		
		b3=new JButton("Student Data");
		b3.setSize(110,30);
		b3.setLocation(420,170);
		b3.addActionListener(this);
		b3.setBackground(Color.BLUE);
		b3.setForeground(Color.GREEN);
		add(b3);
		
		b4=new JButton("Change Password");
		b4.setSize(140,30);
		b4.setLocation(570,170);
		b4.addActionListener(this);
		b4.setBackground(Color.BLUE);
		b4.setForeground(Color.GREEN);
		add(b4);
		
		
		ta=new JTextArea();
		ta.setSize(650,350);
		ta.setLocation(100,250);
		ta.setEditable(false);
		sp= new JScrollPane(ta);
		sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		sp.setBounds(100,250,650,350);
		sp.getViewport().setBackground(Color.WHITE);
		sp.getViewport().add(ta);
		add(sp);
		
		tf3=new JTextField();
		tf3.setSize(300,30);
		tf3.setLocation(300,650);
		tf3.setEditable(false);
		tf3.setBackground(Color.WHITE);
		add(tf3);
		
		
		setTitle("Coach Window");
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
			
			String s=ae.getActionCommand();
			
			
			if(s.equals("My Data"))
			{
				ta.setText("CID\tNAME\t\tPHONE.NO\tSalary\t\tADDRESS\n");
				tf3.setText("");
					
				ps=con.prepareStatement("select * from coach where cid=?");
				ps.setInt(1,cid);
						
				rs=ps.executeQuery();
						
				if(rs.next())
				{
						
					tf3.setText("Showing Coach's Data");
					ta.setText("CID\tNAME\t\tPHONE.NO\tSalary\t\tADDRESS\n");
					ta.append("---\t----\t\t----------\t-------\t\t-------\n");
					ta.append(rs.getInt("cid")+"\t"+rs.getString("name")+"\t\t"+rs.getString("phoneno")+"\t"+rs.getDouble("salary")+"\t\t"+rs.getString("address")+"\n");
					while(rs.next())
					{
						ta.append(rs.getInt("cid")+"\t"+rs.getString("name")+"\t\t"+rs.getString("phoneno")+"\t"+rs.getDouble("salary")+"\t\t"+rs.getString("address")+"\n");
					}
						
				}
				else
				{
					ta.setText("CID\tNAME\t\tPHONE.NO\tSalary\t\tADDRESS\n");
					tf3.setText("Record not Found !");							
					tf1.requestFocus();
				}
				
			}
			
			
			
			if(s.equals("Student Names"))
			{
				ta.setText("Student Names\n");
				ta.append("------------------\n");
				tf3.setText("");
				
				ps=con.prepareStatement("select student.name from student,coach,games where student.sid=games.sid and coach.cid=games.cid and coach.cid=?");
				ps.setInt(1,cid);
						
				rs=ps.executeQuery();
						
				if(rs.next())
				{
					tf3.setText("Showing Coach's Students Names");
					ta.setText("Student Names\n");
					ta.append("------------------\n");
					ta.append(rs.getString("name")+"\n");
					while(rs.next())
					{
						ta.append(rs.getString("name")+"\n");
					}
							
				}
				else
				{
					ta.setText("Student Names\n");
					tf3.setText("Record not Found !");
				}
			
			}
			
			
			
			
			if(s.equals("Student Data"))
			{
				
				tf3.setText("");
				ta.setText("SID\tNAME\t\tPHONE.NO\t\tADDRESS\n");
		
					
				tf3.setText("");
						
					
				ps=con.prepareStatement(" select student.sid,student.name,student.phoneno,student.address from student,coach,games where student.sid=games.sid and coach.cid=games.cid and coach.cid=?");
				ps.setInt(1,cid);
				rs=ps.executeQuery();
					
				ta.setText("SID\tNAME\t\tPHONE.NO\t\tADDRESS\n");
				ta.append("---\t----\t\t---------\t\t-------\n");
					
				if(rs.next())
				{
					tf3.setText("Showing Coach's Students Data");
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
				
				}
							
			}
			
			
			if(s.equals("Change Password"))
			{
				JOptionPane.showMessageDialog(new ChangeCoachPassword(),"Directed to change Password");
			}
			
			
			
			
			con.close();

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
