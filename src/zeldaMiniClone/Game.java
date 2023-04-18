package zeldaMiniClone;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable, KeyListener{
	private static final long serialVersionUID = 1L;
	
	public static int WIDTH = 640, HEIGHT = 480;
	public static int SCALE = 3;
	public Player player;
	public World world;
	
	public List<Inimigo> inimigos = new ArrayList<Inimigo>();

	public Game() {// Classe principal
		this.addKeyListener(this);//Para escutar as teclas nesta classe
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		new Spritesheet();
		player = new Player(32,32);
		world = new World();
		
		inimigos.add(new Inimigo(32, 32));
		inimigos.add(new Inimigo(32, 70));
	}
	
	public void tick() {// Lógica do jogo(colisão, movimentação, etc...)
		player.tick();
		
		for(int i = 0; i < inimigos.size(); i++) {
			inimigos.get(i).tick(); //Realizar a movimentação de todos os inimigos 
		}
	}
	
	public void render() {//Renderiza todos os gráficos
		BufferStrategy bs = this.getBufferStrategy();//Para poder desenhar na tela
		
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();//Para usar os comandos de criação de desenhos na tela
		
		g.setColor(new Color(0,135,13));
		g.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);//Background
		
		player.render(g);
		
		for(int i = 0; i < inimigos.size(); i++) {
			inimigos.get(i).render(g); //Renderizar todos os inimigos 
		}
		
		world.render(g);
		
		bs.show();//Se n, n aparece a imagem na tela
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		JFrame frame = new JFrame();
		
		frame.add(game);
		frame.setTitle("Mini Zelda");
		
		frame.pack();//Calcular o tamanho certo da nossa janela
		
		frame.setLocationRelativeTo(null); //Para centralizar a tela do jogo		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Ao fechar o jogo, para de usar a memória do pc
		
		frame.setVisible(true);
		
		new Thread(game).start(); //chamar o método run de Runnable
	}
	@Override
	public void run() { //Onde todas as ações do jogo ocorrem
		while(true) {
			tick();
			render();
			
			try {//Rodar a 60FPS
				Thread.sleep(1000/60);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			player.right = true;
		} else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.left = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			player.up = true;
		} else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			player.down = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_Z) {
			player.shoot = true;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {//Quando solta a tecla
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			player.right = false;
		} else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.left = false;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			player.up = false;
		} else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			player.down = false;
		}
		
	}

}
