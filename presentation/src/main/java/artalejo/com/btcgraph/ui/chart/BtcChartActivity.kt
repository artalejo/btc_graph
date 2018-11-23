package artalejo.com.btcgraph.ui.chart

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import artalejo.com.btc_graph.R
import artalejo.com.btcgraph.ui.base.BaseActivity
import javax.inject.Inject

class BtcChartActivity: BaseActivity() {

    companion object {
        @JvmStatic fun getIntent(context: Context) = Intent(context, BtcChartActivity::class.java)
    }

    @Inject lateinit var btcChartViewModel: BtcChartViewModel
    override var layout = R.layout.activity_btc_chart

    override fun onViewLoaded(savedInstanceState: Bundle?) {
        setUpViewModelStateObserver()
        // TODO sartalejo: pass the parameters properly
        btcChartViewModel.loadBtcChartData("", "")
    }

    private fun setUpViewModelStateObserver()=
            btcChartViewModel.btcLiveData.observe(this, stateObserver)

    private val stateObserver = Observer<BtcChartModel> { state ->
        state?.let {
            when (state) {
                is DataRetrievedState -> {
//
                }
                is LoadingState -> {
//
                }
                is ErrorState -> {
//
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        btcChartViewModel.btcLiveData.removeObserver(stateObserver)
    }
}