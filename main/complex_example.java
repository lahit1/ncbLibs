
import naifcanbasci.math.Complex;
import naifcanbasci.math.ComplexUtils;

public class complex_example 
{
	public static void main(String[] args) {
		Complex c1 = new Complex(4),
			c2 = new Complex(5, -2),
			c3 = ComplexUtils.sqrt(-4);
			
		System.out.println(c1);
		System.out.println(c2);
		System.out.println(c3);
		/*
		 4,0
		 5,0 - 2,0i
		 0,0 + 2,0i
		*/
		
		Complex c4 = c1.add(c2);
		System.out.println(c4);
		//9,0 - 2,0i
		
		Complex c5 = c4.mul(c3);
		System.out.println(c5);
		//4,0 + 18,0i
	}
}
