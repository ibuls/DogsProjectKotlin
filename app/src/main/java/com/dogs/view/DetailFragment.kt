package com.dogs.view


import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

import com.dogs.R
import com.dogs.databinding.FragmentDetailBinding
import com.dogs.model.DogPalette
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

                it.imageUrl?.let {

                    setupBackgroundColor(it)
                }

            }
        })


    }

    private fun setupBackgroundColor(url: String) {
        Glide.with(this)
            .asBitmap()
            .load(url)
            .into(object :CustomTarget<Bitmap>(){
                override fun onLoadCleared(placeholder: Drawable?) {

                }

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    Palette.from(resource).generate { palette ->

                        val intColor = palette?.lightMutedSwatch?.rgb?:0; // if rgb is null then we send 0
                        val myPallet = DogPalette(intColor)
                        binding.pelette = myPallet
                    }
                }

            })
    }


}
