package artalejo.com.btcgraph.di.application

import android.app.Application
import android.content.Context
import artalejo.com.btcgraph.di.qualifier.ApplicationContext
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Named
import javax.inject.Singleton

const val SCHEDULERS_MAIN_THREAD = "SCHEDULERS_MAIN_THREAD"
const val SCHEDULERS_IO = "SCHEDULERS_IO"

@Module
class ApplicationModule {

    @Provides
    @Singleton
    @ApplicationContext
    internal fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @Named(SCHEDULERS_IO)
    fun provideIoScheduler() : Scheduler = Schedulers.io()

    @Provides
    @Named(SCHEDULERS_MAIN_THREAD)
    fun provideMainThreadScheduler() : Scheduler = AndroidSchedulers.mainThread()

}