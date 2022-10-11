package context;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class EncryptorMd5 {
	
	public String encryptString(String input) throws NoSuchAlgorithmException {
		
		MessageDigest md = MessageDigest.getInstance("MD5");
		
		byte[] messageDigest  = md.digest(input.getBytes());
		
		BigInteger bigInt = new BigInteger(1, messageDigest);
		
		
		return bigInt.toString(16);		
	}
	
	public static void main(String[] args) throws NoSuchAlgorithmException {
		EncryptorMd5 encryptor = new EncryptorMd5();
		
		String password = "123";
		String hashedPassword = "202cb962ac59075b964b07152d234b70";
		
		System.out.print("Please input your password : ");
		Scanner sc =new Scanner(System.in);
		String inputPassword  = sc.nextLine();
		
		// so sanh password
		if (encryptor.encryptString(inputPassword).equals(hashedPassword)) {
			System.out.println("Correct! You are in");
		} else {
			System.out.println("Wrong!!");
		}
		
		System.out.println(encryptor.encryptString(password));
		
		sc.close();
	}

}
