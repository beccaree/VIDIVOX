package videoPlayer.BGTasks;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class AudioTools {

	public static void createFestivalScheme(String comment, double speed, boolean save) {
		try {
			// http://stackoverflow.com/questions/2885173/how-to-create-a-file-and-write-to-a-file-in-java
			
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File("MP3Files/.festival.scm")));
            bw.write("(Parameter.set 'Duration_Stretch " + speed + ")");
			
            if(save) {
            	// Piazza 206 post 130
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
