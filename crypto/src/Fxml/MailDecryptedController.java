package Fxml;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class MailDecryptedController implements Initializable {

	private Stage stage;
	
	@FXML
	private TextArea mailDecrypted;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

	public void getMail(String Mail) {
		mailDecrypted.setText(Mail);
	}
	
	public void start(Stage primaryStage, Parent root, byte[] dec) {
		getMail(new String(dec));
		Scene scene = new Scene(root);
		stage = primaryStage;
		stage.setTitle("Mail Decrypted Page");
		scene.getStylesheets().add(getClass().getResource("Background.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}

}
