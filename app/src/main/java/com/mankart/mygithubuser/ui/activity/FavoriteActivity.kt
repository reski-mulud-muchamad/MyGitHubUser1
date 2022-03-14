package com.mankart.mygithubuser.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mankart.mygithubuser.data.model.UserModel
import com.mankart.mygithubuser.data.viewmodel.FavUserViewModel
import com.mankart.mygithubuser.data.viewmodel.ViewModelFactory
import com.mankart.mygithubuser.databinding.ActivityFavoriteBinding
import com.mankart.mygithubuser.ui.adapter.ListUserAdapter
import com.mankart.mygithubuser.utils.DateUtils

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var rvUser: RecyclerView
    private lateinit var listUserAdapter: ListUserAdapter
    private lateinit var factory: ViewModelFactory
    private val favUserViewModel: FavUserViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rvUser = binding.rvUsers
        rvUser.setHasFixedSize(true)

        factory = ViewModelFactory.getInstance(this)

        initObserver()
        showRecycleView()

        supportActionBar?.title = "Favourites"
    }

    private fun initObserver() {
        favUserViewModel.getListFavUser().observe(this) { users ->
            listUserAdapter.setData(users as ArrayList<UserModel>)
        }
    }

    private fun showRecycleView() {
        rvUser.layoutManager = LinearLayoutManager(this)
        listUserAdapter = ListUserAdapter { user ->
            if (user.isFavorite) {
                favUserViewModel.deleteFavUser(user)
            } else {
                user.isFavorite = true
                user.createdAt = DateUtils.getCurrentDate()
                favUserViewModel.insertFavUser(user)
            }
        }
        rvUser.adapter = listUserAdapter
    }
}