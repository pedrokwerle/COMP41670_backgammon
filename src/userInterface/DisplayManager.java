package userInterface;

import java.util.ArrayList;

public class DisplayManager {
    Display display;
    ArrayList<Displayable> renderCache;
    public DisplayManager(int rows, int cols){
        this.display = new Display(rows, cols);
        this.renderCache = new ArrayList<>();
    }
    public void addToCache(Displayable object, int xPos, int yPos){
        object.getArt().adjustxPos(xPos);
        object.getArt().adjustyPos(yPos);
        this.renderCache.add(object);
    }
    public void clearCache(){
        this.renderCache = new ArrayList<>();
    }
    public void renderObject(Displayable object){
        object.renderArt();
        this.renderToDisplay(object, 0, 0);

    }
    public void renderToDisplay(Displayable object, int xCompositePos, int yCompositePos){
        AsciiArt objectArt = object.getArt();
        if(objectArt.isComposite()){
            for(Displayable subObject : objectArt.components) {
                renderToDisplay(subObject, xCompositePos+ objectArt.getxPos(), yCompositePos + objectArt.getyPos());
            }
        }
        else {
            int xPos = objectArt.getxPos() + xCompositePos;
            int yPos = objectArt.getyPos() + yCompositePos;
            int height = objectArt.getUniqueArt().size();
            int length;
            for (int rowsNum = 0; rowsNum < height; rowsNum++) {
                length = objectArt.getUniqueArt().get(rowsNum).size();
                for (int colsNum = 0; colsNum < length; colsNum++) {
                    this.display.setPixel(objectArt.getUniqueArt().get(rowsNum).get(colsNum), rowsNum + yPos, colsNum + xPos);
                }
            }
        }
    }

    public void printString(String string, int yPos, int xPos){
        for (int charNum = 0; charNum < string.length(); charNum++){
            PixelAscii pixel = new PixelAscii(string.charAt(charNum), ColorsAscii.WHITE);
            this.display.setPixel(pixel, yPos, xPos);
        }
    }

    public void printDisplay(){
        this.display.resetDisplay();
        for (Displayable object : renderCache){
            renderObject(object);
        }
        this.display.print();
    }
}
