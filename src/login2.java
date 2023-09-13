import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Objects;

public class login2 extends JFrame {
    private JPanel MAINPANEL;
    private JPanel PANEL2;
    private JLabel TITLE;
    private JTextField textField_login;
    private JButton SIGNUPButton;
    private JButton SIGNINButton;
    private JPasswordField password_login;
    private JLabel logoimage;

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

    public login2(){
         connect();
        setContentPane(MAINPANEL);
        ImageIcon logo = new ImageIcon("image/logo.png");
        setTitle("WELCOME");
        setIconImage(logo.getImage());
        setSize(650,400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        textField_login.requestFocus();

        SIGNINButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pass = password_login.getText();

               try{
                String query = "SELECT LOGIN_ID,PASSWORD from user_data where LOGIN_ID='" + textField_login.getText() + "'";
                pst = con.prepareStatement(query);
                ResultSet rs = pst.executeQuery();
               while (rs.next()) {

                   if (pass.equals(rs.getString("PASSWORD"))) {
                       setVisible(false);
                       new landingpage().setVisible(true);
                   } else {
                       JOptionPane.showMessageDialog(null, "Invalid Credentials");
                   }

               }  }catch (Exception e1){
    JOptionPane.showMessageDialog(null,e1);
}
            }
        });
        SIGNUPButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new signup().setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        login2 mylogin = new login2();




    }



}



