import java.util.ArrayList;

public class Main {
	public static int[][] appropriatedState;
	
	public static void main(String[] args) {
		int[][] board = {
				{0, 0, 0}, 
				{0, 0, 0}, 
				{0, 0, 0} 
		};				

		boolean isXTurn = false;
		for(int i = 0; i < 9; i++) {
			makeMove(board, isXTurn);
			
			board = appropriatedState;
			Utils.printState(board);
			
			if(TicTacToeUtils.evaluate(board, isXTurn, true) != 0) break;
			
			isXTurn = !isXTurn;
		}
	}

	public static Move makeMove(int[][] state, boolean isXTurn) {
		ArrayList<int[][]> possibilities = TicTacToeUtils.generateNewPossibleStates(state, isXTurn);
		int count = possibilities.size();
		
		if(count == 0 || TicTacToeUtils.evaluate(state, isXTurn, true) != 0) 
			return new Move(TicTacToeUtils.evaluate(state, isXTurn, true), 1);
		
		Move betterMove = new Move(0, 0);
		int[][] betterState = new int[3][3];
		int[][] deepestState = new int[3][3];
		boolean haveBetterChoice = false;
		for(int[][] possibility: possibilities) {
			int evaluation = TicTacToeUtils.evaluate(possibility, isXTurn, false);
			
			Move move = makeMove(possibility, !isXTurn);
			int weight = evaluation + move.weight;
			
			
			boolean condition = isXTurn ? 
					weight >= betterMove.weight : weight <= betterMove.weight;
			if(condition) {
				betterMove.weight = weight;
				betterState = Utils.copy(possibility);
				haveBetterChoice = true;
			}
			
			if(move.depth > betterMove.depth) {
				deepestState = Utils.copy(possibility);
				betterMove.depth = move.depth;
			}
		}
		
		appropriatedState = haveBetterChoice ? betterState : deepestState;
		
		betterMove.depth += 1;
		
		return betterMove;
	}
}

class Move {
	int weight;
	int depth;
	
	public Move(int w, int d) {
		weight = w;
		depth = d;
	}
}
