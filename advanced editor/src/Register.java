import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Register extends JPanel implements ActionListener{
	private static final long serialVersionUID = 1L;
	JLabel userL = new JLabel("Choose a username: ");
	JTextField userTF = new JTextField();
	JLabel passL = new JLabel("Enter Password: ");
	JPasswordField passTF = new JPasswordField();
	JLabel passLC = new JLabel("Re-enter Password: ");
	JPasswordField passTFC = new JPasswordField();
	JButton register = new JButton("Register");
	JButton back = new JButton("Back");
	Register(){
		JPanel loginP = new JPanel();
		loginP.setLayout(new GridLayout(4,2));
		loginP.add(userL);
		loginP.add(userTF);
		loginP.add(passL);
		loginP.add(passTF);
		loginP.add(passLC);
		loginP.add(passTFC);
		register.addActionListener(this);
		back.addActionListener(this);
		loginP.add(register);
		loginP.add(back);
		add(loginP);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == register && passTF.getPassword().length > 0 && userTF.getText().length() > 0){
			String pass = new String(passTF.getPassword());
			String cnfrmPass = new String(passTFC.getPassword());
			if(pass.equals(cnfrmPass)) {
				try {
					BufferedReader input = new BufferedReader(new FileReader("passwords.txt"));
					String line = input.readLine();
					while(line != null) {
						StringTokenizer	st = new StringTokenizer(line);
						if(userTF.getText().equals(st.nextToken())) {
							JOptionPane.showMessageDialog(this,"Username already in use",
								    "Username error",
								    JOptionPane.ERROR_MESSAGE);
							System.out.println("Username already exists");
							userTF.setText("");
							passTF.setText("");
							passTFC.setText("");
							input.close();
							return;
						}
						line = input.readLine();
					}
					input.close();
					MessageDigest md = MessageDigest.getInstance("SHA-256");
					//getInstance()-->Returns a MessageDigest object that implements the specified digest algorithm.
					md.update(pass.getBytes());
					//update()-->Updates the digest using the specified byte.
					byte byteData[] = md.digest();
					//digest()-->Completes the hash computation by performing final operations such as padding. The digest is reset after this call is made.
					StringBuffer sb = new StringBuffer();
					for(int i = 0; i < byteData.length; i++) {
						sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
					}
					BufferedWriter output = new BufferedWriter(new FileWriter("passwords.txt"));
					output.write(userTF.getText()+" "+sb);
					output.close();
					Login login = (Login) getParent();
					login.cl.show(login, "login");
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NoSuchAlgorithmException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		if(e.getSource() == back) {
			Login login = (Login) getParent();
			login.cl.show(login, "login");
		}
	}
}
