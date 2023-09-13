import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.mysql.cj.xdevapi.AddResult;
import com.sun.source.tree.IfTree;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class invoice extends JFrame {
    private JLabel titltinvoice;
    private JTextField text_invoiceno;
    private JLabel ID;
    private JTextField text_id;
    private JTextField text_gst;
    private JTextField text_date;
    private JPanel INVOICE_PANEL;
    private JTable table1;
    private JPanel tablepanel;
    private JScrollPane tablepane;
    private JLabel ProductLabel;
    private JTextField text_quantity;
    private JButton ADDButton;
    private JButton REMOVEButton;
    private JButton UPDATEButton;
    private JTextField textField6;
    private JButton GENERATEINVOICEButton;
    private JPanel SecondPanel;
    private JLabel LogoImage2;
    private JTextField text_name;
    private JTextField text_product;
    private JComboBox comboBox1;
    private JLabel bill_type;
    private JButton HOMEButton;
    private JPanel title_panel;
    private JLabel LogoImage1;
    public int FinalTotal = 0;
    DefaultTableModel model;
    Connection con;
    PreparedStatement pst;
    Connection con1;
    PreparedStatement pst1;
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
    public void connect1() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/stock", "root", "Sahilk@0008");
            System.out.println("Success");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void ItemCost(){

        FinalTotal=0;
        for (int i=0;i<table1.getRowCount();i++){

            FinalTotal= FinalTotal + Integer.parseInt(table1.getValueAt(i,5).toString());
        }

        String FinalTotal1 = String.valueOf(FinalTotal);
        textField6.setText(FinalTotal1);
    }



    public invoice() {

        connect1();
        connect();
        invoiceid();
        setContentPane(INVOICE_PANEL);
        ImageIcon logo = new ImageIcon("image/logo.png");
        setTitle("INVOICE");
        setIconImage(logo.getImage());
        setSize(1200, 780);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        CreateTable();

       //CALLING CUSTOMER DETAILS FROM DATABASE

        text_name.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String customer_name;
                customer_name = text_name.getText();
                try{
                    pst = con.prepareStatement("select * from customer_data where CustomerName like '"+customer_name+"%'");
                    ResultSet rs = pst.executeQuery();
                    if (rs.next()){
                        text_id.setText(rs.getString(1));
                        text_name.setText(rs.getString(2));
                         text_gst.setText(rs.getString(6));

                    }
                    else
                        JOptionPane.showMessageDialog(null,"No Such Data Exists");

                }
                catch (Exception e1){
                    JOptionPane.showMessageDialog(null,e1);
                }



            }
        });
        SimpleDateFormat dFormat = new  SimpleDateFormat ("yyyy-MM-dd") ;
        Date date = new Date() ;
        text_date.setText(dFormat.format(date)) ;

       //ADD BUTTON
        ADDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

               String product_name;
               int quantity;
               product_name= text_product.getText();
               try {
                   pst1 = con1.prepareStatement("select * from particulars where Product_name like '"+product_name+"%'");
                   ResultSet rs = pst1.executeQuery();

                  if (rs.next()){
                      quantity = Integer.parseInt(text_quantity.getText());
                      int quantity1 =  Integer.parseInt(rs.getString(3));
                      if (quantity1>=quantity) {
                          String p_name = rs.getString(2);
                          int price = Integer.parseInt(rs.getString(6));
                          int id = Integer.parseInt(rs.getString(1));
                          int GST = Integer.parseInt(rs.getString(5));
                          quantity = Integer.parseInt(text_quantity.getText());
                          int total1 = price * quantity;

                          model = (DefaultTableModel) table1.getModel();
                          model.addRow(new Object[]{id, p_name, price, quantity, GST, total1});


                          ItemCost();
                          text_product.setText("");
                          text_quantity.setText("");
                      }
                      else { String p_name = rs.getString(2);
                          JOptionPane.showMessageDialog(null,"Product "+p_name+" are just "+quantity1+" left");}

                  }else{JOptionPane.showMessageDialog(null,"No Such Product Exists");}
               } catch (Exception e1){
                   JOptionPane.showMessageDialog(null,e1);
               }




            }
        });
        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                int i =table1.getSelectedRow();
                text_product.setText(model.getValueAt(i,1).toString());
                text_quantity.setText(model.getValueAt(i,3).toString());
            }
        });

      //UPDATE BUTTON

        UPDATEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i =table1.getSelectedRow();
                model.setValueAt(text_product.getText(),i,1);
                model.setValueAt(text_quantity.getText(),i,3);
                String product_name;
                int quantity;
                product_name= text_product.getText();

                try {
                    pst1 = con1.prepareStatement("select * from particulars where Product_name like '"+product_name+"%'");
                    ResultSet rs = pst1.executeQuery();


                    while (rs.next()) {

                        int price = Integer.parseInt(rs.getString(6));
                        int id = Integer.parseInt(rs.getString(1));
                        int GST = Integer.parseInt(rs.getString(5));
                        quantity = Integer.parseInt(text_quantity.getText());
                        int total1 = price * quantity;

                        model = (DefaultTableModel) table1.getModel();
                        model.setValueAt(total1,i,5);


                        ItemCost();
                        text_product.setText("");
                        text_quantity.setText("");

                    }


                  }
                catch (Exception e1){
                    JOptionPane.showMessageDialog(null,e1);
                }


            }
        });

        //REMOVE BUTTON

        REMOVEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model = (DefaultTableModel) table1.getModel();
                int Remove= table1.getSelectedRow();
                if (Remove>=0){

                    model.removeRow(Remove);
                }
                ItemCost();
                text_product.setText("");
                text_quantity.setText("");



            }
        });

        // GENERATE INVOICE BUTTON

        GENERATEINVOICEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = text_name.getText();
                String CustomerId = text_id.getText();
                String InvoiceNo = text_invoiceno.getText();
                 String bill_type = comboBox1.getModel().getSelectedItem().toString();
                String date = text_date.getText();
               String gst_no = text_gst.getText();
                String total_price = textField6.getText();
                String path ="D:\\INVOICE AND STOCK MAINTENANCE\\invoice";
                com.itextpdf.text.Document doc = new  com.itextpdf.text.Document();

               // PDF GENERATOR CODE

                   try{

                    PdfWriter.getInstance(doc,new FileOutputStream(path +""+InvoiceNo+""+".pdf"));
                    doc.open();
                    Paragraph paragraph1 = new Paragraph("                                     INVOICE AND STOCK MAINTENANCE APPLICATION                              \n" + "\nInvoice No. : "+InvoiceNo+"                                                                                     Date : "+text_date.getText()+"\n");
                    doc.add(paragraph1);

                    Paragraph paragraph2 = new Paragraph("Bill Type:    "+bill_type+"\n\nCustomer Details : "+"\nCustomer Name : "+name+"\nCustomer_ID : "+CustomerId+"\nGST_NO. : "+gst_no+"\n\n\n");
                    doc.add(paragraph2);

                    PdfPTable tbl = new PdfPTable(6);
                    tbl.addCell("Product_id");
                    tbl.addCell("Product_name");
                    tbl.addCell("Price");
                    tbl.addCell("Quantity");
                    tbl.addCell("GST%");
                    tbl.addCell("Total_Price");
                    for(int i= 0;i<table1.getRowCount();i++){
                        String ID = table1.getValueAt(i,0).toString();
                        String n = table1.getValueAt(i,1).toString();
                        String p = table1.getValueAt(i,2).toString();
                        String q = table1.getValueAt(i,3).toString();
                        String G = table1.getValueAt(i,4).toString();
                        String tp = table1.getValueAt(i,5).toString();
                        tbl.addCell(ID);
                        tbl.addCell(n);
                        tbl.addCell(p);
                        tbl.addCell(q);
                        tbl.addCell(G);
                        tbl.addCell(tp);

                    }
                    doc.add(tbl);

                    Paragraph para = new Paragraph("\n                                                                                                                   Total Price : "+textField6.getText()+"\n\n                                                          THANKS FOR VISITING!\n\n                                                                                                   AUTHORIZED SIGNATORY              ");
                    doc.add(para);
                    JOptionPane.showMessageDialog(null,"Invoice Generated Successfully");

                }
                catch(Exception e1){
                    JOptionPane.showMessageDialog(null,e1);
                }
                doc.close();
            try {
                pst = con.prepareStatement("insert into invoice(Invoice_No,Customer_name,Date,Bill_type,Total_price)values(?,?,?,?,?)");
                pst.setString(1, InvoiceNo);
                pst.setString(2, name);
                pst.setString(3, date);
                pst.setString(4, bill_type);
                pst.setString(5, total_price);
                pst.executeUpdate();
            } catch(Exception e1){
                JOptionPane.showMessageDialog(null,e1);
            }

                for (int i=0;i<table1.getRowCount();i++){

                    int pr_id= Integer.parseInt(table1.getValueAt(i,0).toString());
                    try{
                        pst1 = con1.prepareStatement("select * from particulars where Product_ID like '"+pr_id+"%'");
                        ResultSet rs = pst1.executeQuery();

                       while (rs.next()) {
                           int old_quantity = Integer.valueOf(rs.getString(3));
                           int table_quantity = Integer.parseInt(table1.getValueAt(i, 3).toString());

                           int new_quantity = old_quantity - table_quantity;
                           pst1 = con1.prepareStatement("update particulars set Quantity="+new_quantity+" where Product_ID="+pr_id );
                           pst1.executeUpdate();

                       }


                }catch(Exception e1) {
                        JOptionPane.showMessageDialog(null, e1);

                    }}

          //UPDATE CREDIT DATABASE


             if (bill_type.equals("CREDIT")){
                try {
                    pst = con.prepareStatement("select * from customer_data where CustomerID like '"+CustomerId+"%'");
                    ResultSet rs = pst.executeQuery();

                    while (rs.next()) {
                        int old_credit = Integer.valueOf(rs.getString(7));
                        int new_credit = Integer.parseInt(total_price);

                        int updated_credit = old_credit + new_credit;
                        pst = con.prepareStatement("update customer_data set Credit="+updated_credit+" where CustomerID="+CustomerId );
                        pst.executeUpdate();

                    }


                }catch(Exception e1) {
                    JOptionPane.showMessageDialog(null, e1);}}

                 setVisible(false);
                new invoice().setVisible(true);

              try{

                  Desktop desktop = Desktop.getDesktop();
                 File file = new File("D:\\INVOICE AND STOCK MAINTENANCE\\"+"invoice"+InvoiceNo+".pdf");
                  if(file.exists()) desktop.open(file);

                }catch(Exception e1) {
                  JOptionPane.showMessageDialog(null, e1);}

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


    // INVOICE NO. GENERATOR

    public void invoiceid()
    {
        try {

            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("SELECT MAX(Invoice_No) FROM invoice");
            rs.next();
            rs.getString("MAX(Invoice_No)");
            if (rs.getString("MAX(Invoice_No)") == null) {
                text_invoiceno.setText("E-0000001");
            } else {
                long id = Long.parseLong(rs.getString("MAX(Invoice_No)").substring(2, rs.getString("MAX(Invoice_No)").length()));
                id++;
                text_invoiceno.setText("E-" + String.format("%07d", id));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


  // PARTICULARS TABLE

    private void CreateTable() {
        model = new DefaultTableModel();
        Object[] column = {"Product_id","Product_name", "Price", "Quantity","GST%" ,"Total_price"};
        Object[] row = new Object[0];
        model.setColumnIdentifiers(column);
        table1.setModel(model);
    }

    public static void main(String[] args) {
        invoice myinvoice = new invoice();


    }


    private void createUIComponents() {


        LogoImage2 = new JLabel();
        ImageIcon icon2 = new ImageIcon(new ImageIcon("image/6.png").getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));
        LogoImage2.setIcon(icon2);

    }
}