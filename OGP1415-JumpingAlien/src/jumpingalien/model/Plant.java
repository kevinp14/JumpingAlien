package jumpingalien.model;

import jumpingalien.util.Sprite;

public class Plant extends Mazub{
	//private double positionX;
	//private double positionY;
	//private Sprite[] spriteList;
	
	public Plant(int positionX, int positionY, Sprite[] spriteList){
		//this.positionX = (double)positionX/100d;
		//this.positionY = (double)positionY/100d;
		//this.spriteList = spriteList;
		super(positionX, positionY, spriteList);
	}
	
	public void advanceTime(double dt){
		
	}
}
