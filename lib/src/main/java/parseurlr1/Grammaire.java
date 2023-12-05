package parseurlr1;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

public class Grammaire {
    private List<String> nonTerminaux;
    private List<String> terminaux;
    private List<Regle> regles;
    private List<String> nonTerminauxVides;
    private String axiome;
    public Grammaire(List<String> nonTerminaux, List<String> terminaux, List<Regle> regles, String axiome) {
        this.nonTerminaux = nonTerminaux;
        this.terminaux = terminaux;
        this.regles = regles;
        this.nonTerminauxVides = this.donneLeMotVide();
        this.axiome = axiome;
    }

    public String getAxiome() {
        return axiome;
    }

    public List<String> getNonTerminaux() {
        return nonTerminaux;
    }

    public List<String> getTerminaux() {
        return terminaux;
    }

    public List<Regle> getRegles() {
        return regles;
    }

    public Set<String> first(String nonTerminal) {
        Set<String> firstSet = new HashSet<>();

        for (Regle production : this.getRegles()) {
            if (production.getGauche().equals(nonTerminal)) {
                for (String symbol : production.getDroite()) {
                    if (this.getRegles().stream().anyMatch(p -> p.getGauche().equals(symbol))) {
                        // Recursively compute the first set of the symbol
                        firstSet.addAll(first(symbol));

                        // If the first set of the symbol does not contain ε, then we can stop
                        if (!first(symbol).contains("^")) {
                            break;
                        }
                    } else {
                        // The symbol is a terminal
                        firstSet.add(symbol);
                        break;
                    }
                }
            }
        }
        return firstSet;
    }

    public Set<String> first(List<String> tokens) {
        if (tokens.isEmpty()) {
            return Set.of();
        } else if (!this.nonTerminaux.contains(tokens.get(0))) {
            return Set.of(tokens.get(0));
        } else {
            Set<String> firstSet = first(tokens.get(0));
            if (firstSet.contains("^")) {
                firstSet.addAll(first(tokens.subList(1, tokens.size())));
            }
            return firstSet;
        }
    }

    public List<String> donneLeMotVide() {
        List<String> nonTerminauxVides = new ArrayList<>();
        boolean nouveauxTokensAjoutes;

        // Initialisation : les non-terminaux ayant une production vide sont marqués
        // comme vides
        do {
            nouveauxTokensAjoutes = false;

            for (Regle regle : this.getRegles()) {
                String nonTerminal = regle.getGauche();
                List<String> droite = regle.getDroite();

                if (droite.contains("^")) {
                    if (!nonTerminauxVides.contains(nonTerminal)) {
                        nonTerminauxVides.add(nonTerminal);
                        nouveauxTokensAjoutes = true;
                    }
                } else {
                    // Vérification si tous les symboles de la production sont vides
                    boolean allNullable = true;
                    for (String symbol : droite) {
                        if (!nonTerminauxVides.contains(symbol)) {
                            allNullable = false;
                            break;
                        }
                    }

                    // Si tous les symboles sont vides, marquer le non-terminal comme vide
                    if (allNullable && !nonTerminauxVides.contains(nonTerminal)) {
                        nonTerminauxVides.add(nonTerminal);
                        nouveauxTokensAjoutes = true;
                    }
                }
            }
        } while (nouveauxTokensAjoutes);

        return nonTerminauxVides;
    }
}
