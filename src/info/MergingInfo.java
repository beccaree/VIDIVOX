package info;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.Font;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

/**
 * @author Rebecca Lee (Isabel Zhuang - prototype)
 * Class contains graphical user interface code for the merging instructions and information.
 */
@SuppressWarnings("serial")
public class MergingInfo extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public MergingInfo() {
		setTitle("Help - Merging Functions");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(350, 250, 450, 300);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel title_panel = new JPanel();
		title_panel.setBackground(Color.LIGHT_GRAY);
		contentPane.add(title_panel, BorderLayout.NORTH);
		
		JLabel lblTitle = new JLabel("How To Merge");
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 20));
		title_panel.add(lblTitle);
		
		JPanel bottom = new JPanel();
		contentPane.add(bottom, BorderLayout.CENTER);
		bottom.setLayout(new BorderLayout(0, 0));
		
		JPanel buttons_panel = new JPanel();
		buttons_panel.setBackground(Color.DARK_GRAY);
		bottom.add(buttons_panel, BorderLayout.NORTH);
		buttons_panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		final JLabel lblSubtitle = new JLabel("Merge Audio here...");
		// Explanations begin with \n for formatting reasons (looks better)
		final JTextArea textArea = new JTextArea("Please ensure you create the MP3 file you want to merge first before you click the merge button.\n"
				+"The merge function will merge the chosen audio at the point where the video is at, "
				+ "although you can change the time at which you want to merge within the prompt that pops up.\n"
				+ "This video is then saved in a folder named VideoFiles.");
		
		JButton btnMergeAudioHere = new JButton("Merge Audio here...");
		btnMergeAudioHere.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Help on how the merge button works
				lblSubtitle.setText("Merging audio at any point");
				textArea.setText("Please ensure you create the MP3 file you want to merge first before you click the merge button.\n"
						+"The merge function will merge the chosen audio at the point where the video is at, "
						+ "although you can change the time at which you want to merge within the prompt that pops up.\n"
						+ "This video is then saved in a folder named VideoFiles.");
			}
		});
		buttons_panel.add(btnMergeAudioHere);
		
		JPanel explain_panel = new JPanel();
		explain_panel.setBackground(Color.LIGHT_GRAY);
		explain_panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		bottom.add(explain_panel);
		explain_panel.setLayout(new BorderLayout(0, 0));
		
		explain_panel.add(lblSubtitle, BorderLayout.NORTH);
		
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		textArea.setBackground(Color.LIGHT_GRAY);
		textArea.setForeground(Color.BLACK);
		explain_panel.add(textArea);
	}

}
