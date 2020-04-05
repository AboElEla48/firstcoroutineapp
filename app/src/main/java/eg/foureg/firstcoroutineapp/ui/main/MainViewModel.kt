package eg.foureg.firstcoroutineapp.ui.main

import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eg.foureg.firstcoroutineapp.R
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    fun initModel(c: Context) {
        context = c
    }

    /**
     * Load value counter from backend and change text in UI according to result value
     */
    fun changeText() {
        val counterLoaderJob = viewModelScope.launch {
            // Prepare UI for loading
            progressVisibility.value = View.VISIBLE
            counterBtnEnabled.value = false

            // Load Counter from async data provider (Backend simulation)
            txtMsg.value = String.format(context.getString(R.string.txt_counter_msg), counterModel.loadCounter())
        }

        viewModelScope.launch {
            // Wait till loading is finished from backend
            counterLoaderJob.join()

            // Hide progresss bar when finishes loading
            progressVisibility.value = View.GONE
            counterBtnEnabled.value = true
        }

    }

    lateinit var context : Context
    val counterModel : CounterModel = CounterModel()

    val txtMsg : MutableLiveData<String> = MutableLiveData()
    val progressVisibility : MutableLiveData<Int> = MutableLiveData()
    val counterBtnEnabled : MutableLiveData<Boolean> = MutableLiveData()

}
