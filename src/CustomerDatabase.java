import net.proteanit.sql.DbUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.* ;

public class CustomerDatabase extends JFrame {
    private JPanel CustomerPanel;
    private JTable table3;
    private JPanel SecondaryPanel;
    private JPanel TablePanel;
    private JPanel TitlePanel;
    private JTextField txtName;
    private JTextField txtID;
    private JTextField txtPhone;
    private JTextField txtEmail;
    private JTextField txtAddress;
    private JTextField txtGst;
    private JTextField txtCredit;
    private JButton ADDButton;
    private JButton REMOVEButton;
    private JButton UPDATEButton;
    private JLabel Title;
    private JScrollPane TableScrollPane;
    private JLabel LogoImage1;
    private JPanel LogoPanel;
    private JButton SEARCHButton;
    private JButton HOMEButton;
    private JButton INVOICEButton;
    private JButton SALESButton;


    Connection con;
    PreparedStatement pst;

    public void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/login", "root", "Sahilk@0008");
            System.out.println("Success");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    void table_load() {
        try {
            pst = con.prepareStatement("select * from customer_data");
            ResultSet rs = pst.executeQuery();
            table3.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public CustomerDatabase() {
        connect();
        table_load();
        setContentPane(CustomerPanel);
        ImageIcon logo = new ImageIcon("image/logo.png");
        setTitle("CUSTOMER DATABASE");
        setIconImage(logo.getImage());
        setSize(900, 820);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);

        ADDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String CustomerName,  PhoneNo, EmailId, Address, GSTNo, Credit;

                CustomerName = txtName.getText();
                PhoneNo = txtPhone.getText();
                EmailId = txtEmail.getText();
                Address = txtAddress.getText();
                GSTNo = txtGst.getText();
                Credit = txtCredit.getText();


                try {
                    pst = con.prepareStatement("insert into customer_data(CustomerName,PhoneNo,EmailId,Address,GST_No,Credit)values(?,?,?,?,?,?)");
                    pst.setString(1, CustomerName);
                    pst.setString(2, PhoneNo);
                    pst.setString(3, EmailId);
                    pst.setString(4, Address);
                    pst.setString(5, GSTNo);
                    pst.setString(6, Credit);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Customer Added");
                    table_load();
                    txtName.setText("");
                    txtPhone.setText("");
                    txtEmail.setText("");
                    txtAddress.setText("");
                    txtGst.setText("");
                    txtCredit.setText("");

                    txtName.requestFocus();
                } catch (SQLException e1) {

                    e1.printStackTrace();
                }

            }
        });

        SEARCHButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    String customerid = txtID.getText();
                    String customer_name1 =txtID.getText();
                    pst = con.prepareStatement("select CustomerName,PhoneNo,EmailId,Address,GST_No,Credit from customer_data where CustomerID = ?");
                    pst.setString(1, customerid );
                    ResultSet rs = pst.executeQuery();

                    if(rs.next()==true)
                    {
                        String CustomerName = rs.getString(1);
                        String PhoneNo = rs.getString(2);
                        String EmailId = rs.getString(3);
                        String Address = rs.getString(4);
                        String GST_No = rs.getString(5);
                        String Credit= rs.getString(6);

                        txtName.setText(CustomerName);
                        txtPhone.setText(PhoneNo);
                        txtEmail.setText(EmailId);
                        txtAddress.setText(Address);
                        txtGst.setText(GST_No);
                        txtCredit.setText(Credit);

                    }
                    else
                    {
                        txtName.setText("");
                        txtPhone.setText("");
                        txtEmail.setText("");
                        txtAddress.setText("");
                        txtGst.setText("");
                        txtCredit.setText("");
                        JOptionPane.showMessageDialog(null,"Invalid Customer Id");

                    }
                }
                catch (SQLException ex)
                {
                    ex.printStackTrace();
                }
            }
            }
        );
        UPDATEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String CustomerID,CustomerName,  PhoneNo, EmailId, Address, GSTNo, Credit;

                CustomerName = txtName.getText();
                PhoneNo = txtPhone.getText();
                EmailId = txtEmail.getText();
                Address = txtAddress.getText();
                GSTNo = txtGst.getText();
                Credit = txtCredit.getText();
               CustomerID = txtID.getText();

                try {
                    pst = con.prepareStatement("update customer_data set CustomerName = ?,PhoneNo = ?,EmailId = ?,Address = ?,GST_No = ?,Credit  = ? where CustomerID = ?");
                    pst.setString(1, CustomerName);
                    pst.setString(2, PhoneNo);
                    pst.setString(3, EmailId);
                    pst.setString(4,  Address);
                    pst.setString(5,GSTNo);
                    pst.setString(6, Credit);
                    pst.setString(7, CustomerID);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Updated");
                    table_load();
                    txtName.setText("");
                    txtPhone.setText("");
                    txtEmail.setText("");
                    txtAddress.setText("");
                    txtGst.setText("");
                    txtCredit.setText("");
                    txtName.requestFocus();
                }

                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
            }
            }
        );
        REMOVEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String CustomerID;
                CustomerID = txtID.getText();

                try {
                    pst = con.prepareStatement("delete from customer_data  where CustomerID = ?");

                    pst.setString(1, CustomerID);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Deleted");
                    table_load();
                    txtName.setText("");
                    txtPhone.setText("");
                    txtEmail.setText("");
                    txtAddress.setText("");
                    txtGst.setText("");
                    txtCredit.setText("");
                    txtName.requestFocus();
                }

                catch (SQLException e1)
                {

                    e1.printStackTrace();
                }
            }
            }
        );
        SALESButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                setVisible(false);
                new Sales_Report().setVisible(true);


            }
        });
        HOMEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new landingpage().setVisible(true);
            }
        });
        INVOICEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new invoice().setVisible(true);
            }
        });
    }
        public static void main (String args[]){
            CustomerDatabase myCustomerPage = new CustomerDatabase();
        }
        private void createUIComponents () {
            LogoImage1 = new JLabel();
            ImageIcon icon2 = new ImageIcon(new ImageIcon("image/10.png").getImage().getScaledInstance(350, 250, Image.SCALE_SMOOTH));
            LogoImage1.setIcon(icon2);
        }

}
