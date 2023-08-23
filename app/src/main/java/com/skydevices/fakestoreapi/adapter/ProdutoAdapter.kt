package com.skydevices.fakestoreapi.adapter

import android.graphics.Paint
import android.text.SpannableString
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.skydevices.fakestoreapi.R
import com.skydevices.fakestoreapi.databinding.ItemProdutoBinding
import com.skydevices.fakestoreapi.model.responseAPIItem
import com.squareup.picasso.Picasso

class ProdutoAdapter(

    private val onClick: (responseAPIItem) -> Unit

) : RecyclerView.Adapter<ProdutoAdapter.FilmeViewHolder>() {

    private var listaFilmes = mutableListOf<responseAPIItem>()

    fun adicionarLista(lista : List<responseAPIItem>){
        this.listaFilmes.clear()
        this.listaFilmes.addAll(lista)
        notifyDataSetChanged()
    }
    var showShimmer = true

    inner class FilmeViewHolder(val binding: ItemProdutoBinding)
        : RecyclerView.ViewHolder(binding.root) {

        val shimmerLayout: ShimmerFrameLayout = binding.shimmerLayout

        fun bind(item : responseAPIItem){

            Picasso.get()
                .load(item.image)
                .error(R.drawable.ic_launcher_background)
                .into(binding.imageProduto)

            binding.txtProduto.text = item.title



            binding.textView5.text = "R$ ${item.price}"


            val valorDesconto = item.price * 0.1
            val valorFinal = item.price - valorDesconto
            val valorDescontoFormatado = String.format("%.2f", valorFinal)

            binding.textView5.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);

            binding.tvtPrice.text = "R$ ${valorDescontoFormatado}"


            binding.cvItem.setOnClickListener {
                onClick(item)
            }
        }

        fun showShimmer(show: Boolean) {
            if (show) {
                shimmerLayout.startShimmer()
                binding.cvItem.alpha = 0.4f
            } else {
                shimmerLayout.stopShimmer()
                binding.cvItem.alpha = 1.0f
                shimmerLayout.setShimmer(null)
                binding.imageProduto.background = null
                binding.txtProduto.background = null
                binding.textView5.background = null
                binding.tvtPrice.background = null
            }
        }







    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemProdutoBinding.inflate(layoutInflater,parent,false
        )

        return FilmeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilmeViewHolder, position: Int) {
        if (showShimmer) {
            holder.showShimmer(true)

        } else {

            holder.showShimmer(false)
            val filme = listaFilmes[position]
            holder.bind(filme)


        }
    }



    override fun getItemCount(): Int {
        val SHIMMER_ITEM_NUMBER = 10
        return if (showShimmer) SHIMMER_ITEM_NUMBER else listaFilmes.size
    }


}