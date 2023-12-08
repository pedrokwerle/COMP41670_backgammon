package userInterface;


// Character display
public class Display {
    PixelAscii[][] pixelGrid;
    int rowsNum;
    int colsNum;

    // Create display object with given row and column resolution
    public Display(int rows, int cols){
        this.rowsNum = rows;
        this.colsNum = cols;
        this.pixelGrid = new PixelAscii[rows][cols];
        for (int row = 0; row < rowsNum; row++){
            for (int col = 0; col < colsNum; col++){
                this.pixelGrid[row][col] = new PixelAscii(' ',ColorsAscii.BLACK);
            }
        }
    }

    public void setPixel(PixelAscii pixel, int row, int col){
        this.pixelGrid[row][col] = pixel;
    }

    public void print(){
        int lastRow = removeWhiteSpace();
        for(int row = 0; row< lastRow; row++){
            System.out.println();
            for(int col = 0; col< colsNum; col++){
                PixelAscii pixel = this.pixelGrid[row][col];
                System.out.print(pixel.getColor().toCode()+pixel.getCharacter());
            }
        }
        System.out.println();
    }
    private int removeWhiteSpace(){
        int lastRow;
        for(lastRow = rowsNum-1; lastRow > 0;lastRow-- ){
            for(int col = 0; col< colsNum; col++){
                PixelAscii pixel = this.pixelGrid[lastRow][col];
                if(pixel.getCharacter()>32){
                    return lastRow+1;
                }
            }
        }
        return rowsNum;
    }
    public void resetDisplay(){
        for (int row = 0; row < rowsNum; row++){
            for (int col = 0; col < colsNum; col++){
                this.pixelGrid[row][col] = new PixelAscii(' ',ColorsAscii.BLACK);
            }
        }
    }
}
