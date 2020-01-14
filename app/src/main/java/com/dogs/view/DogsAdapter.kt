package com.dogs.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.dogs.R
import com.dogs.databinding.ItemDogBinding
import com.dogs.ignore.TestClass
import com.dogs.model.DogBreed
import com.dogs.utils.getProgressDrawable
import com.dogs.utils.loadImage
import kotlinx.android.synthetic.main.item_dog.view.*


class DogsAdapter(val listDogs:ArrayList<DogBreed>):RecyclerView.Adapter<DogsAdapter.DogsViewHolder>(),DogClickListener{



    fun updateListData(newListData:List<DogBreed>){
        listDogs.clear()
        listDogs.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogsViewHolder {
        val inflator = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemDogBinding>(inflator,R.layout.item_dog,parent,false)
        return DogsViewHolder(view)
    }

    override fun getItemCount(): Int {
       return listDogs.size
    }

    override fun onBindViewHolder(holder: DogsViewHolder, position: Int) {
        holder.view.dog = listDogs[position]
        holder.view.listener = this

    }

    class DogsViewHolder(val view: ItemDogBinding):RecyclerView.ViewHolder(view.root)

    override fun onDogClicked(v: View) {
        val uuid = v.dogId.text.toString().toInt()
        val actionDetailFragment = ListFragmentDirections.actionDetailFragment()
        actionDetailFragment.dogUuid = uuid
        Navigation.findNavController(v).navigate(actionDetailFragment)
    }



}