package naifcanbasci.math;

public class Complex {
	private static int DEFAULT_DIGITS_AFTER_COMMA=1;

	public static void setDefaultDigitsAfterComma(int d) {
		DEFAULT_DIGITS_AFTER_COMMA = d;
	}

	public double REAL, IMAGINER;

	public Complex() {}

	public Complex(double REAL) {
		this.REAL = REAL;
	}

	public Complex(double REAL, double IMAGINER) {
		this.REAL = REAL;
		this.IMAGINER = IMAGINER;
	}

	public Complex(Complex c) {
		this.REAL = c.REAL;
		this.IMAGINER = c.IMAGINER;
	}

	@Override
	public String toString() {
		return toString(DEFAULT_DIGITS_AFTER_COMMA);
	}

	public String toString(int digitsaftercomma) {
		String formatstring;
		if (digitsaftercomma > 0) {
			formatstring = "%." + digitsaftercomma + "f";
		} else
			formatstring = "%f";
		String str = String.format(formatstring, REAL);
		if (IMAGINER != 0) {
			str += Math.signum(IMAGINER) > 0 ? " + " : " - ";
			str += String.format(formatstring, Math.abs(IMAGINER));
			str += "i";
		}
		return str;
	}

	public Complex add(Complex c) {
		Complex nc = new Complex(this);
		nc.REAL += c.REAL;
		nc.IMAGINER += c.IMAGINER;
		return nc;
	}

	public Complex add(Double REAL) {
		Complex nc = new Complex(this);
		nc.REAL += REAL;
		return nc;
	}

	public Complex add(Double REAL, Double IMAGINER) {
		Complex nc = new Complex(this);
		nc.REAL += REAL;
		nc.IMAGINER += IMAGINER;
		return nc;
	}

	public Complex mul(Complex c) {
		Complex nc = new Complex();
		nc.REAL = REAL * c.REAL - IMAGINER * c.IMAGINER;
		nc.IMAGINER = IMAGINER * c.REAL + REAL * c.IMAGINER;
		return nc;
	}

	public Complex mul(double REAL, double IMAGINER) {
		Complex nc = new Complex();
		nc.REAL = this.REAL * REAL - this.IMAGINER * IMAGINER;
		nc.IMAGINER = this.IMAGINER * REAL + this.REAL * IMAGINER;
		return nc;
	}

	public Complex mul(double REAL) {
		Complex nc = new Complex(this);
		nc.REAL *= REAL;
		nc.IMAGINER *= REAL;
		return nc;
	}
}
