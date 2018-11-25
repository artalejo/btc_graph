package artalejo.com.btcgraph.conditionWatcher.instructions

import android.support.v7.app.AppCompatActivity
import artalejo.com.btcgraph.R
import artalejo.com.btcgraph.conditionWatcher.Instruction
import com.github.mikephil.charting.charts.LineChart

class FetchBtcChartDataInstruction(private val timestampSelected: String) : Instruction() {

    override val description: String = "Waiting for the btc chart to be populated by the btc data"

    override fun checkCondition(activity: AppCompatActivity): Boolean {
        val btcChart = activity.findViewById(R.id.btc_chart) as LineChart
        return btcChart.data != null && btcChart.data.entryCount > 0
                && btcChart.data.dataSetCount > 0
                && btcChart.data.dataSetLabels[0] == timestampSelected
    }
}