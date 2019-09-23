package bsu.labs;

import java.util.Objects;

class DictionaryEntry {
   private String word;
   private int position;

    public DictionaryEntry(String word, int position) {
        this.word = word;
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DictionaryEntry that = (DictionaryEntry) o;

        if (position != that.position) return false;
        return word != null ? word.equals(that.word) : that.word == null;
    }

    @Override
    public int hashCode() {
        int result = word != null ? word.hashCode() : 0;
        result = 31 * result + position;
        return result;
    }

    public String getWord() {
        return word;
    }

    public int getPosition() {
        return position;
    }



}
