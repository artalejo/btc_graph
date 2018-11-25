package artalejo.com.btcgraph.ui.entities

import artalejo.com.domain.entities.BtcDataInfo
import com.github.mikephil.charting.data.Entry

data class BtcChartViewEntity(
        val description: String,
        val values: List<Entry>)

fun BtcDataInfo.toBtcChartViewEntity() = BtcChartViewEntity(this.description,
        this.values.map { Entry(it.x.toFloat(), it.y.toFloat()) })