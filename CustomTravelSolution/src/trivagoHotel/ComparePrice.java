package trivagoHotel;

public class ComparePrice extends Utility{

	public static void main(String[] args) {
setUp("https://www.trivago.in/");  //open website 
		
		try {
			searchHotel("22","April","2022","LONDON",5);         
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Please add date in correct formate ex:-   11/February/2022");
		}
		
		compairPrice(1, 3);
		
		tearDown();

	}

	}

