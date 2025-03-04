/*
Knight class is an instance of Piece

                            METHODS
    Knight(color): initializes Knight to be of Type = Knight and Color = color    
        @Param Color color (color of piece)

    validateMove(move): Checks whether move entered is valid for Knight
        @Param Move move (move object contains the piece to be moved and where it is moving (TODO: Implement Move class))
        @Return: True if move is valid, false otherwise
*/

public class Knight extends Piece{
   
   public Knight(Color color){
      super(color);
      this.type = Type.Knight;
      this.value = 3;
   }

   //TODO:Implement this method

   public boolean validateMove(Square c, Square d, Board b,boolean ignore){

         int cx = c.getX();
         int cy = c.getY();
         int dx = d.getX();
         int dy = d.getY();

         if(cx == dx || cy == dy){
            return false;
         }

         if(Math.abs(dx-cx)==2 && Math.abs(dy-cy)==1)
            return true;

         if(Math.abs(dx-cx)==1 && Math.abs(dy-cy)==2)
            return true;
         
         return false;
   }



}