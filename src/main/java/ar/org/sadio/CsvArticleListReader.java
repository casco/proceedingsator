package ar.org.sadio;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

/**
 * Created by alejandrofernandez on 6/1/16.
 */
public class CsvArticleListReader implements ArticleListReader {

    String inputFolder;
    String listFileName;

    public CsvArticleListReader(String inputFolder, String listFileName) {
        this.inputFolder = inputFolder;
        this.listFileName = listFileName;
    }

    @Override
    public void readInto(Proceedings proceedings) {
        Reader in = null;
        try {
            in = new FileReader(listFileName);
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
            String title = record.get("titulo");
            String authors = record.get("autores");
            String file = record.get("documento");
            proceedings.addArticle(new Article(title, authors, file, inputFolder));
        }

    }
}
