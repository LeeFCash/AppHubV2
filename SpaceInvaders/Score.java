import javax.swing.JFrame;
import java.io.File;
import java.io.IOException;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.FileReader;
import javax.swing.JLabel;
public class Score extends JFrame {
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int windowWidth = (int)(screenSize.width * 0.30);
        int windowHeight = (int)(screenSize.height * 0.05);
	boolean check(){
//		try{
		File file = new File("score.txt");
		if(file.exists()){
			System.out.println("file does exists.");
			return true;
		} else {
			System.out.println("file does not exists.");
			return false;
		}
//		}
//		catch(IOException e){
//			e.printStackTrace();
//		}
	}
	Score(){
		if(check() == true){
			try{
				File file = new File("score.txt");
				BufferedReader reader = new BufferedReader(new FileReader(file));
				String line;
				while((line = reader.readLine()) != null){
					JLabel label = new JLabel(line);
					this.add(label);
				}
			}
			catch(IOException e){
				e.printStackTrace();
			}
		}
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		this.setTitle("Space Invaders Scores");
		this.setSize(windowWidth, windowHeight);
		this.setResizable(false);
		this.setLayout(new GridLayout(0, 1));
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
}
