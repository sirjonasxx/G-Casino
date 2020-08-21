package misc;

public class TextReader {

    private String text;
    private int index;

    public TextReader(String text, int index) {
        this.text = text;
        this.index = index;
    }

    public String read(int len) {
        String result = text.substring(index, index + len);
        index += len;
        return result;
    }

    public String getText() {
        return text;
    }

    public int getIndex() {
        return index;
    }

    public boolean isEOF() {
        return index >= text.length();
    }
}
