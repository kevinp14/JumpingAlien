package jumpingalien.model;

import jumpingalien.model.Mazub.*;

public class World {
	private int tileSize;
	private int nbTilesX;
	private int nbTilesY;
	private int visibleWindowWidth;
	private int visibleWindowHeight;
	private int targetTileX;
	private int targetTileY;
	private boolean won;
	
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
	}
	
	public int getGeologicalFeature(int pixelX, int pixelY){
		return 0;
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
		
	}
	
	public void addPlant(Plant plant){
		
	}
	
	public void addShark(Shark shark){
		
	}
	
	public void addSlime(Slime slime){
		
	}
	
	public void advanceTime(double dt){
		
	}
	
	public boolean didPlayerWin(){
		if (this.won){
			return true;
		}
		else{
			return false;
		}
	}
	
}
