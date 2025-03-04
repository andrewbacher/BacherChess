import javafx.application.Application; 
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import java.io.*;
import java.util.*;
import javafx.scene.Group; 
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.ScrollPane;
/***
 * Graphical User Interface for Sprint II
 *
 * event handles, drawing, validations, user input, etc...
 *
 */

public class ChessGUI extends Application{

    private BorderPane border = new BorderPane();
    private GridPane board;
    private Game game;

    
    //check or not check
    private Text status = new Text("");
    private Text checkMate = new Text("");

    private Text winner; //Displays who won the game.

    private Insets insets;

    //top and bottom panes

    private VBox whiteStatus;
    private VBox blackStatus;

    Text score; // Displays the score 
    Text moves; // Displays the previous moves
    HBox top = new HBox();
    VBox left = new VBox();
    HBox bottom = new HBox();
    VBox right = new VBox();
    public static Button newGame = new Button("New Game");
 
    Stage stage = new Stage();
  
  
  
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
    
        primaryStage.setTitle("CHESS - CS205 FINAL PROJECT");
        border.setStyle("-fx-background-color: beige;" +
                "-fx-border-width:10;");

        board = new GridPane();
        board.setStyle("-fx-border-width: 4;" +
        "-fx-border-color: black;" +
        "-fx-border-insets: 4;");
        board.setAlignment(Pos.CENTER);
        board.setPrefSize(800,800);

        winner = new Text(" "); // There is no winner yet.
        
        game = new Game();
        game.getBoard().initializeBoard();        

        

   

        //Set up user board.
        drawBoard();
        
        border.setCenter(board);//put the chess board in the middle

        
        //Border Panels (4 panels: top,bottom,left, and right)
        //top: new game button, points, check
        top.setStyle("-fx-border-width: 1;" +
                                "-fx-border-color: black;" +
                                "-fx-background-width: 1;" +
                                "-fx-background-color: F1D9C0;");

        top.setPadding(new Insets(15, 12, 15, 12));
        top.setSpacing(10);

        //left: moves array
        left.setStyle("-fx-border-width: 1;" +
                                "-fx-border-color: black;" +
                                "-fx-background-width: 1;" +
                                "-fx-background-color: F1D9C0;");

        left.setPadding(new Insets(15, 20, 55, 10));
        left.setSpacing(10);

        //bottom: white captured pieces
        bottom.setStyle("-fx-border-width: 1;" +
                                "-fx-border-color: black;" +
                                "-fx-background-width: 1;" +
                                "-fx-background-color: F1D9C0;");

        bottom.setPadding(new Insets(15, 12, 15, 12));
        bottom.setSpacing(10);
        bottom.setAlignment(Pos.CENTER);

        //right: black captured pieces
        right.setStyle("-fx-border-width: 1;" +
                                "-fx-border-color: black;" +
                                "-fx-background-width: 1;" +
                                "-fx-background-color: F1D9C0;");

        right.setPadding(new Insets(15, 12, 15, 12));
        right.setSpacing(10);
       
        //New Game button resets the game on click
        newGame.setPrefSize(150, 30);
        newGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override 
            public void handle(ActionEvent e) {
            
                game.newGame();//clears and reinitializes board, resets points, moves, captured pieces
                                     
                updatePanes();//updates panes

                status.setText("");//reset check status

                checkMate.setText("");//reset checkmate status

                drawBoard();//redraw board                                
            }
        });
        
        //add the border panes
        border.setTop(top);
        border.setLeft(left);
        border.setRight(right);
        border.setBottom(bottom);
        updatePanes();
       
        Scene scene = new Scene(border);
        primaryStage.setScene(scene);
        primaryStage.show();

 
 

    }
    public void drawBoard(){
        //iterate over all x and y coords
        
        for(int i = 0; i < 8; i++){
           for(int j = 0; j < 8; j++){
                //if there is a piece on the square, set it to p
                Piece p = game.getBoard().getSquare(i, j).getPiece();
              
                
                //create an hBox for the square
                HBox h = new HBox();
                h.setAlignment(Pos.CENTER);
                h.setPrefSize(80,80);
                if(!game.getIsCheckmate())//if not checkmate
                    handleClicks(h,p);//get user input
                
        
                //this is code for drawing the board squares in alternating colors     
                if(p != null){
                    if((i+j)%2 != 0)
                        h.setStyle("-fx-border-width: 1;" +
                                "-fx-border-color: black;" +
                                "-fx-background-width: 1;" +
                                "-fx-background-color: A97A65;" +
                                "-fx-background-image: url('" + p.getImage() + "');");
                    else
                        h.setStyle("-fx-border-width: 1;" +
                                "-fx-border-color: black;" +
                                "-fx-background-width: 1;" +
                                "-fx-background-color: F1D9C0;" +
                                "-fx-background-image: url('" + p.getImage() + "');");
                }
                else{
                    if((i+j)%2 != 0)
                        h.setStyle("-fx-border-width: 1;" +
                                "-fx-border-color: black;" +
                                "-fx-background-width: 1;" +
                                "-fx-background-color: A97A65;");
                    else
                        h.setStyle("-fx-border-width: 1;" +
                                "-fx-border-color: black;" +
                                "-fx-background-width: 1;" +
                                "-fx-background-color: F1D9C0;");
                }

                board.add(h, i, j);     
            
            
           }
        }
        if(game.getIsCheckmate()){
                   
            stage.setTitle("checkmate");
            
            winner.setText(game.getCheckmateString());
            winner.setX(40);
            winner.setY(40);
            winner.setFont(Font.font("verdana", 40));
            Group g = new Group(winner);
            border.setCenter(board);
            Scene scene1 = new Scene(g,550,200, Color.BEIGE);
            switch(game.getCheckmateString().split("[ ]+")[0]){
                case "DRAW!":
                    checkMate.setText("DRAW");
                    break;
                default:
                    checkMate.setText("CHECKMATE");
                    break;
            }
            checkMate.setFont(Font.font("verdana", 20));
            status.setText("");
            updatePanes();
          
            
            stage.setScene(scene1);
           
    
            stage.show();
    
        }

    
    }

    public void handleClicks(HBox h,Piece p){
         h.setOnMousePressed(new EventHandler<MouseEvent>() {
             @Override
             public void handle(MouseEvent event) {
                drawBoard();
                

                if(p != null && p.getColor() != Piece.Color.Black){
                //if(p != null){
                    highlightMoves(p);  
                }
             }
         });
         

    }

    public void highlightMoves(Piece p){
        Square squares[] = game.getBoard().getSquares();

        //first highlight the square the selected piece is on
        HBox h = new HBox();

        h.setStyle("-fx-border-width: 1;" +
                    "-fx-border-color: black;" +
                    "-fx-background-width: 1;" +
                    "-fx-background-color: yellow;" +
                    "-fx-background-image: url('" + p.getImage() + "');");

        board.add(h, p.getSquare().getX(), p.getSquare().getY());

        //iterate over all squares
        for (Square s : squares){
            if(s != null && p.getSquare() != null){//make sure the square is not null
                HBox h2 = new HBox();
                Move move = new Move(p.getSquare(),s,game.getBoard());//attempt to move the piece to that square
                if(move.validate(false).getValid()){//if the move is valid, highlight the square
                    move.undoMove();
                    h2.setAlignment(Pos.CENTER);
                    h2.setPrefSize(60,60);
                    if(s.getPiece() != null)
                        h2.setStyle("-fx-border-width: 1;" +
                                    "-fx-border-color: black;" +
                                    "-fx-background-width: 1;" +
                                    "-fx-background-color: lightgreen;" +
                                    "-fx-background-image: url('" + s.getPiece().getImage() + "');");
                    else
                        h2.setStyle("-fx-border-width: 1;" +
                                "-fx-border-color: black;" +
                                "-fx-background-width: 1;" +
                                "-fx-background-color: lightgreen;");
                    board.add(h2, s.getX(),s.getY());
                }
                h2.setOnMousePressed(new EventHandler<MouseEvent>(){//if user clicks one of the legal spaces, move piece to that space
                    @Override
                    public void handle(MouseEvent event) {
                       
                        Move move = new Move(p.getSquare(),s,game.getBoard());//move Piece to square s
                        game.guiMove(move,p);
                        drawBoard();//redraw board
                                                                                                                                  
                        while(!game.getIsCheckmate() && !game.getBlackMoveOnePlayer()){//get black's move (if not checkmate)
            
                        }
                        status.setText(game.getCheckString());//if check, set status to check
                        status.setFont(Font.font("verdana", 20));
                        updatePanes();
                       

                        if(!game.getIsCheckmate())//redraw board if not checkmate
                            drawBoard();                   
                            
                    }
                });
            }
        }
    }
    
    public void updatePanes(){
        
        score = new Text("White Score: "+game.getScore()[0]+"\t Black Score: "+game.getScore()[1]);

        //clear all panels
        top.getChildren().clear();
        bottom.getChildren().clear();
        right.getChildren().clear();
        left.getChildren().clear();
               
        top.getChildren().addAll(newGame,score,status,checkMate);//add top panel
        top.setAlignment(Pos.CENTER);//center top panel
        border.setTop(top);

        //write moves array to string s
        String s = new String();
        for(int i = 0; i <game.getMovesArray().size(); ++i){
            if(game.getMovesArray().get(i) != null)
                if(i%2 == 0){
                    s += (i/2)+1+ ". "+game.getMovesArray().get(i);
                    
                }
                else{
                    s += ", "+game.getMovesArray().get(i);    
                }
        }

        //captured pieces
       
        for(int i = 0; i < game.getBlackCapturedPieces().size(); ++i){
            
            Image image = new Image(game.getBlackCapturedPieces().get(i).getImage());
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(45);
            imageView.setFitHeight(45);
            right.getChildren().add(imageView);
        }

        for(int i = 0; i < game.getWhiteCapturedPieces().size(); ++i){
            Image image = new Image(game.getWhiteCapturedPieces().get(i).getImage());
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(45);
            imageView.setFitHeight(45);
            bottom.getChildren().add(imageView);
        }    
       
        
        border.setBottom(bottom);//set captured piece images to bottom 
        border.setRight(right);
        
        
        moves = new Text(s);//make the string of previous moves a text object
        
        left.getChildren().addAll(moves);//previous moves displays on the left

        if(game.getMovesArray().size() > 70){//if over 70 moves, add a scroll bar
            ScrollPane scroll = new ScrollPane();
            scroll.setContent(left);
            border.setLeft(scroll);
        }
        else
            border.setLeft(left);
    }

   

   

    
}

