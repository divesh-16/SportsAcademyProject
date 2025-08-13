package com.sportsacadmey;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.postgresql.util.PSQLException;

public class PaymentWindow extends JFrame implements ActionListener
{
	JLabel l1,l2;
	JTextField tf1,tf2;
	JButton b1,b2;
	
	JRadioButton rb1,rb2;
	ButtonGroup bg;
	JTextArea ta;
	JScrollPane sp;
	
	Connection con=null;
	PreparedStatement ps=null;
	ResultSet rs=null;
	
	int sid,quantity;
	double amount;
	String product;
	
	PaymentWindow(int sid,String product,int quantity,double amount)
	{
		this.sid=sid;
		this.quantity=quantity;
		this.amount=amount;
		this.product=product;
		
		setLayout(null);
		
		l1=new JLabel("Amount ");
		l1.setSize(150,30);
		l1.setLocation(50,50);
		add(l1);
		
		tf1=new JTextField();
		tf1.setSize(150,30);
		tf1.setLocation(250,50);
		tf1.setEditable(false);
		tf1.setBackground(Color.WHITE);
		add(tf1);
		tf1.setText(""+amount+"");
		
		
		l2=new JLabel("Payment Option");
		l2.setSize(150,30);
		l2.setLocation(50,100);
		add(l2);
		
		rb1=new JRadioButton("COD");
		rb1.setSize(100,30);
		rb1.setLocation(50,125);
		rb1.addActionListener(this);
		add(rb1);
		
		rb2=new JRadioButton("Card");
		rb2.setSize(100,30);
		rb2.setLocation(50,150);
		rb2.addActionListener(this);
		add(rb2);
		
		bg=new ButtonGroup();
		bg.add(rb1);
		bg.add(rb2);
		
		tf2=new JTextField();
		tf2.setSize(150,30);
		tf2.setLocation(250,100);
		tf2.setEditable(false);
		tf2.setBackground(Color.WHITE);
		add(tf2);
		
		
		b1=new JButton("Ok");
		b1.setSize(150,30);
		b1.setLocation(200,300);
		b1.addActionListener(this);
		add(b1);
		
		b2=new JButton("Cancel");
		b2.setSize(150,30);
		b2.setLocation(400,300);
		b2.addActionListener(this);
		add(b2);
		
		ta=new JTextArea();
		ta.setSize(700,200);
		ta.setLocation(100,400);
		ta.setEditable(false);
		sp= new JScrollPane(ta);
		sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		sp.setBounds(100,400,700,200);
		sp.getViewport().setBackground(Color.WHITE);
		sp.getViewport().add(ta);
		add(sp);
		
		setTitle("Payment");
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
			if(s.equals("COD") || s.equals("Card"))
			{
				tf2.setText(s);
			}
			
			Class.forName("org.postgresql.Driver");
			con=DriverManager.getConnection("jdbc:postgresql://localhost/collegedb","root","root@123");
			String payment=tf2.getText();
			
			if(s.equals("Ok"))
			{
				try
				{
					ps=con.prepareStatement("update accessories set stock=stock-"+quantity+" where name=?");
					ps.setString(1,product);
					
					int updateStatus=ps.executeUpdate();
					if(updateStatus==0)
					{
						ta.setText("Unable to place order !");
					}
					else
					{
						if(payment.equals("COD"))
						{
							String payStatus="unpaid";
							
							ps=con.prepareStatement("insert into oorder values(default,?,?,?,?,?,?,default) returning oid",Statement.RETURN_GENERATED_KEYS);
							ps.setInt(1,sid);
							ps.setString(2,product);
							ps.setInt(3,quantity);
							ps.setDouble(4,amount);
							ps.setString(5,payment);
							ps.setString(6,payStatus);
							
							int insertStatus=ps.executeUpdate();
							if(insertStatus==0)
							{
								ta.setText("Unable to place order !");
							}
							else
							{
								rs=ps.getGeneratedKeys();
								if(rs.next())
								{
									int oid=rs.getInt("oid");
									ta.setText("Hooray! Order Placed !!\n\n");
									ta.append("Order Summary\n\n");
									
									ps=con.prepareStatement("select student.name,student.phoneno,student.address,oorder.oid,oorder.product_name,quantity,amount,pay_option from student,oorder where oorder.sid=student.sid and oid=?");
									ps.setInt(1, oid);
									
									rs=ps.executeQuery();
									if(rs.next())
									{
										ta.append("NAME\tPHONE.NO\tADDRESS\tORDER NO\tPRODUCT\tQUANTITY\tAMOUNT\tPayment\n");
										ta.append("----\t-------\t---------\t---\t-----------\t--------\t-------\t------\n");
										String name=rs.getString("name");
										String phno=rs.getString("phoneno");
										String adres=rs.getString("address");
										String ono=rs.getString("oid");
										String p=rs.getString("product_name");
										String q=rs.getString("quantity");
										String amt=rs.getString("amount");
										String pay=rs.getString("pay_option");
										ta.append(name+"\t"+phno+"\t"+adres+"\t"+ono+"\t"+p+"\t"+q+"\t"+amt+"\t"+pay+"\n\n");
										ta.append("Collect your order from our office after 5 workings days !!");
										
									}
									
									
								}
								else
								{
									ta.setText("Unable to place order Football!");
									
								}
							}
						}
						
						if(payment.equals("Card"))
						{
							JOptionPane.showMessageDialog(new CardPayment(sid,product,quantity,amount,payment),"Directed to Card Payment");
							tf1.setText("");
							tf2.setText("");
						}
						
						
						
					}
				}
				catch(NumberFormatException nfe)
				{
					JOptionPane.showMessageDialog(null,"Please Enter all Values");
				}
				catch(PSQLException psql)
				{
					ta.setText("We are currently out-of-stock,Please reach out to us later !");
				}
			}
			
			
		
			
			if(s.equals("Cancel"))
			{
				tf1.setText("");
				tf2.setText("");
				ta.setText("");
				dispose();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
}
