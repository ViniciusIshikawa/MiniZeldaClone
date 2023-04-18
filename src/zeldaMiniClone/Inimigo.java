package zeldaMiniClone;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class Inimigo extends Rectangle{//Pois o retângulo de todas as boxs de colisão
	private static final long serialVersionUID = 1L;
	
	public int right = 1, up = 0, down = 0, left = 0;
	public int spd = 2;
	public int curAnimation = 0;
	public int curFrames = 0, targetFrame = 10;
	public static List<Bullet> bullets = new ArrayList<>();
	public boolean shoot = false;
	public int dir = 1;
	
	public Inimigo(int x, int y) {
		super(x,y,32,32);//Medidas do retângulo(player)
	}
	
	public void perseguirPlayer() {
		Player p = Game.player;
		
		if(x < p.x && World.isFree(x+spd, y)) {
			x += spd;
		} else if(x > p.x && World.isFree(x-spd, y)) {
			x-=spd;
		}
		
		if(y < p.y && World.isFree(x, y+spd)) {
			y+= spd;
		} else if(y > p.y && World.isFree(x, y-spd)) {
			y-=spd;
		}
	}

	public void tick() {//Lógica do player
		boolean moved = true;

		perseguirPlayer();
		
		if(moved) {
			curFrames++;
			if(curFrames == targetFrame) {
				curFrames = 0;
				curAnimation++;
				
				if(curAnimation == Spritesheet.inimigo_front.length) {
					curAnimation = 0;
				}
			}
		}
		
		if(shoot) {
			shoot = false;
			bullets.add(new Bullet(x, y, dir));
		}
		
		for(int i = 0; i < bullets.size(); i++) {
			bullets.get(i).tick();
		}
	}
	
	public void render(Graphics g) {//Rederização do player
		//g.setColor(Color.blue);
		//g.fillRect(x, y, width, height);
		g.drawImage(Spritesheet.inimigo_front[curAnimation],x,y,32,32, null);	
		
		for(int i = 0; i < bullets.size(); i++) {
			bullets.get(i).render(g);
		}
	}
}
