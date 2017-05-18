package nl.amsta09.app;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.amsta09.data.SqlConnector;

import nl.amsta09.model.Photo;

/**
 * @author Hugo Thunnissen en Marco Bergsma
 */
public class SlideShowController implements KeyListener {

    private Timer timer;
    private SlideshowView view;
    private ArrayList<Photo> photoList;
    private int PhotoId = 0;

    public SlideShowController() {
        this.timer = new Timer();
        this.view = new SlideshowView();
    }

    /**
     * Initialiseer de view en geef aan welke foto als eerste wordt weergegeven
     *
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public void initialize() throws SQLException, ClassNotFoundException {
        view.initialize();
        photoList = new ArrayList<>();
        getPictures(photoList);
        view.setImage(photoList.get(PhotoId).getURL());
        view.getPicture().addKeyListener(this);
    }

    /**
     * Haal de filepaths op naar alle foto's in de resources map
     *
     * @return photos
     */
    private static void getPictures(ArrayList<Photo> photoList) throws SQLException, ClassNotFoundException {
        new SqlConnector().getAllPhoto(photoList);

    }

    public SlideshowView getView() {
        return view;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println("(Key typed)");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            view.setImage(photoList.get(PhotoId).getURL());
            PhotoId += 1;
            if (PhotoId >= photoList.size()) {
                PhotoId = 0;
            }

        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            //        clip.play();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("(Key released)");
    }
}
