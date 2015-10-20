package videoPlayer.components;

import info.AudioInfo;
import info.MergingInfo;
import info.VideoInfo;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import universalMethods.Utility;
import videoPlayer.MainFrame;

@SuppressWarnings("serial")
public class MyMenuBar extends JMenuBar {
	
	public MyMenuBar(final JFrame parent) {
				
		JMenu mnFile = new JMenu("File");
		add(mnFile);
				
		JMenuItem mntmOpenNewVideo = new JMenuItem("Open New Video...");
		mntmOpenNewVideo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Changing the video that we are editing
				// Prompt user for the video they want to change to
						
				String newPath;
				JFileChooser videoChooser = new JFileChooser(System.getProperty("user.dir") + "/VideoFiles/");
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Video Files (*.avi) or (*.mp4)", "avi", "mp4");
				videoChooser.setFileFilter(filter);
				int okReturnVal = videoChooser.showOpenDialog(getParent());
				if(okReturnVal == JFileChooser.APPROVE_OPTION) {
					newPath = videoChooser.getSelectedFile().getPath();
					if(Utility.isVideo(newPath)) {
						// Set current video path to new path
						VideoPane.setCurrentVideoPath(newPath);
						MainFrame.initialiseVideo();
						VideoPane.setPlayBtnIcon();
					} else {
						JOptionPane.showMessageDialog(parent, "The file you have chosen is not a video, please try again.");
					}
				}
				
			}
		});
		mnFile.add(mntmOpenNewVideo);
				
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Exit the program
				System.exit(0);
			}
		});
		mnFile.add(mntmExit);
				
		JMenu mnEdit = new JMenu("Edit");
		add(mnEdit);
		
		JMenu mnSkip = new JMenu("Set Skip Interval to...");
		mnEdit.add(mnSkip);
		
		JMenuItem mntmInterval5 = new JMenuItem("5 Seconds");
		mntmInterval5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Set the interval to 5 seconds
				VideoPane.setSkipInterval(5);
				JOptionPane.showMessageDialog(parent, "The skip interval has been set to 5 seconds.");
			}
		});
		mnSkip.add(mntmInterval5);
		
		JMenuItem mntmInterval10 = new JMenuItem("10 Seconds");
		mntmInterval10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Set the interval to 10 seconds
				VideoPane.setSkipInterval(10);
				JOptionPane.showMessageDialog(parent, "The skip interval has been set to 10 seconds.");
			}
		});
		mnSkip.add(mntmInterval10);
		
		JMenuItem mntmInterval15 = new JMenuItem("15 Seconds");
		mntmInterval15.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Set the interval to 15 seconds
				VideoPane.setSkipInterval(15);
				JOptionPane.showMessageDialog(parent, "The skip interval has been set to 15 seconds.");
			}
		});
		mnSkip.add(mntmInterval15);
		
		JMenuItem mntmInterval20 = new JMenuItem("20 Seconds");
		mntmInterval20.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Set the interval to 20 seconds
				VideoPane.setSkipInterval(20);
				JOptionPane.showMessageDialog(parent, "The skip interval has been set to 20 seconds.");
			}
		});
		mnSkip.add(mntmInterval20);
		
		JMenu mnColor = new JMenu("Theme...");
		mnEdit.add(mnColor);
		
		JMenuItem mntmPink = new JMenuItem("Pink");
		mntmPink.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Set the theme to pink
				VideoPane.setTheme(Color.pink);
				AudioPane.setTheme(Color.pink);
			}
		});
		mnColor.add(mntmPink);
		
		JMenuItem mntmBlue = new JMenuItem("Blue");
		mntmBlue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Set the theme to blue
				VideoPane.setTheme(Color.cyan);
				AudioPane.setTheme(Color.cyan);
			}
		});
		mnColor.add(mntmBlue);
		
		JMenu mnHelp = new JMenu("Help");
		add(mnHelp);
		
		JMenuItem mntmVideo = new JMenuItem("Video Controls");
		mntmVideo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Bring up instructions on video control
				VideoInfo vid = new VideoInfo();
				vid.setVisible(true);
			}
		});
		mnHelp.add(mntmVideo);	
		
		JMenuItem mntmMerging = new JMenuItem("Merging");
		mntmMerging.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Bring up instructions on merging
				MergingInfo merge = new MergingInfo();
				merge.setVisible(true);
			}
		});
		mnHelp.add(mntmMerging);
		
		JMenuItem mntmCreating = new JMenuItem("Creating an MP3");
		mntmCreating.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Bring up instructions on creating mp3
				AudioInfo audio = new AudioInfo();
				audio.setVisible(true);
			}
		});
		mnHelp.add(mntmCreating);
	}

}
