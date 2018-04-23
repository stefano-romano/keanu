package com.research.pocketKeanu.randomFactory

import com.research.pocketKeanu.abstractTypes.DoubleLike
import com.research.pocketKeanu.abstractTypes.IntLike

interface RandomFactory<D : DoubleLike<D>, I : IntLike<I>> : ContinuousRandomFactory<D>, DiscreteRandomFactory<I>
