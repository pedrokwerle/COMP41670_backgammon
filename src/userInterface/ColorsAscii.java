package userInterface;

public enum ColorsAscii {
    BLACK((char)27 +"[30m"),
    BLUE((char)27 +"[34m"),
    RED((char)27 +"[31m"),
    GREEN((char)27 +"[32m"),
    YELLOW((char)27 +"[93m"),
    WHITE((char)27 +"[37m");
    private final String code;
    public String toCode(){
        return this.code;
    }
    ColorsAscii(String code){
        this.code = code;
    }
}
