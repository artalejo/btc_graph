package artalejo.com.btcgraph.di.viewmodel

import artalejo.com.btcgraph.di.application.DataModule
import artalejo.com.btcgraph.ui.chart.BtcChartViewModel
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [(DataModule::class)])
interface ViewModelInjector {

    fun inject(btcChartViewModel: BtcChartViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector
        fun dataModule(dataModule: DataModule): Builder
    }
}