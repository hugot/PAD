package nl.amsta09.app;

import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import nl.amsta09.model.Photo;

public class SlideShowController {
	Theme theme;
	SlideShowView view;
	Stage stage;

	public SlideShowController(Stage stage){

		StackPane stackPane = new StackPane();
		view = new SlideShowView(stackPane);

		Photo photo = new Photo("Resources/Foto/Cijfer2.jpg","naam");
		view.setImage(photo);

		this.stage = stage;
		stage.setScene(view);
		stage.show();
	}
}
