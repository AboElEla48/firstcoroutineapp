package eg.foureg.firstcoroutineapp.ui.main

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eg.foureg.firstcoroutineapp.R
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    fun initModel(c: Context) {
        context = c
    }

    fun changeText() {
        viewModelScope.launch {
            txtMsg.value = String.format(context.getString(R.string.txt_counter_msg), counterModel.loadCounter())
        }
    }

    lateinit var context : Context
    val counterModel : CounterModel = CounterModel()
    var txtMsg : MutableLiveData<String> = MutableLiveData()

}
