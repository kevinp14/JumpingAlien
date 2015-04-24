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
public class Mazub extends GameObject {
	private double normalHorizontalVelocity = 1;
	private double normalHorizontalAcceleration = 0.9;
	private double maxHorizontalVelocity;
	private double maxRunningVelocity = 3;
	private double maxDuckingVelocity = 1;
	private double normalVerticalVelocity = 8;
	private Sprite[] spriteList;
	private double timeStalled;
	private double timeMovingHorizontally;
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
		super(positionX, positionY, spriteList);
		assert (isValidSpriteList(spriteList));
		this.spriteList = spriteList;
	    this.timeMovingHorizontally = 0;
	}

	/**
	 * @return	The current sprite of the Mazub alien.
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
	public double getMaxDuckingVelocity() {
		return this.maxDuckingVelocity;
	}
	
	public boolean isValidJumpingPosition(int[] position) {
		return position[1] < this.getMaxPosition()[1];
	}
	
	/**
	 * Check whether the alien is jumping or not.
	 * 
	 * @return
	 */
	public boolean isJumping() {
		if (this.getVerticalVelocity() > 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	private boolean isAirborne() {
		if (this.getVerticalVelocity() == 0)
			return false;
		else
			return true;
	}
	
	/**
	 * Check whether the alien is ducking or not.
	 * 
	 * @return
	 */
	public boolean isDucking() {
		if (this.getMaxHorizontalVelocity() == this.getMaxDuckingVelocity()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Make the alien begin to jump (move in the positive y-direction).
	 */
	public void startJump() {
		this.setVerticalVelocity(this.normalVerticalVelocity);
		this.setVerticalAcceleration(this.normalVerticalAcceleration);
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
	
	private boolean canEndDuck(){
		if (this.world.isNotPassable(this.world.getGeologicalFeature(this.getPosition()[0],
				this.getPosition()[1] + this.spriteList[0].getHeight()))){
			return false;
		}
		return true;
	}
	
	/**
	 * End the ducking of the alien.
	 * moet nog defensief gemaakt worden
	 */
	public void endDuck() {
		if (this.canEndDuck()){
			this.setMaxHorizontalVelocity(3);
		}
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
		this.setVerticalVelocity(this.getVerticalVelocity() + this.normalVerticalAcceleration*dt);
		double newPositionY = this.getVerticalVelocity() * dt 
				- this.getVerticalAcceleration() * Math.pow(dt, 2)
				+ this.getVerticalAcceleration() * Math.pow(dt, 2)/2;
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
		if (this.getHorizontalVelocity() == 0) {
		    this.timeStalled += 1;
			this.timeMovingHorizontally = 0;
		}
		else {
			this.timeStalled = 0;
			this.timeMovingHorizontally += 1;
		}
		for (Plant plant: this.world.getPlants()) {
			if (this.collidesWith(plant))
				this.changeNbHitPoints(50);
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
		for (Slime slime: this.world.getSlimes()) {
			if ((this.collidesWith(slime)) && (!this.bottomCollidesWithTopOfObject(slime))) {
				this.setHorizontalAcceleration(0);
				this.setHorizontalVelocity(0); /*Is dit goed om beweging te blokkeren? Want in de opgave
				staat: "Properties of the ongoing movement of the colliding game, e.g. direction, velocity 
				and acceleration, may not change directly as a result of the collision."*/
				this.changeNbHitPoints(-50);
			}
		}
		if (this.isInWater())
			this.changeNbHitPoints((int)(-2 * (dt % (20))));
		if (this.isInLava())
			this.changeNbHitPoints((int)(-50 *((dt + 1) % (20))));
	}
}
