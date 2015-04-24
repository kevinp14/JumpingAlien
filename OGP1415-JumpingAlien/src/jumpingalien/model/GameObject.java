package jumpingalien.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import jumpingalien.util.*;

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
	private Direction lastDirection;
	private Direction nextDirection;
	private int hitPoints;
	private boolean isImmune;
	protected World world;

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
	    this.lastDirection = Direction.STALLED;
	    this.nextDirection = Direction.STALLED;
		this.isImmune = false;
		this.world = null;
	}
	
	public void setWorld(World world){
		this.world = world;
	}
	
	public Sprite getCurrentSprite() {
		if (this.isMovingLeft())
			return spriteList[0];
		else
			return spriteList[1];
	}

	public int[] getPosition() {
		int[] position = new int[]{ (int)this.positionX, (int)this.positionY };
		return position;
	}

	protected void setPosition(double positionX, double positionY) {
		this.positionX = positionX;
		this.positionY = positionY;
	}
	
	protected int[] getMaxPosition() {
		int[] maxPosition = { (int)this.maxPositionX, (int)this.maxPositionY };
	    return maxPosition;
	}

	public int getNbHitPoints() {
		return this.hitPoints;
	}

	protected void changeNbHitPoints(int hitPointsDifference) {
		this.hitPoints += hitPointsDifference;
	}
	
	public double getHorizontalVelocity() {
		return this.horizontalVelocity;
	}

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

	public double getHorizontalAcceleration() {
		return this.horizontalAcceleration;
	}

	public void setHorizontalAcceleration(double horizontalAcceleration) {
		this.horizontalAcceleration = horizontalAcceleration;
	}

	public double getMaxHorizontalVelocity() {
		return this.maxHorizontalVelocity;
	}

	public double getVerticalVelocity() {
		return this.verticalVelocity;
	}
	
	protected void setVerticalVelocity(double verticalVelocity) {
		this.verticalVelocity = verticalVelocity;
	}
	
	public double getVerticalAcceleration() {
		return this.verticalAcceleration;
	}

	protected void setVerticalAcceleration(double verticalAcceleration) {
		this.verticalAcceleration = verticalAcceleration;
	}
	
	protected Direction getLastDirection() {
		return lastDirection;
	}
	
	protected void setLastDirection(Direction direction){
		this.lastDirection = direction;
	}
	
	protected Direction getNextDirection() {
		return nextDirection;
	}
	
	protected void setNextDirection(Direction direction){
		this.nextDirection = direction;
	}

	protected boolean isValidMovingDirection(Direction direction) {
		return (direction == Direction.RIGHT) || (direction == Direction.LEFT);
	}

	protected boolean isValidPosition(int[] position) {
		return (position[0] <= this.getMaxPosition()[0]) && (position[0] >= 0) && 
				(position[1] <= this.getMaxPosition()[1]) && (position[1] >= 0);
	}

	public boolean isValidSpriteList(Sprite[] spriteList) {
		return (spriteList != null);
	}

	protected boolean isValidDt(double dt) {
		return (dt > 0) && (dt < 0.2);
	}

	protected boolean isValidNbHitpoints(int hitpoints) {
		return (hitpoints >= 0);
	}
	
	public boolean isMovingHorizontally() {
		if (this.getHorizontalVelocity() != 0){
			return true;
		}
		else {
			return false;
		}
	}

	public boolean hasJustMovedHorizontally() {
		if (this.timeStalled > 30){
			return false;
		}
		else{
			return true;
		}
	}

	public boolean isMovingLeft(){
		if (this.getLastDirection() == Direction.LEFT){
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean isMovingRight() {
		if (this.getLastDirection() == Direction.RIGHT)
			return true;
		else 
			return false;
	}
	
	protected boolean isFalling() {
		if ((this.getVerticalVelocity() < 0) && (this.getVerticalAcceleration() == -10))
			return true;
		else
			return false;
	}
	
	protected boolean isImmune(){
		if (this.isImmune){
			return true;
		}
		else{
			return false;
		}
	}
	
	protected boolean isInAir() {
		if (this.world.getGeologicalFeature(this.getPosition()[0], this.getPosition()[1]) == 0)
			return true;
		else
			return false;
	}
	
	protected boolean isInWater(){
		if (this.world.getGeologicalFeature(this.getPosition()[0], this.getPosition()[1]) == 2){
			return true;
		}
		else{
			return false;
		}
	}
	
	protected boolean isInLava(){
		if (this.world.getGeologicalFeature(this.getPosition()[0], this.getPosition()[1]) == 3){
			return true;
		}
		else{
			return false;
		}
	}
	
	protected void makeImmune() {
		if (this.timeImmune <= 60) 
			this.isImmune = true;
		else {
			this.isImmune = false;
			this.timeImmune = 0;
		}
	}
	
	protected boolean collidesWith(GameObject object){
		if ((this.getPosition()[0] + (this.getCurrentSprite().getWidth() - 1) < object.getPosition()[0])
				|| (object.getPosition()[0] + (object.getCurrentSprite().getWidth() - 1) 
						< this.getPosition()[0]) 
				|| (this.getPosition()[1] + (this.getCurrentSprite().getHeight() - 1) 
						< object.getPosition()[1])
				|| (object.getPosition()[1] + (object.getCurrentSprite().getHeight() - 1) 
						< this.getPosition()[1]))
			return false;
		else
			return true;
	}
	
	protected boolean bottomCollidesWithTopOfObject(GameObject object) {
		if ((this.collidesWith(object)) && (this.getPosition()[1] == (object.getPosition()[1] - 1)))
			return true;
		else
			return false;
	}
	
	public void startMoveHorizontally(Direction direction) {
		assert (isValidMovingDirection(direction));
		if (direction == Direction.RIGHT){
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