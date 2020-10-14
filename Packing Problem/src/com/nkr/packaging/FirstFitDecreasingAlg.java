package com.nkr.packaging;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FirstFitDecreasingAlg {

    private List<Bin> bins = new ArrayList<Bin>();
    private List<Integer> items;
    private int binCapacity;
    private int binCounter = 1;

    public FirstFitDecreasingAlg(List<Integer> items, int binCapacity)
    {
        this.items = items;
        this.binCapacity = binCapacity;
        this.bins = new ArrayList<>(this.items.size()); //don't create 'infinite' capacity
    }

    public void solvePackingProblem()
    {
        //start with the biggest element
        Collections.sort(items, Collections.reverseOrder());

        if (this.items.get(0) > this.binCapacity)
        {
            System.out.println("No feasible solution...");
            return;
        }

        this.bins.add(new Bin (binCapacity, binCounter));

        for (Integer currentItem : items)
        {
            //track whether we have put the item into a bin or not (== flag)
            boolean putItem = false;
            int currentBin = 0;

            while (!putItem)
            {
                if (currentBin == this.bins.size())
                {
                    //item did not fit in the last bin -> try a new bin
                    Bin newBin = new Bin(binCapacity, ++binCounter);
                    newBin.put(currentItem);
                    this.bins.add(newBin);
                    putItem = true;
                }
                else if (this.bins.get(currentBin).put(currentItem))
                {
                    //current item fits in the given bin
                    putItem = true;
                }
                else
                {
                    //trying the next bin
                    currentBin++;
                }
            }
        }
    }

    /**
     * Print the result
     */
    public void showResults()
    {
        for (Bin bin : this.bins)
        {
            System.out.println(bin);
        }
    }
}
