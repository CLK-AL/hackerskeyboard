/*
 * Copyright (C) 2024 Hacker's Keyboard
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.pocketworkstation.pckeyboard

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Unified emoji and symbol picker view.
 * Provides search, category navigation, and grid display.
 */
class EmojiPickerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    companion object {
        private const val TAG = "HK/EmojiPicker"
        private const val GRID_COLUMNS = 8
        private const val RECENT_MAX = 32
    }

    interface OnEmojiSelectedListener {
        fun onEmojiSelected(emoji: String)
    }

    private var listener: OnEmojiSelectedListener? = null
    private val parser: EmojiParser by lazy { EmojiParser.getInstance(context) }

    private lateinit var searchBar: EditText
    private lateinit var categoryTabs: HorizontalScrollView
    private lateinit var categoryContainer: LinearLayout
    private lateinit var breadcrumbBar: LinearLayout
    private lateinit var emojiGrid: RecyclerView
    private lateinit var adapter: EmojiGridAdapter

    private var currentGroup: String? = null
    private var currentSubgroup: String? = null
    private var currentQuery: String? = null

    private val recentEmoji = mutableListOf<String>()

    init {
        orientation = VERTICAL
        setupViews()
        loadCategories()
        showAllEmoji()
    }

    private fun setupViews() {
        val density = resources.displayMetrics.density
        val padding = (8 * density).toInt()

        // Search bar
        searchBar = EditText(context).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT).apply {
                setMargins(padding, padding / 2, padding, padding / 2)
            }
            hint = "Search emoji & symbols..."
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
            setPadding(padding, padding / 2, padding, padding / 2)
            setBackgroundColor(Color.parseColor("#33FFFFFF"))
            setSingleLine(true)
            addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    currentQuery = s?.toString()?.takeIf { it.isNotBlank() }
                    updateResults()
                }
            })
        }
        addView(searchBar)

        // Breadcrumb bar
        breadcrumbBar = LinearLayout(context).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT).apply {
                setMargins(padding, 0, padding, 0)
            }
            orientation = HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            visibility = View.GONE
        }
        addView(breadcrumbBar)

        // Category tabs
        categoryTabs = HorizontalScrollView(context).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
            isHorizontalScrollBarEnabled = false
        }
        categoryContainer = LinearLayout(context).apply {
            layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
            orientation = HORIZONTAL
            setPadding(padding / 2, padding / 2, padding / 2, padding / 2)
        }
        categoryTabs.addView(categoryContainer)
        addView(categoryTabs)

        // Emoji grid
        emojiGrid = RecyclerView(context).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, 0, 1f)
            layoutManager = GridLayoutManager(context, GRID_COLUMNS)
            setPadding(padding / 2, 0, padding / 2, 0)
            clipToPadding = false
        }
        adapter = EmojiGridAdapter { emoji ->
            listener?.onEmojiSelected(emoji)
            addToRecent(emoji)
        }
        emojiGrid.adapter = adapter
        addView(emojiGrid)
    }

    private fun loadCategories() {
        categoryContainer.removeAllViews()

        val density = resources.displayMetrics.density
        val padding = (8 * density).toInt()
        val tabHeight = (36 * density).toInt()

        // Recent tab
        addCategoryTab("Recent", "\u23F1") { // Clock icon
            showRecent()
        }

        // All emoji/symbols tab
        addCategoryTab("All", "\u2606") { // Star icon
            clearFilters()
            showAllEmoji()
        }

        // Group tabs
        for (group in parser.getGroups()) {
            val icon = getGroupIcon(group.name)
            addCategoryTab(group.name, icon) {
                selectGroup(group.name)
            }
        }
    }

    private fun addCategoryTab(name: String, icon: String, onClick: () -> Unit) {
        val density = resources.displayMetrics.density
        val padding = (6 * density).toInt()

        val tab = TextView(context).apply {
            layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
                setMargins(2, 0, 2, 0)
            }
            text = icon
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
            setPadding(padding, padding / 2, padding, padding / 2)
            gravity = Gravity.CENTER
            setOnClickListener { onClick() }
            contentDescription = name
        }
        categoryContainer.addView(tab)
    }

    private fun getGroupIcon(groupName: String): String {
        return when {
            groupName.contains("Smiley", ignoreCase = true) -> "\uD83D\uDE00"
            groupName.contains("People", ignoreCase = true) -> "\uD83D\uDC4B"
            groupName.contains("Animal", ignoreCase = true) -> "\uD83D\uDC36"
            groupName.contains("Food", ignoreCase = true) -> "\uD83C\uDF54"
            groupName.contains("Travel", ignoreCase = true) -> "\uD83D\uDE97"
            groupName.contains("Activity", ignoreCase = true) -> "\u26BD"
            groupName.contains("Object", ignoreCase = true) -> "\uD83D\uDCA1"
            groupName.contains("Symbol", ignoreCase = true) -> "\u2764"
            groupName.contains("Flag", ignoreCase = true) -> "\uD83C\uDFF3"
            groupName.contains("IPA", ignoreCase = true) -> "\u0259" // Schwa
            groupName.contains("Arrow", ignoreCase = true) -> "\u2192"
            groupName.contains("Math", ignoreCase = true) -> "\u221A"
            groupName.contains("Currency", ignoreCase = true) -> "\u20AC"
            groupName.contains("Latin", ignoreCase = true) -> "\u00E6"
            groupName.contains("Greek", ignoreCase = true) -> "\u03B1"
            groupName.contains("Cyrillic", ignoreCase = true) -> "\u0414"
            groupName.contains("Box", ignoreCase = true) -> "\u2500"
            groupName.contains("Block", ignoreCase = true) -> "\u2588"
            groupName.contains("Geometric", ignoreCase = true) -> "\u25A0"
            groupName.contains("Dingbat", ignoreCase = true) -> "\u2714"
            groupName.contains("Braille", ignoreCase = true) -> "\u2800"
            groupName.contains("Technical", ignoreCase = true) -> "\u2318"
            groupName.contains("Punctuation", ignoreCase = true) -> "\u201C"
            groupName.contains("Number", ignoreCase = true) -> "\u2460"
            groupName.contains("Letterlike", ignoreCase = true) -> "\u2122"
            groupName.contains("Misc", ignoreCase = true) -> "\u2600"
            else -> "\u2022" // Bullet
        }
    }

    private fun clearFilters() {
        currentGroup = null
        currentSubgroup = null
        searchBar.setText("")
        currentQuery = null
        updateBreadcrumbs()
    }

    private fun selectGroup(group: String) {
        currentGroup = group
        currentSubgroup = null
        searchBar.setText("")
        currentQuery = null
        updateResults()
        updateBreadcrumbs()
    }

    private fun selectSubgroup(subgroup: String) {
        currentSubgroup = subgroup
        updateResults()
        updateBreadcrumbs()
    }

    private fun updateBreadcrumbs() {
        breadcrumbBar.removeAllViews()

        if (currentGroup == null && currentSubgroup == null && currentQuery == null) {
            breadcrumbBar.visibility = View.GONE
            return
        }

        breadcrumbBar.visibility = View.VISIBLE
        val density = resources.displayMetrics.density

        // Home button
        val homeBtn = TextView(context).apply {
            text = "\u2302" // Home icon
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
            setPadding((8 * density).toInt(), 0, (8 * density).toInt(), 0)
            setOnClickListener {
                clearFilters()
                showAllEmoji()
            }
        }
        breadcrumbBar.addView(homeBtn)

        // Group
        if (currentGroup != null) {
            addBreadcrumbSeparator()
            val groupBtn = TextView(context).apply {
                text = currentGroup
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
                setTypeface(null, if (currentSubgroup == null) Typeface.BOLD else Typeface.NORMAL)
                setPadding((4 * density).toInt(), 0, (4 * density).toInt(), 0)
                setOnClickListener {
                    currentSubgroup = null
                    updateResults()
                    updateBreadcrumbs()
                }
            }
            breadcrumbBar.addView(groupBtn)

            // Show subgroup chips if we're at group level
            if (currentSubgroup == null) {
                val subgroups = parser.getSubgroupNames(currentGroup!!)
                if (subgroups.size > 1) {
                    addBreadcrumbSeparator()
                    for (subgroup in subgroups.take(5)) {
                        val chip = TextView(context).apply {
                            text = subgroup.replace("-", " ").take(12)
                            setTextSize(TypedValue.COMPLEX_UNIT_SP, 10f)
                            setPadding((6 * density).toInt(), (2 * density).toInt(), (6 * density).toInt(), (2 * density).toInt())
                            setBackgroundColor(Color.parseColor("#22FFFFFF"))
                            setOnClickListener { selectSubgroup(subgroup) }
                        }
                        val lp = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
                        lp.setMargins((2 * density).toInt(), 0, (2 * density).toInt(), 0)
                        chip.layoutParams = lp
                        breadcrumbBar.addView(chip)
                    }
                }
            }
        }

        // Subgroup
        if (currentSubgroup != null) {
            addBreadcrumbSeparator()
            val subgroupText = TextView(context).apply {
                text = currentSubgroup!!.replace("-", " ")
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
                setTypeface(null, Typeface.BOLD)
                setPadding((4 * density).toInt(), 0, (4 * density).toInt(), 0)
            }
            breadcrumbBar.addView(subgroupText)
        }

        // Query
        if (currentQuery != null) {
            addBreadcrumbSeparator()
            val queryText = TextView(context).apply {
                text = "\"$currentQuery\""
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
                setTypeface(null, Typeface.ITALIC)
                setPadding((4 * density).toInt(), 0, (4 * density).toInt(), 0)
            }
            breadcrumbBar.addView(queryText)
        }
    }

    private fun addBreadcrumbSeparator() {
        val sep = TextView(context).apply {
            text = " > "
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
            setTextColor(Color.parseColor("#88FFFFFF"))
        }
        breadcrumbBar.addView(sep)
    }

    private fun showAllEmoji() {
        val entries = parser.getAllFullyQualified()
        adapter.submitList(entries.map { it.emoji })
        Log.i(TAG, "Showing all ${entries.size} emoji/symbols")
    }

    private fun showRecent() {
        currentGroup = null
        currentSubgroup = null
        searchBar.setText("")
        currentQuery = null
        adapter.submitList(recentEmoji.toList())
        updateBreadcrumbs()
        Log.i(TAG, "Showing ${recentEmoji.size} recent emoji")
    }

    private fun updateResults() {
        val builder = parser.filter()

        currentGroup?.let { builder.group(it) }
        currentSubgroup?.let { builder.subgroup(it) }
        currentQuery?.let { builder.query(it) }

        val results = builder.results()
        adapter.submitList(results.map { it.emoji })
        Log.i(TAG, "Filter: group=$currentGroup, subgroup=$currentSubgroup, query=$currentQuery -> ${results.size} results")

        updateBreadcrumbs()
    }

    private fun addToRecent(emoji: String) {
        recentEmoji.remove(emoji)
        recentEmoji.add(0, emoji)
        while (recentEmoji.size > RECENT_MAX) {
            recentEmoji.removeAt(recentEmoji.size - 1)
        }
    }

    fun setOnEmojiSelectedListener(listener: OnEmojiSelectedListener) {
        this.listener = listener
    }

    fun setOnEmojiSelectedListener(block: (String) -> Unit) {
        this.listener = object : OnEmojiSelectedListener {
            override fun onEmojiSelected(emoji: String) = block(emoji)
        }
    }

    /**
     * RecyclerView adapter for emoji grid.
     */
    private class EmojiGridAdapter(
        private val onEmojiClick: (String) -> Unit
    ) : RecyclerView.Adapter<EmojiGridAdapter.ViewHolder>() {

        private var items = listOf<String>()

        fun submitList(newItems: List<String>) {
            items = newItems
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val density = parent.resources.displayMetrics.density
            val size = (40 * density).toInt()

            val textView = TextView(parent.context).apply {
                layoutParams = ViewGroup.LayoutParams(size, size)
                gravity = Gravity.CENTER
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 24f)
            }
            return ViewHolder(textView)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val emoji = items[position]
            (holder.itemView as TextView).apply {
                text = emoji
                setOnClickListener { onEmojiClick(emoji) }
            }
        }

        override fun getItemCount(): Int = items.size

        class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
    }
}
