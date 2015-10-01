package videoPlayer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;
import javax.swing.Timer;

import uk.co.caprica.vlcj.player.MediaPlayer;

/**
 * @author Rebecca Lee (Isabel Zhuang - prototype)
 * Class executes fast-forwarding and rewinding in the background until the user clicks play
 */
public class BgForward extends SwingWorker<Void, Void> {
	
	private int interval;
	private MediaPlayer video;

	public BgForward(int interval, MediaPlayer video) {
		this.interval = interval;
		this.video = video;
	}
	
	/* 
	 * Uses javas.swing.Timer to continually skip bits of video to simulate fast forward and rewind
	 * @see javax.swing.SwingWorker#doInBackground()
	 */
	@Override
	protected Void doInBackground() throws Exception {
		
		// Skip by interval every time timer clicks, negative = rewind, positive = forward
		Timer timer = new Timer(100, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!VideoPane.playClicked) { // While user has not clicked play
					if(!VideoPane.stopForward) { // And While it is not the start or end of the video
						video.skip(interval);
					} else {
						((Timer)e.getSource()).stop(); 
					}
				} else {
					((Timer)e.getSource()).stop();
				}
			}
		});
		timer.start();
		
		return null;
	}
}
