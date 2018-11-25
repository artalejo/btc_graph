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
import artalejo.com.btcgraph.ui.extensions.getTimestampTitle
import artalejo.com.btcgraph.ui.extensions.setGone
import artalejo.com.btcgraph.ui.extensions.setVisible
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.android.synthetic.main.activity_btc_chart.*
import kotlinx.android.synthetic.main.error_view_stub.*
import javax.inject.Inject

class BtcChartActivity: BaseActivity(), ChartTimestampWidget.OnChartTimeStampChangedListener {

    companion object {
        private const val CURRENT_BTN_SELECTED = "CURRENT_BTN_SELECTED"
        private const val TIMESTAMP_SELECTED = "TIMESTAMP_SELECTED"
        private const val CHART_ANIM_DURATION = 800
        @JvmStatic fun getIntent(context: Context) = Intent(context, BtcChartActivity::class.java)
    }

    @Inject lateinit var btcChartViewModel: BtcChartViewModel
    override var layout = R.layout.activity_btc_chart

    private lateinit var timestampSelected: String
    private val stateObserver = Observer<BtcChartModel>(function = handleStateChanges())

    override fun onViewLoaded(savedInstanceState: Bundle?) {
        checkSavedInstanceState(savedInstanceState)
        setUpViewModelStateObserver()
        setUpChartTimestampWidget()
        fetchBtcChartData(timestampSelected)
    }

    private fun checkSavedInstanceState(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            this.timestampSelected = it.getString(TIMESTAMP_SELECTED, getString(R.string.timestamp_year_value))
            timestamp_widget.selectSelectedButton(it.getInt(CURRENT_BTN_SELECTED, R.id.timestamp_year_btn))
        } ?: run {
            this.timestampSelected = getString(R.string.timestamp_year_value)
            timestamp_widget.selectSelectedButton(R.id.timestamp_year_btn)
        }
    }

    private fun setUpViewModelStateObserver() =
            btcChartViewModel.btcLiveData.observe(this, stateObserver)

    private fun setUpChartTimestampWidget() {
        timestamp_widget.setListener(this)
    }

    private fun fetchBtcChartData(timestamp: String) {
        btcChartViewModel.loadBtcChartData(timestamp)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putString(TIMESTAMP_SELECTED, timestampSelected)
        outState?.putInt(CURRENT_BTN_SELECTED, timestamp_widget.currentIdSelected)
    }

    private fun handleStateChanges(): (BtcChartModel?) -> Unit {
        return { state ->
            state?.let {
                when (state) {
                    is DataRetrievedState -> {
                        it.data?.let { btcData ->
                            populateBtcDataChart(btcData)
                            btc_chart.setVisible()
                            btc_data_progress_bar.setGone()
                            hideErrorView()
                        } ?: run { ErrorState(getString(R.string.retrieve_data_error)) }
                    }
                    is LoadingState -> {
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
    }

    private fun populateBtcDataChart(btcData: BtcChartViewEntity) {
        configureBtcChart(btcData)
        btc_chart.invalidate()
    }

    private fun configureBtcChart(btcData: BtcChartViewEntity) {
        val btcDataSet = LineDataSet(btcData.values, getTimestampTitle(timestampSelected))
        btcDataSet.setDrawCircles(false)
        btcDataSet.setDrawFilled(true)
        btcDataSet.color = R.color.colorPrimaryDark
        val lineData = LineData(btcDataSet)
        lineData.setDrawValues(false)
        btc_chart.data = lineData
        // Chart configuration
        btc_chart.description.text = btcData.description
        btc_chart.xAxis.isEnabled = false
        btc_chart.axisRight.isEnabled = false
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

        try_again_data_progress_bar.setGone()
        error_try_again?.setOnClickListener {
            try_again_data_progress_bar.setVisible()
            fetchBtcChartData(timestampSelected)
        }
    }

    private fun hideErrorView() {
        error_view_stub?.let {
            error_view_stub.setGone()
        } ?: run {
            findViewById<ViewStub>(R.id.error_stub_id).setGone()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        btcChartViewModel.btcLiveData.removeObserver(stateObserver)
    }
}