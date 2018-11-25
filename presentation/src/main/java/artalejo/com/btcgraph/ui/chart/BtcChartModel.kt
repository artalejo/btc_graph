package artalejo.com.btcgraph.ui.chart

import artalejo.com.btcgraph.ui.entities.BtcChartViewEntity

sealed class BtcChartModel {
    abstract val data: BtcChartViewEntity?
}

data class DataRetrievedState(override val data: BtcChartViewEntity?) : BtcChartModel()
data class LoadingState(override val data: BtcChartViewEntity? = null) : BtcChartModel()
data class ErrorState(val errorMessage: String? = null, override val data: BtcChartViewEntity? = null) : BtcChartModel()
