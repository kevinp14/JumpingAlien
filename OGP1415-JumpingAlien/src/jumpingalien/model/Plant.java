package jumpingalien.model;

import java.util.Random;

import be.kuleuven.cs.som.annotate.Basic;
import jumpingalien.util.Sprite;

public class Plant extends GameObject {
	private double normalHorizontalVelocity = 0.5;
	private double normalHorizontalAcceleration = 0;
	private double maxHorizontalVelocity = 0.5;
	private double timeMovingHorizontally;
	private int hitPoints = 1;
	
	public Plant(int positionX, int positionY, Sprite[] spriteList){
		super(positionX, positionY, spriteList);
	    this.timeMovingHorizontally = 0;
	}
	
	/*
	 * 0 = LEFT
	 * 1 = RIGHT
	 */
	private Direction getRandomMove(){
		Random rand = new Random();
	    int directionNumber = rand.nextInt(2);
	    if (directionNumber == 0)
	    	return Direction.LEFT;
	    else
	    	return Direction.RIGHT;
	}
	
	protected double horizontalMovement(double dt) throws IllegalArgumentException {
		if (! isValidDt(dt))
			throw new IllegalArgumentException("The given period of time dt is invalid!");
		if ((this.getPosition()[0] <= 0) && (this.getHorizontalVelocity() < 0)) {
			this.setHorizontalVelocity(0);
		}
		if ((this.getPosition()[0] >= this.getMaxPosition()[0]) && (this.getHorizontalVelocity() > 0)) {
			this.setHorizontalVelocity(0);
		}
		else {
			if (this.isMovingHorizontally()) {
				if (!(this.timeMovingHorizontally < 50)) {
					this.startMoveHorizontally(this.getNextDirection());
				}
			}
			else {
				this.startMoveHorizontally(this.getRandomMove());
			}
		}
		double newPositionX = this.getHorizontalVelocity() * dt;
		return newPositionX;
	}
	
	public void advanceTime(double dt) throws IllegalArgumentException {
		if (! isValidDt(dt))
			throw new IllegalArgumentException("The given period of time dt is invalid!");
		this.setPosition(this.getPosition()[0] + (int)(100 * this.horizontalMovement(dt)),
				this.getPosition()[1]);
		if (this.getPosition()[0] < 0)
			this.setPosition(0, this.getPosition()[1]);
		if (this.getPosition()[0] > this.getMaxPosition()[0])
			this.setPosition(this.getMaxPosition()[0], this.getPosition()[1]);
		if (this.getPosition()[1] < 0)
			this.setPosition(this.getPosition()[0], 0);
		if (getPosition()[1] > getMaxPosition()[1])
			this.setPosition(this.getPosition()[0], this.getMaxPosition()[1]);
		if (!(this.getHorizontalVelocity() == 0)) {
			this.timeMovingHorizontally += 1;
		}
		if (this.collidesWith(this.world.getMazub()))
			this.changeNbHitPoints(-1);
	}
}