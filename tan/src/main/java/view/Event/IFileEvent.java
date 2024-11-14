package view.Event;

import java.awt.event.ActionListener;

public interface IFileEvent extends ActionListener {

    void onFileAccept();
    void onFileCancel();
    void onFileError();

}
