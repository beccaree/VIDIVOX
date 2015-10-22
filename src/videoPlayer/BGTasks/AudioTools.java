package videoPlayer.BGTasks;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Rebecca Lee
 * Class with method to create a festival scheme file
 */
public class AudioTools {

	/**
	 * @param comment for festival to speak out
	 * @param speed at which to speak at
	 * @param whether or not to save (if false, it means speak)
	 */
	public static void createFestivalScheme(String comment, double speed, boolean save) {
		try {
			/*
			 * Writing to a text file
			 * http://stackoverflow.com/questions/2885173/how-to-create-a-file-and-write-to-a-file-in-java
			 */
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File("MP3Files/.festival.scm")));
            bw.write("(Parameter.set 'Duration_Stretch " + speed + ")");
			
            if(save) {
            	/*
            	 * Converting to .wav without speaking
            	 * Piazza 206 post 130
            	 */
				bw.write("\n(set! utt1 (Utterance Text \"" + comment + "\"))");
				bw.write("\n(utt.synth utt1)");
				bw.write("\n(utt.save.wave utt1 \"./MP3Files/.sound.wav\" 'riff)");
			} else {
				bw.write("\n(SayText \"" + comment + "\")");
			}
			
			bw.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
