package videoPlayer.components.actionListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import videoPlayer.components.AudioPane;

public class StopListener implements ActionListener {

	private ArrayList<Integer> pid;
	
	public StopListener(ArrayList<Integer> pid) {
		this.pid = pid;
	}

	public void actionPerformed(ActionEvent e) {
		// Kill the festival process (Stop speaking)
		String cmd = "pstree -p " + pid.get(0);
		ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", cmd);
		try {
			Process process = builder.start();
			process.waitFor();

			InputStream stdout = process.getInputStream();
			BufferedReader stdoutbr = new BufferedReader(new InputStreamReader(stdout));

			String line = stdoutbr.readLine();

			if (line != null) {
				String festID = line.substring(line.indexOf("play(") + 5, line.indexOf(")---{play}"));
				cmd = "kill -9 " + festID;
				builder = new ProcessBuilder("/bin/bash", "-c", cmd);
				try {
					builder.start();
					pid.set(0, 0);
					AudioPane.disableCancel();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}

		} catch (IOException | InterruptedException e1) {
			e1.printStackTrace();
		}

	}
}

