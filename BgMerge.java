package merge;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

public class BgMerge extends SwingWorker<Void, Void> {

	private String name;
	private String videoPath;
	private String audioPath;
	private int time;
	private JFrame progress;
	
	public BgMerge(String name, String videoPath, String audioPath, int time, JFrame progress) {
		this.name = name;
		this.videoPath = videoPath;
		this.audioPath = audioPath;
		this.time = time;
		this.progress = progress;
	}
	
	@Override
	protected Void doInBackground() throws Exception {
		
		// Command which extracts the audio from the video and saves it as a hidden file in MP3Files
		String cmd = "ffmpeg -y -i " + videoPath + " -map 0:1 ./MP3Files/.vidAudio.mp3;"
				// Creates a hidden mp3 file output.mp3 that combines the video audio and selected mp3 audio for merging 
				+ "ffmpeg -y -i " + audioPath + " -i ./MP3Files/.vidAudio.mp3 -filter_complex amix=inputs=2 ./MP3Files/.output.mp3;"
				// Creates an output.avi file from merging combined audio (0:0) and existing video stream (1:0)
				+ "ffmpeg -i ./MP3Files/.output.mp3 -i " + videoPath + " -map 0:0 -map 1:0 -acodec copy -vcodec copy ./VideoFiles/"+ name +".avi";

		ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", cmd);
		Process process = builder.start();
		
		process.waitFor();
		
		return null;
	}
	
	@Override
	protected void done() {
		progress.dispose();
		JOptionPane.showMessageDialog(progress, name +".avi has been saved to VideoFiles.");
	}
}
