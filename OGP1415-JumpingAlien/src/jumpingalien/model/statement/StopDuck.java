package jumpingalien.model.statement;

import jumpingalien.model.GameObject;
import jumpingalien.model.Program;
import jumpingalien.model.expression.Expression;
import jumpingalien.part3.programs.SourceLocation;

public class StopDuck implements Statement{
	
	private SourceLocation sourceLocation;
	
	public StopDuck(SourceLocation sourceLocation){
		this.sourceLocation = sourceLocation;
	}

	@Override
	public void execute(Program program, Expression condition) {
		GameObject gameObject = (GameObject) program.getGameObject();
		gameObject.endDuck();
	}

	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}

}
