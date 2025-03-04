/*
Square class defines a board square:

   int xCoord: x coordinate of the square [1-8]
   int yCoord: y coordinate of the square [1-8]
   piece Piece: The current piece on the square initialized to NULL
   
                     METHODS
   
   Square(x,y): Initializes square to have coordinates x and y, and piece to null
      @Param int x (X coordinate)
      @Param int y (Y coordinate)
   
   getX(): Retruns X coordinate
      @Return int x
   
   getY(): Returns Y coordinate
      @Return int y
   
   getPiece(): returns the piece on the square (null if no piece)
      @Return Piece piece
   
   setPiece(piece): sets the piece on the square to the parameter piece (TODO: should we worry about if there is already a piece on that square or will our Move() method deal with that?)
      @Param Piece piece (piece which will now be on the square) 

*/
public class Square{
   private int xCoord;
   private int yCoord;
   private Piece piece;
   
   public Square(int x, int y){
      this.xCoord = x;
      this.yCoord = y;
      this.piece = null;
   }
   
   public int getX(){
      return this.xCoord;
   }
   
   public int getY(){
      return this.yCoord;
   }
   
   public Piece getPiece(){
      return this.piece;
   }
   
   public void setPiece(Piece piece){
      this.piece = piece;
   }

}