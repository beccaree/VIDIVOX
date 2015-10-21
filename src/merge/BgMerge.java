package merge;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import videoPlayer.MainFrame;
import videoPlayer.components.VideoPane;

/**
 * @author Rebecca Lee (Isabel Zhuang - prototype)
 * Class contains background merge process of video and audio.
 */
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
	
	/* 
	 * Combines audio input with video input at specified time
	 * @see javax.swing.SwingWorker#doInBackground()
	 */
	@Override
	protected Void doInBackground() throws Exception {
		
		// Extract the audio from the video and save it as a hidden file in MP3Files
		String cmd = "ffmpeg -y -i " + videoPath + " -map 0:1 ./MP3Files/.vidAudio.mp3";
		
		ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", cmd);
		Process process = builder.start();
		process.waitFor();
		
		for(int i = 1; i < 25; i++) { // Update progress bar when above process is complete
			Thread.sleep(40);
			progress.updateProgress(i);
		}
		if(time == 0) {
			// Create a hidden mp3 file output.mp3 that combines the video audio and selected mp3 audio at time 0 
			cmd = "ffmpeg -y -i " + audioPath + " -i ./MP3Files/.vidAudio.mp3 -filter_complex amix=inputs=2 ./MP3Files/.output.mp3";
		} else {
			// Create a hidden mp3 file output.mp3 that combines the video audio and selected mp3 audio at specified time 
			cmd = "ffmpeg -y -i " + audioPath + " -i ./MP3Files/.vidAudio.mp3 -filter_complex \"[0:0]adelay=" + time + "[aud1];[aud1][1:0]amix=inputs=2\" ./MP3Files/.output.mp3";
		}
		
		builder = new ProcessBuilder("/bin/bash", "-c", cmd);
		process = builder.start();
		process.waitFor();
		
		for(int i = 1; i < 50; i++) { // Update progress bar
			Thread.sleep(40);
			progress.updateProgress(25+i);
		}
		
		// Creates an output.avi file from merging existing video stream (1:0) and combined audio (0:0)
		cmd = "ffmpeg -i " + videoPath + " -i ./MP3Files/.output.mp3 -map 0:0 -map 1:0 -acodec copy -vcodec copy ./VideoFiles/"+ name +".avi";

		builder = new ProcessBuilder("/bin/bash", "-c", cmd);
		process = builder.start();
		process.waitFor();
		
		for(int i = 1; i < 25; i++) { // Update progress bar
			Thread.sleep(40);
			progress.updateProgress(75+i);
		}
		
		return null;
	}
	
	@Override
	protected void done() {
		// Close the merging progress bar
		progress.dispose();
		
		// Prompt user for whether they want to play the video that has just been merged
		int dialogButton = JOptionPane.YES_NO_OPTION;
		dialogButton = JOptionPane.showConfirmDialog (progress, name + ".avi has been saved to VideoFiles" + "Would you like to play it now?", "alert", dialogButton);
		if(dialogButton == JOptionPane.YES_OPTION) {
			VideoPane.setCurrentVideoPath(System.getProperty("user.dir") + "/VideoFiles/" + name + ".avi");
			MainFrame.initialiseVideo();
			VideoPane.setPlayBtnIcon();
		}
	}
}
