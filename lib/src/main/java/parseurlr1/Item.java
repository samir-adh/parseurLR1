package parseurlr1;

import java.util.Set;

public class Item {
    private Regle regle;
    private Integer marqueur;
    private  Set<String> contexte;

    public Item(Regle regle, Integer marqueur, Set<String> contexte) {
        this.regle = regle;
        this.marqueur = marqueur;
        this.contexte = contexte;
    }

    public Regle getRegle() {
        return regle;
    }

    public Integer getMarqueur() {
        return marqueur;
    }

    public Set<String> getContexte() {
        return contexte;
    }

    // Retourne un item dont le marqueur est incrémenté de 1
    public Item getItemSuivant() {
        return new Item(this.getRegle(), this.getMarqueur() + 1, this.getContexte());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Item) {
            Item item = (Item) obj;
            return this.getRegle().equals(item.getRegle())
                    && this.getMarqueur() == item.getMarqueur()
                    && this.getContexte().equals(item.getContexte());
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        String output = this.getRegle().getGauche() + "->";
            for (int i = 0; i < this.getRegle().getDroite().size(); i++) {
                if (i== this.getMarqueur()){
                    output += ".".toString();
                }
                output += this.getRegle().getDroite().get(i);
            };
            output += " " + this.getContexte() + "\n";
        return output;
    }
}
