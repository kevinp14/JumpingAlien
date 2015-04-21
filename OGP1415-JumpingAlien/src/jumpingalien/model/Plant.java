package jumpingalien.model;

import be.kuleuven.cs.som.annotate.Basic;
import jumpingalien.util.Sprite;

public class Plant extends GameObject {
	private Sprite[] spriteList;
	private double timeStalled;
	private double timeMoving;
	private Direction lastDirection;
	private int hitPoints = 1;
	
	public Plant(int positionX, int positionY, Sprite[] spriteList){
		super(positionX, positionY);
		this.spriteList = spriteList;
		this.timeMoving = 0;
		this.timeStalled = 0;
		this.lastDirection = Direction.STALLED;
	}
	
	public Sprite[] getCurrentSprite() {
		
	}
	
	public void advanceTime(double dt) {
			
	}
}