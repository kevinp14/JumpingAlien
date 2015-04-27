package jumpingalien.model;

import java.util.ArrayList;

import jumpingalien.part2.facade.IFacadePart2;
import be.kuleuven.cs.som.annotate.Basic;

/**
 * A class of worlds involving the size of tiles (square blocks of pixels), the number of tiles in x- 
 * and y-direction, the height and width of the visible window, the target tile (end tile), a list of 
 * plants, sharks and slimes, an alien, a method to start the game, methods to get the lists of slimes,
 * sharks and plant, and the alien, methods to add slimes, sharks, plants and an alien to the world,
 * a method to inspect the tile length and one to get the tiles position in pixels, a method to get the
 * visible window size and one to get the game world size, methods to get the bottom left or top right
 * pixel of a tile, a method to calculate an accurate period of time dt, methods to get and set the 
 * geological feature (air, water, magma, impassable) at a tile, methods to inspect whether the player
 * won the game or not, a method to inspect whether a tile is passable or not, and a method to advance 
 * the time.
 * 
 * @invar The world must be in a valid gameState.
 * 		  | isValidGameState(gameState)
 * @invar Every tile in the world must have a valid type.
 * 		  | isValidTileType(tileType)
 * @invar The world must have a valid ending point.
 * 		  | targetTileX <= this.getWorldSize()[0] && targetTileY <= this.getWorldSize()[1]
 * @author	Kevin Peeters (Tweede fase ingenieurswetenschappen)
 * 			Jasper Mariën (Tweede fase ingenieurswetenschappen)
 * @version 5.0
 *
 */
public class World {
	private int tileSize;
	protected int nbTilesX;
	protected int nbTilesY;
	private int visibleWindowWidth;
	private int visibleWindowHeight;
	private int targetTileX;
	private int targetTileY;
	private boolean won;
	private boolean gameOver;
	private ArrayList<Plant> plants = new ArrayList<>();
	private ArrayList<Shark> sharks = new ArrayList<>();
	private	ArrayList<Slime> slimes = new ArrayList<>();
	private Mazub alien;
	private int[][] tiles;
	private GameState gameState;
	
	/**
	 * Initialize a world with the given size of tiles, number of tiles in x- and y-direction, the 
	 * height and width of the visible window and the target tiles.
	 * 
	 * @param 	tileSize
	 * 			The length of the square tiles.
	 * @param 	nbTilesX
	 * 			The number of tiles in the x-direction.
	 * @param	nbTilesY
	 * 			The number of tiles in the y-direction.
	 * @param	visibleWindowWidth
	 * 			The width of the visible window, the game window displayed on the screen.
	 * @param	visibleWindowHeight
	 * 			The height of the visible window, the game window displayed on the screen.
	 * @param	targetTileX
	 * 			The target tile number in x-direction. The target tile is the place where the alien has
	 * 			to stand on to win the game.
	 * @param	targetTileY
	 * 			The target tile number in y-direction. The target tile is the place where the alien has
	 * 			to stand on to win the game.
	 */
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
		this.tiles = new int[this.nbTilesX][this.nbTilesY];
		this.won = false;
		this.gameOver = false;
		this.gameState = GameState.INITIATED;
	}
	
	/**
	 * Check whether the given tileType is a valid one.
	 * 
	 * @param 	tileType
	 * 			The tileType which has to be checked.
	 * @return	True if and only if the direction is 0, 1, 2 or 3.
	 */
	private boolean isValidTileType(int tileType){
		return ((tileType == 0)
				|| (tileType == 1)
				|| (tileType == 2)
				|| (tileType == 3)
				);
	}
	
	/**
	 * Check whether the given GameState is a valid one.
	 * 
	 * @param 	gameState
	 * 			The GameState which has to be checked.
	 * @return	True if and only if the direction is initiated, started, stopped.
	 */
	private boolean isValidGameState(GameState gameState){
		return ((gameState == GameState.INITIATED)
				|| (gameState == GameState.STARTED)
				|| (gameState == GameState.STOPPED));
	}
	
	/**
	 * Starts the game that is played in the given world.
	 * After this method has been invoked, no further game objects will be added
	 * via {@link IFacadePart2#addPlant(World, Plant)},
	 * {@link IFacadePart2#addShark(World, Shark)},
	 * {@link IFacadePart2#addSlime(World, Slime)}, or
	 * {@link IFacadePart2#setMazub(World, Mazub)}), and no geological features
	 * will be changed via
	 * {@link IFacadePart2#setGeologicalFeature(World, int, int, int)}.
	 * 
	 * @param The
	 *            world for which to start the game.
	 */
	public void startGame(){
		this.gameState = GameState.STARTED;
	}
	
	/**
	 * @return	The alien in this world
	 */
	public Mazub getMazub() {
		return this.alien;
	}
	
	/**
	 * Sets the given alien as the player's character in the given world.
	 * 
	 * @param world
	 *            The world for which to set the player's character.
	 * @param mazub
	 *            The alien to be set as the player's character.
	 */
	public void setMazub(Mazub alien){
		this.alien = alien;
		this.alien.setWorld(this);
	}
	
	/**
	 * Check whether more game objects can be added to this world.
	 * 
	 * @return	True if and only if the amount of objects in this world is smaller than 100.
	 */
	private boolean canAddMore(){
		int amountOfObjects = 0;
		if (!(this.gameState == GameState.STARTED)) {
			if (plants != null){
				amountOfObjects += plants.size();
			}
			if  (sharks != null){
				amountOfObjects += sharks.size();
			}
			if (slimes != null){
				amountOfObjects += slimes.size();
			}
			if (amountOfObjects == 100){
				return false;
			}
			else{
				return true;
			}
		}
		else
			return false;
	}
	
	/**
	 * Returns all the plants currently located in the given world.
	 * 
	 * @param world
	 *            The world for which to retrieve all plants.
	 * @return All plants that are located somewhere in the given world. There
	 *         are no restrictions on the type or order of the returned
	 *         collection, but each plant may only be returned once.
	 */
	@Basic
	public ArrayList<Plant> getPlants(){
		return new ArrayList<>(plants);
	}
	
	/**
	 * Add the given plant as a game object to the given world.
	 * 
	 * @param world
	 *            The world to which the plant should be added.
	 * @param plant
	 *            The plant that needs to be added to the world.
	 */
	public void addPlant(Plant plant){
		if (this.canAddMore()){
			this.plants.add(plant);
		}
	}
	
	/**
	 * Returns all the sharks currently located in the given world.
	 * 
	 * @param world
	 *            The world for which to retrieve all sharks.
	 * @return All sharks that are located somewhere in the given world. There
	 *         are no restrictions on the type or order of the returned
	 *         collection, but each shark may only be returned once.
	 */
	@Basic
	public ArrayList<Shark> getSharks(){
		return new ArrayList<>(sharks);
	}
	
	/**
	 * Add the given shark as a game object to the given world.
	 * 
	 * @param shark
	 *            The shark that needs to be added to the world.
	 */
	public void addShark(Shark shark){
		if (this.canAddMore()){
			this.sharks.add(shark);
		}
	}
	
	/**
	 * Returns all the slimes currently located in the given world.
	 * 
	 * @param world
	 *            The world for which to retrieve all slimes.
	 * @return All slimes that are located somewhere in the given world. There
	 *         are no restrictions on the type or order of the returned
	 *         collection, but each slime may only be returned once.
	 */
	@Basic
	public ArrayList<Slime> getSlimes(){
		return new ArrayList<>(slimes);
	}

	/**
	 * Add the given slime as a game object to the given world.
	 * 
	 * @param world
	 *            The world to which the slime should be added.
	 * @param slime
	 *            The slime that needs to be added to the world.
	 */
	public void addSlime(Slime slime){
		if (this.canAddMore()){
			this.slimes.add(slime);
		}
	}
	
	/**
	 * Returns the length of a square tile side in the given world.
	 * 
	 * @param world
	 *            The game world for which to retrieve the tile length
	 * 
	 * @return The length of a square tile side, expressed as a number of
	 *         pixels.
	 */
	@Basic
	public int getTileLength(){
		return this.tileSize;
	}
	
	/**
	 * Returns the tile positions of all tiles within the given rectangular
	 * region.
	 * 
	 * @param world
	 *            The world from which the tile positions should be returned.
	 * @param pixelLeft
	 *            The x-coordinate of the left side of the rectangular region.
	 * @param pixelBottom
	 *            The y-coordinate of the bottom side of the rectangular region.
	 * @param pixelRight
	 *            The x-coordinate of the right side of the rectangular region.
	 * @param pixelTop
	 *            The y-coordinate of the top side of the rectangular region.
	 * 
	 * @return An array of tile positions, where each position (x_T, y_T) is
	 *         represented as an array of 2 elements, containing the horizontal
	 *         (x_T) and vertical (y_T) coordinate of a tile in that order.
	 *         The returned array is ordered from left to right,
	 *         bottom to top: all positions of the bottom row (ordered from
	 *         small to large x_T) precede the positions of the row above that.
	 * 
	 */
	@Basic
	public int[][] getTilePositionsIn(int pixelLeft, int pixelBottom,
			   int pixelRight, int pixelTop){
		ArrayList<int[]> tilePositions = new ArrayList<int[]>();
		for(int rowPos = (pixelBottom/tileSize)*tileSize; rowPos <= pixelTop; rowPos+=tileSize){
			for(int colPos = (pixelLeft/tileSize)*tileSize;colPos <= pixelRight;colPos+=tileSize){
				if((this.tileSize*nbTilesY)-getTileLength()>=rowPos
						&& (this.tileSize*nbTilesX)-getTileLength()>=colPos)
					tilePositions.add(new int[]{colPos/tileSize,rowPos/tileSize});
				}
			}
	return tilePositions.toArray(new int[tilePositions.size()][]);
	}
/*	public int[][] getTilePositionsIn(int pixelLeft, int pixelBottom,
			int pixelRight, int pixelTop){
		int dimension = 0;
		for (int x = (int)(pixelLeft/this.tileSize); x < pixelRight; x += this.tileSize ){
			for (int y = (int)(pixelBottom/this.tileSize); y < pixelTop; y += this.tileSize){
				dimension += 1;
			}
		}
		int[][] positions = new int[dimension][2];
		int placeInPositions = 0;
		for (int x = (int)(pixelLeft/this.tileSize); x < pixelRight; x += this.tileSize ){
			for (int y = (int)(pixelBottom/this.tileSize); y < pixelTop; y += this.tileSize){
				positions[placeInPositions][0] = x;
				positions[placeInPositions][1] = y;
				placeInPositions += 1;
			}
		}
		return positions;
	}*/
	
	/**
	 * Return the coordinates of the rectangular visible window that moves
	 * together with Mazub.
	 * 
	 * @return The pixel coordinates of the visible window, in the order
	 *         <b>left, bottom, right, top</b>.
	 */
	@Basic
	public int[] getVisibleWindow() {
		if (this.getMazub().getLastDirection() == Direction.LEFT){
			if (this.getMazub().getPosition()[0] < 200){
				if (this.getMazub().getPosition()[1] < 200){
					return new int[]{0,0,this.visibleWindowWidth, this.visibleWindowHeight};
				}
				else{
					return new int[]{0, this.getMazub().getPosition()[1] - 200, this.visibleWindowWidth, 
							this.getMazub().getPosition()[1] - 200 + this.visibleWindowHeight};
				}
			}
			else{
				if (this.getMazub().getPosition()[1] < 200){
					return new int[]{this.getMazub().getPosition()[0] + 200 - this.visibleWindowWidth,
							0, this.getMazub().getPosition()[0] + 200, this.visibleWindowHeight};
				}
				else{
					return new int[]{this.getMazub().getPosition()[0] + 200 - this.visibleWindowWidth,
							this.getMazub().getPosition()[1] - 200, this.getMazub().getPosition()[0] + 200,
							this.getMazub().getPosition()[1] - 200 + this.visibleWindowHeight};
				}
			}
		}
		else {
			if (this.getMazub().getPosition()[0] < 200){
				if (this.getMazub().getPosition()[1] < 200){
					return new int[]{0,0,this.visibleWindowWidth, this.visibleWindowHeight};
				}
				else{
					return new int[]{0, this.getMazub().getPosition()[1] - 200, this.visibleWindowWidth, 
							this.getMazub().getPosition()[1] - 200 + this.visibleWindowHeight};
				}
			}
			else{
				if (this.getMazub().getPosition()[1] < 200){
					return new int[]{this.getMazub().getPosition()[0] - 200,
							0, this.getMazub().getPosition()[0] - 200 + this.visibleWindowWidth, 
							this.visibleWindowHeight};
				}
				else{
					return new int[]{this.getMazub().getPosition()[0] - 200,
							this.getMazub().getPosition()[1] - 200, this.getMazub().getPosition()[0] - 200 + 
							this.visibleWindowWidth, this.getMazub().getPosition()[1] - 200 + 
							this.visibleWindowHeight};
				}
			}
		}
	}
	
	/**
	 * Returns the size of the given game world, in number of pixels.
	 * 
	 * @param world
	 *            The world for which to return the size.
	 * @return The size of the game world, in pixels, as an array of two
	 *         elements: width (X) and height (Y), in that order.
	 */
	@Basic
	public int[] getWorldSize(){
		int[] worldSize = new int[]{nbTilesX*this.tileSize, nbTilesY*this.tileSize};
		return worldSize;
	}
	
	/**
	 * Returns the bottom left pixel coordinate of the tile at the given tile
	 * position.
	 * 
	 * @param world
	 *            The world from which to retrieve the tile.
	 * @param tileX
	 *            The x-position x_T of the tile
	 * @param tileY
	 *            The y-position y_T of the tile
	 * @return An array which contains the x-coordinate and y-coordinate of the
	 *         bottom left pixel of the given tile, in that order.
	 */
	public int[] getBottomLeftPixelOfTile(int tileX, int tileY){
		int tilePositionX = tileX*this.tileSize;
		int tilePositionY = tileY*this.tileSize;
		int[] positionBottomLeftPixelOfTile = new int[]{tilePositionX, tilePositionY};
		return positionBottomLeftPixelOfTile;
	}
	
	/**
	 * Returns the top right pixel coordinate of the tile at the given tile
	 * position.
	 * 
	 * @param world
	 *            The world from which to retrieve the tile.
	 * @param tileX
	 *            The x-position x_T of the tile
	 * @param tileY
	 *            The y-position y_T of the tile
	 * @return An array which contains the x-coordinate and y-coordinate of the
	 *         top right pixel of the given tile, in that order.
	 */
	protected int[] getTopRightPixelOfTile(int tileX, int tileY) {
		int tilePositionX = (tileX + 1)*this.tileSize;
		int tilePositionY = (tileY + 1)*this.tileSize;
		int[] positionTopRightPixelOfTile = new int[]{tilePositionX, tilePositionY};
		return positionTopRightPixelOfTile;
	}
	
	/**
	 * @param 	dt
	 * 			The period of time dt for which the new period of time needs to be calculated.
	 * @return	The new period of time dt based on the current velocity and acceleration of the alien
	 * 			in this world. (used for accurate collision detection).
	 */
/*	private double getNewDt(double dt){
		double velocity = Math.pow((Math.pow(this.alien.getHorizontalVelocity(), 2) + 
				Math.pow(this.alien.getVerticalVelocity(),2)), 1/2);
		double acceleration = Math.pow((Math.pow(this.alien.getHorizontalAcceleration(), 2) + 
				Math.pow(this.alien.getVerticalAcceleration(),2)), 1/2);
		double newDt = 0.01 / (velocity + acceleration*dt);
		return newDt;
	}*/
	
	/**
	 * Returns the geological feature of the tile with its bottom left pixel at
	 * the given position.
	 * 
	 * @param world
	 *            The world containing the tile for which the
	 *            geological feature should be returned.
	 * 
	 * @param pixelX
	 *            The x-position of the pixel at the bottom left of the tile for
	 *            which the geological feature should be returned.
	 * @param pixelY
	 *            The y-position of the pixel at the bottom left of the tile for
	 *            which the geological feature should be returned.
	 * 
	 * @return The type of the tile with the given bottom left pixel position,
	 *         where
	 *         <ul>
	 *         <li>the value 0 is returned for an <b>air</b> tile;</li>
	 *         <li>the value 1 is returned for a <b>solid ground</b> tile;</li>
	 *         <li>the value 2 is returned for a <b>water</b> tile;</li>
	 *         <li>the value 3 is returned for a <b>magma</b> tile.</li>
	 *         </ul>
	 * 
	 * @note This method must return its result in constant time.
	 * 
	 * @throw ModelException if the given position does not correspond to the
	 *        bottom left pixel of a tile.
	 */
	@Basic
	public int getGeologicalFeature(int pixelX, int pixelY){
		return this.tiles[(pixelX/this.tileSize)][(pixelY/this.tileSize)];
	}
	
	/**
	 * Modify the geological type of a specific tile in the given world to a
	 * given type.
	 * 
	 * @param world
	 *            The world in which the geological type of a tile needs to be
	 *            modified
	 * @param tileX
	 *            The x-position x_T of the tile for which the type needs to be
	 *            modified
	 * @param tileY
	 *            The y-position y_T of the tile for which the type needs to be
	 *            modified
	 * @param tileType
	 *            The new type for the given tile, where
	 *            <ul>
	 *            <li>the value 0 is provided for an <b>air</b> tile;</li>
	 *            <li>the value 1 is provided for a <b>solid ground</b> tile;</li>
	 *            <li>the value 2 is provided for a <b>water</b> tile;</li>
	 *            <li>the value 3 is provided for a <b>magma</b> tile.</li>
	 *            </ul>
	 */
	public void setGeologicalFeature(int tileX, int tileY, int tileType){
		assert (isValidTileType(tileType));
		if (!(this.gameState == GameState.STARTED))
			this.tiles[tileX][tileY] = tileType;
	}
	
	/**
	 * Returns whether the game played in the given world has finished and the
	 * player has won. The player wins when Mazub has reached the target tile.
	 * 
	 * @param world
	 *            The world for which to check whether the player won
	 * @return true if the game is over and the player has won; false otherwise.
	 */
	public boolean didPlayerWin(){
		if (this.won){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * Returns whether the game, played in the given game world, is over.
	 * The game is over when Mazub has died, or has reached the target tile.
	 * 
	 * @param world
	 *            The world for which to check whether the game is over
	 * @return true if the game is over, false otherwise.
	 */
	public boolean isGameOver(){
		return (this.gameOver);
	}
	
	/**
	 * Check whether the geological feature is passable or not.
	 * 
	 * @param	geologicalFeature
	 * 			The geological feature which has to be checked.
	 * @return	True if and only if the geological feature equals 1.
	 */
	public boolean isNotPassable(int geologicalFeature){
		return (geologicalFeature == 1);
	}
	
	public void advanceTime(double dt) {
		this.getMazub().advanceTime(dt);
		if ((this.getMazub().getNbHitPoints() == 0) 
				|| (this.getMazub().getPosition()[1] < 0)) {
			this.gameOver = true;
			this.gameState = GameState.STOPPED;
		}
		if ((this.getMazub().getPosition()[0] == this.targetTileX) 
				&& (this.getMazub().getPosition()[1] == this.targetTileY)) {
			this.won = true;
			this.gameOver = true;
			this.gameState = GameState.STOPPED;
		}
		for (Shark shark: this.getSharks()){
			shark.advanceTime(dt);
			if ((shark.getNbHitPoints() == 0) || (!shark.isValidPosition(shark.getPosition())))
				this.sharks.remove(shark);
		}
		for (Slime slime: this.getSlimes()){
			slime.advanceTime(dt);
			if ((slime.getNbHitPoints() == 0) || (!slime.isValidPosition(slime.getPosition())))
				this.slimes.remove(slime);
		}
		for(Plant plant: this.getPlants()){
			plant.advanceTime(dt);
			if ((plant.getNbHitPoints() == 0) || (!plant.isValidPosition(plant.getPosition())))
				this.plants.remove(plant);
		}
	}
}
