package graphs.shortestpaths;

import graphs.BaseEdge;
import graphs.Graph;
import priorityqueues.DoubleMapMinPQ;
import priorityqueues.ExtrinsicMinPQ;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.HashSet;
import java.util.Stack;

/**
 * Computes shortest paths using Dijkstra's algorithm.
 * @see SPTShortestPathFinder for more documentation.
 */
public class DijkstraShortestPathFinder<G extends Graph<V, E>, V, E extends BaseEdge<V, E>>
    extends SPTShortestPathFinder<G, V, E> {

    protected <T> ExtrinsicMinPQ<T> createMinPQ() {
        return new DoubleMapMinPQ<>();
        /*
        If you have confidence in your heap implementation, you can disable the line above
        and enable the one below.
         */
        // return new ArrayHeapMinPQ<>();

        /*
        Otherwise, do not change this method.
        We override this during grading to test your code using our correct implementation so that
        you don't lose extra points if your implementation is buggy.
         */
    }

    @Override
    protected Map<V, E> constructShortestPathsTree(G graph, V start, V end) {
        HashMap<V, E> spt = new HashMap<>();
        Set<V> knownSet = new HashSet<>();
        HashMap<V, V> edgeTo = new HashMap<>();
        HashMap<V, Double> distTo = new HashMap<>();
        V currentVertex = start;
        DoubleMapMinPQ<V> nextVertex = new DoubleMapMinPQ<>();
        if (Objects.equals(start, end)) {
            return spt;
        }
        edgeTo.put(start, start);
        distTo.put(start, 0.0);
        while (!knownSet.contains(end)) {
            knownSet.add(currentVertex);
            for (E edge : graph.outgoingEdgesFrom(currentVertex)) {
                if (!edgeTo.containsKey(edge.to()) && !knownSet.contains(edge.to())) {
                    edgeTo.put(edge.to(), edge.from());
                    distTo.put(edge.to(), distTo.get(edge.from()) + edge.weight());
                    nextVertex.add(edge.to(), distTo.get(edge.from()) + edge.weight());
                    spt.put(edge.to(), edge);
                } else {
                    double oldDist = distTo.get(edge.to());
                    double newDist = distTo.get(edge.from()) + edge.weight();
                    if (newDist < oldDist && !knownSet.contains(edge.to())) {
                        distTo.put(edge.to(), newDist);
                        edgeTo.put(edge.to(), edge.from());
                        nextVertex.changePriority(edge.to(), newDist);
                        spt.replace(edge.to(), edge);
                    }
                }
            }
            if (nextVertex.isEmpty()) {
                break;
            } else {
                currentVertex = nextVertex.removeMin();
            }
        }
        return spt;
    }

    @Override
    protected ShortestPath<V, E> extractShortestPath(Map<V, E> spt, V start, V end) {
        if (Objects.equals(start, end)) {
            return new ShortestPath.SingleVertex<>(start);
        }
        E edge = spt.get(end);
        if (edge == null) {
            return new ShortestPath.Failure<>();
        }
        Stack<E> shortestPath = new Stack<>();
        ArrayList<E> shortestPathFinal = new ArrayList<>();
        shortestPath.push(edge);
        while (!Objects.equals(edge.from(), start)) {
            edge = spt.get(edge.from());
            shortestPath.add(edge);
        }
        while (!shortestPath.isEmpty()) {
            shortestPathFinal.add(shortestPath.pop());
        }
        return new ShortestPath.Success<>(shortestPathFinal);
    }

}
