package jumpingalien.model;

import be.kuleuven.cs.som.annotate.Basic;
import jumpingalien.util.Sprite;

public class Shark extends GameObject {
	private Sprite[] spriteList;
	private double timeStalled;
	private double timeMoving;
	private Direction lastDirection;
	private int hitPoints = 100;
	
	public Shark(int positionX, int positionY, Sprite[] spriteList) {
		super(positionX, positionY);
		this.spriteList = spriteList;		
	}

	public Sprite getCurrentSprite() {
	
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
			this.setNbHitPoints(-50);
		if (/*in lucht*/)
			this.setNbHitPoints((int)(-6 * (dt % (0.2))));
		if (/*in lava*/)
			this.setNbHitPoints((int)(-50 *((dt + 1) % (0.2))));
	}
}
