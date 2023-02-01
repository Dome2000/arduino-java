
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import com.fazecast.jSerialComm.SerialPort;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
public class TEST {
	
	static SerialPort chosenPort;

	private static int T=1;
	
	
	
	public static void main(String[] args) {
		
		
		JFrame window = new JFrame();
		window.setBackground(Color.WHITE);
		window.getContentPane().setBackground(Color.GRAY);
		window.getContentPane().setForeground(Color.WHITE);
		window.setTitle("DHT11");
		window.setSize(593, 526);
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.getContentPane().setLayout(null);
		
		
		JComboBox<String> portList = new JComboBox<String>();
		portList.setFont(new Font("TRS Million", Font.PLAIN, 15));
		portList.setForeground(new Color(0, 0, 0));
		portList.setBackground(new Color(255, 255, 255));
		JButton connectButton = new JButton("Connect");
		connectButton.setFont(new Font("TRS Million", Font.PLAIN, 15));
		connectButton.setForeground(new Color(0, 0, 0));
		connectButton.setBackground(new Color(255, 255, 255));
		JPanel topPanel = new JPanel();
		topPanel.setBounds(0, 0, 584, 33);
		topPanel.setBackground(Color.GRAY);
		topPanel.add(portList);
		topPanel.add(connectButton);
		window.getContentPane().add(topPanel);
		
		JLabel lblNewLabel = new JLabel("loading...");
		lblNewLabel.setBounds(257, 337, 195, 33);
		lblNewLabel.setForeground(Color.ORANGE);
		lblNewLabel.setFont(new Font("Dialog", Font.PLAIN, 22));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		window.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Humidity(%)");
		lblNewLabel_1.setBounds(12, 337, 195, 42);
		lblNewLabel_1.setForeground(Color.ORANGE);
		lblNewLabel_1.setFont(new Font("OCR A Std", Font.PLAIN, 24));
		window.getContentPane().add(lblNewLabel_1);
		
		JLabel TextC = new JLabel("loading...");
		TextC.setBounds(257, 188, 195, 33);
		TextC.setForeground(Color.BLUE);
		TextC.setHorizontalAlignment(SwingConstants.CENTER);
		TextC.setFont(new Font("Dialog", Font.PLAIN, 22));
		window.getContentPane().add(TextC);
		
		JLabel TextF = new JLabel("loading...");
		TextF.setBounds(257, 259, 195, 42);
		TextF.setForeground(Color.GREEN);
		TextF.setHorizontalAlignment(SwingConstants.CENTER);
		TextF.setFont(new Font("Dialog", Font.PLAIN, 22));
		window.getContentPane().add(TextF);
		
		JLabel lblNewLabel_1_1 = new JLabel("Celsius(C)");
		lblNewLabel_1_1.setBounds(9, 188, 170, 42);
		lblNewLabel_1_1.setForeground(Color.BLUE);
		lblNewLabel_1_1.setFont(new Font("OCR A Std", Font.PLAIN, 24));
		window.getContentPane().add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Fahrenheit(F)");
		lblNewLabel_1_2.setBounds(12, 264, 221, 42);
		lblNewLabel_1_2.setForeground(Color.GREEN);
		lblNewLabel_1_2.setFont(new Font("OCR A Std", Font.PLAIN, 24));
		window.getContentPane().add(lblNewLabel_1_2);
		
		JLabel TIME = new JLabel("TIME");
		TIME.setBounds(12, 72, 551, 85);
		TIME.setForeground(Color.BLUE);
		TIME.setFont(new Font("Tahoma", Font.PLAIN, 30));
		TIME.setHorizontalAlignment(SwingConstants.CENTER);
		window.getContentPane().add(TIME);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setBounds(256, 395, 167, 33);
		lblNewLabel_2.setForeground(Color.BLACK);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		window.getContentPane().add(lblNewLabel_2);
		
		JLabel lblStatus = new JLabel("Status");
		lblStatus.setBounds(12, 395, 167, 33);
		lblStatus.setForeground(Color.YELLOW);
		lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 30));
		window.getContentPane().add(lblStatus);
		
		
		
		Timer myTimer;
		myTimer = new Timer();
		myTimer.schedule(new TimerTask() {
			public void run() {
				Calendar c = Calendar.getInstance();
				DateFormat df = new SimpleDateFormat("EEEE dd MMMM yyyy HH:mm:ss");
				String time = df.format(c.getTime());
				TIME.setText(time);
				}
			}, 0, 100);
		
		
		
		
		
		SerialPort[] portNames = SerialPort.getCommPorts();
		for(int i = 0; i < portNames.length; i++) {
			 portList.addItem(portNames[i].getSystemPortName());
		}
		connectButton.addActionListener(new ActionListener(){
			@Override public void actionPerformed(ActionEvent arg0) {
				
				if(connectButton.getText().equals("Connect")) {
					chosenPort = SerialPort.getCommPort( portList.getSelectedItem().toString());
					chosenPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
					chosenPort.setComPortParameters(9600,8,1,0);
					if(chosenPort.openPort()) {
						connectButton.setText("disconnect");
						portList.setEnabled(false);
					}
					
					
					Thread thread = new Thread(){
						@Override public void run() {
							while(true) {
								Scanner in = new Scanner(chosenPort.getInputStream());
								Scanner scanner = new Scanner(chosenPort.getInputStream());
								
								while(scanner.hasNextLine()) {
							
									try {
										String number = String.valueOf(in.next());
										System.out.println(number);
										
										
										if(T==1) {
											lblNewLabel.setText(number);
											T++;
										}
										else if (T==2) {
											TextC.setText(number);
											T++;
											if(Float.valueOf(number) > 28) {
												lblNewLabel_2.setText("TO HOT");
											
											}
											else {
												lblNewLabel_2.setText("Normal");
											}
										}
										else{
											TextF.setText(number);
											T=1;
										}
										window.repaint();
									} catch(Exception e) {}
								}
							}
						}
					};
					thread.start();
				} else {
					
					chosenPort.closePort();
					portList.setEnabled(true);
					connectButton.setText("Connect");
					
				}

			}
		});
		
		
		window.setVisible(true);
	}
}