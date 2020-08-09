package com.picpay.desafio.android.view

import android.graphics.Color
import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.picpay.desafio.android.R
import com.picpay.desafio.android.model.ResponseState
import com.picpay.desafio.android.view.adapter.UserListAdapter
import com.picpay.desafio.android.model.User
import org.koin.androidx.viewmodel.ext.android.viewModel

class UsersActivity : AppCompatActivity(R.layout.activity_users) {

    private val userViewModel: UserViewModel by viewModel()
    private val recyclerView: RecyclerView by lazy { findViewById<RecyclerView>(R.id.recyclerView) }
    private val progressBar: ProgressBar by lazy { findViewById<ProgressBar>(R.id.user_list_progress_bar) }
    private val adapter: UserListAdapter by lazy { UserListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configureRecyclerView()
        fetchUsers()
    }

    private fun fetchUsers() {
        Observer<ResponseState<List<User>>> { responseState ->
            progressBar.gone()

            when(responseState) {
                is ResponseState.Success -> adapter.users = responseState.body
                is ResponseState.Error -> Snackbar.make(
                    findViewById(android.R.id.content),
                    getString(R.string.error),
                    Snackbar.LENGTH_SHORT
                ).setTextColor(Color.YELLOW).show()
            }
        }.apply {
            progressBar.visible()
            userViewModel.fetch().observe(this@UsersActivity, this@apply)
        }
    }

    private fun configureRecyclerView() {
        with(recyclerView) {
            adapter = this@UsersActivity.adapter
            layoutManager = LinearLayoutManager(this@UsersActivity)
        }
    }

}
