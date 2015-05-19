package jumpingalien.model.statement;

import jumpingalien.model.GameObject;
import jumpingalien.model.Program;
import jumpingalien.model.expression.Expression;
import jumpingalien.model.type.BooleanType;
import jumpingalien.model.type.DirectionType;
import jumpingalien.model.Direction;
import jumpingalien.part3.programs.SourceLocation;

public class StartRun implements Statement{
	
	private Expression<DirectionType> direction;
	private SourceLocation sourceLocation;
	
	public StartRun(Expression<DirectionType> direction, SourceLocation sourceLocation){
		this.direction = direction;
		this.sourceLocation = sourceLocation;
	}

	@Override
	public void execute(Program program, Expression<BooleanType> condition) {
		GameObject gameObject = (GameObject) program.getGameObject();
		Direction dir = (Direction) this.direction.evaluate(program);
		gameObject.startMoveHorizontally(dir, gameObject.getNormalHorizontalVelocity(), 
				gameObject.getNormalHorizontalAcceleration());
	}

	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}

}