package parking.management.bcrypt;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BcryptionPassword {
	
	static public String Conversion(String rowPasswordString) {
		BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
		return "{bcrypt}"+bCryptPasswordEncoder.encode(rowPasswordString);
	}
	
	
}
