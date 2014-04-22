/**
 * This class serves to out data in different ways
 */
public class Output {
    private Output(){ throw new AssertionError("Shouldn't be instantiated"); }

    public static void println(Object object){
        System.out.println(object);
    }
}
