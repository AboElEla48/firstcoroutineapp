package eg.foureg.firstcoroutineapp.ui.main

import kotlinx.coroutines.delay


class CounterModel {

     suspend fun loadCounter() : Int{
         delay(3 * 1000)
         return ++counter
    }

    var counter : Int = 0
}