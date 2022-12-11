package subway.view;

import java.util.List;

public class OutputView {

    public void welcomeMain() {
        System.out.println("## 메인 화면\n" +
                "1. 경로 조회\n" +
                "Q. 종료\n");
    }

    public void choose() {
        System.out.println("## 원하는 기능을 선택하세요.");
    }

    public void selectFunc() {
        System.out.println("\n## 경로 기준\n" +
                "1. 최단 거리\n" +
                "2. 최소 시간\n" +
                "B. 돌아가기\n");
    }

    public void start() {
        System.out.println("\n## 출발역을 입력하세요.");
    }

    public void end() {
        System.out.println("\n## 도착역을 입력하세요.");
    }

    // 조회 결과
    public void printResult(List<String> result, int dist, int time) {
        System.out.println("\n## 조회 결과\n" +
                "[INFO] ---\n" +
                "[INFO] 총 거리: " + dist + "km\n" +
                "[INFO] 총 소요 시간: " + time + "분\n" +
                "[INFO] ---");
        for (String station : result) {
            System.out.println("[INFO] " + station);
        }
        System.out.println();
    }

}
