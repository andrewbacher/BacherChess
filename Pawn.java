/*
Pawn class is an instance of Piece:

    boolean isFirstMove: to be used in validate move method (true if pawn has yet to be moved)

                            METHODS
    Pawn(color): initializes pawn to be of Type = Pawn and Color = color    
        @Param Color color (color of Pawn)

    validateMove(move): Checks whether move entered is valid for Pawn
        @Param Move move (move object contains the piece to be moved and where it is moving (TODO: Implement Move class))
        @Return: True if move is valid, false otherwise
*/

import java.util.Scanner;


public class Pawn extends Piece{

  

   public Pawn(Color color){
      super(color);
      this.color = color;
      this.type = Type.Pawn;
      this.value = 1;
   }


   //TODO:Implement this method
   public boolean validateMove(Square c, Square d, Board b,boolean ignore){
        int cx = c.getX();
        int cy = c.getY();
        int dx = d.getX();
        int dy = d.getY();

        int yoff;
        if(cy > dy){
            yoff = -1;
        }
        else{
            yoff = 1;
        }

        //if pawn doesnt move
        if(cy == dy){
            return false;
        }

        //if pwan moves backwards
        if(color == Piece.Color.White){
            if(cy < dy)
                return false;
        }
        else{
            if(cy > dy)
                return false;
        }

        //diagonal capture
        if(d.getPiece() != null){
            if(Math.abs(cx-dx) != 1 || Math.abs(cy - dy) != 1 || d.getPiece().getColor() == color)
                return false;
        }
        //if your not capturing, dont change x position
        else{
            if(cx - dx != 0)
                return false;
        }

        //cant move more than 2 spaces
        if(Math.abs(cy-dy) > 2){
                return false;
        }

        
        if(Math.abs(cy-dy) == 2){
            if(b.getSquare(cx,cy+yoff).getPiece() != null || b.getSquare(cx,cy+(yoff*2)).getPiece() != null){
                return false;
            }
            if(!this.isFirstMove()){
                return false;
            }
        }

        
     
        return true;
   }




}