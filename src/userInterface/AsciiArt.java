package userInterface;

import userInterface.Fileio;

import java.io.IOException;
import java.util.ArrayList;

public class AsciiArt {
    public AsciiArt(String fileLocation){
        this.fileLocation = fileLocation;
        this.components = new ArrayList<>();

    }
    boolean composite = false;
    public boolean isComposite(){
        return composite;
    }
    ArrayList<Displayable> components;
    String fileLocation;
    ArrayList<ArrayList<PixelAscii>> uniqueArt;
    int xPos = 0;
    int yPos = 0;
    public void adjustxPos(int adjustment){
        this.xPos = adjustment;
    }
    public void adjustyPos(int adjustment){
        this.yPos = adjustment;
    }
    public void resetxPos(){
        this.xPos = 0;
    }
    public void resetyPos(){
        this.yPos = 0;
    }
    public int getxPos(){
        return  this.xPos;
    }
    public int getyPos(){
        return  this.yPos;
    }
    public void addComponent(Displayable component, int xPos, int yPos){
        component.getArt().adjustxPos(xPos);
        component.getArt().adjustyPos(yPos);
        this.components.add(component);
        this.composite = true;
    }
    public void setFileLocation(String fileLocation){
        this.fileLocation = fileLocation;
    }
    public void loadArt() throws Exception{
        if(this.fileLocation == ""){
            throw new Exception("No file location provided");
        }
        this.uniqueArt = new ArrayList<>();
        Fileio artFile = new Fileio(fileLocation);
        int row = 0;
        int col;
        try{
            while(true) {
                this.uniqueArt.add(row,new ArrayList<>());
                col = 0;
                for (char charc = artFile.readChar(); charc != '\n'; charc = artFile.readChar()) {
                    if (charc != '\r') {
                        this.uniqueArt.get(row).add(col, new PixelAscii(charc, ColorsAscii.WHITE));

                        col++;
                    }
                }
                row++;
            }
        }
        catch(IOException E){
        }
    }

    public ArrayList<ArrayList<PixelAscii>> getUniqueArt() {
        return uniqueArt;
    }

    public ArrayList<Displayable> getComponents() {
        return components;
    }
    public void clearComponents(){
        this.components.clear();
        this.composite = false;
    }
}
