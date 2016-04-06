package currencyConverter;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.util.Arrays;
import java.text.DecimalFormat;

import javax.swing.JFrame;
import javax.swing.JTextField;
//import javax.swing.JMenuBar;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.math.RoundingMode;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;



public class CurrencyExchange {

	private JFrame frmCurrencyExchangeBy;
	
	String[][] rates = new String[56][56];
	private JTextField fromT;
	private JTextField toT;
	
	


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) throws Exception {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CurrencyExchange window = new CurrencyExchange();
					window.frmCurrencyExchangeBy.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}

	/**
	 * Create the application.
	 * @throws SQLException 
	 */
	public CurrencyExchange() throws Exception {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws Exception 
	 * @throws SQLException 
	 */
	private void initialize() throws Exception {
		
		
		frmCurrencyExchangeBy = new JFrame();
		frmCurrencyExchangeBy.setTitle("Currency Exchange by JDG Technologies");
		frmCurrencyExchangeBy.setBounds(100, 100, 450, 300);
		frmCurrencyExchangeBy.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCurrencyExchangeBy.getContentPane().setLayout(null);
		
		fromT = new JTextField();
		fromT.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		fromT.setBounds(30, 70, 134, 48);
		frmCurrencyExchangeBy.getContentPane().add(fromT);
		fromT.setColumns(10);
		
		toT = new JTextField();
		toT.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		toT.setEditable(false);
		toT.setBounds(30, 146, 134, 48);
		frmCurrencyExchangeBy.getContentPane().add(toT);
		toT.setColumns(10);
		
		final JComboBox<String> from = new JComboBox<String>();
		from.setBounds(206, 84, 210, 27);
		frmCurrencyExchangeBy.getContentPane().add(from);
	
		
		final JComboBox<String>to = new JComboBox<String>();
		to.setBounds(206, 160, 210, 27);
		frmCurrencyExchangeBy.getContentPane().add(to);
		
		JButton btnConvert = new JButton("Convert");
		btnConvert.setFont(new Font("Lao Sangam MN", Font.PLAIN, 27));
		btnConvert.setBounds(30, 206, 356, 51);
		frmCurrencyExchangeBy.getContentPane().add(btnConvert);
		
		JLabel lblNewLabel = new JLabel("Currency Converter");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 27));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 6, 444, 52);
		frmCurrencyExchangeBy.getContentPane().add(lblNewLabel);
		
		Class.forName("com.mysql.jdbc.Driver");
		//try{
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/currencyconverter", "root", "root");
		PreparedStatement statement = con.prepareStatement("select * from currencyConverter");
		final ResultSet result = statement.executeQuery();
		while(result.next()){
			from.addItem(result.getString(1));
			to.addItem(result.getString(1));
		}
		//}catch(Exception e){
			//System.out.println("Exception");
		//}
		result.absolute(0);
		//System.out.println(result.getString(1));
		
		btnConvert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				double txt1 = Double.parseDouble(fromT.getText());
				//System.out.println(txt1);
				double txt2;
				DecimalFormat df = new DecimalFormat("#.00");
				//df.setRoundingMode(RoundingMode.CEILING);
				try {
					result.absolute(0);
					while(result.next()){
						if(from.getSelectedItem().equals(result.getString(1))){
							txt1 = txt1 / Double.parseDouble(result.getString(2));
							//System.out.println("hit" + result.getString(2));
						}
						//System.out.println(result.getString(1));
					}
					result.absolute(0);
					while(result.next()){
						if(to.getSelectedItem().equals(result.getString(1))){
							txt2 = txt1 * Double.parseDouble(result.getString(2));
							df.format(txt2);
							toT.setText(Double.toString(txt2));
						}
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
	}
}
