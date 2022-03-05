package rabinSignature;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Random;

public class myRabin {

	// generate keys
	public static int generateKey(int p, int q) {

		int nRabin = p * q;

		Random r = new Random();
		//between 1-nRabin
		int b = r.nextInt((nRabin - 1) + 1) + 1;

		System.out.println("the public key (n,b) is: (" + nRabin + "," + b + ")");
		System.out.println("the private key (p,q) is: (" + p + "," + q + ")");

		return b;
	}

	// hash function, get message as integer, and u generated random number.
	public static int H(int m, int u) {
		int c = m + u;
		return c;
	}

	// convert Byte Array To Integer
	public static int convertByteArrayToInteger(byte[] b) {
		byte[] bytes = b;

		for (int i = 0; i < bytes.length / 2; i++) {
			byte temp = bytes[i];
			bytes[i] = bytes[bytes.length - i - 1];
			bytes[bytes.length - i - 1] = temp;
		}
		int tmp = new BigInteger(1, bytes).intValue();
		System.out.println("Converted message to Integer = " + tmp);
		return tmp;
	}

	public static ArrayList<Integer> Sign(byte[] m, int p, int q, int b) {
		int U = 0;
		int x = 0;

		
		int i, check, H = 0;
		int nRabin = p * q;

		// convert byte to integer number.
		int mInteger = convertByteArrayToInteger(m.clone());

		while (x == 0) {
			// generate U.
			U = new Random().nextInt(990) + 10;

			// calculate H using hash function.
			H = H(mInteger, U);

			// calculating x
			for (i = 1; i < Math.min(p,q); i++) {
				check = i * (i + b);
				//if ((check % nRabin) == (H % nRabin)) {
				if ( ((check % p) == (H % p)) && ((check % q) == (H % q)) ) {
					x = i;
					break;
				}
			}
		}

		System.out.println("the H is : " + H );
		System.out.println("the x(x+b) mod n is : " + (x * (x + b)) % nRabin);
		System.out.println("the H mod n is : " + H % nRabin);

		ArrayList<Integer> S = new ArrayList<>();
		S.add(U);
		S.add(x);
		return S;
	}

	public static boolean Verify(byte[] m, int U, int x,int p,int q,int b) {
		
		int nRabin = p * q;
		
		// convert byte to integer number.
		int mInteger = convertByteArrayToInteger(m.clone());
		// calculating H
		int H = H(mInteger, U);
		
		// calculating x(x+b)
		int check = x * (x + b);
		
		
		System.out.println("the x(x+b) mod n is : " + (x * (x + b)) % nRabin);
		System.out.println("the H mod n is : " + H % nRabin);
				
				if((check % nRabin) == (H % nRabin))
				{
					System.out.println("Signature verification succeeded");
					return true;
				}
				else
				{
					System.out.println("Signature verification failed");
					return false;
				}
		
	}
	

	public static void main(String[] args) {

		int p = 11;
		int q = 7;
		int b = generateKey(p, q);
		String message = "wael";
		byte[] msg = message.getBytes(StandardCharsets.UTF_8);

		ArrayList<Integer> arrayList = Sign(msg, p, q, b);
		int U = arrayList.get(0);// get U
		int x = arrayList.get(1);// get x
		System.out.println("the signature is : (U =" + U + ", x= " + x + ").");
		
		System.out.println("\n\n");
		
		boolean V= Verify(msg,U,x,p,q,b);
		System.out.println(V);
	}

}
