package view.worker;

import javax.swing.*;

public interface IWorker {
    String GEN_KEY_LABEL = "Generating key...";
    String LOADING_LABEL = "Loading...";
    void init();

    JDialog createDialog();

    JProgressBar createProgressBar();
    void execute();
}
