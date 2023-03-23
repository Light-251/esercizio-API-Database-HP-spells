package it.softwareInside.FreeGamesApiLezione23.services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class GeneratePDFService {

    @Autowired
    SpellService spellService;

    public ByteArrayInputStream generaPDF(String id) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            Paragraph contenuto = new Paragraph();
            contenuto.add(spellService.getSpell(id).toString());

            PdfWriter.getInstance((document), out);
            document.open();
            document.add(contenuto);
            document.close();

            return new ByteArrayInputStream(out.toByteArray());
        } catch (Exception e) {
            return null;
        }

    }

    public ByteArrayInputStream generaPDFAll() {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            Paragraph contenuto = new Paragraph();
            contenuto.add(spellService.getAllSpells().toString());

            PdfWriter.getInstance((document), out);
            document.open();
            document.add(contenuto);
            document.close();

            return new ByteArrayInputStream(out.toByteArray());
        } catch (Exception e) {
            return null;
        }

    }

}
