package com.research.pocketKeanu.randomFactory

import com.research.pocketKeanu.abstractTypes.DoubleLike
import com.research.pocketKeanu.abstractTypes.IntLike
import com.research.pocketKeanu.trace.Trace

class Recorder<D : DoubleLike<D>,I : IntLike<I>>(val factory : RandomFactory<D, I>) : RandomFactory<D,I> {

    val trace = Trace<D,I>()
    var isRecording = true

    fun record() {
        trace.clear()
        isRecording = true
    }

    fun replay() {
        trace.rewind()
        isRecording = false
    }

    override fun nextGaussian(): D {
        if(!isRecording) trace.nextDouble()
        val r = factory.nextGaussian()
        trace.add(r)
        return r
    }

    override fun getLogProb(): D {
        return factory.logProb
    }

    override fun setLogProb(value: Double) {
        factory.setLogProb(value)
    }

    override fun nextConstant(value: Double): D {
        if(!isRecording) trace.nextDouble()
        val r = factory.nextConstant(value)
        trace.add(r)
        return r
    }

    override fun nextInt(): I {
        if(!isRecording) return trace.nextInt()
        val r = factory.nextInt();
        trace.add(r)
        return r
    }

}
