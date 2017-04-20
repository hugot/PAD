package nl.amsta09.app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.*;

/**
 *
 * @author Marco Bergsma
 */
public final class SlideshowView extends JFrame {

    private JLabel pic;
    private double width;
    private double height;
    

    public SlideshowView() throws MalformedURLException {
        super("Java SlideShow");

        pic = new JLabel();
        pic.setFocusable(true);

        //Zet de dimensies
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        width = screenSize.getWidth();
        height = screenSize.getHeight();
        
        pic.setBounds(0, 0, (int) width, (int) height);

    }

    public void initialize(){

        setLayout(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true); //Zet op false om fullscreen uit te schakelen
        setSize((int) width, (int) height);
        getContentPane().setBackground(Color.BLACK);

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

	/**
	 * @return pic
	 */
	public JLabel getPicture(){
		return pic;
	}

    /*
     * Zet het scherm op het aangegeven foto
     */
    public void setImage(URL url) {
        ImageIcon icon = new ImageIcon(url);
        Image img2 = icon.getImage();
        Image newImg = img2.getScaledInstance(pic.getWidth(), pic.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon newImc = new ImageIcon(newImg);
        pic.setIcon(newImc);
        add(pic);
    }
}
