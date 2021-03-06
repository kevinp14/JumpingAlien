package jumpingalien.model;

import java.util.Random;

import jumpingalien.util.Sprite;
import jumpingalien.util.Util;

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
	 * @effect	The new normal vertical velocity is set to 2.
	 * 			| setNormalVerticalVelocity(2)
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
		this.setNormalVerticalVelocity(2);
	    this.timeMovingHorizontally = 0;	
	    this.timesNotJumped = 0;
	    this.changeNbHitPoints(100);
	}
	
	/**
	 * @return	A random time between 1 and 4 seconds during which the shark has to move.
	 * 
	 */
	private double getRandomMovingTime() {
		Random rand = new Random();
		int movingTime = rand.nextInt(40);
		if (movingTime > 10) {
			return (((double)movingTime) / 10);
		}
		else {
			return (((double)(movingTime + 10)) / 10);
		}
	}
	
	/**
	 * @return	A random direction (left, right, up and left, up and right, down and left or 
	 * 			down and right) for the shark to move in.
	 * 
	 */
	private SelfMadeDirection getRandomDirection(){
		Random rand = new Random();
	    int directionNumber = rand.nextInt(6);
	    if (directionNumber == 0){
	    	return SelfMadeDirection.LEFT;
	    }
	    if (directionNumber == 1){
	    	return SelfMadeDirection.RIGHT;
	    }
	    if (directionNumber == 2){
	    	return SelfMadeDirection.UPLEFT;
	    }
	    if (directionNumber == 3){
	    	return SelfMadeDirection.UPRIGHT;
	    }
	    if (directionNumber == 4){
	    	return SelfMadeDirection.DOWNLEFT;
	    }
	    else{
	    	return SelfMadeDirection.DOWNRIGHT;
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
		if (Util.fuzzyEquals((velocity + (acceleration * dt)), 0)) {
			return 0.01;
		}
		else {
			return newDt;
		}
	}
	
	/**
	 * @param 	direction
	 * 			The direction which has to be checked.
	 * @return	True if and only if the direction is right, left, upleft, upright, downleft, downright.
	 */
	@Override
	protected boolean isValidMovingDirection(SelfMadeDirection direction) {
		return ((direction == SelfMadeDirection.RIGHT) 
				|| (direction == SelfMadeDirection.LEFT)
				|| (direction == SelfMadeDirection.UPLEFT)
				|| (direction == SelfMadeDirection.UPRIGHT)
				|| (direction == SelfMadeDirection.DOWNLEFT)
				|| (direction == SelfMadeDirection.DOWNRIGHT));
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
	 * @return	True if and only if the geological feature at the shark's tile's bottom perimeter 
	 * 			equals 2 (water).
	 */
	private boolean touchesWater() {
		return (this.getWorld().getGeologicalFeature(this.getPosition()[0], this.getPosition()[1])
				== 2);
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
	public void startMoveVertically(SelfMadeDirection direction) {
		if (direction == SelfMadeDirection.UP) {
			if (this.isSubmergedInWater()) {
				this.startRise();
				this.timesNotJumped += 1;
			}
			else if ((!this.isSubmergedInWater()) && (this.touchesWater())) {
				if (!(this.timesNotJumped < 4)) {
					this.startJump();
					this.timesNotJumped = 0;
				}
			}
		}
		if (direction == SelfMadeDirection.DOWN) {
			this.timesNotJumped += 1;
			if (this.isSubmergedInWater()) {
				this.startDive();
			}
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
	public void endMoveVertically() {
		if (this.isJumping()) {
			this.endJump();
		}
		if (this.isRising()) {
			this.endRise();
		}
		if (this.isDiving()) {
			this.endDive();
		}
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
		if (Util.fuzzyGreaterThanOrEqualTo(this.timeMovingHorizontally, movingTime)) {
			this.timeMovingHorizontally = 0;
			this.endMoveHorizontally(this.getLastDirection());
			this.endMoveVertically();
			SelfMadeDirection direction = this.getRandomDirection();
			if ((direction == SelfMadeDirection.RIGHT) || (direction == SelfMadeDirection.LEFT)) {
				this.startMoveHorizontally(direction, this.getNormalHorizontalVelocity(),
						this.getNormalHorizontalAcceleration());
				this.timesNotJumped += 1;
			}
			else if (direction == SelfMadeDirection.UPRIGHT) {
				this.startMoveHorizontally(SelfMadeDirection.RIGHT, this.getNormalHorizontalVelocity(),
						this.getNormalHorizontalAcceleration());
				this.startMoveVertically(SelfMadeDirection.UP);
			}
			else if (direction == SelfMadeDirection.UPLEFT) {
				this.startMoveHorizontally(SelfMadeDirection.LEFT, this.getNormalHorizontalVelocity(),
						this.getNormalHorizontalAcceleration());
				this.startMoveVertically(SelfMadeDirection.UP);
			}
			else if (direction == SelfMadeDirection.DOWNRIGHT) {
				this.startMoveHorizontally(SelfMadeDirection.RIGHT, this.getNormalHorizontalVelocity(),
						this.getNormalHorizontalAcceleration());
				this.startMoveVertically(SelfMadeDirection.DOWN);
			}
			else if (direction == SelfMadeDirection.DOWNLEFT) {
				this.startMoveHorizontally(SelfMadeDirection.LEFT, this.getNormalHorizontalVelocity(),
						this.getNormalHorizontalAcceleration());
				this.startMoveVertically(SelfMadeDirection.DOWN);
			}
		}
		if (!Util.fuzzyGreaterThanOrEqualTo(this.timeMovingHorizontally, movingTime)) {
			this.timeMovingHorizontally += dt;
		}
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
		if (Util.fuzzyGreaterThanOrEqualTo(Math.abs(this.getHorizontalVelocity()),
				this.getMaxHorizontalVelocity())) {
			this.setHorizontalAcceleration(0);
			if (!Util.fuzzyGreaterThanOrEqualTo(this.getHorizontalVelocity(), 0)) {
				this.setHorizontalVelocity(-this.getMaxHorizontalVelocity());
			} 
			else {
				this.setHorizontalVelocity(this.getMaxHorizontalVelocity());
			}
		}
		this.setHorizontalVelocity(this.getHorizontalVelocity() + this.getHorizontalAcceleration() * dt);
		double newPositionX = this.getHorizontalVelocity() * dt 
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
				+ this.getVerticalAcceleration() * Math.pow(dt, 2) / 2;
		return newPositionY;
	}
	
	/**
	 * The actions the shark has to take when trying to cross an impassable tile.
	 * 
	 * @param	oldPosition
	 * 			The old position of the shark.
	 * @effect	If the shark is trying to cross an impassable tile to the left or right its horizontal
	 * 			acceleration and velocity are set to 0 and its position is set back to its old 
	 * 			position.
	 * 			| if ((this.crossImpassableLeft()) || (this.crossImpassableRight())) 
	 * 			|	this.setHorizontalAcceleration(0)
	 * 			|	this.setHorizontalVelocity(0)
	 * 			|	this.setPosition(oldPosition[0], oldPosition[1])
	 * @effect	If the shark is trying to cross an impassable tile to the bottom its vertical
	 * 			acceleration and velocity are set to 0 and its position is set back to its old 
	 * 			position.
	 * 			| if (this.crossImpassableBottom()) 
	 * 			|	this.setVerticalAcceleration(0)
	 * 			|	this.setVerticalVelocity(0)
	 * 			|	this.setPosition(oldPosition[0], oldPosition[1])
	 * @effect	If the shark is trying to cross an impassable tile to the top its vertical
	 * 			acceleration is set to the normal vertical acceleration and its velocity is set to 0,
	 * 			and its position is set back to its old position.
	 * 			| if ((this.crossImpassableBottom()) && (!this.isSubmergedInWater())) 
	 * 			|	this.setVerticalAcceleration(this.getNormalVerticalAcceleration())
	 * 			|	this.setVerticalVelocity(0)
	 * 			|	this.setPosition(oldPosition[0], oldPosition[1])
	 */
	@Override
	protected void crossImpassableActions(int[] oldPosition) {
		if ((this.crossImpassableLeft()) || (this.crossImpassableRight())) {
			this.setHorizontalAcceleration(0);
			this.setHorizontalVelocity(0);
			this.setPosition(oldPosition[0], oldPosition[1]);
		}
		if (this.crossImpassableBottom()) {
			this.setVerticalAcceleration(0);
			this.setVerticalVelocity(0);
			this.setPosition(oldPosition[0], oldPosition[1]);
		}
		if ((this.crossImpassableTop()) && (!this.isSubmergedInWater())) {
			this.setVerticalAcceleration(this.getNormalVerticalAcceleration());
			this.setVerticalVelocity(0);
			this.setPosition(oldPosition[0], oldPosition[1]);
		}
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
	 * @effect	If the shark collides with buzam, its movement is blocked if it is trying to move in 
	 * 			the direction in which it collided, and it loses 50 hitpoints if it didn't fall on 
	 * 			top of bazum.
	 * 			| if (this.collidesWith(bazum)) 
	 * 			|	this.collisionBlockMovement(bazum, oldPosition, newDt)
	 * 			|	if ((!this.bottomCollidesWith(bazum)) && (!this.isImmune()))
	 * 			|		this.changeNbHitPoints(-50)
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
				else {
					if (Util.fuzzyLessThanOrEqualTo(this.getTimeImmune(), 0.60)) {
						this.setTimeImmune(this.getTimeImmune() + newDt);
					}
					else {
						this.makeVulnerable();
						this.setTimeImmune(0);
					}
				}
			}
		}
		Buzam buzam = this.getWorld().getBuzam();
		if (this.collidesWith(buzam)) {
			this.collisionBlockMovement(buzam, oldPosition, newDt);
			if (!this.bottomCollidesWith(buzam)) {
				if (!this.isImmune()) {
					this.changeNbHitPoints(-50);
					this.makeImmune();
				}
				else {
					if (Util.fuzzyLessThanOrEqualTo(this.getTimeImmune(), 0.60)) {
						this.setTimeImmune(this.getTimeImmune() + newDt);
					}
					else {
						this.makeVulnerable();
						this.setTimeImmune(0);
					}
				}
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
				else {
					if (Util.fuzzyLessThanOrEqualTo(this.getTimeImmune(), 0.60)) {
						this.setTimeImmune(this.getTimeImmune() + newDt);
					}
					else {
						this.makeVulnerable();
						this.setTimeImmune(0);
					}
				}
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
			if (Util.fuzzyGreaterThanOrEqualTo(this.getTimeInAir(), 0.2)) {
				this.changeNbHitPoints(-6);
				this.setTimeInAir(0);
			}
			else {
				this.setTimeInAir(this.getTimeInAir() + newDt);
			}
		}
		else if (this.isInMagma()) {
			if (Util.fuzzyGreaterThanOrEqualTo(this.getTimeInMagma(), 0.2)) {
				this.changeNbHitPoints(-50);
				this.setTimeInMagma(0);
			}
			else {
				this.setTimeInMagma(this.getTimeInMagma() + newDt);
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
		if (this.getProgram() == null){
			if (!this.isValidDt(dt)) {
				throw new IllegalArgumentException("The given period of time dt is invalid!");
			}
			double sumDt = 0;
			double movingTime = this.getRandomMovingTime();
			while (!Util.fuzzyGreaterThanOrEqualTo(sumDt, dt)) {
				double newDt = this.getNewDt(dt);
				int[] oldPosition = this.getPosition();
				double[] oldPositionAsDouble = this.getPositionAsDouble();
				if ((this.crossImpassableBottom()) || (this.crossImpassableLeft()) 
						|| (this.crossImpassableTop()) || (this.crossImpassableRight()))  {
					this.crossImpassableActions(oldPosition);
				}
				this.collidesWithActions(newDt, oldPosition);
				if ((this.isInAir()) || (this.isInMagma())) {
					this.isInFluidActions(newDt);
				}
				this.move(movingTime, newDt);
				if ((this.isRising()) && (!this.isSubmergedInWater()) && (this.timesNotJumped < 4)) {
					this.startJump();
				}
				if ((!this.crossImpassableBottom()) && (!this.crossImpassableLeft())
						&& (!((this.crossImpassableTop()) && (!this.isSubmergedInWater()))) 
						&& (!this.crossImpassableRight())) {
					if (!this.isSubmergedInWater()) {
						if (!this.touchImpassableBottom()) {
							this.setVerticalAcceleration(this.getNormalVerticalAcceleration());
						}
					}
					this.setPosition(oldPositionAsDouble[0] + 100*this.horizontalMovement(newDt),
						oldPositionAsDouble[1] + 100*this.verticalMovement(newDt));
				}
				sumDt += newDt;
			}
		}
		if (this.programRunning){
			if (!this.isValidDt(dt)) {
				throw new IllegalArgumentException("The given period of time dt is invalid!");
			}
			double sumDt = 0;
			while (!Util.fuzzyGreaterThanOrEqualTo(sumDt, dt)) {
				double newDt = this.getNewDt(dt);
				int[] oldPosition = this.getPosition();
				double[] oldPositionAsDouble = this.getPositionAsDouble();
				if ((this.crossImpassableBottom()) || (this.crossImpassableLeft()) 
						|| (this.crossImpassableTop()) || (this.crossImpassableRight()))  {
					this.crossImpassableActions(oldPosition);
				}
				this.collidesWithActions(newDt, oldPosition);
				if ((this.isInAir()) || (this.isInMagma())) {
					this.isInFluidActions(newDt);
				}
				if ((this.isRising()) && (!this.isSubmergedInWater()) && (this.timesNotJumped < 4)) {
					this.startJump();
				}
				if ((!this.crossImpassableBottom()) && (!this.crossImpassableLeft())
						&& (!this.crossImpassableTop()) && (!this.crossImpassableRight())) {
					this.setPosition(oldPositionAsDouble[0] + 100 * this.horizontalMovement(newDt),
							oldPositionAsDouble[1] + 100 * this.verticalMovement(newDt));
				}
				sumDt += newDt;
			}
		}
		else{
			Thread t = new Thread(this.getProgram());
			t.start();
			this.programRunning =true;
		}
	}
}
