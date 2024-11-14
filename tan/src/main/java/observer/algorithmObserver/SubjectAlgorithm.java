package observer.algorithmObserver;

import java.util.ArrayList;
import java.util.List;

public interface SubjectAlgorithm {
//    List<ObserverAlgorithm> observers = new ArrayList<>();

    boolean register(ObserverAlgorithm observer);

    boolean remove(ObserverAlgorithm observer);

    void notifyAlgorithmObservers();
}
