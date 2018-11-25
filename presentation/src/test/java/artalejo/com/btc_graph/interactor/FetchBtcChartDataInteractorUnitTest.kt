package artalejo.com.btc_graph.interactor

import artalejo.com.domain.entities.BtcDataInfo
import artalejo.com.domain.interactor.FetchBtcChartDataInteractor
import artalejo.com.domain.repository.BtcChartRepository
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.reset
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

class FetchBtcChartDataInteractorUnitTest {

    private lateinit var mockedBtcChartRepository: BtcChartRepository
    private val fetchBtcDataInteractor by lazy { FetchBtcChartDataInteractor(mockedBtcChartRepository) }
    private val timestamp = "timestamp"

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        mockedBtcChartRepository = mock()
    }

    @After
    fun tearDown(){
        reset(mockedBtcChartRepository)
    }

    @Test
    fun onFetchBtcPriceDataEmptyDataInfoSuccess() {
        val btcDataInfo = BtcDataInfo()
        whenever(mockedBtcChartRepository.fetchBtcChartData(timestamp)).thenReturn(Single.just(btcDataInfo))
        fetchBtcDataInteractor.fetchBtcChartData(timestamp).test().assertComplete()
    }

    @Test
    fun onFetchBtcPriceDataSuccess() {
        val btcDataInfo = BtcDataInfo("test description", listOf())
        whenever(mockedBtcChartRepository.fetchBtcChartData(timestamp)).thenReturn(Single.just(btcDataInfo))
        fetchBtcDataInteractor.fetchBtcChartData(timestamp).test().assertValue(btcDataInfo)
    }

    @Test
    fun onFetchBtcPriceDataError() {
        val error = Throwable("Unknown error")
        whenever(mockedBtcChartRepository.fetchBtcChartData(timestamp)).thenReturn(Single.error(error))
        fetchBtcDataInteractor.fetchBtcChartData(timestamp).test().assertError(error)
    }
}