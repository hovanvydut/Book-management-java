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
}
