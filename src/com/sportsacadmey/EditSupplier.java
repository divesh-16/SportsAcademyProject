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
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class EditSupplier extends JFrame implements ActionListener
{	
	JButton b1,b2,b3,b4;
	JTextArea ta;
	JScrollPane sp;
	
	Connection con=null;
	PreparedStatement ps=null;
	ResultSet rs=null;
	
	EditSupplier()
	{
		setLayout(null);
		
		b1=new JButton("Add Supplier");
		b1.setSize(150,30);
		b1.setLocation(150,50);
		b1.addActionListener(this);
		add(b1);
		
		b2=new JButton("Update Supplier");
		b2.setSize(150,30);
		b2.setLocation(350,50);
		b2.addActionListener(this);
		add(b2);
		
		b3=new JButton("Delete Supplier");
		b3.setSize(150,30);
		b3.setLocation(550,50);
		b3.addActionListener(this);
		add(b3);
		
		b4=new JButton("Show Supplier");
		b4.setSize(150,30);
		b4.setLocation(350,200);
		b4.addActionListener(this);
		add(b4);
		
		
		ta=new JTextArea();
		ta.setSize(650,100);
		ta.setLocation(200,300);
		ta.setEditable(false);
		sp= new JScrollPane(ta);
		sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		sp.setBounds(100,300,650,200);
		sp.getViewport().setBackground(Color.WHITE);
		sp.getViewport().add(ta);
		add(sp);
		
		
		setTitle("Edit Suppliers");
		setSize(900,800);
		setLocation(400,50);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	
	public void actionPerformed(ActionEvent ae)
	{
		String s = (String)ae.getActionCommand();
		
		if(s.equals("Add Supplier"))
		{
			JOptionPane.showMessageDialog(new AddSupplier(),"Directed to ADD Supplier");
		}
		
		if(s.equals("Update Supplier"))
		{
			JOptionPane.showMessageDialog(new UpdateSupplier(),"Directed to Update Supplier");
		}
		
		if(s.equals("Delete Supplier"))
		{
			JOptionPane.showMessageDialog(new DeleteSupplier(),"Directed to Delete Supplier");
		}
		
		if(s.equals("Show Supplier"))
		{
			try
			{
				Class.forName("org.postgresql.Driver");
				con = DriverManager.getConnection("jdbc:postgresql://localhost/collegedb", "root", "root@123");
				ta.setText("SPID\tAID\t\tNAME\t\tADDRESS\n");
							
				ps=con.prepareStatement("select * from supplier");
						
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
					ta.setText("Record not Found !");
				
				}
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}
