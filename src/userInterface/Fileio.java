package userInterface;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileReader;
import java.io.IOException;

public class Fileio {
    protected String filePath;
    BufferedReader reader;

    public Fileio(String filePath) {
        this.filePath = filePath;
        this.openReader();
    }

    private void openReader() {
        try {
            this.reader = new BufferedReader(new FileReader(this.filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void closeReader() {
        try {
            this.reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public String readLine() throws IOException {
        String line = this.reader.readLine();
        if (line == null){
            this.closeReader();
            throw new EOFException("End of file");}
        return line;
    }
    public char readChar() throws IOException{
        char charc = (char)this.reader.read();
        if (charc == '\uFFFF'){
            this.closeReader();
            throw new EOFException("End of file");}
        return charc;
    }
}