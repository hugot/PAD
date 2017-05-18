package nl.amsta09.app;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import nl.amsta09.model.Photo;

public class SlideShowView extends Scene {
	Image image;
	ImageView imageView;
	StackPane stackPane;

	public SlideShowView(Parent root) {
		super(root);
		imageView = new ImageView();
		stackPane = (StackPane)root;
		stackPane.getChildren().add(imageView);
	}

	public void setImage(Photo photo){
		this.image = new Image(photo.getURL().toString());
		imageView.setImage(image);
	}
}
