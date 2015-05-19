package jumpingalien.model.statement;

import jumpingalien.model.Program;
import jumpingalien.model.expression.Expression;
import jumpingalien.model.type.BooleanType;
import jumpingalien.part3.programs.SourceLocation;

public interface Statement {
	
	void execute(Program program, Expression<BooleanType> condition);
	
	SourceLocation getSourceLocation();
}
