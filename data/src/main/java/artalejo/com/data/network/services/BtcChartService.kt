package artalejo.com.data.network.services

import artalejo.com.data.network.BTC_CHART
import artalejo.com.data.network.TIMESTAMP
import artalejo.com.data.repository.entities.BtcDataEntity
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface BtcChartService {
    @GET(BTC_CHART)
    fun getBtcChartData(@Query(TIMESTAMP) timeStamp: String) : Single<BtcDataEntity>
}