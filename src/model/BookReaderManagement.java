package model;

public class BookReaderManagement {
    private Book book;
    private Reader reader;
    private int numOfBorrow;
    private String state;
    private int totalBorrowed;

    public BookReaderManagement() {};

    public BookReaderManagement(Book book) {
        this.book = book;
    }

    public BookReaderManagement(Book book, Reader reader,
                                int numOfBorrow, String state, int totalBorrow) {
        this.book = book;
        this.reader = reader;
        this.numOfBorrow = numOfBorrow;
        this.state = state;
        this.totalBorrowed = totalBorrowed;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public int getNumOfBorrow() {
        return numOfBorrow;
    }

    public void setNumOfBorrow(int numOfBorrow) {
        this.numOfBorrow = numOfBorrow;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getTotalBorrowed() {
        return totalBorrowed;
    }

    public void setTotalBorrowed(int totalBorrowed) {
        this.totalBorrowed = totalBorrowed;
    }

    @Override
    public String toString() {
        return "BRM{" +
                "readerID=" + reader.getReaderId() +
                ", readerName=" + reader.getFullName() +
                ", bookID=" + book.getBookID() +
                ", bookName=" + book.getBookName() +
                ", numOfBorrow=" + numOfBorrow +
                ", state='" + state + '\'' +
                ", totalBorrowed=" + totalBorrowed +
                '}';
    }
}
