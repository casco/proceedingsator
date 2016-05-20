package ar.org.sadio;

import com.itextpdf.text.DocumentException;

import java.io.*;
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
    Proceedings proceedings;
    private Logger logger;



    public Proceedingsator() {
        logger = Logger.getLogger(Proceedingsator.class.getName());
        proceedings = new Proceedings(acronym, conference, issn, simposia);
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
        ator.generateHtml();
    }

    private void generateHtml() {
        HtmlPrinter printer = new HtmlPrinter();
    }

    public String getInputFolder() {
        return inputFolder;
    }

    private void stampArticles() throws IOException, DocumentException {
        proceedings.stampArticles(outputFolder);
    }


    private void readArticleList() {
        Reader in = null;
        try {
            in = new FileReader("example/input/articles.csv");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        proceedings.readArticleList(in, inputFolder);
    }






}
