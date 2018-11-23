package artalejo.com.btcgraph.ui.base

import android.arch.lifecycle.ViewModel
import artalejo.com.btcgraph.di.viewmodel.DaggerViewModelInjector
import artalejo.com.btcgraph.di.viewmodel.ViewModelInjector
import artalejo.com.btcgraph.ui.chart.BtcChartViewModel
import artalejo.com.btcgraph.di.application.DataModule

abstract class BaseViewModel: ViewModel(){

    private val injector: ViewModelInjector = DaggerViewModelInjector
            .builder()
            .dataModule(DataModule())
            .build()

    init {
        inject()
    }

    /**
     * Injects the required dependencies
     */
    private fun inject() {
        when (this) {
            is BtcChartViewModel -> injector.inject(this)
        }
    }
}