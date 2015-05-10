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
 * @invar //TODO
 * @author	Kevin Peeters (Tweede fase ingenieurswetenschappen)
 * 			Jasper Mariën (Tweede fase ingenieurswetenschappen)
 * @version 5.0
 *
 */ //TODO: meeste @post veranderen in @effect en @invar bekijken
public class Shark extends GameObject {
	private double normalVerticalVelocity;
	private double timeMovingHorizontally;
	private double timesNotJumped;
	
	/**
	 * Initialize the shark at the given position in x- and y-direction with the given list of
	 * sprites. Also sets the time moving horizontally and the times not jumped to 0.
	 * 
	 * @param 	positionX
	 * 			The position in the x-direction where the shark should be.
	 * @param 	positionY
	 * 			The position in the y-direction where the shark should be.
	 * @param 	spriteList
	 * 			The list of sprites displaying how the shark should look depending on its behavior.
	 */
	public Shark(int positionX, int positionY, Sprite[] spriteList) {
		super(positionX, positionY, spriteList);
		this.setNormalHorizontalVelocity(0);
		this.setNormalHorizontalAcceleration(1.5);
		this.setMaxHorizontalVelocity(4);
		this.normalVerticalVelocity = 2;
		this.setLastDirection(this.getRandomDirection());
	    this.timeMovingHorizontally = 0;	
	    this.timesNotJumped = 0;
	    this.changeNbHitPoints(100);
	}
	
	/**
	 * @return	A random time between 1 and 4 seconds during which the shark has to move.
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
	 * @return A random direction (left, right, up and left, up and right, down and left or 
	 * down and right) for the shark to move in.
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
	 */
	private double getRandomDivingAcceleration() {
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
	private double getNewDt(double dt){
		double velocity = Math.pow((Math.pow(this.getHorizontalVelocity(), 2) + 
				Math.pow(this.getVerticalVelocity(),2)), 1/2);
		double acceleration = Math.pow((Math.pow(this.getHorizontalAcceleration(), 2) + 
				Math.pow(this.getVerticalAcceleration(),2)), 1/2);
		double newDt = 0.01 / (velocity + acceleration*dt);
		if ((velocity + acceleration*dt) == 0)
			return 0.01;
		else {
			return newDt;
		}
	}
	
	/**
	 * Check whether the shark is jumping or not.
	 * 
	 * @return	True if and only if the shark's vertical velocity is bigger than 0 and its vertical
	 * 			acceleration is -10.
	 */
	private boolean isJumping() {
		return ((this.getVerticalVelocity() > 0) && (this.getVerticalAcceleration() == -10));
	}
	
	/**
	 * Check whether the shark is rising (in water) or not.
	 * 
	 * @return	True if and only if the shark's vertical velocity is bigger than 0 and its vertical
	 * 			acceleration is not -10.
	 */
	private boolean isRising() {
		return ((this.getVerticalVelocity() > 0) && (!(this.getVerticalAcceleration() == -10)));
	}
	
	/**
	 * Check whether the shark is rising (in water) or not.
	 * 
	 * @return	True if and only if the shark's vertical velocity is smaller than 0 and its vertical
	 * 			acceleration is not -10.
	 */
	private boolean isDiving() {
		return ((this.getVerticalVelocity() < 0) && (!(this.getVerticalAcceleration() == -10)));
	}
	
	/**
	 * Check whether the shark is completely submerged in water or not.
	 * 
	 * @return	True if and only if the geological feature at the shark's tile's top perimeter 
	 * 			equals 2 (water).
	 */
	private boolean isSubmergedInWater() {
		return (this.getWorld().getGeologicalFeature(this.getPosition()[0], (this.getPosition()[1] 
				+ this.getCurrentSprite().getHeight() - 1)) == 2);
	}
	
	/**
	 * Make the shark begin to move horizontally.
	 * 
	 * @param movingTime
	 * @param dt
	 */
	private void startMoveHorizontally(double movingTime, double dt) {
		if (this.timeMovingHorizontally >= movingTime) {
			this.timeMovingHorizontally = 0;
			Direction direction = this.getRandomDirection();
			if ((direction == Direction.RIGHT) || (direction == Direction.LEFT)) {
				this.endMoveHorizontally(this.getLastDirection());
				this.endMoveVertically();
				this.startMoveHorizontally(direction, this.getNormalHorizontalVelocity(),
						this.getNormalHorizontalAcceleration());
				this.timesNotJumped += 1;
			}
			else if (direction == Direction.UPRIGHT) {
				this.endMoveHorizontally(this.getLastDirection());
				this.endMoveVertically();
				this.startMoveHorizontally(Direction.RIGHT, this.getNormalHorizontalVelocity(),
						this.getNormalHorizontalAcceleration());
				this.startMoveVertically(Direction.UP);
			}
			else if (direction == Direction.UPLEFT) {
				this.endMoveHorizontally(this.getLastDirection());
				this.endMoveVertically();
				this.startMoveHorizontally(Direction.LEFT, this.getNormalHorizontalVelocity(),
						this.getNormalHorizontalAcceleration());
				this.startMoveVertically(Direction.UP);
			}
			else if (direction == Direction.DOWNRIGHT) {
				this.endMoveHorizontally(this.getLastDirection());
				this.endMoveVertically();
				this.startMoveHorizontally(Direction.RIGHT, this.getNormalHorizontalVelocity(),
						this.getNormalHorizontalAcceleration());
				this.startMoveVertically(Direction.DOWN);
			}
			else if (direction == Direction.DOWNLEFT) {
				this.endMoveVertically();
				this.startMoveHorizontally(Direction.LEFT, this.getNormalHorizontalVelocity(),
						this.getNormalHorizontalAcceleration());
				this.startMoveVertically(Direction.DOWN);
			}
		}
		if (this.timeMovingHorizontally < movingTime)
			this.timeMovingHorizontally += dt;
	}
	
	/**
	 * Make the shark begin to jump (move in the positive y-direction out of the water).
	 */
	private void startJump() {
		if (!this.isFalling()) {
			this.setVerticalVelocity(this.normalVerticalVelocity);
			this.setVerticalAcceleration(this.getNormalVerticalAcceleration());
		}
	}
	
	/**
	 * End the jumping of the shark.
	 */
	private void endJump() {
		if (this.getVerticalVelocity() > 0) {
			this.setVerticalVelocity(0);
		}
	}
	
	/**
	 * Make the shark begin to dive (move in the negative y-direction while in of the water).
	 */
	private void startDive() {
		this.setVerticalAcceleration(-this.getRandomDivingAcceleration());
	}
	
	/**
	 * End the diving of the shark.
	 */
	private void endDive() {
		this.setVerticalAcceleration(0);
	}
	
	/**
	 * Make the shark begin to rise (move in the positive y-direction while in of the water).
	 */
	private void startRise() {
		this.setVerticalAcceleration(this.getRandomDivingAcceleration());
	}
	
	/**
	 * End the rising of the shark.
	 */
	private void endRise() {
		this.setVerticalAcceleration(0);
	}
	
	/**
	 * Make the alien vertically by making him jump, dive or rise.
	 * 
	 * @param 	direction
	 * 			The vertical direction in which the alien has to move.
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
	 * End the vertical movement of the alien.
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
	 * Return the new x-position in the game world of the shark after it moved horizontally. Also limit
	 * the shark's velocity to the maximum and stop the shark from moving if the tile to which it wants 
	 * to move is not passable.
	 * @param	dt
	 * 			The time passed dt.
	 * @post	If the horizontal velocity is bigger than or equal to the maximum, the new horizontal 
	 * 			acceleration is set to 0 and the velocity to the maximum in the positive of negative 
	 * 			direction, depending on the direction the alien was going in.
	 * 			| if (Math.abs(this.getHorizontalVelocity()) >= this.getMaxHorizontalVelocity())
	 * 			|	this.setHorizontalAcceleration(0)
	 * 			|	if (this.getHorizontalVelocity() < 0)
	 * 			|		(new this).setHorizontalVelocity(-this.getMaxHorizontalVelocity())
	 * 			|	else
	 * 			|		(new this).setHorizontalVelocity(this.getMaxHorizontalVelocity())
	 * @post	If the shark's x-position is the smaller than or equal to the minimum and the shark is 
	 * 			trying to move in the negative x-direction, the new horizontal velocity and acceleration 
	 * 			are set to 0 and the new x-position is set to the minimum.
	 * 			| if ((this.getPosition()[0] <= 0) && (this.getHorizontalVelocity() < 0)) 
	 * 			|	(new this).setHorizontalAcceleration(0) 
	 * 			|	(new this).setHorizontalVelocity(0)
	 * @post	If the shark's x-position is the bigger than or equal to the maximum and the shark is 
	 * 			trying to move in the positive x-direction, the horizontal velocity and acceleration are
	 * 			set to 0 and the x-position is set to the maximum.
	 * 			| if ((this.getPosition()[0] >= (this.maxPositionX)) && 
	 * 			|		(this.getHorizontalVelocity() > 0)) 
	 * 			|	(new this).setHorizontalAcceleration(0) 
	 * 			|	(new this).setHorizontalVelocity(0)
	 * @post	If the shark's horizontal velocity is smaller than 0 and the tile to the left of the
	 * 			shark is not passable, the new horizontal acceleration and velocity are set to 0.
	 * 			| 		if ((this.world.isNotPassable(this.world.getGeologicalFeature(
	 * 			|				this.getPosition()[0], this.getPosition()[1])))	
	 * 			|				&& (this.getHorizontalVelocity() < 0)) 
	 * 			|			(new this).setHorizontalAcceleration(0)
	 * 			|			(new this).setHorizontalVelocity(0)
	 * @post	If the shark's horizontal velocity is bigger than 0 and the tile to the right of the
	 * 			shark is not passable, the new horizontal acceleration and velocity are set to 0.
	 * 			| 		if ((this.world.isNotPassable(this.world.getGeologicalFeature(
	 * 			|				this.getPosition()[0] + this.getCurrentSprite().getWidth(), 
	 * 			|				this.getPosition()[1]))) && (this.getHorizontalVelocity() > 0)) 
	 * 			|			(new this).setHorizontalAcceleration(0)
	 * 			|			(new this).setHorizontalVelocity(0)
	 * @post	If the shark is already moving horizontally for more than a random moving time, the 
	 * 			ongoing movements are ended and it starts to move in a random direction horizontally
	 * 			as well as vertically (if the shark is said to jump, it only jumps if it has not jumped
	 * 			for the last 4 times).
	 * 			| if (this.isMovingHorizontally())
	 * 			|	int movingTime = this.getRandomMovingTime()
	 * 			|	if (!(this.timeMovingHorizontally <= movingTime)) 
	 * 			|		Direction direction = this.getRandomDirection()
	 * 			|		if ((direction == Direction.RIGHT) || (direction == Direction.LEFT))
	 * 			|			this.endMoveHorizontally(this.getLastDirection())
	 * 			|			this.endMoveVertically()
	 * 			|			this.startMoveHorizontally(direction)
	 * 			|			this.timesNotJumped += 1
	 * 			|		if (direction == Direction.UPRIGHT)
	 * 			|			this.endMoveHorizontally(this.getLastDirection())
	 * 			|			this.endMoveVertically()
	 * 			|			this.startMoveHorizontally(Direction.RIGHT)
	 * 			|			this.startMoveVertically(Direction.UP)
	 * 			|		if (direction == Direction.UPLEFT)
	 * 			|			this.endMoveHorizontally(this.getLastDirection())
	 * 			|			this.endMoveVertically()
	 * 			|			this.startMoveHorizontally(Direction.LEFT)
	 * 			|			this.startMoveVertically(Direction.UP)
	 * 			|		if (direction == Direction.DOWNRIGHT)
	 * 			|			this.endMoveHorizontally(this.getLastDirection())
	 * 			|			this.endMoveVertically()
	 * 			|			this.startMoveHorizontally(Direction.RIGHT)
	 * 			|			this.startMoveVertically(Direction.DOWN)
	 * 			|		if (direction == Direction.DOWNLEFT)
	 * 			|			this.endMoveHorizontally(this.getLastDirection())
	 * 			|			this.endMoveVertically()
	 * 			|			this.startMoveHorizontally(Direction.LEFT)
	 * 			|			this.startMoveVertically(Direction.DOWN)
	 * @post	If the shark is not yet moving horizontally, it starts to move in a random direction 
	 * 			horizontally as well as vertically.
	 * 			| if (this.isMovingHorizontally())
	 * 			|	int movingTime = this.getRandomMovingTime()
	 * 			|	if (!(this.timeMovingHorizontally <= movingTime)) 
	 * 			|		Direction direction = this.getRandomDirection()
	 * 			|		if ((direction == Direction.RIGHT) || (direction == Direction.LEFT))
	 * 			|			this.startMoveHorizontally(direction)
	 * 			|			this.timesNotJumped += 1
	 * 			|		if (direction == Direction.UPRIGHT)
	 * 			|			this.startMoveHorizontally(Direction.RIGHT)
	 * 			|			this.startMoveVertically(Direction.UP)
	 * 			|		if (direction == Direction.UPLEFT)
	 * 			|			this.startMoveHorizontally(Direction.LEFT)
	 * 			|			this.startMoveVertically(Direction.UP)
	 * 			|		if (direction == Direction.DOWNRIGHT)
	 * 			|			this.startMoveHorizontally(Direction.RIGHT)
	 * 			|			this.startMoveVertically(Direction.DOWN)
	 * 			|		if (direction == Direction.DOWNLEFT)
	 * 			|			this.startMoveHorizontally(Direction.LEFT)
	 * 			|			this.startMoveVertically(Direction.DOWN)
	 * @post	The new horizontal velocity is set to the sum of the current horizontal velocity and the 
	 * 			product of the current horizontal acceleration and dt.
	 * 			| (new this).setHorizontalVelocity(this.getHorizontalVelocity() + 
	 * 			|	this.getHorizontalAcceleration()*dt)
	 * @return	newPositionX
	 * 			The shark's new x-position after horizontal movement.
	 * 			| newPositionX = this.getHorizontalVelocity() * dt 
	 * 			|	- this.getHorizontalAcceleration() * Math.pow(dt, 2)
	 * 			|	+ this.getHorizontalAcceleration() * Math.pow(dt, 2) / 2
	 * @throws	IllegalArgumentException
	 * 			| !isValidDt(dt)
	 */
	private double horizontalMovement(double dt) throws IllegalArgumentException {
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
	 * Return the new y-position in the game world of the shark after it moved vertically. Also
	 * stop the shark from moving if the tile to which he wants to move is not passable.
	 * @param	dt
	 * 			The time passed dt.
	 * @post	If the given y-position is the bigger than or equal to the maximum and the shark is 
	 * 			trying to move in the positive y-direction, the vertical velocity is set to 0 and the 
	 * 			acceleration is set to -10.
	 * 			| if ((this.getPosition()[1] >= this.maxPositionY) && 
	 * 			|		(this.getVerticalVelocity() > 0))
	 * 			|	(new this).setVerticalVelocity(0)
	 * @post 	If the shark's vertical velocity is smaller than 0 and the tile beneath the shark is not
	 * 			passable, the shark's new vertical acceleration and velocity are set to 0.
	 * 			| 		if ((this.getVerticalVelocity() < 0) && (this.world.isNotPassable(
	 * 			|				this.world.getGeologicalFeature((int)this.getPosition()[0], 
	 * 			|				(int)this.getPosition()[1]))))
	 * 			|			this.setVerticalAcceleration(0)
	 * 			|			this.setVerticalVelocity(0)
	 * @post 	If the shark's vertical velocity is bigger than 0 and the tile above the shark is not
	 * 			passable, the shark's new vertical velocity is set to 0.
	 * 			| 		if ((this.getVerticalVelocity() > 0) && (this.world.isNotPassable(
	 * 			|				this.world.getGeologicalFeature((int)this.getPosition()[0], 
	 * 			|				(int)this.getPosition()[1] + this.getCurrentSprite().getHeight()))))
	 * 			|			this.setVerticalVelocity(0)
	 * @post	If the shark is falling and is completely submerged in water, it's vertical acceleration
	 * 			and velocity are set to 0.
	 * 			| if ((this.isFalling()) && (this.isSubmergedInWater()))
	 * 			|	this.setVerticalAcceleration(0)
	 * 			|	this.setVerticalVelocity(0)
	 * @post	The new vertical velocity is set to the sum of the current vertical velocity and the 
	 * 			product of the current vertical acceleration and dt.
	 * 			| (new this).setVerticalVelocity(this.getVerticalVelocity() + 
	 * 			|	this.getVerticalAcceleration()*dt)
	 * @return	newPositionY
	 * 			The shark's new y-position after vertical movement.
	 * 			| newPositionY = this.getVerticalVelocity() * dt 
	 * 			|	- this.getVerticalAcceleration() * Math.pow(dt, 2)
	 * 			|	+ this.getVerticalAcceleration() * Math.pow(dt, 2)/2;
	 * @throws	IllegalArgumentException
	 * 			| !isValidDt(dt)
	 */
	private double verticalMovement(double dt) throws IllegalArgumentException {
		this.setVerticalVelocity(this.getVerticalVelocity() + this.getVerticalAcceleration()*dt);
		double newPositionY = this.getVerticalVelocity() * dt 
				- this.getVerticalAcceleration() * Math.pow(dt, 2)
				+ this.getVerticalAcceleration() * Math.pow(dt, 2) / 2;
		return newPositionY;
	}
	
	/**
	 * The actions the shark has to take when colliding with another game object.
	 * 
	 * @param newDt
	 * @param oldPosition
	 */
	private void collidesWithActions(double newDt, int[] oldPosition) {
		if ((this.collidesWith(this.getWorld().getMazub())) 
				&& (!this.bottomCollidesWithTop(this.getWorld().getMazub()))) {
			this.setHorizontalAcceleration(0);
			this.setHorizontalVelocity(0);
			this.setPosition(oldPosition[0], oldPosition[1]);
			if (!this.isImmune()) {
				this.changeNbHitPoints(-50);
				this.makeImmune();
			}
			else
				this.setTimeImmune(this.getTimeImmune() + newDt);
		}
		for (Shark shark: this.getWorld().getSharks()) {
			if ((this.collidesWith(shark)) && (!this.bottomCollidesWithTop(shark))) {
				this.setHorizontalAcceleration(0);
				this.setHorizontalVelocity(0); 
				this.setPosition(oldPosition[0], oldPosition[1]);
				/*Is dit goed om beweging te blokkeren? Want in de opgave
				staat: "Properties of the ongoing movement of the colliding game, e.g. direction, velocity 
				and acceleration, may not change directly as a result of the collision."*/
			}
		}
		for (Slime slime: this.getWorld().getSlimes()) {
			if ((this.collidesWith(slime)) && (!this.bottomCollidesWithTop(slime))) {
				this.setHorizontalAcceleration(0);
				this.setHorizontalVelocity(0); 
				this.setPosition(oldPosition[0], oldPosition[1]);
				/*Is dit goed om beweging te blokkeren? Want in de opgave
				staat: "Properties of the ongoing movement of the colliding game, e.g. direction, velocity 
				and acceleration, may not change directly as a result of the collision."*/
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
	 * The actions the shark has to take when in a fluid.
	 * 
	 * @param newDt
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
	 * Set the new position on the screen of the shark to new position in the game world, limit the
	 * shark's position to the minimum (in x-direction) and the maximum (both in x- and y-direction),
	 * block the shark's movement if it wants to move to a tile that is not passable, adapt the time
	 * moving horizontally, block the shark's movement, decrease its hitpoints and make it immune
	 * if it collides with an enemy (only when it does not collide with its bottom perimeter), block the
	 * shark's movement if it collides with another shark, decrease the number of hitpoints if the 
	 * shark is in water or magma, and make it immune for magma if it falls into magma.
	 * @param 	dt
	 * 			The time passed dt.
	 * @effect //TODO
	 * @throws	IllegalArgumentException
	 * 			| !isValidDt(dt)
	 */
	public void advanceTime(double dt) throws IllegalArgumentException {
		double sumDt = 0;
		int movingTime = this.getRandomMovingTime();
		while (sumDt < dt) {
			double newDt = this.getNewDt(dt);
			int[] oldPosition = this.getPosition();
			if (!this.isValidDt(newDt))
				throw new IllegalArgumentException("The given period of time dt is invalid!");
			if ((this.crossImpassableBottom()) || (this.crossImpassableLeft()) 
					|| (this.crossImpassableTop()) || (this.crossImpassableRight()))  {
				this.crossImpassableActions(oldPosition);
			}
			this.collidesWithActions(newDt, oldPosition);
			if ((this.isInAir()) || (this.isInMagma()))
				this.isInFluidActions(newDt);
			this.startMoveHorizontally(movingTime, newDt);
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