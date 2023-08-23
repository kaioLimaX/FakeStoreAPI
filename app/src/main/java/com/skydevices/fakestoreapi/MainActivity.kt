package com.skydevices.fakestoreapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.skydevices.fakestoreapi.adapter.CategoriaAdapter
import com.skydevices.fakestoreapi.adapter.ProdutoAdapter
import com.skydevices.fakestoreapi.api.RetrofitHelper
import com.skydevices.fakestoreapi.databinding.ActivityMainBinding
import com.skydevices.fakestoreapi.model.CategoryResponseAPI
import com.skydevices.fakestoreapi.model.responseAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val produtoAPI by lazy {
        RetrofitHelper.produtoAPI
    }

    var jobProduto: Job? = null

    var gridLayoutManager : GridLayoutManager? = null

    var linearLayoutManager : LinearLayoutManager? = null

    private lateinit var produtoAdapter : ProdutoAdapter
    private lateinit var categoriaAdapter : CategoriaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.title = "LIMA'Store"

        with(binding){
            swiperefresh.setOnRefreshListener {
                recuperarProdutos()
            }
        }


        inicializarView()
        recuperarProdutos()
        binding.swiperefresh.isRefreshing = true
    }

    private fun recuperarProdutos() {

        jobProduto = CoroutineScope(Dispatchers.IO).launch {

            var resposta : Response<responseAPI>? = null
            var respostaCategoria : Response<CategoryResponseAPI>? = null

            respostaCategoria = produtoAPI.recuperarCategoria()

            resposta = produtoAPI.recuperarProdutos()

            try {

            }catch (e: Exception){
                exibirMensagem("erro ao fazer requisição")
            }

            if (resposta != null ){
                if (resposta.isSuccessful){
                    val listaProdutos = resposta.body()
                    val Categoria = respostaCategoria.body()
                    val lista : MutableList<String> = mutableListOf()


                    if (listaProdutos != null && listaProdutos.isNotEmpty()){

                        Categoria?.forEach {item ->
                            withContext(Dispatchers.Main){
                                lista.add(item)
                            }
                        }

                        withContext(Dispatchers.Main){
                            categoriaAdapter.adicionarLista(lista)
                            produtoAdapter?.showShimmer = false
                            produtoAdapter.adicionarLista(listaProdutos)
                            binding.swiperefresh.isRefreshing = false



                        }

                    }
                }else{
                    exibirMensagem("problema ao fazer requisição : ${resposta.code()}")
                }

            }else{
                exibirMensagem("não foi possivel fazer a requisição")
            }

        }
    }

    private fun recuperarPorCategoria(categoria : String) {

        binding.swiperefresh.isRefreshing = true

        jobProduto = CoroutineScope(Dispatchers.IO).launch {

            var resposta : Response<responseAPI>? = null
            var respostaCategoria : Response<CategoryResponseAPI>? = null

            respostaCategoria = produtoAPI.recuperarCategoria()

            resposta = produtoAPI.recuperarPorCategoria(categoria)

            try {

            }catch (e: Exception){
                exibirMensagem("erro ao fazer requisição")
            }

            if (resposta != null ){
                if (resposta.isSuccessful){
                    val listaProdutos = resposta.body()
                    val categoria = respostaCategoria.body()


                    if (listaProdutos != null && listaProdutos.isNotEmpty()){

                        val lista : MutableList<String> = mutableListOf()

                        categoria?.forEach {item ->
                            withContext(Dispatchers.Main){
                                lista.add(item)
                            }
                        }

                        withContext(Dispatchers.Main){
                            categoriaAdapter.adicionarLista(lista)
                            produtoAdapter.adicionarLista(listaProdutos)
                            binding.swiperefresh.isRefreshing = false


                        }

                    }
                }else{
                    exibirMensagem("problema ao fazer requisição : ${resposta.code()}")
                }

            }else{
                exibirMensagem("não foi possivel fazer a requisição")
            }

        }
    }



    private fun exibirMensagem(mensagem : String) {
        CoroutineScope(Dispatchers.Main).launch {
            Toast.makeText(applicationContext, mensagem, Toast.LENGTH_SHORT).show()
        }
    }

    private fun inicializarView() {
        produtoAdapter = ProdutoAdapter { filme ->
            /*val intent = Intent(applicationContext, DetalhesActivity::class.java)
            intent.putExtra("filme", filme)
            startActivity( intent )*/
        }
        binding.rvProdutos.adapter = produtoAdapter

        gridLayoutManager =  GridLayoutManager(this, 2)


        binding.rvProdutos.layoutManager = gridLayoutManager


        //inicializar Categoria

        categoriaAdapter = CategoriaAdapter { item ->
            recuperarPorCategoria(item)

        }
        binding.rvCategoria.adapter = categoriaAdapter

        linearLayoutManager =  LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)


        binding.rvCategoria.layoutManager = linearLayoutManager
        //binding.rvPopulares.layoutManager = GridLayoutManager(this,2)

    }
}