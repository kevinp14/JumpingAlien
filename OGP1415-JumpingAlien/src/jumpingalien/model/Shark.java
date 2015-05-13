package jumpingalien.model;

import java.util.Random;

import jumpingalien.util.Sprite;

/**
 * A class of sharks involving the normal and maximum for the horizontal velocity, the time the shark 
 * is moving horizontally and the times it moved horizontally without jumping, the number of hitpoints, 
 * a method to create a random moving time and direction, methods to inspect the shark's behaviour, 
 * methods to move vertically and a method to advance time and adapt the time depending characteristics 
 * based on the period of time passed.
 * 
 * @invar
 * @author	Kevin Peeters (Tweede fase ingenieurswetenschappen)
 * 			Jasper Mariën (Tweede fase ingenieurswetenschappen)
 * @version 9.0
 *
 */
public class Shark extends GameObject {
	private double normalVerticalVelocity;
	private double timeMovingHorizontally;
	private double timesNotJumped;
	
	/**
	 * @param 	positionX
	 * 			The position in the x-direction where the shark should be.
	 * @param 	positionY
	 * 			The position in the y-direction where the shark should be.
	 * @param 	spriteList
	 * 			The list of sprites displaying how the shark should look depending on its behavior.
	 * @effect	The new last direction is set to a random direction.
	 * 			| this.setLastDirection(this.getRandomDirection())
	 * @effect	The new normal horizontal velocity is set to 0.
	 * 			| this.setNormalHorizontalVelocity(0)
	 * @effect	The new normal horizontal acceleration is set to 1.5.
	 * 			| this.setNormalHorizontalAcceleration(1.5)
	 * @effect	The new maximum horizontal velocity is set to 4.
	 * 			| this.setMaxHorizontalVelocity(4)
	 * @post	The new normal vertical velocity is set to 2.
	 * 			| (new this).normalVerticalVelocity = 2
	 * @post	The new time moving horizontally is set to 0.
	 * 			| (new this).timeMovingHorizontally = 0
	 * @post	The new times not jumped are set to 0.
	 * 			| (new this).timesNotJumped = 0
	 * @effect	The number of hitpoints is increased with 100.
	 * 			| this.changeNbHitPoints(100)
	 */
	public Shark(int positionX, int positionY, Sprite[] spriteList, Program program) {
		super(positionX, positionY, spriteList, program);
		this.setLastDirection(this.getRandomDirection());
		this.setNormalHorizontalVelocity(0);
		this.setNormalHorizontalAcceleration(1.5);
		this.setMaxHorizontalVelocity(4);
		this.normalVerticalVelocity = 2;
	    this.timeMovingHorizontally = 0;	
	    this.timesNotJumped = 0;
	    this.changeNbHitPoints(100);
	}
	
	/**
	 * @return	A random time between 1 and 4 seconds during which the shark has to move.
	 * 
	 */
	private int getRandomMovingTime() {
		Random rand = new Random();
		int movingTime = rand.nextInt(40);
		if (movingTime > 10)
			return (movingTime/10);
		else
			return ((movingTime + 10)/10);
	}
	
	/**
	 * @return	A random direction (left, right, up and left, up and right, down and left or 
	 * 			down and right) for the shark to move in.
	 * 
	 */
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
	
	/**
	 * @return A random acceleration between 0 and 0.2 for the shark to dive with.
	 * 
	 */
	private double getRandomSwimAcceleration() {
		Random rand = new Random();
		double divingAcceleration = (double)((double)(rand.nextInt(2)) / 10);
		return divingAcceleration;
	}
	
	/**
	 * @param 	dt
	 * 			The period of time dt for which the new period of time needs to be calculated.
	 * @return	The new period of time dt based on the current velocity and acceleration of the alien
	 * 			in this world. (used for accurate collision detection).
	 */
	private double getNewDt(double dt) {
		double velocity = Math.sqrt(Math.pow(this.getHorizontalVelocity(), 2) + 
				Math.pow(this.getVerticalVelocity(), 2));
		double acceleration = Math.sqrt(Math.pow(this.getHorizontalAcceleration(), 2) + 
				Math.pow(this.getVerticalAcceleration(), 2));
		double newDt = 0.01 / (velocity + (acceleration * dt));
		if ((velocity + (acceleration * dt)) == 0)
			return 0.01;
		else 
			return newDt;
	}
	
	/**
	 * @param 	direction
	 * 			The direction which has to be checked.
	 * @return	True if and only if the direction is right, left, upleft, upright, downleft, downright.
	 */
	@Override
	protected boolean isValidMovingDirection(Direction direction) {
		return ((direction == Direction.RIGHT) 
				|| (direction == Direction.LEFT)
				|| (direction == Direction.UPLEFT)
				|| (direction == Direction.UPRIGHT)
				|| (direction == Direction.DOWNLEFT)
				|| (direction == Direction.DOWNRIGHT));
	}
	
	/**
	 * @return	True if and only if the shark's vertical velocity is bigger than 0 and its vertical
	 * 			acceleration is -10.
	 */
	private boolean isJumping() {
		return ((this.getVerticalVelocity() > 0) && (this.getVerticalAcceleration() == -10));
	}
	
	/**
	 * @return	True if and only if the shark's vertical velocity is bigger than 0 and its vertical
	 * 			acceleration is not -10.
	 */
	private boolean isRising() {
		return ((this.getVerticalVelocity() > 0) && (!(this.getVerticalAcceleration() == -10)));
	}
	
	/**
	 * @return	True if and only if the shark's vertical velocity is smaller than 0 and its vertical
	 * 			acceleration is not -10.
	 */
	private boolean isDiving() {
		return ((this.getVerticalVelocity() < 0) && (!(this.getVerticalAcceleration() == -10)));
	}
	
	/**
	 * @return	True if and only if the geological feature at the shark's tile's top perimeter 
	 * 			equals 2 (water).
	 */
	private boolean isSubmergedInWater() {
		return (this.getWorld().getGeologicalFeature(this.getPosition()[0], (this.getPosition()[1] 
				+ this.getCurrentSprite().getHeight() - 1)) == 2);
	}
	
	/**
	 * @param	movingTime
	 * 			The time during which the shark has to move in a given direction.
	 * @param	dt
	 * 			The period of time with which the time is being advanced.
	 * @effect	If the shark is already moving longer than the moving time, all ongoing movements are
	 * 			ended, and it starts moving in another random direction.
	 * 			| if (this.timeMovingHorizontally >= movingTime)
	 * 			|	this.endMoveHorizontally(this.getLastDirection())
	 * 			|	this.endMoveVertically()
	 * 			|	Direction direction = this.getRandomDirection()
	 * 			|	this.startMoveHorizontally(direction, this.getNormalHorizontalVelocity(),
	 * 			|		this.getNormalHorizontalAcceleration())
	 * 			|		this.startMoveVertically(direction)
	 */
	private void move(double movingTime, double dt) {
		if (this.timeMovingHorizontally >= movingTime) {
			this.timeMovingHorizontally = 0;
			this.endMoveHorizontally(this.getLastDirection());
			this.endMoveVertically();
			Direction direction = this.getRandomDirection();
			if ((direction == Direction.RIGHT) || (direction == Direction.LEFT)) {
				this.startMoveHorizontally(direction, this.getNormalHorizontalVelocity(),
						this.getNormalHorizontalAcceleration());
				this.timesNotJumped += 1;
			}
			else if (direction == Direction.UPRIGHT) {
				this.startMoveHorizontally(Direction.RIGHT, this.getNormalHorizontalVelocity(),
						this.getNormalHorizontalAcceleration());
				this.startMoveVertically(Direction.UP);
			}
			else if (direction == Direction.UPLEFT) {
				this.startMoveHorizontally(Direction.LEFT, this.getNormalHorizontalVelocity(),
						this.getNormalHorizontalAcceleration());
				this.startMoveVertically(Direction.UP);
			}
			else if (direction == Direction.DOWNRIGHT) {
				this.startMoveHorizontally(Direction.RIGHT, this.getNormalHorizontalVelocity(),
						this.getNormalHorizontalAcceleration());
				this.startMoveVertically(Direction.DOWN);
			}
			else if (direction == Direction.DOWNLEFT) {
				this.startMoveHorizontally(Direction.LEFT, this.getNormalHorizontalVelocity(),
						this.getNormalHorizontalAcceleration());
				this.startMoveVertically(Direction.DOWN);
			}
		}
		if (this.timeMovingHorizontally < movingTime)
			this.timeMovingHorizontally += dt;
	}
	
	/**
	 * @effect	If the shark is not falling, its new vertical velocity and acceleration are set to the
	 * 			normal ones.
	 * 			| this.setVerticalVelocity(this.normalVerticalVelocity)
	 * 			| this.setVerticalAcceleration(this.getNormalVerticalAcceleration())
	 */
	private void startJump() {
		if (!this.isFalling()) {
			this.setVerticalVelocity(this.normalVerticalVelocity);
			this.setVerticalAcceleration(this.getNormalVerticalAcceleration());
		}
	}
	
	/**
	 * @effect	If the current vertical velocity is bigger than 0, the new vertical velocity is set to
	 * 			0.
	 * 			| if (this.getVerticalVelocity() > 0)
	 * 			|	this.setVerticalVelocity(0)
	 */
	private void endJump() {
		if (this.getVerticalVelocity() > 0) {
			this.setVerticalVelocity(0);
		}
	}
	
	/**
	 * @effect	The new vertical acceleration is set to a random swimming acceleration.
	 * 			| this.setVerticalAcceleration(-this.getRandomSwimAcceleration())
	 */
	private void startDive() {
		this.setVerticalAcceleration(-this.getRandomSwimAcceleration());
	}
	
	/**
	 * @effect	The new vertical acceleration is set to 0 .
	 * 			| this.setVerticalAcceleration(0)
	 */
	private void endDive() {
		this.setVerticalAcceleration(0);
	}
	
	/**
	 * @effect	The new vertical acceleration is set to a random swimming acceleration.
	 * 			| this.setVerticalAcceleration(this.getRandomSwimAcceleration())
	 */
	private void startRise() {
		this.setVerticalAcceleration(this.getRandomSwimAcceleration());
	}
	
	/**
	 * @effect	The new vertical acceleration is set to 0 .
	 * 			| this.setVerticalAcceleration(0)
	 */
	private void endRise() {
		this.setVerticalAcceleration(0);
	}
	
	/**
	 * @param 	direction
	 * 			The vertical direction in which the alien has to move.
	 * @effect	If the shark is submerged in water and the given direction is up, it starts rising.
	 * 			| if ((direction == Direction.UP) && (this.isSubmergedInWater()))
	 * 			|	this.startRise()
	 * @effect	If the shark is not submerged in water and the given direction is up, and it didn't
	 * 			jump for 4 consecutive movements, it starts jumping.
	 * 			| if ((direction == Direction.UP) && (!this.isSubmergedInWater()))
	 * 			|	this.startJump()
	 * @effect	If the shark is submerged in water and the given direction is down, it starts diving.
	 * 			| if ((direction == Direction.DOWN) && (this.isSubmergedInWater()))
	 * 			|	this.startDive()
	 */
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
	
	/**
	 * @effect	If the shark is jumping, its jump is ended.
	 * 			| if (this.isJumping())
	 * 			|	this.endJump()
	 * @effect	If the shark is rising, its rise is ended.
	 * 			| if (this.isRising())
	 * 			|	this.endRise()
	 * @effect	If the shark is diving, its dive is ended.
	 * 			| if (this.isDiving())
	 * 			|	this.endDive()
	 */
	private void endMoveVertically() {
		if (this.isJumping())
			this.endJump();
		if (this.isRising())
			this.endRise();
		if (this.isDiving())
			this.endDive();
	}
	
	/**
	 * @param	dt
	 * 			The time passed dt.
	 * @effect	If the horizontal velocity is bigger than or equal to the maximum, the new horizontal 
	 * 			acceleration is set to 0 and the velocity to the maximum in the positive of negative 
	 * 			direction, depending on the direction the alien was going in.
	 * 			| if (Math.abs(this.getHorizontalVelocity()) >= this.getMaxHorizontalVelocity())
	 * 			|	this.setHorizontalAcceleration(0)
	 * 			|	if (this.getHorizontalVelocity() < 0)
	 * 			|		this.setHorizontalVelocity(-this.getMaxHorizontalVelocity())
	 * 			|	else
	 * 			|		this.setHorizontalVelocity(this.getMaxHorizontalVelocity())
	 * @effect	The new horizontal velocity is set to the sum of the current horizontal velocity and the 
	 * 			product of the current horizontal acceleration and dt.
	 * 			| this.setHorizontalVelocity(this.getHorizontalVelocity() + 
	 * 			|	this.getHorizontalAcceleration() * dt)
	 * @return	newPositionX
	 * 			The shark's new x-position after horizontal movement.
	 * 			| newPositionX = this.getHorizontalVelocity() * dt 
	 * 			|	- this.getHorizontalAcceleration() * Math.pow(dt, 2)
	 * 			|	+ this.getHorizontalAcceleration() * Math.pow(dt, 2) / 2
	 */
	private double horizontalMovement(double dt) {
		if (Math.abs(this.getHorizontalVelocity()) >= this.getMaxHorizontalVelocity()) {
			this.setHorizontalAcceleration(0);
			if (this.getHorizontalVelocity() < 0) {
				this.setHorizontalVelocity(-this.getMaxHorizontalVelocity());
			} 
			else
				this.setHorizontalVelocity(this.getMaxHorizontalVelocity());
		}
		this.setHorizontalVelocity(this.getHorizontalVelocity() + this.getHorizontalAcceleration() * dt);
		double newPositionX = this.getHorizontalVelocity() * dt 
				- this.getHorizontalAcceleration() * Math.pow(dt, 2) 
				+ this.getHorizontalAcceleration() * Math.pow(dt, 2) / 2;
		return newPositionX;
	}
	
	/**
	 * @param	dt
	 * 			The time passed dt.
	 * @effect	The new vertical velocity is set to the sum of the current vertical velocity and the 
	 * 			product of the current vertical acceleration and dt.
	 * 			| this.setVerticalVelocity(this.getVerticalVelocity() + 
	 * 			|	this.getVerticalAcceleration()*dt)
	 * @return	newPositionY
	 * 			The alien's new y-position after vertical movement.
	 * 			| newPositionY = this.getVerticalVelocity() * dt 
	 * 			|	- this.getVerticalAcceleration() * Math.pow(dt, 2)
	 * 			|	+ this.getVerticalAcceleration() * Math.pow(dt, 2)/2
	 */
	private double verticalMovement(double dt) {
		this.setVerticalVelocity(this.getVerticalVelocity() + this.getVerticalAcceleration()*dt);
		double newPositionY = this.getVerticalVelocity() * dt 
				- this.getVerticalAcceleration() * Math.pow(dt, 2)
				+ this.getVerticalAcceleration() * Math.pow(dt, 2) / 2;
		return newPositionY;
	}
	
	/**
	 * @param	newDt
	 * 			The period of time on which collision has to be detected.
	 * @param	oldPosition
	 * 			The shark's old position.
	 * @effect	If the shark collides with an alien in its game world, its movement is blocked if it
	 * 			is trying to move in the direction in which it collided, and it loses 50 hitpoints if
	 * 			it didn't fall on top of the alien.
	 * 			| if (this.collidesWith(alien))
	 * 			|	this.collisionBlockMovement(alien, oldPosition, newDt)
	 * 			|	if ((!this.bottomCollidesWith(alien)) && (!this.isImmune()))
	 * 			|		this.changeNbHitPoints(-50)
	 * @effect	If the shark collides with another shark in its game world, its movement is blocked if
	 * 			it is trying to move in the direction in which it collided.
	 * 			| for (Shark shark: this.getWorld().getSharks())
	 * 			|	if ((this.collidesWith(shark)) && (!this.bottomCollidesWith(shark)))
	 * 			|		this.collisionBlockMovement(shark, oldPosition, newDt)
	 * @effect	If the shark collides with a slime in its game world, its movement is blocked if it
	 * 			is trying to move in the direction in which it collided, and it loses 50 hitpoints if
	 * 			it didn't fall on top of the slime.
	 * 			| for (Slime slime: this.getWorld().getSlimes())
	 * 			|	if (this.collidesWith(slime)) 
	 * 			|		this.collisionBlockMovement(slime, oldPosition, newDt)
	 * 			|		if ((!this.bottomCollidesWith(slime)) && (!this.isImmune()))
	 * 			|			this.changeNbHitPoints(-50)
	 */
	private void collidesWithActions(double newDt, int[] oldPosition) {
		Mazub alien = this.getWorld().getMazub();
		if (this.collidesWith(alien)) {
			this.collisionBlockMovement(alien, oldPosition, newDt);
			if (!this.bottomCollidesWith(alien)) {
				if (!this.isImmune()) {
					this.changeNbHitPoints(-50);
					this.makeImmune();
				}
				else
					this.setTimeImmune(this.getTimeImmune() + newDt);
			}
		}
		for (Shark shark: this.getWorld().getSharks()) {
			if ((this.collidesWith(shark)) && (!this.bottomCollidesWith(shark))) {
				this.collisionBlockMovement(shark, oldPosition, newDt);
			}
		}
		for (Slime slime: this.getWorld().getSlimes()) {
			if ((this.collidesWith(slime)) && (!this.bottomCollidesWith(slime))) {
				this.collisionBlockMovement(slime, oldPosition, newDt);
				if (!this.isImmune()) {
					this.changeNbHitPoints(-50);
					this.makeImmune();
				}
				else
					this.setTimeImmune(this.getTimeImmune() + newDt);
			}
		}

	}
	
	/**
	 * @param	newDt
	 * 			The period of time with which the time is advanced.
	 * @effect	If the shark is in air, its hitpoints are reduced with 6 every 0.2 seconds.
	 * 			| if ((this.isInAir()) && (this.getTimeInAir() >= 0.2)) 
	 * 			|		this.changeNbHitPoints(-6)
	 * @effect	If the shark is in magma, its hitpoints are reduced with 50 upon contact and every 
	 * 			0.2 seconds. However, the alien can not lose more than 50 hitpoints every 0.2 seconds.
	 * 			| if (this.isInMagma()) 
	 * 			|		this.changeNbHitPoints(-50)
	 */
	private void isInFluidActions(double newDt) {
		if (this.isInAir()) {
			if (this.getTimeInAir() >= 0.2) {
				this.changeNbHitPoints(-6);
				this.setTimeInAir(0);
			}
			else 
				this.setTimeInAir(this.getTimeInAir() + newDt);
		}
		else if (this.isInMagma()) {
			this.setTimeInMagma(this.getTimeInMagma() + newDt);
			if (!this.isImmuneForMagma()) {
				this.changeNbHitPoints(-50);
				this.makeImmuneForMagma();
			}
			else {
				if (this.getTimeImmuneForMagma() < 0.20) 
					this.setTimeImmuneForMagma(this.getTimeImmuneForMagma() + newDt);
				else {
					this.makeVulnerableForMagma();
					this.setTimeImmuneForMagma(0);
				}
			}
		}
		else {
			this.setTimeInAir(0);
			this.setTimeInMagma(0);
		}
	}
	
	/**
	 * @param 	dt
	 * 			The time passed dt.
	 * @effect	If the shark is trying to cross an impassable tile, take the corresponding actions.
	 * 			| if ((this.crossImpassableBottom()) || (this.crossImpassableLeft()) 
	 * 			|	|| (this.crossImpassableTop()) || (this.crossImpassableRight()))
	 * 			|		this.crossImpassableActions(oldPosition)
	 * @effect	If the shark is in a fluid, the corresponding actions are taken.
	 * 			| if ((this.isInAir()) || (this.isInMagma())) 
	 * 			|	this.isInFluidActions(newDt)
	 * @effect	If the shark is not on an impassable tile and is not submerged in water, its vertical 
	 * 			acceleration is set to the normal one.
	 * 			| if ((!this.touchImpassableBottom()) && (!this.isSubmergedInWater()))
	 * 			|	this.setVerticalAcceleration(this.getNormalVerticalAcceleration())
	 * @effect	If the alien is not trying to cross an impassable tile, its position is increased
	 * 			by 100 times the horizontal/vertical movement.
	 * 			| if ((!this.crossImpassableBottom()) && (!this.crossImpassableLeft())
	 * 			|	&& (!this.crossImpassableTop()) && (!this.crossImpassableRight()))
	 * 			|		this.setPosition(oldPosition[0] + 100*this.horizontalMovement(newDt),
	 * 			|			oldPosition[1] + 100*this.verticalMovement(newDt))
	 * @throws	IllegalArgumentException
	 * 			| !isValidDt(dt)
	 */
	public void advanceTime(double dt) throws IllegalArgumentException {
		if (!this.isValidDt(dt))
			throw new IllegalArgumentException("The given period of time dt is invalid!");
		double sumDt = 0;
		int movingTime = this.getRandomMovingTime();
		while (sumDt < dt) {
			double newDt = this.getNewDt(dt);
			int[] oldPosition = this.getPosition();
			if ((this.crossImpassableBottom()) || (this.crossImpassableLeft()) 
					|| (this.crossImpassableTop()) || (this.crossImpassableRight()))  {
				this.crossImpassableActions(oldPosition);
			}
			this.collidesWithActions(newDt, oldPosition);
			if ((this.isInAir()) || (this.isInMagma()))
				this.isInFluidActions(newDt);
			this.move(movingTime, newDt);
			if ((!this.crossImpassableBottom()) && (!this.crossImpassableLeft())
					&& (!this.crossImpassableTop()) && (!this.crossImpassableRight())) {
				if ((!this.touchImpassableBottom()) && (!this.isSubmergedInWater())) {
					this.setVerticalAcceleration(this.getNormalVerticalAcceleration());
				}
				this.setPosition(oldPosition[0] + 100*this.horizontalMovement(newDt),
					oldPosition[1] + 100*this.verticalMovement(newDt));
			}
			sumDt += newDt;
		}
	}
}