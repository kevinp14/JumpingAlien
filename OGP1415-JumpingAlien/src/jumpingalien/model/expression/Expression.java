package jumpingalien.model.expression;

import jumpingalien.model.Program;
import jumpingalien.model.type.Type;
import jumpingalien.part3.programs.SourceLocation;

public interface Expression<T extends Type<?>> {
	
	Object evaluate(Program program);

	SourceLocation getSourceLocation();
}

