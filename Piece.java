/*
Piece Class abstractly defines all pieces:
   
   enum Color: defines color of piece (white or black)
   enum Type: defines the name of the piece (King, Queen, Bishop, Knight, Rook, or Pawn)
   boolean status: defines the status of piece (true if alive, false if dead)
   Square square: defines the square which the piece resides
   boolean isFirstMove: true if the piece has yet to move
   int value: the value of the piece
   
                        METHODS
                        
   Piece(color,type): initializes piece to have a color and a type and status = true and square = NULL
      @Param Color color (white or black) 
      @Param Type type (King, Queen, Bishop, Knight, Rook, or Pawn)
      
   getColor(): returns color
      @Return Color color
   
   getType(): returns type
      @Retun Type type
      
   setStatus(status): sets the status to whatever status is
      @Param boolean status (true or false)
      
   getStatus(): returns status
      @Return boolean status
      
   setSquare(s): sets square to s
      @Param Square s (Square) 
   
   getSquare(): returns the square
      @Retrun Square square

    isFirstMove(): returns true if the piece has not moved
       @return boolean isFirstMove

    setIsFirstMove(b): sets the isFirstMove variable to b

    getValue(): returns the value of the Piece
        @return int value
   
   printPiece():Prints the Piece as a letter, X if piece is null
      
*/


public abstract class Piece{

   public enum Type{
      King, Queen, Bishop, Knight, Rook, Pawn
   }
   
   public enum Color{
      White, Black
   }
   
   protected Type type;
   protected Color color;
   private boolean status;
   private Square square;
   int value;
   private boolean isFirstMove = true;
   

   public Piece(Color color){
          this.color = color;
          this.status = true;
          this.square = null;
          this.value = 0;
   }

   public abstract boolean validateMove(Square c, Square d, Board b, boolean ignore);

   public String getImage(){
      String out = "";
      switch(this.type){
            case King:
            out+= "king";
            break;
            case Knight:
            out+= "knight";
            break;
            case Bishop:
            out+= "bishop";
            break;
            case Rook:
            out+= "rook";
            break;
            case Pawn:
            out+= "pawn";
            break;
            default:
            out+= "queen";
      }
 
      switch(this.color){
            case Black:
            out+= "black";
            break;
            default:
            out+= "white";
      }

      return "chess-pieces/" + out + ".png";
   }
   public Color getColor(){
      return this.color;
   }
   
   public Type getType(){
      return this.type;
   }
   
   public boolean getStatus(){
      return this.status;
   }
   
   public void setStatus(boolean status){
      this.status = status;
   }

   public Square getSquare(){
          return this.square;
   }

   public void setSquare(Square square){
          this.square = square;
   }

   public boolean isFirstMove(){
          return this.isFirstMove;
   }

   public void setIsFirstMove(boolean b){
          this.isFirstMove = b;
   }

   public int getValue(){
      return this.value;
   }
   
   public String printPiece(){
      switch(this.color){
         case White:
            switch(this.type){
               case King:
                  return "K";

               case Bishop:
                  return "B";

               case Knight:
                  return "N";

               case Rook:
                  return "R";

               case Pawn:
                  return "P";
                  
               case Queen:
                  return "Q";

               default:
                  return "X";
            }
         
         case Black:
            switch(this.type){
               case King:
                  return "k";

               case Bishop:
                  return "b";

               case Knight:
                  return "n";

               case Rook:
                  return "r";

               case Pawn:
                  return "p";
                  
               case Queen:
                  return "q";

               default:
                  return "X";
            }
         
         default:
            return "X";
      }
   }
   

}
   