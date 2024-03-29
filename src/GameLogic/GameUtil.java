package GameLogic;

import ChessPiece.ChessPiece;

import java.util.Random;

public class GameUtil {
    public static boolean successAttack(double attacker, double defender) {
        double tmp = (attacker) / (defender + attacker);
        double randomValue = new Random().nextDouble();
        return randomValue <= tmp;
    }

    public static boolean inRangeOfBoard(int x, int y) {
        return (x >= 0 && y >= 0 && x < 8 && y < 8);
    }

    public static boolean attack(ChessPiece attacker, ChessPiece defender, int x, int y) {
        if (successAttack(attacker.getRate(), defender.getRate())) {
            GameLogic.getInstance().setChessPieceAt(x, y, attacker); //set new position
            defender.setPosX(-10);
            defender.setPosY(-10);
            return true;
        } else { //got revenged
            return false;
        }
    }

    public static boolean isClearPath(int currentX, int currentY, int finalX, int finalY, ChessPiece thisPiece) {
        //this is used after making sure the path is can only be diagonal,vertical or horizontal
        //finalX,Y are also certainly on the board
        int dy = finalY - currentY;
        int dx = finalX - currentX;
        int ix = currentX;
        int iy = currentY;
        if (dy == 0) { // checking horizontal path
            int directionX = (dx > 0) ? 1 : -1;
            for (int i = 0; i < Math.abs(dx) - 1; i++) {
                ix += directionX;
                if (GameLogic.getInstance().getChessPieceAt(ix, iy) != null)
                    return false;
            }
        } else if (dx == 0) { // checking vertical path
            int directionY = (dy > 0) ? 1 : -1;
            for (int i = 0; i < Math.abs(dy) - 1; i++) {
                iy += directionY;
                if (GameLogic.getInstance().getChessPieceAt(ix, iy) != null)
                    return false;
            }
        } else { //checking diagonal path
            int directionX = (dx > 0) ? 1 : -1;
            int directionY = (dy > 0) ? 1 : -1;
            for (int i = 0; i < Math.abs(dx) - 1; i++) {
                ix += directionX;
                iy += directionY;
                //if any square in the path contains a teammate, it's not clear
                if (GameLogic.getInstance().getChessPieceAt(ix, iy) != null)
                    return false;
            }
        }
        return true;
    }
}
