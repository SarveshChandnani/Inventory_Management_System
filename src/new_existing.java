import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class new_existing extends JFrame {
    private JPanel new_panel;
    private JButton NEWButton;
    private JButton EXISTINGButton;

    public new_existing()
    {
        setContentPane(new_panel);
        ImageIcon logo = new ImageIcon("logo.png");
        setTitle("CUSTOMER");
        setIconImage(logo.getImage());
        setSize(390, 200);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);


        NEWButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new CustomerDatabase().setVisible(true);
            }
        });
        EXISTINGButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new invoice().setVisible(true);
            }
        });
    }


    public static void main(String[] args) {
        new_existing mynew_existing = new new_existing();
    }
}
