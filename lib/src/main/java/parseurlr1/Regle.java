package parseurlr1;

import java.util.List;

public class Regle {
    private String gauche;
    private List<String> droite;

    public Regle(String gauche, List<String> droite) {
        this.gauche = gauche;
        this.droite = droite;
    }

    public String getGauche() {
        return gauche;
    }

    public List<String> getDroite() {
        return droite;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Regle) {
            Regle regle = (Regle) obj;
            return this.getDroite().equals(regle.getDroite())
                    && this.getGauche().equals(regle.getGauche());
        } else {
            return false;
        }
    }
}
