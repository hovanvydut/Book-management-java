package controller;

import model.Book;
import model.BookReaderManagement;
import model.Reader;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class DataController {
    private FileWriter fileWriter;
    private BufferedWriter bufferedWriter;
    private PrintWriter printWriter;
    private Scanner scanner;

    public void openFileToWrite(String fileName) {
        try {
            this.fileWriter = new FileWriter(fileName, true);
            this.bufferedWriter = new BufferedWriter(this.fileWriter);
            this.printWriter = new PrintWriter(this.bufferedWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void writeBookToFile(Book book, String fileName) {
        openFileToWrite(fileName);
        this.printWriter.println(book.getBookID() + "|" + book.getBookName() + "|"
                + book.getAuthor() + "|" + book.getSpecialization() + "|"
                + book.getPublishYear() + "|" + book.getQuantity());
        closeFileAfterWrite(fileName);
    }

    void writeReaderToFile(Reader reader, String fileName) {
        openFileToWrite(fileName);
        this.printWriter.println(reader.getReaderId() + "|" + reader.getFullName() + "|"
                + reader.getAddress() + "|" + reader.getPhoneNumber());
        closeFileAfterWrite(fileName);
    }

    void writeBRMToFile(BookReaderManagement brm, String fileName) {
        openFileToWrite(fileName);
        this.printWriter.println(brm.getReader().getReaderId() + "|" + brm.getBook().getBookID() + "|"
                + brm.getNumOfBorrow() + "|" + brm.getState());
        closeFileAfterWrite(fileName);
    }

    void closeFileAfterWrite(String fileName) {
        try {
            this.printWriter.close();
            this.bufferedWriter.close();
            fileWriter.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
