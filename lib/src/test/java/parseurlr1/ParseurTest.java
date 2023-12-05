package parseurlr1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class ParseurTest {
    Parseur instance = new Parseur(exempleGrammaire());

    private static Grammaire exempleGrammaire() {
        List<Regle> regles = new ArrayList<>();
        regles.add(new Regle("S", List.of("A", "B")));
        regles.add(new Regle("S", List.of("A", "D")));
        regles.add(new Regle("A", List.of("^")));
        regles.add(new Regle("A", List.of("e")));
        regles.add(new Regle("B", List.of("a")));
        regles.add(new Regle("C", List.of("c")));
        regles.add(new Regle("D", List.of("^")));
        return new Grammaire(List.of("S", "A", "B", "C", "D"), List.of("a", "b"), regles,"S");
    }

    @Test
    public void testItemEstDansListe() {
        Regle r0 = new Regle("A", List.of("a", "B", "c"));
        Regle r1 = new Regle("A", List.of("a", "B", "d"));
        Item i0 = new Item(r0, 0, Set.of("a"));
        Item i0bis = new Item(r0, 0, Set.of("a"));
        Item i1 = new Item(r0, 1, Set.of("a"));
        Item i2 = new Item(r0, 0, Set.of("b"));
        Item i3 = new Item(r1, 0, Set.of("a"));
        List<Item> items = List.of(i0, i1, i2);

        assertTrue(Parseur.itemDansLaListe(i0bis, items));
        assertFalse(Parseur.itemDansLaListe(i3, items));
    }

    @Test
    public void testFermeture() {
        Regle r0 = new Regle("S'", List.of("S"));
        Regle r1 = new Regle("S", List.of("C", "C"));
        Regle r2 = new Regle("C", List.of("a", "C"));
        Regle r3 = new Regle("C", List.of("d"));
        Grammaire grammaire = new Grammaire(
                List.of("S'", "S", "C"),
                List.of("a", "d"),
                List.of(r0, r1, r2, r3),
                "S'"
        );
        Parseur parseur = new Parseur(grammaire);
        Item start = new Item(r0,0,Set.of("$"));
        List<Item> fermeture = parseur.calculFermeture(List.of(start));
        for (Item item :fermeture){
            System.out.println(item);
        }
    }


    @Test
    public void testConstruireAutomate(){
        Regle r0 = new Regle("S'", List.of("S"));
        Regle r1 = new Regle("S", List.of("C", "C"));
        Regle r2 = new Regle("C", List.of("a", "C"));
        Regle r3 = new Regle("C", List.of("d"));
        Grammaire grammaire = new Grammaire(
                List.of("S'", "S", "C"),
                List.of("a", "d"),
                List.of(r0, r1, r2, r3),
                "S'"
        );
        Parseur parseur = new Parseur(grammaire);
        Automate automate = parseur.contruireAutomate();
        System.out.println("Etat initial : " + automate.getEtatInintial() + "\n Liste etats : ");
        for (Etat etat : automate.getListeEtats()) {
            System.out.println(etat);
        }
        assertEquals(10, automate.getListeEtats().size());
    }
}
