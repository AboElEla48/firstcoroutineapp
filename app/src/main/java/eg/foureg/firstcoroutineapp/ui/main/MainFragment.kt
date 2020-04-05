package eg.foureg.firstcoroutineapp.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import eg.foureg.firstcoroutineapp.R
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.initModel(context!!)

        viewModel.txtMsg.observe(viewLifecycleOwner, Observer {text ->
            message_text_view.text = text

            msng_progress_bar.visibility = View.GONE
        })


        counter_btn.setOnClickListener{
            msng_progress_bar.visibility = View.VISIBLE
            viewModel.changeText()
        }


    }

}