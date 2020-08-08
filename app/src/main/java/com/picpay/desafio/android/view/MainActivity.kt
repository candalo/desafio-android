package com.picpay.desafio.android.view

import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.R
import com.picpay.desafio.android.UserListAdapter
import com.picpay.desafio.android.model.User
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val userViewModel: UserViewModel by viewModel()
    private val recyclerView: RecyclerView by lazy { findViewById<RecyclerView>(R.id.recyclerView) }
    private val progressBar: ProgressBar by lazy { findViewById<ProgressBar>(R.id.user_list_progress_bar) }
    private val adapter: UserListAdapter by lazy { UserListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupRecyclerView()
        fetchUsers()
    }

    private fun fetchUsers() {
        Observer<List<User>> { users ->
            progressBar.gone()
            adapter.users = users
        }.apply {
            progressBar.visible()
            userViewModel.fetch().observe(this@MainActivity, this@apply)
        }
    }

    private fun setupRecyclerView() {
        with(recyclerView) {
            adapter = this@MainActivity.adapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

}
