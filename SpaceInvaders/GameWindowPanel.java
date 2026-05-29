import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
public class GameWindowPanel extends JPanel implements ActionListener, KeyListener {
	int windowWidth;
	int windowHeight;
	int playerXP;
	int playerYP;
	int playerWidth;
	int playerHeight;
	int playerSpeed = 6;
	int enemySpawnTimer = 0;
	int enemyY = 0;
	int score = 0;
	String inputFileScore;
	boolean leftPressed;
	boolean rightPressed;
	boolean upPressed;
	boolean downPressed;
	boolean gameOver = false;
	ArrayList<Rectangle> bullets = new ArrayList<>();
	ArrayList<Rectangle> enemiesBullets = new ArrayList<>();
	ArrayList<Rectangle> enemies = new ArrayList<>();
//	ArrayList<Rectangle> enemiesR = new ArrayList<>();
	Timer timer;
	GameWindowPanel(int width, int height) {
		this.windowWidth = width;
		this.windowHeight = height;
		this.setBackground(Color.BLACK);
		this.setFocusable(true);
		this.addKeyListener(this);
		playerWidth = 50;
		playerHeight = 20;
		playerXP = windowWidth / 2 - playerWidth / 2;
		playerYP = windowHeight - 100;
		timer = new Timer(16, this);
		timer.start();
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.GREEN);
		g.fillRect(
			playerXP,
			playerYP,
			playerWidth,
			playerHeight
		);
		g.setColor(Color.WHITE);
		for(Rectangle bullet : bullets) {
			g.fillRect(
				bullet.x,
				bullet.y,
				bullet.width,
				bullet.height
			);
		}
		g.setColor(Color.WHITE);
		for(Rectangle enemiesBullet : enemiesBullets) {
			g.fillRect(
				enemiesBullet.x,
				enemiesBullet.y,
				enemiesBullet.width,
				enemiesBullet.height
			);
		}
		g.setColor(Color.RED);
		for(Rectangle enemy : enemies) {
			g.fillRect(
					enemy.x,
					enemy.y,
					enemy.width,
					enemy.height
				);
		}
		if(gameOver) {
			g.setColor(Color.WHITE);
			g.drawString(
				"GAME OVER " + score,
				windowWidth / 2 - 40,
				windowHeight / 2
				);
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(gameOver) {
			inputFileScore = "Player score is " + score;
			try {
				FileWriter fileScore = new FileWriter("score.txt");
				fileScore.write(inputFileScore);
				fileScore.close();
			} catch (IOException s) {
				System.out.println("An error occurred.");
				s.printStackTrace();
			}
			repaint();
			Score score = new Score();
			timer.stop();
			return;
		}
		enemySpawnTimer++;
		if(enemySpawnTimer >= 60) {
			enemySpawnTimer = 0;
			if(Math.random() < 0.5) {
				int enemyWidth = 40;
				int enemyHeight = 40;
				int enemyX = (int)(Math.random() * (windowWidth - enemyWidth));
				enemies.add(
						new Rectangle(
							enemyX,
							0,
							enemyWidth,
							enemyHeight
							)
						);
			}
		}
		Rectangle playerRect = new Rectangle(
			playerXP,
			playerYP,
			playerWidth,
			playerHeight
		);
		if(leftPressed) {
			playerXP -= playerSpeed;
		}
		if(rightPressed) {
			playerXP += playerSpeed;
		}
		if(upPressed) {
			playerYP -= playerSpeed;
		}
		if(downPressed) {
			playerYP += playerSpeed;
		}
		if(playerXP < 0) {
			playerXP = 0;
		}
		if(playerXP > windowWidth - playerWidth) {
			playerXP = windowWidth - playerWidth;
		}
		if(playerYP < 0) {
			playerYP = 0;
		}
		if(playerYP > windowHeight - playerHeight) {
			playerYP = windowHeight - playerHeight;
		}
		for(int i = 0; i < bullets.size(); i++) {
			Rectangle bullet = bullets.get(i);
			bullet.y -= 10;
			if(bullet.y < 0) {
				bullets.remove(i);
				i--;
			}
		}
		for(int eIndex = 0; eIndex < enemies.size(); eIndex++) {
			Rectangle enemy = enemies.get(eIndex);
			for(int i = 0; i < bullets.size(); i++) {
				Rectangle bullet = bullets.get(i);
				if(enemy.intersects(bullet)) {
					bullets.remove(i);
					enemies.remove(eIndex);
					score++;
					i--;
					eIndex--;
					break;
				}
			}
			if(eIndex < 0 || eIndex >= enemies.size()) {
				continue;
			}
			enemy = enemies.get(eIndex);
			if(enemy.intersects(playerRect)) {
				gameOver = true;
			}
			if(enemy.y + enemy.height >= windowHeight) {
				gameOver = true;
			}
			enemy.y += 1;
			int randomNumber =(int)(Math.random() * 2) + 1;
			if(randomNumber == 2 && enemySpawnTimer == 59) {
				enemiesBullets.add(
					new Rectangle(
						enemy.x + enemy.width / 2 - 2,
						enemy.y,
						5,
						10
					)
				);
			}
		}
		int randomNumberBulletSpeed =(int)(Math.random() * 10);
		while(randomNumberBulletSpeed != 0){
			for(int r = 0; r < enemiesBullets.size(); r++) {
				Rectangle enemiesBullet = enemiesBullets.get(r);
				enemiesBullet.y += 3;
				if(enemiesBullet.intersects(playerRect)) {
					gameOver = true;
				}
				if(enemiesBullet.y > windowHeight) {
					enemiesBullets.remove(r);
					r--;
				}
			}
			randomNumberBulletSpeed =(int)(Math.random() * 1);
		}
		repaint();
	}
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_A) {
			leftPressed = true;
		}
		if(key == KeyEvent.VK_D) {
			rightPressed = true;
		}
		if(key == KeyEvent.VK_W) {
			upPressed = true;
		}
		if(key == KeyEvent.VK_S) {
			downPressed = true;
		}
		if(key == KeyEvent.VK_SPACE) {
			bullets.add(
				new Rectangle(
					playerXP + playerWidth / 2 - 2,
					playerYP,
					5,
					10
				)
			);
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_A) {
			leftPressed = false;
		}
		if(key == KeyEvent.VK_D) {
			rightPressed = false;
		}
		if(key == KeyEvent.VK_W) {
			upPressed = false;
		}
		if(key == KeyEvent.VK_S) {
			downPressed = false;
		}
	}
	@Override
	public void keyTyped(KeyEvent e) {
	}
}
