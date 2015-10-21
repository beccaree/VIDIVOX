package videoPlayer.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import universalMethods.Utility;
import videoPlayer.BGTasks.BgForward;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;

@SuppressWarnings("serial")
public class VideoPane extends JPanel {
	
	private static JProgressBar bar;
	private static JButton btnPlay;
	private static JLabel lblVideoName;
	
	public static MediaPlayer video;
	protected static String currentVideoPath;
	protected static int skipInterval = 5;
	protected static Icon pauseIcon;
	private static int currentVideoLength = 0; // In seconds
	
	public static boolean playClicked = true;
	protected boolean muteClicked = false;
	public static boolean stopForward = false;

	public VideoPane(String videoPath, JFrame parent, Color theme) {
		
		// Left side of the split pane
		setLayout(new BorderLayout());
		        
		// Add a media component
		EmbeddedMediaPlayerComponent component = new EmbeddedMediaPlayerComponent();
		add(component, BorderLayout.CENTER);
		video = component.getMediaPlayer();
				
		JPanel controls = new JPanel(); // Holds everything under the video player
		add(controls, BorderLayout.SOUTH);
		controls.setLayout(new BoxLayout(controls, BoxLayout.Y_AXIS));
				
		JPanel progress = new JPanel(); // Holds the time in seconds and the progress bar (in controls Panel)
		controls.add(progress);
		progress.setLayout(new BoxLayout(progress, BoxLayout.X_AXIS));
				
		final JLabel lblTime = new JLabel("0:00"); // Shows the time in seconds since the start of video (GUI)
		progress.add(lblTime);
				
		bar = new JProgressBar(); // Shows the progress of the video (GUI)
		bar.addMouseListener(new MouseAdapter() {            
		    public void mouseClicked(MouseEvent e) {
		    	// http://stackoverflow.com/questions/18146914/get-value-on-clicking-jprogressbar ------->

		        // Retrieves the mouse position relative to the component origin.
		        int mouseX = e.getX();
		        // Computes how far along the mouse is relative to the component width then multiply it by the progress bar's maximum value.
		        int positionClicked = (int)Math.round((((double)mouseX / (double)bar.getWidth()) * bar.getMaximum())*1000); // In Milliseconds
		        
		        video.setTime(positionClicked);
		        bar.setValue(positionClicked/1000);

		   }                                     
		});
		bar.setForeground(theme);
		progress.add(bar);
		
		final JLabel lblTimeLeft = new JLabel("0:00");
		progress.add(lblTimeLeft);

		JPanel video_control = new JPanel(); // Holds all the video control buttons (in controls Panel)
		controls.add(video_control);
		video_control.setLayout(new BoxLayout(video_control, BoxLayout.X_AXIS));
				
		// Initialize all the buttons in video_control Panel
		JButton btnSkipBack = new JButton();
		btnSkipBack.setIcon(new ImageIcon(getClass().getResource("/buttons/skipb.png")));
		JButton btnRewind = new JButton();
		btnRewind.setIcon(new ImageIcon(getClass().getResource("/buttons/rewind.png")));
		btnPlay = new JButton();
		pauseIcon = new ImageIcon(getClass().getResource("/buttons/pause.png"));
		btnPlay.setIcon(pauseIcon);
		JButton btnForward = new JButton();
		btnForward.setIcon(new ImageIcon(getClass().getResource("/buttons/forward.png")));
		JButton btnSkipForward = new JButton();
		btnSkipForward.setIcon(new ImageIcon(getClass().getResource("/buttons/skipf.png")));
				
		btnSkipBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Skips backward 5 seconds every time it is clicked
				video.skip(-skipInterval*1000);
			}
		});
		video_control.add(btnSkipBack);
				
		btnRewind.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Continues rewinding until user clicks play
				playClicked = true;
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				playClicked = false;
				btnPlay.setIcon(new ImageIcon(getClass().getResource("/buttons/play.png"))); // Set button to play
				BgForward rewind = new BgForward(-500, video); // Make a new background task
				rewind.execute();
			}
		});
		video_control.add(btnRewind);
				
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Play or pause video depending on boolean variable playClicked
				if(!playClicked) {
					btnPlay.setIcon(new ImageIcon(getClass().getResource("/buttons/pause.png")));
					video.play(); // Play the video
					playClicked = true;
					stopForward = false;
				} else {
					btnPlay.setIcon(new ImageIcon(getClass().getResource("/buttons/play.png")));
					video.pause(); // Pause the video
					playClicked = false;
				}
			}
		});
		video_control.add(btnPlay);

		btnForward.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Continues forwarding until user clicks play
				playClicked = false;
				btnPlay.setIcon(new ImageIcon(getClass().getResource("/buttons/play.png"))); // Set button to play
				BgForward forward = new BgForward(500, video); // Make a new background task
				forward.execute();
			}
		});
		video_control.add(btnForward);
				
		btnSkipForward.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Skips forward 5 seconds every time it is clicked
				video.skip(skipInterval*1000);
			}
		});
		video_control.add(btnSkipForward);
		
		JPanel video_info = new JPanel();
		controls.add(video_info);
		video_info.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblCurrently = new JLabel("Currently Playing: ");
		video_info.add(lblCurrently);
		
		lblVideoName = new JLabel();
		video_info.add(lblVideoName);
		
		JPanel volume_control = new JPanel(); // Holds the volume control buttons (JSlider and Mute button)
		controls.add(volume_control);
		volume_control.setLayout(new BorderLayout(0, 0));
				
		JPanel panel_1 = new JPanel(); // Panel used for layout purposes
		volume_control.add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
				
		JLabel lblVolume = new JLabel("Volume"); // Label to tell user JSlider is for volume control
		panel_1.add(lblVolume);
		
		final JButton btnMute = new JButton(); // Initialize btnMute here for use in JSlider actionListener
		
		final JSlider slider = new JSlider(); // JSlider for volume control
		slider.addChangeListener(new ChangeListener() {
		     @Override
		     public void stateChanged(ChangeEvent e) {
		     	// Change the volume of the video to the value obtained from the slider
		    	 video.setVolume(((JSlider) e.getSource()).getValue());
		     }
		});
		slider.setMajorTickSpacing(50);
		slider.setMinorTickSpacing(10);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		panel_1.add(slider);
				
		btnMute.setIcon(new ImageIcon(getClass().getResource("/buttons/mute.png")));
		btnMute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Toggle the mute for the video depending on boolean variable muteClicked
				if(!muteClicked) {
					btnMute.setIcon(new ImageIcon(getClass().getResource("/buttons/unmute.png")));
					video.mute(); // Toggles mute
					muteClicked = true;
					slider.setEnabled(false);
				} else {
					btnMute.setIcon(new ImageIcon(getClass().getResource("/buttons/mute.png")));
					video.mute(); // Toggles mute
					muteClicked = false;
					slider.setEnabled(true);
				}
			}
		});
		panel_1.add(btnMute);
		setMinimumSize(new Dimension(300, 500)); // Sets minimum dimensions for resizing purposes
				
		video.addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
			@Override
			public void finished(MediaPlayer mediaPlayer) {
				// Play button for playing again when video finishes playing
				playClicked = false;
				btnPlay.setIcon(new ImageIcon(getClass().getResource("/buttons/play.png")));
				stopForward = true; // For stopping the BgForward SwingWorker implementation (fast forwarding)
			}
		});
				
		// Timer for updating the label and progress bar every half a second
		Timer timer = new Timer(500, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblTime.setText(Utility.toMinColonSec((int)(video.getTime())/1000)); // Update the label
				lblTimeLeft.setText(Utility.toMinColonSec((int)(currentVideoLength - (video.getTime()/1000))));
				bar.setValue((int)(video.getTime()/1000)); // Update the progress bar
				if(video.getLength() == 0) {
					// If video gets to the start, stop the rewinding
					stopForward = true;
				}
			}
		});
		timer.start();
				
		// For fixing problem where video being muted to start with, when last execution exits while muted.
		parent.addWindowListener(new WindowAdapter() {
			@Override
		    public void windowClosing(WindowEvent e) {
				e.getWindow().dispose();
				// If the video is muted, unmuted before exiting the program
				if(muteClicked) {
					video.mute();
				}
			}
		});
	}
	
	public static void setMaxBar(int length) {
		// Sets the maximum length of the progress bar to length
		bar.setMaximum(length);
	}
	
	public static void setVideoName(String vidName) {
		// Sets the name of the currently playing video for the user to see
		lblVideoName.setText(vidName);
	}

	public static void setCurrentVideoPath(String newPath) {
		// Sets the current video path to the new path
		currentVideoPath = newPath;
	}
	
	public static String getCurrentVideoPath() {
		// Returns the path of the video currently playing
		return currentVideoPath;
	}

	public static void setPlayBtnIcon() {
		// Sets the icon of play button
		if(!playClicked) {
			btnPlay.setIcon(pauseIcon);
			playClicked = true;
		}
	}

	public static void setSkipInterval(int second) {
		// Sets the skip interval of video player to user's choice
		skipInterval = second;
	}

	public static void setVideoLength(int length) {
		// Sets the max video field so we can use it in the lblTimeLeft
		currentVideoLength = length;
	}

	public static int getVideoLength() {
		// Returns video length of current video in seconds
		return currentVideoLength;
	}

	public static void setTheme(Color c) {
		// Changes the theme (color) of the progress bar
		bar.setForeground(c);
	}
}
