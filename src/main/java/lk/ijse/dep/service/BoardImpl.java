package lk.ijse.dep.service;

import lk.ijse.dep.controller.BoardController;

public class BoardImpl implements Board{

    private Piece [][] pieces;
    BoardUI boardUI;

    public BoardImpl(BoardUI boardUI) {
        this.boardUI = boardUI;
    pieces = new Piece[NUM_OF_COLS][NUM_OF_ROWS];
    for (int i = 0; i < pieces.length; i++) {
        for (int j = 0; j < pieces[i].length; j++) {
            pieces[i][j] = Piece.EMPTY;
        }
    }
    }


    @Override
    public BoardUI getBoardUI() {
        return this.boardUI;
    }

    @Override
    public int findNextAvailableSpot(int col) {
        int filled = 0;
        for (int i = 0 ; i < pieces[i].length; i++){
                if (pieces[col][i]!=Piece.EMPTY){
                    filled++;
                }
                if (filled>=5){
                    filled=-1;
                }

        }
        return filled;
    }

    @Override
    public boolean isLegalMove(int col) {
        boolean move = true;

        int result = findNextAvailableSpot(col);
        if (result == -1)move =false;
        return move;
    }

    @Override
    public boolean existLegalMoves() {
        for (int i = 0; i < pieces.length; i++) {
            if (isLegalMove(i)){
                return true;
            }
        }
        return false;
    }
    @Override
    public void updateMove(int col, Piece move) {
        pieces[col][findNextAvailableSpot(col)]=move;
    }
    @Override
    public void updateMove(int col, int row, Piece move) {
        pieces[col][row] = move;
    }
    @Override
    public Winner findWinner() {

        int count=0;

        //Vertically

        for (int i = 0; i < pieces.length; i++){
            for (int j = 0; j < pieces[i].length-1; j++){
                if (pieces[i][j]==pieces[i][j+1]){
                    count++;
                    if (count==3 && pieces[i][j]!=Piece.EMPTY){
                        return new Winner(pieces[i][j],i,(j-2),i,(j+1));
                    }
                }
                else{
                    count=0;
                }
            }
            count=0;
        }

        count=0;

        //Horizontally

        for (int i = 0; i < pieces[0].length; i++){
            for (int j = 0; j < pieces.length-1; j++){
                if (pieces[j][i]==pieces[j+1][i]){
                    count++;
                    if (count==3 && pieces[j][i]!=Piece.EMPTY){
                        return  new Winner(pieces[j][i],(j-2),i,(j+1),i);
                    }
                }
                else{
                    count=0;
                }
            }
            count=0;
        }
        return new Winner(Piece.EMPTY);

    }
}



