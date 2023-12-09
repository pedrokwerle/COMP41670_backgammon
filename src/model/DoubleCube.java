package model;

//
public class DoubleCube {
    int value;
    public DoubleCube(){
        this.value = 1;
    }

    public void doTheDouble(){
        this.value = this.value*2;
    }

    public int getValue(){
        return this.value;
    }
}
