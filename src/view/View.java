package view;

import controller.DataController;
import model.Book;
import model.BookReaderManagement;
import model.Reader;

import java.util.ArrayList;
import java.util.Scanner;

public class View {
    public static void main(String[] args) {
        int choice = 0;
        var bookFileName = "BOOK.DAT";
        var readersFileName = "READER.DAT";
        var brmFileName = "BRM.DAT";
        var controller = new DataController();

        var books = new ArrayList<Book>();
        var readers = new ArrayList<Reader>();
        var brms = new ArrayList<BookReaderManagement>();

        boolean isCheckedBook = false;
        boolean isReaderCheck = false;

        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("_______________MENU_______________");
            System.out.println("1. Thêm một đầu sách vào file");
            System.out.println("2. Hiển thị danh sách các sách có trong file");
            System.out.println("3. Thêm một bạn đọc vào file");
            System.out.println("4. Hiển thị danh sách các bạn đọc có trong file");
            System.out.println("5. Lập thông tin quản lý mượn");
            System.out.println("0. Thoát khỏi ứng dụng");
            System.out.println("Bạn chọn ?");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 0: {
                    System.out.println("______________________________");
                    System.out.println("Cảm ơn bạn đã sử dụng ứng dụng!");
                    break;
                }
                case 1: {
                    if (!isCheckedBook) {
                        checkBookID(controller, bookFileName);
                        isCheckedBook = true;
                    }

                    String[] specs = {"Science", "Art", "Economic", "IT"};
                    String bookName, author, specialization;
                    int publishYear, quantity, specChoice;

                    System.out.println("Nhập tên sách: ");
                    bookName = scanner.nextLine();

                    System.out.println("Nhập tên tác giả: ");
                    author = scanner.nextLine();

                    System.out.println("Chọn thể loại sách: ");
                    System.out.println("1. Science \n2. Art \n3. Economic \n4. IT");
                    do {
                        specChoice = scanner.nextInt();
                    } while (specChoice < 1 || specChoice > 4);
                    specialization = specs[specChoice - 1];

                    System.out.println("Nhập năm xuất bản: ");
                    publishYear = scanner.nextInt();

                    System.out.println("Nhập số lượng: ");
                    quantity = scanner.nextInt();

                    Book book = new Book(0, bookName, author, specialization, publishYear, quantity);
                    controller.writeBookToFile(book, bookFileName);
                    break;
                }
                case 2: {
                    books = controller.readBooksFromFile(bookFileName);
                    showBookInfo(books);
                    break;
                }
                case 3: {
                    if (!isReaderCheck) {
                        checkReaderID(controller, readersFileName);
                        isReaderCheck = true;
                    }

                    String fullName, address, phoneNumber;
                    System.out.println("Nhập họ tên: ");
                    fullName = scanner.nextLine();

                    System.out.println("Nhập địa chỉ: ");
                    address = scanner.nextLine();

                    do {
                        System.out.println("Nhập số điện thoại: ");
                        phoneNumber = scanner.nextLine();
                    } while (!phoneNumber.matches("\\d{10}"));

                    Reader reader = new Reader(0, fullName, address, phoneNumber);
                    controller.writeReaderToFile(reader, readersFileName);
                  break;
                }
                case 4: {
                    readers = controller.readReadersFromFile(readersFileName);
                    showReaderInfo(readers);
                    break;
                }
                case 5: {
                    readers = controller.readReadersFromFile(readersFileName);
                    books = controller.readBooksFromFile(bookFileName);
                    brms = controller.readBRMsFromFile(brmFileName);

                    int readerID, bookID;
                    boolean isBorrowable = false;
                    do {
                        showReaderInfo(readers);
                        System.out.println("-----------------------------------");
                        System.out.println("Nhập mã bạn đọc, nhập 0 để bỏ qua: ");
                        readerID = scanner.nextInt();

                        if (readerID == 0)
                            break;

                        isBorrowable = checkBorrowed(brms, readerID);
                        if (isBorrowable)
                            break;
                        else
                            System.out.println("Bạn đọc đã mượn đủ số lượng cho phép!");
                    } while (true);
                    break;
                }
            }
        } while (choice != 0);
    }

    private static boolean checkBorrowed(ArrayList<BookReaderManagement> brms, int readerID) {
        int count = 0;
        for (var r : brms) {
            if (r.getReader().getReaderId() == readerID)
                count += r.getNumOfBorrow();
        }

        if (count >= 15) return false;
        return true;
    }

    private static void showReaderInfo(ArrayList<Reader> readers) {
        System.out.println("___________Thông tin bạn đọc trong file___________");
        for (Reader reader : readers)
            System.out.println(reader.toString());
    }

    private static void checkReaderID(DataController controller, String readersFileName) {
        var readers = controller.readReadersFromFile(readersFileName);

        if (readers.size() != 0)
            Reader.setId(readers.get(readers.size() - 1).getReaderId() + 1);

    }

    private static void checkBookID(DataController controller, String fileName) {
        ArrayList<Book> listBooks = controller.readBooksFromFile(fileName);

        if (listBooks.size() != 0)
            Book.setId(listBooks.get(listBooks.size() - 1).getBookID() + 1);
    }

    private static void showBookInfo(ArrayList<Book> books) {
        System.out.println("___________Thông tin sách trong file___________");
        for (Book book: books) {
            System.out.println(book.toString());
        }
    }
}
