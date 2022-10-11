package context;

public class RandomPassword {
	// random int 100-999
	public String getRanDomPassword() {
		int n = 0;			
		while (n<100) {
			n = (int) (Math.random()*1000);
		}
		
		return Integer.toString(n);		
	}
}
