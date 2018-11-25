package artalejo.com.btc_graph.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import artalejo.com.btcgraph.ui.chart.*
import artalejo.com.btcgraph.ui.entities.toBtcChartViewEntity
import artalejo.com.domain.entities.BtcDataInfo
import artalejo.com.domain.interactor.FetchBtcChartDataInteractor
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.reset
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.MockitoAnnotations


class BtcChartViewModelUnitTest {

    private lateinit var mockedFetchBtcDataInteractor: FetchBtcChartDataInteractor
    private lateinit var btcViewModelState : Observer<BtcChartModel>
    private val timestamp = "timestamp"
    private val btcViewModel by lazy { BtcChartViewModel(mockedFetchBtcDataInteractor,
                                                         Schedulers.trampoline(),
                                                         Schedulers.trampoline()) }

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        mockedFetchBtcDataInteractor = mock()
        btcViewModelState = mock()
        btcViewModel.btcLiveData.observeForever(btcViewModelState)
    }

    @After
    fun tearDown(){
        reset(mockedFetchBtcDataInteractor)
    }

    @Test
    fun loadBtcChartDataSuccess() {
        //given
        val btcDataInfo = BtcDataInfo()
        whenever(mockedFetchBtcDataInteractor.fetchBtcChartData(timestamp)).thenReturn(Single.just(btcDataInfo))
        //when
        btcViewModel.loadBtcChartData(timestamp)
        //then
        verify(btcViewModelState).onChanged(LoadingState())
        verify(btcViewModelState).onChanged(DataRetrievedState(btcDataInfo.toBtcChartViewEntity()))
    }


    @Test
    fun loadBtcChartDataError() {
        //given
        val errorMessage = "Unknown error"
        val error = Throwable(errorMessage)
        whenever(mockedFetchBtcDataInteractor.fetchBtcChartData(timestamp)).thenReturn(Single.error(error))
        //when
        btcViewModel.loadBtcChartData(timestamp)
        //then
        verify(btcViewModelState).onChanged(LoadingState())
        verify(btcViewModelState).onChanged(ErrorState(errorMessage = errorMessage))
    }

}