package artalejo.com.btcgraph.ui.chart

import android.arch.lifecycle.MutableLiveData
import artalejo.com.btcgraph.ui.base.BaseViewModel
import artalejo.com.btcgraph.ui.entities.BtcChartViewEntity
import artalejo.com.btcgraph.ui.entities.toBtcChartViewEntity
import artalejo.com.domain.interactor.FetchBtcChartDataInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class BtcChartViewModel @Inject constructor(private val fetchBtcChartDataInteractor: FetchBtcChartDataInteractor): BaseViewModel() {

    private lateinit var subscription: Disposable
    val stateLiveData =  MutableLiveData<BtcChartViewEntity>()

    init {
//        stateLiveData.value = LoadingState(BtcChartViewEntity())
        loadBtcChartData()
    }

    private fun loadBtcChartData(){
        // TODO set the params properly for the call
        subscription = fetchBtcChartDataInteractor.fetchBtcChartData("", "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onFetchBtcDataStarted() }
                .subscribe(
                        { onFetchBtcDataSuccess(it.toBtcChartViewEntity()) },
                        { onFetchBtcDataError() }
                )
    }


    private fun onFetchBtcDataStarted(){

    }

    private fun onFetchBtcDataSuccess(btcData: BtcChartViewEntity){

    }

    private fun onFetchBtcDataError(){

    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

}