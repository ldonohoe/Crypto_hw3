import java.math.BigInteger;

public class HW3 {

	static int p = 499;
	static int q = 547;
	static int a = -57;
	static int b = 52;
	static int n = p * q;

	static int dx;
	static int blocks;

	public static void main(String[] args) {

		int m = Integer.parseInt("10011100000100001100", 2);

		System.out.println("Plaintext is " + Integer.toBinaryString(m));
		int e = encrypt(m);
		System.out.println("Ciphertext is " + Integer.toBinaryString(e));

		int m2 = decrypt(e, dx, blocks);
		System.out.println("Plaintext is " + Integer.toBinaryString(m2));

		Boolean eq = m == m2;
		System.out.println("Does plain = cipher? " + eq + " Yes! It does!");

	}

	/*
	 * Helper function for doing log base 2, as Java does not Have a built in
	 * function for this.
	 * 
	 */
	public static int log(int x, int base) {
		return (int) (Math.log(x) / Math.log(base));
	}

	/*
	 * Had to add this helper function for decryption,
	 * as some of the exponentiation overflowed the integer limit
	 * So I had to get creative and use BigIntegers.
	 * Turns out there is a built in function for this exact
	 * purpose using these.
	 */
	public static int bigExpMod(int x, int exp, int mod) {
		BigInteger bigx = BigInteger.valueOf(x);
		BigInteger bige = BigInteger.valueOf(exp);
		BigInteger bigm = BigInteger.valueOf(mod);
		
		return bigx.modPow(bige, bigm).intValue();
	}

	/*
	 * Encryptionn of m, using predefined values p, q, a, b and x0
	 */
	public static int encrypt(int m) {

		int x0 = 159201;
		int k = (int) log(p * q, 2);
		int h = (int) log(k, 2);
		int numBlocks = (Integer.toBinaryString(m).length()) / h;
		blocks = numBlocks;
		int x = x0;
		int e = 0;
		int mask, num, mx, px, cx;
		for (int i = numBlocks - 1; i >= 0; i--) {
			mask = 1 << h;
			mask -= 1;
			x = (int) (Math.pow(x, 2) % n);

			num = m >> (h * i);
			mx = num & mask;
			px = x & mask;
			cx = px ^ mx;
			e <<= h;
			e |= cx;

		}
		x = (int) (Math.pow(x, 2) % n);
		dx = x;
		return e;
	}

	public static int decrypt(int e, int x, int bs) {
		int k = (int) log(p * q, 2);
		int h = (int) log(k, 2);

		int np = (p + 1) / 4;
		int nq = (q + 1) / 4;
		int modp = p - 1;
		int modq = q - 1;
		int d1 = (int) (Math.pow(np, bs + 1) % modp);
		int d2 = (int) (Math.pow(nq, bs + 1) % modq);
		int pre1 = bigExpMod(x, d1, p);
		int pre2 = bigExpMod(x, d2, q);
		
		int add1 = pre2 * a * p;
		int add2 = pre1 * b * q;
		int x0 = (int) (add1 + add2) % n;
		int nx = x0;
		int mask, num, mx, px, cx;
		int mes = 0;
		for (int i = bs - 1; i >= 0; i--) {
			mask = 1 << h;
			mask -= 1;
			nx = (int) (Math.pow(nx, 2) % n);
			num = e >> (h * i);
			cx = num & mask;
			px = nx & mask;
			mx = px ^ cx;
			mes <<= h;
			mes |= mx;
		}
		return mes;

	}

}
