package jumpingalien.model;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import jumpingalien.util.*;


/**
 * A class of Mazub aliens involving the horizontal and vertical velocity and acceleration 
 * of the alien and their maximum, the position of the bottom left pixel of the alien and its maximum,
 * a list of sprites or images of the alien that are displayed, the time the alien is moving or standing 
 * still, the last direction in which the alien was moving, methods to move the alien horizontally as 
 * well as vertically (jumping, falling and ducking), methods to inspect the behavior of the alien and
 * a method to change the characteristics of the alien which are time dependent.
 * 
 * 
 * @invar	
 * @author	Kevin Peeters (Tweede fase ingenieurswetenschappen)
 * 			Jasper Mariën (Tweede fase ingenieurswetenschappen)
 * @version 5.0
 *
 */
public class Mazub {
	
	private double horizontalVelocity;
	private double horizontalAcceleration;
	private double initialVelocity = 1;
	private double maxHorizontalVelocity;
	private double maxRunningVelocity = 3;
	private double maxDuckingVelocity = 1;
	private double verticalVelocity;
	private double verticalAcceleration;
	private double positionX;
	private double positionY;
	private double maxPositionX = 1023;
	private double maxPositionY = 767;
	private Sprite[] spriteList;
	private double timeStalled;
	private double timeMoving;
	private Direction lastDirection;
	private int hitPoints = 100;
	
	/**
	 * Initialize the Mazub alien at the given position in x- and y-direction with the given list of
	 * sprites. Also sets initial values to the maximum of the position, the velocity and acceleration
	 * in the horizontal as well as in the vertical direction, the maximum for the velocity, the time
	 * moving or not moving and the direction.
	 * 
	 * @param 	positionX
	 * 			The position in the x-direction where the alien should be.
	 * @param 	positionY
	  * 			The position in the y-direction where the alien should be.
	 * @param 	spriteList
	 * 			The list of sprites displaying how the alien should look depending on its behavior.
	 */
	public Mazub(double positionX, double positionY, Sprite[] spriteList) {
		int[] position = { (int)(positionX/100), (int)(positionY/100) };
		assert (isValidPosition(position));
		assert (isValidSpriteList(spriteList));
		this.setPosition(positionX, positionY);
		this.setHorizontalVelocity(0);
		this.setHorizontalAcceleration(0);
		this.setMaxHorizontalVelocity(this.maxRunningVelocity);
	    this.setVerticalVelocity(0);
	    this.setVerticalAcceleration(0);
	    this.spriteList = spriteList;
	    this.timeStalled = 0;
	    this.timeMoving = 0;
	    this.lastDirection = Direction.STALLED;
	}
	
	/**
	 * @return	An array consisting of the position of the alien in x- and y-direction.
	 * 
	 */
	@Basic
	public int[] getPosition() {
		int[] position = new int[]{ (int)this.positionX, (int)this.positionY };
		return position;
	}
	
	/**
	 * Set the position of the alien to the given position in x- an y-direction.
	 * 
	 * @param 	positionX
	 * 			The position in the x-direction of the alien.
	 * @param 	positionY
	 * 			The position in the y-direction of the alien.
	 * 
	 */
	@Basic
	private void setPosition(double positionX, double positionY) {
		this.positionX = positionX;
		this.positionY = positionY;
	}
	
	private int[] getMaxPosition() {
		int[] maxPosition = { (int)this.maxPositionX, (int)this.maxPositionY };
	    return maxPosition;
	}

	/**
	 * @return	The current sprite of the Mazub alien.
	 */
	@Basic @Immutable
	public Sprite getCurrentSprite() {
		if (this.isMovingHorizontally()) {
			if (this.isMovingLeft()) {
				if (this.isDucking())
					return spriteList[7];
				if (this.isJumping())
					return spriteList[5];
				else {
					if ((this.timeMoving % 75) <= 11)
						return spriteList[(int) (18 + (this.timeMoving % 75))];
					else
						this.timeMoving = 0;
				}
			}
			else {
				if (this.isDucking())
					return spriteList[6];
				if (this.isJumping())
					return spriteList[4];
				else {
					if ((this.timeMoving % 75) <= 11)
						return spriteList[(int) (7 + (this.timeMoving % 75))];
					else 
						this.timeMoving = 0;
				}
			}
		}
		if (this.hasJustMovedHorizontally()) {
			if (this.isMovingLeft()) {
				if (this.isDucking())
					return spriteList[7];
				else
					return spriteList[3];
			}
			else {
				if (this.isDucking())
					return spriteList[6];
				else
					return spriteList[2];
			}
		}
		else {
			if (this.isDucking())
				return spriteList[1];
			else
				return spriteList[0];
		}
	}
	
	/**
	 * @return The velocity of the alien in the x-direction.
	 *
	 */
	@Basic
	public double getHorizontalVelocity() {
		return this.horizontalVelocity;
	}
	
	/**
	 * Set the velocity of the alien in the x-direction to the given horizontal velocity.
	 * @param	horizontalVelocity
	 * 			The horizontal velocity for the alien.
	 * @post	If the given horizontal velocity is smaller than the maximum horizontal velocity, the new
	 * 			horizontal velocity is set to the given one. If the given horizontal velocity is bigger
	 * 			or equal to the maximum horizontal velocity, the new velocity is set to the maximum.
	 * 			| if (Math.abs(horizontalVelocity) < this.getMaxHorizontalVelocity())
	 * 			|	(new this).horizontalVelocity = horizontalVelocity;
	 * 			| else
	 * 			|	(new this).horizontalVelocity = this.getMaxHorizontalVelocity();
	 */
	@Basic
	private void setHorizontalVelocity(double horizontalVelocity) {
		if (Math.abs(horizontalVelocity) < this.getMaxHorizontalVelocity())
			this.horizontalVelocity = horizontalVelocity;
		else {
			if (horizontalVelocity > 0)
				this.horizontalVelocity = this.getMaxHorizontalVelocity();
			else
				this.horizontalVelocity = -(this.getMaxHorizontalVelocity());
		}
	}
	
	/**
	 * @return	The acceleration of the alien in the x-direction.
	 *
	 */
	@Basic
	public double getHorizontalAcceleration() {
		return this.horizontalAcceleration;
	}
	
	/**
	 * Set the acceleration of the alien in the x-direction to the given vertical acceleration.
	 * @param	horizontalAcceleration
	 * 			The horizontal acceleration for the alien.
	 * @post	The new horizontal acceleration is set to the given one.
	 * 			| (new this).horizontalAcceleration = horizontalAcceleration
	 */
	@Basic
	public void setHorizontalAcceleration(double horizontalAcceleration) {
		this.horizontalAcceleration = horizontalAcceleration;
	}
	
	/**
	 * @return	The maximum of the velocity of the alien in the x-direction.
	 * 
	 */
	@Basic
	public double getMaxHorizontalVelocity() {
		return this.maxHorizontalVelocity;
	}
	
	/**
	 * Set the maximum of the velocity of the alien in the x-direction to the given maximum for the 
	 * horizontal velocity.
	 * @param	maxHorizontalVelocity
	 * 			The maximum for the horizontal velocity the alien can have.
	 * @post	The new maximum horizontal velocity is set to the given one.
	 * 			| (new this).maxHorizontalVelocity = maxHorizontalVelocity
	 */
	@Basic
	private void setMaxHorizontalVelocity(double maxHorizontalVelocity) {
		this.maxHorizontalVelocity = maxHorizontalVelocity;
	}
	
	/**
	 * @return	The maximum of the velocity of the alien while ducking in the x-direction.
	 * 
	 */
	@Basic
	@Immutable
	public double getMaxDuckingVelocity() {
		return this.maxDuckingVelocity;
	}
	
	/**
	 * @return	The velocity of the alien in the y-direction.
	 * 
	 */
	@Basic
	public double getVerticalVelocity() {
		return this.verticalVelocity;
	}
	
	/**
	 * Set the velocity of the alien in the y-direction to the given vertical velocity.
	 * @param	verticalVelocity
	 * 			The vertical velocity for the alien.
	 * @post	The new vertical velocity is set to the given one.
	 * 			| (new this).verticalVelocity = verticalVelocity
	 */
	@Basic
	private void setVerticalVelocity(double verticalVelocity) {
		this.verticalVelocity = verticalVelocity;
	}
	
	/**
	 * @return	The acceleration of the alien in the y-direction.
	 * 
	 */
	@Basic
	public double getVerticalAcceleration() {
		return this.verticalAcceleration;
	}
	
	/**
	 * Set the acceleration of the alien in the y-direction to the given vertical acceleration.
	 * @param 	verticalAcceleration
	 * 			The vertical acceleration for the alien.
	 * @post	The new vertical acceleration is set to the given one.
	 * 			| (new this).verticalAcceleration = verticalAcceleration
	 */
	@Basic
	private void setVerticalAcceleration(double verticalAcceleration) {
		this.verticalAcceleration = verticalAcceleration;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getNbHitPoints() {
		return this.hitPoints;
	}
	
	/**
	 * 
	 * @param hitPointsDifference
	 */
	private void setNbHitPoints(int hitPointsDifference) {
		this.hitPoints += hitPointsDifference;
	}
	
	/**
	 * 
	 * @param direction
	 * @return
	 */
	private boolean isValidMovingDirection(Direction direction) {
		return (direction == Direction.RIGHT) || (direction == Direction.LEFT);
	}
	
	/**
	 * 
	 * @param position
	 * @return
	 */
	private boolean isValidPosition(int[] position) {
		return (position[0] <= this.getMaxPosition()[0]) && (position[0] >= 0) && 
				(position[1] <= this.getMaxPosition()[1]) && (position[1] >= 0);
	}
	
	/**
	 * 
	 * @param position
	 * @return
	 */
	public boolean isValidJumpingPosition(int[] position) {
		return position[1] < this.getMaxPosition()[1];
	}
	
	/**
	 * 
	 * @param spriteList
	 * @return
	 */
	public boolean isValidSpriteList(Sprite[] spriteList) {
		return (spriteList != null) && (spriteList.length == 30);
	}
	
	/**
	 * 
	 * @param dt
	 * @return
	 */
	public boolean isValidDt(double dt) {
		return (dt > 0.0D) && (dt < 0.2D);
	}
	
	/**
	 * 
	 * @param hitpoints
	 * @return
	 */
	private boolean isValidNbHitpoints(int hitpoints) {
		return (hitpoints >= 0) && (hitpoints <= 500);
	}
	
	/**
	 * Check whether the alien is jumping or not.
	 * 
	 * @return
	 */
	public boolean isJumping() {
		if (this.getPosition()[1] == 0){
			return false;
		}
		else{
			return true;
		}
	}
	
	/**
	 * Check whether the alien is ducking or not.
	 * 
	 * @return
	 */
	public boolean isDucking(){
		if (this.getMaxHorizontalVelocity() == this.getMaxDuckingVelocity()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Check whether the alien is moving in the x-direction or not.
	 * 
	 * @return
	 */
	public boolean isMovingHorizontally() {
		if (this.getHorizontalVelocity() != 0){
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Check whether the alien has just moved in the x-direction or not.
	 * 
	 * @return
	 */
	public boolean hasJustMovedHorizontally() {
		if (this.timeStalled > 30){
			return false;
		}
		else{
			return true;
		}
	}
	
	/**
	 * Check whether the alien is moving or has just moved to the left (negative x-direction) or not.
	 * 
	 * @return
	 */
	public boolean isMovingLeft(){
		if (this.lastDirection == Direction.LEFT){
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Make the alien begin to move in the x-direction to the left (negative x-direction) or to the right 
	 * (positive x-direction) depending on the given direction. Also limit the velocity in the 
	 * x-direction to its maximum.
	 */
	public void startMoveHorizontally(Direction direction) {
		assert (isValidMovingDirection(direction));
		if (direction == Direction.RIGHT){
			this.lastDirection = Direction.RIGHT;
			if (this.getHorizontalVelocity() < this.getMaxHorizontalVelocity()) { 
				this.setHorizontalVelocity(this.initialVelocity);
				this.setHorizontalAcceleration(0.9);
			}
			else {
				this.setHorizontalAcceleration(0);
				this.setHorizontalVelocity(this.getMaxHorizontalVelocity());
			}
		}
		else{
			this.lastDirection = Direction.LEFT;
			if (this.getHorizontalVelocity() > -this.getMaxHorizontalVelocity()) { 
				this.setHorizontalVelocity(-this.initialVelocity);
				this.setHorizontalAcceleration(-0.9);
			}
			else {
				this.setHorizontalAcceleration(0);
				this.setHorizontalVelocity(-(this.getMaxHorizontalVelocity()));
			}
		}
	}
	
	/**
	 * Make the alien stop moving in the given direction.
	 */
	public void endMoveHorizontally(Direction direction) {
		assert (isValidMovingDirection(direction));
		if ((direction == Direction.RIGHT) && (this.getHorizontalVelocity() > 0)) {
			this.setHorizontalAcceleration(0);
			this.setHorizontalVelocity(0);
		}
		if ((direction == Direction.LEFT) && (this.getHorizontalVelocity() < 0)) {
			this.setHorizontalAcceleration(0);
			this.setHorizontalVelocity(0);
		}
	}
	
	/**
	 * Make the alien begin to jump (move in the positive y-direction).
	 */
	public void startJump() {
		this.setVerticalVelocity(8);
		this.setVerticalAcceleration(-10);
	}
	
	/**
	 * End the jumping of the alien.
	 */
	public void endJump() {
		if (this.getVerticalVelocity() > 0) {
			this.setVerticalVelocity(0);
		}
	}
	
	/**
	 * Make the alien begin to duck (move in the negative y-direction).
	 */
	public void startDuck() {
		this.setHorizontalAcceleration(0);
		this.setMaxHorizontalVelocity(this.getMaxDuckingVelocity());
	}
	
	/**
	 * End the ducking of the alien.
	 */
	public void endDuck() {
		this.setMaxHorizontalVelocity(this.maxRunningVelocity);
	}
	
	/**
	 * Return the new x-position in the game world of the alien after it moved horizontally. Also limit 
	 * the x-position to its maximum.
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
	 * @post	If the given x-position is the smaller than or equal to the minimum and the alien is 
	 * 			trying to move in the negative x-direction, the new horizontal velocity and acceleration 
	  * 			are set to 0 and the new x-position is set to the minimum.
	 * 			| if ((this.getPosition()[0] <= 0) && (this.getHorizontalVelocity() < 0)) 
	 * 			|	(new this).setHorizontalAcceleration(0) 
	 * 			|	(new this).setHorizontalVelocity(0) 
	 * 			|	(new this).setPosition(0, this.getPosition()[1])
	 * @post	If the given x-position is the bigger than or equal to the maximum and the alien is 
	 * 			trying to move in the positive x-direction, the horizontal velocity and acceleration are
	 * 			set to 0 and the x-position is set to the maximum.
	 * 			| if ((this.getPosition()[0] >= (this.maxPositionX)) && 
	 * 			|		(this.getHorizontalVelocity() > 0)) 
	 * 			|	(new this).setHorizontalAcceleration(0) 
	 * 			|	(new this).setHorizontalVelocity(0) 
	 * 			|	(new this).setPosition(0, this.getPosition()[1])
	 * @post	The new horizontal velocity is set to the sum of the current horizontal velocity and the 
	 * 			product of the current horizontal acceleration and dt.
	 * 			| (new this).setHorizontalVelocity(this.getHorizontalVelocity() + 
	 * 			|	this.getHorizontalAcceleration()*dt)
	 */
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
		if ((this.getPosition()[0] <= 0) && (this.getHorizontalVelocity() < 0)) {
			this.setHorizontalAcceleration(0);
			this.setHorizontalVelocity(0);
		}
		if ((this.getPosition()[0] >= this.getMaxPosition()[0]) && (this.getHorizontalVelocity() > 0)) {
			this.setHorizontalAcceleration(0);
			this.setHorizontalVelocity(0);
		}
		this.setHorizontalVelocity(this.getHorizontalVelocity() + this.getHorizontalAcceleration() * dt);
		double newPositionX = this.getHorizontalVelocity() * dt - 
				this.getHorizontalAcceleration() * Math.pow(dt, 2) + 
				this.getHorizontalAcceleration() * Math.pow(dt, 2) / 2;
		return newPositionX;
	}
	
	/**
	 * Return the new y-position in the game world of the alien after it moved vertically. Also limit 
	 * the y-position to its maximum.
	 * @param	dt
	 * 			The time passed dt.
	 * @post	If the given y-position is the smaller than or equal to the minimum and the alien is 
	  * 			trying to move in the negative y-direction, the new vertical velocity and acceleration 
	 * 			are set to 0 and the new y-position is set to the minimum.
	 * 			| if ((this.getPosition()[1] <= 0) && (this.getVerticalVelocity() < 0)) 
	 * 			|	(new this).setVerticalAcceleration(0) 
	 * 			|	(new this).setVerticalVelocity(0) 
	 * 			|	(new this).setPosition(this.getPosition()[0], 0)
	 * @post	If the given y-position is the bigger than or equal to the maximum and the alien is 
	 * 			trying to move in the positive y-direction, the vertical velocity is set to 0 and the 
	 * 			acceleration is set to -10.
	 * 			| if ((this.getPosition()[1] >= this.maxPositionY) && 
	 * 			|		(this.getVerticalVelocity() > 0))
	 * 			|	(new this).setVerticalAcceleration(-10)
	 * 			|	(new this).setVerticalVelocity(0)
	 * @post	The new vertical velocity is set to the sum of the current vertical velocity and the 
	 * 			product of the current vertical acceleration and dt.
	 * 			| (new this).setVerticalVelocity(this.getVerticalVelocity() + 
	 * 			|	this.getVerticalAcceleration()*dt)
	 */ 
	private double verticalMovement(double dt) throws IllegalArgumentException {
		if (! isValidDt(dt))
			throw new IllegalArgumentException("The given period of time dt is invalid!");
		if ((this.getPosition()[1] <= 0) && (this.getVerticalVelocity() < 0)) {
			this.setVerticalAcceleration(0);
			this.setVerticalVelocity(0);
		}
		if ((this.getPosition()[1] >= this.getMaxPosition()[1]) && (this.getVerticalVelocity() > 0)) {
			this.setVerticalAcceleration(-10);
			this.setVerticalVelocity(0.0D);
		}
		this.setVerticalVelocity(this.getVerticalVelocity() + this.getVerticalAcceleration()*dt);
		double newPositionY = this.getVerticalVelocity() * dt - 
				this.getVerticalAcceleration() * Math.pow(dt, 2) + 
				this.getVerticalAcceleration() * Math.pow(dt, 2) / 2;
		return newPositionY;
	}
	
	/**
	 * Set the new position on the screen of the alien to new position in the game world. Also adapt the
	 * time moving or not moving to the time passed (dt).
	 * @param 	dt
	 * 			The time passed dt.
	 */
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
		if (this.getHorizontalVelocity() == 0) {
		    this.timeStalled += 1;
			this.timeMoving = 0;
		}
		else {
			this.timeStalled = 0;
			this.timeMoving += 1;
		}
	}
}









