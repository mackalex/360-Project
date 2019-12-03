import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.JEditorPane;
import javax.swing.JFormattedTextField;

import java.awt.TextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Formatter;
import java.awt.Font;


public class tester {
	private boolean loaded =false;
	private boolean prev = false;
	private boolean preview = false;
	private JFrame frame;
	private Parser parser = new Parser();
	private ArrayList<String> input = new ArrayList<>();
	private ArrayList<String> output = new ArrayList<>();
	private ArrayList<String> errorlog = new ArrayList<String>();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					tester window = new tester();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public tester() {
		initialize();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	
	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 696, 453);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		final TextArea textArea1 = new TextArea();
		textArea1.setFont(new Font("Consolas", Font.PLAIN, 15));
		textArea1.setBounds(159, 46, 511, 346);
		frame.getContentPane().add(textArea1);
		
		
		JButton btnSaveAs = new JButton("Save As");
		btnSaveAs.setBounds(50, 156, 89, 23);
		btnSaveAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
					String name =null;
				    
				      JFileChooser fc = new JFileChooser();
				      if (fc.showSaveDialog(null) != JFileChooser.CANCEL_OPTION)
				        name = fc.getSelectedFile().getAbsolutePath();
				    
				    if (name != null) {  // else user cancelled
				      try {
				        Formatter out = new Formatter(new File(name));  // might fail
				        String filename = name+".txt";
				        out.format("%s", textArea1.getText());
				        out.close();
				        JOptionPane.showMessageDialog(null, "Saved to " + filename,
				          "Save File", JOptionPane.PLAIN_MESSAGE);
				      }
				      catch (FileNotFoundException e1) {
				        JOptionPane.showMessageDialog(null, "Cannot write to file: " + name,
				          "Error", JOptionPane.ERROR_MESSAGE);
				      }
				    }
				  }
				
				/*
				JFrame SecondFrame = new JFrame();
		          JFileChooser fileChooser = new JFileChooser();
		          fileChooser.setDialogTitle("Specify a file to save");
		          int userSelection = fileChooser.showSaveDialog( SecondFrame);*/
			
		});
		frame.getContentPane().add(btnSaveAs);
		
		JButton btnLoadFile = new JButton("Load File");
		btnLoadFile.setBounds(50, 102, 89, 23);
		btnLoadFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser  = new JFileChooser();
				chooser.showOpenDialog(null);
				File f = chooser.getSelectedFile();
				String filename = f.getAbsolutePath();
				loaded =false;
				input =new ArrayList<>();

				if(f.getName().toLowerCase().endsWith(".txt")){
					try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
						
					    while (br.ready()) 
					        input.add(br.readLine());
					    loaded =true;
					    preview = false;
					}
					catch(Exception e1 ){
						JOptionPane.showMessageDialog(null, e1);
					}
				}
				else{
					String error= "Error: "+ f.getName()+" file is not a txt file";
					errorlog.add(error);
				}
			}
		});
		frame.getContentPane().add(btnLoadFile);
		
		JButton btnErrorLog = new JButton("Error Log");
		btnErrorLog.setBounds(50, 267, 89, 23);
		btnErrorLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea1.setText(null);

						parser.run(errorlog);
						ArrayList<String> errorlog = parser.output();
						for(String a : errorlog)
							textArea1.append(a + "\n");

			}
		});
		frame.getContentPane().add(btnErrorLog);
		
		JButton btnPreview = new JButton("Preview");
		btnPreview.setBounds(50, 209, 89, 23);
		btnPreview.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea1.setText(null);
				if(loaded == true){
					parser.run(input);
					output = parser.output();
					errorlog = parser.errorLog();
					for(String a : output)
						textArea1.append(a + "\n");
					}
				preview =true;
				}
		});
		frame.getContentPane().add(btnPreview);
	}
}
