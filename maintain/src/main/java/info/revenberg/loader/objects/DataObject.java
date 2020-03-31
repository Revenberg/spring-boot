package info.revenberg.loader.objects;

import java.util.ArrayList;
import java.util.List;

import info.revenberg.domain.Line;

public class DataObject {
    List<Line> lines = new ArrayList<Line>();

    public DataObject() {
    }

    public void add(Line line) {
        this.lines.add(line);
    }

	public List<Line> getLines() {
		return this.lines;
	}
}
