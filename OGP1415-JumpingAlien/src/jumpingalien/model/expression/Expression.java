package jumpingalien.model.expression;

import jumpingalien.model.Program;
import jumpingalien.part3.programs.SourceLocation;

public interface Expression {
	
	Object evaluate(Program program);

	SourceLocation getSourceLocation();
}

