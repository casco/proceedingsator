package ar.org.sadio;

import com.itextpdf.text.DocumentException;
import org.apache.commons.cli.*;

import java.io.*;
import java.util.logging.Logger;

/**
 * Created by alejandrofernandez on 5/19/16.
 */
public class Proceedingsator {
    Options commandLineOptions;
    Proceedings proceedings;
    String inputFolder;
    String outputFolder;
    String listFileName;
    private Logger logger;


    public Proceedingsator() {
        logger = Logger.getLogger(Proceedingsator.class.getName());
    }

    public static void main(String[] args) {
        Proceedingsator ator = new Proceedingsator();
        try {
            ator.configureFromCommandLine(args);
        } catch (Exception e) {
            ator.printHelp();
            System.exit(1);
        }
        if (ator.isReady()) {
            ator.proceed();
        }
    }

    private void printHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("proceedingsator", commandLineOptions);
    }

    private boolean isReady() {
        return true;
    }

    private void configureFromCommandLine(String[] args) throws Exception {
        prepareCommandLineOptions();
        CommandLine line = parseCommandLineOptions(args);
        if (! validateCommandLineOptions(line)) {
            throw new Exception("Argumentos incompletos o inválidos");
        }
        inputFolder = line.getOptionValue("p");
        outputFolder = line.getOptionValue("o");
        listFileName = line.getOptionValue("l");
        String acronym = line.getOptionValue("a");
        String conference = line.getOptionValue("c");
        String isbn = line.getOptionValue("i");
        String simposyum = line.getOptionValue("s");
        proceedings = new Proceedings(acronym, conference, isbn, simposyum);

    }

    private boolean validateCommandLineOptions(CommandLine line) {

        return (line.getOptionValue("a") != null &
                line.getOptionValue("c") != null &
                line.getOptionValue("i") != null &
                line.getOptionValue("s") != null &
                line.getOptionValue("l") != null &
                line.getOptionValue("p") != null &
                line.getOptionValue("o") != null);

    }

    private CommandLine parseCommandLineOptions(String[] args) {
        CommandLineParser parser = new DefaultParser();
        CommandLine line = null;
        try {
            line = parser.parse(commandLineOptions, args);
        } catch (ParseException exp) {
            System.err.println("Parsing failed.  Reason: " + exp.getMessage());
        }
        return line;
    }

    private void prepareCommandLineOptions() {
        commandLineOptions = new Options();
        commandLineOptions.addOption("c", true, "nombre corto de la conferencia (p.e. 45JAIIO)");
        commandLineOptions.addOption("s", true, "nombre completo del simposio (p.e. \"Simposio De Informática\")");
        commandLineOptions.addOption("a", true, "acrónimo del  (p.e. SIA)");
        commandLineOptions.addOption("i", true, "issn del simposio  (p.e. 2451-7585)");
        commandLineOptions.addOption("l", true, "archivo CSV con el listado de articulos (p.e. ./originales/lista.csv)");
        commandLineOptions.addOption("p", true, "ubicación de los documentos pdf (p.e. ./originales)");
        commandLineOptions.addOption("o", true, "ubicación de los documentos resultantes (p.e. ./numerados)");
    }

    private void proceed() {
        readArticleList();
        try {
            prepareOutputFolder();
            stampArticles();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        generateHtml();
    }

    private void prepareOutputFolder() {
        File htmlFile = new File(outputFolder);
        htmlFile.mkdirs();
    }

    private void generateHtml() {
        HtmlPrinter printer = new HtmlPrinter();
        printer.print(proceedings, outputFolder + "/listado.html");
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
            in = new FileReader(listFileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        proceedings.readArticleList(in, inputFolder);
    }


}
