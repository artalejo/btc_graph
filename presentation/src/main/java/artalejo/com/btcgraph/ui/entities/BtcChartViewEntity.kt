package artalejo.com.btcgraph.ui.entities

import artalejo.com.domain.entities.BtcDataInfo

data class BtcChartViewEntity(
        val status: String,
        val name: String,
        val unit: String,
        val period: String,
        val description: String,
        val values: List<BtcValueViewEntity>)

fun BtcDataInfo.toBtcChartViewEntity() = BtcChartViewEntity(this.status, this.name, this.unit,
        this.period, this.description, this.values.map { it.toBtcChartValueViewEntity() })