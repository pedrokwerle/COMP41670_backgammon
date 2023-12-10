package userInterface;

import userInterface.Fileio;

import java.io.IOException;
import java.util.ArrayList;

public class AsciiArt {
    public AsciiArt(String fileLocation){
        this.fileLocation = fileLocation;
        this.components = new ArrayList<>();

    }

    /**
     * Tells if the art is composite or not
     */
    boolean composite = false;

    /**
     * Returns the value of the composite attribute of AsciiArt
     * @return
     */
    public boolean isComposite(){
        return composite;
    }

    /**
     * In case the art is composite, contains the Displayable objects that compose this AsciiArt
     */
    ArrayList<Displayable> components;
    /**
     * Location of the file used when the art is not composite
     */
    String fileLocation;
    /**
     * Grid of PixelAscii that represent the unique art of a non-composite AsciiArt. Only available after loading the
     * unique art with loadArt();
     */
    ArrayList<ArrayList<PixelAscii>> uniqueArt;
    /**
     * Tells the x position of the entire art (composite or not). Used when the art is part of another composite art
     * to set its position relative to other arts in the composite.
     */
    int xPos = 0;
    /**
     * Tells the y position of the entire art (composite or not). Used when the art is part of another composite art
     * to set its position relative to other arts in the composite.
     */
    int yPos = 0;

    /**
     * Change the x position of the entire art to the value of adjustment
     * @param adjustment
     */
    public void adjustxPos(int adjustment){
        this.xPos = adjustment;
    }
    /**
     * Change the y position of the entire art to the value of adjustment
     * @param adjustment
     */
    public void adjustyPos(int adjustment){
        this.yPos = adjustment;
    }

    /**
     * returns the value of xPos
     * @return
     */
    public int getxPos(){
        return  this.xPos;
    }
    /**
     * returns the value of yPos
     * @return
     */
    public int getyPos(){
        return  this.yPos;
    }

    /**
     * Adds the component to the art at the given x and y coordinates and sets the art to composite
     * @param component
     * @param xPos
     * @param yPos
     */
    public void addComponent(Displayable component, int xPos, int yPos){
        component.getArt().adjustxPos(xPos);
        component.getArt().adjustyPos(yPos);
        this.components.add(component);
        this.composite = true;
    }

    /**
     * Set the value of fileLocation attribute
     * @param fileLocation
     */
    public void setFileLocation(String fileLocation){
        this.fileLocation = fileLocation;
    }

    /**
     * Loads art contained in the fileLocation into uniqueArt as a grid of PixelAscii with all white color
     * @throws Exception
     */
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

    /**
     * returns the uniqueArt attribute
     * @return
     */
    public ArrayList<ArrayList<PixelAscii>> getUniqueArt() {
        return uniqueArt;
    }

    /**
     * returns the attribute components
     * @return
     */
    public ArrayList<Displayable> getComponents() {
        return components;
    }

    /**
     * Clears all components of the AsciiArt and sets it as not composite
     */
    public void clearComponents(){
        this.components.clear();
        this.composite = false;
    }
}
