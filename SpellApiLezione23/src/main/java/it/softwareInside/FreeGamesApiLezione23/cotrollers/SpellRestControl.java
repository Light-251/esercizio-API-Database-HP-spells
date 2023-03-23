package it.softwareInside.FreeGamesApiLezione23.cotrollers;

import java.io.ByteArrayInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import it.softwareInside.FreeGamesApiLezione23.models.Spell;
import it.softwareInside.FreeGamesApiLezione23.services.GeneratePDFService;
import it.softwareInside.FreeGamesApiLezione23.services.SpellService;

@RestController
public class SpellRestControl {

    @Autowired
    SpellService spellService;
    @Autowired
    GeneratePDFService generatePDFService;

    @GetMapping("/add-spell-by-id")
    public Spell getSpell(@RequestParam("id") String id) {
        return spellService.getSpell(id);
    }

    @DeleteMapping("/remove-spell-by-id")
    public Spell deleteById(@RequestParam("id") String id) {
        return spellService.deleteSpellById(id);
    }

    @DeleteMapping("/remove-all-spells")
    public String deleteAllSpells() {
        return spellService.deleteAll();
    }

    @GetMapping("/get-all-spells")
    public Spell[] getAllSPells() {
        return spellService.generate();
    }

    @GetMapping("/get-spell-by-id")
    public Spell getSpellById(@RequestParam("id") String id) {
        return spellService.getSpellById(id);
    }

    @GetMapping("/numero-spell")
    public int getSpellNumber() {
        return spellService.quantita();
    }

    @PostMapping("/add-random-spell")
    public String addRanomSpell() {
        return spellService.addRandom();
    }

    // salva nel PDF un solo incantesimo tramite id
    @RequestMapping(value = "/genera-pdf", method = RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> generaPDF(@RequestParam("id") String id) {

        try {

            ByteArrayInputStream bis = generatePDFService.generaPDF(id);

            var headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=example.pdf");

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(new InputStreamResource(bis));

        } catch (Exception e) {
            return null;
        }
    }

    // salva nel PDF tutti gli incantesimi
    @RequestMapping(value = "/database-pdf", method = RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> generaPDFAll() {

        try {

            ByteArrayInputStream bis = generatePDFService.generaPDFAll();

            var headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=example.pdf");

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(new InputStreamResource(bis));

        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping("/controllo-presenza")
    public boolean isSpellPresent(@RequestParam("id") String id) {
        return spellService.isSpellPresent(id);
    }
}
