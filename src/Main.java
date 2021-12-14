import java.util.ArrayList;
import java.util.Random;

public class Main {
	public static int[][] appropriatedState;
	public static Random random = new Random();
	
	public static void main(String[] args) {
		int[][] board = {
				{-1, 0, 0},
				{0, 0, 0},
				{1, 0, 0}
		};

		boolean isXTurn = false;
		for(int i = 2; i < 9; i++) {
			makeBetterMove(board, isXTurn);
			
			board = appropriatedState;
			Utils.printState(board);
			
			if(evaluate(board, isXTurn, true) != 0) break;
			
			isXTurn = !isXTurn;
		}
	}

	// Funções para avaliação de um estado
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
		
		if(!needFinalResults)
			return checkResults(lines, columns, diagonal, isXTurn);
		else 
			return finalResults(lines, columns, diagonal);
	}
	
	public static int checkResults(int[] lines, int[] columns, int[] diagonal, boolean isXTurn) {
		int countX = 0;
		int countO = 0;
		for(int i = 0; i < 3; i++) {
			if(lines[i] == 3 || columns[i] == 3) {
				return 1;
			} 
			if(lines[i] == -3 || columns[i] == -3) {
				return -1;
			}
			
			if(lines[i] == 2) {
				countX++;
			} 
			if(columns[i] == 2) {
				countX++;
			}
			if(lines[i] == -2) {
				countO++;
			}
			if(columns[i] == -2) {
				countO++;
			}
		}
		
		for(int i = 0; i < 2; i++) {
			if(diagonal[i] == 3) {
				return 1;
			} 
			if(diagonal[i] == -3) {
				return -1;
			}
			
			if(diagonal[i] == 2) {
				countX++;
			}
			if(diagonal[i] == -2) {
				countO++;
			}
		}
		
		if(countX == 2) {
			return 1;
		}
		if(countO == 2) {
			return -1;
		}
		
		if(isXTurn && countX == 1) {
			return 1;
		}
		if(!isXTurn && countO == 1) {
			return -1;
		}
		
		return 0;
	}

	public static int finalResults(int[] lines, int[] columns, int[] diagonal) {
		for(int i = 0; i < 3; i++) {
			if(lines[i] == 3 || columns[i] == 3) {
				return 1;
			} 
			if(lines[i] == -3 || columns[i] == -3) {
				return -1;
			}
		}
		
		for(int i = 0; i < 2; i++) {
			if(diagonal[i] == 3) {
				return 1;
			} 
			if(diagonal[i] == -3) {
				return -1;
			}
		}

		return 0;
	}
	// Fim funções de avaliação
	
	// Dado um estado e de quem é a vez de jogar, 
	// determina qual melhor movimento
	public static int makeBetterMove(int[][] state, boolean isXTurn) {
		ArrayList<int[][]> possibilities = generateNewPossibleStates(state, isXTurn);
		int count = possibilities.size();
		
		if(count == 0 || evaluate(state, isXTurn, true) != 0) 
			return evaluate(state, isXTurn, true);
		
		int[][] betterState = possibilities.get(random.nextInt(count));
		int betterWeight = 0;
		for(int[][] possibility: possibilities) {
			int evaluation = evaluate(possibility, isXTurn, false);
			
			int weight = evaluation + makeBetterMove(possibility, !isXTurn);
			
			boolean condition = isXTurn ? 
					weight >= betterWeight : weight <= betterWeight;
			if(condition) {
				betterWeight = weight;
				betterState = Utils.copy(possibility);
			}
		}
		
		appropriatedState = betterState;
		
		return betterWeight;
	}
	
	// Gera todas a possibilidades de um estado
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
