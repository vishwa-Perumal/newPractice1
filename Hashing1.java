package Hashing1;

import org.mindrot.jbcrypt.BCrypt;

class PasswordUtil{
	
	public static String hashpass(String pass) {
		return BCrypt.hashpw(pass, BCrypt.gensalt(10));
	}
	
	public static boolean checkPass(String pass, String hashedpass) {
	     return BCrypt.checkpw(pass, hashedpass);
	}
	
}

public class Hashing1 {

	public static void main(String[] args) {
		
		
		String password = "vishwa";
		String hashedkey = PasswordUtil.hashpass(password);
		System.out.println(hashedkey);
		
		if(PasswordUtil.checkPass(password,"$2a$10$lvIiHvGAPKzTwHD4fDK7vO.CoOKUiFqwweTbfYH15Tb53vWMbBN6.")) {
			System.out.println("login succussfullu");
		}
		else {
			System.out.println("not successfully login");
		}
		
		
		
		

	}

}
