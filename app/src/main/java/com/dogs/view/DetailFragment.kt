package com.dogs.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.dogs.R
import kotlinx.android.synthetic.main.fragment_detail.*

/**
 * A simple [Fragment] subclass.
 */
class DetailFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // arguments?.let means only execute the following code if arguments are not null
        val let = arguments?.let {

            // read the arguments which are sent by other fragment
            // here it is basically whetever was before ? i.e arguments in our case
            //DetailFragmentArgs class is automatically generated....
            val dogUuid = DetailFragmentArgs.fromBundle(it).dogUuid
            val test = DetailFragmentArgs.fromBundle(it).test
            val dogBreed = DetailFragmentArgs.fromBundle(it).dogBreed
            val lifeSpan = DetailFragmentArgs.fromBundle(it).lifeSpan
            val temperment = DetailFragmentArgs.fromBundle(it).temperment
            val imageUrl = DetailFragmentArgs.fromBundle(it).imageUrl

            test?.testMethod(activity?.applicationContext)

            tvName.text = dogBreed
            tvLifeSpan.text = lifeSpan
            tvTemprament.text = temperment

        }
    }


}
