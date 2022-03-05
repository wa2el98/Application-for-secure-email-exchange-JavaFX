package Fxml;

import ecElGamal.EcElGamal;
import ecElGamal.PairP;
import ecElGamal.Point;
import home.AlertHelper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import Mars.MARS;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import rabinSignature.myRabin;

public class NewMailController {

	@FXML
	private Button btnSend;

	@FXML
	private TextField txtMail;
	
	@FXML
    private TextArea txtAr;

	private Stage stage;

	private Parent root;

	@FXML
	void Send(ActionEvent event) {

		if (txtAr.getText().equals("")) {
			Window owner = btnSend.getScene().getWindow();
			AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!", "You must wirte an email");
			return;
		}

		else {
			String Mail = txtAr.getText();

			System.out.println("The Orginal text is: " + Mail + "\n\n\n");

			System.out.println("PART 1:\n******************************");

			System.out.println("encrypting with Mars:");

			/* encrypting info */
			String k = "1d58as6toa6s5752";
			byte[] encr = MARS.encrypt(Mail.getBytes(StandardCharsets.UTF_8), k.getBytes(StandardCharsets.UTF_8));
			/* encrypting info */

			System.out.println("NewMail Page :     " + new String(encr) + "\n");

			System.out.println("encrypting key with EC Elgamal:");
			/* encrypting key */
			List<PairP<Point, Point>> enc = EcElGamal.encryptAndSendKey(k);
			
			StringBuilder str= new StringBuilder();
			
			 System.out.println("");
			for (PairP<Point,Point> pp: enc) {
				str.append(String.format("%02x",pp.left.x.intValue()));
				str.append(String.format("%02x",pp.left.y.intValue()));
				str.append(String.format("%02x",pp.right.x.intValue()));
				str.append(String.format("%02x",pp.right.y.intValue()));
		    }
			
			String encryptedEcElgamal = str.toString();
			
			System.out.println("\n");
			/* encrypting key */

			System.out.println("Signing with Rabin:");
			/* Sign with Rabin */
			int p = 11;
			int q = 7;
			int b = myRabin.generateKey(p, q);
			byte[] msg = encryptedEcElgamal.getBytes(StandardCharsets.UTF_8);

			ArrayList<Integer> arrayList = myRabin.Sign(msg, p, q, b);
			int U = arrayList.get(0);// get u
			int x = arrayList.get(1);// get x
			System.out.println("the signature is : (U =" + U + ", x= " + x + ").");
			/* Sign with Rabin */

			try {
				FXMLLoader loader = new FXMLLoader();
				root = (Parent) loader.load(getClass().getResource("/Fxml/MailEncrypted.fxml").openStream());

				MailEncryptedController encrypted = loader.getController();
				encrypted.getMail(encr);

				encrypted.getkey(enc);

				encrypted.getU(U);
				encrypted.getx(x);
				encrypted.getb(b);
				
				encrypted.getByteEncryptedkey(msg);
				
				encrypted.start(stage, root);
				
				
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	public void start(Stage primaryStage, Parent root) throws Exception {
		Scene scene = new Scene(root, 950, 550);
		stage = primaryStage;
		stage.setTitle("New Mail");
		scene.getStylesheets().add(getClass().getResource("Background.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}

}
