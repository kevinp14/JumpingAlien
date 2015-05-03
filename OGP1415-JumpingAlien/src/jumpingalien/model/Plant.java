package jumpingalien.model;

import java.util.Random;

import jumpingalien.util.Sprite;

/**
 * A class of plants involving the normal and maximum for the horizontal velocity, the time the plant 
 * is moving horizontally, the number of hitpoints, a method to create a random direction and a method 
 * to advance time and adapt the time depending characteristics based on the period of time passed.
 * 
 * @invar //TODO
 * @author	Kevin Peeters (Tweede fase ingenieurswetenschappen)
 * 			Jasper Mariën (Tweede fase ingenieurswetenschappen)
 * @version 3.0
 *
 */
public class Plant extends GameObject {
	
	private double timeMovingHorizontally;
	
	/**
	 * Initialize the plant at the given position in x- and y-direction with the given list of
	 * sprites. Also sets the time moving horizontally to 0.
	 * 
	 * @param 	positionX
	 * 			The position in the x-direction where the plant should be.
	 * @param 	positionY
	 * 			The position in the y-direction where the plant should be.
	 * @param 	spriteList
	 * 			The list of sprites displaying how the plant should look depending on its behavior.
	 */
	public Plant(int positionX, int positionY, Sprite[] spriteList){
		super(positionX, positionY, spriteList);
		this.setLastDirection(this.getRandomDirection());
		this.normalHorizontalVelocity = 0.5;
	    this.timeMovingHorizontally = 0;
	    this.changeNbHitPoints(1);
	}
	
	/**
	 * @return A random direction (left or right) for the plant to move in.
	 */
	private Direction getRandomDirection(){
		Random rand = new Random();
	    int directionNumber = rand.nextInt(2);
	    if (directionNumber == 0)
	    	return Direction.LEFT;
	    else
	    	return Direction.RIGHT;
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
	
	@Override
	public void startMoveHorizontally(Direction direction) {
		assert (isValidMovingDirection(direction));
		if (direction == Direction.RIGHT) {
			this.setLastDirection(Direction.RIGHT);
			this.setNextDirection(Direction.LEFT);
			this.setHorizontalVelocity(this.normalHorizontalVelocity);
		}
		else {
			this.setLastDirection(Direction.LEFT);
			this.setNextDirection(Direction.RIGHT);
			this.setHorizontalVelocity(-this.normalHorizontalVelocity);
		}
	}
	
	@Override
	public void endMoveHorizontally(Direction direction) {
		assert (isValidMovingDirection(direction));
		if ((direction == Direction.RIGHT) && (this.getHorizontalVelocity() > 0))
			this.setHorizontalVelocity(0);
		if ((direction == Direction.LEFT) && (this.getHorizontalVelocity() < 0))
			this.setHorizontalVelocity(0);
	}
	
	/**
	 * Return the new x-position in the game world of the plant after it moved horizontally. Also
	 * stop the plant from moving if the tile to which it wants to move is not passable.
	 * @param	dt
	 * 			The time passed dt.
	 * @post	If the plant's x-position is the smaller than or equal to the minimum and the plant is 
	 * 			trying to move in the negative x-direction, the new horizontal velocity and acceleration 
	 * 			are set to 0 and the new x-position is set to the minimum.
	 * 			| if ((this.getPosition()[0] <= 0) && (this.getHorizontalVelocity() < 0)) 
	 * 			|	(new this).setHorizontalAcceleration(0) 
	 * 			|	(new this).setHorizontalVelocity(0)
	 * @post	If the plant's x-position is the bigger than or equal to the maximum and the plant is 
	 * 			trying to move in the positive x-direction, the horizontal velocity and acceleration are
	 * 			set to 0 and the x-position is set to the maximum.
	 * 			| if ((this.getPosition()[0] >= (this.maxPositionX)) && 
	 * 			|		(this.getHorizontalVelocity() > 0)) 
	 * 			|	(new this).setHorizontalAcceleration(0) 
	 * 			|	(new this).setHorizontalVelocity(0)
	 * @post	If the plant's horizontal velocity is smaller than 0 and the tile to the left of the
	 * 			plant is not passable, the new horizontal acceleration and velocity are set to 0.
	 * 			| 		if ((this.world.isNotPassable(this.world.getGeologicalFeature(
	 * 			|				this.getPosition()[0], this.getPosition()[1])))	
	 * 			|				&& (this.getHorizontalVelocity() < 0)) 
	 * 			|			(new this).setHorizontalAcceleration(0)
	 * 			|			(new this).setHorizontalVelocity(0)
	 * @post	If the plant's horizontal velocity is bigger than 0 and the tile to the right of the
	 * 			plant is not passable, the new horizontal acceleration and velocity are set to 0.
	 * 			| 		if ((this.world.isNotPassable(this.world.getGeologicalFeature(
	 * 			|				this.getPosition()[0] + this.getCurrentSprite().getWidth(), 
	 * 			|				this.getPosition()[1]))) && (this.getHorizontalVelocity() > 0)) 
	 * 			|			(new this).setHorizontalAcceleration(0)
	 * 			|			(new this).setHorizontalVelocity(0)
	 * @post	If the plant is already moving horizontally for more than 0.5s, it starts moving into
	 * 			the next (opposite) direction.
	 * 			|if (!(this.timeMovingHorizontally <= 50))
	 * 			|	this.endMoveHorizontally(this.getLastDirection())
	 * 			|	this.startMoveHorizontally(this.getNextDirection())
	 * @return	newPositionX
	 * 			The plant's new x-position after horizontal movement.
	 * 			| newPositionX = this.getHorizontalVelocity() * dt
	 */
	private double horizontalMovement(double dt) throws IllegalArgumentException {
		if ((this.getPosition()[0] <= 0) && (this.getHorizontalVelocity() < 0)) {
			this.setHorizontalAcceleration(0); 
			this.setHorizontalVelocity(0);
		}
		else if ((this.getPosition()[0] >= (this.getMaxPosition()[0])) && 
				(this.getHorizontalVelocity() > 0)) {
			this.setHorizontalAcceleration(0);
			this.setHorizontalVelocity(0);
		}
		else if ((this.getWorld().isNotPassable(this.getWorld().getGeologicalFeature(
				this.getPosition()[0] + 1, this.getPosition()[1] + 1)))
				&& (this.getHorizontalVelocity() < 0)) {
			this.setHorizontalAcceleration(0);
			this.setHorizontalVelocity(0);
		}
		else if ((this.getWorld().isNotPassable(this.getWorld().getGeologicalFeature(
				this.getPosition()[0] + this.getCurrentSprite().getWidth() - 1, 
				this.getPosition()[1] + 1))) 
				&& (this.getHorizontalVelocity() > 0)) {
			this.setHorizontalAcceleration(0);
			this.setHorizontalVelocity(0);
		}
		double newPositionX = this.getHorizontalVelocity() * dt;
		return newPositionX;
	}
	
	/**
	 * 
	 * @param newDt
	 * @param oldPosition
	 */
	private void collidesWithActions(double newDt, int[] oldPosition) {
		if ((this.collidesWith(this.getWorld().getMazub())) && 
				(this.getWorld().getMazub().getNbHitPoints() <= 500))
			this.changeNbHitPoints(-1);
	}
	
	/**
	 * Set the new position on the screen of the plant to new position in the game world, limit the
	 * plant's position to the minimum (in x-direction) and the maximum (both in x- and y-direction),
	 * block the plant's movement if it wants to move to a tile that is not passable, adapt the time
	 * moving horizontally, and decrease the plants hitpoints if it collides with a hungry alien.
	 * @param	dt
	 * 			The time passed dt.
	 * @effect //TODO
	 * @throws	IllegalArgumentException
	 * 			| !isValidDt(dt)
	 */
	public void advanceTime(double dt) throws IllegalArgumentException {
		double newDt = this.getNewDt(dt);
		int[] oldPosition = this.getPosition();
		if (!this.isValidDt(newDt))
			throw new IllegalArgumentException("The given period of time dt is invalid!");
		if (this.crossBoundaries())
			this.crossBoundariesActions();
		else if (this.touchImpassableRight())
			this.crossImpassableActions(oldPosition);
		this.setPosition(this.getPosition()[0] + (100 * this.horizontalMovement(newDt)),
				this.getPosition()[1]);
		this.collidesWithActions(newDt, oldPosition);
		if (this.timeMovingHorizontally >= 0.50) {
			this.timeMovingHorizontally = 0;
			this.endMoveHorizontally(this.getLastDirection());
			this.startMoveHorizontally(this.getNextDirection());
		}
		if (this.timeMovingHorizontally < 0.50) {
			this.timeMovingHorizontally += dt;
		}
	}
}