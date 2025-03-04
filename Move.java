
//need enum for return value


import java.util.Scanner;

public class Move {

    //instance variables

    private Piece piece;
    private Piece captured;
    private Board board;
    private Square currLoc;
    private Square destLoc;
    private boolean isFirst;
    private String moveNotation;


    /**
     *
     *
     */
    public Move(Square c, Square d, Board b){

        this.currLoc = c;
        this.destLoc = d;
        this.piece = c.getPiece();
        this.board = b;
    }

    public MoveReturn validate(boolean ignore){
        MoveReturn validate = new MoveReturn();
        if(currLoc.getPiece() == null)
            return validate;
        //check if invalid move
        if(piece.validateMove(currLoc,destLoc,board,ignore)) {
            //move is valid and there is a piece there

            //need to add !castle
            if(destLoc.getPiece() != null && destLoc.getPiece().getColor() == currLoc.getPiece().getColor()){
                return validate;
            }
            if(ignore)
                validate.setValid(true);
            if(!ignore){
                if (destLoc.getPiece() != null) {
                    this.captured = destLoc.getPiece();
                    validate.setCaptured(captured);
                    captured.setSquare(null);
                }

                //move piece in board 
                //instead, move the piece in Game.java
                destLoc.setPiece(piece);
                currLoc.setPiece(null);
                piece.setSquare(destLoc);

                //if king move, set King square to destination
                if(piece.getType() == Piece.Type.King){
                    if(piece.getColor() == Piece.Color.White){
                        board.setWhiteKingSquare(destLoc);
                    }
                    else{
                        board.setBlackKingSquare(destLoc);
                    }
                    
                }

                if(piece.getType() == Piece.Type.Pawn && !movedIntoCheck(piece)){

                    if(isSwitch(destLoc)){
                        
                        Queen q = new Queen(piece.color);
                        destLoc.setPiece(q);
                        destLoc.getPiece().setSquare(destLoc);


                    }


                }

                //set the board array in case of any changes
                board.setPieceArray();

                //save the first move state of the piece in case we need to undo the move
                this.isFirst = piece.isFirstMove();

                //check if you moved your king into check
                if(movedIntoCheck(piece)){
                    undoMove();
                    return validate;
                }

                //piece removal should happen piece wise?, maybe return change in x and y for each piece


                validate.setMoveLoc(destLoc.getX() + "," + destLoc.getY());
                validate.setValid(true);
                //this.isFirst = piece.isFirstMove();
                piece.setIsFirstMove(false);
           
                validate.setCheck(isCheck());
                setMoveNotation();
                
            }
        }


        return validate;
    }

    public boolean isSwitch(Square d){

        //white side
        if(piece.getColor() == Piece.Color.White && d.getY() == 0){

            return true;

        }else return piece.getColor() == Piece.Color.Black && d.getY() == 7;
    }


    public boolean isCheck(){
        Piece [] pieces = board.getPieceArray();
        Move check;

        for(Piece p : pieces){
            if(piece.getColor() == Piece.Color.White && p != null && p.getSquare() != null && board.getBlackKingSquare() != null){
                check = new Move(p.getSquare(),board.getBlackKingSquare(),board);
                if(check.validate(true).getValid()){
                    return true;
                }
            }
            if(piece.getColor() == Piece.Color.Black && p != null && p.getSquare() != null && board.getBlackKingSquare() != null){
                check = new Move(p.getSquare(),board.getWhiteKingSquare(),board);
                if(check.validate(true).getValid()){
                    return true;
                }
            }
        }
        return false;
    }

    public Square getCurrLoc(){
        return currLoc;
    }

    public Square getDestLoc(){
        return destLoc;
    }

    public boolean movedIntoCheck(Piece piece){
        Piece [] pieces = board.getPieceArray();
        Move check;

        for(Piece p : pieces){
            
            if(piece.getColor() == Piece.Color.White && p != null && p.getSquare() != null){
                check = new Move(p.getSquare(),board.getWhiteKingSquare(),board);
                if(check.validate(true).getValid()){                                                                                                      
                    return true;
                }
            }

            if(piece.getColor() == Piece.Color.Black && p != null && p.getSquare() != null){
                check = new Move(p.getSquare(),board.getBlackKingSquare(),board);
                if(check.validate(true).getValid()){
                    
                    return true;
                }                                                                        
            }

            if(piece.getType() == Piece.Type.King && (Math.abs(currLoc.getX()-destLoc.getX())==2) && (piece.getColor() !=p.getColor())){//if castle
                //try to attack king on the passing squares and the original square
                Move check1 = new Move(p.getSquare(),board.getSquare(currLoc.getX()-1,currLoc.getY()),board);
               
                Move check2 = new Move(p.getSquare(),board.getSquare(currLoc.getX()+1,currLoc.getY()),board);
                Move check3 = new Move(p.getSquare(),currLoc,board);
                   
                if(check1.validate(true).getValid() 
                || check2.validate(true).getValid() 
                || check3.validate(true).getValid()){//if king is attacked, castle move is invalid
                    return true;    
                }
            }
        }

        return false;
    }

    public void undoMove(){
        //if you captured a piece, put it back
        if(captured != null){
            destLoc.setPiece(captured);
            captured.setSquare(destLoc);
        }
        //otherwise, set the destination square to nothing
        else{
            destLoc.setPiece(null);
        }

        //move the piece back to original square
        currLoc.setPiece(piece);
        piece.setSquare(currLoc);

        //if the piece you moved was a king, reset the King Square on board
        if(piece.getType() == Piece.Type.King){
            if(piece.getColor() == Piece.Color.White){
                board.setWhiteKingSquare(currLoc);
                if(currLoc.getX()-destLoc.getX() == -2){
                    //undo white Kingside castle
                    board.getSquare(7,7).setPiece(board.getSquare(5,7).getPiece());
                    board.getSquare(5,7).setPiece(null);
                    board.getSquare(7,7).getPiece().setSquare(board.getSquare(7,7));
                    board.getSquare(7,7).getPiece().setIsFirstMove(true);
                    piece.setIsFirstMove(true);
                }
                if(currLoc.getX() - destLoc.getX() == 2){
                    //undo white queenside castle
                    board.getSquare(0,7).setPiece(board.getSquare(3,7).getPiece());
                    board.getSquare(3,7).setPiece(null);
                    board.getSquare(0,7).getPiece().setSquare(board.getSquare(0,7));
                    board.getSquare(0,7).getPiece().setIsFirstMove(true);
                    piece.setIsFirstMove(true);
                }
            }
            else{
                board.setBlackKingSquare(currLoc);
                if(currLoc.getX()-destLoc.getX() == -2){
                    //undo black Kingside castle
                    board.getSquare(7,0).setPiece(board.getSquare(5,0).getPiece());
                    board.getSquare(5,0).setPiece(null);
                    board.getSquare(7,0).getPiece().setSquare(board.getSquare(7,0));
                    board.getSquare(7,0).getPiece().setIsFirstMove(true);
                    piece.setIsFirstMove(true);
                }
                if(currLoc.getX() - destLoc.getX() == 2){
                    //undo white queenside castle
                    board.getSquare(0,0).setPiece(board.getSquare(3,0).getPiece());
                    board.getSquare(3,0).setPiece(null);
                    board.getSquare(0,0).getPiece().setSquare(board.getSquare(0,0));
                    board.getSquare(0,0).getPiece().setIsFirstMove(true);
                    piece.setIsFirstMove(true);
                }
            }               
        }

        //let the devs know
        //System.out.println("YOU MOVED INTO CHECK, DUMMY");

        piece.setIsFirstMove(isFirst);//set the first move status to whatever it was before

        board.setPieceArray();
    }

    public Piece getCaptured(){
        return captured;
    }

    public void setMoveNotation(){
        if(piece.getType() != Piece.Type.Pawn)
            moveNotation = (piece.printPiece()).toUpperCase();
        else{
            moveNotation = " ";
        }
            
        if(captured != null){
            moveNotation +="x";
        }
        switch(destLoc.getX()){
            case 0:
                moveNotation += "a";
                break;
            case 1:
                moveNotation += "b";
                break;
            case 2:
                moveNotation += "c";
                break;
            case 3:
                moveNotation += "d";
                break;
            case 4:
                moveNotation += "e";
                break;
            case 5:
                moveNotation += "f";
                break;
            case 6:
                moveNotation += "g";
                break;
            case 7:
                moveNotation += "h";
                break;
            default:
                break;
        }
        moveNotation += destLoc.getY()+1;

        if(piece.getType() == Piece.Type.King && (currLoc.getX()-destLoc.getX())==2){
            moveNotation = "O-O-O";
        }
        if(piece.getType() == Piece.Type.King && (currLoc.getX()-destLoc.getX())==-2){
            moveNotation = "O-O";
        }
        if(isCheck())
            moveNotation += "+";
        
    }
    public String getMoveNotation(){
        return moveNotation;
    }

}
