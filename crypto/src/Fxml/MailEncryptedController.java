package Fxml;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.ResourceBundle;

import Mars.MARS;
import ecElGamal.EcElGamal;
import ecElGamal.PairP;
import ecElGamal.Point;
import home.AlertHelper;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.Window;
import rabinSignature.myRabin;

public class MailEncryptedController implements Initializable {

	@FXML
	private Button btnDecrypt;

	@FXML
	private TextArea mailEncrypted;

	private Stage stage;

	private Parent root;

	private byte[] Mail;

	private List<PairP<Point, Point>> enc;

	private int U;
	private int x;
	private int b;
	
	private byte[] encryptedEcElgamal;

	@FXML
	void Decrypt(ActionEvent event) {

		System.out.println("\n\n\nPART 2:\n******************************");

		System.out.println("Verification with Rabin:");
		/* verify with Rabin */
		int p = 11;
		int q = 7;
		

		
		byte[] msg = encryptedEcElgamal;
		

		boolean V = myRabin.Verify(msg, U, x, p, q, b);
		/* verify with Rabin */

		if (V) {

			System.out.println("\ndecrypting key with EC Elgamal:");
			/* decrypting key */
			String key = EcElGamal.getAndDecryptKey(enc);
			/* decrypting key */

			System.out.println("\ndecrypting with Mars:");

			/* decrypting res */
			// String k = "1d58as6toa6s5752";
			byte[] dec = MARS.decrypt(Mail, key.getBytes(StandardCharsets.UTF_8));
			/* decrypting res */

			System.out.println("MailEncrypted Page : " + new String(dec) + "\n");

			try {
				FXMLLoader loader = new FXMLLoader();
				root =  loader.load(getClass().getResource("/Fxml/MailDecrypted.fxml").openStream());

				MailDecryptedController ask = loader.getController();
				//ask.getMail(new String(dec));
				ask.start(stage, root, dec);
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else {
			Window owner = btnDecrypt.getScene().getWindow();
			AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!", "Signature verification failed");
			return;
		}

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

	public void getMail(byte[] Mail) {
		this.Mail = Mail;
		mailEncrypted.setText(new String(Mail));
	}

	public void getkey(List<PairP<Point, Point>> enc) {
		this.enc = enc;
	}

	public void getU(int U) {
		this.U = U;
	}

	public void getx(int x) {
		this.x = x;
	}

	public void getb(int b) {
		this.b = b;
	}
	
	public void getByteEncryptedkey(byte[] encryptedEcElgamal) {
		this.encryptedEcElgamal = encryptedEcElgamal;
		
	}

	public void start(Stage primaryStage, Parent root) {
		Scene scene = new Scene(root);
		stage = primaryStage;
		stage.setTitle("Mail Encrypted Page");
		scene.getStylesheets().add(getClass().getResource("Background.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}

}
