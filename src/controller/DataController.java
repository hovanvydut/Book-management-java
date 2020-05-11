package controller;

import model.Book;
import model.BookReaderManagement;
import model.Reader;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class DataController {
    private FileWriter fileWriter;
    private BufferedWriter bufferedWriter;
    private PrintWriter printWriter;
    private Scanner scanner;

    // write file handle
    private void openFileToWrite(String fileName) {
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }

            this.fileWriter = new FileWriter(fileName, true);
            this.bufferedWriter = new BufferedWriter(this.fileWriter);
            this.printWriter = new PrintWriter(this.bufferedWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeBookToFile(Book book, String fileName) {
        openFileToWrite(fileName);
        this.printWriter.println(book.getBookID() + "|" + book.getBookName() + "|"
                + book.getAuthor() + "|" + book.getSpecialization() + "|"
                + book.getPublishYear() + "|" + book.getQuantity());
        closeFileAfterWrite(fileName);
    }

    public void writeReaderToFile(Reader reader, String fileName) {
        openFileToWrite(fileName);
        this.printWriter.println(reader.getReaderId() + "|" + reader.getFullName() + "|"
                + reader.getAddress() + "|" + reader.getPhoneNumber());
        closeFileAfterWrite(fileName);
    }

    public void writeBRMToFile(BookReaderManagement brm, String fileName) {
        openFileToWrite(fileName);
        this.printWriter.println(brm.getReader().getReaderId() + "|" + brm.getBook().getBookID() + "|"
                + brm.getNumOfBorrow() + "|" + brm.getState());
        closeFileAfterWrite(fileName);
    }

    private void closeFileAfterWrite(String fileName) {
        try {
            this.printWriter.close();
            this.bufferedWriter.close();
            fileWriter.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    // read file handle
    private void openFileToRead(String fileName) {
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }

            this.scanner = new Scanner(Paths.get(fileName), "UTF-8");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void closeFileAfterRead(String fileName) {
        try {
            scanner.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    // reader
    public ArrayList<Reader> readReadersFromFile(String fileName) {
        openFileToRead(fileName);
        ArrayList<Reader> readers = new ArrayList<>();
        while (this.scanner.hasNextLine()) {
            String data = this.scanner.nextLine();
            Reader reader = createReaderFromData(data);
            readers.add(reader);
        }
        closeFileAfterRead(fileName);
        return readers;
    }

    public Reader createReaderFromData(String data) {
        String[] datas = data.split("\\|");
        Reader reader = new Reader(Integer.parseInt(datas[0]), datas[1], datas[2], datas[3]);
        return reader;
    }

    // book
    public ArrayList<Book> readBooksFromFile(String fileName) {
        ArrayList<Book> books = new ArrayList<>();
        openFileToRead(fileName);
        while (this.scanner.hasNextLine()) {
            String data = scanner.nextLine();
            Book book = createBookFromData(data);
            books.add(book);
        }
        closeFileAfterRead(fileName);
        return books;
    }

    public Book createBookFromData(String data) {
        String[] datas = data.split("\\|");
        Book book = new Book(Integer.parseInt(datas[0]), datas[1], datas[2], datas[3],
                Integer.parseInt(datas[4]), Integer.parseInt(datas[5]));
        return book;
    }

    // BRM
    public ArrayList<BookReaderManagement> readBRMsFromFile(String fileName) {
        openFileToRead(fileName);
        ArrayList<BookReaderManagement> BRMs = new ArrayList<>();
        while (this.scanner.hasNextLine()) {
            String data = this.scanner.nextLine();
            BookReaderManagement brm = createBRMFromData(data);
            BRMs.add(brm);
        }
        closeFileAfterRead(fileName);
        return BRMs;
    }

    public BookReaderManagement createBRMFromData(String data) {
        String[] datas = data.split("\\|");
        BookReaderManagement brm = new BookReaderManagement(new Book(Integer.parseInt(datas[1])), new Reader(Integer.parseInt(datas[0])),
                Integer.parseInt(datas[2]), datas[3], 0);
        return brm;
    }

    public void updateBRMFile(ArrayList<BookReaderManagement> brmList, String fileName) {
        // xóa bỏ file cũ
        File file = new File(fileName);
        if (file.exists())
            file.delete();

        // ghi mới file
        openFileToWrite(fileName);
        for (var brm : brmList)
            this.printWriter.println(brm.getReader().getReaderId() + "|" + brm.getBook().getBookID() + "|"
                    + brm.getNumOfBorrow() + "|" + brm.getState());
        closeFileAfterWrite(fileName);
    }
}
