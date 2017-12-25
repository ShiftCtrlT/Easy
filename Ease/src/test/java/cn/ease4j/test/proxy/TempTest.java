package cn.ease4j.test.proxy;

import cn.ease4j.util.StringUtil;
import org.junit.Test;

public class TempTest {

    public int count=11;
    public int i=0;
    @Test
    public void myTest(){
        for (; i < count;) {
            if(i++<=9){
                innerMethod(this);
            }
                System.err.println("This is else, i = "+i);

        }
    }
    private void innerMethod(TempTest t){
        System.out.println("This is inner,i = "+t.i);
        t.myTest();
    }

    @Test
    public void  soutChar(){
        String s = String.valueOf((char) 32);
        System.out.println(s);
    }
}
