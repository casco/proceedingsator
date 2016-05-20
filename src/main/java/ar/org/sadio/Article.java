package ar.org.sadio;

/**
 * Created by alejandrofernandez on 5/19/16.
 */
public class Article {
    String authors;
    String title;
    String filename;
    int startPage;
    int endPage;

    public Article(String title, String authors, String file) {
        this.authors = authors;
        this.title = title;
        this.filename = file;
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
}
