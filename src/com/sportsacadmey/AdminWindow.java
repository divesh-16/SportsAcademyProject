package com.sportsacadmey;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
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

public class AdminWindow extends JFrame implements ActionListener
{
	int id;
	String password;
	
	
	JButton addStudent,deleteStudent,updateStudent,showStudent;
	JButton addCoach,deleteCoach,updateCoach,showCoach;
	JButton addAccessories,deleteAccessories,updateAccessories,showAccessories;
	JButton editBatch,mydata,chpw,supplier,editOrder,reports;
	JLabel l1,l2,l3,l4,l5,l6,l7,l8;
	
	JTextArea ta;
	JScrollPane sp;
	
	Connection con=null;
	PreparedStatement ps=null;
	ResultSet rs=null;
	
	AdminWindow(int id,String password)
	{
		this.id=id;
		this.password=password;
		setLayout(null);
		
		l1=new JLabel("STUDENT");
		l1.setSize(100,30);
		l1.setLocation(100,30);
		add(l1);
		
		addStudent=new JButton("ADD Student");
		addStudent.setSize(150,30);
		addStudent.setLocation(100,80);
		addStudent.addActionListener(this);
		addStudent.setBackground(Color.BLUE);
		addStudent.setForeground(Color.GREEN);
		add(addStudent);
		
		deleteStudent=new JButton("DELETE Student");
		deleteStudent.setSize(150,30);
		deleteStudent.setLocation(100,130);
		deleteStudent.addActionListener(this);
		deleteStudent.setBackground(Color.BLUE);
		deleteStudent.setForeground(Color.GREEN);
		add(deleteStudent);
		
		updateStudent=new JButton("UPDATE Student");
		updateStudent.setSize(150,30);
		updateStudent.setLocation(100,180);
		updateStudent.addActionListener(this);
		updateStudent.setBackground(Color.BLUE);
		updateStudent.setForeground(Color.GREEN);
		add(updateStudent);
		
		showStudent=new JButton("SHOW Student");
		showStudent.setSize(150,30);
		showStudent.setLocation(100,230);
		showStudent.addActionListener(this);
		showStudent.setBackground(Color.BLUE);
		showStudent.setForeground(Color.GREEN);
		add(showStudent);
		
		
		l2=new JLabel("COACH");
		l2.setSize(100,30);
		l2.setLocation(300,30);
		add(l2);
		
		addCoach=new JButton("ADD Coach");
		addCoach.setSize(150,30);
		addCoach.setLocation(300,80);
		addCoach.addActionListener(this);
		addCoach.setBackground(Color.BLUE);
		addCoach.setForeground(Color.GREEN);
		add(addCoach);
		
		deleteCoach=new JButton("DELETE Coach");
		deleteCoach.setSize(150,30);
		deleteCoach.setLocation(300,130);
		deleteCoach.addActionListener(this);
		deleteCoach.setBackground(Color.BLUE);
		deleteCoach.setForeground(Color.GREEN);
		add(deleteCoach);
		
		updateCoach=new JButton("UPDATE Coach");
		updateCoach.setSize(150,30);
		updateCoach.setLocation(300,180);
		updateCoach.addActionListener(this);
		updateCoach.setBackground(Color.BLUE);
		updateCoach.setForeground(Color.GREEN);
		add(updateCoach);
		
		showCoach=new JButton("SHOW Coach");
		showCoach.setSize(150,30);
		showCoach.setLocation(300,230);
		showCoach.addActionListener(this);
		showCoach.setBackground(Color.BLUE);
		showCoach.setForeground(Color.GREEN);
		add(showCoach);
		
		l3=new JLabel("ACCESSORIES");
		l3.setSize(100,30);
		l3.setLocation(500,30);
		add(l3);
		
		addAccessories=new JButton("ADD Accessories");
		addAccessories.setSize(150,30);
		addAccessories.setLocation(500,80);
		addAccessories.addActionListener(this);
		addAccessories.setBackground(Color.BLUE);
		addAccessories.setForeground(Color.GREEN);
		add(addAccessories);
		
		deleteAccessories=new JButton("DELETE Accessories");
		deleteAccessories.setSize(150,30);
		deleteAccessories.setLocation(500,130);
		deleteAccessories.addActionListener(this);
		deleteAccessories.setBackground(Color.BLUE);
		deleteAccessories.setForeground(Color.GREEN);
		add(deleteAccessories);
		
		updateAccessories=new JButton("UPDATE Accessories");
		updateAccessories.setSize(150,30);
		updateAccessories.setLocation(500,180);
		updateAccessories.addActionListener(this);
		updateAccessories.setBackground(Color.BLUE);
		updateAccessories.setForeground(Color.GREEN);
		add(updateAccessories);
		
		showAccessories=new JButton("SHOW Accessories");
		showAccessories.setSize(150,30);
		showAccessories.setLocation(500,230);
		showAccessories.addActionListener(this);
		showAccessories.setBackground(Color.BLUE);
		showAccessories.setForeground(Color.GREEN);
		add(showAccessories);
		
		
		l4=new JLabel("BATCH");
		l4.setSize(100,30);
		l4.setLocation(100,300);
		add(l4);
		
		editBatch=new JButton("Edit Batch");
		editBatch.setSize(150,30);
		editBatch.setLocation(100,350);
		editBatch.addActionListener(this);
		editBatch.setBackground(Color.BLUE);
		editBatch.setForeground(Color.GREEN);
		add(editBatch);
		
		
		l6=new JLabel("SUPPLIER");
		l6.setSize(100,30);
		l6.setLocation(300,300);
		add(l6);
		
		supplier=new JButton("Edit Supplier");
		supplier.setSize(150,30);
		supplier.setLocation(300,350);
		supplier.addActionListener(this);
		supplier.setBackground(Color.BLUE);
		supplier.setForeground(Color.GREEN);
		add(supplier);
		
		
		l7=new JLabel("Orders");
		l7.setSize(100,30);
		l7.setLocation(500,300);
		add(l7);
		
		editOrder=new JButton("Edit Orders");
		editOrder.setSize(150,30);
		editOrder.setLocation(500,350);
		editOrder.addActionListener(this);
		editOrder.setBackground(Color.BLUE);
		editOrder.setForeground(Color.GREEN);
		add(editOrder);
		
		
		l5=new JLabel("PERSONAL");
		l5.setSize(100,30);
		l5.setLocation(100,400);
		add(l5);
		
		mydata=new JButton("My Data");
		mydata.setSize(150,30);
		mydata.setLocation(100,450);
		mydata.addActionListener(this);
		mydata.setBackground(Color.BLUE);
		mydata.setForeground(Color.GREEN);
		add(mydata);
		
		chpw=new JButton("Change Password");
		chpw.setSize(150,30);
		chpw.setLocation(300,450);
		chpw.addActionListener(this);
		chpw.setBackground(Color.BLUE);
		chpw.setForeground(Color.GREEN);
		add(chpw);
		
		l8=new JLabel("Generate Reports");
		l8.setSize(200,30);
		l8.setLocation(500,400);
		add(l8);
		
		reports=new JButton("Reports");
		reports.setSize(150,30);
		reports.setLocation(500,450);
		reports.addActionListener(this);
		reports.setBackground(Color.BLUE);
		reports.setForeground(Color.GREEN);
		add(reports);
		
		
		
		ta=new JTextArea();
		ta.setSize(550,200);
		ta.setLocation(100,500);
		ta.setEditable(false);
		sp= new JScrollPane(ta);
		sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		sp.setBounds(100,500,550,200);
		sp.getViewport().setBackground(Color.WHITE);
		sp.getViewport().add(ta);
		add(sp);
	
		
		setTitle("Admin Window ");
		setSize(900,800);
		setLocation(400,50);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		String s=(String)ae.getActionCommand();
		
		if(s.equals("ADD Student"))
		{
			JOptionPane.showMessageDialog(new AddStudent(),"Directed to ADD Student");
		}
		
		if(s.equals("DELETE Student"))
		{
			JOptionPane.showMessageDialog(new DeleteStudent(),"Directed to DELETE Student");
		}
		
		if(s.equals("UPDATE Student"))
		{
			JOptionPane.showMessageDialog(new UpdateStudent(),"Directed to Update Student");
		}
		
		if(s.equals("SHOW Student"))
		{
			JOptionPane.showMessageDialog(new ShowStudent(),"Showing Student Data");
		}
		
		
		
		if(s.equals("ADD Coach"))
		{
			JOptionPane.showMessageDialog(new AddCoach(),"Directed to ADD Coach");
		}
		
		if(s.equals("DELETE Coach"))
		{
			JOptionPane.showMessageDialog(new DeleteCoach(),"Directed to DELETE Coach");
		}
		
		if(s.equals("UPDATE Coach"))
		{
			JOptionPane.showMessageDialog(new UpdateCoach(),"Directed to Update Coach");
		}
		
		if(s.equals("SHOW Coach"))
		{
			JOptionPane.showMessageDialog(new ShowCoach(),"Showing Coaches Data");
		}
		
		
		
		if(s.equals("ADD Accessories"))
		{
			JOptionPane.showMessageDialog(new AddAccessories(),"Directed to ADD Accessories");
		}
		
		if(s.equals("DELETE Accessories"))
		{
			JOptionPane.showMessageDialog(new DeleteAccessories(),"Directed to DELETE Accessories");
		}
		
		if(s.equals("UPDATE Accessories"))
		{
			JOptionPane.showMessageDialog(new UpdateAccessories(),"Directed to Update Accessories");
		}
		
		if(s.equals("SHOW Accessories"))
		{
			JOptionPane.showMessageDialog(new ShowAccessories(),"Showing Accessories Data");
		}
		
		
		if(s.equals("Edit Batch"))
		{
			JOptionPane.showMessageDialog(new EditBatch(),"Directed to Edit Batch");
		}
		
		
		if(s.equals("Edit Supplier"))
		{
			JOptionPane.showMessageDialog(new EditSupplier(),"Directed to Edit Batch");
		}
		
		
		if(s.equals("Edit Orders"))
		{
			JOptionPane.showMessageDialog(new EditOrders(),"Directed to Edit Batch");
		}
		
		if(s.equals("Reports"))
		{
			try
			{
				Class.forName("org.postgresql.Driver");
				con = DriverManager.getConnection("jdbc:postgresql://localhost/collegedb", "root", "root@123");
				
				String fnames[] = {"student.csv","coach.csv","accessories.csv"};
				
				for(int i=0; i<fnames.length;i++)
				{
					File f = new File("D:\\Study\\Java\\Advance Java\\JDBC\\CollegeProject\\resources\\student.txt");

					if(!f.exists())
					{
						System.out.println("File not Exists");
						System.exit(0);
					}

					if(!f.isFile())
					{
						System.out.println("Is not a file");
						System.exit(0);
					}

					FileWriter fw = new FileWriter(f);
					
					if(i==0)
					{
						ps=con.prepareStatement("select * from student");
						rs = ps.executeQuery();
						
						while(rs.next())
						{
							fw.write(rs.getString("name")+","+rs.getString("phoneno")+","+rs.getString("address")+"\n");
						}
						
						JOptionPane.showMessageDialog(null,"Reports Generated");
						fw.close();
					}
					
					
				}
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		
		if(s.equals("My Data"))
		{
			try
			{
				Class.forName("org.postgresql.Driver");
				con = DriverManager.getConnection("jdbc:postgresql://localhost/collegedb", "root", "root@123");
				ta.setText("ID\tNAME\t\tPHONE.NO\tSalary\tADDRESS\n");

				
				ps=con.prepareStatement(" select * from admin where id=?");
				ps.setInt(1,id);
						
				rs=ps.executeQuery();
						
				if(rs.next())
				{
					
					ta.setText("ID\tNAME\t\tPHONE.NO\tSalary\tADDRESS\n");
					ta.append(rs.getInt("id")+"\t"+rs.getString("name")+"\t\t"+rs.getString("phoneno")+"\t"+rs.getDouble("salary")+"\t"+rs.getString("address")+"\n");
					while(rs.next())
					{
						ta.append(rs.getInt("id")+"\t"+rs.getString("name")+"\t\t"+rs.getString("phoneno")+"\t"+rs.getDouble("salary")+"\t"+rs.getString("address")+"\n");
					}
						
				}
				else
				{
					ta.setText("ID\t\tNAME\tPHONE.NO\tSalary\t\tADDRESS\n");
					ta.append("No data Found !");
				}
				
				con.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		
		if(s.equals("Change Password"))
		{
			JOptionPane.showMessageDialog(new ChangeAdminPassword(),"Directed to Change Password");
		}
		
		
		
	}
	
}
