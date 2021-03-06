package artalejo.com.btcgraph.di.application

import android.app.Application
import artalejo.com.btcgraph.BaseApplication
import artalejo.com.btcgraph.di.activity.ActivityInjector
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, AndroidSupportInjectionModule::class,
                      ActivityInjector::class, DataModule::class])

interface ApplicationComponent {
    fun inject(application: BaseApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): ApplicationComponent
    }
}