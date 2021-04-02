/**
 * Generate a source of bits which look random to any attacker.
 * Use a linear feedback shift register (LFSR).
 */

public class LFSR {
	private String seed;
	private int tap;

	// constructor
	public LFSR(String newSeed, int newTap) {
		seed = newSeed;
		tap = newTap;
	}

	// Return a String with all of the values from the register.
	public String toString() {
		return seed;
	}

	// Calculate the xor of the leftmost bit and the tap bit.
	public int step() {
		int len = seed.length();
		int leftmostBit = seed.charAt(0) - '0';
		int tapBit = seed.charAt(len - tap - 1) -'0';
		int bit = leftmostBit ^ tapBit;

		seed = seed.substring(1) + bit;

		return bit;
	}

	// Return k pseudorandom bits encoded as an integer.
	public int generate(int k) {
		int integer = 0;

		for(int i = k-1; i >= 0; i--) {
			int bit = step();
			integer += bit * Math.pow(2, i);
		}

		return integer;
	}

	public static void main(String[] args) {

		LFSR lfsr1 = new LFSR("01101000010", 8);
		for (int i = 0; i < 10; i++) {
		    int bit = lfsr1.step();
		    System.out.println(lfsr1 + " " + bit);
		}

		LFSR lfsr2 = new LFSR("01101000010", 8);
		for (int i = 0; i < 10; i++) {
		    int r = lfsr2.generate(5);
		    System.out.println(lfsr2 + " " + r);
		}
	}
}
