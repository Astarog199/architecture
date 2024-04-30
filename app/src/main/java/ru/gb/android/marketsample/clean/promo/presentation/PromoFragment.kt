package ru.gb.android.marketsample.clean.promo.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.gb.android.marketsample.databinding.FragmentPromoBinding
import ru.gb.android.marketsample.clean.ServiceLocator
import ru.gb.android.marketsample.clean.promo.data.PromoEntity
import ru.gb.android.marketsample.clean.promo.presentation.adapter.PromoAdapter

class PromoFragment : Fragment() {


    private var _binding: FragmentPromoBinding? = null
    private val binding get() = _binding!!

    private val adapter = PromoAdapter()
    private val viewModel: PromoViewModel by viewModels(
        factoryProducer = { ServiceLocator.provideViewModelFactory() }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPromoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView

        subscribeUI()

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun subscribeUI() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.items.collect { items: List<PromoEntity> ->
                        adapter.submitList(items)
                        showList()
                    }
                }

                launch {
                    viewModel.isError
                        .filter { isError -> isError }
                        .onEach {
                            Toast.makeText(
                                requireContext(),
                                "Error wile loading data",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        .collect()
                }

                launch {
                    viewModel.isLoading
                        .collect { isLoading ->
                            if (isLoading) {
                                showLoading()
                            } else {
                                showList()
                            }
                        }
                }
            }
        }
    }

    private fun showLoading() {
        hideAll()
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun showList() {
        hideAll()
        binding.recyclerView.visibility = View.VISIBLE
    }

    private fun hideAll() {
        binding.recyclerView.visibility = View.GONE
        binding.progressBar.visibility = View.GONE
        binding.message.visibility = View.GONE
        binding.swipeRefreshLayout.isRefreshing = false
    }
}