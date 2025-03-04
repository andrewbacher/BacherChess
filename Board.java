/*
Board class defines the chess board:

   Square[][] grid: 2D array of board squres (8x8)
   Square[] squares: array of all squars on the board
   Board board: board instance
   Square white/blackKingSquare: the square the king is on
   Piece[] pieces: array of all pieces
   
                  METHODS
   Board(): initializes 2D array of board squares
     
   initializePieces(): deletes all pieces on the board and resets with all the pieces in their initial spots
   
   getSquare(x,y): returns the board square at (x,y) coordinates
      @Param int x (Y coordinate)
      @Param int y (Y coordinate)

   getSquares(): returns array of all squares
       @return Square[] squares 

   getKingSquare(color): returns the board square the color king is on
        @param color Color (Color of king)
        @return Square s (Square on which the king resides)

   setPieceArray(): fills the pieces array with all the pieces on the board

   getPieceArray(): returns the pieces array
        @retrun Piece[] (array of all pieces on board)
   
   printBoard(): Prints the board with Xs denoting empty spaces

   clearBoard (): sets all the squares too null (effectively clears the board for reinitializing.)      
   
   */

public class Board{

   private static Square[][] grid = new Square[8][8];

   private static Square[] squares = new Square[64];
   
   private static Board board = new Board();

   private static Piece[] pieces = new Piece[32];

   private static Square whiteKingSquare;

   private static Square blackKingSquare; 
   
   public Board(){
      int c = 0;    
      for(int x = 0; x<8; x++){
         for(int y = 0; y<8; y++){
            grid[x][y] = new Square(x,y);
            squares[c] = grid[x][y];
            c +=1;
         }
      }
   }

   public Square getSquare(int x, int y){
      return grid[x][y];
   }

   public Square[] getSquares(){
          return squares;
   }

   public void setWhiteKingSquare(Square s){
          whiteKingSquare = s;
   }

   public void setBlackKingSquare(Square s){
          blackKingSquare = s;
   }

   public Square getBlackKingSquare(){
          return blackKingSquare;
   }

   public Square getWhiteKingSquare(){
          return whiteKingSquare;
   }

   public void setPieceArray(){
      int i = 0;
      for(int x = 0; x<8; x++){
         for(int y = 0; y<8; y++){
            if(grid[x][y].getPiece() != null && grid[x][y].getPiece().getSquare() != null){
                pieces[i] = grid[x][y].getPiece();
                i+=1;
            }
         }
      }
   }

   public Piece[] getPieceArray(){
          return pieces;
   }
   
   public void printBoard(){
      System.out.println("\t 1    2    3    4    5    6    7    8 \n");  
      for(int y = 0; y<8; y++){
         System.out.print("\t");
         for(int x = 0; x<8; x++){
            if(grid[x][y].getPiece() == null)
               if(x == 7){
                  System.out.print(" |     "+(y+1));
                  System.out.println();
                 // System.out.print(" _  _  _  _  _  _  _  _");

               }
               else 
                  System.out.print(" | ");  
            else
                if(x == 7){
                    System.out.print("|"+grid[x][y].getPiece().printPiece()+ "|    "+(y+1));
                    System.out.println();
                    //System.out.print(" _  _  _  _  _  _  _  _");

                }
                else
                    System.out.print("|"+grid[x][y].getPiece().printPiece()+"|");
            System.out.print("  ");
         }
         System.out.println();
      }
   }
   public void initializeBoard(){
        Piece.Type t = Piece.Type.Pawn;
        Piece.Color c = Piece.Color.Black;
        
        Pawn p = new Pawn(c);
        
        //Black Pawns
        for(int i =0; i<8; i++){
            getSquare(i,1).setPiece(new Pawn(c));
            getSquare(i,1).getPiece().setSquare(getSquare(i,1));
        }


       
        t = Piece.Type.Rook;
        p = new Pawn(c);
      
        //black rooks
        board.getSquare(7,0).setPiece(new Rook(c));
        board.getSquare(7,0).getPiece().setSquare(board.getSquare(7,0));

        board.getSquare(0,0).setPiece(new Rook(c));
        board.getSquare(0,0).getPiece().setSquare(board.getSquare(0,0));
        
        //black knights
        board.getSquare(6,0).setPiece(new Knight(c));
        board.getSquare(6,0).getPiece().setSquare(board.getSquare(6,0));

        board.getSquare(1,0).setPiece(new Knight(c));
        board.getSquare(1,0).getPiece().setSquare(board.getSquare(1,0));
        
        //black bishops
        board.getSquare(5,0).setPiece(new Bishop(c));
        board.getSquare(5,0).getPiece().setSquare(board.getSquare(5,0));

        board.getSquare(2,0).setPiece(new Bishop(c));
        board.getSquare(2,0).getPiece().setSquare(board.getSquare(2,0));
        
        //black king and queen       
        board.getSquare(4,0).setPiece(new King(c));
        board.getSquare(4,0).getPiece().setSquare(board.getSquare(4,0));
        setBlackKingSquare(board.getSquare(4,0));

        board.getSquare(3,0).setPiece(new Queen(c));
        board.getSquare(3,0).getPiece().setSquare(board.getSquare(3,0));
        
        //white pieces
        c = Piece.Color.White;
        
        
        //white pawns
        
        for(int i =0; i<8; i++){
            board.getSquare(i,6).setPiece(new Pawn(c));
            board.getSquare(i,6).getPiece().setSquare(board.getSquare(i,6));
        }
            
        //white rooks

        board.getSquare(7,7).setPiece(new Rook(c));
        board.getSquare(7,7).getPiece().setSquare(board.getSquare(7,7));

        board.getSquare(0,7).setPiece(new Rook(c));
        board.getSquare(0,7).getPiece().setSquare(board.getSquare(0,7));

        //white knights
        board.getSquare(6,7).setPiece(new Knight(c));
        board.getSquare(6,7).getPiece().setSquare(board.getSquare(6,7));

        board.getSquare(1,7).setPiece(new Knight(c));
        board.getSquare(1,7).getPiece().setSquare(board.getSquare(1,7));
        
        //white bishops
        board.getSquare(5,7).setPiece(new Bishop(c));
        board.getSquare(5,7).getPiece().setSquare(board.getSquare(5,7));

        board.getSquare(2,7).setPiece(new Bishop(c));
        board.getSquare(2,7).getPiece().setSquare(board.getSquare(2,7));
        
        //white king and queen       
        board.getSquare(4,7).setPiece(new King(c));
        board.getSquare(4,7).getPiece().setSquare(board.getSquare(4,7));
        setWhiteKingSquare(board.getSquare(4,7));

        board.getSquare(3,7).setPiece(new Queen(c));
        board.getSquare(3,7).getPiece().setSquare(board.getSquare(3,7));

        setPieceArray();
        

   }


   public void clearBoard(){
        for(Square s : squares){
            s.setPiece(null);   
        }
   }

}