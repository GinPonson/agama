package cnblog;

import java.util.Comparator;
import java.util.TreeSet;

/**
 * Created by GinPonson on 2/25/2017.
 */
public class Test {
    public static void main(String[] args) {
        String b = "1";
        //String a = b + "2";
        //StringBuffer sb = new StringBuffer("1");
        //System.out.print("12" == a);
        String cc = new String ("1");
        System.out.print(b == cc.intern());
    }
}
