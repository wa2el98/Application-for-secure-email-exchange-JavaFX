package home;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import Mars.MARS;
import ecElGamal.EcElGamal;
import ecElGamal.PairP;
import ecElGamal.Point;
import rabinSignature.myRabin;

public class MainWithoutGUI {

	public static void main(String[] args) throws IOException {

		String Mail = "hi my name is wael";

		System.out.println("The Orginal text is: " + Mail + "\n\n\n");

		System.out.println("PART 1:\n******************************");
		System.out.println("encrypting with Mars:");

		/* encrypting info */
		String k = "1d58as6toa6s5752";
		byte[] encr = MARS.encrypt(Mail.getBytes(StandardCharsets.UTF_8), k.getBytes(StandardCharsets.UTF_8));
		/* encrypting info */
		System.out.println("encrypted mail :     " + new String(encr) + "\n");

		System.out.println("encrypting key with EC Elgamal:");
		/* encrypting key */
		List<PairP<Point, Point>> enc = EcElGamal.encryptAndSendKey(k);

		StringBuilder str = new StringBuilder();

		System.out.println("");
		for (PairP<Point, Point> pp : enc) {
			str.append(String.format("%02x", pp.left.x.intValue()));
			str.append(String.format("%02x", pp.left.y.intValue()));
			str.append(String.format("%02x", pp.right.x.intValue()));
			str.append(String.format("%02x", pp.right.y.intValue()));
		}

		String encryptedEcElgamal = str.toString();
		System.out.println("String = " + encryptedEcElgamal);

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

		
		
		
		
		
		
		
		
		System.out.println("\n\n\nPART 2:\n******************************");

		System.out.println("Verification with Rabin:");
		/* verify with Rabin */
		boolean V = myRabin.Verify(msg, U, x, p, q, b);
		System.out.println(V);
		/* verify with Rabin */

		if (V) {
			System.out.println("\ndecrypting key with EC Elgamal:");
			/* decrypting key */
			String key = EcElGamal.getAndDecryptKey(enc);
			/* decrypting key */

			System.out.println("\ndecrypting with Mars:");

			/* decrypting res */
			// String k = "1d58as6toa6s5752";
			byte[] dec = MARS.decrypt(encr, key.getBytes(StandardCharsets.UTF_8));
			/* decrypting res */

			System.out.println("MailEncrypted Page : " + new String(dec) + "\n");

		} else
			System.out.println("Signature verification failed.");

	}

}
