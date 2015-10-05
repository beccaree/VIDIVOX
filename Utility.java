package universalMethods;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import merge.WaitProcessBar;

/**
 * @author Rebecca Lee (Isabel Zhuang - prototype)
 * Class contains necessary methods for completing actions in MainFrame and StartFrame
 */
public class Utility {
	
	public static void merge(String name, String videoPath, String audioPath, int time) {
		// User sees progress bar while video and audio are merging in the background
		JFrame wait = new WaitProcessBar();
		wait.setVisible(true);
		
		// Do all the merging
		
		//checking that it works with fake merging
		for(int i = 0; i<10; i++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// Close the wait frame
		wait.dispose();
	}

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
	
	public static void saveAsMp3(String commentary, String name) {
		
		try {

			File file = new File("./MP3Files/.commentary.txt");
			// If file doesn't exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(commentary);
			bw.close();
			
			// Generate a hidden sound.wav file from the saved user commentary
			String cmd = "text2wave ./MP3Files/.commentary.txt -o ./MP3Files/.sound.wav;"
					// Create a MP3 file from the sound.wav file
					+ "ffmpeg -i ./MP3Files/.sound.wav \'./MP3Files/" + name + ".mp3\'";
			startProcess(cmd);

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Starts building a process for any BASH command passed in
	 * @param cmd
	 * @throws IOException
	 */
	private static void startProcess(String cmd) throws IOException{
		ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", cmd);
		builder.start();
	}

}
