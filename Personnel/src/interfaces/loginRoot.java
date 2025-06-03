package interfaces;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class loginRoot {
	
	private JLabel labelTitre;



	private JPanel getTitrePanel()
	{
		JPanel panel = new JPanel();
		panel.add(getLabelTitre());
		return panel;
	}
	

	private Component getLabelTitre() {
		labelTitre = new JLabel("Page connexion");
		return labelTitre ;
	}

	 private JLabel getLabelMotPass()
	 {
	  return new JLabel("Mot de passe = ");
	 }
	 
	
	private JPanel getPanelLogin()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,2));
		panel.add(getLabelMotPass());
		return panel;
		
	}
	
	 private JPanel getMainPanel()
	 {
	  JPanel panel = new JPanel();
	  panel.setLayout(new BorderLayout());
	  panel.add(getTitrePanel(), BorderLayout.NORTH);
	  panel.add(getPanelLogin(), BorderLayout.CENTER);
	  return panel;
	 }

	 
	 
	public loginRoot()
	{
		JFrame FrameLogin = new JFrame();
		FrameLogin.setTitle("M2L");
		FrameLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		FrameLogin.setContentPane(getMainPanel());
		FrameLogin.setVisible(true);
		FrameLogin.pack();
}
	
	public static void main(String [] args)
	{
		new loginRoot();
	}
}
