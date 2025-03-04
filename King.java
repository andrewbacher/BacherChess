/*
King class is an instance of Piece
       boolean isFirstMove: to be used in validate move method (true if pawn has yet to be moved)
                            METHODS
    King(color): initializes King to be of Type = King and Color = color    
        @Param Color color (color of piece)

    validateMove(move): Checks whether move entered is valid for King
        @Param Move move (move object contains the piece to be moved and where it is moving (TODO: Implement Move class))
        @Return: True if move is valid, false otherwise
*/

public class King extends Piece{

   public King(Color color){
      super(color);
      this.type = Type.King;
      //this.isFirstMove = true;
   }

   //TODO:Implement this method
   public boolean validateMove(Square c, Square d, Board b,boolean ignore){
      int cx = c.getX();
      int cy = c.getY();
      int dx = d.getX();
      int dy = d.getY();


      //check if castle, both rook and king havent moved and if there are no pieces in between
      if(b.getSquare(cx,cy).getPiece().isFirstMove()) {
         //kingside
     
         if ((cx - dx) == -2 && cy == dy) {
           
            if (b.getSquare(cx + 1, cy).getPiece() != null || b.getSquare(cx + 2, cy).getPiece() != null || b.getSquare(cx+3,dy).getPiece() == null) {
               return false;
            }

          
            if(!b.getSquare(cx+3,dy).getPiece().isFirstMove() || b.getSquare(cx+3,dy).getPiece().getType() != Piece.Type.Rook){
                return false;
            }
            if(ignore)
                return true;
           
            switch(color){
                case White:
           
                    b.getSquare(5,7).setPiece(b.getSquare(7,7).getPiece());
                    b.getSquare(7,7).setPiece(null);
                    b.getSquare(5,7).getPiece().setSquare(b.getSquare(5,7));
                    b.getSquare(5,7).getPiece().setIsFirstMove(false);
                    return true;
                case Black:
                    b.getSquare(5,0).setPiece(b.getSquare(7,0).getPiece());
                    b.getSquare(7,0).setPiece(null);
                    b.getSquare(5,0).getPiece().setSquare(b.getSquare(5,0));
                    return true;
                                    
                default:
                    return false;
            }
           
         } else {
            //queenside
            if ((cx - dx) == 2 && cy == dy) {
              
               if (b.getSquare(cx - 1, cy).getPiece() != null || b.getSquare(cx - 2, cy).getPiece() != null || b.getSquare(cx - 3, cy).getPiece() != null || b.getSquare(cx-4,dy).getPiece() == null) {
                  return false;
               }
              
               if(!b.getSquare(cx-4,dy).getPiece().isFirstMove() || b.getSquare(cx-4,dy).getPiece().getType() != Piece.Type.Rook){
                  return false;
               }

               if(ignore)
                  return true;
               
               switch(color){
                    case White:
                        
                        b.getSquare(3,7).setPiece(b.getSquare(0,7).getPiece());
                        b.getSquare(0,7).setPiece(null);
                        b.getSquare(3,7).getPiece().setSquare(b.getSquare(3,7));
                        return true;
                    case Black:
                        b.getSquare(3,0).setPiece(b.getSquare(0,0).getPiece());
                        b.getSquare(0,0).setPiece(null);
                        b.getSquare(3,0).getPiece().setSquare(b.getSquare(3,0));
                        return true;
                                    
                    default:
                        return false;
               }
            
            }
         }
      }
      if(Math.abs(cy - dy)>1 || Math.abs(cx - dx) >1){
        return false;
      }
        
      return true;
   }

}