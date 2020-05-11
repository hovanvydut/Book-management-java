package view;

import controller.ControllerUtility;
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

                    // chọn một bạn đọc từ danh sách để cho phép mượn,
                    // nếu số lượng sách đã mượn của bạn đọc >= 15 thì không cho mượn
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

                    // chọn một đầu sách cần mượn, nếu danh sách đó đã cho mượn tối đa số cuốn cho phép thì
                    // không được mượn tiếp, yêu cầu mượn sách khác.
                    boolean isFullBook = false;
                    do {
                        showBookInfo(books);
                        System.out.println("-----------------------------------");
                        System.out.println("Nhập mã sách, nhập 0 để bỏ qua: ");
                        bookID = scanner.nextInt();

                        if (bookID == 0)
                            break;

                        isFullBook = isFullBook(brms, readerID, bookID);
                        if (isFullBook) {
                            System.out.println("Bạn đã vượt quá số lượng mượn đối với đầu sách này. " +
                                    "Vui lòng chọn đầu sách khác!");
                            continue;
                        } else
                            break;
                    } while (true);

                    // Nếu được phép được mượn tiếp thì thực hiện nhập số lượng mượn
                    // tình trạng sách lúc mượn
                    int total = getTotal(brms, readerID, bookID);
                    do {
                        System.out.println("Nhập số lượng mượn (đã mượn " + total
                                + " ) (tối đa 3 quyển trên một đầu sách) : ");
                        int x = scanner.nextInt();

                        if ((x + total) >= 1 && (x + total) <= 3) {
                            total += x;
                            break;
                        }
                        else
                            System.out.println("Nhập quá số lượng cho phép, vui lòng nhập lại!");
                    } while (true);
                    scanner.nextLine();

                    System.out.println("Nhập tình trạng: ");
                    String status = "";
                    status = scanner.nextLine();

                    //
                    Book currentBook = getBook(books, bookID);
                    Reader currentReader = getReader(readers, readerID);
                    BookReaderManagement b = new BookReaderManagement(currentBook, currentReader, total, status, 0);

                    var ultility = new ControllerUtility();
                    ultility.updateBRMsFile(brms, b); // cap nhat danh sach quan li muon
                    controller.updateBRMFile(brms, brmFileName); // cap nhat file

                    // show BRM info
                    showBRMInfo(brms);
                    break;
                }
            }
        } while (choice != 0);
    }

    private static void showBRMInfo(ArrayList<BookReaderManagement> brms) {
        for (var brm : brms) {
            System.out.println(brm.toString());
        }
    }

    private static Reader getReader(ArrayList<Reader> readers, int readerID) {
        for (Reader reader : readers)
            if (reader.getReaderId() == readerID)
                return reader;
        return null;
    }

    private static Book getBook(ArrayList<Book> books, int bookID) {
        for (Book book : books) {
            if (book.getBookID() == bookID)
                return book;
        }
        return null;
    }

    private static int getTotal(ArrayList<BookReaderManagement> brms, int readerID, int bookID) {
        for (var r : brms) {
            if (r.getBook().getBookID() == bookID && r.getReader().getReaderId() == readerID)
                return r.getNumOfBorrow();
        }
        return 0;
    }

    private static boolean isFullBook(ArrayList<BookReaderManagement> brms, int readerID, int bookID) {
        for (var r : brms) {
            if (r.getReader().getReaderId() == readerID && r.getBook().getBookID() == bookID && r.getNumOfBorrow() >= 3)
                return true;
        }
        return false;
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
