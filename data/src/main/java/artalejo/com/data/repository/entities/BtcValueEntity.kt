package artalejo.com.data.repository.entities

import artalejo.com.domain.entities.BtcValueInfo

data class BtcValueEntity(val x: Long, val y: Double)

fun BtcValueEntity.toBtcValueInfo() = BtcValueInfo(x, y)