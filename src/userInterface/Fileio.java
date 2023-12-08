package userInterface;

import java.io.*;

public class Fileio {
    protected String filePath;
    BufferedReader reader;

    public Fileio(String filePath) throws IOException {
        this.filePath = filePath;
        this.openReader();
    }

    private void openReader() throws IOException {
        try {
            InputStream in = getClass().getResourceAsStream(this.filePath);
            this.reader = new BufferedReader(new InputStreamReader(in));
        }
        catch(Exception e) {
            throw new IOException("File not found");
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
        }
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