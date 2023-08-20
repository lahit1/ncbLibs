package naifcanbasci.math;

public class ComplexUtils
{
	public static Complex sqrt(double i) {
		if(i < 0) {
			return new Complex(0d, Math.sqrt(-i));
		}
		return new Complex(Math.sqrt(i));
	}
}
