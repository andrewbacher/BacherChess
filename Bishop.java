/*
Bishop class is an instance of Piece

                            METHODS
    Bishop(color): initializes Bishop to be of Type = Bishop and Color = color    
        @Param Color color (color of piece)

    validateMove(move): Checks whether move entered is valid for Bishop
        @Param Move move (move object contains the piece to be moved and where it is moving (TODO: Implement Move class))
        @Return: True if move is valid, false otherwise
*/

public class Bishop extends Piece{


   public Bishop(Color color){

      super(color);
      this.type = Type.Bishop;
      this.value = 3;

   }

   //TODO:Implement this method
   public boolean validateMove(Square c, Square d, Board b,boolean ignore){

         //file is x --> col
         //row is y --> row

         int cx = c.getX();
         int cy = c.getY();
         int dx = d.getX();
         int dy = d.getY();

         //piece didnt move
         if(cx == dx || cy == dy){
            return false;
         }

         if(Math.abs(dy - cy) != Math.abs(dx - cx)){
            return false;
         }

         int yoff;
         int xoff;

         if(cy < dy){
            yoff = 1;
         }else{
            yoff = -1;
         }

         if(cx < dx){
            xoff = 1;
         }else{
            xoff = -1;
         }
         int y = cy + yoff;
         
         for(int x = cx + xoff; x != dx; x+=xoff){
            
            if(b.getSquare(x,y).getPiece() != null){
                return false;
            }
            if(y < 7 && y > 0)
                y += yoff;

         }


          return true;
   }

   //public int getValue(){
      //return this.value;
   //}



}