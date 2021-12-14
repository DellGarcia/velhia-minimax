
public class Utils {
	public static int[][] copy(int[][] state) {
		int[][] copy = new int[state.length][state.length];
		
		for(int i=0; i<state.length; i++)
		  for(int j=0; j<state[i].length; j++)
		    copy[i][j]=state[i][j];
		
		return copy;
	}
	
	public static void printState(int[][] state) {
		for(int i = 0; i < state.length; i++) {
			for(int j = 0; j < state[i].length; j++) {
				System.out.print(state[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

}
