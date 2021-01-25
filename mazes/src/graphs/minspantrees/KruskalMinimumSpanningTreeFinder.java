package graphs.minspantrees;

import disjointsets.DisjointSets;
import disjointsets.QuickFindDisjointSets;
import graphs.BaseEdge;
import graphs.KruskalGraph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Computes minimum spanning trees using Kruskal's algorithm.
 * @see MinimumSpanningTreeFinder for more documentation.
 */
public class KruskalMinimumSpanningTreeFinder<G extends KruskalGraph<V, E>, V, E extends BaseEdge<V, E>>
    implements MinimumSpanningTreeFinder<G, V, E> {

    protected DisjointSets<V> createDisjointSets() {
        return new QuickFindDisjointSets<>();
    }

    @Override
    public MinimumSpanningTree<V, E> findMinimumSpanningTree(G graph) {
        List<E> edges = new ArrayList<>(graph.allEdges());
        edges.sort(Comparator.comparingDouble(E::weight));

        DisjointSets<V> disjointSets = createDisjointSets();
        ArrayList<E> finalMST = new ArrayList<>();
        int uMST;
        int vMST;
        for (V vertex : graph.allVertices()) {
            disjointSets.makeSet(vertex);
        }
        if (edges.size() == 0) {
            if (graph.allVertices().size() > 1) {
                return new MinimumSpanningTree.Failure<>();
            } else {
                return new MinimumSpanningTree.Success<>();
            }
        }
        for (E edge : edges) {
            uMST = disjointSets.findSet(edge.from());
            vMST = disjointSets.findSet(edge.to());
            if (uMST != vMST) {
                finalMST.add(edge);
                disjointSets.union(edge.from(), edge.to());
            }
        }
        if (graph.allVertices().size() != finalMST.size() + 1) {
            return new MinimumSpanningTree.Failure<>();
        }
        return new MinimumSpanningTree.Success<>(finalMST);
    }
}
