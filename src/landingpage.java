import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class landingpage extends JFrame {
    private JButton INVOICEButton;
    private JButton STOCKButton;
    private JButton CUSTOMERDATABASEButton;
    private JLabel landingimage;
    private JPanel landingpanel;
    private JPanel HEADING;

    private void createUIComponents() {
        landingimage = new JLabel();
        ImageIcon icon1 = new ImageIcon(new ImageIcon("image/4.png").getImage().getScaledInstance(450, 350, Image.SCALE_SMOOTH));
        landingimage.setIcon(icon1);
    }

    public landingpage() {

        setContentPane(landingpanel);
        ImageIcon logo = new ImageIcon("image/logo.png");
        setTitle("INVOICE AND STOCK MAINTENANCE");
        setIconImage(logo.getImage());
        setSize(750, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        CUSTOMERDATABASEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new CustomerDatabase().setVisible(true);
            }
        });
        STOCKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new Stock().setVisible(true);
            }
        });
        INVOICEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new new_existing().setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        landingpage mylandingpage = new landingpage();

    }
}
