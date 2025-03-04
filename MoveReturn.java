public class MoveReturn {

    private String moveLoc;
    private boolean valid;
    private Piece captured;
    private boolean isCheck;


    public MoveReturn(){
        moveLoc = "Invalid Move";
        valid = false;
        isCheck = false;
    }

    public MoveReturn(String loc, boolean valid, boolean check){

        this.captured = null;
        this.moveLoc = loc;
        this.valid = valid;
        this.isCheck = check;

    }

    public String getLoc(){
        return moveLoc;
    }

    public boolean getValid(){
        return valid;
    }

    public Piece getCaptured(){
        return captured;
    }

    public boolean is_check(){
        return isCheck;
    }

    public void setMoveLoc(String loc){
        this.moveLoc = loc;
    }

    public void setValid(boolean valid){
        this.valid = valid;
    }

    public void setCaptured(Piece c){
        this.captured = c;
    }

    public void setCheck(boolean c){
        this.isCheck = c;
    }

}
