package artalejo.com.data

import artalejo.com.data.network.services.BtcChartService
import artalejo.com.data.repository.BtcChartApiRepository
import artalejo.com.data.repository.entities.BtcDataEntity
import artalejo.com.data.repository.entities.toBtcDataInfo
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.reset
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

class BtcChartApiRepositoryUnitTest {

    private lateinit var mockedApiService: BtcChartService
    private val btcChartApiRepository by lazy { BtcChartApiRepository(mockedApiService) }
    private val timestamp = "timestamp"
    private val btcDataEntity = BtcDataEntity("status_ok", "name", "USD", "1week", "test description", listOf())

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        mockedApiService = mock()
    }

    @After
    fun tearDown(){
        reset(mockedApiService)
    }

    @Test
    fun onFetchBtcPriceDataEmptyDataInfoSuccess() {
        whenever(mockedApiService.getBtcChartData(timestamp)).thenReturn(Single.just(btcDataEntity))
        btcChartApiRepository.fetchBtcChartData(timestamp).test().assertComplete()
    }

    @Test
    fun onFetchBtcPriceDataSuccess() {
        whenever(mockedApiService.getBtcChartData(timestamp)).thenReturn(Single.just(btcDataEntity))
        btcChartApiRepository.fetchBtcChartData(timestamp).test().assertValue(btcDataEntity.toBtcDataInfo())
    }

    @Test
    fun onFetchBtcPriceDataError() {
        val error = Throwable("Unknown error")
        whenever(mockedApiService.getBtcChartData(timestamp)).thenReturn(Single.error(error))
        btcChartApiRepository.fetchBtcChartData(timestamp).test().assertError(error)
    }
}