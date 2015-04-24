package jumpingalien.model;

import java.util.Random;

import be.kuleuven.cs.som.annotate.Basic;
import jumpingalien.util.Sprite;

public class Shark extends GameObject {
	private double normalHorizontalVelocity = 0;
	private double normalHorizontalAcceleration = 1.5;
	private double maxHorizontalVelocity = 4;
	private double normalVerticalVelocity = 2;
	private double timeMovingHorizontally;
	private double timesNotJumped;
	private int hitPoints = 100;
	
	public Shark(int positionX, int positionY, Sprite[] spriteList) {
		super(positionX, positionY, spriteList);
	    this.timeMovingHorizontally = 0;	
	    this.timesNotJumped = 0;
	}
	
	private int getRandomMovingTime() {
		Random rand = new Random();
		int movingTime = rand.nextInt(40);
		if (movingTime > 10)
			return movingTime;
		else
			return (movingTime + 10);
	}
	
	private Direction getRandomDirection(){
		Random rand = new Random();
	    int directionNumber = rand.nextInt(6);
	    if (directionNumber == 0){
	    	return Direction.LEFT;
	    }
	    if (directionNumber == 1){
	    	return Direction.RIGHT;
	    }
	    if (directionNumber == 2){
	    	return Direction.UPLEFT;
	    }
	    if (directionNumber == 3){
	    	return Direction.UPRIGHT;
	    }
	    if (directionNumber == 4){
	    	return Direction.DOWNLEFT;
	    }
	    else{
	    	return Direction.DOWNRIGHT;
	    }
	}
	
	private double getRandomDivingAcceleration() {
		Random rand = new Random();
		double divingAcceleration = (double)((double)(rand.nextInt(2)) / 10);
		return divingAcceleration;
	}
	
	private boolean isJumping() {
		if ((this.getVerticalVelocity() > 0) && (this.getVerticalAcceleration() == -10))
			return true;
		else
			return false;
	}
	
	private boolean isRising() {
		if ((this.getVerticalVelocity() > 0) && (!(this.getVerticalAcceleration() == -10)))
			return true;
		else
			return false;
	}
	
	private boolean isDiving() {
		if ((this.getVerticalVelocity() < 0) && (!(this.getVerticalAcceleration() == -10)))
			return true;
		else
			return false;
	}
	
	private boolean isSubmergedInWater() {
		if (this.world.getGeologicalFeature(this.getPosition()[0], (this.getPosition()[1] 
				+ this.getCurrentSprite().getHeight() - 1)) == 2)
			return true;
		else
			return false;
	}
	
	private void startJump() {
		if (! this.isFalling()) {
			this.setVerticalVelocity(this.normalVerticalVelocity);
			this.setVerticalAcceleration(this.normalVerticalAcceleration);
		}
	}
	
	private void endJump() {
		if (this.getVerticalVelocity() > 0) {
			this.setVerticalVelocity(0);
		}
	}
	
	private void startDive() {
		this.setVerticalAcceleration(-this.getRandomDivingAcceleration());
	}
	
	private void endDive() {
		this.setVerticalAcceleration(0);
	}
	
	private void startRise() {
		this.setVerticalAcceleration(this.getRandomDivingAcceleration());
	}
	
	private void endRise() {
		this.setVerticalAcceleration(0);
	}
	
	private void startMoveVertically(Direction direction) {
		if (direction == Direction.UP) {
			if (this.isSubmergedInWater()) {
				this.startRise();
				this.timesNotJumped += 1;
			}
			else {
				if (!(this.timesNotJumped < 4)) {
					this.startJump();
					this.timesNotJumped = 0;
				}
			}
		}
		if (direction == Direction.DOWN) {
			this.timesNotJumped += 1;
			if (this.isSubmergedInWater())
				this.startDive();
		}
	}
	
	private void endMoveVertically() {
		if (this.isJumping())
			this.endJump();
		if (this.isRising())
			this.endRise();
		if (this.isDiving())
			this.endDive();
	}
	
	private double horizontalMovement(double dt) throws IllegalArgumentException {
		if (! isValidDt(dt))
			throw new IllegalArgumentException("The given period of time dt is invalid!");
		if (Math.abs(this.getHorizontalVelocity()) >= this.getMaxHorizontalVelocity()) {
			this.setHorizontalAcceleration(0);
			if (this.getHorizontalVelocity() < 0) {
				this.setHorizontalVelocity(-this.getMaxHorizontalVelocity());
			} else
				this.setHorizontalVelocity(this.getMaxHorizontalVelocity());
		}
		if ((this.world.isNotPassable(this.world.getGeologicalFeature(
				this.getPosition()[0], this.getPosition()[1])))
				&& (this.getHorizontalVelocity() < 0)) {
			this.setHorizontalAcceleration(0);
			this.setHorizontalVelocity(0);
		}
		if ((this.world.isNotPassable(this.world.getGeologicalFeature(
				this.getPosition()[0] + this.getCurrentSprite().getWidth(), this.getPosition()[1]))) 
				&& (this.getHorizontalVelocity() > 0)) {
			this.setHorizontalAcceleration(0);
			this.setHorizontalVelocity(0);
		}
		if (this.isMovingHorizontally()) {
			if (!(this.timeMovingHorizontally < this.getRandomMovingTime())) {
				Direction direction = this.getRandomDirection();
				if ((direction == Direction.RIGHT) || (direction == Direction.LEFT)) {
					this.endMoveHorizontally(this.getLastDirection());
					this.endMoveVertically();
					this.startMoveHorizontally(direction);
					this.timesNotJumped += 1;
				}
				if (direction == Direction.UPRIGHT) {
					this.endMoveHorizontally(this.getLastDirection());
					this.endMoveVertically();
					this.startMoveHorizontally(Direction.RIGHT);
					this.startMoveVertically(Direction.UP);
				}
				if (direction == Direction.UPLEFT) {
					this.endMoveHorizontally(this.getLastDirection());
					this.endMoveVertically();
					this.startMoveHorizontally(Direction.LEFT);
					this.startMoveVertically(Direction.UP);
				}
				if (direction == Direction.DOWNRIGHT) {
					this.endMoveHorizontally(this.getLastDirection());
					this.endMoveVertically();
					this.startMoveHorizontally(Direction.RIGHT);
					this.startMoveVertically(Direction.DOWN);
				}
				if (direction == Direction.DOWNLEFT) {
					this.endMoveHorizontally(this.getLastDirection());
					this.endMoveVertically();
					this.startMoveHorizontally(Direction.LEFT);
					this.startMoveVertically(Direction.DOWN);
				}
			}
		}
		if (!this.isMovingHorizontally()) {
			Direction direction = this.getRandomDirection();
			if ((direction == Direction.RIGHT) || (direction == Direction.LEFT)) {
				this.endMoveHorizontally(this.getLastDirection());
				this.endMoveVertically();
				this.startMoveHorizontally(direction);
				this.timesNotJumped += 1;
			}
			if (direction == Direction.UPRIGHT) {
				this.endMoveHorizontally(this.getLastDirection());
				this.endMoveVertically();
				this.startMoveHorizontally(Direction.RIGHT);
				this.startMoveVertically(Direction.UP);
			}
			if (direction == Direction.UPLEFT) {
				this.endMoveHorizontally(this.getLastDirection());
				this.endMoveVertically();
				this.startMoveHorizontally(Direction.LEFT);
				this.startMoveVertically(Direction.UP);
			}
			if (direction == Direction.DOWNRIGHT) {
				this.endMoveHorizontally(this.getLastDirection());
				this.endMoveVertically();
				this.startMoveHorizontally(Direction.RIGHT);
				this.startMoveVertically(Direction.DOWN);
			}
			if (direction == Direction.DOWNLEFT) {
				this.endMoveHorizontally(this.getLastDirection());
				this.endMoveVertically();
				this.startMoveHorizontally(Direction.LEFT);
				this.startMoveVertically(Direction.DOWN);
			}
		}	
		this.setHorizontalVelocity(this.getHorizontalVelocity() + this.getHorizontalAcceleration() * dt);
		double newPositionX = this.getHorizontalVelocity() * dt - 
				this.getHorizontalAcceleration() * Math.pow(dt, 2) + 
				this.getHorizontalAcceleration() * Math.pow(dt, 2) / 2;
		return newPositionX;
	}
	
	private double verticalMovement(double dt) throws IllegalArgumentException {
		if (! isValidDt(dt))
			throw new IllegalArgumentException("The given period of time dt is invalid!");
		if ((this.getVerticalVelocity() < 0) && (this.world.isNotPassable(
				this.world.getGeologicalFeature((int)this.getPosition()[0], 
						(int)this.getPosition()[1])))) {
			this.setVerticalAcceleration(0);
			this.setVerticalVelocity(0);
		}
		if ((this.getVerticalVelocity() > 0) && (this.world.isNotPassable(
				this.world.getGeologicalFeature((int)this.getPosition()[0], 
				(int)this.getPosition()[1] + this.getCurrentSprite().getHeight())))) {
			this.setVerticalVelocity(0);
		}
		if ((this.isFalling()) && (this.isSubmergedInWater())) {
			this.setVerticalAcceleration(0);
			this.setVerticalVelocity(0);
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
		if ((this.world.isNotPassable(this.world.getGeologicalFeature(
				this.getPosition()[0], this.getPosition()[1])))
				&& (this.getHorizontalVelocity() < 0)) {
			this.setPosition(this.world.getBottomLeftPixelOfTile(this.getPosition()[0], 
					this.getPosition()[1])[0] + this.world.getTileLength(), this.getPosition()[1]);
		}
		if ((this.world.isNotPassable(this.world.getGeologicalFeature(
				this.getPosition()[0] + this.getCurrentSprite().getWidth(), this.getPosition()[1]))) 
				&& (this.getHorizontalVelocity() > 0)) {
			this.setPosition(this.world.getBottomLeftPixelOfTile(this.getPosition()[0], 
					this.getPosition()[1])[0] - this.world.getTileLength(), this.getPosition()[1]);
		}
		if ((this.getVerticalVelocity() < 0) && (this.world.isNotPassable(
				this.world.getGeologicalFeature((int)this.getPosition()[0], 
						(int)this.getPosition()[1])))) {
			this.setPosition(this.getPosition()[0], 
					this.world.getBottomLeftPixelOfTile((int)this.getPosition()[0], 
					(int)this.getPosition()[1])[1] + this.world.getTileLength());
		}			
		if ((this.getVerticalVelocity() > 0) && 
				(this.world.isNotPassable(this.world.getGeologicalFeature((int)this.getPosition()[0], 
				(int)this.getPosition()[1] + this.getCurrentSprite().getHeight())))) {
			this.setPosition(this.getPosition()[0], 
					this.world.getBottomLeftPixelOfTile((int)this.getPosition()[0],
					(int)this.getPosition()[1])[1] - this.world.getTileLength());
		}
		if (!(this.getHorizontalVelocity() == 0)) {
			this.timeMovingHorizontally += 1;
		}
		if ((this.collidesWith(this.world.getMazub())) 
				&& (!this.bottomCollidesWithTopOfObject(this.world.getMazub()))) {
			this.setHorizontalAcceleration(0);
			this.setHorizontalVelocity(0);
			if (!this.isImmune()) {
				this.changeNbHitPoints(-50);
				this.makeImmune();
			}
			else
				this.timeImmune += 1;
		}
		for (Shark shark: this.world.getSharks()) {
			if ((this.collidesWith(shark)) && (!this.bottomCollidesWithTopOfObject(shark))) {
				this.setHorizontalAcceleration(0);
				this.setHorizontalVelocity(0); /*Is dit goed om beweging te blokkeren? Want in de opgave
				staat: "Properties of the ongoing movement of the colliding game, e.g. direction, velocity 
				and acceleration, may not change directly as a result of the collision."*/
			}
		}
		for (Slime slime: this.world.getSlimes()) {
			if ((this.collidesWith(slime)) && (!this.bottomCollidesWithTopOfObject(slime))) {
				this.setHorizontalAcceleration(0);
				this.setHorizontalVelocity(0); /*Is dit goed om beweging te blokkeren? Want in de opgave
				staat: "Properties of the ongoing movement of the colliding game, e.g. direction, velocity 
				and acceleration, may not change directly as a result of the collision."*/
				if (!this.isImmune()) {
					this.changeNbHitPoints(-50);
					this.makeImmune();
				}
				else
					this.timeImmune += 1;
			}
		}
		if (this.isInAir())
			this.changeNbHitPoints((int)(-6 * (dt / (0.2))));
		if (this.isInLava())
			this.changeNbHitPoints((int)(-50 *((dt + 0.2) / (0.2))));
	}
}