import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.* ;
public class signup extends JFrame {
    private JTextField loginid;
    private JPasswordField password1;
    private JPasswordField passwordconfirm;
    private JPanel signup_panel;
    private JPanel sec_panel;
    private JButton SAVEButton;
    private JTextField namefield;
    private JButton button1;

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



    public signup() {
        connect();
        setContentPane(signup_panel);
        ImageIcon logo = new ImageIcon("image/logo.png");
        setTitle("SIGN-UP");
        setIconImage(logo.getImage());
        setSize(650, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        SAVEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name, login_id, password, confirmpassword;
                name = namefield.getText();
                login_id = loginid.getText();
                password = password1.getText();
                confirmpassword = passwordconfirm.getText();

                if (namefield.getText().isEmpty()||loginid.getText().isEmpty()||password1.getText().isEmpty()||passwordconfirm.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null,"All fields are necessary");
                }

else{

                if (password.equals(confirmpassword )){
                try {
                    pst = con.prepareStatement("insert into user_data(NAME,LOGIN_ID,PASSWORD)values(?,?,?)");
                    pst.setString(1, name);
                    pst.setString(2, login_id);
                    pst.setString(3, password);
                    pst.executeUpdate();

                        JOptionPane.showMessageDialog(null, "NEW USER ADDED");
                        setVisible(false);
                        new login2().setVisible(true);
                   } catch (SQLException e1) {

                    e1.printStackTrace();
                }
                }else {
                    JOptionPane.showMessageDialog(null, "Password does not match");
                    password1.setText("");
                    passwordconfirm.setText("");
                    password1.requestFocus();
                }}



            }

            ;
        });
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new login2().setVisible(true);
            }
        });
    }

     public static void main(String[] args) {
        signup mysignup = new signup();


    }
 }