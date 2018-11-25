package artalejo.com.btcgraph.ui.custom

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import artalejo.com.btc_graph.R
import kotlinx.android.synthetic.main.custom_chart_timestamp_button.view.*

class ChartTimestampButton : ConstraintLayout, View.OnClickListener{

    interface OnTimeStampClickedListener {
        fun onTimeStampClicked(viewClickedId: Int, timestamp: String)
    }

    private var attrs: AttributeSet? = null
    private var defStyleAttr: Int = 0
    private val defaultTitle = context.getString(R.string.timestamp_year_title)
    private val defaultValue = context.getString(R.string.timestamp_year_value)
    private lateinit var title: String
    private var listener : OnTimeStampClickedListener? = null
    private var isBtnSelected: Boolean = false
    lateinit var value: String

    constructor(context: Context): super(context) {
        initialize()
    }

    constructor(context: Context?, attrs: AttributeSet?): super(context, attrs) {
        this.attrs = attrs
        initialize()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)  {
        this.attrs = attrs
        this.defStyleAttr = defStyleAttr
        initialize()
    }

    private fun initialize() {
        LayoutInflater
                .from(context)
                .inflate(R.layout.custom_chart_timestamp_button, this, true)

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ChartTimestampButton, defStyleAttr, 0)
            title = typedArray.getString(R.styleable.ChartTimestampButton_title) ?: defaultTitle
            value = typedArray.getString(R.styleable.ChartTimestampButton_value) ?: defaultValue
            isBtnSelected = typedArray.getBoolean(R.styleable.ChartTimestampButton_selected, false)
            setupInitialValues()
        }
        setOnClickListener(this)
    }

    fun setListener(onTimeStampClickedListener: OnTimeStampClickedListener) {
        this.listener = onTimeStampClickedListener
    }

    private fun setupInitialValues() {
        timestamp.text = title
        if (isBtnSelected) setSelectedMode() else setDeselectedMode()
    }

    fun setSelectedMode() {
        chart_time_container.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent))
        timestamp.setTextColor(ContextCompat.getColor(context, android.R.color.white))
        this.isBtnSelected = true
    }

    fun setDeselectedMode() {
        chart_time_container.setBackgroundColor(ContextCompat.getColor(context, android.R.color.white))
        timestamp.setTextColor(ContextCompat.getColor(context, R.color.colorAccent))
        this.isBtnSelected = false
    }

    override fun onClick(v: View?) {
        if (!isBtnSelected) {
            setSelectedMode()
            v?.let { view ->  this.listener?.onTimeStampClicked(view.id, value)}
        }
    }
}