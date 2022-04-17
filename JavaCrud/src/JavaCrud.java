import java.awt.EventQueue;
import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class JavaCrud {

	private JFrame frame;
	private JTextField txtedition;
	private JTextField txtbname;
	private JTextField txtprice;
	private JTable table;
	private JTextField txtbid;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JavaCrud window = new JavaCrud();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public JavaCrud() {
		initialize();
		Connect();
		table_load();
	}
	
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	 
	public void Connect()
	    {
	        try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            con = DriverManager.getConnection("jdbc:mysql://localhost/javacrud" + "", "root","");
	        }
	        catch (ClassNotFoundException ex)
	        {
	          ex.printStackTrace();
	        }
	        catch (SQLException ex)
	        {
	            ex.printStackTrace();
	        }
	 
	    }

	  public void table_load()
		    {
		    	try 
		    	{
			    pst = con.prepareStatement("select * from book");
			    rs = pst.executeQuery();
			    table.setModel(DbUtils.resultSetToTableModel(rs));
			} 
		    	catch (SQLException e) 
		    	 {
		    		e.printStackTrace();
			  } 
		    }


	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 671, 429);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblBookStore = new JLabel("Book store");
		lblBookStore.setFont(new Font("Dialog", Font.BOLD, 14));
		lblBookStore.setBounds(278, 0, 114, 25);
		frame.getContentPane().add(lblBookStore);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Registration", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(30, 37, 325, 285);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblBookId = new JLabel("edition");
		lblBookId.setBounds(30, 26, 84, 30);
		panel.add(lblBookId);
		
		JLabel lblBookId_1 = new JLabel("Book name");
		lblBookId_1.setBounds(30, 79, 84, 30);
		panel.add(lblBookId_1);
		
		JLabel lblBookId_1_1 = new JLabel("Price");
		lblBookId_1_1.setBounds(30, 134, 84, 30);
		panel.add(lblBookId_1_1);
		
		txtedition = new JTextField();
		txtedition.setBounds(182, 32, 114, 19);
		panel.add(txtedition);
		txtedition.setColumns(10);
		
		txtbname = new JTextField();
		txtbname.setBounds(182, 85, 114, 19);
		panel.add(txtbname);
		txtbname.setColumns(10);
		
		txtprice = new JTextField();
		txtprice.setColumns(10);
		txtprice.setBounds(182, 140, 114, 19);
		panel.add(txtprice);
		
		JButton btnSave = new JButton("Save");
btnSave.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent e) 
{			
	String bname,edition,price;
	bname = txtbname.getText();
	edition = txtedition.getText();
	price = txtprice.getText();
				
	 try {
		pst = con.prepareStatement("insert into book(name,edition,price)values(?,?,?)");
		pst.setString(1, bname);
		pst.setString(2, edition);
		pst.setString(3, price);
		pst.executeUpdate();
		JOptionPane.showMessageDialog(null, "Record Addedddd!!!!!");
		table_load();
			           
		txtbname.setText("");
		txtedition.setText("");
		txtprice.setText("");
		txtbname.requestFocus();
	   }

	catch (SQLException e1) 
        {
						
	e1.printStackTrace();
        }
	}
});
		btnSave.setBounds(12, 199, 117, 25);
		panel.add(btnSave);
		
		JButton btn_1 = new JButton("exit");
		btn_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btn_1.setBounds(179, 199, 117, 25);
		panel.add(btn_1);
		
		JButton btn_1_1 = new JButton("clear");
		btn_1_1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				 try {
			          
			            String id = txtbid.getText();

			                pst = con.prepareStatement("select name,edition,price from book where id = ?");
			                pst.setString(1, id);
			                ResultSet rs = pst.executeQuery();

			            if(rs.next()==true)
			            {
			              
			                String name = rs.getString(1);
			                String edition = rs.getString(2);
			                String price = rs.getString(3);
			                
			                txtbname.setText(name);
			                txtedition.setText(edition);
			                txtprice.setText(price);
			                
			                
			            }   
			            else
			            {
			            	txtbname.setText("");
			            	txtedition.setText("");
			                txtprice.setText("");
			                 
			            }
			            


			        } 
				
				 catch (SQLException ex) {
			           
			        }
				
				
				
				
			}
		});
		btn_1_1.setBounds(109, 236, 117, 25);
		panel.add(btn_1_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(383, 37, 258, 289);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "search", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(40, 326, 352, 54);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblBookId_1_1_1 = new JLabel("book id");
		lblBookId_1_1_1.setBounds(26, 24, 84, 17);
		panel_1.add(lblBookId_1_1_1);
		
		txtbid = new JTextField();
		txtbid.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				 try {
			          
			            String id = txtbid.getText();

			                pst = con.prepareStatement("select name,edition,price from book where id = ?");
			                pst.setString(1, id);
			                ResultSet rs = pst.executeQuery();

			            if(rs.next()==true)
			            {
			              
			                String name = rs.getString(1);
			                String edition = rs.getString(2);
			                String price = rs.getString(3);
			                
			                txtbname.setText(name);
			                txtedition.setText(edition);
			                txtprice.setText(price);
			                
			                
			            }   
			            else
			            {
			            	txtbname.setText("");
			            	txtedition.setText("");
			                txtprice.setText("");
			                 
			            }
			            


			        } 
				
				 catch (SQLException ex) {
			           
			        }
				
				
				
				
			}
		});
		txtbid.setColumns(10);
		txtbid.setBounds(194, 23, 114, 19);
		panel_1.add(txtbid);
		
		JButton btnDelete = new JButton("delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
		           String bid;
					bid  = txtbid.getText();
					
					 try {
							pst = con.prepareStatement("delete from book where id =?");
					
				            pst.setString(1, bid);
				            pst.executeUpdate();
				            JOptionPane.showMessageDialog(null, "Record Delete!!!!!");
				            table_load();
				           
				            txtbname.setText("");
				            txtedition.setText("");
				            txtprice.setText("");
				            txtbname.requestFocus();
						}

			            catch (SQLException e1) {
							
							e1.printStackTrace();
						}
			}
		});
		btnDelete.setBounds(404, 326, 117, 25);
		frame.getContentPane().add(btnDelete);
		
		JButton btnSave_1_1 = new JButton("update");
		btnSave_1_1.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent e) {
				
				
				String bname,edition,price,bid;
				
				
				bname = txtbname.getText();
				edition = txtedition.getText();
				price = txtprice.getText();
				bid  = txtbid.getText();
				
				 try {
						pst = con.prepareStatement("update book set name= ?,edition=?,price=? where id =?");
						pst.setString(1, bname);
			            pst.setString(2, edition);
			            pst.setString(3, price);
			            pst.setString(4, bid);
			            pst.executeUpdate();
			            JOptionPane.showMessageDialog(null, "Record Update!!!!!");
			            table_load();
			           
			            txtbname.setText("");
			            txtedition.setText("");
			            txtprice.setText("");
			            txtbname.requestFocus();
					}

		            catch (SQLException e1) {
						
						e1.printStackTrace();
					}
	
			}
		});
		btnSave_1_1.setBounds(525, 355, 117, 25);
		frame.getContentPane().add(btnSave_1_1);
	}
}
