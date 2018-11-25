package artalejo.com.btcgraph.interactor

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
    private val timespan = "timespan"
    private val btcDataInfo = BtcDataInfo("test description", listOf())

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
        whenever(mockedBtcChartRepository.fetchBtcChartData(timespan)).thenReturn(Single.just(btcDataInfo))
        fetchBtcDataInteractor.fetchBtcChartData(timespan).test().assertComplete()
    }

    @Test
    fun onFetchBtcPriceDataSuccess() {
        whenever(mockedBtcChartRepository.fetchBtcChartData(timespan)).thenReturn(Single.just(btcDataInfo))
        fetchBtcDataInteractor.fetchBtcChartData(timespan).test().assertValue(btcDataInfo)
    }

    @Test
    fun onFetchBtcPriceDataError() {
        val error = Throwable("Unknown error")
        whenever(mockedBtcChartRepository.fetchBtcChartData(timespan)).thenReturn(Single.error(error))
        fetchBtcDataInteractor.fetchBtcChartData(timespan).test().assertError(error)
    }
}