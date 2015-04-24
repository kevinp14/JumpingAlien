package jumpingalien.model;

import java.util.Random;

import be.kuleuven.cs.som.annotate.Basic;
import jumpingalien.util.Sprite;

public class Slime extends GameObject {
	private double normalHorizontalVelocity = 0;
	private double normalHorizontalAcceleration = 0.7;
	private double maxHorizontalVelocity = 2.5;
	private School school;
	private double timeMovingHorizontally;
	private int hitPoints = 100;

	public Slime (int positionX, int positionY, Sprite[] spriteList, School school) {
		super(positionX, positionY, spriteList);
	    this.timeMovingHorizontally = 0;
		this.school = school;
	}
	
	@Basic
	public School getSchool(){
		return this.school;
	}
	
	public void setSchool(School newSchool){
		this.school.removeSlimeFromSchool(this);
		this.school = newSchool;
		newSchool.addSlimeToSchool(this);
	}
	
	private int getRandomMovingTime() {
		Random rand = new Random();
		int movingTime = rand.nextInt(60);
		if (movingTime > 20)
			return movingTime;
		else
			return (movingTime + 20);
	}
	
	/*
	 * 0 = LEFT
	 * 1 = RIGHT
	 */
	private Direction getRandomDirection(){
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
		if (Math.abs(this.getHorizontalVelocity()) >= this.getMaxHorizontalVelocity()) {
			this.setHorizontalAcceleration(0);
			if (this.getHorizontalVelocity() < 0) {
				this.setHorizontalVelocity(-this.getMaxHorizontalVelocity());
			} else
				this.setHorizontalVelocity(this.getMaxHorizontalVelocity());
		}
		if ((this.getPosition()[0] <= 0) && (this.getHorizontalVelocity() < 0)) {
			this.setHorizontalAcceleration(0);
			this.setHorizontalVelocity(0);
		}
		if ((this.getPosition()[0] >= this.getMaxPosition()[0]) && (this.getHorizontalVelocity() > 0)) {
			this.setHorizontalAcceleration(0);
			this.setHorizontalVelocity(0);
		}
		if (this.isMovingHorizontally()) {
			int movingTime = this.getRandomMovingTime();
			if (!(this.timeMovingHorizontally < movingTime))
				this.endMoveHorizontally(this.getLastDirection());
				this.startMoveHorizontally(this.getRandomDirection());
		}
		if (!this.isMovingHorizontally())
			this.startMoveHorizontally(this.getRandomDirection());
		this.setHorizontalVelocity(this.getHorizontalVelocity() + this.getHorizontalAcceleration() * dt);
		double newPositionX = this.getHorizontalVelocity() * dt - 
				this.getHorizontalAcceleration() * Math.pow(dt, 2) + 
				this.getHorizontalAcceleration() * Math.pow(dt, 2) / 2;
		return newPositionX;
	}
	
	private double verticalMovement(double dt) throws IllegalArgumentException {
		if (! isValidDt(dt))
			throw new IllegalArgumentException("The given period of time dt is invalid!");
		if ((this.getPosition()[1] <= 0) && (this.getVerticalVelocity() < 0)) {
			this.setVerticalAcceleration(0);
			this.setVerticalVelocity(0);
		}
		if ((this.getVerticalVelocity() <= 0) && (this.world.isNotPassable(
				this.world.getGeologicalFeature((int)this.getPosition()[0], 
						(int)this.getPosition()[1])))) {
			this.setVerticalAcceleration(0);
			this.setVerticalVelocity(0);
			this.setPosition(this.getPosition()[0], 
					this.world.getBottomLeftPixelOfTile((int)this.getPosition()[0], 
					(int)this.getPosition()[1])[1] + this.world.getTileLength());
		}			
		if (this.getPosition()[1] >= this.getMaxPosition()[1]) {
			this.setVerticalVelocity(this.normalVerticalAcceleration*dt);
		}
		if ((this.getVerticalVelocity() >= 0) && 
				(this.world.isNotPassable(this.world.getGeologicalFeature((int)this.getPosition()[0], 
				(int)this.getPosition()[1] + this.getCurrentSprite().getHeight())))) {
			this.setVerticalVelocity(0);
			this.setPosition(this.getPosition()[0], 
					this.world.getBottomLeftPixelOfTile((int)this.getPosition()[0],
					(int)this.getPosition()[1])[1] - this.world.getTileLength());
		}
		if ((this.getVerticalVelocity() == 0) && (!(this.world.isNotPassable(
				this.world.getGeologicalFeature((int)this.getPosition()[0], 
						(int)this.getPosition()[1]-1))))) {
			this.setVerticalAcceleration(this.normalVerticalAcceleration);
		}
		this.setVerticalVelocity(this.getVerticalVelocity() + this.normalVerticalAcceleration*dt);
		double newPositionY = this.getVerticalVelocity() * dt 
				- this.getVerticalAcceleration() * Math.pow(dt, 2)
				+ this.getVerticalAcceleration() * Math.pow(dt, 2)/2;
		return newPositionY;
	}
	
	public void advanceTime(double dt) throws IllegalArgumentException {
		if (! isValidDt(dt)) 
			throw new IllegalArgumentException("The given period of time dt is invalid!");
		this.setPosition(this.getPosition()[0] + (int)(100 * this.horizontalMovement(dt)),
				this.getPosition()[1] + (int)(100 * this.verticalMovement(dt)));
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
		for (Slime slime: this.world.getSlimes()) {
			if (this.collidesWith(slime)) {
				this.setHorizontalAcceleration(0);
				this.setHorizontalVelocity(0); /*Is dit goed om beweging te blokkeren? Want in de opgave
				staat: "Properties of the ongoing movement of the colliding game, e.g. direction, velocity 
				and acceleration, may not change directly as a result of the collision."*/
				if (!(this.getSchool() == slime.getSchool())) {
					for (Slime otherSlime: this.getSchool().getSlimes()) {
						if (!(otherSlime == this)) {
							this.changeNbHitPoints(-1);
							otherSlime.changeNbHitPoints(1);
						}
					}
					this.setSchool(slime.getSchool());
					for (Slime otherSlime: slime.getSchool().getSlimes()) {
						if (!(otherSlime == this)) {
							otherSlime.changeNbHitPoints(-1);
							this.changeNbHitPoints(1);
						}
					}
				}
			}
		}
		for (Shark shark: this.world.getSharks()) {
			if ((this.collidesWith(shark)) && (!this.bottomCollidesWithTopOfObject(shark))) {
				this.setHorizontalAcceleration(0);
				this.setHorizontalVelocity(0); /*Is dit goed om beweging te blokkeren? Want in de opgave
				staat: "Properties of the ongoing movement of the colliding game, e.g. direction, velocity 
				and acceleration, may not change directly as a result of the collision."*/
				this.changeNbHitPoints(-50);
			}
		}
		if ((this.collidesWith(this.world.getMazub())) 
				&& (!this.bottomCollidesWithTopOfObject(this.world.getMazub()))) {
			this.setHorizontalAcceleration(0);
			this.setHorizontalVelocity(0);
			this.changeNbHitPoints(-50);
			for (Slime slime: this.getSchool().getSlimes()) {
				if (!(slime == this))
					slime.changeNbHitPoints(-1);
			}
		}
		if (this.isInWater())
			this.changeNbHitPoints((int)(-2 * (dt % (20))));
		if (this.isInLava())
			this.changeNbHitPoints((int)(-50 *((dt + 20) % (20))));
	}
}