package com.dicoding.habitapp.ui.list

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.dicoding.habitapp.R
import com.dicoding.habitapp.data.Habit
import com.dicoding.habitapp.setting.SettingsActivity
import com.dicoding.habitapp.ui.ViewModelFactory
import com.dicoding.habitapp.ui.add.AddHabitActivity
import com.dicoding.habitapp.ui.detail.DetailHabitActivity
import com.dicoding.habitapp.ui.random.RandomHabitActivity
import com.dicoding.habitapp.utils.Event
import com.dicoding.habitapp.utils.HABIT_ID
import com.dicoding.habitapp.utils.HabitSortType
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class HabitListActivity : AppCompatActivity() {

    private lateinit var recycler: RecyclerView
    private lateinit var viewModel: HabitListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_habit_list)
        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            val addIntent = Intent(this, AddHabitActivity::class.java)
            startActivity(addIntent)
        }

        //TODO 6 : Initiate RecyclerView with LayoutManager
        recycler = findViewById(R.id.rv_habit)
        recycler.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        initAction()

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory).get(HabitListViewModel::class.java)

        //TODO 7 : Submit pagedList to adapter and add intent to detail
        viewModel.habits.observe(this) { habits ->
            showRecyclerView(habits)
        }

        viewModel.snackbarText.observe(this) {
            showSnackBar(it)
        }
    }

    //TODO 15 : Fixing bug : Menu not show and SnackBar not show when list is deleted using swipe
    private fun showSnackBar(eventMessage: Event<Int>) {
        val message = eventMessage.getContentIfNotHandled() ?: return
        Snackbar.make(
            findViewById(R.id.coordinator_layout),
            getString(message),
            Snackbar.LENGTH_SHORT
        ).setAction("Undo"){
            viewModel.insert(viewModel.undo.value?.getContentIfNotHandled() as Habit)
        }.show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_random -> {
                val intent = Intent(this@HabitListActivity, RandomHabitActivity::class.java)
                startActivity(intent)
            }

            R.id.action_filter -> {
                showFilteringPopUpMenu()
            }

            R.id.action_settings -> {
                val intent = Intent(this@HabitListActivity, SettingsActivity::class.java)
                startActivity(intent)
            }
        }
        return true
    }

    private fun showFilteringPopUpMenu() {
        val view = findViewById<View>(R.id.action_filter) ?: return
        PopupMenu(this, view).run {
            menuInflater.inflate(R.menu.filter_habits, menu)

            setOnMenuItemClickListener {
                viewModel.filter(
                    when (it.itemId) {
                        R.id.minutes_focus -> HabitSortType.MINUTES_FOCUS
                        R.id.title_name -> HabitSortType.TITLE_NAME
                        else -> HabitSortType.START_TIME
                    }
                )
                true
            }
            show()
        }
    }

    private fun initAction() {
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                return makeMovementFlags(0, ItemTouchHelper.RIGHT)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val habit = (viewHolder as HabitAdapter.HabitViewHolder).getHabit
                viewModel.deleteHabit(habit)
            }

        })
        itemTouchHelper.attachToRecyclerView(recycler)
    }

    private fun showRecyclerView(habit: PagedList<Habit>) {
        val habitAdapter = HabitAdapter {
            val intent = Intent(this@HabitListActivity, DetailHabitActivity::class.java)
            intent.putExtra(HABIT_ID, it.id)
            startActivity(intent)
        }

        recycler.adapter = habitAdapter
        habitAdapter.submitList(habit)
    }
}
