package artalejo.com.btcgraph.ui.entities

import artalejo.com.domain.entities.BtcValueInfo

data class BtcValueViewEntity(val x: Long, val y: Double)

fun BtcValueInfo.toBtcChartValueViewEntity() = BtcValueViewEntity(x, y)