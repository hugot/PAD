package nl.amsta09.app;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;

import nl.amsta09.model.Photo;

/**
 * @author Hugo Thunnissen en Marco Bergsma
 */
public class SlideShowController implements KeyListener{
	private Timer timer;
	private SlideshowView view;

	public SlideShowController(SlideshowView view){
		this.timer = new Timer();
		this.view = view;
	}

	/**
	 * Initialiseer de view en geef aan welke foto als eerste wordt
	 * weergegeven
	 */
	public void initialize(){
		view.initialize();
		view.getPicture().addKeyListener(this);
		view.setImage(getAllPictures().get(0).getFilePath());
	}

	/**
	 * Haal de filepaths op naar alle foto's in de resources map
	 * @return photos
	 */
	private static List<Photo> getAllPictures(){
		ArrayList<Photo> photos = new ArrayList<>();
		File folder = new File("Resources/Foto");
		System.out.println(Arrays.toString(folder.listFiles()));

		for (File file : folder.listFiles()) {
			if (file.isFile()) {
				try {
					photos.add(new Photo(file.toURI().toURL(), file.getName(), "theme"));
				} catch (MalformedURLException ex){
					ex.printStackTrace();
				}
				System.out.println(file.getName());
			}
		}
		return photos;
	}

	public SlideshowView getView(){
		return view;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		System.out.println("(Key typed)");
	}

	@Override
	public void keyPressed(KeyEvent e) {
		//        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
		//
		//            SetImageSize(x);
		//            x += 1;
		//            if (x >= list.size()) {
		//                x = 0;
		//            }
		//        }
		//        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
		//    //        clip.play();
		//        }
	}

	@Override
	public void keyReleased(KeyEvent e) {
		System.out.println("(Key released)");
	}
}
