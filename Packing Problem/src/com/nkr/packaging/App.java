package com.nkr.packaging;

import java.util.Arrays;
import java.util.List;

public class App {

    public static void main(String[] args) {

        List<Integer> items = Arrays.asList(10, 5, 5);
        int binCapacity = 10;

        FirstFitDecreasingAlg alg = new FirstFitDecreasingAlg(items, binCapacity);
        alg.solvePackingProblem();
        alg.showResults();
    }
}
