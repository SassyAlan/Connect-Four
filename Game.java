package assignment4Game;

import java.io.*;

public class Game {
	
	public static int play(InputStreamReader input){
		BufferedReader keyboard = new BufferedReader(input);
		Configuration c = new Configuration();
		int columnPlayed = 3; int player;
		
		// first move for player 1 (played by computer) : in the middle of the grid
		c.addDisk(firstMovePlayer1(), 1);
		int nbTurn = 1;
		
		while (nbTurn < 42){ // maximum of turns allowed by the size of the grid
			player = nbTurn %2 + 1;
			if (player == 2){
				columnPlayed = getNextMove(keyboard, c, 2);
			}
			if (player == 1){
				columnPlayed = movePlayer1(columnPlayed, c);
			}
			System.out.println(columnPlayed);
			c.addDisk(columnPlayed, player);
			if (c.isWinning(columnPlayed, player)){
				c.print();
				System.out.println("Congrats to player " + player + " !");
				return(player);
			}
			nbTurn++;
		}
		return -1;
	}
	
	public static int getNextMove(BufferedReader keyboard, Configuration c, int player) {
        // ADD YOUR CODE HERE
        c.print();
        int i;
        while (true) {
            try {
                System.out.println("wut do u want: ");
                i = Integer.parseInt(keyboard.readLine());
                if ((c.available[i] != 6)) {
                    return i;
                }
            } catch (Exception e) {
                System.out.println("get it right");
            }
        }
    }
	
	public static int firstMovePlayer1 (){
		return 3;
	}
	
	public static int movePlayer1 (int columnPlayed2, Configuration c){
	    if (c.canWinNextRound(1) != -1) {
	        System.out.println("get ready to lose");
	        return c.canWinNextRound(1);
        }
	    else if (c.canWinTwoTurns(1) != -1) {
	        System.out.println("lose in 2!");
	        return c.canWinTwoTurns(1);
        }
	    else if (c.available[columnPlayed2] != 6) return columnPlayed2;
	    else{
	        for (int i = 0; i < 7; i ++){
	            if (columnPlayed2 - i > 0 && c.available[columnPlayed2 - i] != 6) return columnPlayed2 - i;
	            else if (columnPlayed2 + i < 7 && c.available[columnPlayed2 + i] != 6) return columnPlayed2 + i;
            }
        }
        return 0;
	}
	
}
