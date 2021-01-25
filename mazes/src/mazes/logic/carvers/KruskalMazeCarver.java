package mazes.logic.carvers;

import graphs.EdgeWithData;
import graphs.minspantrees.MinimumSpanningTreeFinder;
import mazes.entities.Room;
import mazes.entities.Wall;
import mazes.logic.MazeGraph;

import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

/**
 * Carves out a maze based on Kruskal's algorithm.
 */
public class KruskalMazeCarver extends MazeCarver {
    MinimumSpanningTreeFinder<MazeGraph, Room, EdgeWithData<Room, Wall>> minimumSpanningTreeFinder;
    private final Random rand;

    public KruskalMazeCarver(MinimumSpanningTreeFinder
                                 <MazeGraph, Room, EdgeWithData<Room, Wall>> minimumSpanningTreeFinder) {
        this.minimumSpanningTreeFinder = minimumSpanningTreeFinder;
        this.rand = new Random();
    }

    public KruskalMazeCarver(MinimumSpanningTreeFinder
                                 <MazeGraph, Room, EdgeWithData<Room, Wall>> minimumSpanningTreeFinder,
                             long seed) {
        this.minimumSpanningTreeFinder = minimumSpanningTreeFinder;
        this.rand = new Random(seed);
    }

    @Override
    protected Set<Wall> chooseWallsToRemove(Set<Wall> walls) {
        List<EdgeWithData<Room, Wall>> randomWeights = new ArrayList<>();
        for (Wall set : walls) {
            randomWeights.add(new EdgeWithData<>(set.getRoom1(), set.getRoom2(), this.rand.nextDouble(), set));
        }
        Collection<EdgeWithData<Room, Wall>> mstEdges = this.minimumSpanningTreeFinder.
            findMinimumSpanningTree(new MazeGraph(randomWeights)).edges();
        Set<Wall> wallsToBeRemoved = new HashSet<>();
        for (EdgeWithData<Room, Wall> wl : mstEdges) {
            wallsToBeRemoved.add(wl.data());
            wl.weight();
        }
        return wallsToBeRemoved;
    }
}
