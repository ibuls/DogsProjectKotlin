package com.dogs.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.dogs.R
import com.dogs.ignore.TestClass
import com.dogs.model.DogBreed
import com.dogs.utils.getProgressDrawable
import com.dogs.utils.loadImage
import kotlinx.android.synthetic.main.item_dog.view.*


class DogsAdapter(val listDogs:ArrayList<DogBreed>):RecyclerView.Adapter<DogsAdapter.DogsViewHolder>(){



    fun updateListData(newListData:List<DogBreed>){
        listDogs.clear()
        listDogs.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogsViewHolder {
        val inflator = LayoutInflater.from(parent.context)
        val view = inflator.inflate(R.layout.item_dog, parent, false)
        return DogsViewHolder(view)
    }

    override fun getItemCount(): Int {
       return listDogs.size
    }

    override fun onBindViewHolder(holder: DogsViewHolder, position: Int) {
        val dog = listDogs.get(position)
        holder.itemView.tvDogName.text = dog.dogBreed
        holder.itemView.tvLifeSpan.text = dog.lifeSpan
        holder.itemView.imageView.loadImage(dog.imageUrl, getProgressDrawable(context = holder.itemView.context))
        holder.itemView.setOnClickListener {
            val actionDetailFragment = ListFragmentDirections.actionDetailFragment()
           dog.breedId?.let {
               actionDetailFragment.dogUuid = dog.uuid
           }
            Navigation.findNavController(it).navigate(actionDetailFragment)
        }
    }

    class DogsViewHolder(view: View):RecyclerView.ViewHolder(view);

}