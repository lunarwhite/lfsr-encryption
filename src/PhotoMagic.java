
/**
 * Encrypt pictures.
 */

import java.awt.Color;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PhotoMagic {
	private static LFSR lfsr;

	public PhotoMagic(LFSR newLfsr) {
		lfsr = newLfsr;
	}

	// Save the password details. "XfileName.png seed tap dateAndTime"
	public void savePasswordDetials(String fileName, String seed, String tap) {
		FileWriter passwordDetail = null;
		try {
			passwordDetail = new FileWriter("res/keychain.txt", true);
		} catch (IOException e) {
			e.printStackTrace();
		}

		PrintWriter output = new PrintWriter(passwordDetail);
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		String currentTime = df.format(new Date());
		String newFileName = "X" + fileName;

		output.println(newFileName + " " + seed + " " + tap + " " + currentTime);
		output.close();
	}

	// Transform (encrypt) the picture.
	public Picture transform(Picture picture) {
		int height = picture.height();
		int width = picture.width();

		for(int col = 0; col < width; col++) {
			for(int row = 0; row < height; row++) {
				Color pixel = picture.get(col, row);
				int[] colors = {pixel.getRed(), pixel.getGreen(), pixel.getBlue()};

				colors[0] = lfsr.generate(8) ^ colors[0];
				colors[1] = lfsr.generate(8) ^ colors[1];
				colors[2] = lfsr.generate(8) ^ colors[2];

				Color newColor = new Color(colors[0], colors[1], colors[2]);
				picture.set(col, row, newColor);
			}
		}

		return picture;
	}

	public static void main(String[] args) {
		String fileName = args[0];
		String seed = args[1];
		String tap = args[2];

		Picture myPicture = new Picture(fileName);
		LFSR lfsr = new LFSR(seed, Integer.parseInt(tap));
		PhotoMagic pm = new PhotoMagic(lfsr);

		Picture myNewPicture = pm.transform(myPicture);

		File encryptedPicture = new File("X" + fileName);
		myNewPicture.save(encryptedPicture);

		pm.savePasswordDetials(fileName, seed, tap);

		myNewPicture.show();
	}
}
