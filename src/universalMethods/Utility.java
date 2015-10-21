package universalMethods;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import videoPlayer.BGTasks.AudioTools;

/**
 * @author Rebecca Lee (Isabel Zhuang - prototype)
 * Class contains necessary methods for completing actions in MainFrame and StartFrame
 */
public class Utility {

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
	
	public static boolean isProject(String path) {
		String ext = path.substring(path.length() - 3);
		
		return ext.equals("vdp");
	}
	
	public static void saveAsMp3(String commentary, String name, Double speed) {
		
		try {

			AudioTools.createFestivalScheme(commentary, speed, true);

			// Generate a hidden sound.wav file from the saved user commentary
			String cmd = "festival -b ./MP3Files/.festival.scm;"
					// Create a MP3 file from the sound.wav file
					+ "ffmpeg -i ./MP3Files/.sound.wav \'./MP3Files/" + name + ".mp3\'";
			startProcess(cmd);

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static boolean isAlphaNumeric(String s) {
		// This returns true if the string contains only alphanumeric characters, else returns false
	    String pattern= "^[a-zA-Z0-9]*$";
	    if(s.matches(pattern)){
	        return true;
	    }
	    return false;   
	}
	
	public static int fileNumber(String folderPath) {
		// This returns the number of files in the folder
		return new File(folderPath).listFiles().length;
	}
	
	public static String toMinColonSec(int seconds) {
		// This method returns the seconds in the correct format (min:sec)
		int secs = seconds % 60;
		int mins = (seconds-secs)/60;
		
		if(secs > 9) {
			return mins + ":" + secs;
		} else {
			return mins + ":0" + secs;
		}
	}

	/**
	 * Starts building a process for any BASH command passed in
	 * @param cmd
	 * @throws IOException
	 */
	private static void startProcess(String cmd) throws IOException {
		ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", cmd);
		builder.start();
	}

}
