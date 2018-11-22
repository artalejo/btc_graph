package artalejo.com.data.network.services

import artalejo.com.data.network.BTC_CHART
import artalejo.com.data.network.ROLlING_AVERAGE
import artalejo.com.data.network.TIMESTAMP
import artalejo.com.data.repository.entities.BtcDataEntity
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.GET

interface BtcChartService {
    @GET(BTC_CHART)
    fun getBtcChartData(@Field(TIMESTAMP) timeStamp: String,
                        @Field(ROLlING_AVERAGE) rollingAverage: String) : Single<BtcDataEntity>
}