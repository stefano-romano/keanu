package com.research.pocketKeanu;

import com.research.pocketKeanu.abstractTypes.DoubleLike;
import com.research.pocketKeanu.randomFactory.ContinuousRandomFactory;

import java.util.List;


public interface ContinuousModel {
    <T extends DoubleLike<T>> List<T> apply(T[] doubleParams, ContinuousRandomFactory<T> rand);
}
