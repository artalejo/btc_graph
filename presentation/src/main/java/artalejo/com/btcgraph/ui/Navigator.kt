package artalejo.com.btcgraph.ui

import android.content.Context
import artalejo.com.btcgraph.ui.chart.BtcChartActivity
import javax.inject.Inject

class Navigator @Inject constructor() {
    fun navigateToBtcChartActivity(context: Context) =
            context.startActivity(BtcChartActivity.getIntent(context))
}