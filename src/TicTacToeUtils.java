import java.util.ArrayList;

public class TicTacToeUtils {

	public static int evaluate(int[][] state, boolean isXTurn, boolean needFinalResults) {
		int lines[] = new int[3];
		int columns[] = new int[3];
		int diagonal[] = new int[2];
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				lines[j] += state[j][i];
				columns[j] += state[i][j];
				
				if(i == j) 
					diagonal[0] += state[i][j];
				
				if(i + j == 2) 
					diagonal[1] += state[i][j];
			}
		}
		

		return checkResults(lines, columns, diagonal, isXTurn, needFinalResults);
	}
	
	public static int checkResults(int[] lines, int[] columns, int[] diagonal, boolean isXTurn, boolean needFinalResults) {
		int countX = 0;
		int countO = 0;
		for(int i = 0; i < 3; i++) {
			if(lines[i] == 3 || columns[i] == 3) 
				return 1;
			
			if(lines[i] == -3 || columns[i] == -3) 
				return -1;
			
			
			if(!needFinalResults) {
				if(lines[i] == 2) countX++;
				
				if(columns[i] == 2) countX++;
				
				if(lines[i] == -2) countO++;
				
				if(columns[i] == -2) countO++;
			}
		}
		
		for(int i = 0; i < 2; i++) {
			if(diagonal[i] == 3) 
				return 1;
			
			if(diagonal[i] == -3) 
				return -1;
			
			
			if(!needFinalResults) {
				if(diagonal[i] == 2) countX++;
				
				if(diagonal[i] == -2) countO++;
			}
		}
		
		if(needFinalResults)
			return 0;
		
		if(countX == 2) 
			return 1;
		
		if(countO == 2) 
			return -1;
		
		
		if(isXTurn && countX == 1) 
			return 1;
		
		if(!isXTurn && countO == 1) 
			return -1;
		
		
		return 0;
	}
	
	public static ArrayList<int[][]> generateNewPossibleStates(int[][] state, boolean isXTurn) {
		ArrayList<int[][]> possibilities = new ArrayList<int[][]>();
		
		for(int i = 0; i < state.length; i++) {
			for(int j = 0; j < state[i].length; j++) {
				if(state[i][j] == 0) {
					int[][] possibility = Utils.copy(state);
					possibility[i][j] = isXTurn ? 1 : -1;
					possibilities.add(possibility);
				}
			}
		}
		
		return possibilities;
	}
	
}
