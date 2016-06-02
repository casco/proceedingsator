package ar.org.sadio;

import com.itextpdf.text.DocumentException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Vector;
import java.util.logging.Logger;


public class Proceedings {
    String simposia;
    String acronym;
    String conference;
    String issn;
    int lastUsedPageNumber;
    List<Article> articles;
    private Logger logger;

    public Proceedings(String acronym, String conference, String issn, String simposia) {
        logger = Logger.getLogger(Proceedingsator.class.getName());
        this.acronym = acronym;
        this.conference = conference;
        this.issn = issn;
        this.simposia = simposia;
        articles = new Vector<Article>();
        lastUsedPageNumber = 0;
    }

    public String getAcronym() {
        return acronym;
    }

    public String getConference() {
        return conference;
    }

    public String getIssn() {
        return issn;
    }

    public String getSimposia() {
        return simposia;
    }

    public int getLastUsedPageNumber() {
        return lastUsedPageNumber;
    }

    public void setLastUsedPageNumber(int lastUsedPageNumber) {
        this.lastUsedPageNumber = lastUsedPageNumber;
    }

    public void addArticle(Article article) {
        articles.add(article);
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void stampArticles(String outputFolder) throws IOException, DocumentException {
        for (Article article : articles) {
            article.stamp(getConference(), getIssn(), getLastUsedPageNumber() + 1,
                    outputFolder, getAcronym(), getSimposia());
            setLastUsedPageNumber(article.getEndPage());
        }
    }


}
