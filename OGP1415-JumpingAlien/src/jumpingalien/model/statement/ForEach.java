package jumpingalien.model.statement;

import jumpingalien.model.Program;
import jumpingalien.model.expression.Expression;
import jumpingalien.part3.programs.SourceLocation;
import jumpingalien.part3.programs.IProgramFactory.Kind;
import jumpingalien.part3.programs.IProgramFactory.SortDirection;

public class ForEach implements Statement{
	
	private String variableName;
	private Kind variableKind;
	private Expression where;
	private Expression sort;
	private SortDirection sortDirection;
	private Statement body;
	private SourceLocation sourceLocation;
	
	public ForEach(String variableName, Kind variableKind,
			Expression where, Expression sort, SortDirection sortDirection,
			Statement body, SourceLocation sourceLocation) {
		this.variableName = variableName;
		this.variableKind = variableKind;
		this.where = where;
		this.sort = sort;
		this.sortDirection = sortDirection;
		this.body = body;
		this.sourceLocation = sourceLocation;
	}

	@Override
	public void execute(Program program, Expression condition) {}

	@Override
	public SourceLocation getSourceLocation() {
		return sourceLocation;
	}
	
	

}
