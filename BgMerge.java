package merge;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

public class BgMerge extends SwingWorker<Void, Integer> {

	private String name;
	private String videoPath;
	private String audioPath;
	private long time;
	private WaitProcessBar progress;
	
	public BgMerge(String name, String videoPath, String audioPath, long time, WaitProcessBar progress) {
		this.name = name;
		this.videoPath = videoPath;
		this.audioPath = audioPath;
		this.time = time;
		this.progress = progress;
	}
	
	@Override
	protected Void doInBackground() throws Exception {
		
		// Command which extracts the audio from the video and saves it as a hidden file in MP3Files
		String cmd = "ffmpeg -y -i " + videoPath + " -map 0:1 ./MP3Files/.vidAudio.mp3";
		
		ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", cmd);
		Process process = builder.start();
		process.waitFor();
		
		for(int i = 1; i < 25; i++) {
			Thread.sleep(40);
			progress.updateProgress(i);
		}
		if(time == 0) {
			// Creates a hidden mp3 file output.mp3 that combines the video audio and selected mp3 audio for merging 
			cmd = "ffmpeg -y -i " + audioPath + " -i ./MP3Files/.vidAudio.mp3 -filter_complex amix=inputs=2 ./MP3Files/.output.mp3";
		} else {
			cmd = "ffmpeg -y -i " + audioPath + " -i ./MP3Files/.vidAudio.mp3 -filter_complex \"[0:0]adelay=" + time + "[aud1];[aud1][1:0]amix=inputs=2\" ./MP3Files/.output.mp3";
		}
		
		builder = new ProcessBuilder("/bin/bash", "-c", cmd);
		process = builder.start();
		process.waitFor();
		
		for(int i = 1; i < 50; i++) {
			Thread.sleep(40);
			progress.updateProgress(25+i);
		}
		
		// Creates an output.avi file from merging existing video stream (1:0) and combined audio (0:0)
		cmd = "ffmpeg -i " + videoPath + " -i ./MP3Files/.output.mp3 -map 0:0 -map 1:0 -acodec copy -vcodec copy ./VideoFiles/"+ name +".avi";

		builder = new ProcessBuilder("/bin/bash", "-c", cmd);
		process = builder.start();
		process.waitFor();
		
		for(int i = 1; i < 25; i++) {
			Thread.sleep(40);
			progress.updateProgress(75+i);
		}
		
		return null;
	}
	
	@Override
	protected void done() {
		JOptionPane.showMessageDialog(progress, name +".avi has been saved to VideoFiles.");
		progress.dispose();
	}
}
