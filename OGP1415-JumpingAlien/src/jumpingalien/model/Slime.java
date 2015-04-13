package jumpingalien.model;

import be.kuleuven.cs.som.annotate.Basic;
import jumpingalien.util.Sprite;

public class Slime extends Mazub{
	//private double positionX;
	//private double positionY;
	//private Sprite[] spriteList;
	private School school;
	//private int timeMoving;
	//private Direction lastDirection;

	public Slime (int positionX, int positionY, Sprite[] spriteList, School school) {
		//this.positionX = (double)positionX/100d;
		//this.positionY = (double)positionY/100d;
		//this.spriteList = spriteList;
		super(positionX, positionY, spriteList);
		this.school = school;
		//this.timeMoving = 0;
		//this.lastDirection = Direction.STALLED;
	}
	
	//public void advanceTime(double dt){
	//	
	//}
	
	//@Basic
	//public double[] getPosition() {
	//	double[] position = new double[]{this.positionX, this.positionY};
	//	return position;
	//}
	
	@Basic
	public School getSchool(){
		return this.school;
	}
	
	public void setSchool(School newSchool){
		this.school.removeSlimeFromSchool(this);
		this.school = newSchool;
		newSchool.addSlimeToSchool(this);
	}
	
	//public Sprite getCurrentSprite(){
	//	if (this.timeMoving == 0){
	//		return this.spriteList[0];
	//	}
	//	else{
	//		return this.spriteList[0];
	//	}
	//}
	
	//public Direction getDirection(){
	//	return this.lastDirection;
	//}
	
	public void startDuck(){
		
	}
	
	public void startJump(){
		
	}
	
}
