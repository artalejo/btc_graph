package artalejo.com.data.repository

import artalejo.com.data.network.services.BtcChartService
import artalejo.com.data.repository.entities.toBtcDataInfo
import artalejo.com.domain.entities.BtcDataInfo
import artalejo.com.domain.repository.BtcChartRepository
import io.reactivex.Single
import javax.inject.Inject

class BtcChartApiRepository @Inject constructor(private val btcChartService: BtcChartService): BtcChartRepository {

    override fun getBtcChartData(timestamp: String, rollingAverage: String): Single<BtcDataInfo> {
        return btcChartService.getBtcChartData(timestamp, rollingAverage).map { it.toBtcDataInfo() }
    }

}