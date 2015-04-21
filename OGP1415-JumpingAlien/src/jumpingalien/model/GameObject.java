package jumpingalien.model;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import jumpingalien.util.*;

public abstract class GameObject {
		
	private double horizontalVelocity;
	private double horizontalAcceleration;
	private double normalHorizontalVelocity;
	private double normalHorizontalAcceleration;
	private double maxHorizontalVelocity;
	private double verticalVelocity;
	private double verticalAcceleration;
	private double positionX;
	private double positionY;
	private double maxPositionX = 1023;
	private double maxPositionY = 767;
	private double timeStalled;
	private Direction lastDirection;
	private int hitPoints;
	private boolean isImmune;
	private World world;

	public GameObject(double positionX, double positionY) {
		int[] position = { (int)(positionX/100), (int)(positionY/100) };
		assert (isValidPosition(position));
		this.setPosition(positionX, positionY);
		this.setHorizontalVelocity(0);
		this.setHorizontalAcceleration(0);
	    this.setVerticalVelocity(0);
		this.setVerticalAcceleration(0);
	    this.timeStalled = 0;
	    this.lastDirection = Direction.STALLED;
		this.isImmune = false;
		this.world = null;
	}
	
	public void setWorld(World world){
		this.world = world;
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

	protected void setNbHitPoints(int hitPointsDifference) {
		this.hitPoints += hitPointsDifference;
	}

	public abstract Sprite getCurrentSprite();

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
		if (this.lastDirection == Direction.LEFT){
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean isImmune(){
		if (this.isImmune){
			return true;
		}
		else{
			return false;
		}
	}
	
	public void startMoveHorizontally(Direction direction) {
		assert (isValidMovingDirection(direction));
		if (direction == Direction.RIGHT){
			this.lastDirection = Direction.RIGHT;
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
			this.lastDirection = Direction.LEFT;
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
	
	public abstract void advanceTime(double dt);
	
}
