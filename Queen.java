
/*
Queen class is an instance of Piece

                            METHODS
    Queen(color): initializes Queen to be of Type = Queen and Color = color
        @Param Color color (color of piece)

    validateMove(move): Checks whether move entered is valid for Queen
        @Param Move move (move object contains the piece to be moved and where it is moving (TODO: Implement Move class))
        @Return: True if move is valid, false otherwise
*/

public class Queen extends Piece{


   public Queen(Color color){
      super(color);
      this.type = Type.Queen;
      this.value = 9;
   }

   //TODO:Implement this method
   public boolean validateMove(Square c, Square d, Board b,boolean ignore){

      Rook valRook = new Rook(c.getPiece().getColor());
      Bishop valBish = new Bishop(c.getPiece().getColor());

      if(valRook.validateMove(c,d,b,ignore) || valBish.validateMove(c,d,b,ignore)){
         return true;
      }

      return false;
   }



}