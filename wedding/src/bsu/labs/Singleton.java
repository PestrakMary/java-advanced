package bsu.labs;

import java.util.ArrayList;

public class Singleton<T> {
    private static Singleton instance;
    private ArrayList<T> collection;
    private Singleton(){
        collection = new ArrayList<>();
    }
    public static Singleton getInstance(){
        if(instance == null){
            instance = new Singleton();
        }
        return instance;
    }

    public ArrayList<T> getCollection() {
        return collection;
    }
}
