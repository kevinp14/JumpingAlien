package jumpingalien.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import jumpingalien.util.*;

/**
 *
 * @author Gebruiker
 *
 */ //TODO
public class GameObject {
		
	private double horizontalVelocity;
	private double horizontalAcceleration;
	private double normalHorizontalVelocity;
	private double normalHorizontalAcceleration;
	private double maxHorizontalVelocity;
	private double verticalVelocity;
	private double verticalAcceleration;
	protected double normalVerticalAcceleration = -10;
	private double positionX;
	private double positionY;
	private double maxPositionX = 1023;
	private double maxPositionY = 767;
	private Sprite[] spriteList;
	private double timeStalled;
	protected double timeImmune;
	protected double timeImmuneForMagma;
	private Direction lastDirection;
	private Direction nextDirection;
	private int hitPoints;
	private boolean isImmune;
	private boolean isImmuneForMagma;
	protected World world;

	/**
	 * 
	 * @param positionX
	 * @param positionY
	 * @param spriteList
	 */ //TODO
	public GameObject(double positionX, double positionY, Sprite[] spriteList) {
		int[] position = { (int)(positionX/100), (int)(positionY/100) };
		assert (isValidPosition(position));
		assert (isValidSpriteList(spriteList));
		this.setPosition(positionX, positionY);
		this.setHorizontalVelocity(0);
		this.setHorizontalAcceleration(0);
	    this.setVerticalVelocity(0);
		this.setVerticalAcceleration(0);
		this.spriteList = spriteList;
	    this.timeStalled = 0;
	    this.timeImmune = 0;
	    this.timeImmuneForMagma = 0;
	    this.lastDirection = Direction.STALLED;
	    this.nextDirection = Direction.STALLED;
		this.isImmune = false;
		this.isImmuneForMagma = false;
		this.world = null;
	}
	
	/**
	 * Set the world of where the game object has to be to the give world.
	 * 
	 * @param world
	 */
	protected void setWorld(World world){
		this.world = world;
	}
	
	/**
	 * Return the current sprite image for the given game object.
	 * 
	 * @return The current sprite image for the given game object, determined by its
	 *         orientation as defined in the assignment.
	 */
	@Basic
	@Immutable
	public Sprite getCurrentSprite() {
		if (this.isMovingLeft())
			return spriteList[0];
		else
			return spriteList[1];
	}

	/**
	 * Returns the current location of the given game object.
	 * 
	 * @return An array, consisting of 2 integers {x, y}, that represents the
	 *         coordinates of the given game object's bottom left pixel in the world.
	 */
	@Basic
	public int[] getPosition() {
		int[] position = new int[]{ (int)this.positionX, (int)this.positionY };
		return position;
	}

	/**
	 * Set the position of the game object to the given position in x- an y-direction.
	 * 
	 * @param 	positionX
	 * 			The position in the x-direction of the game object.
	 * @param 	positionY
	 * 			The position in the y-direction of the game object.
	 * 
	 */
	@Basic
	protected void setPosition(double positionX, double positionY) {
		this.positionX = positionX;
		this.positionY = positionY;
	}
	
	/**
	 * Returns the maximum possible position in the game world.
	 * 
	 * @return An array, consisting of 2 integers {x, y}, that represents the
	 *         maximum possible position in the game world.
	 */
	@Basic
	protected int[] getMaxPosition() {
		int[] maxPosition = { (int)this.maxPositionX, (int)this.maxPositionY };
	    return maxPosition;
	}

	/**
	 * @return	The current number of hitpoints of the game object.
	 */
	@Basic
	public int getNbHitPoints() {
		return this.hitPoints;
	}

	/**
	 * Add the given hitpoints difference to the current number of hitpoints of the game object if its
	 * hitpoints stay bigger than 0 by doing this.
	 * 
	 * @param hitPointsDifference
	 */ //TODO totaal
	@Basic
	protected void changeNbHitPoints(int hitPointsDifference) {
		if ((hitPointsDifference < 0) && (Math.abs(hitPointsDifference) > this.getNbHitPoints()))
			this.hitPoints = 0;
		else
			this.hitPoints += hitPointsDifference;
	}
	
	/**
	 * @return The velocity of the game object in the x-direction.
	 *
	 */
	@Basic
	public double getHorizontalVelocity() {
		return this.horizontalVelocity;
	}

	/**
	 * Set the velocity of the game object in the x-direction to the given horizontal velocity.
	 * @param	horizontalVelocity
	 * 			The horizontal velocity for the game object.
	 * @post	If the given horizontal velocity is smaller than the maximum horizontal velocity, the new
	 * 			horizontal velocity is set to the given one. If the given horizontal velocity is bigger
	 * 			or equal to the maximum horizontal velocity, the new velocity is set to the maximum.
	 * 			| if (Math.abs(horizontalVelocity) < this.getMaxHorizontalVelocity())
	 * 			|	(new this).horizontalVelocity = horizontalVelocity;
	 * 			| else
	 * 			|	(new this).horizontalVelocity = this.getMaxHorizontalVelocity();
	 */
	@Basic
	protected void setHorizontalVelocity(double horizontalVelocity) {
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
	 * @return	The acceleration of the game object in the x-direction.
	 *
	 */
	@Basic
	public double getHorizontalAcceleration() {
		return this.horizontalAcceleration;
	}

	/**
	 * Set the acceleration of the game object in the x-direction to the given vertical acceleration.
	 * @param	horizontalAcceleration
	 * 			The horizontal acceleration for the game object.
	 * @post	The new horizontal acceleration is set to the given one.
	 * 			| (new this).horizontalAcceleration = horizontalAcceleration
	 */ //TODO totaal
	@Basic
	protected void setHorizontalAcceleration(double horizontalAcceleration) {
		this.horizontalAcceleration = horizontalAcceleration;
	}

	/**
	 * @return	The maximum of the velocity of the game object in the x-direction.
	 * 
	 */
	@Basic
	public double getMaxHorizontalVelocity() {
		return this.maxHorizontalVelocity;
	}

	/**
	 * @return	The velocity of the game object in the y-direction.
	 * 
	 */
	@Basic
	public double getVerticalVelocity() {
		return this.verticalVelocity;
	}
	
	/**
	 * Set the velocity of the game object in the y-direction to the given vertical velocity.
	 * @param	verticalVelocity
	 * 			The vertical velocity for the game object.
	 * @post	The new vertical velocity is set to the given one.
	 * 			| (new this).verticalVelocity = verticalVelocity
	 */
	@Basic
	protected void setVerticalVelocity(double verticalVelocity) {
		this.verticalVelocity = verticalVelocity;
	}
	
	/**
	 * @return	The acceleration of the game object in the y-direction.
	 * 
	 */
	@Basic
	public double getVerticalAcceleration() {
		return this.verticalAcceleration;
	}

	/**
	 * Set the acceleration of the game object in the y-direction to the given vertical acceleration.
	 * @param 	verticalAcceleration
	 * 			The vertical acceleration for the game object.
	 * @post	The new vertical acceleration is set to the given one.
	 * 			| (new this).verticalAcceleration = verticalAcceleration
	 */ //TODO totaal
	@Basic
	protected void setVerticalAcceleration(double verticalAcceleration) {
		this.verticalAcceleration = verticalAcceleration;
	}
	
	/**
	 * @return The last direction in which the game object has moved.
	 */
	protected Direction getLastDirection() {
		return this.lastDirection;
	}
	
	/**
	 * Set the last direction in which game object has moved to the given direction.
	 * 
	 * @param 	direction
	 * 			The direction to which the last direction has to be set.
	 */
	protected void setLastDirection(Direction direction){
		this.lastDirection = direction;
	}
	
	/**
	 * @return The next direction in which the game object has to move.
	 */
	protected Direction getNextDirection() {
		return this.nextDirection;
	}
	
	/**
	 * Set the next direction in which game object has to move to the given direction.
	 * 
	 * @param 	direction
	 * 			The direction to which the next direction has to be set.
	 */
	protected void setNextDirection(Direction direction){
		this.nextDirection = direction;
	}

	/**
	 * Check whether the given direction is a valid one to move in.
	 * 
	 * @param 	direction
	 * 			The direction which has to be checked.
	 * @return	True if and only if the direction is right or left.
	 */
	protected boolean isValidMovingDirection(Direction direction) {
		return (direction == Direction.RIGHT) || (direction == Direction.LEFT);
	}

	/**
	 * Check whether the given position is a valid one.
	 * 
	 * @param 	position
	 * 			The position which has to be checked.
	 * @return	True if and only if the position is bigger than 0 (both in x- and y-direction) and 
	 * 			smaller than the maximum position in the game world (both in x- and y-direction).
	 */
	protected boolean isValidPosition(int[] position) {
		return (position[0] <= this.getMaxPosition()[0]) && (position[0] >= 0) && 
				(position[1] <= this.getMaxPosition()[1]) && (position[1] >= 0);
	}

	/**
	 * Check whether the given sprite list is a valid one.
	 * 
	 * @param 	spriteList
	 * 			The sprite list which has to be checked.
	 * @return	True if and only if the given sprite list is not null.
	 */
	protected boolean isValidSpriteList(Sprite[] spriteList) {
		return (spriteList != null);
	}

	/**
	 * Check whether the given period of time dt is a valid dt.
	 * 
	 * @param 	dt
	 * 			The dt which has to be checked.
	 * @return	True if and only if the given dt is between 0 and 0.2.
	 */
	protected boolean isValidDt(double dt) {
		return (dt > 0) && (dt <= 0.2);
	}
	
	/**
	 * Check whether the game object is moving in the x-direction or not.
	 * 
	 * @return	True if and only if the horizontal velocity of the game object is not 0.
	 */
	public boolean isMovingHorizontally() {
		return (this.getHorizontalVelocity() != 0);
	}

	/**
	 * Check whether the game object has just moved in the x-direction or not.
	 * 
	 * @return	True if and only if the time stalled is smaller than or equal to 30.
	 */
	public boolean hasJustMovedHorizontally() {
		return (this.timeStalled <= 30);
	}

	/**
	 * Check whether the game object is moving or has just moved to the left (negative x-direction) 
	 * or not.
	 * 
	 * @return	True if and only if the last direction of the game object is left.
	 */
	public boolean isMovingLeft(){
		return (this.getLastDirection() == Direction.LEFT);
	}
	
	/**
	 * Check whether the game object is moving or has just moved to the right (negative x-direction) 
	 * or not.
	 * 
	 * @return	True if and only if the last direction of the game object is right.
	 */
	protected boolean isMovingRight() {
		return (this.getLastDirection() == Direction.RIGHT);
	}
	
	/**
	 * Check whether the game object is falling or not.
	 * 
	 * @return	True if and only if the vertical velocity of the game object is negative and the 
	 * 			vertical acceleration of the game object is -10.
	 */
	protected boolean isFalling() {
		return ((this.getVerticalVelocity() < 0) && (this.getVerticalAcceleration() == -10));
	}
	
	/**
	 * Check whether the given game object is currently immune against enemies.
	 * 
	 * @return True if and only if isImmune is true for the game object.
	 */
	public boolean isImmune(){
		return (this.isImmune);
	}
	
	/**
	 * Check whether the given game object is currently immune against magma.
	 * 
	 * @return True if and only if isImmuneForMagma is true for the game object.
	 */
	protected boolean isImmuneForMagma() {
		return (this.isImmuneForMagma);
	}
	
	/**
	 * Check whether the game object is in air or not.
	 * 
	 * @return	True if and only if the geological feature at the game object's position is 0 (air).
	 */
	protected boolean isInAir() {
		return (this.world.getGeologicalFeature(this.getPosition()[0], this.getPosition()[1]) == 0);
	}
	
	/**
	 * Check whether the game object is in water or not.
	 * 
	 * @return	True if and only if the geological feature at the game object's position is 2 (water).
	 */
	protected boolean isInWater(){
		return (this.world.getGeologicalFeature(this.getPosition()[0], this.getPosition()[1]) == 2);
	}
	
	/**
	 * Check whether the game object is in magma or not.
	 * 
	 * @return	True if and only if the geological feature at the game object's position is 3 (magma).
	 */
	protected boolean isInMagma(){
		return (this.world.getGeologicalFeature(this.getPosition()[0], this.getPosition()[1]) == 3);
	}
	
	/**
	 * Makes the game object immune for enemies.
	 */
	protected void makeImmune() {
		if (this.timeImmune <= 60) 
			this.isImmune = true;
		else {
			this.isImmune = false;
			this.timeImmune = 0;
		}
	}
	
	/**
	 * Makes the game object immune for magma.
	 */
	protected void makeImmuneForMagma() {
		if (this.timeImmuneForMagma <= 20) 
			this.isImmuneForMagma = true;
		else {
			this.isImmuneForMagma = false;
			this.timeImmuneForMagma = 0;
		}
	}
	
	/**
	 * Check whether the game object collides with the given object.
	 * 
	 * @param 	object
	 * 			The object with which the game object could collide.
	 * @return	True if and only if no sides of the game object's tile collide with a side of the 
	 * 			given object's tile
	 */
	protected boolean collidesWith(GameObject object){
		return (!((this.getPosition()[0] + (this.getCurrentSprite().getWidth() - 1) 
						< object.getPosition()[0])
				|| (object.getPosition()[0] + (object.getCurrentSprite().getWidth() - 1) 
						< this.getPosition()[0]) 
				|| (this.getPosition()[1] + (this.getCurrentSprite().getHeight() - 1) 
						< object.getPosition()[1])
				|| (object.getPosition()[1] + (object.getCurrentSprite().getHeight() - 1) 
						< this.getPosition()[1])));
	}
	
	/**
	 * Check whether the bottom side of the game object's tile collides with the top side of the given
	 * object's tile.
	 *  
	 * @param 	object
	 * 			The object with which the game object could collide.
	 * @return	True if and only if the game object collides with the object and the y-position of the
	 * 			game object equals the y-position of the given object - 1.
	 */
	protected boolean bottomCollidesWithTopOfObject(GameObject object) {
		return ((this.collidesWith(object)) && (this.getPosition()[1] == (object.getPosition()[1] - 1)));
	}
	
	/**
	 * Make the game object begin to move in the x-direction to the left (negative x-direction) 
	 * or to the right (positive x-direction) depending on the given direction and set the next
	 * direction (only useful for plants). Also limit the velocity in the x-direction to its maximum.
	 * 
	 * @param	direction
	 * 			The given direction in which the alien has to move.
	 */
	public void startMoveHorizontally(Direction direction) {
		assert (isValidMovingDirection(direction));
		if (direction == Direction.RIGHT) {
			this.setLastDirection(Direction.RIGHT);
			this.setNextDirection(Direction.LEFT);
			if (this.getHorizontalVelocity() < this.getMaxHorizontalVelocity()) { 
				this.setHorizontalVelocity(this.normalHorizontalVelocity);
				this.setHorizontalAcceleration(this.normalHorizontalAcceleration);
			}
			else {
				this.setHorizontalAcceleration(0);
				this.setHorizontalVelocity(this.getMaxHorizontalVelocity());
			}
		}
		else{
			this.setLastDirection(Direction.LEFT);
			this.setNextDirection(Direction.RIGHT);
			if (this.getHorizontalVelocity() > -this.getMaxHorizontalVelocity()) { 
				this.setHorizontalVelocity(-this.normalHorizontalVelocity);
				this.setHorizontalAcceleration(-this.normalHorizontalAcceleration);
			}
			else {
				this.setHorizontalAcceleration(0);
				this.setHorizontalVelocity(-this.getMaxHorizontalVelocity());
			}
		}
	}

	/**
	 * Make the game object stop moving in the given horizontal direction.
	 *
	 * @param	direction
	 * 			The horizontal direction in which the game object was moving.
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
}