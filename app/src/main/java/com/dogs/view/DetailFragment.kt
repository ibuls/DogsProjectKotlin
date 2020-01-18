package com.dogs.view


import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.telephony.SmsManager
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.dogs.MainActivity

import com.dogs.R
import com.dogs.databinding.FragmentDetailBinding
import com.dogs.databinding.SendSmsDialogBinding
import com.dogs.model.DogBreed
import com.dogs.model.DogPalette
import com.dogs.model.SmsInfo
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

    private var sendSmsStarted = false

    private var currentDog: DogBreed? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        setHasOptionsMenu(true)
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
            currentDog = dog

            dog?.let {
                binding.dog = it
                it.imageUrl?.let {

                    setupBackgroundColor(it)
                }

            }
        })


    }


    fun onPermissionResult(permissionGranted: Boolean) {

        if (sendSmsStarted && permissionGranted) {
            context?.let {
                val smsInfo = SmsInfo(
                    "",
                    "${currentDog?.dogBreed} bread for ${currentDog?.breedFor}",
                    currentDog?.imageUrl
                )

                val dialogBinding = DataBindingUtil.inflate<SendSmsDialogBinding>(
                    LayoutInflater.from(it),
                    R.layout.send_sms_dialog,
                    null,
                    false
                )
                dialogBinding.smsInfo = smsInfo


                AlertDialog.Builder(it)
                    .setView(dialogBinding.root)
                    .setPositiveButton("Send Sms"){dialog, which ->
                        if (!dialogBinding.smsDestination.text.isNullOrEmpty()){
                            smsInfo.to = dialogBinding.smsDestination.text.toString()
                            sendSms(smsInfo)
                        }
                    }
                    .setNegativeButton("Cancel"){
                        dialog, which ->
                        // nothing needed
                    }
                    .show()

            }
        }

    }


    //functionalty to send SMS
    private fun sendSms(smsInfo:SmsInfo){
        val intent  = Intent(context,MainActivity::class.java)
        val pi = PendingIntent.getActivity(context,0,intent,0)
        val sms = SmsManager.getDefault()
        sms.sendTextMessage(smsInfo.to,null,smsInfo.text,pi,null)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.detail_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_send_sms -> {
                sendSmsStarted = true
                (activity as MainActivity).checkSmsPermission()

            }
            R.id.action_share -> {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_SUBJECT,"check out this dog breed")
                intent.putExtra(Intent.EXTRA_TEXT,"${currentDog?.dogBreed} bread for ${currentDog?.breedFor}")
                intent.putExtra(Intent.EXTRA_STREAM,currentDog?.imageUrl)

                //to set a dialog title we use create chooser
                startActivity(Intent.createChooser(intent,"Share With"))

            }
        }
        return super.onOptionsItemSelected(item)

    }

    private fun setupBackgroundColor(url: String) {
        Glide.with(this)
            .asBitmap()
            .load(url)
            .into(object : CustomTarget<Bitmap>() {
                override fun onLoadCleared(placeholder: Drawable?) {

                }

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    Palette.from(resource).generate { palette ->

                        val intColor =
                            palette?.lightMutedSwatch?.rgb ?: 0; // if rgb is null then we send 0
                        val myPallet = DogPalette(intColor)
                        binding.pelette = myPallet
                    }
                }

            })
    }


}
