package com.research.pocketKeanu.randomFactory;

import com.research.pocketKeanu.abstractTypes.DoubleLike;
import com.research.pocketKeanu.abstractTypes.IntLike;

public interface RandomFactory<D extends DoubleLike<D>, I extends IntLike<I>> extends DoubleLikeRandomFactory<D>, IntLikeRandomFactory<I> {
}
