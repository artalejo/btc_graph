package artalejo.com.btcgraph.ui.chart

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ViewStub
import artalejo.com.btc_graph.R
import artalejo.com.btcgraph.ui.base.BaseActivity
import artalejo.com.btcgraph.ui.custom.ChartTimestampWidget
import artalejo.com.btcgraph.ui.entities.BtcChartViewEntity
import artalejo.com.btcgraph.ui.extensions.setGone
import artalejo.com.btcgraph.ui.extensions.setVisible
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.android.synthetic.main.activity_btc_chart.*
import kotlinx.android.synthetic.main.error_view_stub.*
import javax.inject.Inject

class BtcChartActivity: BaseActivity(), ChartTimestampWidget.OnChartTimeStampChangedListener {

    companion object {
        private const val CHART_ANIM_DURATION = 800
        @JvmStatic fun getIntent(context: Context) = Intent(context, BtcChartActivity::class.java)
    }

    @Inject lateinit var btcChartViewModel: BtcChartViewModel
    override var layout = R.layout.activity_btc_chart

    private lateinit var timestampSelected : String

    override fun onViewLoaded(savedInstanceState: Bundle?) {
        setUpViewModelStateObserver()
        setUpChartTimestampWidget()
        fetchBtcChartData(timestampSelected)
    }

    private fun setUpChartTimestampWidget() {
        this.timestampSelected = getString(R.string.timestamp_year_value)
        timestamp_widget.setListener(this)
    }

    private fun fetchBtcChartData(timestamp: String) {
        btcChartViewModel.loadBtcChartData(timestamp)
    }

    private fun setUpViewModelStateObserver()=
            btcChartViewModel.btcLiveData.observe(this, stateObserver)

    private val stateObserver = Observer<BtcChartModel> { state ->
        state?.let {
            when (state) {
                is DataRetrievedState -> {
                    it.data?.let {
                        btcData -> populateBtcDataChart(btcData)
                        btc_chart.setVisible()
                        btc_data_progress_bar.setGone()
                    } ?: run { ErrorState(getString(R.string.retrieve_data_error)) }
                }
                is LoadingState -> {
                    hideErrorView()
                    btc_chart.setGone()
                    btc_data_progress_bar.setVisible()
                }
                is ErrorState -> {
                    setUpErrorViewStub()
                    btc_chart.setVisible()
                    btc_data_progress_bar.setGone()
                }
            }
        }
    }

    private fun populateBtcDataChart(btcData: BtcChartViewEntity) {
        val entries = arrayListOf<Entry>()
        btcData.values.forEach { entries.add(Entry(it.x.toFloat(), it.y.toFloat())) }
        configureBtcChart(entries, btcData.description)
        btc_chart.invalidate()
    }

    private fun configureBtcChart(entries : ArrayList<Entry>, description: String) {
        val btcDataSet = LineDataSet(entries, description)
        btcDataSet.setDrawCircles(false)
        btcDataSet.setDrawFilled(true)
        val lineData = LineData(btcDataSet)
        lineData.setDrawValues(false)
        btc_chart.data = lineData
        btc_chart.xAxis.isEnabled = false
        btc_chart.axisRight.isEnabled = false
        btc_chart.setBorderColor(R.color.colorAccent)
        btc_chart.description.isEnabled = false
        btc_chart.setScaleEnabled(false)
        btc_chart.setDrawGridBackground(false)
        btc_chart.isDragEnabled = false
        btc_chart.animateX(CHART_ANIM_DURATION)
    }

    override fun onTimestampChanged(timestamp: String) {
        this.timestampSelected = timestamp
        fetchBtcChartData(timestamp)
    }

    private fun setUpErrorViewStub() {
        error_view_stub?.let { it.inflate() } ?: run {
            findViewById<ViewStub>(R.id.error_stub_id).setVisible()
        }
        error_try_again?.setOnClickListener { fetchBtcChartData(timestampSelected) }
    }

    private fun hideErrorView() {
        // The view stub is null when it has already been inflated
        if (error_view_stub == null)  findViewById<ViewStub>(R.id.error_stub_id).setGone()
    }

    override fun onDestroy() {
        super.onDestroy()
        btcChartViewModel.btcLiveData.removeObserver(stateObserver)
    }
}