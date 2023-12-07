package ChessPiece;

import GameLogic.GameInstance;
import GameLogic.GameUtil;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public abstract class ChessPiece implements Movable {
    private int posX;
    private int posY;
    private double rate;
    private String pieceUrl;
    private boolean whiteTeam; //white is true black is false
    protected ArrayList<ChessPosition> possibleMoves;
    //public Image icon; //to be removed

    public ChessPiece(int x, int y, boolean t) {
        setTeam(t);
        posX = x;
        posY = y;
        //addEventHandler();
        //icon=new Image(getPieceUrl());
    }


    public boolean isWhite() {
        return whiteTeam;
    }


    public void setTeam(boolean team) {
        this.whiteTeam = team;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getPieceUrl() {
        return pieceUrl;
    }

    public void setPieceUrl(String pieceUrl) {
        this.pieceUrl = pieceUrl;
    }

    private void addEventHandler(){
        setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setCurrentAllPossibleMoves();
            }
        });
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getPosY() {
        return posY;
    }

    public String toString() {
        if (isWhite()) {
            if (this instanceof Pawn) {
                return "White Pawn at " + getChessNotation(posX, posY);
            } else if (this instanceof Knight) {
                return "White Knight at " + getChessNotation(posX, posY);
            } else if (this instanceof Rook) {
                return "White Rook at " + getChessNotation(posX, posY);
            } else if (this instanceof Queen) {
                return "White Queen at " + getChessNotation(posX, posY);
            } else if (this instanceof King) {
                return "White King at " + getChessNotation(posX, posY);
            }
        } else {
            if (this instanceof Pawn) {
                return "Black Pawn at " + getChessNotation(posX, posY);
            } else if (this instanceof Knight) {
                return "Black Knight at " + getChessNotation(posX, posY);
            } else if (this instanceof Rook) {
                return "Black Rook at " + getChessNotation(posX, posY);
            } else if (this instanceof Queen) {
                return "Black Queen at " + getChessNotation(posX, posY);
            } else if (this instanceof King) {
                return "Black King at " + getChessNotation(posX, posY);
            }
        }
        return "Invalid";
    }

    private String getChessNotation(int x, int y) {
        char file = (char) ('A' + x - 1);
        char rank = (char) ('1' + y - 1);
        return String.valueOf(file) + rank;
    }

    public abstract void setCurrentAllPossibleMoves();

    public boolean move(int x, int y){
        if (isValidMove(x,y)){
            if (GameInstance.getInstance().getChessPieceAt(x,y)==null){
                GameInstance.getInstance().setChessPieceAt(getPosX(),getPosY(),null);
                setPosY(y);
                setPosX(x);
                GameInstance.getInstance().setChessPieceAt(getPosX(),getPosY(),this);
                System.out.println(this+" moved to "+getPosX()+" "+getPosY());
            }
            else{
                //attack
                if (GameUtil.attack(this,GameInstance.getInstance().getChessPieceAt(x,y),x,y)){ //successfully attacked
                    GameInstance.getInstance().setChessPieceAt(getPosX(),getPosY(),null);
                    setPosY(y);
                    setPosX(x);
                    GameInstance.getInstance().setChessPieceAt(getPosX(),getPosY(),this);
                    System.out.println(this +" killed an enemy at "+getPosX()+" "+getPosY());
                }
                else{ //failed to attack, killed
                    GameInstance.getInstance().setChessPieceAt(this.getPosX(),this.getPosY(),null);
                    setPosX(-1);
                    setPosY(-1);
                    System.out.println(this+" failed to kill, got revenged to death.");
                }
            }
            return true; //successfully moved, this can move with or w/o attack or can be revenged to death
        }
        return false; //invalid Move,new input needed
    }
}

