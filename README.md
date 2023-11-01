# COMP41670_backgammon
Backgammon game for group assignment of COMP41670


# How to use display backend
The display backend works by creating a N by M display matrix. Each element in the matrix represents a pixel and is able to display a single ASCII character with one color. 
You cannot display characters directly onto the display if you are using the display manager. 
To display something on the display you must use an object of a class which implements the Displayable interface *correctly*. 
The process goes like this:
1 - Instantiate a DisplayManager
2 - Instantiate desired class
3 - Pass you object to the addToCache method of the DisplayManager along with the coordinates where you want the object to be displayed
4 -  Use the printDisplay method from the displayManager to realize the display
5 - If you want to clear the display you must clear the cache of objects in the DisplayManager, to do this use the clearCache method from the DisplayManager

Here is an example using the AsciiString class, the equivalent of a String for this display:
DisplayManager displayManager = new DisplayManager(50,50);
AsciiString string = new AsciiString("hello world");
displayManager.addToCache(string, 0 , 0);
displayManager.printDisplay();
