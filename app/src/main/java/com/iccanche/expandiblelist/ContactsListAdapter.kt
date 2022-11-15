package com.iccanche.expandiblelist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import com.iccanche.expandiblelist.databinding.ContactHeaderRowBinding
import com.iccanche.expandiblelist.databinding.ContactItemRowBinding

class ContactsListAdapter constructor(
    context: Context,
    private val sectionList: List<String>,
    private val dataList: Map<String, List<String>>
) : BaseExpandableListAdapter() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private lateinit var groupBinding: ContactHeaderRowBinding
    private lateinit var itemBinding: ContactItemRowBinding

    override fun getGroupCount(): Int {
        return sectionList.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        val currentGroup = this.dataList[sectionList[groupPosition]] ?: return 0
        return currentGroup.size
    }

    override fun getGroup(groupPosition: Int): Any {
        return sectionList[groupPosition]
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        val key = this.dataList[sectionList[groupPosition]] ?: return Any()
        return key[childPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        view: View?,
        parent: ViewGroup?
    ): View {
        var convertView = view
        val holder: GroupViewHolder
        if (convertView == null) {
            groupBinding = ContactHeaderRowBinding.inflate(inflater)
            convertView = groupBinding.root
            holder = GroupViewHolder()
            holder.sectionTitle = groupBinding.sectionHeaderLabel
            holder.counterText = groupBinding.sectionHeaderCountLabel
            holder.arrowIcon = groupBinding.arrowImageView
            convertView.tag = holder
        } else {
            holder = convertView.tag as GroupViewHolder
        }
        holder.sectionTitle?.text = getGroup(groupPosition) as String
        val counter = dataList[sectionList[groupPosition]]?.size
        holder.counterText?.text = String.format("(%s)", counter)
        holder.arrowIcon?.isVisible = counter != null && counter > 0
        holder.arrowIcon?.setImageResource(if (isExpanded) R.drawable.ic_up else R.drawable.ic_down)
        return convertView
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        view: View?,
        parent: ViewGroup?
    ): View {
        var convertView = view
        val holder: ItemViewHolder
        if (convertView == null) {
            itemBinding = ContactItemRowBinding.inflate(inflater)
            convertView = itemBinding.root
            holder = ItemViewHolder()
            holder.contactName = itemBinding.contactNameLabel
            convertView.tag = holder
        } else {
            holder = convertView.tag as ItemViewHolder
        }
        val name = getChild(groupPosition, childPosition) as String
        holder.contactName?.text = name
        return convertView
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

    inner class GroupViewHolder {
        internal var sectionTitle: TextView? = null
        internal var counterText: TextView? = null
        internal var arrowIcon: ImageView? = null
    }

    inner class ItemViewHolder {
        internal var contactName: TextView? = null
    }

}