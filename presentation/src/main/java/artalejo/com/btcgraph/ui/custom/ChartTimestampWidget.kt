package artalejo.com.btcgraph.ui.custom

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import artalejo.com.btc_graph.R
import kotlinx.android.synthetic.main.custom_timestamp_widget.view.*

class ChartTimestampWidget : ConstraintLayout, ChartTimestampButton.OnTimeStampClickedListener {

    interface OnChartTimeStampChangedListener {
        fun onTimestampChanged(timestamp: String)
    }

    var currentIdSelected: Int = R.id.timestamp_year_btn
    private var listener : OnChartTimeStampChangedListener? = null

    constructor(context: Context): super(context) {
        initialize()
    }

    constructor(context: Context?, attrs: AttributeSet?): super(context, attrs) {
        initialize()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)  {
        initialize()
    }

    private fun initialize() {
        LayoutInflater
                .from(context)
                .inflate(R.layout.custom_timestamp_widget, this, true)
        setUpListeners()
    }

    private fun setUpListeners() {
        timestamp_week_btn.setListener(this)
        timestamp_two_weeks_btn.setListener(this)
        timestamp_month_btn.setListener(this)
        timestamp_two_months_btn.setListener(this)
        timestamp_six_months_btn.setListener(this)
        timestamp_year_btn.setListener(this)
        timestamp_two_years_btn.setListener(this)
        timestamp_all_time_btn.setListener(this)
    }

    fun setListener(onChartTimeStampChangedListener: OnChartTimeStampChangedListener) {
        this.listener = onChartTimeStampChangedListener
    }

    override fun onTimeStampClicked(viewClickedId: Int, timestamp: String) {
        deselectPreviousSelectedButton()
        this.currentIdSelected = viewClickedId
        this.listener?.onTimestampChanged(timestamp)
    }

    private fun deselectPreviousSelectedButton() {
        findViewById<ChartTimestampButton>(this.currentIdSelected).setDeselectedMode()
    }

    fun selectSelectedButton(selectedId: Int) {
        this.currentIdSelected = selectedId
        findViewById<ChartTimestampButton>(selectedId).setSelectedMode()
    }
}