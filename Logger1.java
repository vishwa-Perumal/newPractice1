package Logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 

public class Logger1 {

	public static void main(String[] args) {
		
		Logger log = LoggerFactory.getLogger(Logger1.class);
		log.info("hello");
		
		

	}

}
