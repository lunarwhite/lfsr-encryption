/**
 * Encrypt pictures with 64-character alphabet.
 */

import java.io.File;

public class PhotoMagicDeluxe {
	final static String base64Characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

	// Convert the alphanumeric password into bits.
	private static String alphanumericTobits(String alphanumericSeed) {
		String bitsSeed = "";
		int alphanumericSeedlen = alphanumericSeed.length();

		for(int i = 0; i < alphanumericSeedlen; i++) {
			int charElement = alphanumericSeed.charAt(i);
			int charIndex = base64Characters.indexOf(charElement);

			String eachString = Integer.toBinaryString(charIndex);
			int eachStringLen = eachString.length();

			for(int j = 6; j > eachStringLen; j--) {
				eachString = "0" + eachString;
			}

			bitsSeed += eachString;
		}

		return bitsSeed;
	}

	public static void main(String[] args) {
		String fileName = args[0];
		String seed = args[1];
		String tap = args[2];

		Picture myPicture = new Picture(fileName);
		LFSR lfsr = new LFSR(alphanumericTobits(seed), Integer.parseInt(tap));
		PhotoMagic pm = new PhotoMagic(lfsr);

		Picture myNewPicture = pm.transform(myPicture);

		File encryptedPicture = new File("X" + fileName);
		myNewPicture.save(encryptedPicture);

		pm.savePasswordDetials(fileName, seed, tap);

		myNewPicture.show();
	}
}
