package artalejo.com.btcgraph.di.activity

import artalejo.com.btcgraph.di.scope.PerActivity
import artalejo.com.btcgraph.ui.chart.BtcChartActivity
import artalejo.com.btcgraph.ui.chart.BtcChartActivityModule
import artalejo.com.btcgraph.ui.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityInjector {
    @PerActivity
    @ContributesAndroidInjector(modules = [])
    abstract fun contributeSplashInjector(): SplashActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [(BtcChartActivityModule::class)])
    abstract fun contributeBtcChartInjector(): BtcChartActivity
}