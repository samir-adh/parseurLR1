package parseurlr1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

public class RegleTest {
    @Test
    public void testRegleEqualsMethod(){
        Regle r0 = new Regle("A",List.of("a","B","c"));
        Regle r0bis = new Regle("A",List.of("a","B","c"));
        Regle r1 = new Regle("A",List.of("a","B","d"));
        assertEquals(r0, r0bis);
        assertNotEquals(r0, r1);
    }
}
