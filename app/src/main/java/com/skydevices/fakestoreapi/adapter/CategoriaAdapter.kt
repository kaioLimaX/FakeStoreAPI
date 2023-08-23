package com.skydevices.fakestoreapi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skydevices.fakestoreapi.R
import com.skydevices.fakestoreapi.databinding.ItemCategoriaBinding
import com.skydevices.fakestoreapi.databinding.ItemProdutoBinding
import com.skydevices.fakestoreapi.model.CategoryResponseAPI
import com.skydevices.fakestoreapi.model.responseAPIItem
import com.squareup.picasso.Picasso

class CategoriaAdapter(

    private val onClick: (String) -> Unit

) : RecyclerView.Adapter<CategoriaAdapter.FilmeViewHolder>() {

    private var listaFilmes = mutableListOf<String>()

    fun adicionarLista(lista: List<String>) {
        this.listaFilmes.clear()
        this.listaFilmes.addAll(lista)
        notifyDataSetChanged()
    }

    inner class FilmeViewHolder(val binding: ItemCategoriaBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: String) {
            binding.btnCategoria.text = item

            binding.btnCategoria.setOnClickListener {
                onClick(item)

            }
        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCategoriaBinding.inflate(
            layoutInflater, parent, false
        )

        return FilmeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilmeViewHolder, position: Int) {
        val filme = listaFilmes[position]
        holder.bind(filme)
    }

    override fun getItemCount(): Int {
        return listaFilmes.size
    }


}