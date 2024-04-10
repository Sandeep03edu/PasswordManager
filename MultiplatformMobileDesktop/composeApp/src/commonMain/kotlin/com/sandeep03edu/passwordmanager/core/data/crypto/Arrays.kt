package com.sandeep03edu.passwordmanager.core.data.crypto



private inline fun _arraycmp(srcPos: Int, dstPos: Int, size: Int, cmp: (Int, Int) -> Int): Int {
    for (n in 0 until size) {
        cmp(srcPos + n, dstPos + n).also { if (it != 0) return it }
    }
    return 0
}
private inline fun _arrayequal(srcPos: Int, dstPos: Int, size: Int, cmp: (Int, Int) -> Boolean): Boolean {
    for (n in 0 until size) {
        if (!cmp(srcPos + n, dstPos + n)) {
            //println("Failed at $n : ${srcPos + n}, ${dstPos + n}")
            return false
        }
    }
    return true
}



/** Copies [size] elements of [src] starting at [srcPos] into [dst] at [dstPos]  */
public fun arraycopy(src: ByteArray, srcPos: Int, dst: ByteArray, dstPos: Int, size: Int) {
    src.copyInto(dst, dstPos, srcPos, srcPos + size)
}


public inline fun <T> arraycopy(size: Int, src: Any?, srcPos: Int, dst: Any?, dstPos: Int, setDst: (Int, T) -> Unit, getSrc: (Int) -> T) {
    val overlapping = src === dst && dstPos > srcPos
    if (overlapping) {
        var n = size
        while (--n >= 0) setDst(dstPos + n, getSrc(srcPos + n))
    } else {
        for (n in 0 until size) setDst(dstPos + n, getSrc(srcPos + n))
    }
}

