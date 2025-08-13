package com.sportsacadmey;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CardPayment extends JFrame implements ActionListener
{
	JLabel l1,l2,l3;
	JTextField tf1,tf2,tf3;
	
	JButton b1,b2;
	JTextArea ta;
	JScrollPane sp;
	
	Connection con=null;
	PreparedStatement ps=null;
	ResultSet rs=null;
	
	int sid,quantity;
	double amount;
	String product;
	String payment;
	
	CardPayment(int sid,String product,int quantity,double amount,String payment)
	{
		this.sid=sid;
		this.quantity=quantity;
		this.amount=amount;
		this.product=product;
		this.payment=payment;
		
		setLayout(null);
		
		l1=new JLabel("Card Number ");
		l1.setSize(200,30);
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
		
		l2=new JLabel("Card Holder Name");
		l2.setSize(200,30);
		l2.setLocation(50,100);
		add(l2);
		
		tf2=new JTextField();
		tf2.setSize(200,30);
		tf2.setLocation(300,100);
		add(tf2);
		tf2.addKeyListener(new KeyAdapter()
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
		
		l3=new JLabel("CVV");
		l3.setSize(200,30);
		l3.setLocation(50,150);
		add(l3);
		
		tf3=new JTextField();
		tf3.setSize(200,30);
		tf3.setLocation(300,150);
		add(tf3);
		tf3.addKeyListener(new KeyAdapter()
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
		
		
		b1=new JButton("Pay");
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
		ta.setLineWrap(true);
		sp= new JScrollPane(ta);
		sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		sp.setBounds(100,400,700,200);
		sp.getViewport().setBackground(Color.WHITE);
		sp.getViewport().add(ta);
		add(sp);
		
		
		setTitle("Card Payment");
		setSize(900,800);
		setVisible(true);
		setLocation(400,50);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		try
		{
			String s=(String)ae.getActionCommand();
			
			Class.forName("org.postgresql.Driver");
			con=DriverManager.getConnection("jdbc:postgresql://localhost/collegedb","root","root@123");
			
			if(s.equals("Pay"))
			{
				String number=tf1.getText();
				String cName = tf2.getText();
				String cvv = tf3.getText();
				
				if(number.equals("") || cName.equals("") || cvv.equals(""))
				{
					JOptionPane.showMessageDialog(null,"Please Enter all Values");
				}
				else if(cvv.length()>3)
				{
					JOptionPane.showMessageDialog(null,"CVV must be 3 Digit number");
					tf1.setText("");
					tf2.setText("");
					tf3.setText("");
					tf1.requestFocus();
				}
				else if(number.length()!=16)
				{
					JOptionPane.showMessageDialog(null,"Account Number must be 16 Digit number");
					tf1.setText("");
					tf2.setText("");
					tf3.setText("");
					tf1.requestFocus();
				}
				else
				{
					String payStatus="paid";
					
				
					
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
								tf1.setText("");
								tf2.setText("");
								tf3.setText("");
							}
							
							
						}
						else
						{
							ta.setText("Unable to place order Football!");
							
						}
					}
				}
				
			}
			
			if(s.equals("Cancel"))
			{
				tf1.setText("");
				tf2.setText("");
				tf3.setText("");
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
