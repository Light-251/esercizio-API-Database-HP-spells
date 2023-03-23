package it.softwareInside.FreeGamesApiLezione23.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import it.softwareInside.FreeGamesApiLezione23.models.Spell;
import it.softwareInside.FreeGamesApiLezione23.repository.SpellRepository;

@Service
public class SpellService {

    @Autowired
    SpellRepository spellRepository;

    /**
     * Aggiunge un incantesimo al DB
     * fornendo un id
     * 
     * @param id
     * @return
     */
    public Spell getSpell(String id) {
        try {
            Spell spell = new Spell();
            RestTemplate restTemplate = new RestTemplate();

            spell = restTemplate.getForObject("https://wizard-world-api.herokuapp.com/Spells/" + id, Spell.class);
            spellRepository.save(spell);
            return spell;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Ritorna tutti gli incantesimi nel DB
     * 
     * @return
     */
    public Iterable<Spell> getAllSpells() {
        try {
            return spellRepository.findAll();

        } catch (Exception e) {
            System.err.println("ERRORE: " + e);
            return null;
        }
    }

    /**
     * Restituisce un Incantesimo presente nel DB
     * fornendo un id
     * 
     * @param id
     * @return
     */
    public Spell getSpellById(String id) {
        try {
            return spellRepository.findById(id).get();

        } catch (Exception e) {
            System.out.println("errore: " + e);
            return null;
        }
    }

    /**
     * Rimuove una spell passando l'id
     * 
     * @param id
     * @return
     */
    public Spell deleteSpellById(String id) {
        try {
            Spell spellRemoved = getSpell(id);
            spellRepository.deleteById(id);
            return spellRemoved;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Recupera tutti gli incantesimi dall'API
     * e li salva in un array
     * 
     * @return
     */
    public Spell[] generate() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Spell[]> spells = restTemplate.getForEntity("https://wizard-world-api.herokuapp.com/Spells/",
                Spell[].class);

        return spells.getBody();
    }

    /**
     * Ritorna il numero di incantesimi totale
     * 
     * @return
     */
    public int quantita() {
        try {
            int quantita = generate().length;
            return quantita;

        } catch (Exception e) {
            System.out.println("errore: " + e);
            return -1;
        }
    }

    /**
     * Rimuove tutti gli incantesimi dal DB
     * 
     * @return
     */
    public String deleteAll() {
        try {
            spellRepository.deleteAll();
            return "Tutti gli incantesimi rimossi";
        } catch (Exception e) {
            return "Non è stato possibile cancellare tutti gli incantesimi";
        }
    }

    /**
     * il metodo serve per aggiungere un incantesimo al nostro database,
     * restituisce un messaggio affermativo nel caso in cui l'operazione sia andata
     * a buon fine
     * altrimenti restituisce un messaggio negativo, mostrando in console il tipo di
     * errore
     * 
     * @return
     */
    public String addR() {
        try {
            Random casuale = new Random();

            spellRepository.save(generate()[casuale.nextInt(1, quantita())]);
            return "incantesimo aggiunto";
        } catch (Exception e) {
            System.out.println("errore: " + e);
            return "non è stato possibile aggiungere l'incantesimo";
        }
    }

    /**
     * aggiunge un incantesimo casuale se non è già presente nel DB
     * 
     * @return
     */
    public String addRandom() {
        try {
            Random casuale = new Random();
            Spell nuovo = (generate()[casuale.nextInt(1, quantita())]);
            if (!isSpellPresent(nuovo.getId())) {
                spellRepository.save(nuovo);
                return "incantesimo aggiunto";
            }
            return "non è stato possibile aggiungere l'incantesimo" + nuovo.getId();
        } catch (Exception e) {
            System.out.println("errore: " + e);
            return "non è stato possibile aggiungere l'incantesimo";
        }
    }

    /**
     * Cerca nel database se un incantesimo è già presente
     * 
     * @param id
     * @return
     */
    public boolean isSpellPresent(String id) {
        for (Spell spell : getAllSpells()) {
            if (spell.getId().equals(id))
                return true;
        }

        return false;
    }

}
