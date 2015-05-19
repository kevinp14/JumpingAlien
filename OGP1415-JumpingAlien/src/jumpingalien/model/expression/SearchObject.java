package jumpingalien.model.expression;

import jumpingalien.model.Buzam;
import jumpingalien.model.Mazub;
import jumpingalien.model.Plant;
import jumpingalien.model.Program;
import jumpingalien.model.Shark;
import jumpingalien.model.Slime;
import jumpingalien.model.World;
import jumpingalien.model.type.GameObjectType;
import jumpingalien.part3.programs.IProgramFactory.Direction;
import jumpingalien.part3.programs.SourceLocation;

public class SearchObject implements Expression<GameObjectType> {
	
	private Expression<GameObjectType> expr;
	private SourceLocation sourceLocation;
	
	public SearchObject(Expression<GameObjectType> expr, SourceLocation sourceLocation){
		this.expr = expr;
		this.sourceLocation = sourceLocation;
	}

	@Override
	public Object evaluate(Program program) {
		Direction direction = (Direction) this.expr.evaluate(program);
		World world = program.getGameObject().getWorld();
		int[] position = program.getGameObject().getPosition();
		int tileX = position[0] / world.getTileLength();
		int tileY = position[1] / world.getTileLength();
		if (direction == Direction.DOWN){
			int iterateTile = tileY - 1;
			return this.searchObjectVertically(iterateTile, tileX, program);
		}
		if (direction == Direction.LEFT){
			int iterateTile = tileX - 1;
			return this.searchObjectHorizontally(iterateTile, tileY, program);
		}
		if (direction == Direction.RIGHT){
			int iterateTile = tileX + 1;
			return this.searchObjectHorizontally(iterateTile, tileY, program);
		}
		else {
			int iterateTile = tileY + 1;
			return this.searchObjectVertically(iterateTile, tileX, program);
		}
	}
	
	private Object searchObjectVertically(int iterateTile, int tileX, Program program) {
		World world = program.getGameObject().getWorld();
		int[] position = program.getGameObject().getPosition();
		while (iterateTile >= 0) {
			if (world.getGeologicalFeature(position[0], position[1]) == 2)
				return null; //TODO
			else {
				for (Plant plant: world.getPlants()) {
					int plantTileX = plant.getPosition()[0] / world.getTileLength();
					int plantTileY = plant.getPosition()[1] / world.getTileLength();
					if ((plantTileX == tileX) && (iterateTile == plantTileY))
						return plant;
				}
				for (Shark shark: world.getSharks()) {
					int sharkTileX = shark.getPosition()[0] / world.getTileLength();
					int sharkTileY = shark.getPosition()[1] / world.getTileLength();
					if ((sharkTileX == tileX) && (iterateTile == sharkTileY))
						return shark;
				}
				for (Slime slime: world.getSlimes()) {
					int slimeTileX = slime.getPosition()[0] / world.getTileLength();
					int slimeTileY = slime.getPosition()[1] / world.getTileLength();
					if ((slimeTileX == tileX) && (iterateTile == slimeTileY))
						return slime;
				}
				Mazub mazub = world.getMazub();
				int mazubTileX = mazub.getPosition()[0] / world.getTileLength();
				int mazubTileY = mazub.getPosition()[1] / world.getTileLength();
				if ((mazubTileX == tileX) && (iterateTile == mazubTileY))
					return mazub;
				Buzam buzam = world.getBuzam();
				int buzamTileX = buzam.getPosition()[0] / world.getTileLength();
				int buzamTileY = buzam.getPosition()[1] / world.getTileLength();
				if ((buzamTileX == tileX) && (iterateTile == buzamTileY))
					return buzam;
			}
			iterateTile -= 1;
		}
		return null;
	}
	
	private Object searchObjectHorizontally(int iterateTile, int tileY, Program program) {
		World world = program.getGameObject().getWorld();
		int[] position = program.getGameObject().getPosition();
		while (iterateTile >= 0) {
			if (world.getGeologicalFeature(position[0], position[1]) == 2)
				return null; //TODO
			else {
				for (Plant plant: world.getPlants()) {
					int plantTileX = plant.getPosition()[0] / world.getTileLength();
					int plantTileY = plant.getPosition()[1] / world.getTileLength();
					if ((plantTileX == iterateTile) && (plantTileY == tileY))
						return plant;
				}
				for (Shark shark: world.getSharks()) {
					int sharkTileX = shark.getPosition()[0] / world.getTileLength();
					int sharkTileY = shark.getPosition()[1] / world.getTileLength();
					if ((sharkTileX == iterateTile) && (sharkTileY == tileY))
						return shark;
				}
				for (Slime slime: world.getSlimes()) {
					int slimeTileX = slime.getPosition()[0] / world.getTileLength();
					int slimeTileY = slime.getPosition()[1] / world.getTileLength();
					if ((slimeTileX == iterateTile) && (slimeTileY == tileY))
						return slime;
				}
				Mazub mazub = world.getMazub();
				int mazubTileX = mazub.getPosition()[0] / world.getTileLength();
				int mazubTileY = mazub.getPosition()[1] / world.getTileLength();
				if ((mazubTileX == iterateTile) && (mazubTileY == tileY))
					return mazub;
				Buzam buzam = world.getBuzam();
				int buzamTileX = buzam.getPosition()[0] / world.getTileLength();
				int buzamTileY = buzam.getPosition()[1] / world.getTileLength();
				if ((buzamTileX == iterateTile) && (buzamTileY == tileY))
					return buzam;
			}
		}
		return null;
	}
	
	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}

}
