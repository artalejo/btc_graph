package artalejo.com.btcgraph.ui.extensions

import android.content.Context
import android.view.View
import artalejo.com.btc_graph.R

/**
 * Sets a view visibility to [View.VISIBLE]
 */
fun View.setVisible() {
    this.visibility = View.VISIBLE
}

/**
 * Sets a view visibility to [View.INVISIBLE]
 */

fun View.setInvisible() {
    if (this.visibility == View.VISIBLE) this.visibility = View.INVISIBLE
}

/**
 * Sets a view visibility to [View.GONE]
 */
fun View.setGone() {
    if (this.visibility == View.VISIBLE) this.visibility = View.GONE
}

fun Context.getTimestampTitle(timestampValue :String) : String {
    return when(timestampValue) {
        getString(R.string.timestamp_all_time_value)  -> getString(R.string.timestamp_all_time_title)
        getString(R.string.timestamp_two_years_value) -> getString(R.string.timestamp_two_years_title)
        getString(R.string.timestamp_year_value) -> getString(R.string.timestamp_year_title)
        getString(R.string.timestamp_six_months_value) -> getString(R.string.timestamp_six_months_title)
        getString(R.string.timestamp_two_months_value) -> getString(R.string.timestamp_two_months_title)
        getString(R.string.timestamp_month_value) -> getString(R.string.timestamp_month_title)
        getString(R.string.timestamp_two_weeks_value) -> getString(R.string.timestamp_two_weeks_title)
        getString(R.string.timestamp_week_value) -> getString(R.string.timestamp_week_title)
        else -> { getString(R.string.timestamp_year_title) }
    }
}