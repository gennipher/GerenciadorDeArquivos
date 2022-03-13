package br.edu.infnet.gerenciadordearquivos

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var listViewFiles : ListView
    private lateinit var files: File
    private lateinit var textViewConteudo: TextView
    private lateinit var listFiles: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listViewFiles = findViewById(R.id.listViewFiles)
        textViewConteudo = findViewById(R.id.textViewConteudo)

        files = filesDir

        if(files.isDirectory && !files.list().isNullOrEmpty()) {
            listFiles = files.list().toList()
            listViewFiles.adapter = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                listFiles
            )
        }
        else
            showSnackbar("Diretório Vazio.")

        listViewFiles.setOnItemClickListener{ adapterView, view, i, l ->
            abrirFile(listFiles.get(i))
        }

        openFileOutput("teste.txt", Context.MODE_APPEND).write("\nConteúdo do arquivo".toByteArray())
    }

    private fun showSnackbar(msg: String) {
        Snackbar
            .make(listViewFiles, msg, Snackbar.LENGTH_LONG)
            .show()
    }

    fun abrirFile(child: String) {
        val file = File(filesDir, child)
        if (file.isDirectory)
            showSnackbar("É um diretório.")
        else
            if(file.isFile)
                textViewConteudo.text = lerConteudoArquivo(child)
    }

    fun lerConteudoArquivo(child: String) = openFileInput(child).bufferedReader().readText()




}