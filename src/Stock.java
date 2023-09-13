import net.proteanit.sql.DbUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Stock extends JFrame {
    private JPanel StockMainPanel;
    private JTable table2;
    private JTextField txt_quantity;
    private JTextField txt_cost;
    private JTextField txt_gst;
    private JTextField txt_sell;
    private JTextField txt_name;
    private JPanel TablePanel;
    private JButton ADDButton;
    private JButton REMOVEButton;
    private JButton UPDATEButton;
    private JPanel ProductPanel;
    private JLabel LogoImage1;
    private JScrollPane TableScrollPane;
    private JPanel ImagePanel;
    private JButton btn_search;
    private JTextField txt_search;
    private JPanel title_panel;
    private JButton HOMEButton;


    Connection con;
    PreparedStatement pst;

    public void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stock", "root", "Sahilk@0008");
            System.out.println("Success");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    void table_load() {
        try {
            pst = con.prepareStatement("select * from particulars");
            ResultSet rs = pst.executeQuery();
            table2.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,e);
        }
    }

    void alert(){
        try {
            pst = con.prepareStatement("select * from particulars");
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                int quantity2= Integer.parseInt(rs.getString(3));
                String product_name = rs.getString(2);
                if (quantity2<5){
                    JOptionPane.showMessageDialog(null,"Product "+product_name+" is just "+quantity2+" left");
                }

            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,e);
        }

    }

    public Stock() {
        connect();
        table_load();
        setContentPane(StockMainPanel);
        ImageIcon logo = new ImageIcon("image/logo.png");
        setTitle("STOCK");
        setIconImage(logo.getImage());
        setSize(850, 850);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        alert();


        ADDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Product_Name, Quantity, Cost_Price, GST_Percentage, Selling_Price;
                Product_Name = txt_name.getText();
                Quantity = txt_quantity.getText();
                Cost_Price = txt_cost.getText();
                Selling_Price = txt_sell.getText();
                GST_Percentage = txt_gst.getText();

                try {
                    pst = con.prepareStatement("insert into particulars(Product_Name,Quantity,Cost_Price,GST_Per,Selling_Price)values(?,?,?,?,?)");
                    pst.setString(1, Product_Name);
                    pst.setString(2, Quantity);
                    pst.setString(3, Cost_Price);
                    pst.setString(5, Selling_Price);
                    pst.setString(4, GST_Percentage);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Product Added");

                    table_load();
                    txt_name.setText("");
                    txt_quantity.setText("");
                    txt_cost.setText("");
                    txt_sell.setText("");
                    txt_gst.setText("");

                    txt_name.requestFocus();

                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(null, e1);
                }
            }  });
        REMOVEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String productID;
                productID = txt_search.getText();

                try {
                    pst = con.prepareStatement("delete from particulars  where Product_ID = ?");

                    pst.setString(1, productID);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Deleted");
                    table_load();
                    txt_name.setText("");
                    txt_quantity.setText("");
                    txt_cost.setText("");
                    txt_sell.setText("");
                    txt_gst.setText("");
                    txt_name.requestFocus();

                }

                catch (SQLException e1)
                {

                    JOptionPane.showMessageDialog(null, e1);
                }


            }
        });
        btn_search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    String product_id = txt_search.getText();

                    pst = con.prepareStatement("select Product_Name,Quantity,Cost_Price,GST_Per,Selling_Price from particulars where Product_ID = ?");
                    pst.setString(1,product_id );
                    ResultSet rs = pst.executeQuery();

                    if(rs.next()==true)
                    {
                        String product_name = rs.getString(1);
                        String quantity = rs.getString(2);
                        String cost_price = rs.getString(3);
                        String gst = rs.getString(4);
                        String sell_price = rs.getString(5);


                        txt_name.setText(product_name);
                        txt_quantity.setText(quantity);
                        txt_cost.setText(cost_price);
                        txt_gst.setText(gst);
                        txt_sell.setText(sell_price);


                    }
                    else
                    {
                        txt_name.setText("");
                        txt_quantity.setText("");
                        txt_cost.setText("");
                        txt_gst.setText("");
                        txt_sell.setText("");
                        JOptionPane.showMessageDialog(null,"Product Id");

                    }
                }
                catch (SQLException ex)
                {
                    ex.printStackTrace();
                }


            }
        });
        UPDATEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String Product_Name, Quantity, Cost_Price, GST_Percentage, Selling_Price,Product_id;
                Product_Name = txt_name.getText();
                Quantity = txt_quantity.getText();
                Cost_Price = txt_cost.getText();
                Selling_Price = txt_sell.getText();
                GST_Percentage = txt_gst.getText();
                Product_id = txt_search.getText();
                try {
                    pst = con.prepareStatement("update particulars set Product_Name = ?, Quantity = ?,Cost_Price = ?,Selling_Price = ?,GST_Per= ? where Product_ID = ?");
                    pst.setString(1, Product_Name);
                    pst.setString(2, Quantity);
                    pst.setString(3, Cost_Price);
                    pst.setString(4, Selling_Price);
                    pst.setString(5, GST_Percentage);
                    pst.setString(6, Product_id);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Stock Data Updated");
                    table_load();
                    txt_name.setText("");
                    txt_quantity.setText("");
                    txt_cost.setText("");
                    txt_sell.setText("");
                    txt_gst.setText("");

                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(null, e1);
                }



            }
        });
        HOMEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                setVisible(false);
                new landingpage().setVisible(true);
            }
        });
    }



    public static void main(String[] args) {
        Stock myStockPage = new Stock();
    }

    private void createUIComponents() {
        LogoImage1 = new JLabel();
        ImageIcon icon2 = new ImageIcon(new ImageIcon("image/9.png").getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH));
        LogoImage1.setIcon(icon2);
    }
}
