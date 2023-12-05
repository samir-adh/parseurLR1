package parseurlr1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class ItemTest {
    @Test
    public void testItemEqualsMethod() {
        Regle r0 = new Regle("A", List.of("a", "B", "c"));
        Regle r1 = new Regle("A", List.of("a", "B", "d"));
        Item i0 = new Item(r0, 0, Set.of("a"));
        Item i0bis = new Item(r0, 0, Set.of("a"));
        Item i1 = new Item(r0, 1, Set.of("a"));
        Item i2 = new Item(r0, 0, Set.of("b"));
        Item i3 = new Item(r1, 0, Set.of("a"));

        assertEquals(i0, i0bis);
        assertNotEquals(i0, i1);
        assertNotEquals(i0, i2);
        assertNotEquals(i0, i3);
    }
}
