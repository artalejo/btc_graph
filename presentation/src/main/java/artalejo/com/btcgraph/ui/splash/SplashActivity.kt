package artalejo.com.btcgraph.ui.splash

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import artalejo.com.btc_graph.R
import artalejo.com.btcgraph.ui.Navigator
import artalejo.com.btcgraph.ui.base.BaseActivity
import javax.inject.Inject

class SplashActivity: BaseActivity() {

    override var layout = R.layout.activity_splash

    override fun onViewLoaded(savedInstanceState: Bundle?) {
        navigator.navigateToBtcChartActivity(this)
    }
}