package jumpingalien.model.statement;

import jumpingalien.model.GameObject;
import jumpingalien.model.Program;
import jumpingalien.model.expression.Expression;
import jumpingalien.model.Direction;
import jumpingalien.part3.programs.SourceLocation;

public class StopRun implements Statement{
	
	private Expression direction;
	private SourceLocation sourceLocation;
	
	public StopRun(Expression direction, SourceLocation sourceLocation){
		this.direction = direction;
		this.sourceLocation = sourceLocation;
	}

	@Override
	public void execute(Program program, Expression condition) {
		GameObject gameObject = (GameObject) program.getGameObject();
		Direction dir = (Direction) this.direction.evaluate(program);
		gameObject.endMoveHorizontally(dir);
	}

	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}

}