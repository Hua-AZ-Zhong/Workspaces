package demo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Test
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
		Star star=new Star();
		star.printStar(number);
	}
}