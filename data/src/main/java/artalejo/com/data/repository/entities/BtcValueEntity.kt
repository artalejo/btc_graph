package artalejo.com.data.repository.entities

import artalejo.com.domain.entities.BtcValueInfo

data class BtcValueEntity(var x: Long, var y: Double)

fun BtcValueEntity.toBtcValueInfo() = BtcValueInfo(x, y)