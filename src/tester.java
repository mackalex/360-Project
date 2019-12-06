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
import javax.swing.JTextArea;


public class tester {
	private boolean loaded =false;
	private boolean prev = false;
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
		frame.setBounds(100, 100, 885, 501);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		 JTextArea textArea2 = new JTextArea();
		textArea2.setBounds(36, 141, 119, 221);
		frame.getContentPane().add(textArea2);
		
		
		 TextArea textArea1 = new TextArea();
		textArea1.setFont(new Font("Monospaced", Font.PLAIN, 12));
		textArea1.setBounds(183, 46, 676, 378);
		frame.getContentPane().add(textArea1);
		
		
		JButton btnSaveAs = new JButton("Save As");
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
		btnSaveAs.setBounds(50, 75, 89, 23);
		frame.getContentPane().add(btnSaveAs);
		
		JButton btnLoadFile = new JButton("Load File");
		btnLoadFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser  = new JFileChooser();
				chooser.showOpenDialog(null);
				File f = chooser.getSelectedFile();
				String filename = f.getAbsolutePath();
				
				input =new ArrayList<>();
				textArea1.setText(null);
				textArea2.setText(null);
				
				
				if(f.getName().toLowerCase().endsWith(".txt")){
					try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
							//get data
					    while (br.ready()) 
					        input.add(br.readLine());
					    	//parse data
					    parser.run(input);
					    	//get error log
					    //errorlog = parser.errorLog();
							//get preview
						output = parser.output();
						for(String a : output)
							textArea1.append(a + "\n");
					}
					catch(Exception e1 ){
						JOptionPane.showMessageDialog(null, e1);
					}
				}
				else{
					String error= "Error: "+ f.getName()+" file is not a txt file";
					errorlog.add(error);
				}
				
				for(String a : errorlog)
					textArea2.append(a + "\n");
				
			}
		});
		btnLoadFile.setBounds(50, 29, 89, 23);
		frame.getContentPane().add(btnLoadFile);
		
		
	}
}
