package videoPlayer.BGTasks;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.SwingWorker;

import videoPlayer.components.AudioPane;

/**
 * @author Rebecca Lee (Isabel Zhuang - prototype)
 * Class executes festival text-to-speech from commentary entered by the user
 */
public class BgFestival extends SwingWorker<Void, Void> {

	protected int pid;
	private ArrayList<Integer> killPID = new ArrayList<Integer>();

	public BgFestival(ArrayList<Integer> killPID) {
		this.killPID = killPID;
	}

	/* 
	 * Uses festival to speak out the commentary to the user
	 * @see javax.swing.SwingWorker#doInBackground()
	 */
	@Override
	protected Void doInBackground() throws Exception {

		try {			
			// Speak with festival
			String cmd = "echo $$; festival -b ./MP3Files/.festival.scm; echo 'done'"; // echo $! returns the process ID of the process just executed

			ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", cmd);
			Process process = builder.start();

			// Read the process ID for the process executed above
			InputStream stdout = process.getInputStream();
			BufferedReader stdoutbr = new BufferedReader(new InputStreamReader(stdout));
			String line = stdoutbr.readLine();
			
			pid = Integer.parseInt(line); // Stores ID for canceling(Stop)
			killPID.add(pid);
			
			line = stdoutbr.readLine();
			while(!line.equals("done")) {
				line = stdoutbr.readLine();
			}
						
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	@Override
	public void done() {
		AudioPane.disableCancel();
	}
}
