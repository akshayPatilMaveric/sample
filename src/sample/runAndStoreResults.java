package sample;

public class runAndStoreResults {
	static connectionClass createCon= new connectionClass();
	
	public void runnCommand() {
		
	}
	
	public static void main(String args[]) {
		String[][] QueryResults;
		createCon.createConnection();
		QueryResults=createCon.runStatement("SELECT * FROM user");
		createCon.closeCon();
		for (int i = 0; i < QueryResults.length; i++) {
            for (int j = 0; j < QueryResults[0].length; j++) {
            	System.out.print(QueryResults[i][j]+" | ");
            }
            System.out.println("");
        }		
		
		
	}

}
