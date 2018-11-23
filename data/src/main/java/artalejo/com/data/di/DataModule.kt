package artalejo.com.data.di

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
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun providesRetrofitInterface(): Retrofit {
        val okHttpClient = OkHttpClient.Builder().build()

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
