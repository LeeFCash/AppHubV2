import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.GridLayout;
public class Menu extends JFrame {
	//
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	int width = (int)(screenSize.width * 0.30);
	int height = (int)(screenSize.height * 0.50);
	//
	Menu(){
		JButton newGame = new JButton("Start new game");
		JButton seeScore = new JButton("see scores");
		JButton quitAll = new JButton("quit all");
		newGame.setBounds(200, 100, 100, 50);
		seeScore.setBounds(200, 100, 100, 50);
		quitAll.setBounds(200, 100, 100, 50);
		seeScore.addActionListener(e->{
			Score score = new Score();
			System.out.println("seeScore clicked.");
		});
		quitAll.addActionListener(e->{
			System.exit(0);
		});
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		this.setTitle("Space Invaders Menu");
		this.setSize(width, height);
		this.setLayout(new GridLayout(3, 1));
		this.setLocationRelativeTo(null);
		this.add(newGame);
		this.add(seeScore);
		this.add(quitAll);
		this.setVisible(true);
		//
	}
}
