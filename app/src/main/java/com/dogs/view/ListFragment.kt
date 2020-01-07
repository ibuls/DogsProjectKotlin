package com.dogs.view


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager

import com.dogs.R
import com.dogs.ignore.TestClass
import com.dogs.utils.log
import com.dogs.utils.toast
import com.dogs.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.fragment_list.*
import java.util.zip.GZIPOutputStream

/**
 * A simple [Fragment] subclass.
 */
class ListFragment : Fragment() {


    private lateinit var viewModel: ListViewModel
    private val dogsAdapter=DogsAdapter(arrayListOf())
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)

        // for applying multiple properties
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = dogsAdapter
        }

        viewModel.refresh()

        observeViewModel()
    }


    fun observeViewModel(){
        viewModel.dogs.observe(this, Observer {dogs->

                dogs?.let {

                    recyclerView.visibility = View.VISIBLE
                    dogsAdapter.updateListData(dogs)

            }
        })


        viewModel.dogsLoadError.observe(this, Observer { isError->

            // ?.let means if isError is not null only then
            isError?.let {
                tvError.visibility = if (isError) View.VISIBLE else View.GONE

                if (isError) {
                    recyclerView.visibility = View.GONE
                    progressBar.visibility = View.GONE
                }
            } })

        viewModel.loading.observe(this, Observer { isLoding->
            isLoding?.let {
                progressBar.visibility = if (isLoding) View.VISIBLE else View.GONE

                if (isLoding)
                {
                    recyclerView.visibility = View.GONE
                    tvError.visibility = View.GONE
                }

            } })


    }

}
