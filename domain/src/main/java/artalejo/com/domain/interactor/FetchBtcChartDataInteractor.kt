package artalejo.com.domain.interactor

import artalejo.com.domain.entities.BtcDataInfo
import artalejo.com.domain.repository.BtcChartRepository
import io.reactivex.Single
import javax.inject.Inject

class FetchBtcChartDataInteractor @Inject constructor(private val btcChartRepository: BtcChartRepository) {

    fun fetchBtcChartData(timestamp: String, rollingAverage: String): Single<BtcDataInfo> {
        return btcChartRepository.getBtcChartData(timestamp, rollingAverage)
    }
}