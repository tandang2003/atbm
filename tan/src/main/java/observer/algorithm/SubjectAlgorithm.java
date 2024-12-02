package observer.algorithm;

public interface SubjectAlgorithm {

    boolean register(ObserverAlgorithm observer);

    boolean remove(ObserverAlgorithm observer);

    void notifyAlgorithmObservers();
}
