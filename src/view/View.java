package view;

import controller.DataController;
import model.Book;

import java.util.ArrayList;
import java.util.Scanner;

public class View {
    public static void main(String[] args) {
        int choice = 0;
        var bookFileName = "BOOK.DAT";
        var controller = new DataController();
        var books = new ArrayList<Book>();
        boolean isCheckedBook = false;

        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("_______________MENU_______________");
            System.out.println("1. Thêm một đầu sách vào file");
            System.out.println("2. Hiển thị danh sách các sách có trong file");
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
            }
        } while (choice != 0);
    }

    private static void checkBookID(DataController controller, String fileName) {
        ArrayList<Book> listBooks = controller.readBooksFromFile(fileName);
        Book.setId(listBooks.get(listBooks.size() - 1).getBookID() + 1);
    }

    private static void showBookInfo(ArrayList<Book> books) {
        System.out.println("___________Thông tin sách trong file___________");
        for (Book book: books) {
            System.out.println(book.toString());
        }
    }
}
