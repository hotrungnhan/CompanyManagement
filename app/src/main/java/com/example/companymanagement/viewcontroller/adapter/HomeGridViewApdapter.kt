package com.example.companymanagement.viewcontroller.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.companymanagement.R
import com.example.companymanagement.viewcontroller.fragment.mainhome.HomeGridViewViewModel
import com.google.android.material.imageview.ShapeableImageView


class HomeGridViewApdapter(
    private val ctx: FragmentActivity,
    private val items: MutableList<HomeGridViewViewModel>,
) :
    RecyclerView.Adapter<HomeGridViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): HomeGridViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_cardview_button, viewGroup, false)
        return HomeGridViewHolder(view)

    }

    override fun onBindViewHolder(holder: HomeGridViewHolder, position: Int) {
        holder.setText(items[position].text)
        holder.setImageDrawable(items[position].imageDrawable)
        holder.setOnclickListener() { v ->
            ctx.findNavController(R.id.activity_container).navigate(items[position].linkID!!)
        }
    }

    override fun getItemCount() = items.size

}


class HomeGridViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val img: ShapeableImageView = itemView.findViewById(R.id.cardview_img)
    val text: TextView = itemView.findViewById(R.id.cardview_text)
    val layout: LinearLayout = itemView.findViewById(R.id.cardview_layout)

    init {
        text.isSelected = true;
    }

    fun setImageDrawable(imageid: Int) {
        this.img.setImageResource(imageid)
    }

    fun setOnclickListener(e: View.OnClickListener) {
        this.layout.setOnClickListener(e);
    }

    fun setText(text: String) {
        this.text.setText(text);
    }
}
