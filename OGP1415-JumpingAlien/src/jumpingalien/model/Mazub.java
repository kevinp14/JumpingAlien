package jumpingalien.model;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import jumpingalien.util.*;


/**
 * A class of Mazub aliens involving some maximums for the horizontal velocity depending on its current
 * actions, a list of sprites or images of the alien that are displayed depending on its current actions, 
 * the time the alien is moving horizontally or standing still, the last direction in which the alien
 * was moving and the secondary direction in which the alien is moving (only when performing multiple
 * movements at the same time), the number of hitpoints, methods to move the alien horizontally as 
 * well as vertically (jumping, falling and ducking), methods to inspect the behavior of the alien 
 * and a method to advance time and adapt the time depending characteristics based on the period of 
 * time passed.
 * 
 * @invar //TODO
 * @author	Kevin Peeters (Tweede fase ingenieurswetenschappen)
 * 			Jasper Mariën (Tweede fase ingenieurswetenschappen)
 * @version 8.0
 *
 */
public class Mazub extends GameObject {
	private double normalHorizontalVelocity;
	private double normalHorizontalAcceleration;
	private double maxHorizontalVelocity;
	private double maxRunningVelocity = 3;
	private double maxDuckingVelocity = 1;
	private double normalVerticalVelocity;
	private Sprite[] spriteList;
	private double timeStalled;
	private double timeMovingHorizontally;
	private Direction secondaryDirection;
	
	/**
	 * Initialize the Mazub alien at the given position in x- and y-direction with the given list of
	 * sprites. Also sets the time moving horizontally to 0.
	 * 
	 * @param 	positionX
	 * 			The position in the x-direction where the alien should be.
	 * @param 	positionY
	 * 			The position in the y-direction where the alien should be.
	 * @param 	spriteList
	 * 			The list of sprites displaying how the alien should look depending on its behavior.
	 */
	public Mazub(double positionX, double positionY, Sprite[] spriteList) {
		super(positionX, positionY, spriteList);
		assert (isValidSpriteList(spriteList));
		this.spriteList = spriteList;
		this.normalHorizontalVelocity = 1;
		this.normalHorizontalAcceleration = 0.9;
		this.normalVerticalVelocity = 8;
	    this.timeMovingHorizontally = 0;
	    this.changeNbHitPoints(100);
	}

	/**
	 * Return the current sprite image for the given alien.
	 * 
	 * @param 	alien
	 *          The alien for which to get the current sprite image.
	 * @return 	The current sprite image for the given alien, determined by its
	 * 			state as defined in the assignment.
	 */
	@Basic 
	@Immutable
	@Override
	public Sprite getCurrentSprite() {
		if (this.isMovingHorizontally()) {
			if (this.isMovingLeft()) {
				if (this.isDucking())
					return spriteList[7];
				if (this.isAirborne())
					return spriteList[5];
				else {
					if ((this.timeMovingHorizontally % 75) <= 11)
						return spriteList[(int) (18 + (this.timeMovingHorizontally % 75))];
					else
						this.timeMovingHorizontally = 0;
				}
			}
			else {
				if (this.isDucking())
					return spriteList[6];
				if (this.isAirborne())
					return spriteList[4];
				else {
					if ((this.timeMovingHorizontally % 75) <= 11)
						return spriteList[(int) (7 + (this.timeMovingHorizontally % 75))];
					else 
						this.timeMovingHorizontally = 0;
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
	private double getMaxDuckingVelocity() {
		return this.maxDuckingVelocity;
	}
	
	/**
	 * @return	The secondary direction if the alien is moving in two directions at the same time.
	 * 
	 */
	private Direction getSecondaryDirection() {
		return this.secondaryDirection;
	}
	
	/**
	 * Set the secondary direction (if the alien is moving in two directions at the same time) to the 
	 * given direction.
	 * 
	 * @param direction
	 */
	private void setSecondaryDirection(Direction direction){
		this.secondaryDirection = direction;
	}
	
	/**
	 * Check whether the alien can jump at the given position or not.
	 * 
	 * @param	position
	 * @return	True if and only if the tile above the alien is passable.
	 */
	public boolean isValidJumpingPosition(int[] position) {
		return (!(this.getWorld().isNotPassable(this.getWorld().getGeologicalFeature(position[0], 
				position[1] + this.getCurrentSprite().getHeight()))));
	}
	
	/**
	 * Check whether the alien is jumping or not.
	 * 
	 * @return	True if and only if the alien's vertical velocity is bigger than 0.
	 */
	public boolean isJumping() {
		return (this.getVerticalVelocity() > 0);
	}
	
	/**
	 * Check whether the alien is in the air or not.
	 * 
	 * @return	True if and only if the alien's vertical velocity is not 0.
	 */
	private boolean isAirborne() {
		return (!(this.getVerticalVelocity() == 0));
	}
	
	/**
	 * Check whether the alien is ducking or not.
	 * 
	 * @return	True if and only if the alien's maximum horizontal velocity is equal to the maximum
	 * 			ducking velocity.
	 */
	public boolean isDucking() {
		return (this.getMaxHorizontalVelocity() == this.getMaxDuckingVelocity());
	}
	
	/**
	 * Check whether the alien can stand up straight again.
	 * 
	 * @return	True if and only if the tile at the height of where the alien's top perimeter would be 
	 * 			when ducking is passable.
	 */
	public boolean canEndDuck() {
		return (!(this.getWorld().isNotPassable(this.getWorld().getGeologicalFeature(this.getPosition()[0],
				this.getPosition()[1] + this.spriteList[0].getHeight()))));
	}
	

	/**
	 * Make the alien begin to jump (move in the positive y-direction).
	 */
	public void startJump() {
		if (this.isValidJumpingPosition(this.getPosition())) {
			this.setVerticalVelocity(this.normalVerticalVelocity);
			this.setVerticalAcceleration(this.normalVerticalAcceleration);
		}
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
	 * Make the alien begin to move in the x-direction to the left (negative x-direction) 
	 * or to the right (positive x-direction) depending on the given direction and set the secondary
	 * direction for if the alien would want to do two horizontal movements at the same time. Also 
	 * limit the velocity in the x-direction to its maximum.
	 * 
	 * @param	direction
	 * 			The given direction in which the alien has to move.
	 */
	@Override
	public void startMoveHorizontally(Direction direction) {
		assert (isValidMovingDirection(direction));
		if (direction == Direction.RIGHT) {
			this.setLastDirection(Direction.RIGHT);
			if (this.isMovingLeft())
				this.setSecondaryDirection(Direction.LEFT);
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
			if (this.isMovingRight())
				this.setSecondaryDirection(Direction.RIGHT);
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
	 * Make the alien stop moving in the given horizontal direction.
	 *
	 * @param	direction
	 * 			The horizontal direction in which the alien was moving.
	 */
	@Override
	public void endMoveHorizontally(Direction direction) {
		assert (isValidMovingDirection(direction));
		if ((direction == Direction.RIGHT) && (this.getHorizontalVelocity() > 0) 
				&& (!(this.getSecondaryDirection() == Direction.LEFT))) {
			this.setHorizontalAcceleration(0);
			this.setHorizontalVelocity(0);
		}
		if ((direction == Direction.RIGHT) && (this.getHorizontalVelocity() > 0) 
				&& (this.getSecondaryDirection() == Direction.LEFT)) {
			this.setHorizontalAcceleration(-this.getHorizontalAcceleration());
		}
		if ((direction == Direction.LEFT) && (this.getHorizontalVelocity() < 0)
				&& (!(this.getSecondaryDirection() == Direction.RIGHT))) {
			this.setHorizontalAcceleration(0);
			this.setHorizontalVelocity(0);
		}
		if ((direction == Direction.LEFT) && (this.getHorizontalVelocity() < 0) 
				&& (this.getSecondaryDirection() == Direction.RIGHT)) {
			this.setHorizontalAcceleration(-this.getHorizontalAcceleration());
		}
	}
	
	/**
	 * Return the new x-position in the game world of the alien after it moved horizontally. Also limit
	 * the alien's velocity to the maximum and stop the alien from moving if the tile to which it wants 
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
	 * @post	If the alien's x-position is the smaller than or equal to the minimum and the alien is 
	 * 			trying to move in the negative x-direction, the new horizontal velocity and acceleration 
	 * 			are set to 0 and the new x-position is set to the minimum.
	 * 			| if ((this.getPosition()[0] <= 0) && (this.getHorizontalVelocity() < 0)) 
	 * 			|	(new this).setHorizontalAcceleration(0) 
	 * 			|	(new this).setHorizontalVelocity(0)
	 * @post	If the alien's x-position is the bigger than or equal to the maximum and the alien is 
	 * 			trying to move in the positive x-direction, the horizontal velocity and acceleration are
	 * 			set to 0 and the x-position is set to the maximum.
	 * 			| if ((this.getPosition()[0] >= (this.maxPositionX)) && 
	 * 			|		(this.getHorizontalVelocity() > 0)) 
	 * 			|	(new this).setHorizontalAcceleration(0) 
	 * 			|	(new this).setHorizontalVelocity(0)
	 * @post	If the alien's horizontal velocity is smaller than 0 and the tile to the left of the
	 * 			alien is not passable, the new horizontal acceleration and velocity are set to 0.
	 * 			| 		if ((this.world.isNotPassable(this.world.getGeologicalFeature(
	 * 			|				this.getPosition()[0], this.getPosition()[1])))	
	 * 			|				&& (this.getHorizontalVelocity() < 0)) 
	 * 			|			(new this).setHorizontalAcceleration(0)
	 * 			|			(new this).setHorizontalVelocity(0)
	 * @post	If the alien's horizontal velocity is bigger than 0 and the tile to the right of the
	 * 			alien is not passable, the new horizontal acceleration and velocity are set to 0.
	 * 			| 		if ((this.world.isNotPassable(this.world.getGeologicalFeature(
	 * 			|				this.getPosition()[0] + this.getCurrentSprite().getWidth(), 
	 * 			|				this.getPosition()[1]))) && (this.getHorizontalVelocity() > 0)) 
	 * 			|			(new this).setHorizontalAcceleration(0)
	 * 			|			(new this).setHorizontalVelocity(0)
	 * @post	The new horizontal velocity is set to the sum of the current horizontal velocity and the 
	 * 			product of the current horizontal acceleration and dt.
	 * 			| (new this).setHorizontalVelocity(this.getHorizontalVelocity() + 
	 * 			|	this.getHorizontalAcceleration()*dt)
	 * @return	newPositionX
	 * 			The alien's new x-position after horizontal movement.
	 * 			| newPositionX = this.getHorizontalVelocity() * dt 
	 * 			|	- this.getHorizontalAcceleration() * Math.pow(dt, 2)
	 * 			|	+ this.getHorizontalAcceleration() * Math.pow(dt, 2) / 2
	 * @throws	IllegalArgumentException
	 * 			| !isValidDt(dt)
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
		if ((this.getPosition()[0] >= (this.getMaxPosition()[0])) && 
				(this.getHorizontalVelocity() > 0)) {
			this.setHorizontalAcceleration(0);
			this.setHorizontalVelocity(0);
		}
		if ((this.getWorld().isNotPassable(this.getWorld().getGeologicalFeature(
				this.getPosition()[0], this.getPosition()[1])))
				&& (this.getHorizontalVelocity() < 0)) {
			this.setHorizontalAcceleration(0);
			this.setHorizontalVelocity(0);
		}
		if ((this.getWorld().isNotPassable(this.getWorld().getGeologicalFeature(
				this.getPosition()[0] + this.getCurrentSprite().getWidth(), this.getPosition()[1]))) 
				&& (this.getHorizontalVelocity() > 0)) {
			this.setHorizontalAcceleration(0);
			this.setHorizontalVelocity(0);
		}
		this.setHorizontalVelocity(this.getHorizontalVelocity() + this.getHorizontalAcceleration() * dt);
		double newPositionX = this.getHorizontalVelocity() * dt 
				- this.getHorizontalAcceleration() * Math.pow(dt, 2) 
				+ this.getHorizontalAcceleration() * Math.pow(dt, 2) / 2;
		return newPositionX;
	}

	/**
	 * Return the new y-position in the game world of the alien after it moved vertically. Also
	 * stop the alien from moving if the tile to which he wants to move is not passable.
	 * @param	dt
	 * 			The time passed dt.
	 * @post	If the given y-position is the bigger than or equal to the maximum and the alien is 
	 * 			trying to move in the positive y-direction, the vertical velocity is set to 0 and the 
	 * 			acceleration is set to -10.
	 * 			| if ((this.getPosition()[1] >= this.maxPositionY) && 
	 * 			|		(this.getVerticalVelocity() > 0))
	 * 			|	(new this).setVerticalVelocity(0)
	 * @post 	If the alien's vertical velocity is smaller than 0 and the tile beneath the alien is not
	 * 			passable, the alien's new vertical acceleration and velocity are set to 0.
	 * 			| 		if ((this.getVerticalVelocity() < 0) && (this.world.isNotPassable(
	 * 			|				this.world.getGeologicalFeature((int)this.getPosition()[0], 
	 * 			|				(int)this.getPosition()[1]))))
	 * 			|			this.setVerticalAcceleration(0)
	 * 			|			this.setVerticalVelocity(0)
	 * @post 	If the alien's vertical velocity is bigger than 0 and the tile above the alien is not
	 * 			passable, the alien's new vertical velocity is set to 0.
	 * 			| 		if ((this.getVerticalVelocity() > 0) && (this.world.isNotPassable(
	 * 			|				this.world.getGeologicalFeature((int)this.getPosition()[0], 
	 * 			|				(int)this.getPosition()[1] + this.getCurrentSprite().getHeight()))))
	 * 			|			this.setVerticalVelocity(0)
	 * @post	The new vertical velocity is set to the sum of the current vertical velocity and the 
	 * 			product of the current vertical acceleration and dt.
	 * 			| (new this).setVerticalVelocity(this.getVerticalVelocity() + 
	 * 			|	this.getVerticalAcceleration()*dt)
	 * @return	newPositionY
	 * 			The alien's new y-position after vertical movement.
	 * 			| newPositionY = this.getVerticalVelocity() * dt 
	 * 			|	- this.getVerticalAcceleration() * Math.pow(dt, 2)
	 * 			|	+ this.getVerticalAcceleration() * Math.pow(dt, 2)/2;
	 * @throws	IllegalArgumentException
	 * 			| !isValidDt(dt)
	 */ 
	private double verticalMovement(double dt) throws IllegalArgumentException {
		if (! isValidDt(dt))
			throw new IllegalArgumentException("The given period of time dt is invalid!");
		if ((this.getPosition()[1] >= this.getMaxPosition()[1]) 
				&& (this.getVerticalVelocity() > 0)) {
			this.setVerticalVelocity(0);
		}
		if ((this.getVerticalVelocity() < 0) && (this.getWorld().isNotPassable(
				this.getWorld().getGeologicalFeature((int)this.getPosition()[0], 
						(int)this.getPosition()[1])))) {
			this.setVerticalAcceleration(0);
			this.setVerticalVelocity(0);
		}
		if ((this.getVerticalVelocity() > 0) && (this.getWorld().isNotPassable(
				this.getWorld().getGeologicalFeature((int)this.getPosition()[0], 
				(int)this.getPosition()[1] + this.getCurrentSprite().getHeight())))) {
			this.setVerticalVelocity(0);
		}
		this.setVerticalVelocity(this.getVerticalVelocity() + this.normalVerticalAcceleration*dt);
		double newPositionY = this.getVerticalVelocity() * dt 
				- this.getVerticalAcceleration() * Math.pow(dt, 2)
				+ this.getVerticalAcceleration() * Math.pow(dt, 2)/2;
		return newPositionY;
	}
	
	/**
	 * Set the new position on the screen of the alien to new position in the game world, limit the
	 * alien's position to the minimum (in x-direction) and the maximum (both in x- and y-direction),
	 * block the alien's movement if it wants to move to a tile that is not passable, adapt the time
	 * moving horizontally and the time not moving, increase the number of hitpoints if the alien 
	 * collides with a plant, block the alien's movement, decrease its hitpoints and make it immune
	 * if it collides with an enemy (only when it does not collide with its bottom perimeter), 
	 * decrease the number of hitpoints if the alien is in water or magma, and make it immune for 
	 * magma if it falls into magma.
	 * @param 	dt
	 * 			The time passed dt.
	 * @effect //TODO
	 * @throws	IllegalArgumentException
	 * 			| !isValidDt(dt)
	 */
	public void advanceTime(double dt) throws IllegalArgumentException {
		if (! isValidDt(dt))
			throw new IllegalArgumentException("The given period of time dt is invalid!");
		this.setPosition(this.getPosition()[0] + (int)(100 * this.horizontalMovement(dt)),
				this.getPosition()[1] + (int)(100 * this.verticalMovement(dt)));
		if ((this.getPosition()[0] <= 0) && (this.getHorizontalVelocity() < 0)) {
			this.setPosition(0, this.getPosition()[1]);
		}
		if ((this.getPosition()[0] >= (this.getMaxPosition()[0])) && 
				(this.getHorizontalVelocity() > 0)) {
			this.setPosition(this.getMaxPosition()[0], this.getPosition()[1]);
		}
		if ((this.getPosition()[1] >= this.getMaxPosition()[1]) 
				&& (this.getVerticalVelocity() > 0)) {
			this.setPosition(this.getPosition()[0], this.getMaxPosition()[1]);
		}
		if ((this.getWorld().isNotPassable(this.getWorld().getGeologicalFeature(
				this.getPosition()[0], this.getPosition()[1])))
				&& (this.getHorizontalVelocity() < 0)) {
			this.setPosition(this.getWorld().getBottomLeftPixelOfTile(this.getPosition()[0], 
					this.getPosition()[1])[0] + this.getWorld().getTileLength(), this.getPosition()[1]);
		}
		if ((this.getWorld().isNotPassable(this.getWorld().getGeologicalFeature(
				this.getPosition()[0] + this.getCurrentSprite().getWidth(), this.getPosition()[1]))) 
				&& (this.getHorizontalVelocity() > 0)) {
			this.setPosition(this.getWorld().getBottomLeftPixelOfTile(this.getPosition()[0], 
					this.getPosition()[1])[0] - this.getWorld().getTileLength(), this.getPosition()[1]);
		}
		if ((this.getVerticalVelocity() < 0) && (this.getWorld().isNotPassable(
				this.getWorld().getGeologicalFeature((int)this.getPosition()[0], 
						(int)this.getPosition()[1])))) {
			this.setPosition(this.getPosition()[0], 
					this.getWorld().getBottomLeftPixelOfTile((int)this.getPosition()[0], 
					(int)this.getPosition()[1])[1] + this.getWorld().getTileLength());
		}			
		if ((this.getVerticalVelocity() > 0) && 
				(this.getWorld().isNotPassable(this.getWorld().getGeologicalFeature((int)this.getPosition()[0], 
				(int)this.getPosition()[1] + this.getCurrentSprite().getHeight())))) {
			this.setPosition(this.getPosition()[0], 
					this.getWorld().getBottomLeftPixelOfTile((int)this.getPosition()[0],
					(int)this.getPosition()[1])[1] - this.getWorld().getTileLength());
		}
		if (this.getHorizontalVelocity() == 0) {
		    this.timeStalled += 1;
			this.timeMovingHorizontally = 0;
		}
		else {
			this.timeStalled = 0;
			this.timeMovingHorizontally += 1;
		}
		for (Plant plant: this.getWorld().getPlants()) {
			if (this.collidesWith(plant)) {
				if (this.getNbHitPoints() <= 500)
					this.changeNbHitPoints(50);
			}
		}
		for (Shark shark: this.getWorld().getSharks()) {
			if ((this.collidesWith(shark)) && (!this.bottomCollidesWithTopOfObject(shark))) {
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
		for (Slime slime: this.getWorld().getSlimes()) {
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
		if (this.isInWater())
			this.changeNbHitPoints((int)(-2 * (dt / (0.2))));
		if (this.isInMagma()) {
			if (!this.isImmuneForMagma()) {
				this.changeNbHitPoints((int)(-50 *((dt + 1) / (0.2))));
				this.makeImmuneForMagma();
			}
			else
				this.timeImmuneForMagma += 1;
		}
	}
}
