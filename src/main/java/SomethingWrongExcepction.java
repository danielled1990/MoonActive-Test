/**
 * costom exception, that is triggered so the program wont fail.
 */
public class SomethingWrongExcepction extends Exception{
    public SomethingWrongExcepction(String error){
        super(error);
    }
}
