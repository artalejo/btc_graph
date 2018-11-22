package artalejo.com.btcgraph.di.application

import android.app.Application
import android.content.Context
import artalejo.com.btcgraph.di.qualifier.ApplicationContext
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule {

    @Provides
    @Singleton
    @ApplicationContext
    internal fun provideContext(application: Application): Context {
        return application
    }

}