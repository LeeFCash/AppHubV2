import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

public class GameWindow extends JFrame {
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	int windowWidth = (int)(screenSize.width * 0.30);
	int windowHeight = (int)(screenSize.height * 0.90);
	GameWindow(){
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		this.setTitle("Space Invaders Game Window");
		this.setSize(windowWidth, windowHeight);
		this.setResizable(false);
//		this.setLayout(new GridLayout(3, 1));
		this.setLocationRelativeTo(null);
		this.add(new GameWindowPanel(windowWidth, windowHeight));
		this.setVisible(true);
	}
}
