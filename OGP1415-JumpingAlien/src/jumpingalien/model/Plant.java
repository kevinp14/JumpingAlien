package jumpingalien.model;

import java.util.Random;

import be.kuleuven.cs.som.annotate.Basic;
import jumpingalien.util.Sprite;

/**
 * 
 * @author Gebruiker
 *
 */ //TODO
public class Plant extends GameObject {
	private double normalHorizontalVelocity = 0.5;
	private double normalHorizontalAcceleration = 0;
	private double maxHorizontalVelocity = 0.5;
	private double timeMovingHorizontally;
	private int hitPoints = 1;
	
	/**
	 * 
	 * @param positionX
	 * @param positionY
	 * @param spriteList
	 */ //TODO
	public Plant(int positionX, int positionY, Sprite[] spriteList){
		super(positionX, positionY, spriteList);
	    this.timeMovingHorizontally = 0;
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
	 * 			| if (this.isMovingHorizontally())
	 * 			|	if (!(this.timeMovingHorizontally <= 50))
	 * 			|		this.endMoveHorizontally(this.getLastDirection())
	 * 			|		this.startMoveHorizontally(this.getNextDirection())
	 * @post	If the plant is not moving horizontally already, it starts moving into a random direction
	 * 			(left or right).
	 * 			| if (!this.isMovingHorizontally())
	 * 			|	this.startMoveHorizontally(this.getRandomMove())
	 * @return	newPositionX
	 * 			The plant's new x-position after horizontal movement.
	 * 			| newPositionX = this.getHorizontalVelocity() * dt
	 */
	private double horizontalMovement(double dt) throws IllegalArgumentException {
		if (! isValidDt(dt))
			throw new IllegalArgumentException("The given period of time dt is invalid!");
		if ((this.getPosition()[0] <= 0) && (this.getHorizontalVelocity() < 0)) {
			this.setHorizontalAcceleration(0); 
			this.setHorizontalVelocity(0);
		}
		if ((this.getPosition()[0] >= (this.getMaxPosition()[0])) && 
				(this.getHorizontalVelocity() > 0)) {
			this.setHorizontalAcceleration(0);
			this.setHorizontalVelocity(0);
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
			if (!(this.timeMovingHorizontally <= 50)) {
				this.endMoveHorizontally(this.getLastDirection());
				this.startMoveHorizontally(this.getNextDirection());
			}
		}
		else {
			this.startMoveHorizontally(this.getRandomDirection());
		}
		double newPositionX = this.getHorizontalVelocity() * dt;
		return newPositionX;
	}
	
	/**
	 * Set the new position on the screen of the plant to new position in the game world, limit the
	 * plant's position to the minimum (in x-direction) and the maximum (both in x- and y-direction),
	 * block the plant's movement if it wants to move to a tile that is not passable, adapt the time
	 * moving horizontally, and decrease the plants hitpoints if it collides with a hungry alien.
	 * @param	dt
	 * 			The time passed dt.
	 * @throws	//TODO
	 */
	public void advanceTime(double dt) throws IllegalArgumentException {
		if (! isValidDt(dt))
			throw new IllegalArgumentException("The given period of time dt is invalid!");
		this.setPosition(this.getPosition()[0] + (int)(100 * this.horizontalMovement(dt)),
				this.getPosition()[1]);
		if ((this.getPosition()[0] <= 0) && (this.getHorizontalVelocity() < 0)) {
			this.setPosition(0, this.getPosition()[1]);
		}
		if ((this.getPosition()[0] >= (this.getMaxPosition()[0])) && 
				(this.getHorizontalVelocity() > 0)) {
			this.setPosition(this.getMaxPosition()[0], this.getPosition()[1]);
		}
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
		if (!(this.getHorizontalVelocity() == 0)) {
			this.timeMovingHorizontally += 1;
		}
		if ((this.collidesWith(this.world.getMazub())) && (this.world.getMazub().getNbHitPoints() <= 500))
			this.changeNbHitPoints(-1);
	}
}