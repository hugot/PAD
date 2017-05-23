package nl.amsta09.app;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

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
}
