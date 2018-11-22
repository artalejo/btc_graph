package artalejo.com.data.repository

import artalejo.com.data.network.services.BtcChartService
import artalejo.com.data.repository.entities.toBtcDataInfo
import artalejo.com.domain.entities.BtcDataInfo
import artalejo.com.domain.repository.BtcChartRepository
import io.reactivex.Single
import retrofit2.Retrofit
import javax.inject.Inject

class BtcChartApiRepository @Inject constructor(private val retrofit: Retrofit): BtcChartRepository {

    override fun getBtcChartData(timestamp: String, rollingAverage: String): Single<BtcDataInfo> {
        // TODO sartalejo: remove this service from here
        val service = retrofit.create(BtcChartService::class.java)
        return service.getBtcChartData(timestamp, rollingAverage).map { it.toBtcDataInfo() }
    }

}