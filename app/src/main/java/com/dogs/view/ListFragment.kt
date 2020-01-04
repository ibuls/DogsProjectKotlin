package com.dogs.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation

import com.dogs.R
import com.dogs.ignore.TestClass
import kotlinx.android.synthetic.main.fragment_list.*

/**
 * A simple [Fragment] subclass.
 */
class ListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fabCall.setOnClickListener {

            // class ListFragmentDirections is automatically generated..
            val actionDetailPage = ListFragmentDirections.actionDetailFragment()
            actionDetailPage.dogUuid = 12
            actionDetailPage.test = TestClass()
            Navigation.findNavController(it).navigate(actionDetailPage)
        }
    }
}
