package home;

import java.io.IOException;

import Fxml.NewMailController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		Parent root = loader.load(getClass().getResource("/Fxml/NewMail.fxml").openStream());
		NewMailController newMailController = loader.getController();
		newMailController.start(primaryStage, root);
	}

	public static void main(String[] args) throws IOException {
		launch(args);

	}
	/*Hello World my name is Alaa and my ID nymber is 206681793.
	How are you?*/

}