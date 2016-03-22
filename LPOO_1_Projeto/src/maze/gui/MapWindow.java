package maze.gui;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

import maze.logic.Direction;
import maze.logic.Dragao;
import maze.logic.GameMode;
import maze.logic.Jogo;

public class MapWindow extends JPanel implements KeyListener{

	private Jogo j;
	private BufferedImage hero;
	private BufferedImage wall;
	private BufferedImage sword;
	private BufferedImage dragon;
	private BufferedImage win;
	private BufferedImage lose;
	private GameMode mode;

	public MapWindow(Jogo j1,GameMode m) {

		this.addKeyListener(this);

		try {
			hero =  ImageIO.read(new File("hero.jpg"));
			wall =  ImageIO.read(new File("wall.jpg"));
			sword =  ImageIO.read(new File("sword.jpg"));
			dragon =  ImageIO.read(new File("dragon.jpg"));
			win =  ImageIO.read(new File("win.jpg"));
			lose =  ImageIO.read(new File("lose.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		j=j1;
		mode=m;
		requestFocus();
	}
	public int dimensionFrame(){
		int dim = j.getLabirinto().getMaze().length;
		if(dim == 5 || dim == 15)
			return 600;
		else if(dim == 11)
			return 605;
		else if(dim == 13)
			return 604;
		else if(dim == 17 )
			return 612;
		else if(dim == 21)
			return 609;
		else
			return 608;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g); // clears the backgorund ...
		char[][] maze=j.getLabirinto().getMaze();
		int width = dimensionFrame()/maze.length ;
		int height = dimensionFrame()/maze.length ;
		if(j.getFimJogo()){
			g.drawImage(lose, 50, 50, 200, 200, null);
		}
		else if(j.getSair()){
			g.drawImage(win, 50, 50, 200, 200, null);
		}
		else{
			
			for (int i = 0; i < maze.length; i++) {
				for (int j = 0; j < maze[i].length; j++) {
					switch (maze[i][j]) {
					case 'd':
						g.drawImage(dragon, j*width, i*height, width, height , null);
						break;
					case 'D':
						g.drawImage(dragon, j*width, i*height, width, height , null);
						break;
					case 'H': 
						g.drawImage(hero, j*width, i*height, width, height , null);
						break;
					case 'E':
						g.drawImage(sword, j*width, i*height, width, height , null);
						break;
					case 'A':
						g.drawImage(hero, j*width, i*height, width, height , null);
						break;
					case 'X':
						g.drawImage(wall, j*width, i*height, width, height , null);
						break;
					default:
						break;
					}
				}
			}
		}
	}

	public void update() {
		repaint();	
	}

	private void dragonAction(){

		switch (mode) {
		case StaticDragon://dragao parado
			break; 
		case MovingDragon://Dragao com Movimentacao 
			for (Dragao d : j.getDragoes()) {
				j.moveDragon(d);
			} 
			j.checkDragon();
			break;
		case ToogleSleepAndMoveDragon://Dragao com Movimentacao intercalada com Dormir 
			j.toggleAdormecerRandom();
			for (Dragao d : j.getDragoes()) {
				if(d.getAdormecido()==false){//se n�o estiver adormecido o dragao pode mover-se
					j.moveDragon(d);
				}
			}
			j.checkDragon();
			break;
		default:
			break;
		} 
	}


	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()){
		case KeyEvent.VK_LEFT: 
			j.move(Direction.LEFT); 
			break;

		case KeyEvent.VK_RIGHT: 
			j.move(Direction.RIGHT);
			break;

		case KeyEvent.VK_UP: 
			j.move(Direction.UP);
			break;

		case KeyEvent.VK_DOWN: 
			j.move(Direction.DOWN);
			break;

		}
		dragonAction();
		repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}


	
}
