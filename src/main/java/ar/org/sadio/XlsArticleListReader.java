package ar.org.sadio;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.util.Iterator;

/**
 * Created by alejandrofernandez on 6/1/16.
 */
public class XlsArticleListReader implements ArticleListReader {

    String inputFolder;
    String listFileName;

    public XlsArticleListReader(String inputFolder, String listFileName) {
        this.inputFolder = inputFolder;
        this.listFileName = listFileName;
    }

    @Override
    public void readInto(Proceedings proceedings) {
        InputStream in = null;
        Workbook wb = null;
        try {
            in = new FileInputStream(listFileName);
            wb = WorkbookFactory.create(in);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Sheet sheet = wb.getSheetAt(0);

        Iterator<Row> rows = sheet.rowIterator();
        rows.next(); //Ignore header
        while (rows.hasNext()) {
            Row row = rows.next();
            String title = row.getCell(0).getStringCellValue();
            String authors = row.getCell(1).getStringCellValue();
            String file = row.getCell(2).getStringCellValue();
            proceedings.addArticle(new Article(title, authors, file, inputFolder));
        }


    }
}
