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
	private double positionX;
	private double positionY;
	private double maxPositionX = 1023;
	private double maxPositionY = 767;
	private Sprite[] spriteList;
	private double timeStalled;
	private double timeMoving;
	private int hitPoints = 100;
	private World world;
	
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
		super(positionX, positionY);
		assert (isValidSpriteList(spriteList));
		this.spriteList = spriteList;
		this.setMaxHorizontalVelocity(this.maxRunningVelocity);
	    this.timeMoving = 0;
	}

	/**
	 * @return	The current sprite of the Mazub alien.
	 */
	@Basic 
	@Immutable
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
		if (this.getVerticalVelocity() == 0) {
			return false;
		}
		else {
			return true;
		}
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
		if (this.getVerticalVelocity() <= 0){
			int geologicalFeature = this.world.getGeologicalFeature((int)this.positionX, 
					(int)this.positionY);
			if (this.world.isNotPassable(geologicalFeature)){
				this.setVerticalVelocity(0);
				this.setVerticalAcceleration(0);
				this.setPosition(this.getPosition()[0], 
						this.world.getBottomLeftPixelOfTile((int)this.positionX, 
						(int)this.positionY)[1] + this.world.getTileLength());
			}
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
		if (/*botsing*/)
			this.setNbHitPoints(-50);
		if (/*in water*/)
			this.setNbHitPoints((int)(-2 * (dt % (0.2))));
		if (/*in lava*/)
			this.setNbHitPoints((int)(-50 *((dt + 1) % (0.2))));
	}
}
