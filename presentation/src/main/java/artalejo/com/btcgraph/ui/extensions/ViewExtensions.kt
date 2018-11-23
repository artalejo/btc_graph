package artalejo.com.btcgraph.ui.extensions

import android.view.View

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
    this.visibility = View.INVISIBLE
}

/**
 * Sets a view visibility to [View.GONE]
 */
fun View.setGone() {
    this.visibility = View.GONE
}