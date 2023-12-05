package parseurlr1;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Parseur {
    private Grammaire grammaire;
    

    public Parseur(Grammaire grammaire) {
        this.grammaire = grammaire;
        
    }

    public List<Item> calculFermeture(List<Item> items) {
        List<Item> fermeture = new ArrayList<>();
        Stack<Item> pileItems = new Stack<>();
        pileItems.addAll(items);

        while (!pileItems.empty()) {
            Item item = pileItems.pop();
            Regle regleItem = item.getRegle();
            Integer marqueurItem = item.getMarqueur();
            Set<String> contexteItem = item.getContexte();
            if (!itemDansLaListe(item, fermeture)) {
                fermeture.add(item);
            }
            if (marqueurItem >= regleItem.getDroite().size()){
                continue;
            }
            String tokenApresMarqueur = regleItem.getDroite().get(marqueurItem);
            List<Regle> reglesPertinentes = grammaire.getRegles().stream()
                    .filter(r -> r.getGauche().equals(tokenApresMarqueur)).toList();
            for (Regle regle : reglesPertinentes) {
                Set<String> contexte = new HashSet<>();
                if (marqueurItem + 1 >= regleItem.getDroite().size()) {
                    contexte = contexteItem;
                } else {
                    List<String> sub = item.getRegle().getDroite()
                            .subList(item.getMarqueur() + 1, item.getRegle().getDroite().size());
                    for (String token : item.getContexte()) {
                        List<String> beta = Stream.concat(sub.stream(), Stream.of(token)).collect(Collectors.toList());
                        contexte.addAll(grammaire.first(beta));
                    }
                }
                pileItems.add(new Item(regle, 0, contexte));
            }
        }
        return fermeture;
    }

    public Automate contruireAutomate() {
        Automate automate = new Automate();
        Regle r0 = grammaire.getRegles().stream()
                .filter(p -> p.getGauche().equals(grammaire.getAxiome())).findFirst()
                .get();
        Item item0 = new Item(r0, 0, Set.of("$"));
        Etat etat0 = new Etat(calculFermeture(List.of(item0)));
        automate.getListeEtats().add(etat0);
        automate.setEtatInintial(etat0);
        Stack<Etat> pileEtats = new Stack<>();
        pileEtats.push(etat0);

        while (!pileEtats.empty()) {
            Etat etat = pileEtats.pop();
            List<Item> items = etat.getItems();
            for (int index = 0; index < items.size(); index++) {
                Item item = items.get(index);
                Regle regle = item.getRegle();
                Integer marqueur = item.getMarqueur();
                Set<String> contexte = item.getContexte();
                if (marqueur >= regle.getDroite().size()){
                    automate.getEtatsFinaux().add(etat);
                } else {
                    String tokenApresMarqueur = 
                        regle.getDroite().get(marqueur);
                    Item nouvelItem = new Item(regle, marqueur+1, contexte);
                    Etat nouvelEtat = new Etat(calculFermeture(List.of(nouvelItem)));
                    Boolean etatExisteDeja = false;
                    for (Etat etatExistant : automate.getListeEtats()) {
                        if (nouvelEtat.equals(etatExistant)) {
                            nouvelEtat = etatExistant;
                            etatExisteDeja = true;
                            break;
                        }
                    }
                    if (!etatExisteDeja){
                        pileEtats.add(nouvelEtat);
                        automate.getListeEtats().add(nouvelEtat);                         
                    }
                    etat.addTransition(tokenApresMarqueur, nouvelEtat);
                }   
            } 
        }

        return automate;
    }

    public static Boolean itemDansLaListe(Item item, List<Item> liste) {
        for (Item item0 : liste) {
            if (item.equals(item0)) {
                return true;
            }
        }
        return false;
    }
}
