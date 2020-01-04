package com.dogs.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.dogs.R

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
            val testObj = DetailFragmentArgs.fromBundle(it).test

            testObj?.testMethod(activity?.applicationContext)
        }
    }


}
