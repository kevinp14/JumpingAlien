package jumpingalien.model;

import be.kuleuven.cs.som.annotate.Basic;
import jumpingalien.util.Sprite;

public class Shark extends Mazub {
	private double positionX;
	private double positionY;
	private Sprite[] spriteList;
	private int timeMoving;
	private Direction lastDirection;
	private int hitPoints = 100;
	
	public Shark(int positionX, int positionY, Sprite[] spriteList) {
		this.positionX = (double)positionX/100d;
		this.positionY = (double)positionY/100d;
		this.spriteList = spriteList;
		
	}

	public Sprite getCurrentSprite() {
	
	}
	
	@Basic
	public int[] getPosition() {
		int[] position = new int[]{(int)this.positionX, (int)this.positionY};
		return position;
	}
	
	@Basic
	private void setPosition(double positionX, double positionY) {
		this.positionX = positionX;
		this.positionY = positionY;
	}
	
	public int getHitPoints(){
		return this.hitPoints;
	}
	
	private void setHitPoints(int hitPointsDifference) {
		this.hitPoints += hitPointsDifference;
	}
	
	public void advanceTime(double dt) throws IllegalArgumentException {
		if (! isValidDt(dt)) 
			throw new IllegalArgumentException("The given period of time dt is invalid!");
		this.setPosition();
		if (this.getPosition()[0] < 0)
			this.setPosition(0, this.getPosition()[1]);
		if (this.getPosition()[0] > this.getMaxPosition()[0])
			this.setPosition(this.getMaxPosition()[0], this.getPosition()[1]);
		if (this.getPosition()[1] < 0)
			this.setPosition(this.getPosition()[0], 0);
		if (getPosition()[1] > getMaxPosition()[1])
			this.setPosition(this.getPosition()[0], this.getMaxPosition()[1]);
		if (/*botsing*/)
			this.setHitPoints(-50);
		if (/*in lucht*/)
			this.setHitPoints((int)(-6 * (dt % (0.2))));
		if (/*in lava*/)
			this.setHitPoints((int)(-50 *((dt + 1) % (0.2))));
	}
}
