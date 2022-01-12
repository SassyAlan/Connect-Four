package assignment4Game;

public class Configuration {
	
	public int[][] board;
	public int[] available;
	boolean spaceLeft;
	
	public Configuration(){
		board = new int[7][6];
		available = new int[7];
		spaceLeft = true;
	}
	
	public void print(){
		System.out.println("| 0 | 1 | 2 | 3 | 4 | 5 | 6 |");
		System.out.println("+---+---+---+---+---+---+---+");
		for (int i = 0; i < 6; i++){
			System.out.print("|");
			for (int j = 0; j < 7; j++){
				if (board[j][5-i] == 0){
					System.out.print("   |");
				}
				else{
					System.out.print(" "+ board[j][5-i]+" |");
				}
			}
			System.out.println();
		}
	}
	
	public void addDisk (int index, int player){
        //put a disk
	    int i = 0;
		while (i < 6 && board[index][i] != 0) i ++;
		board[index][i] = player;
		//update available
        available[index] = i + 1;
		//update spaceLeft
        spaceLeft = false;
		for (int a = 0; a < 7; a ++){
		    for (int b = 0; b < 6; b ++){
		        if (board[a][b] == 0) spaceLeft = true;
            }
        }
	}

	public int horizontalCounter (int lastColumn, int player){
        int yPosition = available[lastColumn] - 1;
        //check horizontal
        int countHorizontal = 1;
        int current = lastColumn - 1;
        while(current >= 0 && board[current][yPosition] == player){
            countHorizontal ++;
            current --;
        }
        current = lastColumn + 1;
        while(current < 7 && board[current][yPosition] == player){
            countHorizontal ++;
            current ++;
        }
        return countHorizontal;
    }

    public int vertiCounter(int lastColumn, int player){
        int yPosition = available[lastColumn] - 1;
        int counterVertical = 1;
        int current = yPosition + 1;
        while(current < 6 && board[lastColumn][current] == player){
            counterVertical ++;
            current ++;
        }
        current = yPosition - 1;
        while(current >= 0 && board[lastColumn][current] == player){
            counterVertical ++;
            current --;
        }
        return counterVertical;
    }

    public int rightCounter (int lastColumnPlayed, int player){
	    int yPosition = available[lastColumnPlayed] - 1;
        int counterRightDiagonal = 1;
        int current = lastColumnPlayed - 1;
        int current2 = yPosition + 1;
        while(current >= 0 && current2 < 6 && board[current][current2] == player){
            counterRightDiagonal ++;
            current --;
            current2 ++;
        }
        current = lastColumnPlayed + 1;
        current2 = yPosition - 1;
        while(current < 7 && current2 >= 0 && board[current][current2] == player){
            counterRightDiagonal ++;
            current ++;
            current2 --;
        }
        return counterRightDiagonal;
    }
    public int leftCounter (int lastColumnPlayed, int player){
        int yPosition = available[lastColumnPlayed] - 1;
        int counterLeftDiagonal = 1;
        int current = lastColumnPlayed - 1;
        int current2 = yPosition - 1;
        while(current >= 0 && current2 >= 0 && board[current][current2] == player){
            counterLeftDiagonal ++;
            current --;
            current2 --;
        }
        current = lastColumnPlayed + 1;
        current2 = yPosition + 1;
        while(current < 7 && current2 < 6 && board[current][current2] == player){
            counterLeftDiagonal ++;
            current ++;
            current2 ++;
        }
        return counterLeftDiagonal;
    }
	public boolean isWinning (int lastColumnPlayed, int player){
        //check horizontal
        if (horizontalCounter(lastColumnPlayed, player) >= 4) return true;
        //check vertical
        if (vertiCounter(lastColumnPlayed, player) >= 4)return true;
        //check right diagonal
        if (rightCounter(lastColumnPlayed, player) >= 4) return true;
        //check left diagonal
        if (leftCounter(lastColumnPlayed, player) >= 4) return true;
        return false;
	}

	public void removeDisk(int lastIndex){
        int j = 5;
        while (board[lastIndex][j] == 0) j --;
        board[lastIndex][j] = 0;
        available[lastIndex] --;
    }

	public int canWinNextRound (int player){
	    int column = 10;
        for (int i = 0; i < 7; i ++){
            if (available[i] != 6) {
                addDisk(i, player);
                if (isWinning(i, player) && i < column) column = i;
                removeDisk(i);
            }
        }
        if (column == 10) return -1;
		return column;
	}
	
	public int canWinTwoTurns (int player){
	    int otherPlayer = 0;
        for (int a = 0; a < 7; a ++){
            for (int b = 0; b < 6; b ++){
                if (board[a][b] != player && board[a][b] != 0) otherPlayer = board[a][b];
            }
        }
        for (int i = 0; i < 7; i ++){
            int winCounter = 0;
            if (available[i] != 6) {
                addDisk(i, player);
                for (int j = 0; j < 7; j ++){
                    if (available[j] != 6) {
                        addDisk(j, otherPlayer);
                        if (canWinNextRound(player) != -1) {
                            winCounter ++;
                            removeDisk(j);
                        }
                        else {
                            removeDisk(j);
                            break;
                        }
                    }
                }
                removeDisk(i);
                if (winCounter == 7) return i;
            }
        }
		return -1;
	}
	
}
