/**
 * 
 */
package tajo.engine.exec.eval;

import com.google.common.base.Objects;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import tajo.catalog.proto.CatalogProtos.DataType;
import tajo.datum.Datum;
import tajo.engine.json.GsonCreator;

/**
 * @author Hyunsik Choi
 *
 */
public class ConstEval extends EvalNode implements Comparable<ConstEval>, Cloneable {
	@Expose Datum datum = null;
	
	public ConstEval(Datum datum) {
		super(Type.CONST);
		this.datum = datum;
	}

  @Override
  public EvalContext newContext() {
    return null;
  }

  @Override
  public Datum terminate(EvalContext ctx) {
    return this.datum;
  }


  public Datum getValue() {
    return this.datum;
  }
	
	public String toString() {
		return datum.toString();
	}
	
	public String toJSON() {
		Gson gson = GsonCreator.getInstance();
		return gson.toJson(this, EvalNode.class);
	}

  @Override
	public DataType [] getValueType() {
		switch(this.datum.type()) {
      case CHAR: return new DataType[] {DataType.CHAR};
      case BOOLEAN: return new DataType[] {DataType.BOOLEAN};
      case BYTE: return new DataType[] {DataType.BYTE};
      case BYTES : return new DataType[] {DataType.BYTES};
      case DOUBLE : return new DataType[] {DataType.DOUBLE};
      case FLOAT: return new DataType[] {DataType.FLOAT};
      case INT: return new DataType[] {DataType.INT};
      case IPv4: return new DataType[] {DataType.IPv4};
      case LONG: return new DataType[] {DataType.LONG};
      case SHORT: return new DataType[] {DataType.SHORT};
      case STRING: return new DataType[] {DataType.STRING};
      default: return new DataType[] {DataType.ANY};
		}
	}

	@Override
	public String getName() {
		return this.datum.toString();
	}
	
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof ConstEval) {
      ConstEval other = (ConstEval) obj;

      if (this.type == other.type && this.datum.equals(other.datum)) {
        return true;
      }
    }
    return false;
  }
  
  @Override
  public int hashCode() {
    return Objects.hashCode(type, datum.type(), datum);
  }
  
  @Override
  public Object clone() throws CloneNotSupportedException {
    ConstEval eval = (ConstEval) super.clone();
    eval.datum = datum;
    
    return eval;
  }

  @Override
  public int compareTo(ConstEval other) {    
    return datum.compareTo(other.datum);
  }
  
  @Override
  public void preOrder(EvalNodeVisitor visitor) {
    visitor.visit(this);
  }
  
  @Override
  public void postOrder(EvalNodeVisitor visitor) {
    visitor.visit(this);
  }
}