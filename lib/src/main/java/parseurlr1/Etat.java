package parseurlr1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Etat {
    private List<Item> items;
    private Map<String,Etat> transitions = new HashMap<>();
    private Integer id;

    public Etat(List<Item> items){
        this.items = items;
        this.id = (new Random()).nextInt(1000);
    }

    public void addTransition(String token, Etat etat){
        this.transitions.put(token, etat);
    }

    public List<Item> getItems() {
        return items;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Etat){
            Etat etat = (Etat) obj;
            return this.items.equals(etat.getItems());
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        String output = "Id : ".toString() +this.id.toString() + "\n";
        for (Item item : items) {
            output += item.toString();
        }
        return output;
    }
}
