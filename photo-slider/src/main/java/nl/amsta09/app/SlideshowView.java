package nl.amsta09.app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

/**
 *
 * @author Marco Bergsma
 */
public final class SlideshowView extends JFrame {

    private JLabel pic;
    

    public SlideshowView() throws MalformedURLException {
        super("Java SlideShow");

        pic = new JLabel();
        pic.setFocusable(true);
        //Zet de dimensies
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        pic.setBounds(0, 0, (int) width, (int) height);

//      pic.addKeyListener(this);
    
//        SlideshowController.GetAllPictures(list);
        SetImageSize(0);

        add(pic);
        setLayout(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true); //Zet op false om fullscreen uit te schakelen
        setSize((int) width, (int) height);
        getContentPane().setBackground(Color.BLACK);

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /*
     * Zet het scherm op het aangegeven foto
     */
    public void SetImageSize(int i) {

        ImageIcon icon = new ImageIcon(list.get(i));
        Image img2 = icon.getImage();
        Image newImg = img2.getScaledInstance(pic.getWidth(), pic.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon newImc = new ImageIcon(newImg);
        pic.setIcon(newImc);

    }
}
