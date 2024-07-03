package campus.tech.kakao.map.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import campus.tech.kakao.map.R
import campus.tech.kakao.map.models.SearchResult
import campus.tech.kakao.map.view_models.SearchActivityViewModel
import campus.tech.kakao.map.views.adapters.SearchResultAdapter

class SearchResultFragment : Fragment() {
    lateinit var searchResultRecyclerView: RecyclerView
    lateinit var noResultHelpText: View
    private val viewModel by activityViewModels<SearchActivityViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_result, container, false)
    }

    private fun setInitialValueToAdapter(){
        viewModel.searchResult.value?.let{
            (searchResultRecyclerView.adapter as? SearchResultAdapter)?.updateResult(it)
        }
    }

    private fun initiateRecyclerView(view: View) {
        searchResultRecyclerView = view.findViewById(R.id.list_search_result)

        searchResultRecyclerView.adapter =
            SearchResultAdapter(LayoutInflater.from(activity)) { item: SearchResult, _: Int ->
                viewModel.clickSearchResultItem(item)
            }
        searchResultRecyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
    }

    private fun initiateSearchResultLiveDataObservation() {
        viewModel.searchResult.observe(viewLifecycleOwner) {
            (searchResultRecyclerView.adapter as? SearchResultAdapter)?.updateResult(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noResultHelpText = view.findViewById(R.id.text_no_result)

        initiateRecyclerView(view)
        initiateSearchResultLiveDataObservation()
        setInitialValueToAdapter()
    }
}