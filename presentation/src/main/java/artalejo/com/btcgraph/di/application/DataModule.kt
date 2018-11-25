package artalejo.com.btcgraph.di.application

import artalejo.com.data.network.BASE_URL
import artalejo.com.data.network.services.BtcChartService
import artalejo.com.data.repository.BtcChartApiRepository
import artalejo.com.domain.repository.BtcChartRepository
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

// Changing the timeout to a lower value so that it does not take long much time to show the
// error view when attempting to do a request when no connection ( airplane mode...)
const val TIMEOUT = 10L

@Module
class DataModule {

    @Provides
    @Singleton
    fun providesRetrofitInterface(): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .build()

        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .build()
    }

    @Provides
    @Singleton
    fun providesBtcApiRepository(btcChartApiRepository: BtcChartApiRepository): BtcChartRepository {
        return btcChartApiRepository
    }

    @Provides
    @Singleton
    fun providesBtcChartService(retrofit: Retrofit): BtcChartService {
        return retrofit.create(BtcChartService::class.java)
    }

}
