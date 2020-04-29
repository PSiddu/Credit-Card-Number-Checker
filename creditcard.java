/* Verifies the authenticity of a user-inputed credit card number using Luhn's Algorithm and other tests.
 * Once the authenticity is verified, the program prints out the company of the credit card.
 * Currently supports American Express, MasterCard, and Visa cards.
 * 
 * @author Siddhardh Palaparthi
 */

package creditcard;
import java.util.Scanner;

public class creditcard{
	
	//Declaring global variable for user input to be accessed by all methods
	static String userint;
	
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		
		//conditional variable for the while loop
		int cond = 0;
		
		//initializing some variables
		boolean luhnCheck;
		String company = "";
		
		//while loop that runs until the user inputs a valid credit card number
		while (cond == 0) {
			
		//getting credit card number from user
		System.out.println("Number:");
		userint = scanner.nextLine();
		
		/*This is the simplest of 4 checks for credit card number authenticity, and is thus done first.
		 * This calls the isInteger method, which checks if the inputed String is an actual number, preventing
		 * try/catch exceptions and improving run-time.
		 */
		boolean intchecker = isInteger(userint);
		
		/*if the first test is passed, move onto putting the input through the Luhn Algorithm, which I implemented in the
		 * luhnCheck method.
		 */
		if (intchecker == true) {
			luhnCheck = luhnAlg(userint);
			
			/*If the second test is passed, move onto the 3rd and 4th tests, which look at the first few numbers of the input
			 *to determine the company of the credit card, and then ensure that the length of the input matches this. Calls the 
			 *companyCheck method to do this.
			 */
			if (luhnCheck == true) {
				company = companyCheck(userint);
				
				//if final test is passed, then cond is made equal to 1 and the while loop is ended, and the card is valid
				if (company.equals("AMERICAN EXPRESS") || company.equals("MASTERCARD") || company.equals("VISA")) {
					cond = 1;
				}
				//loop continuation if condition is not met
				else {
					cond = 0;
					System.out.println("INVALID");
					System.out.println("");
				}
			}
			//loop continuation if condition is not met
			else {
				cond = 0;
				System.out.println("INVALID");
				System.out.println("");
			}
		}
		//if any of the above tests fail, then the while loop continues and INVALID is printed, continuing until a valid input is entered.
		else {
			cond = 0;
			System.out.println("INVALID");
			System.out.println("");
		}
		}
		//after breaking out of the while loop, the name of the inputted card's company is printed
		System.out.println(company);
		
		//closing the scanner to prevent resource leak
		scanner.close();
	}
	
	//method to check if the input is valid as according the the Luhn Algorithm
	static boolean luhnAlg(String userint) {
		
		//converting the input to an integer array for the sake of simplicity
		int[] carddigits = new int[userint.length()];
		
		for(int i = 0; i < userint.length(); i++) {
			carddigits[i] = Character.getNumericValue(userint.charAt(i));
		}
		
		//doubling every other number starting from the second last one, going backwards
		for (int i = carddigits.length-2; i >=0; i=i-2)	{
			carddigits[i] = carddigits[i] * 2;
			
			/*since the sum of all digits is needed, not all integers, double digit integers are converted to the sum of their
			 *two integers by subtracting by 9
			 */
			if (carddigits[i] >= 10) {
				carddigits[i] = carddigits[i] - 9;
			}
		}
		
		//adding the doubled numbers with the rest of the numbers in the array to get the checksum
		int sum = 0;
		for (int i = 0; i < carddigits.length; i++) {
			sum = sum + carddigits[i];
		}
		
		//if checksum ends in 0, then the input is valid as according to Luhn's Algorithm, otherwise false is returned
		if (sum % 10 == 0) {
			
			return true;
		}
		else {
			return false;
		}
	}
	
	//method for checking whether the input is a integer (to prevent exceptions when invalid input is entered)
	public static boolean isInteger(String str) {
	    if (str == null) {
	        return false;
	    }
	    int length = str.length();
	    if (length == 0) {
	        return false;
	    }
	    int i = 0;
	    if (str.charAt(0) == '-') {
	        if (length == 1) {
	            return false;
	        }
	        i = 1;
	    }
	    for (; i < length; i++) {
	        char c = str.charAt(i);
	        if (c < '0' || c > '9') {
	            return false;
	        }
	    }
	    return true;
	}
	
	/*Method to check the first few digits of the input to distinguish between 3 credit card companies. (Amex, MasterCard, and
	 * Visa) After this check is completed, the length of the input is cross referenced with the company as the final check of
	 * authenticity. Boolean method couldn't be used because there are more than 2 potential outcomes from this check.
	 */
	public static String companyCheck (String userint) {
		String visa = "VISA";
		String amex = "AMERICAN EXPRESS";
		String master = "MASTERCARD";
		String invalid = "invalid";
		int length = userint.length();
		char first = userint.charAt(0);
		char second = userint.charAt(1);
		String firststring = "" + first;
		String firstandsecond = "" + first + second;
		
		//check for American Express
		if (firstandsecond.equals("34") || firstandsecond.equals("37")) {
			
			if (length == 15) {
				return amex;
			}
			else {
				return invalid;
			}
		}
		/*check for MasterCard
		 * (Some test cases have MasterCards starting with 22, so I have added it, although they usually start with the "50's")
		 */
		else if (firstandsecond.equals("51") || firstandsecond.equals("52") || firstandsecond.equals("53") 
				|| firstandsecond.equals("54") || firstandsecond.equals("55") || firstandsecond.equals("22")) {
			
			if (length == 16) {
				return master;
			}
			else {
				return invalid;
			}
		}
		//check for Visa
		else if (firststring.equals("4")) {
			
			if (length == 13 || length == 16) {
				return visa;
			}
			else {
				return invalid;
			}
		}
		else {
			return invalid;
		}
	}
}