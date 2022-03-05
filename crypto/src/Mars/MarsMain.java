package Mars;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class MarsMain {
public static void main(String[] args) throws IOException {
		
		
		//String textClar = "my name is wael.";
		String textClar = "Hello my name is wael and my id is 2066";
		//String textClar = "12345678123456781234567812345678";
		

		String k = "1d58as6toa6s5752";

		
		System.out.println("Text clar: "+textClar);
		byte[] enc = MARS.encrypt(textClar.getBytes(StandardCharsets.UTF_8), k.getBytes(StandardCharsets.UTF_8));
		System.out.println("Text criptat MARS: "+new String(enc));
		


		byte[] dec = MARS.decrypt(enc, k.getBytes(StandardCharsets.UTF_8));
		System.out.println("Text decriptat MARS: "+new String(dec));
		
		
	}
}
