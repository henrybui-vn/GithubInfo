package com.android.githubinfo.ui.adapter

import android.content.Context
import android.icu.text.DecimalFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.android.githubinfo.R
import com.android.githubinfo.databinding.ItemProjectListBinding
import com.android.githubinfo.model.ProjectInfo

typealias OnClickItemDetailsEvent = (ProjectInfo) -> Unit

class ProjectsAdapter(
    private val activity: Context,
    private val items: List<ProjectInfo>,
    val onClickDetails: OnClickItemDetailsEvent
) :
    RecyclerView.Adapter<ProjectsAdapter.ListViewHolder>() {

    inner class ListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val projectName = MutableLiveData<String>()
        val projectDescription = MutableLiveData<String>()
        val projectLanguage = MutableLiveData<String>()
        val projectWatchers = MutableLiveData<String>()
        val projectForks = MutableLiveData<String>()

        fun onClickDetails(): Unit =
            this@ProjectsAdapter.onClickDetails(items[layoutPosition])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val lifecycleOwner = activity as LifecycleOwner
        val binding = DataBindingUtil.inflate<ItemProjectListBinding>(
            LayoutInflater.from(activity),
            R.layout.item_project_list,
            parent,
            false
        )
        val vh = ListViewHolder(binding.root)

        binding.let {
            it.setLifecycleOwner(lifecycleOwner)
            it.vh = vh
        }

        vh.projectDescription.observe(lifecycleOwner, Observer {
            binding.tvProjectDescription.isVisible = it != "" && it != null
        })

        vh.projectLanguage.observe(lifecycleOwner, Observer {
            binding.tvProjectLanguage.isVisible = it != "" && it != null
        })

        vh.projectWatchers.observe(lifecycleOwner, Observer {
            binding.imgWatchers.isVisible = it != "0"
            binding.tvWatchers.isVisible = it != "0"
        })

        vh.projectForks.observe(lifecycleOwner, Observer {
            binding.imgForks.isVisible = it != "0"
            binding.tvForks.isVisible = it != "0"
        })

        return vh
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int): Unit = with(holder) {
        val project = items[position]
        projectName.value = project.name
        projectDescription.value = project.description
        projectLanguage.value = project.language
        projectWatchers.value = convertValue(project.watchers_count)
        projectForks.value = convertValue(project.forks_count)
    }

    private fun convertValue(value: Int): String {
        var convertedValue = value.toString()
        if (value in 1001..999999) {
            val tmp = value / 1000
            convertedValue = DecimalFormat(".#k").format(tmp)
        } else if (value > 999999) {
            val tmp = value / 1000000
            convertedValue = DecimalFormat(".#m").format(tmp)
        }
        return convertedValue
    }
}