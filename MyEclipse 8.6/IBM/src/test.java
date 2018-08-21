import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class test
{
	public static void main(String args[]){
		System.out.println("hello world");
		int number = 0; 
		try{
			System.out.print("please input number of line:");
			BufferedReader reader = new BufferedReader(new InputStreamReader(
	                System.in));
	        number = Integer.valueOf(reader.readLine());
		} catch (IOException ex) {   
        }   
		star star=new star();
		star.printStar(number);
	}
}