package kg.geektech.mouth8work1.presentation.task

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import kg.geektech.mouth8work1.R
import kg.geektech.mouth8work1.core.extentions.showToastShort
import kg.geektech.mouth8work1.databinding.ActivityTaskBinding
import kg.geektech.mouth8work1.domain.model.ShopItem
import kg.geektech.mouth8work1.presentation.main.MainViewModel
import kg.geektech.mouth8work1.presentation.shopItemAdapter.ShopItemAdapter

class TaskActivity : AppCompatActivity(R.layout.activity_task) {
    //делегаты биндинг
    private val binding by viewBinding(ActivityTaskBinding::bind, R.id.task_container)
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: ShopItemAdapter
    private lateinit var launcher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initLauncher()
        initViewModel()
        initRecyclerView()
        initListeners()
        initObservers()
    }

    private fun initObservers() {
        viewModel.shopListLD.observe(this) {
            adapter.submitList(it)// notifydatasetchanged это обновление адаптера
        }
    }

    private fun initListeners() {
        adapter.onShopItemClickListener = {
            viewModel.deleteShopItem(it)
        }
        adapter.onShopItemLongClickListener = {
            viewModel.editShopItem(it)
            showToastShort("Изменено состояние объекта ${it.enabled}")
        }
        binding.fab.setOnClickListener {
            val intent = Intent(this@TaskActivity, SecondTaskActivity::class.java)
            launcher.launch(intent)
        }
    }

    private fun initRecyclerView() {
        adapter = ShopItemAdapter()
        binding.taskRecycler.adapter = adapter
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
    }

    private fun initLauncher() {
        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val userText = it.data?.getStringExtra(USER_KEY)
                if (userText != null) {
                    viewModel.addShopItem(ShopItem(userText, 1, enabled = false))
                }
            }
        }
    }

    companion object {
        const val USER_KEY = "User"
    }
}