package test.com.rejasupotaro.sson;

import junit.framework.TestCase;
import main.com.rejasupotaro.sson.Sson;

public class SsonTest extends TestCase {

    public void testToSson() {
        Sson sson = new Sson();
        String sexprString = sson.toSexpr(new Person("rejasupotaro", 23));
        assertNotNull(sexprString);
        assertEquals("((name rejasupotaro) (age 23))", sexprString);
    }
}
