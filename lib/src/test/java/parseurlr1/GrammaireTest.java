package parseurlr1;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Set;

public class GrammaireTest {
    Grammaire instance;
    public GrammaireTest(){
        List<Regle> regles = new ArrayList<>();
        regles.add(new Regle("S", List.of("A", "B")));
        regles.add(new Regle("S", List.of("A", "D")));
        regles.add(new Regle("A", List.of("^")));
        regles.add(new Regle("A",List.of("e")));
        regles.add(new Regle("B", List.of("a")));
        regles.add(new Regle("C", List.of("c")));
        regles.add(new Regle("D", List.of("^")));
        this.instance = new Grammaire(List.of("S","A","B","C","D"), List.of("a","b"), regles,"S");
    }

    @Test
    public void testNTVides() {
        List<String> ntVides = instance.donneLeMotVide();
        assertTrue(ntVides.contains("S"));
        assertTrue(ntVides.contains("D"));
        assertTrue(ntVides.contains("A"));
        assertFalse(ntVides.contains("B"));
        assertFalse(ntVides.contains("C"));
    }
    @Test
    public void testFirstNonTerminal(){
        String nonTerminalTested = "S";
        Set<String> firstSet = instance.first(nonTerminalTested);
        Set<String> expectedSet = Set.of("a","e","^");
        assertEquals(expectedSet,firstSet);
    }

    @Test
    public void testFirstConcatenation(){
        List<String> wordTested = List.of("A","$");
        Set<String> firstSet = instance.first(wordTested);
        Set<String> expectedSet = Set.of("e","$","^");
        assertEquals(firstSet,expectedSet);
    }
}
