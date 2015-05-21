package jumpingalien.model;

import java.util.ArrayList;

import jumpingalien.util.Util;
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
 * @invar	Every tile in the world must have a valid type.
 * 			| isValidTileType(tileType)
 * @invar	The world must have a valid ending point.
 * 			| isValidTargetTile(targetTileX, targetTileY)
 * 
 * @author	Kevin Peeters (Tweede fase ingenieurswetenschappen)
 * 			Jasper Mariën (Tweede fase ingenieurswetenschappen)
 * @version 7.0
 * 
 */
public class World {
	private int tileSize;
	private int nbTilesX;
	private int nbTilesY;
	private int visibleWindowWidth;
	private int visibleWindowHeight;
	private int targetTileX;
	private int targetTileY;
	private boolean won;
	private boolean gameOver;
	private ArrayList<Plant> plants = new ArrayList<Plant>();
	private ArrayList<Shark> sharks = new ArrayList<Shark>();
	private	ArrayList<Slime> slimes = new ArrayList<Slime>();
	private Mazub alien;
	private Buzam buzam;
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
	 * @post	The new tile size is set to the given one.
	 * 			| (new this).tileSize = tileSize
	 * @post	The new number of tiles in x-direction is set to the given one.
	 * 			| (new this).nbTilesX = nbTilesX
	 * @post	The new number of tiles in y-direction is set to the given one.
	 * 			| (new this).nbTilesY = nbTilesY
	 * @post	The new width of the visible window is set to the given one.
	 * 			| (new this).visibleWindowWidth = visibleWindowWidth
	 * @post	The new height of the visible window is set to the given one.
	 * 			| (new this).visibleWindowHeight = visibleWindowHeight
	 * @post	The new target tile in x-direction is set to the given one.
	 * 			| (new this).targetTileX = targetTileX
	 * @post	The new target tile in y-direction is set to the given one.
	 * 			| (new this).targetTileY = targetTileY
	 * @post	The new matrix of tiles is initialized.
	 * 			| (new this).tiles = new int[this.nbTilesX][this.nbTilesY]
	 * @post	The game is not won.
	 * 			| (new this).won = false
	 * @post	The game is not over.
	 * 			| (new this).gameOver = false
	 * @post	The new game state is set to initiated.
	 * 			| (new this).gameState = GameState.INITIATED
	 */
	public World (int tileSize, int nbTilesX, int nbTilesY,
			int visibleWindowWidth, int visibleWindowHeight, int targetTileX,
			int targetTileY) {
		assert (isValidTargetTile(targetTileX, targetTileY));
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
	 * @post	The new game state is set to started.
	 * 			| (new this).gameState = GameState.STARTED
	 */
	public void startGame(){
		this.gameState = GameState.STARTED;
	}
	
	/**
	 * @return	The tile on which the target position is.
	 * 
	 */
	protected int[] getTargetTile() {
		int[] targetTile = {this.targetTileX, this.targetTileY};
		return targetTile;
	}
	
	/**
	 * @return	The alien in this world.
	 * 
	 */
	public Mazub getMazub() {
		return this.alien;
	}
	
	/**
	 * @param	alien
	 * 			The alien to be set as the player's character.
	 */
	public void setMazub(Mazub alien){
		this.alien = alien;
		this.alien.setWorld(this);
	}
	
	/**
	 * @return	The buzam, the evil twin of Mazub in this world.
	 * 
	 */
	public Buzam getBuzam() {
		return this.buzam;
	}
	
	/**
	 * @param	buzam
	 * 			The buzam to be set as the evil twin of Mazub
	 */
	public void setBuzam(Buzam buzam){
		this.buzam = buzam;
		this.buzam.setWorld(this);
	}
	
	/**
	 * @return	True if and only if the amount of objects in this world is smaller than 100.
	 * 
	 */
	private boolean canAddMore(){
		int amountOfObjects = 0;
		if (!(this.gameState == GameState.STARTED)) {
			if (this.plants != null){
				amountOfObjects += this.plants.size();
			}
			if (this.sharks != null){
				amountOfObjects += this.sharks.size();
			}
			if (this.slimes != null){
				amountOfObjects += this.slimes.size();
			}
			if (this.buzam != null) {
				amountOfObjects += 1;
			}
			return (amountOfObjects < 100);
		}
		return false;
	}
	
	/**
	 * @return An array list of plants in the current game world.
	 * 
	 */
	@Basic
	public ArrayList<Plant> getPlants(){
		return new ArrayList<>(plants);
	}
	
	/**
	 * @param 	plant
	 *          The plant that needs to be added to the world.
	 * @effect	If the world is not full yet, the plant is added to it.
	 * 			| if (this.canAddMore())
	 * 			|	this.plants.add(plant)
	 */
	public void addPlant(Plant plant){
		if (this.canAddMore()){
			this.plants.add(plant);
		}
	}
	
	/**
	 * @return An array list of sharks in the current game world.
	 * 
	 */
	@Basic
	public ArrayList<Shark> getSharks(){
		return new ArrayList<>(sharks);
	}
	
	/**
	 * @param 	shark
	 *          The shark that needs to be added to the world.
	 * @effect	If the world is not full yet, the shark is added to it.
	 * 			| if (this.canAddMore())
	 * 			|	this.sharks.add(shark)
	 */
	public void addShark(Shark shark){
		if (this.canAddMore()){
			this.sharks.add(shark);
		}
	}
	
	/**
	 * @return An array list of slimes in the current game world.
	 * 
	 */
	@Basic
	public ArrayList<Slime> getSlimes(){
		return new ArrayList<>(slimes);
	}

	/**
	 * @param 	slime
	 *          The slime that needs to be added to the world.
	 * @effect	If the world is not full yet, the slime is added to it.
	 * 			| if (this.canAddMore())
	 * 			|	this.slimes.add(slime)
	 */
	public void addSlime(Slime slime){
		if (this.canAddMore()){
			this.slimes.add(slime);
		}
	}
	
	/**
	 * @return The length of a square tile side, expressed as a number of pixels.
	 */
	@Basic
	public int getTileLength(){
		return this.tileSize;
	}
	
	/**
	 * @param 	pixelLeft
	 * 			The x-coordinate of the left side of the rectangular region.
	 * @param	pixelBottom
	 * 			The y-coordinate of the bottom side of the rectangular region.
	 * @param	pixelRight
	 *			The x-coordinate of the right side of the rectangular region.
	 * @param	pixelTop
	 *			The y-coordinate of the top side of the rectangular region.
	 * @return	An array of tile positions, where each position (x_T, y_T) is
	 * 			represented as an array of 2 elements, containing the horizontal
	 *			(x_T) and vertical (y_T) coordinate of a tile in that order.
	 *			The returned array is ordered from left to right,
	 *			bottom to top: all positions of the bottom row (ordered from
	 *			small to large x_T) precede the positions of the row above that.
	 * 
	 */
	@Basic
	public int[][] getTilePositionsIn(int pixelLeft, int pixelBottom,
			   int pixelRight, int pixelTop){
		ArrayList<int[]> tilePositions = new ArrayList<int[]>();
		for (int rowPosition = (pixelBottom / this.tileSize ) * this.tileSize; rowPosition <= pixelTop; 
				rowPosition += this.tileSize) {
			for (int columnPosition = (pixelLeft / this.tileSize) * this.tileSize; columnPosition 
					<= pixelRight; columnPosition += tileSize) {
				if ((this.tileSize * this.nbTilesY) - this.getTileLength() >= rowPosition
						&& (this.tileSize * this.nbTilesX) - this.getTileLength() >= columnPosition) {
					int[] tilePosition = new int[] {columnPosition / this.tileSize, 
						rowPosition / this.tileSize};
					tilePositions.add(tilePosition);
				}
			}
		}
		return tilePositions.toArray(new int[tilePositions.size()][]);
	}
	
	/**
	 * @return	The pixel coordinates of the visible window, in the order
	 *			left, bottom, right, top. The visible window must always contain the alien so that
	 *			it is always at least 200 pixels away from the edges of the window.
	 */
	@Basic
	public int[] getVisibleWindow() {
		Mazub mazub = this.getMazub();
		int positionX = mazub.getPosition()[0];
		int positionY = mazub.getPosition()[1];
		int worldSizeX = this.getWorldSize()[0];
		int worldSizeY = this.getWorldSize()[1];
		int maxX = worldSizeX - this.visibleWindowWidth;
		int maxY = worldSizeY - this.visibleWindowHeight;
		if (positionX < 200) {
			if (positionY < 200) {
				return new int[]{0, 0, this.visibleWindowWidth, this.visibleWindowHeight};
			}
			else {
				return new int[]{0, Math.min(maxY, positionY - 200), this.visibleWindowWidth, 
						Math.min(worldSizeY, positionY - 200 + this.visibleWindowHeight)};
			}
		}
		else {
			if (positionY < 200) {
				return new int[]{Math.min(maxX, positionX - 200), 0, Math.min(worldSizeX, 
						positionX - 200 + this.visibleWindowWidth), this.visibleWindowHeight};
			}
			else {
				return new int[]{Math.min(maxX, positionX - 200), Math.min(maxY, positionY - 200), 
						Math.min(worldSizeX, positionX - 200 + this.visibleWindowWidth), 
						Math.min(worldSizeY, positionY - 200 + this.visibleWindowHeight)};
			}
		}
	}
	
	/**
	 * @return	The size of the game world, in pixels, as an array of two
	 *			elements: width (X) and height (Y), in that order.
	 */
	@Basic
	public int[] getWorldSize() {
		int[] worldSize = new int[] {this.nbTilesX * this.tileSize, this.nbTilesY * this.tileSize };
		return worldSize;
	}
	
	/**
	 * @return	The top right tile of the game world.
	 * 
	 */
	public int[] getTopRightTile() {
		int[] topRightTile = new int[] { this.nbTilesX, this.nbTilesY };
		return topRightTile;
	}
	
	/**
	 * @param	tileX
	 *			The x-position x_T of the tile
	 * @param	tileY
	 *			The y-position y_T of the tile
	 * @return	An array which contains the x-coordinate and y-coordinate of the
	 *			bottom left pixel of the given tile, in that order.
	 */
	public int[] getBottomLeftPixelOfTile(int tileX, int tileY) {
		int tilePositionX = tileX * this.tileSize;
		int tilePositionY = tileY * this.tileSize;
		int[] tilePosition = new int[] { tilePositionX, tilePositionY };
		return tilePosition;
	}
	
	/**
	 * @param	tileX
	 *			The x-position x_T of the tile
	 * @param	tileY
	 *			The y-position y_T of the tile
	 * @return	An array which contains the x-coordinate and y-coordinate of the
	 *			top right pixel of the given tile, in that order.
	 */
	protected int[] getTopRightPixelOfTile(int tileX, int tileY) {
		int tilePositionX = (tileX + 1) * this.tileSize;
		int tilePositionY = (tileY + 1) * this.tileSize;
		int[] tilePosition = new int[] { tilePositionX, tilePositionY };
		return tilePosition;
	}
	
	/**
	 * @param	pixelX
	 *			The x-position of the pixel at the bottom left of the tile for
	 *			which the geological feature should be returned.
	 * @param	pixelY
	 *			The y-position of the pixel at the bottom left of the tile for
	 * 			which the geological feature should be returned.
	 * 
	 * @return	The type of the tile with the given bottom left pixel position, where
	 *			the value 0 is returned for an air tile
	 * 			the value 1 is returned for a solid ground tile
	 *			the value 2 is returned for a water tile
	 * 			the value 3 is returned for a magma tile.
	 */
	@Basic
	public int getGeologicalFeature(int pixelX, int pixelY){
		return this.tiles[(pixelX/this.tileSize)][(pixelY/this.tileSize)];
	}
	
	/**
	 * @param	tileX
	 *			The x-position x_T of the tile for which the type needs to be
	 * 			modified
	 * @param	tileY
	 * 			The y-position y_T of the tile for which the type needs to be
	 * 			modified
	 * @param	tileType
	 * 			The new type for the given tile, where
	 * 			the value 0 is returned for an air tile
	 * 			the value 1 is returned for a solid ground tile
	 *			the value 2 is returned for a water tile
	 * 			the value 3 is returned for a magma tile.
	 * @pre	The given tile type should be a valid one.
	 * 		| isValidTileType(tileType)
	 * @post	If the game is not started yet, the tile at the given position can be set to the
	 * 			given type
	 * 			| if (!(this.gameState == GameState.STARTED))
	 * 			|	this.tiles[tileX][tileY] = tileType
	 */
	public void setGeologicalFeature(int tileX, int tileY, int tileType){
		assert (isValidTileType(tileType));
		if (!(this.gameState == GameState.STARTED)) {
			this.tiles[tileX][tileY] = tileType;
		}
	}
	
	/**
	 * @return True if and only if the game is over.
	 * 
	 */
	public boolean isGameOver(){
		return (this.gameOver);
	}
	
	
	/**
	 * @return True if and only if the game is over and the player has won.
	 * 
	 */
	public boolean didPlayerWin(){
		return ((this.isGameOver()) && (this.won));
	}

	/**
	 * @param	geologicalFeature
	 * 			The geological feature which has to be checked.
	 * @return	True if and only if the geological feature equals 1.
	 */
	public boolean isNotPassable(int geologicalFeature){
		return (geologicalFeature == 1);
	}
	
	/**
	 * @param 	tileType
	 * 			The tile type which has to be checked.
	 * @return	True if and only if the given tile type is 0, 1, 2 or 3.
	 */
	private boolean isValidTileType(int tileType){
		return ((tileType == 0)
				|| (tileType == 1)
				|| (tileType == 2)
				|| (tileType == 3)
				);
	}
	
	/**
	 * @param	targetTileX
	 * 			The x-position of the target tile that has to be checked.
	 * @param	targetTileY
	 * 			The y-position of the target tile that has to be checked.
	 * @return	True if and only if the target tile is located in the game world.
	 */
	private boolean isValidTargetTile(int targetTileX, int targetTileY) {
		return ((targetTileX <= this.getWorldSize()[0]) && (targetTileY <= this.getWorldSize()[1]));
	}
	
	/**
	 * @return	True if and only if the alien touches the target tile.
	 * 
	 */
	protected boolean alienOnTargetTile() {
		return (this.getMazub().touchTargetTile());
	}
	
	/**
	 * @effect	If the given object is dead for 0.6 seconds or longer, its movement is ended.
	 * 			if (Util.fuzzyGreaterThanOrEqualTo(object.getTimeDead(), 0.6))
	 * 			|	object.setHorizontalAcceleration(0)
	 * 			|	object.setVerticalAcceleration(0)
	 * 			|	object.setHorizontalVelocity(0)
	 * 			|	object.setVerticalVelocity(0);
	 * @effect	If the given game object is a plant, it is removed from this world's plants.
	 * 			| if (this.plants.contains(object))
	 * 			|	this.plants.remove(object)
	 * @effect	If the given game object is a shark, it is removed from this world's sharks.
	 * 			| if (this.sharks.contains(object))
	 * 			|	this.sharks.remove(object)
	 * @effect	If the given game object is a slime, it is removed from this world's slimes.
	 * 			| if (this.slimes.contains(object))
	 * 			|	this.slimes.remove(object)
	 * @effect	If the given game object is buzam, it is removed from this game world.
	 * 			| if (object == this.getBuzam())
	 * 			|	this.setBuzam(null)
	 */
	public void removeDeadObject(GameObject object, double dt) {
		object.setHorizontalAcceleration(0);
		object.setVerticalAcceleration(0);
		object.setHorizontalVelocity(0);
		object.setVerticalVelocity(0);
		if (Util.fuzzyGreaterThanOrEqualTo(object.getTimeDead(), 0.6)) {
			object.setTimeDead(0);
			if (this.getPlants().contains(object)) {
				this.plants.remove(object);
			}
			if (this.getSharks().contains(object)) {
				this.sharks.remove(object);
			}
			if (this.getSlimes().contains(object)) {
				this.slimes.remove(object);
			}
			if (object == this.getBuzam()) {
				this.setBuzam(null);
			}
		}
		else {
			object.setTimeDead(object.getTimeDead() + dt);
		}
	}
	
	public void advanceTime(double dt) {
		Mazub alien = this.getMazub();
		alien.advanceTime(dt);
		if ((alien.getNbHitPoints() == 0) //TODO alien uit wereld
				|| (alien.getPosition()[1] < 0)) {
			alien.setHorizontalAcceleration(0);
			alien.setHorizontalVelocity(0);
			alien.setVerticalAcceleration(0);
			alien.setVerticalVelocity(0);
			if (alien.getTimeDead() >= 0.6) {
				alien.setPosition(alien.getPosition()[0], -100);
				this.gameOver = true;
				this.gameState = GameState.STOPPED;
			}
			else {
				alien.setTimeDead(alien.getTimeDead() + dt);
			}
		}
		if (this.alienOnTargetTile()) {
			this.won = true;
			this.gameOver = true;
			this.gameState = GameState.STOPPED;
		}
//		Buzam buzam = this.getBuzam();
//		buzam.advanceTime(dt);
//		if (buzam.isDead()) {
//			this.removeDeadObject(buzam, dt);
//		}
		for (Shark shark: this.getSharks()) {
			if (shark.isDead()) {
				this.removeDeadObject(shark, dt);
			}
			shark.advanceTime(dt);
		}
		for (Slime slime: this.getSlimes()) {
			if (slime.isDead()) {
				this.removeDeadObject(slime, dt);
			}
			slime.advanceTime(dt);
		}
		for (Plant plant: this.getPlants()) {
			if (plant.isDead()) {
				this.removeDeadObject(plant, dt);
			}
			plant.advanceTime(dt);
		}
	}
}