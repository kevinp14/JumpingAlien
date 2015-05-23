package jumpingalien.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import jumpingalien.model.statement.Statement;
import jumpingalien.model.type.Type;

public class Program implements Runnable{
	private final Statement mainStatement;
	private Map<String, Type<?>> globalVariables;
	private Map<String, Object> variables = new HashMap<String, Object>();
	private GameObject gameObject;
	
	public Program(Statement mainStatement, Map<String, Type<?>> globalVariables){
		this.mainStatement = mainStatement;
		this.globalVariables = globalVariables;
		for (Entry<String, Type<?>> entry : this.globalVariables.entrySet()){
			String variableName = entry.getKey();
			this.variables.put(variableName, null);
		}
	}
	
	public Statement getMainStatement(){
		return this.mainStatement;
	}
	
	public Map<String,Type<?>> getGlobalVariables(){
		return this.globalVariables;
	}
	
	public GameObject getGameObject() {
		return this.gameObject;
	}
	
	public void setGameObject(GameObject gameObject) {
		this.gameObject = gameObject;
	}
	
	public boolean isWellFormed(){
		return true;
	}
	
	public Object getObjectByName(String variableName, Type<?> variableType){
		for(Entry<String, Object> entry : this.variables.entrySet()) {
		    String name = entry.getKey();
		    Object value = entry.getValue();
		    if ((name == variableName) && (variableType == this.globalVariables.get(name))){
		    	return value;
		    }
		}
		return null;
	}
	
	public void setObjectByName(String variableName, Type<?> type, Object value){
		for(Entry<String, Object> entry : this.variables.entrySet()) {
		    String name = entry.getKey();
		    if ((name == variableName) && (this.globalVariables.get(name) == type)){
		    	this.variables.put(name, value);
		    }
		}
	}

	@Override
	public void run() {
		this.mainStatement.execute(this, null);
	}
}
