package nl.amsta09.app;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javax.imageio.ImageIO;

import nl.amsta09.model.Photo;

public class SlideShowView extends Scene {
	private Image image;
	private ImageView imageView;
	private StackPane stackPane;
	private SlideShowController controller;
        private Photo photo;

	public SlideShowView(Parent root, SlideShowController controller) {
		super(root);
		imageView = new ImageView();
		stackPane = (StackPane)root;
		stackPane.getChildren().add(imageView);
		this.controller = controller;
	}

	/**
	 * Verander welke foto er weergegeven wordt.
	 * @param photo
	 */
	public void setImage(Photo photo){
                this.photo = photo;
		this.image = new Image(photo.getURL().toString());
                correctPhotoResolution(photo);
                imageView.setSmooth(true);
		imageView.setImage(image);
	}
        
        public Photo getPhoto(){
            return photo;
        }

	public void setKeyListener(){
		EventHandler<KeyEvent> eventHandler = new EventHandler<KeyEvent>() {
			
			@Override
			public void handle(KeyEvent k){
                                if(photo.getPlayedSoundEffect() == false && photo.getSoundEffect() != null){
                                    photo.getSoundEffect().playSound();
                                    photo.setPlayedSoundEffect(true);
                                }else{
                                    controller.showNextImage();
                                }
			}
		};
		this.setOnKeyPressed(eventHandler);
	}
        
        public void correctPhotoResolution(Photo image){
        BufferedImage readImage = null;
        double imageHeight = 0;
        double imageWidth = 0;
        try {
            readImage = ImageIO.read(new File(image.getRelativePath()));
            imageHeight = readImage.getHeight();
            imageWidth = readImage.getWidth();
        } catch (Exception e) {
            readImage = null;
        }
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double screenWidth = screenSize.getWidth();
        double screenHeight = screenSize.getHeight();
        double imageResolutionRatio = imageWidth / imageHeight;
        double screenResolutionRatio = screenWidth / screenHeight;
        if(imageResolutionRatio > screenResolutionRatio){
            imageWidth = screenWidth;
            imageHeight = (imageWidth / imageResolutionRatio);
        }else{
            imageHeight = screenHeight;
            imageWidth = (imageResolutionRatio * imageHeight);
        }
        imageView.setFitHeight(imageHeight);
        imageView.setFitWidth(imageWidth);
    }
}
