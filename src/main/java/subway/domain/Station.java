package subway.domain;

import java.util.ArrayList;
import java.util.List;

public class Station {
    private String name;

    public Station(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    // 추가 기능 구현
    private List<Line> lines = new ArrayList<>();

    public void addLineInfo(Line line) {
        lines.add(line);
    }
}
