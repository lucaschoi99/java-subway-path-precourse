package subway.service;

import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import subway.domain.Station;

import java.util.List;

public class LookUpLogic {

    private Station start;
    private Station end;
    WeightedMultigraph<String, DefaultWeightedEdge> graph;

    public LookUpLogic(Station start, Station end, WeightedMultigraph<String, DefaultWeightedEdge> graph) {
        this.start = start;
        this.end = end;
        this.graph = graph;
    }

    // 최단 거리, 최소 시간 로직
    public List<String> shortestPathList() {
        DijkstraShortestPath dijkstraShortestPath = new DijkstraShortestPath(graph);
        return dijkstraShortestPath.getPath(start.getName(), end.getName()).getVertexList();
    }

    // 총 거리, 총 소요시간 계산 로직
    public int computeWeight(List<String> result, WeightedMultigraph<String, DefaultWeightedEdge> graph) {
        double sum = 0;
        for (int i = 0; i < result.size() - 1; i++) {
            DefaultWeightedEdge edge = graph.getEdge(result.get(i), result.get(i + 1));
            double edgeWeight = graph.getEdgeWeight(edge);
            sum += edgeWeight;
        }
        return (int) sum;
    }

}
