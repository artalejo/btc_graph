package artalejo.com.domain.repository

import artalejo.com.domain.entities.BtcDataInfo
import io.reactivex.Single

interface BtcChartRepository {
    fun fetchBtcChartData(timespan: String): Single<BtcDataInfo>
}