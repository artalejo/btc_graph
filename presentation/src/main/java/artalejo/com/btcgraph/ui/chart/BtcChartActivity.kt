package artalejo.com.btcgraph.ui.chart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import artalejo.com.btc_graph.R
import artalejo.com.btcgraph.ui.base.BaseActivity

class BtcChartActivity: BaseActivity() {

    companion object {
        @JvmStatic fun getIntent(context: Context) = Intent(context, BtcChartActivity::class.java)
    }

    override var layout = R.layout.activity_btc_chart

    override fun onViewLoaded(savedInstanceState: Bundle?) {

    }

}