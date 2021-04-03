/**
 * Try all possible seeds and all possible taps and finds the picture.
 * Make the program decide if the picture is garbage or is a real picture.
 */

import java.awt.Color;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BreakPhotoMagic {

	private static Picture magicPicture;
	private static Picture guessPicture;
	private static LFSR lfsr;
	private static PhotoMagic pm;

	// Calculate the absolute value  of average RGB.
	public static double calculateAbsAvgRBG(String fileName, String seed, String tap) {
		magicPicture = new Picture(fileName);
		lfsr = new LFSR(seed, Integer.parseInt(tap));
		pm = new PhotoMagic(lfsr);

		guessPicture = pm.transform(magicPicture);

		int height = guessPicture.height();
		int width = guessPicture.width();

		int sumRBG = 0;

		for(int col = 0; col < width; col++) {
			for(int row = 0; row < height; row++) {
				Color pixel = guessPicture.get(col, row);
				int oldRed = pixel.getRed();
				int oldGreen = pixel.getGreen();
				int oldBlue = pixel.getBlue();

				sumRBG += (oldRed + oldGreen + oldBlue);
			}
		}

		double absAvgRBG = Math.abs(1.0 * sumRBG / (height * width * 3) - 128);
		return absAvgRBG;
	}

	// Save the picture. "G-seed-tap-fileName.png"
	public static void savePicture(String fileName, String seed, String tap) {
		File encryptedPicture = new File("G-" + seed + "-" + tap + "-" + fileName);
		guessPicture.save(encryptedPicture);
	}

	// Save the Guessing details. "Guess-fileName.png seed: 0101 tap: 2  dateAndTime"
	public static void saveGuessingDetials(String fileName, String seed, String tap) {
		FileWriter GuessingDetial = null;
		try {
			GuessingDetial = new FileWriter("res/guesschain.txt", true);
		} catch (IOException e) {
			e.printStackTrace();
		}

		PrintWriter output = new PrintWriter(GuessingDetial);
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		String currentTime = df.format(new Date());
		String newFileName = "Guess-" + fileName;

		output.println(newFileName + " seed: " + seed + " tap: " + tap + " " + currentTime);
		output.close();
	}

	public static void main(String[] args) {
		final String fileName = "1111111111-6.png";
		final int largestBitNum = 16;
		final int smallestIntSeed = 512;

		int largestIntSeed = (int) Math.pow(2, largestBitNum);

		double largestDeviatedValue = 0;

		String mostLikelySeed = "";
		String mostLikelyTap = "";

		for(int intSeed = smallestIntSeed; intSeed < largestIntSeed; intSeed++) {
			String strSeed = Integer.toBinaryString(intSeed);
			int len = strSeed.length();

			for(int i = len - 1; i< largestBitNum; i++) {
				for(int intTap = 0; intTap < len; intTap++) {
					String strTap = String.valueOf(intTap);

					double thisAvg = calculateAbsAvgRBG(fileName, strSeed, strTap);
					if(largestDeviatedValue < thisAvg) {
						largestDeviatedValue = thisAvg;
						mostLikelySeed = strSeed;
						mostLikelyTap = strTap;

						savePicture(fileName, mostLikelySeed, mostLikelyTap);
						saveGuessingDetials(fileName, mostLikelySeed, mostLikelyTap);
					}
				}
				System.out.println("strSeed: " + String.format("%16s", strSeed));

				strSeed = "0" + strSeed;
			}
		}
	}
}
