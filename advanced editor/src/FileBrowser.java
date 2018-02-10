import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class FileBrowser extends JPanel implements ActionListener{
	private static final long serialVersionUID = 1L;
	JLabel label = new JLabel("File List: ");
    JButton newFile = new JButton("New File");
    JButton open = new JButton("Open");
    JButton logOut = new JButton("Log Out");
    JTextField newFileTF = new JTextField(10);
    ButtonGroup bg;
    File directory;
    FileBrowser(String path){
    	directory = new File(path);
    	directory.mkdir();
    	JPanel fileList = new JPanel(new GridLayout(directory.listFiles().length+4,1));
    	fileList.add(label);
    	bg = new ButtonGroup();
    	for(File file : directory.listFiles()) {
    		JRadioButton name = new JRadioButton(file.getName());
    		name.setActionCommand(file.getName());
    		bg.add(name);
    		fileList.add(name);
    	}
    	JPanel options = new JPanel(new GridLayout(3,1));
    	open.addActionListener(this);
    	newFile.addActionListener(this);
    	JPanel newF = new JPanel(new GridLayout(1,2));
    	newF.add(newFileTF);
    	newF.add(newFile);
    	options.add(newF);
    	options.add(open);
    	logOut.addActionListener(this);
    	options.add(logOut);
    	add(fileList);
    	add(options);
    }
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Login login = (Login) getParent();
		if(e.getSource() == open) {
			login.add(new Editor(directory.getName()+"\\"+bg.getSelection().getActionCommand()),"editor");
			login.cl.show(login,"editor");
		}
		if(e.getSource() == newFile) {
			String file = directory.getName()+"\\"+newFileTF.getText()+".txt";
			if(newFileTF.getText().length() > 0 && !(new File(file).exists())) {
				login.add(new Editor(file),"editor");
				login.cl.show(login, "editor");
			}
		}
		if(e.getSource() == logOut) {
			login.userTF.setText("");
			login.passTF.setText("");
			login.cl.show(login,"login");
		}
	}
}
