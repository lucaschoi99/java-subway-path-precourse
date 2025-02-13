package subway.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class LineRepository {
    private static final List<Line> lines = new ArrayList<>();

    public static List<Line> lines() {
        return Collections.unmodifiableList(lines);
    }

    public static void addLine(Line line) {
        lines.add(line);
    }

    public static boolean deleteLineByName(String name) {
        return lines.removeIf(line -> Objects.equals(line.getName(), name));
    }

    public static void deleteAll() {
        lines.clear();
    }

    // 라인 이름으로 Line 객체 찾기
    public static Line findLineByName(String name) {
        return lines().stream()
                .filter(s -> s.getName().equals(name))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
