package observer.alphabet;

import java.util.List;

public abstract class AlphaSubject {
    protected List<AlphaObserver> observers;
    protected AlphaSubject(){
        observers = new java.util.ArrayList<>();
    }

    public boolean register(AlphaObserver observer) {
        if (observers.contains(observer)) {
            return false;
        }
        observers.add(observer);
        return true;
    }

    public boolean unregister(AlphaObserver observer) {
        if (!observers.contains(observer)) {
            return false;
        }
        observers.remove(observer);
        return true;
    }

    public abstract void notifyObservers() ;

    ;

}
