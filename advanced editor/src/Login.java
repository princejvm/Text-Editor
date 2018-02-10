import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login extends JPanel implements ActionListener{
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
    	/*CardLayout-->only one component is visible at a time.
    	 *  It treats each component as a card
    	 *  card.next(c); c-->container
    	 *  panel is also a specific container
    	 */
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
    	add(panel);
    	cl = (CardLayout) getLayout();
    }
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public static void main(String[] args) {
    		JFrame frame = new JFrame("Text Editor");
    		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    		frame.setSize(500,500);
    		frame.add(new Login());
    		frame.setVisible(true);
	}
}
