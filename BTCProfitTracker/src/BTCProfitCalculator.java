
import javax.swing.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class BTCProfitCalculator extends JFrame {
	private JPanel panel;
	private JLabel priceLabel;
	private JLabel addLabel;
	private JTextField btcTextField;
	public JTextField newTextField;
	private JButton calcButton;
	private JButton addButton;
	public int counter;
	static double  btcPrice; 
	public static ArrayList<Double> buys = new ArrayList<Double>();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BTCProfitCalculator operation = new BTCProfitCalculator();
		operation.setFrame();
		btcPrice = operation.apiCall();
		
	}
	
			
	public void setFrame() {
		final int WINDOW_WIDTH = 350;
		final int WINDOW_Length = 350;
				
		buildPanel();
		add(panel);
		setLayout(new GridLayout(0,1));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
			}
	
	
	public void buildPanel() {
		priceLabel = new JLabel("Enter Price");
		btcTextField = new JTextField(10);
		calcButton = new JButton("Calculate");
		addLabel = new JLabel("Click to add new Button");
		addButton = new JButton("New Button");
		addButton.addActionListener(new addButtonListener());
		calcButton.addActionListener(new CalcButtonListener());
		panel = new JPanel(new GridLayout(0,1));
		panel.add(priceLabel);
		panel.add(calcButton);
		panel.add(btcTextField);
		panel.add(addLabel);
		panel.add(addButton);					
			}
	
			private class CalcButtonListener implements ActionListener {
				public void actionPerformed(ActionEvent e) {
				String input0;
				double val0;
				String input1;
				double val1;	
				input0 = btcTextField.getText();
				val0 = Double.parseDouble(input0);	
				if (newTextField!=null) {
					input1 = newTextField.getText();
					val1 = Double.parseDouble(input1);
					buys.add(val1);
				}
				buys.add(val0);			
				//returnBuys();
				Double s = calculate(12,buys);
				JOptionPane.showMessageDialog(null, s);
						
			}
			}
			
			private class addButtonListener implements ActionListener{
				public void actionPerformed(ActionEvent e) {
					counter+=1;
					if (newTextField!=null) {
						buys.add( Double.parseDouble(newTextField.getText()));
					}
					 newTextField = new JTextField(10);
					 panel.add(newTextField);
					 panel.validate();
					 panel.repaint();
				}
			}
			public static ArrayList<Double> returnBuys(){
				
				return buys;
			}
			
			public static double calculate(double btc, ArrayList<Double> array) {
				 double profit = 0;
				 double current_price = 0;
				
				for (int x =0;x<buys.size();x+=1) {
					
					current_price= btcPrice - buys.get(x);
					profit = profit+current_price;
				}
				System.out.println(profit);
				return profit;
				
			}
			public double apiCall() {
				 String amount;
				
			     double inum;
			     HttpClient client = HttpClient.newHttpClient();
					HttpRequest request = HttpRequest.newBuilder()
							.GET()
							.header("accept", "application/json")
							.uri(URI.create("https://api.coinbase.com/v2/prices/spot?currency=USD"))
							.build();
					
					
					
					HttpResponse<String> response;
					try {
						response = client.send(request, HttpResponse.BodyHandlers.ofString());
						String str = response.body();
						String[] arrOfStr = str.split(":"); 
						String btcPrice = arrOfStr[arrOfStr.length-1];
						String convertAmount = btcPrice.substring(0, btcPrice.length() - 3);
						convertAmount = convertAmount.substring(1);
						inum = Double.parseDouble(convertAmount);
						return inum;
						
					} catch (IOException | InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return 0.0;
			}
			
}

