package ar.org.sadio;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class HtmlPrinter {

    public void print(Proceedings proceedings, String outputFilename) {
        File htmlFile = new File(outputFilename);
        htmlFile.getParentFile().mkdirs();

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(htmlFile));
            writer.write("<html>\n<body>\n");
            writer.write("\t<ul>\n");
            for (Article art : proceedings.getArticles()) {
                writer.write("\t\t<li>");
                writer.write(art.getAuthors() + " - ");
                writer.write("<a href=\"http://host/folder/" + art.getFilename() + "\">"
                        + art.getTitle() + "</a> (" + art.getStartPage() + "-" + art.getEndPage() + ")" );
                writer.write("</li>\n");
             }
            writer.write("\t</ul>\n");
            writer.write("</body>\n</html>");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (Exception e) {
            }
        }

    }
}
