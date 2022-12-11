package subway;

import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import subway.domain.Line;
import subway.domain.LineRepository;
import subway.domain.Station;
import subway.domain.StationRepository;
import subway.service.LookUpLogic;
import subway.view.InputView;
import subway.view.OutputView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static subway.ErrorMessage.*;
import static subway.domain.LineRepository.*;
import static subway.domain.StationRepository.*;

public class Application {

    static OutputView outputView = new OutputView();
    static InputView inputView = new InputView();

    static WeightedMultigraph<String, DefaultWeightedEdge> distGraph
            = new WeightedMultigraph(DefaultWeightedEdge.class);

    static WeightedMultigraph<String, DefaultWeightedEdge> timeGraph
            = new WeightedMultigraph(DefaultWeightedEdge.class);

    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        init();
        mainLoop(scanner);
    }

    private static void mainLoop(Scanner scanner) {
        while (true) {
            outputView.welcomeMain();
            outputView.choose();
            try {
                String mode = inputView.chooseMode(scanner);
                if (mode.equals("Q")) {
                    break;
                }
                continueOrQuit(scanner);
            } catch (IllegalArgumentException e) {
                System.out.println(INVALID_USER_INPUT.getMessage() + "\n");
            }
        }
    }

    private static void continueOrQuit(Scanner scanner) {
        try {
            outputView.selectFunc();
            outputView.choose();
            String func = inputView.selectFunc(scanner);
            selectFunction(scanner, func);
            return;
        } catch (IllegalArgumentException e) {
            System.out.println(INVALID_USER_INPUT.getMessage());
            continueOrQuit(scanner);
        }
    }

    private static void selectFunction(Scanner scanner, String func) {
        if (func.equals("1")) {
            if (computeLogic(scanner, distGraph)) {
                continueOrQuit(scanner);
            }
        }
        if (func.equals("2")) {
            if (computeLogic(scanner, timeGraph)) {
                continueOrQuit(scanner);
            }
        }
    }

    // 최단 거리, 최소 시간 로직
    private static boolean computeLogic(Scanner scanner, WeightedMultigraph<String, DefaultWeightedEdge> graph) {
        try {
            List<String> destInfo = commonPathLogic(scanner);
            if (!checkDestination(destInfo.get(0), destInfo.get(1))) {
                LookUpLogic lookUpLogic = new LookUpLogic(findStationByName(destInfo.get(0)), findStationByName(destInfo.get(1)), graph);
                List<String> stationsResult = lookUpLogic.shortestPathList();
                int dist = lookUpLogic.computeWeight(stationsResult, distGraph);
                int time = lookUpLogic.computeWeight(stationsResult, timeGraph);
                outputView.printResult(stationsResult, dist, time);
                return false;
            }
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println(INVALID_STATION_NAME.getMessage());
            return true;
        }
    }


    // 최단 거리와 최소 시간 공통 로직
    private static List<String> commonPathLogic(Scanner scanner) {
        outputView.start();
        String start = inputView.inputStation(scanner);
        outputView.end();
        String end = inputView.inputStation(scanner);

        return List.of(start, end);
    }

    private static boolean checkDestination(String start, String end) {
        if (checkInvalidDestination(start, end)) {
            System.out.println(SAME_START_AND_END.getMessage());
            return true;
        }
        return false;
    }

    // 출발지와 도착지가 같은 경우
    private static boolean checkInvalidDestination(String start, String end) {
        if (start.equals(end)) {
            return true;
        }
        return false;
    }

    private static void init() {
        initStations();
        initLines();
        initConnections();
    }

    // 지하철 역 초기 설정
    private static void initStations() {
        List<Station> newStations = new ArrayList<Station>(List.of(
                new Station("교대역"), new Station("강남역"),
                new Station("역삼역"), new Station("남부터미널역"),
                new Station("양재역"), new Station("양재시민의숲역"),
                new Station("매봉역")
        ));
        for (Station newStation : newStations) {
            addStation(newStation);
        }
    }

    // 지하철 노선 초기 설정
    private static void initLines() {
        List<Line> newLines = new ArrayList<Line>(List.of(
                new Line("2호선"),
                new Line("3호선"),
                new Line("신분당선")
        ));
        for (Line newLine : newLines) {
            addLine(newLine);
        }
    }

    // 연결 정보 초기 설정
    private static void initConnections() {
        setStationVertex();
        setLineInfos();
        setDistInfos();
        setTimeInfos();
    }

    private static void setStationVertex() {
        for (Station station : stations()) {
            distGraph.addVertex(station.getName());
            timeGraph.addVertex(station.getName());
        }
    }

    private static void setLineInfos() {
        findStationByName("교대역").addLineInfo(findLineByName("2호선"));
        findStationByName("강남역").addLineInfo(findLineByName("2호선"));
        findStationByName("역삼역").addLineInfo(findLineByName("2호선"));
        findStationByName("교대역").addLineInfo(findLineByName("3호선"));
        findStationByName("남부터미널역").addLineInfo(findLineByName("3호선"));
        findStationByName("양재역").addLineInfo(findLineByName("3호선"));
        findStationByName("매봉역").addLineInfo(findLineByName("3호선"));
        findStationByName("강남역").addLineInfo(findLineByName("신분당선"));
        findStationByName("양재역").addLineInfo(findLineByName("신분당선"));
        findStationByName("양재시민의숲역").addLineInfo(findLineByName("신분당선"));
    }

    private static void setDistInfos() {
        distGraph.setEdgeWeight(distGraph.addEdge("교대역", "강남역"), 2);
        distGraph.setEdgeWeight(distGraph.addEdge("강남역", "역삼역"), 2);
        distGraph.setEdgeWeight(distGraph.addEdge("교대역", "남부터미널역"), 3);
        distGraph.setEdgeWeight(distGraph.addEdge("남부터미널역", "양재역"), 6);
        distGraph.setEdgeWeight(distGraph.addEdge("양재역", "매봉역"), 1);
        distGraph.setEdgeWeight(distGraph.addEdge("강남역", "양재역"), 2);
        distGraph.setEdgeWeight(distGraph.addEdge("양재역", "양재시민의숲역"), 10);
    }

    private static void setTimeInfos() {
        timeGraph.setEdgeWeight(timeGraph.addEdge("교대역", "강남역"), 3);
        timeGraph.setEdgeWeight(timeGraph.addEdge("강남역", "역삼역"), 3);
        timeGraph.setEdgeWeight(timeGraph.addEdge("교대역", "남부터미널역"), 2);
        timeGraph.setEdgeWeight(timeGraph.addEdge("남부터미널역", "양재역"), 5);
        timeGraph.setEdgeWeight(timeGraph.addEdge("양재역", "매봉역"), 1);
        timeGraph.setEdgeWeight(timeGraph.addEdge("강남역", "양재역"), 8);
        timeGraph.setEdgeWeight(timeGraph.addEdge("양재역", "양재시민의숲역"), 3);
    }

}
