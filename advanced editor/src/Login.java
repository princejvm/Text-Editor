import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login extends JPanel implements ActionListener{
	private static final long serialVersionUID = 1L;
	JLabel userL = new JLabel("Username: ");
    JTextField userTF = new JTextField();
    JLabel passL = new JLabel("Password: ");
    JPasswordField passTF = new JPasswordField();
    JPanel loginP = new JPanel(new GridLayout(3,2));
    JPanel panel = new JPanel();
    JButton login = new JButton("LOGIN");
    JButton register = new JButton("REGISTER");
    CardLayout cl;
    Login(){
    	setLayout(new CardLayout());
    	//add every component in bottom-up approach
    	loginP.add(userL);
    	loginP.add(userTF);
    	loginP.add(passL);
    	loginP.add(passTF);
    	/*this is used as we ActionListener as a part of it-->basically it's
    	 * the reference to the class which implemented listener
    	 */
    	login.addActionListener(this);
    	register.addActionListener(this);
    	loginP.add(login);
    	loginP.add(register);
    	panel.add(loginP);
    	add(panel,"login");
    	cl = (CardLayout) getLayout();
    }
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getSource() == login) {
			try {
				BufferedReader input = new BufferedReader(new FileReader("passwords.txt"));
				String line = input.readLine();
				String pass = "";
				while(line != null) {
					StringTokenizer st = new StringTokenizer(line);
					if(userTF.getText().equals(st.nextToken()))
						pass = st.nextToken();
					line = input.readLine();
				}
				input.close();
				if(pass.equals("")) {
					JOptionPane.showMessageDialog(this,"User doesn't exist","Invalid Username",JOptionPane.ERROR_MESSAGE);
					userTF.setText("");
				    passTF.setText("");
				    return;
				}
				MessageDigest md = MessageDigest.getInstance("SHA-256");
				md.update(new String(passTF.getPassword()).getBytes());
				byte byteData[] = md.digest();
				StringBuffer sb = new StringBuffer();
				for(int i = 0; i< byteData.length; i++) {
					sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
				}
				if(pass.equals(sb.toString())) {
					add(new FileBrowser(userTF.getText()),"fb");
					cl.show(this,"fb");
				}
				else {
					JOptionPane.showMessageDialog(this,"Password is not correct!!","Password Error",JOptionPane.ERROR_MESSAGE);
				    passTF.setText("");
				    return;
				}
					
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(arg0.getSource() == register) {
			add(new Register(), "register");
			cl.show(this, "register");
		}
	}
	public static void main(String[] args) {
    		JFrame frame = new JFrame("Text Editor");
    		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    		frame.setSize(500,500);
    		frame.add(new Login());
    		frame.setVisible(true);
	}
}
