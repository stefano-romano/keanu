package com.research.pocketKeanu.randomFactory;

import com.research.pocketKeanu.abstractTypes.IntLike;

public interface IntLikeRandomFactory<T extends IntLike<T>> {
    T nextInt();
}
