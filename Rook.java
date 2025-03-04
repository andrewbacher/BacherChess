/*
Rook class is an instance of Piece:

       boolean isFirstMove: to be used in validate move method (true if pawn has yet to be moved)
    
                            METHODS
    Rook(color): initializes rook to be of Type = rook and Color = color    
        @Param Color color (color of piece)

    validateMove(move): Checks whether move entered is valid for rook
        @Param Move move (move object contains the piece to be moved and where it is moving (TODO: Implement Move class))
        @Return: True if move is valid, false otherwise
*/

public class Rook extends Piece{
   
    private boolean isFirstMove;
    public Rook(Color color){
        super(color);
        this.type = Type.Rook;
        this.isFirstMove = true;
        this.value = 5;
    }

    public boolean isFirst(){
        return isFirstMove;
    }
   

   
    public boolean validateMove(Square c, Square d, Board b,boolean ignore){
      
        int cx = c.getX();
        int cy = c.getY();
        int dx = d.getX();
        int dy = d.getY();


        if(cx != dx && cy != dy){
            return false;
        }
        if(cx == dx && cy == dy){
            return false;
        }

        //rank case
        int yoff;

        if(cy != dy){

            if(cy >= dy){
                yoff = -1;
            }
            else{
                yoff = 1;
            }

            for(int i = cy + yoff; i != dy; i = i + yoff){

                //check if piece is in the way
                if(b.getSquare(cx,i).getPiece() != null){
                    return false;
                }
            }

        }

        //file case
        int xoff;

        if(cx != dx){
            if(cx >= dx){
                xoff = -1;
            }
            else{
                xoff = 1;
            }

            for(int i = cx + xoff; i != dx; i = i + xoff){

                if(b.getSquare(i,cy).getPiece() != null){
                    return false;
                }
            }

        }


        this.isFirstMove = false;
        return true;
    }

}