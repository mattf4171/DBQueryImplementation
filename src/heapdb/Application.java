package heapdb;

import heapdb.query.AndCondition;
import heapdb.query.Condition;
import heapdb.query.EqCondition;
import heapdb.query.OrCondition;
import heapdb.query.SelectQuery;

public class Application{
	public static void main(String[] args) {

		Schema schema = new Schema(); // create schema


		schema.addKeyIntType("ID"); // ID must be a key
		schema.addVarCharType("name");
		schema.addVarCharType("dept_name");
		schema.addIntType("salary");
		
		Table table = new Table(schema);
		
		Tuple[] tup = new Tuple[] {
				new Tuple(schema, 22222, "Einstein",    "Physics",    95000), // insert Success
				new Tuple(schema, 45565, "Katz",        "Comp. Sci.", 75000),
				new Tuple(schema, 98345, "Kim",         "Elec. Eng.", 80000),
				new Tuple(schema, 10101, "Srinivasan" , "Comp. Sci.", 65000),
				new Tuple(schema, 49494, "Alondra" ,    "Physiology", 72000),
				new Tuple(schema, 49494, "Alondra" ,    "Physiology", 72000) // insert Fail
		};
		for(int i = 0; i < tup.length; i++){ // insert 
			table.insert(tup[i]);
		}
		
		System.out.println("\nContents of table:\n"+table);
		
		System.out.print("Deletion of key, 22222 ");
		if(table.delete(22222) == true) {
			System.out.print("Successful.\n"); 
			}else { System.out.print("Failed, key is not in table.\n"); }
		
		System.out.println("\nContents of table:\n"+table);
		
		System.out.print("Deletion of key, 10001");
		if(table.delete(10001) == true) {
			System.out.print(" Successful.\n"); 
			}else { System.out.print(" Failed, key is not in table.\n"); } // delete Failed
		
		System.out.print("\nLookup 49494");
        if(table.lookup(49494) !=null) {
			System.out.print(" Successful, found in table.\n");// delete Success
        }else {
        	System.out.print(" Failed, not found in table.\n");
        }
		System.out.println("\nContents of table:\n"+table);


		System.out.print("\nLookup salary=72000 , ");
        System.out.print(table.lookup("salary", 72000));
//		System.out.print(" Successful, found in table.\n");

		System.out.print("\nLookup name=Alondra , ");
        System.out.print(table.lookup("name", "Alondra"));

        System.out.print("\nLookup ID=10000 , ");
        System.out.print(table.lookup("ID", 10000));
        //TODO: Add 3 queries:  one is a simple EqCondition that returns multiple rows, 
    	//	    one is a query with AndCondition that returns empty result, 
        //      the third query using a combination of AndCondition and OrConditions and returns a result with a single row
    	
        // based on queryTest 
        System.out.println("\n\nSimple EqCondition that returns multiple rows: ");
        Condition condition = new EqCondition("dept_name", "Comp. Sci.");
        SelectQuery q = new SelectQuery( condition);
        ITable res = q.eval(table);
        System.out.print(res);
        
        System.out.println("\n\nQuery w/ AndCondition that returns empty result: ");
        Condition cond = new AndCondition(new EqCondition("salary", 72000), new EqCondition("name", "Einstein"));
        q = new SelectQuery( cond);
        res = q.eval(table);
        System.out.print(res);
        
        System.out.println("\n\nQuery w/ combination of AndCondition and OrConditions that returns a result with a single row: ");
        condition = new OrCondition(new AndCondition(new EqCondition("salary", 72000 ), new EqCondition("name", "Alondra")), new AndCondition( new EqCondition("name", "Hiccup"), new EqCondition("dept_name", "Comp. Sci.") ) );
        q = new SelectQuery( condition);
        res = q.eval(table);
        System.out.print(res);
        
	}
}