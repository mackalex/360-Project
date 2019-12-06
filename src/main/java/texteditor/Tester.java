package main.java.texteditor;

import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.TextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Formatter;
import java.awt.Font;


public class Tester {
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
					Tester window = new Tester();
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
	public Tester() {
		initialize();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	
	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 970, 555);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		final TextArea textArea2 = new TextArea();
		textArea2.setBounds(10, 435, 886, 71);
		frame.getContentPane().add(textArea2);
		
		final TextArea textArea1 = new TextArea();
		textArea1.setBounds(119, 39, 777, 378);
		textArea1.setFont(new Font("Monospaced", Font.PLAIN, 12));
		frame.getContentPane().add(textArea1);
		
		
		JButton btnSaveAs = new JButton("Save As");
		btnSaveAs.setBounds(10, 255, 89, 23);
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
		});
		frame.getContentPane().add(btnSaveAs);
		
		JButton btnLoadFile = new JButton("Load File");
		btnLoadFile.setBounds(10, 148, 89, 23);
		btnLoadFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser  = new JFileChooser();
				chooser.showOpenDialog(null);
				File f = chooser.getSelectedFile();
				String filename = f.getAbsolutePath();
				errorlog = new ArrayList<>();
				input =new ArrayList<>();
				textArea1.setText(null);
				textArea2.setText(null);
				
				
				if(f.getName().toLowerCase().endsWith(".txt")){
					try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
					    while (br.ready()) 
					        input.add(br.readLine());
					    parser.run(input);
						output = parser.output();
						for(String a : output)
							textArea1.append(a + "\n");
						if(output.size()==0){
							String error= "Error: empty file";
							errorlog.add(error);
						}
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
		frame.getContentPane().add(btnLoadFile);
		
		JLabel lblPreview = new JLabel("Preview");
		lblPreview.setBounds(145, 11, 48, 35);
		frame.getContentPane().add(lblPreview);
		
		JLabel lblNewLabel = new JLabel("Error Log");
		lblNewLabel.setBounds(10, 388, 67, 35);
		frame.getContentPane().add(lblNewLabel);
		

		
	}
}
