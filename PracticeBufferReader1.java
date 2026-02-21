package s1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class PracticeBufferReader1 {

	public static void main(String[] args) throws IOException {
//		
//		BufferedReader reader = 
//				  new BufferedReader(new FileReader("src/main/resources/input.txt"));	
	    String line;
//		String file = "src/main/java/s1/hello.txt";	
		String file = "C:/Users/BLUE SCOPE/TestPracticefiles/Testfile1.txt";	
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			while((line=br.readLine())!=null) {
				System.out.println(line);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
