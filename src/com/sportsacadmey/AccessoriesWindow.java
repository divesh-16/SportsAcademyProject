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

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class AccessoriesWindow extends JFrame implements ActionListener,ItemListener
{
	JLabel l1,l2,l3;
	JTextField tf1,tf2,tf3;
	JRadioButton rb1,rb2,rb3,rb4,rb5,rb6,rb7,rb8;
	ButtonGroup bg;
	JComboBox cbx;
	JTextArea ta;
	JScrollPane sp;
	JButton b1,b2,b3;
	
	
	Connection con=null;
	PreparedStatement ps=null;
	ResultSet rs=null;
	
	AccessoriesWindow()
	{
		setLayout(null);
		
		l1=new JLabel("Enter ID-NO");
		l1.setSize(150,30);
		l1.setLocation(50,50);
		add(l1);
		
		
		tf1=new JTextField();
		tf1.setSize(150,30);
		tf1.setLocation(500,50);
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
		
		
		l2=new JLabel("Product");
		l2.setSize(150,30);
		l2.setLocation(50,100);
		add(l2);
		
		rb1=new JRadioButton("Football");
		rb1.setSize(100,30);
		rb1.setLocation(50,125);
		rb1.addActionListener(this);
		add(rb1);
		
		rb2=new JRadioButton("Bat");
		rb2.setSize(100,30);
		rb2.setLocation(50,150);
		rb2.addActionListener(this);
		add(rb2);
		
		rb3=new JRadioButton("Hockey Stick");
		rb3.setSize(100,30);
		rb3.setLocation(50,175);
		rb3.addActionListener(this);
		add(rb3);
		
		rb4=new JRadioButton("Shingaurd");
		rb4.setSize(100,30);
		rb4.setLocation(50,200);
		rb4.addActionListener(this);
		add(rb4);
		
		rb5=new JRadioButton("Stockings");
		rb5.setSize(100,30);
		rb5.setLocation(200,125);
		rb5.addActionListener(this);
		add(rb5);
		
		rb6=new JRadioButton("Helmet");
		rb6.setSize(100,30);
		rb6.setLocation(200,150);
		rb6.addActionListener(this);
		add(rb6);
		
		rb7=new JRadioButton("Bottle");
		rb7.setSize(100,30);
		rb7.setLocation(200,175);
		rb7.addActionListener(this);
		add(rb7);
		
		rb8=new JRadioButton("Inners");
		rb8.setSize(100,30);
		rb8.setLocation(200,200);
		rb8.addActionListener(this);
		add(rb8);
		
		bg=new ButtonGroup();
		bg.add(rb1);
		bg.add(rb2);
		bg.add(rb3);
		bg.add(rb4);
		bg.add(rb5);
		bg.add(rb6);
		bg.add(rb7);
		bg.add(rb8);
		
		
		tf2=new JTextField();
		tf2.setSize(150,30);
		tf2.setLocation(500,100);
		tf2.setEditable(false);
		tf2.setBackground(Color.WHITE);
		add(tf2);
		
		String no[]=new String[] {"1","2","3","4","5"};
		cbx=new JComboBox(no);
		cbx.setSize(150,30);
		cbx.addItemListener(this);
		cbx.setLocation(50,320);
		add(cbx);
		
		tf3=new JTextField();
		tf3.setSize(150,30);
		tf3.setLocation(250,320);
		tf3.setEditable(false);
		tf3.setBackground(Color.WHITE);
		add(tf3);
		
		
		b1=new JButton("Confirm Order");
		b1.setSize(150,30);
		b1.setLocation(200,400);
		b1.addActionListener(this);
		add(b1);
		
		b2=new JButton("Cancel");
		b2.setSize(150,30);
		b2.setLocation(400,400);
		b2.addActionListener(this);
		add(b2);
		
		
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
		
		b3=new JButton("Place Order");
		b3.setSize(150,30);
		b3.setLocation(350,720);
		b3.addActionListener(this);
		add(b3);
		
		
				
				
		setTitle("Accessories");
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
			
			if(s.equals("Football") || s.equals("Bat") || s.equals("Hockey Stick") || s.equals("Shingaurd") || s.equals("Stockings") || s.equals("Helmet")|| s.equals("Bottle") || s.equals("Inners"))
			{
				tf2.setText(s);
			}
			
			
			if(s.equals("Confirm Order"))
			{
				try
				{
					String id=tf1.getText();
					String product=tf2.getText();
					String qu=tf3.getText();
					
					if(id.equals("") || product.equals("") || qu.equals(""))
					{
						ta.setText("Please enter all values !");
					}
					else
					{	
						int sid=Integer.parseInt(id);
						int quantity=Integer.parseInt(qu);
						
						
						ps=con.prepareStatement("select * from student where sid=?");
						ps.setInt(1,sid);
						rs=ps.executeQuery();
						
						if(rs.next())
						{
							ta.setText("Order Summary\n\n");
							ta.append("ID-NO\tProduct\tQuantity\tAmount\n");
							if(product.equals("Football"))
							{
								int amt=quantity*600;
								ta.append(id+"\t"+product+"\t"+quantity+"\t"+amt+"\t");
							}
							
							if(product.equals("Bat"))
							{
								int amt=quantity*1200;
								ta.append(id+"\t"+product+"\t"+quantity+"\t"+amt+"\t");
							}
							
							if(product.equals("Hockey Stick"))
							{
								int amt=quantity*1500;
								ta.append(id+"\t"+product+"\t"+quantity+"\t"+amt+"\t");
							}
							
							if(product.equals("Shingaurd"))
							{
								int amt=quantity*300;
								ta.append(id+"\t"+product+"\t"+quantity+"\t"+amt+"\t");
							}
							
							if(product.equals("Stockings"))
							{
								int amt=quantity*400;
								ta.append(id+"\t"+product+"\t"+quantity+"\t"+amt+"\t");
							}
							
							if(product.equals("Helmet"))
							{
								int amt=quantity*1000;
								ta.append(id+"\t"+product+"\t"+quantity+"\t"+amt+"\t");
							}
							
							if(product.equals("Bottle"))
							{
								int amt=quantity*200;
								ta.append(id+"\t"+product+"\t"+quantity+"\t"+amt+"\t");
							}
							
							if(product.equals("Inners"))
							{
								int amt=quantity*1000;
								ta.append(id+"\t"+product+"\t"+quantity+"\t"+amt+"\t");
							}
						}
						else
						{
							ta.setText("Student not found,cannot sell product to people who are not in the Organization !!");
						}
						
						
						
					}
				}
				catch(NumberFormatException nfe)
				{
					JOptionPane.showMessageDialog(null,"Please Enter all Values");
				}
				
				
			}
			
			
			if(s.equals("Cancel"))
			{
				tf1.setText("");
				tf2.setText("");
				tf3.setText("");
				ta.setText("");
				tf1.requestFocus();
			}
			
			
			if(s.equals("Place Order"))
			{
				String id=tf1.getText();
				String product=tf2.getText();
				String qu=tf3.getText();
				
				
				if(id.equals("") || product.equals("") || qu.equals(""))
				{
					ta.setText("Please enter all values !");
				}
				else
				{
					int sid=Integer.parseInt(id);
					int quantity=Integer.parseInt(qu);
					
					
					ps=con.prepareStatement("select * from student where sid=?");
					ps.setInt(1,sid);
					rs=ps.executeQuery();
					if(rs.next())
					{
						if(product.equals("Football"))
						{
							int amt=quantity*600;
							
							JOptionPane.showMessageDialog(new PaymentWindow(sid,product,quantity,amt),"Directed to Payment");
							tf1.setText("");
							tf2.setText("");
							tf3.setText("");
							ta.setText("");
							
						}
						
						if(product.equals("Bat"))
						{
							int amt=quantity*1200;
							JOptionPane.showMessageDialog(new PaymentWindow(sid,product,quantity,amt),"Directed to Payment");
							tf1.setText("");
							tf2.setText("");
							tf3.setText("");
							ta.setText("");
						}
						
						if(product.equals("Hockey Stick"))
						{
							int amt=quantity*1500;
							JOptionPane.showMessageDialog(new PaymentWindow(sid,product,quantity,amt),"Directed to Payment");
							tf1.setText("");
							tf2.setText("");
							tf3.setText("");
							ta.setText("");
						}
						
						if(product.equals("Shingaurd"))
						{
							int amt=quantity*300;
							
							JOptionPane.showMessageDialog(new PaymentWindow(sid,product,quantity,amt),"Directed to Payment");
							tf1.setText("");
							tf2.setText("");
							tf3.setText("");
							ta.setText("");
							
						}
						
						if(product.equals("Stockings"))
						{
							int amt=quantity*400;
							JOptionPane.showMessageDialog(new PaymentWindow(sid,product,quantity,amt),"Directed to Payment");
							tf1.setText("");
							tf2.setText("");
							tf3.setText("");
							ta.setText("");
						}
						
						if(product.equals("Helmet"))
						{
							int amt=quantity*1000;
							JOptionPane.showMessageDialog(new PaymentWindow(sid,product,quantity,amt),"Directed to Payment");
							tf1.setText("");
							tf2.setText("");
							tf3.setText("");
							ta.setText("");
						}
						
						if(product.equals("Bottle"))
						{
							int amt=quantity*200;
							JOptionPane.showMessageDialog(new PaymentWindow(sid,product,quantity,amt),"Directed to Payment");
							tf1.setText("");
							tf2.setText("");
							tf3.setText("");
							ta.setText("");
						}
						
						if(product.equals("Inners"))
						{
							int amt=quantity*1000;
							JOptionPane.showMessageDialog(new PaymentWindow(sid,product,quantity,amt),"Directed to Payment");
							tf1.setText("");
							tf2.setText("");
							tf3.setText("");
							ta.setText("");
						}
					}
					else
					{
						ta.setText("Student not found,cannot sell product to people who are not in the Organization !!");
					}
					
					
				}
				
				
			}
			
			con.close();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void itemStateChanged(ItemEvent ie)
	{
		String s=(String)cbx.getSelectedItem();
		tf3.setText(s);
	}
	
}
