package jumpingalien.model;

import java.util.ArrayList;

import be.kuleuven.cs.som.annotate.*;
import jumpingalien.util.*;

/**
 * A class of game object involving the horizontal and vertical velocity and acceleration 
 * of the game object and their maximum, the position of the bottom left pixel of the game object and 
 * its maximum, a list of sprites or images of the game object that are displayed depending on its 
 * actions, the time the game object is standing still, is immune for enemies and is immune for magma,
 * the last direction in which the game object was moving and the next direction in which the game object
 * has to move (only applicable for plants), the number of hitpoints, the world in which the game object
 * is, methods to change the number of position, velocities, accelerations, directions and the number of
 * hitpoints, methods to inspect the behavior and characteristics of the game object, methods to inspect
 * what kind of geological feature the game object is in, methods to make the game object immune for 
 * enemies and magma, methods to detect collisions and a method to move the game object horizontally.
 * 
 * @invar	The period of time dt must be valid.
 * 			| isValidDt(dt)
 * @invar	The amount of hitpoints of a game object must be bigger than or equal to 0.
 *			| 0 <= getNbHitpoints()
 * @invar	The given list of sprites may not be null.
 * 			| isValidSpriteList(spriteList)
 * @invar	The direction in which a game object is ordered to move in must be a valid one.
 * 			| isValidMovingDirection(direction)
 *
 * @author	Kevin Peeters (Tweede fase ingenieurswetenschappen)
 * 			Jasper Mariën (Tweede fase ingenieurswetenschappen)
 * @version 10.0
 *
 */
public class GameObject {
		
	private double horizontalVelocity;
	private double horizontalAcceleration;
	private double normalHorizontalVelocity;
	private double normalHorizontalAcceleration;
	private double maxHorizontalVelocity;
	private double maxDuckingVelocity = 1;
	private double maxRunningVelocity = 3;
	private double verticalVelocity;
	private double normalVerticalVelocity;
	private double verticalAcceleration;
	private double normalVerticalAcceleration;
	private double positionX;
	private double positionY;
	private Sprite[] spriteList;
	private double timeStalled;
	private double timeInWater;
	private double timeInAir;
	private double timeInMagma;
	private double timeImmune;
	private double timeBlocked;
	private double timeDead;
	private SelfMadeDirection lastDirection;
	private int hitPoints = 0;
	private boolean isImmune;
	private boolean isImmuneForMagma;
	private World world;
	private Program program;
	protected boolean programRunning;


	/**
	 * Initialize the game object at the given x-position positionX, y-position positionY and with 
	 * the given list of sprites spriteList.
	 * 
	 * @param 	positionX
	 * 			The position in the x-direction where the game object should be.
	 * @param 	positionY
	 * 			The position in the y-direction where the game object should be.
	 * @param 	spriteList
	 * 			The list of sprites displaying how the game object should look depending on its 
	 * 			behaviour.
	 * @effect	The new position is set to the given one.
	 * 			| this.setPosition(positionX, positionY)
	 * @effect	The new program is set to the given one.
	 * 			| this.setProgram(program)
	 * @post	The new sprite list is set to the given one.
	 * 			| (new this).spriteList = spriteList
	 * @effect	The new horizontal velocity is set to 0.
	 * 			| this.setHorizontalVelocity(0)
	 * @effect	The new horizontal acceleration is set to 0.
	 * 			| this.setHorizontalAcceleration(0)
	 * @effect	The new vertical velocity is set to 0.
	 * 			| this.setVerticalVelocity(0)
	 * @effect	The new vertical acceleration is set to 0.
	 * 			| this.setVerticalAcceleration(0)
	 * @effect	The new normal vertical acceleration is set to -10.
	 * 			| this.setNormalVerticalAcceleration(-10)
	 * @effect	The new time stalled is set to 0.
	 * 			| this.setTimeStalled(0)
	 * @effect	The new time in air is set to 0.
	 * 			| this.setTimeInAir(0)
	 * @effect	The new time in water is set to 0.
	 * 			| this.setTimeInWater(0)
	 * @effect	The new time in magma is set to 0.
	 * 			| this.setTimeInMagma(0)
	 * @effect	The new time immune is set to 0.
	 * 			| this.setTimeImmune(0)
	 * @effect	The new time immune for magma is set to 0.
	 * 			| this.setTimeImmuneForMagma(0)
	 * @post	The new time blocked is set to 0.
	 * 			| (new this).timeBlocked = 0
	 * @effect	The new time dead is set to 0.
	 * 			| this.setTimeDead(0)
	 * @effect	The new last direction is set to stalled.
	 * 			| this.setLastDirection(Direction.STALLED)
	 * @post	The game object is made vulnerable.
	 * 			| this.isImmune = false
	 * @post	The game object is made vulnerable for magma.
	 * 			| this.isImmuneForMagma = false
	 * @post	The new world is set to null.
	 * 			| (new this).world = null
	 */
	@Model
	@Raw
	public GameObject(double positionX, double positionY, Sprite[] spriteList, Program program) {
		int[] position = { (int)(positionX/100), (int)(positionY/100) };
		assert (isValidPosition(position));
		assert (isValidSpriteList(spriteList));
		this.setPosition(positionX, positionY);
		this.spriteList = spriteList;
		this.setProgram(program);
		this.setHorizontalVelocity(0);
		this.setHorizontalAcceleration(0);
	    this.setVerticalVelocity(0);
		this.setVerticalAcceleration(0);
		this.setNormalVerticalAcceleration(-10);
	    this.setTimeStalled(0);
	    this.setTimeInAir(0);
	    this.setTimeInWater(0);
	    this.setTimeInMagma(0.2);
	    this.setTimeImmune(0);
		this.timeBlocked = 0;
	    this.setTimeDead(0);
	    this.setLastDirection(SelfMadeDirection.STALLED);
		this.isImmune = false;
		this.isImmuneForMagma = false;
		this.world = null;
		if (this.getProgram() != null){
			this.getProgram().setGameObject(this);
		}
		this.programRunning = false;
	}
	
	/**
	 * @return	The world in which the game object is.
	 * 
	 */
	@Basic
	public World getWorld() {
		return this.world;
	}
	
	/**
	 * Set the world of where the game object has to be to the give world.
	 * 
	 * @param	world
	 * 			The world where the game object has to be intitialized.
	 */
	@Basic
	public void setWorld(World world){
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
		if (this.isMovingLeft()) {
			return spriteList[0];
		}
		else {
			return spriteList[1];
		}
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
	 * Returns the current location of the given game object.
	 * 
	 * @return An array, consisting of 2 doubles {x, y}, that represents the
	 *         coordinates of the given game object's bottom left pixel in the world.
	 */
	@Basic
	public double[] getPositionAsDouble() {
		double[] position = new double[] { this.positionX, this.positionY };
		return position;
	}
	
	/**
	 * Returns the maximum possible position in the game world.
	 * 
	 * @return An array, consisting of 2 integers {x, y}, that represents the
	 *         maximum possible position in the game world.
	 */
	@Basic
	public int[] getMaxPosition() {
		int[] maxPosition = { this.getWorld().getWorldSize()[0] - 1, 
				this.getWorld().getWorldSize()[1] - 1 };
	    return maxPosition;
	}

	/**
	 * Set the position of the game object to the given position in x- an y-direction.
	 * 
	 * @param 	positionX
	 * 			The position in the x-direction of the game object.
	 * @param 	positionY
	 * 			The position in the y-direction of the game object.
	 */
	@Basic
	protected void setPosition(double positionX, double positionY) {
		this.positionX = positionX;
		this.positionY = positionY;
	}

	/**
	 * @return	The program which makes this game object move.
	 * 
	 */
	@Basic
	public Program getProgram() {
		return this.program;
	}
	
	/**
	 * @param	program
	 * 			The program to which this game object's program needs to be set.
	 */
	@Basic
	protected void setProgram(Program program) {
		this.program = program;
	}
	
	/**
	 * @post	The number of hitpoints should be bigger than or equal to 0.
	 * 			| hitPoints >= 0
	 * @return	The current number of hitpoints of the game object.
	 */
	@Basic
	public int getNbHitPoints() {
		return this.hitPoints;
	}

	/**
	 * Add the given hitpoints difference to the current number of hitpoints of the game object if its
	 * hitpoints stay bigger than 0 and smaller than 500 by doing this.
	 * 
	 * @post	The new number of hitpoints should be between 0 and 500. If it exceeds this range it is
	 * 			set to 0 or 500 depending on whether it was smaller than 0 or bigger then 500.
	 * 			| if (newHitPoints < 0) 
	 * 			|	this.hitPoints = 0
	 * 			| else if (newHitPoints > 500) 
	 * 			|	this.hitPoints = 500
	 * @param 	hitPointsDifference
	 * 			The number of hitpoints that should be added to the game object's hitpoints.
	 */
	@Basic
	protected void changeNbHitPoints(int hitPointsDifference) {
		int newHitPoints = this.hitPoints += hitPointsDifference;
		if (!Util.fuzzyGreaterThanOrEqualTo(newHitPoints, 0)) {
			this.hitPoints = 0;
		}
		else if (!Util.fuzzyLessThanOrEqualTo(newHitPoints, 500)) {
			this.hitPoints = 500;
		}
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
	 * 
	 * @param	horizontalVelocity
	 * 			The horizontal velocity for the game object.
	 */
	@Basic
	protected void setHorizontalVelocity(double horizontalVelocity) {
		this.horizontalVelocity = horizontalVelocity;
	}
	
	/**
	 * @return	The normal/initial horizontal velocity.
	 * 
	 */
	@Basic
	public double getNormalHorizontalVelocity() {
		return this.normalHorizontalVelocity;
	}
	
	/**
	 * Set the normal/initial horizontal velocity to the given one.
	 * 
	 * @param 	normalHorizontalVelocity
	 * 			The normal horizontal velocity the game object has to get.
	 */
	@Basic
	protected void setNormalHorizontalVelocity(double normalHorizontalVelocity) {
		this.normalHorizontalVelocity = normalHorizontalVelocity;
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
	 * Set the maximum horizontal velocity to the given one.
	 * 
	 * @param	maxHorizontalVelocity
	 * 			The maximum horizontal velocity the game object has to get.
	 */
	@Basic
	protected void setMaxHorizontalVelocity(double maxHorizontalVelocity) {
		this.maxHorizontalVelocity = maxHorizontalVelocity;
	}

	/**
	 * @return	The maximum of the velocity of the game object while ducking in the x-direction.
	 * 
	 */
	@Basic
	@Immutable
	protected double getMaxDuckingVelocity() {
		return this.maxDuckingVelocity;
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
	 * 
	 * @param	horizontalAcceleration
	 * 			The horizontal acceleration for the game object.
	 * @post	The new horizontal acceleration is set to the given one.
	 * 			| (new this).horizontalAcceleration = horizontalAcceleration
	 */
	@Basic
	protected void setHorizontalAcceleration(double horizontalAcceleration) {
		this.horizontalAcceleration = horizontalAcceleration;
	}
	
	/**
	 * @return	The normal/initial horizontal acceleration.
	 * 
	 */
	@Basic
	public double getNormalHorizontalAcceleration() {
		return this.normalHorizontalAcceleration;
	}
	
	/**
	 * Set the normal/initial horizontal acceleration to the given one.
	 * 
	 * @param 	normalHorizontalAcceleration
	 * 			The normal horizontal acceleration the game object has to get.
	 */
	@Basic
	protected void setNormalHorizontalAcceleration(double normalHorizontalAcceleration) {
		this.normalHorizontalAcceleration = normalHorizontalAcceleration;
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
	 * 
	 * @param	verticalVelocity
	 * 			The vertical velocity for the game object.
	 */
	@Basic
	protected void setVerticalVelocity(double verticalVelocity) {
		this.verticalVelocity = verticalVelocity;
	}
	
	/**
	 * @return	The normal/initial vertical velocity.
	 * 
	 */
	@Basic
	protected double getNormalVerticalVelocity() {
		return this.normalVerticalVelocity;
	}
	
	/**
	 * Set the normal/initial vertical velocity to the given one.
	 * 
	 * @param 	normalVerticalVelocity
	 * 			The normal vertical velocity the game object has to get.
	 */
	@Basic
	protected void setNormalVerticalVelocity(double normalVerticalVelocity) {
		this.normalVerticalVelocity = normalVerticalVelocity;
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
	 */
	@Basic
	protected void setVerticalAcceleration(double verticalAcceleration) {
		this.verticalAcceleration = verticalAcceleration;
	}
	
	/**
	 * @return	The normal/initial vertical acceleration.
	 * 
	 */
	@Basic
	protected double getNormalVerticalAcceleration() {
		return this.normalVerticalAcceleration;
	}
	
	/**
	 * Set the normal/initial horizontal acceleration to the given one.
	 * 
	 * @param 	normalVerticalAcceleration
	 * 			The normal vertical acceleration the game object has to get.
	 */
	@Basic
	protected void setNormalVerticalAcceleration(double normalVerticalAcceleration) {
		this.normalVerticalAcceleration = normalVerticalAcceleration;
	}
	
	/**
	 * @return The last direction in which the game object has moved.
	 * 
	 */
	protected SelfMadeDirection getLastDirection() {
		return this.lastDirection;
	}
	
	/**
	 * Set the last direction in which game object has moved to the given direction.
	 * 
	 * @param 	direction
	 * 			The direction to which the last direction has to be set.
	 */
	protected void setLastDirection(SelfMadeDirection direction){
		this.lastDirection = direction;
	}
	
	/**
	 * @return	A list of x-pixels of the game object. Used to check the left or right perimeter/side of
	 * 			pixels of a game object.
	 * 
	 */
	protected ArrayList<Integer> getHorizontalPixels() {
		double width = this.getCurrentSprite().getWidth();
		ArrayList<Integer> horizontalPixels = new ArrayList<Integer>();
		for (int x=0; x < width; x++) {
			horizontalPixels.add(this.getPosition()[0] + x);
		}
		return horizontalPixels;
	}
	
	/**
	 * @return	A list of y-pixels of the game object. Used to check the top or bottom perimeter/side of
	 * 			pixels of a game object.
	 * 
	 */
	protected ArrayList<Integer> getVerticalPixels() {
		double height = this.getCurrentSprite().getHeight();
		ArrayList<Integer> verticalPixels = new ArrayList<Integer>();
		for (int y=0; y < height; y++) {
			verticalPixels.add(this.getPosition()[1] + y);
		}
		return verticalPixels;
	}
	
	/**
	 * @return	The time the game object is stalled.
	 * 
	 */
	protected double getTimeStalled() {
		return this.timeStalled;
	}
	
	/**
	 * Set the time stalled to the given one.
	 * 
	 * @param	timeStalled
	 * 			The time the game object has to be stalled.
	 */
	protected void setTimeStalled(double timeStalled) {
		this.timeStalled = timeStalled;
	}
	
	/**
	 * @return	The time the game object is in water.
	 * 
	 */
	protected double getTimeInWater() {
		return this.timeInWater;
	}
	
	/**
	 * Set the time the game object is in water to the given one.
	 * 
	 * @param	timeInWater
	 * 			The time the game object has to be in water.
	 */
	protected void setTimeInWater(double timeInWater) {
		this.timeInWater = timeInWater;
	}
	
	/**
	 * @return	The time the game object is in air.
	 * 
	 */
	protected double getTimeInAir() {
		return this.timeInAir;
	}
	
	/**
	 * Set the time the game object is in air to the given one.
	 * 
	 * @param	timeInAir
	 * 			The time the game object has to be in air.
	 */
	protected void setTimeInAir(double timeInAir) {
		this.timeInAir = timeInAir;
	}
	
	/**
	 * @return	The time the game object is in magma.
	 * 
	 */
	protected double getTimeInMagma() {
		return this.timeInMagma;
	}
	
	/**
	 * Set the time the game object is in magma to the given one.
	 * 
	 * @param	timeInMagma
	 * 			The time the game object has to be in magma.
	 */
	protected void setTimeInMagma(double timeInMagma) {
		this.timeInMagma = timeInMagma;
	}
	
	/**
	 * @return	The time the game object is immune.
	 * 
	 */
	protected double getTimeImmune() {
		return this.timeImmune;
	}
	
	/**
	 * Set the time the game object is immune to the given one.
	 * 
	 * @param	timeImmune
	 * 			The time the game object has to be immune.
	 */
	protected void setTimeImmune(double timeImmune) {
		this.timeImmune = timeImmune;
	}
	
	/**
	 * @return	The time the game object is dead.
	 * 
	 */
	protected double getTimeDead() {
		return this.timeDead;
	}
	
	/**
	 * Set the time the game object is dead to the given one.
	 * 
	 * @param	timeDead
	 * 			The time the game object has to be dead.
	 */
	protected void setTimeDead(double timeDead) {
		this.timeDead = timeDead;
	}
	
	/**
	 * Check whether the given direction is a valid one to move in.
	 * 
	 * @param 	direction
	 * 			The direction which has to be checked.
	 * @return	True if and only if the direction is right, left.
	 */
	protected boolean isValidMovingDirection(SelfMadeDirection direction) {
		return ((direction == SelfMadeDirection.RIGHT) 
				|| (direction == SelfMadeDirection.LEFT));
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
	 * Check whether the alien can jump at the given position or not.
	 * 
	 * @param	position
	 * @return	True if and only if the tile beneath the alien is not passable.
	 */
	public boolean isValidJumpingPosition(int[] position) {
		if (this.getWorld().getSharks().contains(this)) {
			return (!this.isFalling());
		}
		else if ((this.getWorld().getMazub() == this) || (this.getWorld().getBuzam() == this)) {
			for (int horizontalPixel: this.getHorizontalPixels()) {
				if (this.getWorld().isNotPassable(this.getWorld().getGeologicalFeature(horizontalPixel, 
						position[1]))) {
					return true;
				}
			}
		}
		return false;
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
		return (!Util.fuzzyLessThanOrEqualTo(dt, 0)) && (Util.fuzzyLessThanOrEqualTo(dt, 0.2));
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
		return (Util.fuzzyLessThanOrEqualTo(this.getTimeStalled(), 0.30));
	}

	/**
	 * Check whether the game object is moving or has just moved to the left (negative x-direction) 
	 * or not.
	 * 
	 * @return	True if and only if the last direction of the game object is left.
	 */
	public boolean isMovingLeft(){
		return (this.getLastDirection() == SelfMadeDirection.LEFT);
	}
	
	/**
	 * Check whether the game object is moving or has just moved to the right (negative x-direction) 
	 * or not.
	 * 
	 * @return	True if and only if the last direction of the game object is right.
	 */
	protected boolean isMovingRight() {
		return (this.getLastDirection() == SelfMadeDirection.RIGHT);
	}
	
	/**
	 * Check whether the game object is falling or not.
	 * 
	 * @return	True if and only if the vertical velocity of the game object is negative and the 
	 * 			vertical acceleration of the game object is -10.
	 */
	public boolean isFalling() {
		return ((!Util.fuzzyGreaterThanOrEqualTo(this.getVerticalVelocity(), 0)) 
				&& (Util.fuzzyEquals(this.getVerticalAcceleration(), -10)));
	}
	
	/**
	 * Check whether the game object is jumping or not.
	 * 
	 * @return	True if and only if the vertical velocity of the game object is positive and the 
	 * 			vertical acceleration of the game object is -10.
	 */
	public boolean isJumping() {
		return ((!Util.fuzzyLessThanOrEqualTo(this.getVerticalVelocity(), 0)) 
				&& (Util.fuzzyEquals(this.getVerticalAcceleration(), -10)));
	}
	
	/**
	 * Check whether the game object is ducking or not.
	 * 
	 * @return	True if and only if the game object's maximum horizontal velocity is equal to the 
	 * 			maximum ducking velocity.
	 */
	public boolean isDucking() {
		return (Util.fuzzyEquals(this.getMaxHorizontalVelocity(), this.getMaxDuckingVelocity()));
	}
	
	/**
	 * @return	True if and only if the game object's vertical velocity is bigger than 0 and its vertical
	 * 			acceleration is not -10.
	 */
	protected boolean isRising() {
		return ((!Util.fuzzyLessThanOrEqualTo(this.getVerticalVelocity(), 0)) 
				&& (!Util.fuzzyEquals(this.getVerticalAcceleration(), -10)));
	}
	
	/**
	 * @return	True if and only if the game object's vertical velocity is smaller than 0 and its vertical
	 * 			acceleration is not -10.
	 */
	protected boolean isDiving() {
		return ((!Util.fuzzyGreaterThanOrEqualTo(this.getVerticalVelocity(), 0))
				&& (!Util.fuzzyEquals(this.getVerticalAcceleration(), -10)));
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
	 * @return	True if and only if the geological feature at the game object's tile is 0 (air).
	 */
	protected boolean isInAir() {
		for (int verticalPixel: this.getVerticalPixels()) {
			if ((this.getWorld().getGeologicalFeature(this.getPosition()[0], verticalPixel) == 0) ||
					this.getWorld().getGeologicalFeature
					(this.getPosition()[0] + this.getCurrentSprite().getWidth(), verticalPixel) == 0) {
				return true;
			}
		}
		for (int horizontalPixel: this.getHorizontalPixels()) {
			if ((this.getWorld().getGeologicalFeature(horizontalPixel, this.getPosition()[1]) == 0) ||
					(this.getWorld().getGeologicalFeature(horizontalPixel, 
							this.getPosition()[1] + this.getCurrentSprite().getHeight()) == 0)) {
				return true;
			}
		}
		return false;	
	}
	
	/**
	 * Check whether the game object is in water or not.
	 * 
	 * @return	True if and only if the geological feature at the game object's tile is 2 (water).
	 */
	protected boolean isInWater() {
		for (int verticalPixel: this.getVerticalPixels()) {
			if ((this.getWorld().getGeologicalFeature(this.getPosition()[0], verticalPixel) == 2) ||
					this.getWorld().getGeologicalFeature
					(this.getPosition()[0] + this.getCurrentSprite().getWidth(), verticalPixel) == 2) {
				return true;
			}
		}
		for (int horizontalPixel: this.getHorizontalPixels()) {
			if ((this.getWorld().getGeologicalFeature(horizontalPixel, this.getPosition()[1]) == 2) ||
					(this.getWorld().getGeologicalFeature(horizontalPixel, 
							this.getPosition()[1] + this.getCurrentSprite().getHeight()) == 2)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Check whether the game object is in magma or not.
	 * 
	 * @return	True if and only if the geological feature at the game object's tile is 3 (magma).
	 */
	protected boolean isInMagma() {
		for (int verticalPixel: this.getVerticalPixels()) {
			if ((this.getWorld().getGeologicalFeature(this.getPosition()[0], verticalPixel) == 3) ||
					this.getWorld().getGeologicalFeature
					(this.getPosition()[0] + this.getCurrentSprite().getWidth(), verticalPixel) == 3) {
				return true;
			}
		}
		for (int horizontalPixel: this.getHorizontalPixels()) {
			if ((this.getWorld().getGeologicalFeature(horizontalPixel, this.getPosition()[1]) == 3) ||
					(this.getWorld().getGeologicalFeature(horizontalPixel, 
							this.getPosition()[1] + this.getCurrentSprite().getHeight()) == 3)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @return	True if and only if this game object's number of hitpoints equals 0 or if its 
	 * 			position is not valid.
	 * 
	 */
	public boolean isDead() {
		return ((this.getNbHitPoints() == 0) || (!this.isValidPosition(this.getPosition())));
	}
	
	/**
	 * Makes the game object immune for enemies.
	 * 
	 */
	protected void makeImmune() {
		this.isImmune = true;
	}
	
	/**
	 * Makes the game object vulnerable for enemies.
	 * 
	 */
	protected void makeVulnerable() {
		this.isImmune = false;
	}
	
	/**
	 * Makes the game object immune for magma.
	 * 
	 */
	protected void makeImmuneForMagma() {
		this.isImmuneForMagma = true;
	}
	
	/**
	 * Makes the game object vulnerable for magma.
	 * 
	 */
	protected void makeVulnerableForMagma() {
		this.isImmuneForMagma = false;
	}
	
	/**
	 * Check whether the game object collides with the given object.
	 * 
	 * @param 	object
	 * 			The object with which the game object could collide.
	 * @return	True if and only if no sides of the game object's tile collide with a side of the 
	 * 			given object's tile.
	 */
	protected boolean collidesWith(GameObject object){
		return (!(((this.getPosition()[0] + this.getCurrentSprite().getWidth() - 1) 
						< object.getPosition()[0])
				|| ((object.getPosition()[0] + object.getCurrentSprite().getWidth() - 1) 
						< this.getPosition()[0]) 
				|| ((this.getPosition()[1] + this.getCurrentSprite().getHeight() - 1) 
						< object.getPosition()[1])
				|| ((object.getPosition()[1] + object.getCurrentSprite().getHeight() - 1) 
						< this.getPosition()[1])));
	}
	
	/**
	 * Check whether the left side of the game object's tile collides with the right side of the given
	 * object's tile.
	 *  
	 * @param 	object
	 * 			The object with which the game object could collide.
	 * @return	True if and only if the game object collides with the given object and the left 
	 * 			perimeter of the game object equals the right perimeter of the given object - 1.
	 */
	protected boolean leftCollidesWith(GameObject object) {
		return ((this.collidesWith(object)) && (this.getPosition()[0] == (object.getPosition()[0]
				+ object.getCurrentSprite().getWidth() - 1)));
	}
	
	/**
	 * Check whether the right side of the game object's tile collides with the left side of the given
	 * object's tile.
	 *  
	 * @param 	object
	 * 			The object with which the game object could collide.
	 * @return	True if and only if the game object collides with the given object and the right 
	 * 			perimeter of the given object equals the left perimeter of the game object - 1.
	 */
	protected boolean rightCollidesWith(GameObject object) {
		return ((this.collidesWith(object)) && (object.getPosition()[1] == (this.getPosition()[1] 
				+ this.getCurrentSprite().getWidth() - 1)));
	}
	
	/**
	 * Check whether the bottom side of the game object's tile collides with the top side of the given
	 * object's tile.
	 *  
	 * @param 	object
	 * 			The object with which the game object could collide.
	 * @return	True if and only if the game object collides with the object and the bottom perimeter of 
	 * 			the game object equals the top perimeter of the given object - 1.
	 */
	protected boolean bottomCollidesWith(GameObject object) {
		return ((this.collidesWith(object)) && (this.getPosition()[1] == (object.getPosition()[1] 
				+ object.getCurrentSprite().getHeight() - 1)));
	}
	
	/**
	 * Check whether the top side of the game object's tile collides with the bottom side of the given
	 * object's tile.
	 *  
	 * @param 	object
	 * 			The object with which the game object could collide.
	 * @return	True if and only if the game object collides with the object and the bottom perimeter of 
	 * 			the given object equals the top perimeter of the game object - 1.
	 */
	protected boolean topCollidesWith(GameObject object) {
		return ((this.collidesWith(object)) && (object.getPosition()[1] == (this.getPosition()[1] 
				+ this.getCurrentSprite().getHeight() - 1)));
	}
	
	/**
	 * Checks whether the game object is touching an impassable tile to right or not.
	 * 
	 * @return	True if and only if the game object's right perimeter is impassable.
	 */
	protected boolean touchImpassableRight() {
		for (int rightPixel: this.getVerticalPixels()) {
			if ((this.getWorld().isNotPassable(this.getWorld().getGeologicalFeature(
					this.getPosition()[0] + this.getCurrentSprite().getWidth(), rightPixel)))
					&& (rightPixel != this.getPosition()[1])
					&& (rightPixel != this.getPosition()[1] + 1)
					&& (rightPixel != this.getPosition()[1] + this.getCurrentSprite().getHeight() - 1)
					&& (rightPixel != this.getPosition()[1] + this.getCurrentSprite().getHeight())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks whether the game object is touching an impassable tile to top or not.
	 * 
	 * @return	True if and only if the game object's top perimeter is impassable.
	 */
	protected boolean touchImpassableTop() {
		for (int topPixel: this.getHorizontalPixels()) {
			if (this.getWorld().isNotPassable(this.getWorld().getGeologicalFeature(topPixel, 
					this.getPosition()[1] + this.getCurrentSprite().getHeight()))
					&& (topPixel != this.getPosition()[0])
					&& (topPixel != this.getPosition()[0] + 1)
					&& (topPixel != this.getPosition()[0] + this.getCurrentSprite().getWidth() - 1)
					&& (topPixel != this.getPosition()[0] + this.getCurrentSprite().getWidth())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks whether the game object is touching an impassable tile to left or not.
	 * 
	 * @return	True if and only if the game object's left perimeter is impassable.
	 */
	protected boolean touchImpassableLeft() {
		for (int leftPixel: this.getVerticalPixels()) {
			if ((this.getWorld().isNotPassable(this.getWorld().getGeologicalFeature(
					this.getPosition()[0], leftPixel))) && (leftPixel != this.getPosition()[1])
					&& (leftPixel != this.getPosition()[1] + 1)
					&& (leftPixel != this.getPosition()[1] + this.getCurrentSprite().getHeight() - 1)
					&& (leftPixel != this.getPosition()[1] + this.getCurrentSprite().getHeight())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks whether the game object is touching an impassable tile to bottom or not.
	 * 
	 * @return	True if and only if the game object's bottom perimeter is impassable.
	 */
	protected boolean touchImpassableBottom() {
		for (int bottomPixel: this.getHorizontalPixels()) {
			if (this.getWorld().isNotPassable(this.getWorld().getGeologicalFeature(bottomPixel, 
					this.getPosition()[1])) && (bottomPixel != this.getPosition()[0])
					&& (bottomPixel != this.getPosition()[0] + 1)
					&& (bottomPixel != this.getPosition()[0] + this.getCurrentSprite().getWidth() - 1)
					&& (bottomPixel != this.getPosition()[0] + this.getCurrentSprite().getWidth())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks whether the game object is trying to cross an impassable tile to the right or not.
	 * 
	 * @return	True if and only if the game object is touching an impassable tile to the right and
	 * 			its horizontal velocity is bigger than 0.
	 */
	protected boolean crossImpassableRight() {
		return ((this.touchImpassableRight()) 
				&& (!Util.fuzzyLessThanOrEqualTo(this.getHorizontalVelocity(), 0)));
	}
	
	/**
	 * Checks whether the game object is trying to cross an impassable tile to the top or not.
	 * 
	 * @return	True if and only if the game object is toucing an impassable tile to the top and
	 * 			its vertical velocity is bigger than 0.
	 */
	protected boolean crossImpassableTop() {
		return ((this.touchImpassableTop()) 
				&& (!Util.fuzzyLessThanOrEqualTo(this.getVerticalVelocity(), 0)));
	}
	
	/**
	 * Checks whether the game object is trying to cross an impassable tile to the left or not.
	 * 
	 * @return	True if and only if the game object is toucing an impassable tile to the left and
	 * 			its horizontal velocity is smaller than 0.
	 */
	protected boolean crossImpassableLeft() {
		return ((this.touchImpassableLeft()) 
				&& (!Util.fuzzyGreaterThanOrEqualTo(this.getHorizontalVelocity(), 0)));
	}
	
	/**
	 * Checks whether the game object is trying to cross an impassable tile to the bottom or not.
	 * 
	 * @return	True if and only if the game object is toucing an impassable tile to the bottom and
	 * 			its vertical velocity is smaller than 0.
	 */
	protected boolean crossImpassableBottom() {
		return ((this.touchImpassableBottom()) 
				&& (!Util.fuzzyGreaterThanOrEqualTo(this.getVerticalVelocity(), 0)));
	}
	
	/**
	 * Make the game object begin to move in the x-direction to the left (negative x-direction) 
	 * or to the right (positive x-direction) depending on the given direction. Also limit the velocity 
	 * in the x-direction to its maximum.
	 * 
	 * @param	direction
	 * 			The given direction in which the game object has to move.
	 * @pre	The given direction should be valid.
	 * 		| isValidMovingDirection(direction)
	 * @post	The new last direction should be valid.
	 * 			| isValidMovingDirection((new this).getLastDirection())
	 */
	public void startMoveHorizontally(SelfMadeDirection direction, double initalVelocity, 
			double initialAcceleration) {
		assert (isValidMovingDirection(direction));
		if (direction == SelfMadeDirection.RIGHT) {
			this.setLastDirection(SelfMadeDirection.RIGHT);
			if (Util.fuzzyLessThanOrEqualTo(this.getHorizontalVelocity(), 
					this.getMaxHorizontalVelocity())) {
				this.setHorizontalVelocity(initalVelocity);
				this.setHorizontalAcceleration(initialAcceleration);
			}
			else {
				this.setHorizontalAcceleration(0);
				this.setHorizontalVelocity(this.getMaxHorizontalVelocity());
			}
		}
		else {
			this.setLastDirection(SelfMadeDirection.LEFT);
			if (Util.fuzzyGreaterThanOrEqualTo(this.getHorizontalVelocity(),
					(-this.getMaxHorizontalVelocity()))) {
				this.setHorizontalVelocity(-initalVelocity);
				this.setHorizontalAcceleration(-initialAcceleration);
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
	 * @pre	The given direction should be valid.
	 * 		| isValidMovingDirection(direction)
	 */
	public void endMoveHorizontally(SelfMadeDirection direction) {
		assert (isValidMovingDirection(direction));
		if ((direction == SelfMadeDirection.RIGHT) 
				&& (!Util.fuzzyLessThanOrEqualTo(this.getHorizontalVelocity(), 0))) {
			this.setHorizontalAcceleration(0);
			this.setHorizontalVelocity(0);
		}
		if ((direction == SelfMadeDirection.LEFT) 
				&& (!Util.fuzzyGreaterThanOrEqualTo(this.getHorizontalVelocity(), 0))) {
			this.setHorizontalAcceleration(0);
			this.setHorizontalVelocity(0);
		}
	}
	
	/**
	 * Make the game object begin to jump (move in the positive y-direction).
	 * 
	 * @effect	The new vertical velocity and acceleration are set to the normal ones.
	 * 			| this.setVerticalVelocity(this.normalVerticalVelocity)
	 * 			| this.setVerticalAcceleration(this.getNormalVerticalAcceleration())
	 */ //TODO: defensief, misschien zelf exception maken
	public void startJump() {
		if (this.isValidJumpingPosition(this.getPosition())) {
			this.setVerticalVelocity(this.normalVerticalVelocity);
			this.setVerticalAcceleration(this.getNormalVerticalAcceleration());
		}
	}
	
	/**
	 * End the jumping of the game object.
	 * 
	 * @effect	If the current vertical velocity is bigger than 0, the new vertical velocity is set to
	 * 			0.
	 * 			| if (this.getVerticalVelocity() > 0)
	 * 			|	this.setVerticalVelocity(0)
	 */ //TODO: defensief, misschien zelf exception maken
	public void endJump() {
		if (!Util.fuzzyLessThanOrEqualTo(this.getVerticalVelocity(), 0)) {
			this.setVerticalVelocity(0);
		}
	}
	
	/**
	 * Make the game object begin to duck (shrink in the y-direction).
	 * 
	 * @effect	The new horizontal acceleration is set to 0 and the new maximum horizontal velocity is 
	 * 			set to the maximum ducking velocity.
	 * 			| this.setHorizontalAcceleration(0)
	 * 			| this.setMaxHorizontalVelocity(this.getMaxDuckingVelocity())
	 */ //TODO: defensief, misschien zelf exception maken
	public void startDuck() {
		this.setHorizontalAcceleration(0);
		this.setMaxHorizontalVelocity(this.getMaxDuckingVelocity());
	}
	
	/**
	 * End the ducking of the game object.
	 * 
	 * @effect	The new maximum horizontal velocity is set to the maximum running velocity.
	 * 			| this.setMaxHorizontalVelocity(this.maxRunningVelocity)
	 */ //TODO: defensief, misschien zelf exception maken
	public void endDuck() {
		this.setMaxHorizontalVelocity(this.maxRunningVelocity);
		if (this.isMovingHorizontally()) {
			if (this.isMovingLeft()) {
				this.setHorizontalAcceleration(-this.getNormalHorizontalAcceleration());
			}
			else if (this.isMovingRight()) {
				this.setHorizontalAcceleration(this.getNormalHorizontalAcceleration());
			}
		}
	}
	
	/**
	 * The actions the game object has to take when trying to cross an impassable tile.
	 * 
	 * @param oldPosition
	 * @effect	If the game object is trying to cross an impassable tile to the left or right its horizontal
	 * 			acceleration and velocity are set to 0 and its position is set back to its old 
	 * 			position.
	 * 			| if ((this.crossImpassableLeft()) || (this.crossImpassableRight())) 
	 * 			|	this.setHorizontalAcceleration(0)
	 * 			|	this.setHorizontalVelocity(0)
	 * 			|	this.setPosition(oldPosition[0], oldPosition[1])
	 * @effect	If the game object is trying to cross an impassable tile to the bottom its vertical
	 * 			acceleration and velocity are set to 0 and its position is set back to its old 
	 * 			position.
	 * 			| if (this.crossImpassableBottom()) 
	 * 			|	this.setVerticalAcceleration(0)
	 * 			|	this.setVerticalVelocity(0)
	 * 			|	this.setPosition(oldPosition[0], oldPosition[1])
	 * @effect	If the game object is trying to cross an impassable tile to the top its vertical
	 * 			acceleration is set to the normal vertical acceleration and its velocity is set to 0,
	 * 			and its position is set back to its old position.
	 * 			| if (this.crossImpassableBottom()) 
	 * 			|	this.setVerticalAcceleration(this.getNormalVerticalAcceleration())
	 * 			|	this.setVerticalVelocity(0)
	 * 			|	this.setPosition(oldPosition[0], oldPosition[1])
	 */
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
		if (this.crossImpassableTop()) {
			this.setVerticalAcceleration(this.getNormalVerticalAcceleration());
			this.setVerticalVelocity(0);
			this.setPosition(oldPosition[0], oldPosition[1]);
		}
	}
	
	/**
	 * Block the movement of this game object if it collides with another one.
	 * 
	 * @param	object
	 * 			The other game object with wich this game object is colliding.
	 * @param	oldPosition
	 * 			The old position of this game object.
	 * @effect	If this game object's top collides with another game object, its vertical velocity is
	 * 			set to 0 and it is returned to its old position.
	 * 			| if (this.topCollidesWith(object))
	 * 			|	this.setVerticalVelocity(0)
	 * 			|	this.setPosition(oldPosition[0], oldPosition[1])
	 * @effect	If this game object's bottom collides with another game object, its vertical velocity 
	 * 			and acceleration are set to 0 and it is returned to its old position.
	 * 			| if (this.bottomCollidesWith(object))
	 * 			|	this.setVerticalAcceleration(0)
	 * 			|	this.setVerticalVelocity(0)
	 * 			|	this.setPosition(oldPosition[0], oldPosition[1])
	 * @effect	If this game object's left side collides with another game object and it is trying
	 * 			to move to the left, its horizontal velocity and acceleration are set to 0 and it 
	 * 			is returned to its old position.
	 * 			| if ((this.leftCollidesWith(object)) && (this.getLastDirection() == Direction.LEFT))
	 * 			|	this.setHorizontalAcceleration(0)
	 * 			|	this.setHorizontalVelocity(0)
	 * 			|	this.setPosition(oldPosition[0], oldPosition[1])
	 * @effect	If this game object's right side collides with another game object and it is trying
	 * 			to move to the right, its horizontal velocity and acceleration are set to 0 and it 
	 * 			is returned to its old position.
	 * 			| if ((this.rightCollidesWith(object)) && (this.getLastDirection() == Direction.RIGHT))
	 * 			|	this.setHorizontalAcceleration(0)
	 * 			|	this.setHorizontalVelocity(0)
	 * 			|	this.setPosition(oldPosition[0], oldPosition[1])
	 */
	protected void collisionBlockMovement(GameObject object, int[] oldPosition, double dt) {
		if (this.topCollidesWith(object)) {
			if (!Util.fuzzyGreaterThanOrEqualTo(this.timeBlocked, 0.6)) {
				this.timeBlocked += dt;
				this.setVerticalVelocity(0);
				this.setPosition(oldPosition[0], oldPosition[1]);
			}
			else {
				this.timeBlocked = 0;
			}
		}
		if ((this.bottomCollidesWith(object)) && ((this.isFalling()) || (this.isDiving()))) { 
			if (!Util.fuzzyGreaterThanOrEqualTo(this.timeBlocked, 0.6)) {
				this.timeBlocked += dt;
				this.setVerticalAcceleration(0);
				this.setVerticalVelocity(0);
				this.setPosition(oldPosition[0], oldPosition[1]);
			}
			else {
				this.timeBlocked = 0;
			}
		}
		if ((this.leftCollidesWith(object)) && (this.getHorizontalAcceleration() < 0)) {
			if (!Util.fuzzyGreaterThanOrEqualTo(this.timeBlocked, 0.6)) {
				this.timeBlocked += dt;
				this.setHorizontalAcceleration(0);
				this.setHorizontalVelocity(0);
				this.setPosition(oldPosition[0], oldPosition[1]);
			}
			else {
				this.timeBlocked = 0;
			}
		}
		if ((this.rightCollidesWith(object)) && (this.getHorizontalAcceleration() > 0)) {
			if (!Util.fuzzyGreaterThanOrEqualTo(this.timeBlocked, 0.6)) {
				this.timeBlocked += dt;
				this.setHorizontalAcceleration(0);
				this.setHorizontalVelocity(0);
				this.setPosition(oldPosition[0], oldPosition[1]);
			}
			else {
				this.timeBlocked = 0;
			}
		}
	}
}
