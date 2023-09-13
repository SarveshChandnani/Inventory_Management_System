import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Sales_Report extends JFrame {
    private JPanel Report_Panel;
    private JLabel SalesLabel;
    private JPanel Main_Panel;
    private JTable table1;
    private JPanel TablePanel;
    private JButton BACKButton;

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
            pst = con.prepareStatement("select * from invoice");
            ResultSet rs = pst.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Sales_Report() {
        connect();
        table_load();
        setContentPane(Main_Panel);
        ImageIcon logo = new ImageIcon("image/logo.png");
        setTitle("SALES REPORT");
        setIconImage(logo.getImage());
        setSize(900, 800);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);


        BACKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new CustomerDatabase().setVisible(true);
            }
        });
    }
    public static void main (String args[]){
        Sales_Report myStockReport = new Sales_Report();
    }

}


