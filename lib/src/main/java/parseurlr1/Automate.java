package parseurlr1;

import java.util.ArrayList;
import java.util.List;

public class Automate {
    
    private List<Etat> listeEtats;
    private Etat etatInintial;
    private List<Etat> etatsFinaux;
    public Automate(){
        this.listeEtats = new ArrayList<>();
        this.etatsFinaux = new ArrayList<>();

    }

    public Etat getEtatInintial() {
        return etatInintial;
    }
    public void setEtatInintial(Etat etatInintial) {
        this.etatInintial = etatInintial;
    }
    public List<Etat> getEtatsFinaux() {
        return etatsFinaux;
    }
    public List<Etat> getListeEtats() {
        return listeEtats;
    }
}
