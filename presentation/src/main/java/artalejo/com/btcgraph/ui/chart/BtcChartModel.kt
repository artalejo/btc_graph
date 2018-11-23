package artalejo.com.btcgraph.ui.chart

import artalejo.com.btcgraph.ui.entities.BtcChartViewEntity

sealed class BtcChartModel {
    abstract val data: BtcChartViewEntity
}

data class DefaultState(override val data: BtcChartViewEntity) : BtcChartModel()
data class LoadingState(override val data: BtcChartViewEntity) : BtcChartModel()
data class ErrorState(val errorMessage: String, override val data: BtcChartViewEntity) : BtcChartModel()