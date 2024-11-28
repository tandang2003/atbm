package observer.algorithm;

public interface SubjectAlgorithm {
//    List<ObserverAlgorithm> observers = new ArrayList<>();

    boolean register(ObserverAlgorithm observer);

    boolean remove(ObserverAlgorithm observer);

    void notifyAlgorithmObservers();
}
