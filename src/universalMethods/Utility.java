package universalMethods;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import videoPlayer.BGTasks.AudioTools;

/**
 * @author Rebecca Lee (Isabel Zhuang - prototype)
 * Class contains necessary methods for completing actions in many of the classes
 */
public class Utility {

	/**
	 * This method checks if the file in the input is a video file.
	 * @param path of file to be checked
	 * @return if file is a video or not
	 */
	public static boolean isVideo(String path) {
		
		String cmd = "file "+ path;
		// Determines if the file path chosen is a video file
		ProcessBuilder processBuilder = new ProcessBuilder("/bin/bash", "-c", cmd);
		Process process;
		try {
			process = processBuilder.start();
			InputStream output = process.getInputStream();
			BufferedReader stdout = new BufferedReader(new InputStreamReader(output));

			String line = null;
			while ((line = stdout.readLine()) != null) {
				if (line.matches("(.*)AVI(.*)") || line.matches("(.*)MPEG(.*)")){ // Matches video format
					return true;
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}	
		return false;
	}
	
	/**
	 * This method checks if the file in the input is a video file.
	 * @param path of file to be checked
	 * @return if file is .mp3 or not
	 */
	public static boolean isMp3(String path) {

	    String cmd = "file "+ path;
		//Determine if file chosen is a video file
		ProcessBuilder processBuilder = new ProcessBuilder("/bin/bash", "-c", cmd);
		Process process;
		try {
			process = processBuilder.start();
			InputStream output = process.getInputStream();
			BufferedReader stdout = new BufferedReader(new InputStreamReader(output));

			String line = null;
			while ((line = stdout.readLine()) != null) {
				if (line.matches("(.*): Audio file(.*)")){ // Matches audio format
					return true;
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return false;
	}
	
	/**
	 * This method checks if the file in the input is a vidivox project file.
	 * @param path of file to be checked
	 * @return if file is a project or not
	 */
	public static boolean isProject(String path) {
		String ext = path.substring(path.length() - 4);
		
		return ext.equals(".vdp");
	}
	
	/**
	 * Creates a .mp3 file based on the specifications from the input.  
	 * @param commentary from the user input
	 * @param name of the new .mp3 file
	 * @param speed of speaking for festival
	 */
	public static void saveAsMp3(String commentary, String name, Double speed) {
		
		try {

			// Creates the scheme file which creates the .sound.wav file without speaking
			AudioTools.createFestivalScheme(commentary, speed, true);

			// Generate a hidden sound.wav file from the saved user commentary
			String cmd = "festival -b ./MP3Files/.festival.scm;"
					// Create a MP3 file from the sound.wav file
					+ "ffmpeg -i ./MP3Files/.sound.wav \'./MP3Files/" + name + ".mp3\'";
			ProcessBuilder processBuilder = new ProcessBuilder("/bin/bash", "-c", cmd);
			processBuilder.start();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Checks if the string input contains any characters other than alphanumeric characters
	 * @param string to be checked
	 * @return if input contains alphanumeric chars
	 */
	public static boolean isAlphaNumeric(String s) {
		// This returns true if the string contains only alphanumeric characters, else returns false
	    String pattern= "^[a-zA-Z0-9]*$";
	    if(s.matches(pattern)){
	        return true;
	    }
	    return false;   
	}
	
	/**
	 * Finds out the number of files already in the input folder, used for suggested name generation
	 * @param folderPath to check for
	 * @return number of files
	 */
	public static int fileNumber(String folderPath) {
		// This returns the number of files in the folder
		return new File(folderPath).listFiles().length;
	}
	
	/**
	 * Formats the input time in seconss into the format mm:ss for video time labels
	 * @param seconds 
	 * @return formated timing
	 */
	public static String toMinColonSec(int seconds) {
		// This method returns the seconds in the correct format (min:sec)
		int secs = seconds % 60;
		int mins = (seconds-secs)/60;
		
		if(secs > 9) {
			return mins + ":" + secs;
		} else {
			// Add a zero before single digit seconds
			return mins + ":0" + secs;
		}
	}

}
