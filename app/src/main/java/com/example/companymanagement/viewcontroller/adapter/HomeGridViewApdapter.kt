package com.example.companymanagement.viewcontroller.adapter

import android.graphics.drawable.ScaleDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.companymanagement.R
import com.example.companymanagement.viewcontroller.fragment.mainhome.HomeGridViewViewModel


class HomeGridViewApdapter(
    private val ctx: FragmentActivity,
    private val items: MutableList<HomeGridViewViewModel>,
) :
    RecyclerView.Adapter<HomeGridViewApdapter.HomeGridViewHolder>() {

    class HomeGridViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val button: Button = itemView.findViewById(R.id.cardview_item)

        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): HomeGridViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_cardview_button, viewGroup, false)
        return HomeGridViewHolder(view)

    }

    override fun onBindViewHolder(holder: HomeGridViewHolder, position: Int) {
        holder.button.setText(items[position].text)
        var drawable =
            ScaleDrawable(ctx.getDrawable(items[position].imageDrawable), 0, 10f, 10f).drawable
        holder.button.setCompoundDrawablesWithIntrinsicBounds(null,
            drawable,
            null,
            null)
        holder.button.setOnClickListener { v ->
            ctx.findNavController(R.id.activity_container).navigate(items[position].linkID!!)
            Log.d("", "LOG")
        }
    }

    override fun getItemCount() = items.size

}

