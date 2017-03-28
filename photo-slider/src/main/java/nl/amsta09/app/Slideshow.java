/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.amsta09.app;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
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

    String[] list = {
        "/Resources/Foto/cijfer1.png",
        "/Resources/Foto/cijfer2.jpg",
        "/Resources/Foto/cijfer3.jpg",
        "/Resources/Foto/cijfer4.jpg"

    };

    public Slideshow() throws MalformedURLException {
        super("Java SlideShow");
        this.url = Slideshow.class.getResource("Resources/Sounds/duck.wav");
        clip = Applet.newAudioClip(url);
        pic = new JLabel();
        pic.setFocusable(true);
        pic.setBounds(40, 30, 400, 400);
        pic.addKeyListener(this);
        SetImageSize(3);

        tm = new Timer(666666, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent b) {
                SetImageSize(x);
                x += 1;
                if (x >= list.length) {
                    x = 0;
                }
            }
        });
        add(pic);
        tm.start();
        setLayout(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(false);
//setSize(1980, 1080);
        getContentPane().setBackground(Color.decode("#bdb67b"));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /*
     * Gives the images the correct size
     */
    public void SetImageSize(int i) {
        ImageIcon icon = new ImageIcon(list[i]);
        Image img = icon.getImage();
        Image newImg = img.getScaledInstance(pic.getWidth(), pic.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon newImc = new ImageIcon(newImg);
        pic.setIcon(newImc);

    }

    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println("Doet niks (Key typed)");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {

            SetImageSize(x);
            x += 1;
            if (x >= list.length) {
                x = 0;
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            clip.play();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("Doet niks (Key released)");
    }

    public void voegFotoToe(ArrayList list) {
        list.add(Slideshow.class.getResource("/Foto/cijfer1.png"));
    }
}
