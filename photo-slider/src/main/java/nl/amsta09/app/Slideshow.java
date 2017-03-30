/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.amsta09.app;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.*;

/**
 *
 * @author Marco Bergsma
 */
public final class Slideshow extends JFrame implements KeyListener {

    JLabel pic;
    Timer tm;
    int x = 0;

    URL url;
    AudioClip clip;


   List<URL> list;

   public Slideshow() throws MalformedURLException {
        super("Java SlideShow");
        this.url = new File("Resources/Sounds/duck.wav").toURI().toURL();
        clip = Applet.newAudioClip(url);
        pic = new JLabel();
        pic.setFocusable(true);


        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();

        pic.setBounds(0, 0, (int) width, (int) height);

        pic.setBounds(0, 0, 1980, 1080);

        pic.addKeyListener(this);
        list = new ArrayList<URL>();
        GetAllPictures(list);
        SetImageSize(1);

        tm = new Timer(666666, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent b) {
                SetImageSize(x);
                x += 1;
                if (x >= list.size()) {
                    x = 0;
                }
            }
        });
        add(pic);
        tm.start();
        setLayout(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        setUndecorated(true); //Zet op false om fullscreen uit te schakelen
//setSize(1980, 1080);
        getContentPane().setBackground(Color.decode("#bdb67b"));

        setUndecorated(false);
        setSize(1980, 1080);
        getContentPane().setBackground(Color.decode("#190707"));

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /*
     * Gives the images the correct size
     */
    public void SetImageSize(int i) {
        ImageIcon icon = new ImageIcon(list.get(i));
        Image img = icon.getImage();
        Image newImg = img.getScaledInstance(pic.getWidth(), pic.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon newImc = new ImageIcon(newImg);
        pic.setIcon(newImc);

    }

    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println("(Key typed)");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {

            SetImageSize(x);
            x += 1;
            if (x >= list.size()) {
                x = 0;
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            clip.play();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("(Key released)");
    }
    /*
     * krijg alle foto's uit de opgegeven map
     */
    public void GetAllPictures(List<URL> list) throws MalformedURLException {

        File folder = new File("Resources/Foto");
        System.out.println(Arrays.toString(folder.listFiles()));
        File[] listOfFiles = folder.listFiles();

        for (File file : folder.listFiles()) {
            if (file.isFile()) {
                list.add((file).toURI().toURL());
                System.out.println(file.getName());

            }
        }
    }
}
