package artalejo.com.btcgraph.ui.chart

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ViewStub
import artalejo.com.btcgraph.R
import artalejo.com.btcgraph.ui.base.BaseActivity
import artalejo.com.btcgraph.ui.custom.ChartTimespanWidget
import artalejo.com.btcgraph.ui.entities.BtcChartViewEntity
import artalejo.com.btcgraph.ui.extensions.getTimespanTitle
import artalejo.com.btcgraph.ui.extensions.setGone
import artalejo.com.btcgraph.ui.extensions.setVisible
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.android.synthetic.main.activity_btc_chart.*
import kotlinx.android.synthetic.main.error_view_stub.*
import javax.inject.Inject

class BtcChartActivity: BaseActivity(), ChartTimespanWidget.OnChartTimeStampChangedListener {

    companion object {
        private const val CURRENT_BTN_SELECTED = "CURRENT_BTN_SELECTED"
        private const val TIMESPAN_SELECTED = "TIMESPAN_SELECTED"
        private const val CHART_ANIM_DURATION = 800
        @JvmStatic fun getIntent(context: Context) = Intent(context, BtcChartActivity::class.java)
    }

    @Inject lateinit var btcChartViewModel: BtcChartViewModel
    private lateinit var timespanSelected: String
    private val stateObserver = Observer<BtcChartModel>(function = handleStateChanges())
    override var layout = R.layout.activity_btc_chart

    override fun onViewLoaded(savedInstanceState: Bundle?) {
        checkSavedInstanceState(savedInstanceState)
        setUpViewModelStateObserver()
        setUpChartTimestampWidget()
        fetchBtcChartData(timespanSelected)
    }

    private fun checkSavedInstanceState(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            this.timespanSelected = it.getString(TIMESPAN_SELECTED, getString(R.string.timestamp_year_value))
            timespan_widget.selectSelectedButton(it.getInt(CURRENT_BTN_SELECTED, R.id.timestamp_year_btn))
        } ?: run {
            this.timespanSelected = getString(R.string.timestamp_year_value)
            timespan_widget.selectSelectedButton(R.id.timestamp_year_btn)
        }
    }

    private fun setUpViewModelStateObserver() =
            btcChartViewModel.btcLiveData.observe(this, stateObserver)

    private fun setUpChartTimestampWidget() {
        timespan_widget.setListener(this)
    }

    private fun fetchBtcChartData(timespan: String) {
        btcChartViewModel.loadBtcChartData(timespan)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putString(TIMESPAN_SELECTED, timespanSelected)
        outState?.putInt(CURRENT_BTN_SELECTED, timespan_widget.currentIdSelected)
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
        val btcDataSet = LineDataSet(btcData.values, getTimespanTitle(timespanSelected))
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

    override fun onTimestampChanged(timespan: String) {
        this.timespanSelected = timespan
        fetchBtcChartData(timespan)
    }

    private fun setUpErrorViewStub() {
        error_view_stub?.inflate() ?: run {
            findViewById<ViewStub>(R.id.error_stub_id).setVisible()
        }

        try_again_data_progress_bar.setGone()
        error_try_again?.setOnClickListener {
            try_again_data_progress_bar.setVisible()
            fetchBtcChartData(timespanSelected)
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