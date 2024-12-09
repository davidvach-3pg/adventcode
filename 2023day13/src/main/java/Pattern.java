import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Pattern {

    List<String> lines = new ArrayList<String>();
    List<String> columns = new ArrayList<String>();

    public void appendLine(String line) {
        lines.add(line);
    }

    public int getVerticalRFeflectionCount() {
        List<Integer> verticalRFeflectionCollectiont = getVerticalReflectionCollection();
        if (verticalRFeflectionCollectiont.isEmpty()) return 0;
        return verticalRFeflectionCollectiont.get(0);
    }

    public int getHorizontalRFeflectionCount() {
        List<Integer> horizontalRFeflectionCollection = getHorizontalRFeflectionCollection();
        if (horizontalRFeflectionCollection.isEmpty()) return 0;
        return horizontalRFeflectionCollection.get(0);
    }

    public List<Integer> getVerticalReflectionCollection() {
        List<Integer> result = new ArrayList<Integer>();
        for (int i=1;i<columns.size();i++) {
            int width = Math.min(i-0, columns.size()-i);
            boolean found = true;
            for (int x=0;x<width&found;x++) {
                found &= columns.get(i-x-1).equals(columns.get(i+x));
            }
            if (found) result.add(i);
        }
        return result;
    }

    public List<Integer> getHorizontalRFeflectionCollection() {
        List<Integer> result = new ArrayList<Integer>();
        for (int i=1;i<lines.size();i++) {
            int height = Math.min(i-0, lines.size()-i);
            boolean found = true;
            for (int y=0;y<height&found;y++) {
                found &= lines.get(i-y-1).equals(lines.get(i+y));
            }
            if (found) result.add(i);
        }
        return result;
    }

    public void computeColumns() {
        columns.clear();
        for (int i=0;i<lines.get(0).length();i++) {
            StringBuffer buffer = new StringBuffer();
            for (int r=0;r<lines.size();r++) {
                buffer.append(lines.get(r).charAt(i));
            }
            columns.add(buffer.toString());
        }
    }

    public void dump() {
        lines.forEach(l -> System.out.println(l));
    }

    public void swap(int x, int y) {
        String string = lines.get(y);
        char c = lines.get(y).charAt(x);
        StringBuffer b = new StringBuffer(string);
        b.setCharAt(x, (c == '#') ? '.' : '#');
        lines.set(y, b.toString());
        computeColumns();
    }
}
