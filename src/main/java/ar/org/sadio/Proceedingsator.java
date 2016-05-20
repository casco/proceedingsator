package ar.org.sadio;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.List;
import java.util.Vector;
import java.util.logging.Logger;

/**
 * Created by alejandrofernandez on 5/19/16.
 */
public class Proceedingsator {
    static String simposia = "Argentine Symposium on Artificial Intelligence";
    static String acronym = "ASAI 2015";
    static String conference = "44jaiio";
    static String issn = "2451-7585";
    static String metaFile = "articles.csv";
    static String inputFolder = "example/input";
    static String outputFolder = "example/output";
    private Logger logger;
    List<Article> articles;
    int lastUsedPageNumber;

    public Proceedingsator() {
        logger = Logger.getLogger(Proceedingsator.class.getName());
        lastUsedPageNumber = 0;
        articles = new Vector<Article>();
        new File(outputFolder).mkdirs();
    }

    public static void main(String[] args) {
        Proceedingsator ator = new Proceedingsator();
        ator.readArticleList();
        try {
            ator.stampArticles();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }


    private void readArticleList() {
        Reader in = null;
        try {
            in = new FileReader("example/input/articles.csv");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Iterable<CSVRecord> records = null;
        try {
            records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (CSVRecord record : records) {
            String title = record.get("title");
            String authors = record.get("authors");
            String file = record.get("filename");
            articles.add(new Article(title, authors, file));
        }
        logger.info("Read " + articles.size() + " articles");
    }

    private void stampArticles() throws IOException, DocumentException {
        for (Article article : articles) {
            stamp(article);
        }
    }

    private void stamp(Article article) throws IOException, DocumentException {
        PdfReader reader = new PdfReader(inputFolder + "/" + article.getFilename());
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(outputFolder + "/" + article.getFilename()));
        int pages = reader.getNumberOfPages();
        article.setStartPage(lastUsedPageNumber + 1);
        article.setEndPage(article.getStartPage() + pages - 1);
        lastUsedPageNumber = article.getEndPage();
        for (int i = 0; i < pages; i++) {
            PdfContentByte over = stamper.getOverContent(i + 1);
            Font f = new Font(Font.FontFamily.HELVETICA, 8);
            Phrase p = new Phrase(acronym + ", " + simposia, f);
            ColumnText.showTextAligned(over, Element.ALIGN_CENTER, p, 297, 770, 0);
            p = new Phrase(conference + " - " + acronym + " - ISSN: " +
                     issn +" - Página " + (article.getStartPage() + i), f);
            ColumnText.showTextAligned(over, Element.ALIGN_CENTER, p, 297, 50, 0);
        }
        stamper.close();
        reader.close();

    }


}
