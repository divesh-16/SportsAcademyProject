package com.sportsacadmey;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class EditBatch extends JFrame implements ActionListener,ItemListener
{
	JLabel l1,l2,l3,l4;
	JTextField tf1,tf2,tf3,tf4,tf5;
	JButton b1,b2,b4;
	JComboBox cbx;
	JTextArea ta;
	JScrollPane sp;
	
	Connection con=null;
	PreparedStatement ps=null;
	ResultSet rs=null;
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	EditBatch()
	{
		setLayout(null);
		
		
		
		
		l1=new JLabel("Enter Student ID-No");
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
		
		l2=new JLabel("Enter Coach ID-No");
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
				if(Character.isDigit(ch))
				{
					return;
				}
				ke.setKeyChar('\b');
			}
		});
		
		
		String games[] = new String[]{"football","cricket","hockey"};
		cbx = new JComboBox(games);
		cbx.addItemListener(this);
		cbx.setSize(200,30);
		cbx.setLocation(30,150);
		add(cbx);
		
		tf3=new JTextField();
		tf3.setSize(200,30);
		tf3.setLocation(350,150);
		tf3.setEditable(false);
		add(tf3);
		

		
		tf4=new JTextField();
		tf4.setSize(400,30);
		tf4.setLocation(200,650);
		tf4.setEditable(false);
		add(tf4);
		
		
		b1=new JButton("ADD");
		b1.setSize(100,30);
		b1.setLocation(200,250);
		b1.addActionListener(this);
		add(b1);
		
		b2=new JButton("DELETE");
		b2.setSize(100,30);
		b2.setLocation(350,250);
		b2.addActionListener(this);
		add(b2);
		
		b4=new JButton("SHOW Data");
		b4.setSize(100,30);
		b4.setLocation(500,250);
		b4.addActionListener(this);
		add(b4);
		
		ta=new JTextArea();
		ta.setSize(400,300);
		ta.setLocation(200,300);
		ta.setEditable(false);
		sp= new JScrollPane(ta);
		sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		sp.setBounds(200,300,400,300);
		sp.getViewport().setBackground(Color.WHITE);
		sp.getViewport().add(ta);
		add(sp);
		
	
		
		setTitle("Edit Batch");
		setSize(900,800);
		setLocation(400,50);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		
	}
	
	public void itemStateChanged(ItemEvent ie)
	{
		String s = (String)cbx.getSelectedItem();
		tf3.setText(s);
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		try
		{
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost/collegedb", "root", "root@123");
			
			String s=(String)ae.getActionCommand();
			
			if(s.equals("ADD"))
			{
				try
				{		
					
					
					String sid=tf1.getText();
					String cid=tf2.getText();
					String game=tf3.getText();
					
					
					if(cid.equals("") || sid.equals("") || game.equals(""))
					{
						tf4.setText("All values are not given, Please give all value !");
						tf1.requestFocus();
					}
					else
					{
						int s_id=Integer.parseInt(sid);
						int c_id=Integer.parseInt(cid);
						
						
						ps=con.prepareStatement("insert into games values(?,?,?)");
						ps.setInt(1,s_id);
						ps.setInt(2,c_id);
						ps.setString(3,game);
						
						
						int status=ps.executeUpdate();
						if(status==0)
						{
							tf4.setText("Unable to Insert!");
							tf1.setText("");
							tf2.setText("");
							tf3.setText("");
							
							tf1.requestFocus();
							
						}
						else
						{
							tf4.setText("Record Inserted Successfully!");
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
			
			
			if(s.equals("DELETE"))
			{
				try
				{		
					
					
					String sid=tf1.getText();
					String cid=tf2.getText();
					
					
					
					if(cid.equals("") || sid.equals(""))
					{
						tf4.setText("All values are not given, Please give all value !");
						tf1.requestFocus();
					}
					else
					{
						int s_id=Integer.parseInt(sid);
						int c_id=Integer.parseInt(cid);
						
						
						ps=con.prepareStatement("delete from games where sid=? and cid=?");
						ps.setInt(1,s_id);
						ps.setInt(2,c_id);
			
									
						int status=ps.executeUpdate();
						if(status==0)
						{
							tf4.setText("Unable to Delete!");
							tf1.setText("");
							tf2.setText("");
							tf3.setText("");
							
							tf1.requestFocus();
							
						}
						else
						{
							tf4.setText("Record Deleted Successfully!");
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
					
			
			if(s.equals("SHOW Data"))
			{
				ta.setText("SID\tCID\tGAME\n");
				tf1.setText("");
				tf2.setText("");
				tf3.setText("");
				try
				{
					tf1.setText("");
					tf2.setText("");
					tf3.setText("");
					tf1.requestFocus();
					
					ps=con.prepareStatement("select * from games order by sid ASC");
					
					rs=ps.executeQuery();
					
					ta.setText("SID\tCID\tGAME\n");
					ta.append("---\t---\t----\n");
					
					if(rs.next())
					{
						ta.append(rs.getInt("sid")+"\t"+rs.getInt("cid")+"\t"+rs.getString("game")+"\n");
						
						while(rs.next())
						{
							ta.append(rs.getInt("sid")+"\t"+rs.getInt("cid")+"\t"+rs.getString("game")+"\n");
						}
						
					}
					else
					{
						ta.setText("SID\tCID\tGAME\n");
						tf4.setText("No Records in DataBase !");
						tf1.requestFocus();
					}
					
					
					
				}
				catch(NumberFormatException nfe)
				{
					tf4.setText("Please enter data to search !");
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
