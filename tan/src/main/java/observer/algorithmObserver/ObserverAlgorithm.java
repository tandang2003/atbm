package observer.algorithmObserver;

import model.common.Cipher;
import observer.alphabetObserver.AlphaObserver;
import view.AlgorithmPanel.VAlgorithmAbs;

public interface ObserverAlgorithm {
    void update(VAlgorithmAbs algorithmAbs, Cipher cipher);
}
