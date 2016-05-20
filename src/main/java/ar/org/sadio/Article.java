package ar.org.sadio;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import java.io.FileOutputStream;
import java.io.IOException;

import static ar.org.sadio.Proceedingsator.acronym;
import static ar.org.sadio.Proceedingsator.simposia;

/**
 * Created by alejandrofernandez on 5/19/16.
 */
public class Article {
    String authors;
    String title;
    String filename;
    String location;
    int startPage;
    int endPage;

    public Article(String title, String authors, String filename, String location) {
        this.authors = authors;
        this.filename = filename;
        this.location = location;
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getEndPage() {
        return endPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    public int getStartPage() {
        return startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    protected void stamp(String conference, String issn, int firstPage, String outputLocation) throws IOException, DocumentException {
        PdfReader reader = new PdfReader(getLocation() + "/" + getFilename());
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(outputLocation + "/" + getFilename()));
        int pages = reader.getNumberOfPages();
        setStartPage(firstPage);
        setEndPage(getStartPage() + pages - 1);
        for (int i = 0; i < pages; i++) {
            PdfContentByte over = stamper.getOverContent(i + 1);
            Font f = new Font(Font.FontFamily.HELVETICA, 8);
            Phrase p = new Phrase(acronym + ", " + simposia, f);
            ColumnText.showTextAligned(over, Element.ALIGN_CENTER, p, 297, 770, 0);
            p = new Phrase(conference + " - " + acronym + " - ISSN: " +
                    issn +" - Página " + (getStartPage() + i), f);
            ColumnText.showTextAligned(over, Element.ALIGN_CENTER, p, 297, 50, 0);
        }
        stamper.close();
        reader.close();

    }
}
