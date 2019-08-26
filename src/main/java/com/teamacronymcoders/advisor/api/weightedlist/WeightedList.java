package com.teamacronymcoders.advisor.api.weightedlist;

import com.google.common.collect.Lists;
import net.minecraft.entity.player.PlayerEntity;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.util.*;

public class WeightedList<T, U extends IWeightedItem<T>> {
    private final NavigableMap<Double, U> map;
    private final NavigableMap<Double, U> conditionals;
    private final Random random;
    private double totalWeight;
    private double unConditionalWeight;

    public WeightedList() {
        this(new Random());
    }

    public WeightedList(Random random) {
        this.random = random;
        this.map = new TreeMap<>();
        this.conditionals = new TreeMap<>();
    }

    public void add(U weightedItem) {
        totalWeight += weightedItem.getWeight();
        if (!weightedItem.isConditional()) {
            map.put(weightedItem.getWeight(), weightedItem);
            unConditionalWeight += weightedItem.getWeight();
        } else {
            conditionals.put(weightedItem.getWeight(), weightedItem);
        }
    }

    @Nullable
    public T nextUnchecked() {
        double value = random.nextDouble() * totalWeight;
        Map.Entry<Double, U> entry;
        if (value < unConditionalWeight) {
            entry = map.higherEntry(value);
        } else {
            entry = conditionals.higherEntry(value - unConditionalWeight);
        }
        return entry != null ? entry.getValue().getItem() : null;
    }

    @Nullable
    public T next(PlayerEntity playerEntity) {
        if (!conditionals.isEmpty()) {
            List<Pair<Double, T>> achieved = Lists.newArrayList();
            double extraWeight = 0;
            for (U value : conditionals.values()) {
                if (value.isMet(playerEntity)) {
                    achieved.add(Pair.of(value.getWeight(), value.getItem()));
                    extraWeight += value.getWeight();
                }
            }
            double totalCheckedWeight = unConditionalWeight + extraWeight;
            double randomCheck = totalCheckedWeight * random.nextDouble();
            T nextValue;
            if (randomCheck > unConditionalWeight) {
                double currentWeight = unConditionalWeight;
                T currentValue = null;
                Iterator<Pair<Double, T>> achieverIterator = achieved.iterator();
                while (achieverIterator.hasNext() && currentWeight < randomCheck) {
                    Pair<Double, T> currentCheck = achieverIterator.next();
                    currentWeight += currentCheck.getLeft();
                    currentValue = currentCheck.getRight();
                }
                nextValue = currentValue;
            } else {
                Map.Entry<Double, U> value = map.higherEntry(randomCheck);
                nextValue = value != null ? value.getValue().getItem() : null;
            }
            return nextValue;
        } else {
            return nextUnchecked();
        }
    }

    public void clear() {
        map.clear();
    }
}
