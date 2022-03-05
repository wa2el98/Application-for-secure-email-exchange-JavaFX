package ecElGamal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class EcElGamal {
	
	
	
	public static List<PairP<Point,Point>> encryptAndSendKey(String key)
	{
        BigInteger
        a = new BigInteger("1"),
        b = new BigInteger("1"),
        p = new BigInteger("2000003");
    ECC ecc = new ECC(a, b, p);
    ECCEG ecceg = new ECCEG(ecc, ecc.getBasePoint());

    System.out.print("Private key: ");
    System.out.println(ecceg.getPrivateKey());
    System.out.print("Public key: ");
    System.out.println(ecceg.getPublicKey());
    ecceg.savePrivateKey("key.pri");
    //System.out.println("Private key saved to key.pri");
    ecceg.savePublicKey("key.pub");
    //System.out.println("Public key saved to key.pub");

    
    byte[] read = key.getBytes(StandardCharsets.UTF_8);
    System.out.print("Plain text: ");
    System.out.println(key);
    List<PairP<Point,Point>> enc = ecceg.encryptBytes(read);
    System.out.print("Cipher text: ");
    for (PairP<Point,Point> pp: enc) {
        System.out.print(String.format("%02x%02x%02x%02x",
            pp.left.x.intValue(),
            pp.left.y.intValue(),
            pp.right.x.intValue(),
            pp.right.y.intValue()));
    }
    
    return enc;
    
	}
	
	
	public static String getAndDecryptKey(List<PairP<Point,Point>> enc)
	{
		   BigInteger
	        a = new BigInteger("1"),
	        b = new BigInteger("1"),
	        p = new BigInteger("2000003");
	    ECC ecc = new ECC(a, b, p);
	    ECCEG ecceg = new ECCEG(ecc, ecc.getBasePoint());
	    
	    //System.out.print("Cipher text: ");
		//List<Point> dec = ecceg.decrypt(enc);
        //for (Point pp: dec) System.out.print((char)ecc.pointToInt(pp).byteValue());
        System.out.println();
        System.out.println("enc is:"+ enc);
       

        //ECCEG ecceg2 = new ECCEG(ecc, ecc.getBasePoint());
        //System.out.println("Load private key and public key");
        ecceg.loadPrivateKey("key.pri");
        ecceg.loadPublicKey("key.pub");
        //List<PairP<Point,Point>> read_enc = enc;
        List<Point> read_dec = ecceg.decryptBytes(enc);
        
        StringBuilder str = new StringBuilder("");
        
        for (Point pp: read_dec) str.append((char)ecc.pointToInt(pp).byteValue());
        System.out.print("plain text: ");
        System.out.println(str);
        return str.toString();
	}
	
	
	
	
	
    public static void main(String[] args) {

        
    	//String key = ("1d58as6toa6s5752");
    	String key = ("d");
    	
    	
    	List<PairP<Point,Point>> enc=encryptAndSendKey(key);
    	
    	System.out.println("\n\n");
    	
    	
    	String res=getAndDecryptKey(enc);
        
    	System.out.println("my res is :" + res);
        
    }
}