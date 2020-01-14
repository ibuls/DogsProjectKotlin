package com.dogs.view


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.dogs.R
import com.dogs.databinding.FragmentDetailBinding
import com.dogs.utils.getProgressDrawable
import com.dogs.utils.loadImage
import com.dogs.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.fragment_detail.*

/**
 * A simple [Fragment] subclass.
 */
class DetailFragment : Fragment() {


    lateinit var viewModel: DetailViewModel
    lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate<FragmentDetailBinding>(
            inflater,
            R.layout.fragment_detail,
            container,
            false
        )
        return binding.root //inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        // arguments?.let means only execute the following code if arguments are not null
        observeViewModel()

        arguments?.let {

            // read the arguments which are sent by other fragment
            // here it is basically whetever was before ? i.e arguments in our case
            //DetailFragmentArgs class is automatically generated....
            val dogUuid = DetailFragmentArgs.fromBundle(it).dogUuid
            Log.d("UUID", "UUID Fetched: " + dogUuid)
            viewModel.fetch(dogUuid)
        }


    }


    private fun observeViewModel() {

        viewModel.dog.observe(this, Observer { dog ->
            dog?.let {
                binding.dog = it

            }
        })


    }


}
