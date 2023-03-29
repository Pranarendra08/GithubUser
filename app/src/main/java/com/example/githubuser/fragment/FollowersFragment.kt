package com.example.githubuser.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.adapter.FollowAdapter
import com.example.githubuser.api.response.ItemsItem
import com.example.githubuser.databinding.FragmentFollowersBinding
import com.example.githubuser.viewmodel.FollowViewModel


class FollowersFragment : Fragment() {

    private lateinit var binding: FragmentFollowersBinding
    private val followViewModel: FollowViewModel by activityViewModels()

    companion object{
        const val ARG_SECTION_NUMBER = "section_number"
        const val USERNAME_FOLLOWERS = "username"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val index = arguments?.getInt(ARG_SECTION_NUMBER, 0)
        val username = arguments?.getString(USERNAME_FOLLOWERS)

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.recyclerView.layoutManager = layoutManager

        followViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        if (index == 1) {
            showLoading(true)
            followViewModel.getUserFollowers(username.toString())

            followViewModel.followerslUser.observe(viewLifecycleOwner) {
                setFollower(it)
            }
            showLoading(false)
        } else {
            showLoading(true)
            followViewModel.getUserFollowing(username.toString())

            followViewModel.followinglUser.observe(viewLifecycleOwner) {
                setFollower(it)
            }
            showLoading(false)
        }
    }

    private fun setFollower(data: List<ItemsItem>) {
        val adapter = FollowAdapter(data)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        binding.recyclerView.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}