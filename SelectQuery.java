package heapdb.query;


import heapdb.ITable;
import heapdb.Schema;
import heapdb.Table;
import heapdb.Tuple;

/**
 * A simple select query of the form:
 * select column, column, . . . from table where condition
 * 
 * @author Glenn
 *
 */

public class SelectQuery  {
	
	private Condition cond;
	private String[] colNames;	   // a value of null means return all columns of the table
	
	/**
	 * A query that contains both a where condition and a projection of columns
	 * @param colNames are the columns to return
	 * @param cond is the where clause
	 */
	public SelectQuery(String[] colNames, Condition cond) {
		this.colNames = colNames;
		this.cond = cond;
	}
	
	/**
	 * A query that contains both a where condition.  All columns 
	 * of the Tuples are returned.
	 * @param cond is the where clause
	 */
	public SelectQuery(Condition cond) {
		this(null, cond);
	}
	
	
	public static Table naturalJoin(ITable table1, ITable table2) { // do not do!!
		// TODO replace with your code.
		// Hint: use the Schema naturalJoin method.
		
		throw new  UnsupportedOperationException();
	}
	
	public ITable eval(ITable table) {
		// TODO replace with your code.
		
//		1. create a result Schema if colName is not null. Use Schema.project method (line 130).
//		2. create a result table
//		3. Iterate over all tuple in table given as argument
//		4. for each row, cond.eval(tuple) 
//		5. if false skip to next row.
//		6. if true -> project from tuple, using Tuple.project method (line 180)
//		7. return result table.
		Schema s;
		if(this.colNames != null) { // result Schema
			s = table.getSchema().project(colNames);
		}else {
			s = table.getSchema();
		}
		ITable tbl = new Table(s); // result table
		
		for(Tuple t: table) {
			if(cond.eval(t) == true) { // project from tuple, using Tuple.project 
				t.project(s);
			}
		}
		return tbl;
	}

	@Override
	public String toString() {
	    String proj_columns;
	    if (colNames != null) {
	    	proj_columns = String.join(",", colNames);
	    } else {
	    	proj_columns = "*";
	    }
	    return "select " + proj_columns + " where " + cond;
	}

}
