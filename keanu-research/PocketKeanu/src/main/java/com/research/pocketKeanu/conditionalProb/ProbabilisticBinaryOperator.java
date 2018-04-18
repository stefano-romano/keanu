package com.research.pocketKeanu.conditionalProb;

import java.util.function.BinaryOperator;

public interface ProbabilisticBinaryOperator<T, R> extends BinaryOperator<T> {
    R logProb(T randVal, T param1, T param2);
}
