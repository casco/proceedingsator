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
    static String acronym = "ASAI";
    static String issn = "2451-7585";
    static String metaFile = "articles.csv";
    static String inputFolder = "example/input";
    static String outputFolder = "example/output";
    private Logger logger;
    List<Article> articles;
    int pageNumber;

    public Proceedingsator() {
        logger = Logger.getLogger(Proceedingsator.class.getName());
        pageNumber = 1;
        articles = new Vector<Article>();
        new File(outputFolder).mkdirs();
    }


    public static void main(String[] args) {
        Proceedingsator ator = new Proceedingsator();
        ator.readArticleList();

    }

    private  void readArticleList() {
        Reader in = null;
        try {
            in = new FileReader("example/input/articles.csv");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Iterable<CSVRecord>  records = null;
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
        try {
            stamp(articles.get(0));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    private void stamp (Article article) throws IOException, DocumentException {
        PdfReader reader = null;
        PdfStamper stamper = null;
        reader = new PdfReader(inputFolder + "/" + article.getFilename());
        stamper = new PdfStamper(reader, new FileOutputStream(outputFolder + "/" + article.getFilename()));

        System.out.println(reader.getNumberOfPages() + " pages");

        PdfContentByte under = stamper.getUnderContent(1);
        Font f = new Font(Font.FontFamily.HELVETICA, 15);
        Phrase p = new Phrase("This watermark is added UNDER the existing content", f);
        ColumnText.showTextAligned(under, Element.ALIGN_CENTER, p, 297, 550, 0);
        PdfContentByte over = stamper.getOverContent(1);
        p = new Phrase("This watermark is added ON TOP OF the existing content", f);
        ColumnText.showTextAligned(over, Element.ALIGN_CENTER, p, 297, 500, 0);
        p = new Phrase("This TRANSPARENT watermark is added ON TOP OF the existing content", f);
        over.saveState();
        PdfGState gs1 = new PdfGState();
        gs1.setFillOpacity(0.5f);
        over.setGState(gs1);
        ColumnText.showTextAligned(over, Element.ALIGN_CENTER, p, 297, 450, 0);
        over.restoreState();
        stamper.close();
        reader.close();

    }


}
