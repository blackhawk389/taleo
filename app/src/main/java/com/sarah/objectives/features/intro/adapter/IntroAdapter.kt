package com.sarah.objectives.features.intro.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sarah.objectives.callbacks.IntroListener
import com.sarah.objectives.databinding.IntroRowsBinding
import com.sarah.objectives.features.intro.IntroModel
import com.sarah.objectives.utils.show

class IntroAdapter(private var introListener: IntroListener) :
    RecyclerView.Adapter<IntroAdapter.IntroViewHolder>() {

    private var introItems = ArrayList<IntroModel>()

    fun addIntroItems(items: ArrayList<IntroModel>) {
        this.introItems = items
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntroViewHolder {
        val view = IntroRowsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IntroViewHolder(view)

    }

    override fun onBindViewHolder(holder: IntroViewHolder, position: Int) {
        val items = introItems[position]
        holder.bindData(items)

    }

    override fun getItemCount(): Int = introItems.size


    inner class IntroViewHolder(var itemRows: IntroRowsBinding) : RecyclerView.ViewHolder(itemRows.root) {

        fun bindData(items: IntroModel) {
            @Suppress("DEPRECATION")
            if (adapterPosition == 2) {
                itemRows.finishButton.show()
            }
            itemRows.data = items
            itemRows.animationView.setAnimation(items.anim)
            itemRows.finishButton.setOnClickListener {
                introListener.onFinished()
            }
        }


    }

}