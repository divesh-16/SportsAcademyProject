package com.sportsacadmey;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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

public class EditOrders extends JFrame implements ActionListener,ItemListener
{
	JLabel l1,l2,l3;
	JTextField tf1,tf2,tf3,tf4;
	JComboBox cbx1,cbx2;
	JButton b1,b2,b3;
	
	JTextArea ta;
	JScrollPane sp;
	
	Connection con=null;
	PreparedStatement ps=null;
	ResultSet rs=null;
	
	EditOrders()
	{
		setLayout(null);
		
		l1=new JLabel("Enter Order ID");
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
		
		
		String p[]=new String[] {"paid","unpaid"};
		cbx1=new JComboBox(p);
		cbx1.setSize(150,30);
		cbx1.addItemListener(this);
		cbx1.setLocation(50,150);
		add(cbx1);
		
		tf2=new JTextField();
		tf2.setSize(200,30);
		tf2.setLocation(300,150);
		tf2.setEditable(false);
		tf2.setBackground(Color.WHITE);
		add(tf2);
		
		
		String d[]=new String[] {"delivered","not delivered"};
		cbx2=new JComboBox(d);
		cbx2.setSize(150,30);
		cbx2.addItemListener(this);
		cbx2.setLocation(50,250);
		add(cbx2);
		
		tf3=new JTextField();
		tf3.setSize(200,30);
		tf3.setLocation(300,250);
		tf3.setEditable(false);
		tf3.setBackground(Color.WHITE);
		add(tf3);
		
		
		tf4=new JTextField();
		tf4.setSize(400,30);
		tf4.setLocation(200,400);
		tf4.setEditable(false);
		tf4.setBackground(Color.WHITE);
		add(tf4);
		
		b1=new JButton("Update");
		b1.setSize(150,30);
		b1.setLocation(100,350);
		b1.addActionListener(this);
		add(b1);
		
		b2=new JButton("Cancel");
		b2.setSize(150,30);
		b2.setLocation(300,350);
		b2.addActionListener(this);
		add(b2);
		
		b3=new JButton("Show Orders");
		b3.setSize(150,30);
		b3.setLocation(500,350);
		b3.addActionListener(this);
		add(b3);
		
		
		ta=new JTextArea();
		ta.setSize(700,200);
		ta.setLocation(100,500);
		ta.setEditable(false);
		sp= new JScrollPane(ta);
		sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		sp.setBounds(100,500,700,200);
		sp.getViewport().setBackground(Color.WHITE);
		sp.getViewport().add(ta);
		add(sp);
		
		setTitle("Edit Orders");
		setSize(900,800);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocation(400,50);
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		try
		{
			String s=(String)ae.getActionCommand();
			Class.forName("org.postgresql.Driver");
			con=DriverManager.getConnection("jdbc:postgresql://localhost/collegedb","root","root@123");
			
			if(s.equals("Update"))
			{
				String id=tf1.getText();
				String pay=tf2.getText();
				String delivery=tf3.getText();
				
				if(id.equals("") || pay.equals("") || delivery.equals(""))
				{
					tf4.setText("Please enter all values");
				}
				else
				{
					int oid=Integer.parseInt(id);
					
					ps=con.prepareStatement("update oorder set pay_status=?,delivery_status=? where oid=?");
					ps.setString(1,pay);
					ps.setString(2,delivery);
					ps.setInt(3,oid);
					
					int status = ps.executeUpdate();
					
					if(status==0)
					{
						tf4.setText("Unable to Update!");
						tf1.setText("");
						tf3.setText("");
						tf2.setText("");
						tf1.requestFocus();
					}
					else
					{
						tf4.setText("Record Updated Successfully!");
						tf1.setText("");
						tf3.setText("");
						tf2.setText("");
						tf1.requestFocus();
					}
					
				}
			}
			
			
			if(s.equals("Cancel"))
			{
				tf1.setText("");
				tf3.setText("");
				tf2.setText("");
				dispose();
			}
			
			if(s.equals("Show Orders"))
			{
				ps=con.prepareStatement("select * from oorder order by oid ASC");
				rs=ps.executeQuery();
				if(rs.next())
				{
					ta.setText("OID\tSID\tProduct\tQuantity\tAmount\tPay_Option\tPay_status\tDelivery\n");
					ta.append(rs.getInt("oid")+"\t"+rs.getInt("sid")+"\t"+rs.getString("product_name")+"\t"+rs.getInt("quantity")+"\t"+rs.getDouble("amount")+"\t"+rs.getString("pay_option")+"\t"+rs.getString("pay_status")+"\t"+rs.getString("delivery_status")+"\n");
					while(rs.next())
					{
						ta.append(rs.getInt("oid")+"\t"+rs.getInt("sid")+"\t"+rs.getString("product_name")+"\t"+rs.getInt("quantity")+"\t"+rs.getDouble("amount")+"\t"+rs.getString("pay_option")+"\t"+rs.getString("pay_status")+"\t"+rs.getString("delivery_status")+"\n");
					}
				}
				else
				{
					ta.setText("No Data Found");
				}
			}
			
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void itemStateChanged(ItemEvent ie)
	{
		String s1=(String)cbx1.getSelectedItem();
		tf2.setText(s1);
		
		String s2 = (String)cbx2.getSelectedItem();
		tf3.setText(s2);
	}
}
