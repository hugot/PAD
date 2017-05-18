package nl.amsta09.app;

import java.sql.SQLException;

import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import nl.amsta09.data.SqlConnector;
import nl.amsta09.data.SqlConnector.ThemeNotFoundException;
import nl.amsta09.model.Photo;
import nl.amsta09.model.Theme;

public class SlideShowController {
	Theme theme;
	SlideShowView view;
	Stage stage;
	SqlConnector conn;

	/**
	 * Instantieer de slideshow en zorg dat er een willekeurig thema of willekeurige foto te zien is.
	 * @param stage
	 */
	public SlideShowController(Stage stage){
		conn = new SqlConnector();

		//Haal een random thema op om foto's van weer te geven
		try {
			theme = conn.getThemeById((int)(Math.random() * conn.getMaxThemeId()));
		} catch (ThemeNotFoundException | SQLException e) {
			// TODO Doe hier iets nuttigs
			e.printStackTrace();
		}

		StackPane stackPane = new StackPane();
		view = new SlideShowView(stackPane);

		// Kijk of het thema foto's bevat, anders wordt er een willekeurige foto gekozen
		if(theme.getPhotoList().size() > 0){
			view.setImage(theme.getPhotoList().get((int)(Math.random() * theme.getPhotoList().size())));
		}
		else {
			try {
				view.setImage(conn.getPhotoById(3));
			} catch (SQLException e) {
				// TODO: doe hier iets nuttigs
				e.printStackTrace();
			}
		}

		this.stage = stage;
		stage.setScene(view);
		stage.show();
	}

	/**
	 * Verander welke foto er weergegeven wordt.
	 * @param photo
	 */
	public void setImage(Photo photo){
		view.setImage(photo);
	}
}
