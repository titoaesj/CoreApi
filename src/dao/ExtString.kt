package dao

import java.math.BigInteger
import java.nio.ByteBuffer
import java.util.*

fun generatieUniqueId(): String {
    var uniqueId = 0L
    do {
        val uid = UUID.randomUUID()
        val buffer = ByteBuffer.wrap(ByteArray(16))
        buffer.putLong(uid.leastSignificantBits)
        buffer.putLong(uid.mostSignificantBits)
        val bi = BigInteger(buffer.array())
        uniqueId = bi.toLong()
    } while (uniqueId < 0)
    return uniqueId.toString()
}