package jumpingalien.model.expression;
//TODO commentaar
import jumpingalien.model.Buzam;
import jumpingalien.model.GameObject;
import jumpingalien.model.Mazub;
import jumpingalien.model.Plant;
import jumpingalien.model.Program;
import jumpingalien.model.Shark;
import jumpingalien.model.Slime;
import jumpingalien.model.World;
import jumpingalien.part3.programs.IProgramFactory.Direction;
import jumpingalien.part3.programs.SourceLocation;

public class SearchObject implements Expression {
	
	private Expression expr;
	private SourceLocation sourceLocation;
	private int distance;
	
	public SearchObject(Expression expr, SourceLocation sourceLocation){
		this.expr = expr;
		this.sourceLocation = sourceLocation;
	}

	@Override
	public Object evaluate(Program program) {
		Direction direction = (Direction) this.expr.evaluate(program);
		GameObject gameObject = program.getGameObject();
		World world = gameObject.getWorld();
		int[] position = gameObject.getPosition();
		int[] maxPosition = gameObject.getMaxPosition();
		int tileX = position[0] / world.getTileLength();
		int tileY = position[1] / world.getTileLength();
		if (direction == Direction.DOWN){
			int iterateTile = tileY - 1;
			if ((int)this.searchObjectDown(position, world) == position[1]) {
				return this.searchTileDown(iterateTile, tileX, program);
			}
			else {
				return this.searchObjectDown(position, world);
			}
		}
		if (direction == Direction.LEFT){
			int iterateTile = tileX - 1;
			if ((int)this.searchObjectLeft(position, world) == position[0]) {
				return this.searchTileLeft(iterateTile, tileY, program);
			}
			else {
				return this.searchObjectLeft(position, world);
			}
		}
		if (direction == Direction.RIGHT){
			int iterateTile = tileX + 1;
			if ((int)this.searchObjectRight(position, maxPosition, world) 
					== maxPosition[0] - position[0]) {
				return this.searchTileRight(iterateTile, tileY, program);
			}
			else {
				return this.searchObjectRight(position, maxPosition, world);
			}
		}
		else {
			int iterateTile = tileY + 1;
			if ((int)this.searchObjectUp(position, maxPosition, world) 
					== maxPosition[1] - position[1]) {
				return this.searchTileUp(iterateTile, tileX, program);
			}
			else {
				return this.searchObjectUp(position, maxPosition, world);
			}
		}
	}
	
	@Override
	public Object evaluateForGivenObject(Program program, Object object) {
		Direction direction = (Direction) this.expr.evaluate(program);
		GameObject gameObject = (GameObject) object;
		World world = gameObject.getWorld();
		int[] position = gameObject.getPosition();
		int[] maxPosition = gameObject.getMaxPosition();
		int tileX = position[0] / world.getTileLength();
		int tileY = position[1] / world.getTileLength();
		if (direction == Direction.DOWN){
			int iterateTile = tileY - 1;
			if ((int)this.searchObjectDown(position, world) == position[1]) {
				return this.searchTileDown(iterateTile, tileX, program);
			}
			else {
				return this.searchObjectDown(position, world);
			}
		}
		if (direction == Direction.LEFT){
			int iterateTile = tileX - 1;
			if ((int)this.searchObjectLeft(position, world) == position[0]) {
				return this.searchTileLeft(iterateTile, tileY, program);
			}
			else {
				return this.searchObjectLeft(position, world);
			}
		}
		if (direction == Direction.RIGHT){
			int iterateTile = tileX + 1;
			if ((int)this.searchObjectRight(position, maxPosition, world) 
					== maxPosition[0] - position[0]) {
				return this.searchTileRight(iterateTile, tileY, program);
			}
			else {
				return this.searchObjectRight(position, maxPosition, world);
			}
		}
		else {
			int iterateTile = tileY + 1;
			if ((int)this.searchObjectUp(position, maxPosition, world) 
					== maxPosition[1] - position[1]) {
				return this.searchTileUp(iterateTile, tileX, program);
			}
			else {
				return this.searchObjectUp(position, maxPosition, world);
			}
		}
	}
	
	private Object searchTileUp(int iterateTile, int tileX, Program program) {
		World world = program.getGameObject().getWorld();
		while (iterateTile <= world.getTopRightTile()[1]) {
			int[] tilePosition = world.getBottomLeftPixelOfTile(tileX, iterateTile);
			if (world.getGeologicalFeature(tilePosition[0], tilePosition[1]) == 2) {
				return tilePosition;
			}
			iterateTile += 1;
		}
		return null;
	}
	
	private Object searchTileRight(int iterateTile, int tileY, Program program) {
		World world = program.getGameObject().getWorld();
		while (iterateTile <= world.getTopRightTile()[0]) {
			int[] tilePosition = world.getBottomLeftPixelOfTile(iterateTile, tileY);
			if (world.getGeologicalFeature(tilePosition[0], tilePosition[1]) == 2) {
				return tilePosition;
			}
			iterateTile += 1;
		}
		return null;
	}
	
	private Object searchTileDown(int iterateTile, int tileX, Program program) {
		World world = program.getGameObject().getWorld();
		while (iterateTile >= 0) {
			int[] tilePosition = world.getBottomLeftPixelOfTile(tileX, iterateTile);
			if (world.getGeologicalFeature(tilePosition[0], tilePosition[1]) == 2) {
				return tilePosition;
			}
			iterateTile -= 1;
		}
		return null;
	}
	
	private Object searchTileLeft(int iterateTile, int tileY, Program program) {
		World world = program.getGameObject().getWorld();
		while (iterateTile >= 0) {
			int[] tilePosition = world.getBottomLeftPixelOfTile(iterateTile, tileY);
			if (world.getGeologicalFeature(tilePosition[0], tilePosition[1]) == 2) {
				return tilePosition;
			}
			iterateTile -= 1;
		}
		return null;
	}
	
	private Object searchObjectUp(int[] position, int[] maxPosition, World world) {
		this.distance = maxPosition[1] - position[1];
		for (Plant plant: world.getPlants()) {
			if ((plant.getPosition()[1] > position[1]) 
					&& (plant.getPosition()[0] == position[0])) {
				int distanceBetween = plant.getPosition()[1] - position[1];
				if (distanceBetween < this.distance) {
					this.distance = distanceBetween;
				}
			}
		}
		for (Slime slime: world.getSlimes()) {
			if ((slime.getPosition()[1] > position[1]) 
					&& (slime.getPosition()[0] == position[0])) {
				int distanceBetween = slime.getPosition()[1] - position[1];
				if (distanceBetween < this.distance) {
					this.distance = distanceBetween;
				}
			}
		}
		for (Shark shark: world.getSharks()) {
			if ((shark.getPosition()[1] > position[1]) 
					&& (shark.getPosition()[0] == position[0])) {
				int distanceBetween = shark.getPosition()[1] - position[1];
				if (distanceBetween < this.distance) {
					this.distance = distanceBetween;
				}
			}
		}
		Mazub mazub = world.getMazub();
		if ((mazub.getPosition()[1] > position[1]) 
				&& (mazub.getPosition()[0] == position[0])) {
			int distanceBetween = mazub.getPosition()[1] - position[1];
			if (distanceBetween < this.distance) {
				this.distance = distanceBetween;
			}
		}
		Buzam buzam = world.getBuzam();
		if ((buzam.getPosition()[1] > position[1]) 
				&& (buzam.getPosition()[0] == position[0])) {
			int distanceBetween = buzam.getPosition()[1] - position[1];
			if (distanceBetween < this.distance) {
				this.distance = distanceBetween;
			}
		}
		return this.distance;
	}
	
	private Object searchObjectRight(int[] position, int[] maxPosition, World world) {
		this.distance = maxPosition[0] - position[0];
		for (Plant plant: world.getPlants()) {
			if ((plant.getPosition()[0] > position[0]) 
					&& (plant.getPosition()[1] == position[1])) {
				int distanceBetween = plant.getPosition()[0] - position[0];
				if (distanceBetween < this.distance) {
					this.distance = distanceBetween;
				}
			}
		}
		for (Slime slime: world.getSlimes()) {
			if ((slime.getPosition()[0] > position[0]) 
					&& (slime.getPosition()[1] == position[1])) {
				int distanceBetween = slime.getPosition()[0] - position[0];
				if (distanceBetween < this.distance) {
					this.distance = distanceBetween;
				}
			}
		}
		for (Shark shark: world.getSharks()) {
			if ((shark.getPosition()[0] > position[0]) 
					&& (shark.getPosition()[1] == position[1])) {
				int distanceBetween = shark.getPosition()[0] - position[0];
				if (distanceBetween < this.distance) {
					this.distance = distanceBetween;
				}
			}
		}
		Mazub mazub = world.getMazub();
		if ((mazub.getPosition()[0] > position[0]) 
				&& (mazub.getPosition()[1] == position[1])) {
			int distanceBetween = mazub.getPosition()[0] - position[0];
			if (distanceBetween < this.distance) {
				this.distance = distanceBetween;
			}
		}
		Buzam buzam = world.getBuzam();
		if ((buzam.getPosition()[0] > position[0]) 
				&& (buzam.getPosition()[1] == position[1])) {
			int distanceBetween = buzam.getPosition()[0] - position[0];
			if (distanceBetween < this.distance) {
				this.distance = distanceBetween;
			}
		}
		return this.distance;
	}
	
	private Object searchObjectDown(int[] position, World world) {
		this.distance = position[1];
		for (Plant plant: world.getPlants()) {
			if ((plant.getPosition()[1] < position[1]) 
					&& (plant.getPosition()[0] == position[0])) {
				int distanceBetween = position[1] - plant.getPosition()[1];
				if (distanceBetween < this.distance) {
					this.distance = distanceBetween;
				}
			}
		}
		for (Slime slime: world.getSlimes()) {
			if ((slime.getPosition()[1] < position[1]) 
					&& (slime.getPosition()[0] == position[0])) {
				int distanceBetween = position[1] - slime.getPosition()[1];
				if (distanceBetween < this.distance) {
					this.distance = distanceBetween;
				}
			}
		}
		for (Shark shark: world.getSharks()) {
			if ((shark.getPosition()[1] < position[1]) 
					&& (shark.getPosition()[0] == position[0])) {
				int distanceBetween = position[1] - shark.getPosition()[1];
				if (distanceBetween < this.distance) {
					this.distance = distanceBetween;
				}
			}
		}
		Mazub mazub = world.getMazub();
		if ((mazub.getPosition()[1] < position[1]) 
				&& (mazub.getPosition()[0] == position[0])) {
			int distanceBetween = position[1] - mazub.getPosition()[1];
			if (distanceBetween < this.distance) {
				this.distance = distanceBetween;
			}
		}
		Buzam buzam = world.getBuzam();
		if ((buzam.getPosition()[1] < position[1]) 
				&& (buzam.getPosition()[0] == position[0])) {
			int distanceBetween = position[1] - buzam.getPosition()[1];
			if (distanceBetween < this.distance) {
				this.distance = distanceBetween;
			}
		}
		return this.distance;
	}
	
	private Object searchObjectLeft(int[] position, World world) {
		this.distance = position[0];
		for (Plant plant: world.getPlants()) {
			if ((plant.getPosition()[0] < position[0]) 
					&& (plant.getPosition()[1] == position[1])) {
				int distanceBetween = position[0] - plant.getPosition()[0];
				if (distanceBetween < this.distance) {
					this.distance = distanceBetween;
				}
			}
		}
		for (Slime slime: world.getSlimes()) {
			if ((slime.getPosition()[0] < position[0]) 
					&& (slime.getPosition()[1] == position[1])) {
				int distanceBetween = position[0] - slime.getPosition()[0];
				if (distanceBetween < this.distance) {
					this.distance = distanceBetween;
				}
			}
		}
		for (Shark shark: world.getSharks()) {
			if ((shark.getPosition()[0] < position[0]) 
					&& (shark.getPosition()[1] == position[1])) {
				int distanceBetween = position[0] - shark.getPosition()[0];
				if (distanceBetween < this.distance) {
					this.distance = distanceBetween;
				}
			}
		}
		Mazub mazub = world.getMazub();
		if ((mazub.getPosition()[0] < position[0]) 
				&& (mazub.getPosition()[1] == position[1])) {
			int distanceBetween = position[0] - mazub.getPosition()[0];
			if (distanceBetween < this.distance) {
				this.distance = distanceBetween;
			}
		}
		Buzam buzam = world.getBuzam();
		if ((buzam.getPosition()[0] < position[0]) 
				&& (buzam.getPosition()[1] == position[1])) {
			int distanceBetween = position[0] - buzam.getPosition()[0];
			if (distanceBetween < this.distance) {
				this.distance = distanceBetween;
			}
		}
		return this.distance;
	}
	
	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}

}
