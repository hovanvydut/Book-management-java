package controller;

import model.BookReaderManagement;

import java.util.ArrayList;

public class ControllerUtility {
    public ArrayList<BookReaderManagement> updateBRMsFile(ArrayList<BookReaderManagement> brmList,
                                                          BookReaderManagement brm) {
        boolean isUpdate = false;

        for (int i = 0; i < brmList.size(); i++) {
            if (brmList.get(i).getBook().getBookID() == brm.getBook().getBookID()
                    && brmList.get(i).getReader().getReaderId() == brm.getReader().getReaderId()) {
                brmList.set(i, brm);
                isUpdate = true;
                break;
            }
        }

        if (!isUpdate) {
            brmList.add(brm);
        }
        return brmList;
    }

    public ArrayList<BookReaderManagement> updateTotalBorrow(ArrayList<BookReaderManagement> list) {
        for (int i = 0; i < list.size(); i++) {
            BookReaderManagement b = list.get(i);
            int count = 0;
            for (int j = 0; j < list.size(); j++) {
                if (list.get(j).getReader().getReaderId() == b.getReader().getReaderId())
                    count += list.get(j).getNumOfBorrow();
            }
            b.setTotalBorrowed(count);
            list.set(i, b);
        }
        return list;
    }

    public ArrayList<BookReaderManagement> sortByReaderName(ArrayList<BookReaderManagement> list) {
        // Bubble sort
        for (int i = 0; i < list.size(); i++) {
            for (int j = list.size() - 1; j > i; j--) {
                BookReaderManagement a = list.get(j - 1);
                BookReaderManagement b = list.get(j);
                String[] name1 = b.getReader().getFullName().split("\\s");
                String[] name2 = a.getReader().getFullName().split("\\s");
                if (name1[name1.length - 1].compareTo(name2[name2.length - 1]) < 0) {
                    list.set(j, a);
                    list.set(j - 1, b);
                }
            }
        }
        return list;
    }

    public ArrayList<BookReaderManagement> sortByNumOfBorrow(ArrayList<BookReaderManagement> list) {
        // Bubble sort
        for (int i = 0; i < list.size(); i++) {
            for (int j = list.size() - 1; j > i; j--) {
                BookReaderManagement a = list.get(j - 1);
                BookReaderManagement b = list.get(j);

                if (b.getTotalBorrowed() > a.getTotalBorrowed()) {
                    list.set(j, a);
                    list.set(j - 1, b);
                }
            }
        }
        return list;
    }
}
