package observer.algorithm;

import model.common.ICipherEnum;
import view.algorithmPanel.VAlgorithmAbs;

public interface ObserverAlgorithm {
    void update(VAlgorithmAbs algorithmAbs, ICipherEnum cipher);
}
