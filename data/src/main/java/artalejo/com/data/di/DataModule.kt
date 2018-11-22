package artalejo.com.data.di

import artalejo.com.data.network.BASE_URL
import artalejo.com.data.repository.BtcChartApiRepository
import artalejo.com.domain.repository.BtcChartRepository
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit {
        val okHttpClient = OkHttpClient.Builder().build()

        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .build()
    }

    @Provides
    @Singleton
    fun providesBtcApiRepository(btcChartApiRepository: BtcChartApiRepository): BtcChartRepository {
        return btcChartApiRepository
    }
}
