package jumpingalien.model.expression;

import jumpingalien.model.GameObject;
import jumpingalien.model.Program;
import jumpingalien.model.type.BooleanType;
import jumpingalien.model.type.DirectionType;
import jumpingalien.model.type.GameObjectType;
import jumpingalien.part3.programs.IProgramFactory.Direction;
import jumpingalien.part3.programs.SourceLocation;

public class IsMoving implements Expression<BooleanType> {
	
	private Expression<GameObjectType> object;
	private Expression<DirectionType> direction;
	private SourceLocation sourceLocation;
	
	public IsMoving(Expression<GameObjectType> object, Expression<DirectionType> direction, 
			SourceLocation sourceLocation){
		this.object = object;
		this.direction = direction;
		this.sourceLocation = sourceLocation;
	}

	@Override
	public Object evaluate(Program program) {
		GameObject gameObject = (GameObject) this.object.evaluate(program);
		Direction direction = (Direction) this.direction.evaluate(program);
		if ((direction == Direction.LEFT) || (direction == Direction.RIGHT)) {
			return (gameObject.isMovingHorizontally());
		}
		else if (direction == Direction.DOWN) {
			return (gameObject.isFalling());
		}
		else if (direction == Direction.UP) {
			return (gameObject.isJumping());
		}
		return false;
	}

	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}
}
