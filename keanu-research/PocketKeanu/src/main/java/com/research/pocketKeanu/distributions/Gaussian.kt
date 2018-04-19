package com.research.pocketKeanu.distributions

import com.research.pocketKeanu.abstractTypes.DoubleLike
import com.research.pocketKeanu.abstractTypes.log

class Gaussian {
    companion object {
        fun <T : DoubleLike<T>> logProb(mu: T, sigma: T, x: T): T {
            val e = ((x-mu)/sigma)
            return e*e - log(sigma)
        }
    }
}
