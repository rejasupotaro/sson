package test.com.rejasupotaro.sson;

import static main.com.rejasupotaro.sson.sexpr.SexprObject.car;
import static main.com.rejasupotaro.sson.sexpr.SexprObject.cdr;
import static main.com.rejasupotaro.sson.sexpr.SexprObject.cons;
import junit.framework.TestCase;
import main.com.rejasupotaro.sson.sexpr.SexprObject;

public class SexprTest extends TestCase {
    public void testSexpr() {
        SexprObject sexpr1 = cons("car1", "cdr1");
        assertEquals("car1", SexprObject.car(sexpr1).toString());
        assertEquals("cdr1", SexprObject.cdr(sexpr1).toString());

        SexprObject sexpr2 = cons("car2", "cdr2");
        sexpr1.setCdr(sexpr2);
        assertEquals("car1", car(sexpr1).toString());
        assertEquals("cdr2", cdr(cdr(sexpr1)).toString());
    }
}
