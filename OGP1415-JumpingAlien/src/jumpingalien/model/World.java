package jumpingalien.model;

import java.util.Collection;
import java.util.Hashtable;
import be.kuleuven.cs.som.annotate.Basic;

public class World {
	private int tileSize;
	private int nbTilesX;
	private int nbTilesY;
	private int visibleWindowWidth;
	private int visibleWindowHeight;
	private int targetTileX;
	private int targetTileY;
	private boolean won;
	private Collection<Plant> plants;
	private Collection<Shark> sharks;
	private Collection<Slime> slimes;
	private Mazub alien;
	private double dt;
	Hashtable<int[], Integer> tiles
	= new Hashtable<int[], Integer>();
	private GameState gameState;
	
	public World (int tileSize, int nbTilesX, int nbTilesY,
			int visibleWindowWidth, int visibleWindowHeight, int targetTileX,
			int targetTileY){
		this.tileSize = tileSize;
		this.nbTilesX = nbTilesX;
		this.nbTilesY = nbTilesY;
		this.visibleWindowWidth = visibleWindowWidth;
		this.visibleWindowHeight = visibleWindowHeight;
		this.targetTileX = targetTileX;
		this.targetTileY = targetTileY;
		this.won = false;
		this.plants = null;
		this.sharks = null;
		this.slimes = null;
		this.alien = null;
		this.dt = 0;
		for (int x  = 0; x<nbTilesX; x++){
			for (int y = 0; y<nbTilesY; y++){
				this.tiles.put(new int[]{x*tileSize, y*tileSize, 0}, 0);
			}
		}
		this.gameState = GameState.INITIATED;
	}
	
	public void addPlant(Plant plant){
		this.plants.add(plant);
	}
	
	public void addShark(Shark shark){
		this.sharks.add(shark);
	}
	
	public void addSlime(Slime slime){
		this.slimes.add(slime);
	}
	
	//hoe zit het met die dt? zelf berekenen of meegegeven?
	public void advanceTime(double dt){
		this.alien.advanceTime(dt);
		for (Shark shark: sharks){
			shark.advanceTime(dt);
		}
		for (Slime slime: slimes){
			slime.advanceTime(dt);
		}
		for(Plant plant: plants){
			plant.advanceTime(dt);
		}
	}
	
	public int[] getBottomLeftPixelOfTile(int tileX, int tileY){
		int tilePositionX = (tileX%this.tileSize)*this.tileSize;
		int tilePositionY = (tileY%this.tileSize)*this.tileSize;
		int[] positionBottomLeftPixelOfTile = new int[]{tilePositionX, tilePositionY};
		return positionBottomLeftPixelOfTile;
	}
	
	private double getNextDt(double dt){
		double velocity = Math.pow((Math.pow(this.alien.getHorizontalVelocity(), 2) + 
				Math.pow(this.alien.getVerticalVelocity(),2)), 1/2);
		double acceleration = Math.pow((Math.pow(this.alien.getHorizontalAcceleration(), 2) + 
				Math.pow(this.alien.getVerticalAcceleration(),2)), 1/2);
		double newDt = 0.01 / (velocity + acceleration*dt);
		return newDt;
	}
	
	public boolean didPlayerWin(){
		if (this.won){
			return true;
		}
		else{
			return false;
		}
	}
	
	@Basic
	public int getGeologicalFeature(int pixelX, int pixelY){
		return this.tiles.get(this.getBottomLeftPixelOfTile(pixelX, pixelY));
	}
	
	@Basic
	public Collection<Plant> getPlants(){
		return this.plants;
	}
	
	@Basic
	public Collection<Shark> getSharks(){
		return this.sharks;
	}
	
	@Basic
	public Collection<Slime> getSlimes(){
		return this.slimes;
	}
	
	@Basic
	public int getTileLength(){
		return this.tileSize;
	}
	
	//nog fout, eerst getVisibleWindow fixen
	@Basic
	public int[][] getTilePositionsIn(int pixelLeft, int pixelBottom,
			int pixelRight, int pixelTop){
		this.getVisibleWindow();
		int [] a = new int[]{0,0};
		int [] c = new int[]{this.tileSize, 0};
		int[][] b = new int[][]{a,c};
		return b;
	}
	
	//nog fout
	@Basic
	public int[] getVisibleWindow() {
		this.alien.getPosition();
		return new int[]{0,0};
	}
	
	@Basic
	public int[] getWorldSize(){
		int[] worldSize = new int[]{nbTilesX*this.tileSize, nbTilesY*this.tileSize};
		return worldSize;
	}
	
	public boolean isGameOver(){
		if (this.alien.getHitPoints() <= 0){
			return true;
		}
		else{
			return false;
		}
	}
	
	public boolean isNotPassable(int geologicalFeature){
		if (geologicalFeature == 1){
			return true;
		}
		else{
			return false;
		}
	}
	
	
	/**
	 *            <ul>
	 *            <li>the value 0 is provided for an <b>air</b> tile;</li>
	 *            <li>the value 1 is provided for a <b>solid ground</b> tile;</li>
	 *            <li>the value 2 is provided for a <b>water</b> tile;</li>
	 *            <li>the value 3 is provided for a <b>magma</b> tile.</li>
	 *            </ul>
	 */
	public void setGeologicalFeature(int tileX, int tileY, int tileType){
		this.tiles.put(this.getBottomLeftPixelOfTile(tileX, tileY),tileType);
	}
	
	public void setMazub(Mazub alien){
		this.alien = alien;
		this.alien.setWorld(this);
	}
	
	public void startGame(){
		this.gameState = GameState.STARTED;
	}

	
}
