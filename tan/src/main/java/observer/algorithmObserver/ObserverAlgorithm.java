package observer.algorithmObserver;

import model.common.Cipher;
import model.common.ICipherEnum;
import observer.alphabetObserver.AlphaObserver;
import view.AlgorithmPanel.VAlgorithmAbs;

public interface ObserverAlgorithm {
    void update(VAlgorithmAbs algorithmAbs, ICipherEnum cipher);
}
