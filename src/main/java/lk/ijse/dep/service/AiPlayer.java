package lk.ijse.dep.service;

import java.util.ArrayList;
import java.util.List;

public class AiPlayer extends Player {

    public AiPlayer(Board newBoard) {
        super(newBoard);
    }

    public void movePiece(int col) {
        col = bestMove();
        board.updateMove(col, Piece.GREEN);
        board.getBoardUI().update(col, false);
        Winner winner = board.findWinner();
        if (winner.getWinningPiece() == Piece.GREEN) {
            board.getBoardUI().notifyWinner(winner);
        } else if (!board.existLegalMoves()) {
            board.getBoardUI().notifyWinner(new Winner(Piece.EMPTY));
        }
    }

    private int bestMove() {
        boolean isUserWinning = false;
        int tiedColumn = 0;

        int i;
        for(i = 0; i < 6; ++i) {
            if (board.isLegalMove(i)) {
                int row = board.findNextAvailableSpot(i);
                board.updateMove(i, Piece.GREEN);
                int heuristicVal = minimax(0, false);
                board.updateMove(i, row, Piece.EMPTY);
                if (heuristicVal == 1) {
                    return i;
                }

                if (heuristicVal == -1) {
                    isUserWinning = true;
                } else {
                    tiedColumn = i;
                }
            }
        }

        if (isUserWinning && board.isLegalMove(tiedColumn)) {
            return tiedColumn;
        } else {
            // Increase randomness in decision-making
            List<Integer> legalMoves = new ArrayList<>();
            for (i = 0; i < 6; i++) {
                if (board.isLegalMove(i)) {
                    legalMoves.add(i);
                }
            }
            // Select a random move with higher probability
            if (Math.random() < 0.8) {
                return tiedColumn; // Return a potentially suboptimal move
            } else {
                return legalMoves.get((int) (Math.random() * legalMoves.size()));
            }
        }
    }




    private int minimax(int depth, boolean maximizingPlayer) {
        Winner winner = board.findWinner();
        if (winner.getWinningPiece() == Piece.GREEN) {
            return 1;
        } else if (winner.getWinningPiece() == Piece.BLUE) {//
            return -1;
        } else if (board.existLegalMoves() && depth != 2) {
            int i;
            int row;
            int heuristicVal;
            if (!maximizingPlayer) {
                for(i = 0; i < 6; ++i) {
                    if (board.isLegalMove(i)) {
                        row = board.findNextAvailableSpot(i);
                        board.updateMove(i, Piece.BLUE);
                        heuristicVal = this.minimax(depth + 1, true);
                        board.updateMove(i, row, Piece.EMPTY);
                        if (heuristicVal == -1) {
                            return heuristicVal;
                        }
                    }
                }
            } else {
                for(i = 0; i < 6; ++i) {
                    if (board.isLegalMove(i)) {
                        row = board.findNextAvailableSpot(i);
                        board.updateMove(i, Piece.GREEN);
                        heuristicVal = minimax(depth + 1, false);
                        board.updateMove(i, row, Piece.EMPTY);
                        if (heuristicVal == 1) {
                            return heuristicVal;
                        }
                    }
                }
            }
            return 0;
        } else {
            return 0;
        }
    }

}