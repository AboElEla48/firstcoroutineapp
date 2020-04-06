package eg.foureg.firstcoroutineapp.ui.main

import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class CounterModelTest {

    @Test
    fun loadCounter() = runBlocking{
        val counterModel = CounterModel()

        var counter = counterModel.loadCounter()
        Assert.assertEquals(counter, 5)

        counter = counterModel.loadCounter()
        Assert.assertEquals(counter, 10)

        counter = counterModel.loadCounter()
        Assert.assertEquals(counter, 15)
    }
}