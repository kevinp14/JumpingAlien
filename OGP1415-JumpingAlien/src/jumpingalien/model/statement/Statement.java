package jumpingalien.model.statement;

import jumpingalien.model.Program;
import jumpingalien.model.expression.Expression;
import jumpingalien.model.type.Type;
import jumpingalien.part3.programs.SourceLocation;

public interface Statement {
	
	void execute(Program program, Expression condition);
	
	SourceLocation getSourceLocation();
}
