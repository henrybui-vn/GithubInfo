package com.android.githubinfo.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.githubinfo.R
import com.android.githubinfo.ui.adapter.ProjectsAdapter
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.github_info_fragment.*


class GithubInfoFragment : Fragment() {
    private lateinit var viewModel: GithubInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.github_info_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(GithubInfoViewModel::class.java)

        initUI()
        observeChanges()
    }

    private fun initUI() {
        //Init UI
        etSearchUser.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                hideUserInfo()
                pbUserInfo.isVisible = true
                viewModel.loadUserInfo(etSearchUser.text.toString())
                etSearchUser.clearFocus()
                true
            }
            false
        }
    }

    private fun observeChanges() {
        viewModel.userInfoData.observe(viewLifecycleOwner, Observer {
            if (it.id == null) {
                pbUserInfo.isVisible = false
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
            } else {
                viewModel.loadProjectsInfo(it.repos_url)

                pbUserInfo.isVisible = false
                tvFullName.isVisible = true
                tvUserId.isVisible = true
                tvUserBio.isVisible = it.bio != "" && it.bio != null
                imgUserBlog.isVisible = it.blog != "" && it.blog != null
                tvUserBlog.isVisible = it.blog != "" && it.blog != null
                cvAvatar.isVisible = true

                tvFullName.text = it.name
                tvUserId.text = it.login
                tvUserBio.text = it.bio
                tvUserBlog.text = it.blog

                if (it.blog != "" && it.blog != null) {
                    tvUserBlog.setOnClickListener { _ ->
                        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(it.blog))
                        startActivity(browserIntent)
                    }
                }

                imgAvatar.setOnClickListener { _ ->
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(it.html_url))
                    startActivity(browserIntent)
                }

                Glide.with(this).load(it.avatar_url).into(imgAvatar)
            }
        })

        viewModel.userInfoErrorData.observe(viewLifecycleOwner, Observer {
            pbUserInfo.isVisible = false
            hideUserInfo()
            if (it.contains("HTTP 404")) {
                Toast.makeText(
                    requireContext(),
                    requireContext().getText(R.string.account_not_found),
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }
        })

        viewModel.projectsInfoData.observe(viewLifecycleOwner, Observer { projects ->
            rvProjects.isVisible = true
            tvProjectsInfo.isVisible = true

            if (projects.isNotEmpty()) {
                tvProjectsInfo.text = requireContext().getText(R.string.projects)
            } else {
                tvProjectsInfo.text = requireContext().getText(R.string.empty_project)
            }

            val projectsAdapter = ProjectsAdapter(requireContext(), projects, onClickDetails = {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(it.url))
                startActivity(browserIntent)
            })

            rvProjects.adapter = projectsAdapter
            rvProjects.layoutManager = LinearLayoutManager(context)
        })
    }

    private fun hideUserInfo() {
        tvFullName.isVisible = false
        tvUserId.isVisible = false
        tvUserBio.isVisible = false
        cvAvatar.isVisible = false
        tvProjectsInfo.isVisible = false
        imgUserBlog.isVisible = false
        tvUserBlog.isVisible = false
        rvProjects.isVisible = false
    }
}