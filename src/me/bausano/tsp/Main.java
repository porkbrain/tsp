package me.bausano.tsp;

import me.bausano.tsp.Enum.Algorithm;
import me.bausano.tsp.IO.Eloquent;
import me.bausano.tsp.IO.InputParser.InputParser;
import me.bausano.tsp.IO.InputParser.PointDistanceParser;
import me.bausano.tsp.IO.Referee;
import me.bausano.tsp.ProblemSolver.BruteForceSolver.BruteForceSolver;
import me.bausano.tsp.ProblemSolver.ProblemSolver;

public class Main {

    public static void main(String[] args) {
        System.out.println("==== Travelling Salesman Problem ====");
        System.out.println("==== by Michael Bausano");
        System.out.println("==== In order to select an algorithm, write it's name in the cmd line.");
        System.out.println("==== Options:");
        System.out.println("==== BRUTE_FORCE");
        System.out.println("==== === === === === === === === ====");
        System.out.println("Afterwards, input the source of you file relative to current working directory.");

        InputParser parser = new PointDistanceParser();
        Eloquent eloquent = new Eloquent(parser);

        try {
            eloquent.requestAlgorithm();

            eloquent.requestData();
        } catch (Exception e) {
            System.out.println(e.getMessage());

            System.exit(1);
        }

        Referee referee = new Referee();
        Algorithm algorithm = eloquent.getAlgorithm();
        int[][] matrix = eloquent.getMap();
        ProblemSolver solver = matchSolver(algorithm);

        referee.start();
        solver.findShortestPath(matrix);
        referee.stop();

        System.out.printf("Problem shortest path %f has been found in %dms.",
                referee.getPath(), referee.getTime());
    }

    private static ProblemSolver matchSolver(Algorithm algorithm) {
        switch (algorithm) {
            case BRUTE_FORCE:
                return new BruteForceSolver();
        }

        return null;
    }
}