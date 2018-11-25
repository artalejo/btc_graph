package artalejo.com.btcgraph.ui.splash

import android.os.Bundle
import artalejo.com.btcgraph.R
import artalejo.com.btcgraph.ui.base.BaseActivity

class SplashActivity: BaseActivity() {

    override var layout = R.layout.activity_splash

    override fun onViewLoaded(savedInstanceState: Bundle?) {
        navigator.navigateToBtcChartActivity(this)
    }
}