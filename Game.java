/*
Game class starts a game of chess:

    Board board: board instance
   
                  METHODS
    Main(): main program that prints the board to consol

    getBoard(): returns board
        @return Board board (current board) 

    newGame(): resets all points, pieces, checkmates etc.

    getScore(): returns an array of the two scores
        @return int[] scores (whiteScore,blackScore)

    getWhiteMove(): called as long as it is not checkmate, takes user input and converts it to a move, validates move and returns true if move is legal
      5755moves should be in coordinate form with spaces between everything (move: 1 1 2 2 moves the piece at square 1,1 to square 2,2)                      
      @return true if valid move, false otherwise
   
    guiMove(): used for chessGUI only, takes input of a move generated in ChessGUI.java and moves the piece 

    getBlackMove(): only used if playing with two players in command line. Takes user input and checks validit of Move
        @return true if valid move, false otherwise
   
   getBlackMoveOnePlayer(): random generated coordinates are used as black move   

   isCheckmate(): sets isCheckmate to true if all dodge moves are impossible
      
   set path=%path%;C:\Program Files\Java\jdk1.8.0_161\bin (this is just for Andrew's compiling purposes)
   */

import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;

public class Game{

    private static Board board = new Board();
    private static boolean isCheckmate = false;
    private static boolean blackCheck = false;
    private static boolean whiteCheck = false;
    private static ArrayList<Piece> whiteCapturedPieces = new ArrayList<Piece>();
    private static ArrayList<Piece> blackCapturedPieces = new ArrayList<Piece>();
    private static int blackScore = 0;
    private static int whiteScore = 0;
    public static String checkmateString;
    private static String checkString;
    private static ArrayList<String> moves = new ArrayList<String>();

    public static void main(String [] args){

        board.initializeBoard();      
        
        while(!isCheckmate){
            //System.out.println("White Score: "+whiteScore);
            //System.out.println("Black Score: "+blackScore);
            System.out.println("");
            board.printBoard();
            System.out.println("");

            while(!getWhiteMove()){

            }
            // System.out.println("");
            // board.printBoard();
            // System.out.println("");
            if(!isCheckmate){
                //while(!getBlackMove()){
              
                //}
                while(!getBlackMoveOnePlayer()){

                }
            }


        }


    }
   
    public Board getBoard(){
        return board;
    }

    public ArrayList<Piece> getWhiteCapturedPieces(){return whiteCapturedPieces;}

    public ArrayList<Piece> getBlackCapturedPieces(){return blackCapturedPieces;}


    public boolean getIsCheckmate(){
        return isCheckmate;
    }

    public void newGame(){
        
        isCheckmate = false;
        whiteCheck = false;
        blackCheck = false;

        whiteScore = 0;
        blackScore = 0;
        moves.clear();
        whiteCapturedPieces.clear();
        blackCapturedPieces.clear();

        board.clearBoard();
        board.initializeBoard();
        
    }

    public String getCheckmateString(){
        return checkmateString;
    }

    public String getCheckString(){
        return checkString;
    }

  

    public int[] getScore(){
        int[] score = new int[2];
        score[0]= whiteScore;
        score[1] = blackScore;
        return score;
    }

    public ArrayList<String> getMovesArray(){
        return moves;
    }

    public static boolean getWhiteMove(){

        //get user input
        Scanner sc = new Scanner(System.in);
        System.out.println("White's Move: ");
        String userMove = sc.nextLine();

        //parese the move, if move is not 4 coordinates: invalid input
        String[] parsedMove = userMove.split("[ ]+");

        if(parsedMove.length != 4){
            System.out.println("Invalid coordinates, try againxxx");
            return false;
        }

        int[] move = {0,0,0,0};// make an array to hold the corrdinates
        for(int i = 0; i <4; ++i){
            switch(parsedMove[i]){//turn user input into 4 integers 
                case "1":
                    move[i] = 0;
                    break;
                case "2":
                    move[i] = 1;
                    break;
                case "3":
                    move[i] = 2;
                    break;
                case "4":
                    move[i] = 3;
                    break;
                case "5":
                    move[i] = 4;
                    break;
                case "6":
                    move[i] = 5;
                    break;
                case "7":
                    move[i] = 6;
                    break;
                case "8":
                    move[i] = 7;
                    break;
                default://invalid coordinate 
                    System.out.println("Invalid Move, try again");
                    return false;
            }
        }
       
        if(board.getSquare(move[0],move[1]).getPiece() == null || board.getSquare(move[0],move[1]).getPiece().getColor() == Piece.Color.Black){
            System.out.println("Invalid piece selected, try again");
            return false;
        }
        Square origin = board.getSquare(move[0],move[1]);
        Square destination = board.getSquare(move[2],move[3]);

        Move m = new Move(origin,destination,board);
        

        MoveReturn mr = m.validate(false);
        if(!mr.getValid()){
            System.out.println("MOVE VALIDATOR Invalid move try again");
            return false;
        }

        if(mr.getCaptured() != null)//if captured opponent piece
             whiteCapturedPieces.add(mr.getCaptured());//add to captured piece ArrayList

        if(mr.is_check()){//check for checkmate
            System.out.println("BLACK IN CHECK");
            whiteCheck = true;
            if(isCheckmate()==true){
                System.out.println("CHECKMATE White wins!");
                board.printBoard();
                isCheckmate = true;
            }         
        }

        if(!mr.is_check())
            whiteCheck = false;

        moves.add(m.getMoveNotation()+"\n");//add to move array

        return true;
    }

    public static void guiMove(Move m, Piece p){

        MoveReturn mr = m.validate(false);//validate and move the piece on board

        if(mr.getCaptured() != null){// if move captured a piece 

            if(mr.getCaptured().getColor() == Piece.Color.Black){// if captured piece is black

                whiteScore += mr.getCaptured().getValue();//add to white score
                whiteCapturedPieces.add(mr.getCaptured());//add to captured piece ArrayList

            }
            else{
                blackScore += mr.getCaptured().getValue();
                blackCapturedPieces.add(mr.getCaptured());
            }
         
        }

        if(mr.is_check()){//if opponent in check

            checkString = "CHECK";// write to string

            if(p.getColor() == Piece.Color.White){
                whiteCheck = true;
                
                if(isCheckmate()==true){//if checkmate

                    checkString = "";//reset check
                    checkmateString = "CHECKMATE White wins! \n White Score: "+whiteScore +"\n Black Score: "+blackScore;//for GUI, announces the winner
            
                    board.printBoard();//draw board in consol
                    isCheckmate = true;
                }
            }

            else{//same as above but for black
                
                blackCheck = true;
         
                
                if(isCheckmate()==true){
                    checkString = "";
                    System.out.println("CHECKMATE");
                    checkmateString = "CHECKMATE Black wins! \n White Score: "+whiteScore +"\n Black Score: "+blackScore;
            
                    board.printBoard();
                    isCheckmate = true;
                }
            }
            
        }

        if(!mr.is_check()){// if opponent not in check

            checkString = "";//reset checkString

            //check for draw
            if(p.getColor()== Piece.Color.White)
                whiteCheck = true;
            else
                blackCheck = true;
            if(isCheckmate()==true){
                checkmateString = "DRAW! \n White Score: "+whiteScore +"\n Black Score: "+blackScore;
                System.out.println("DRAW!");
                board.printBoard();
                isCheckmate = true;

            }
            blackCheck = false;
       

            //reset white or black check
            if(p.getColor()== Piece.Color.White)
                whiteCheck = false;
            else
                blackCheck = false;
        }

        //add move name to ArrayList
        if(p.getColor()== Piece.Color.White)
            moves.add(m.getMoveNotation()+" ");
        else
            moves.add(m.getMoveNotation()+"\n");

     
    }

    public static boolean getBlackMove(){
       
        //TWO PLAYER MODE 
        //same code as getWhiteMove with minor changes

        Scanner sc = new Scanner(System.in);
        System.out.println("Black move: ");
        String userMove = sc.nextLine();


        String[] parsedMove = userMove.split("[ ]+");
        if(parsedMove.length != 4){
            System.out.println("Invalid coordinates, try again");
            return false;
        }
        int[] move = {0,0,0,0};
        for(int i = 0; i <4; ++i){
            switch(parsedMove[i]){
                case "1":
                    move[i] = 0;
                    break;
                case "2":
                    move[i] = 1;
                    break;
                case "3":
                    move[i] = 2;
                    break;
                case "4":
                    move[i] = 3;
                    break;
                case "5":
                    move[i] = 4;
                    break;
                case "6":
                    move[i] = 5;
                    break;
                case "7":
                    move[i] = 6;
                    break;
                case "8":
                    move[i] = 7;
                    break;
                default:
                    System.out.println("Invalid Move, try again");
                    return false;
            }
        }
       
        if(board.getSquare(move[0],move[1]).getPiece() == null || board.getSquare(move[0],move[1]).getPiece().getColor() == Piece.Color.White){
            System.out.println("Invalid piece selected, try again");
            return false;
        }
        Square origin = board.getSquare(move[0],move[1]);
        Square destination = board.getSquare(move[2],move[3]);

        Move m = new Move(origin,destination,board);
        

        MoveReturn mr = m.validate(false);
        if(!mr.getValid()){
            System.out.println("MOVE VALIDATOR: Invalid move try again");
            return false;
        }

        if(mr.getCaptured() != null){
            blackScore += mr.getCaptured().getValue();
            blackCapturedPieces.add(mr.getCaptured());
        }

        if(mr.is_check()){
            System.out.println("WHITE IN CHECK");
            blackCheck = true;
            if(isCheckmate()==true){
                checkmateString = "CHECKMATE Black wins! \n White Score: "+whiteScore +"\n Black Score: "+blackScore;
                System.out.println("CHECKMATE Black wins!");
                board.printBoard();
                isCheckmate = true;
            }
            
        }
        if(!mr.is_check()){
            blackCheck = true;
            if(isCheckmate()==true){
                checkmateString = "DRAW! \n White Score: "+whiteScore +"\n Black Score: "+blackScore;
                System.out.println("DRAW!");
                board.printBoard();
                isCheckmate = true;

            }
            blackCheck = false;
        }

        moves.add(m.getMoveNotation()+"\n");
        
        return true;
    }

    public static boolean getBlackMoveOnePlayer(){
        Random rand = new Random();
        int originX = rand.nextInt(8);
        int originY = rand.nextInt(8);
        int destinationX = rand.nextInt(8);
        int destinationY = rand.nextInt(8);

        if(board.getSquare(originX,originY).getPiece() == null || board.getSquare(originX,originY).getPiece().getColor() == Piece.Color.White){
            return false;
        }

        Square origin = board.getSquare(originX,originY);
        Square destination = board.getSquare(destinationX,destinationY);
     
        Move m = new Move(origin,destination,board);
        MoveReturn mr = m.validate(false);
        if(!mr.getValid()){
            
            return false;
        }


        if(mr.getCaptured() != null){
            blackScore += mr.getCaptured().getValue();
            blackCapturedPieces.add(mr.getCaptured());
            
        }
        //this statement prints what piece moves and where
       // System.out.println("Black moves "+board.getSquare(destinationX,destinationY).getPiece().printPiece()+" from "+(originX+1)+","+(originY+1)+" to "+(destinationX+1)+","+(destinationY+1));

       if(mr.is_check()){
            //System.out.println("WHITE IN CHECK");
            checkString = "CHECK";
            blackCheck = true;
            if(isCheckmate()==true){
                checkmateString = "CHECKMATE Black wins! \n White Score: "+whiteScore +"\n Black Score: "+blackScore;
              //  System.out.println("CHECKMATE Black wins!");
                board.printBoard();
                isCheckmate = true;
            }
            
       }
       if(!mr.is_check()){
            blackCheck = false;
            checkString = "";
       }
       moves.add(m.getMoveNotation()+"\n");

        return true;
    }

    public static boolean isCheckmate(){
        
        Piece [] pieces = board.getPieceArray();
        Move dodge;
        Square [] squares = board.getSquares();
        isCheckmate = true;

        if(whiteCheck){
            for(Piece p : pieces){
                if(p != null && p.getColor() == Piece.Color.Black){
                
                    
                    for(Square s : squares){
                       
                        
                        if(s != null && p.getSquare() != null){
                            
                            //System.out.println(s.getX()+ " "+ s.getY());
                            dodge = new Move(p.getSquare(),s,board);
                            if(dodge.validate(false).getValid()){
    
                                dodge.undoMove();                                    
                                isCheckmate = false;
                                return false;
                                    
                            }                                
                        }
                    }   
                }
            }
        }

        if(blackCheck){
            for(Piece p : pieces){

                if(p != null && p.getColor() == Piece.Color.White){
                    
                    for(Square s : squares){

                        if(s != null && p.getSquare() != null){
                            dodge = new Move(p.getSquare(),s,board);
                            if(dodge.validate(false).getValid()){
                                    
                                dodge.undoMove();                                                                       
                                isCheckmate = false;
                                return false;                                        
                                
                            }
                        }
                    }   
                }
            }
        }
        return true;
    }

}