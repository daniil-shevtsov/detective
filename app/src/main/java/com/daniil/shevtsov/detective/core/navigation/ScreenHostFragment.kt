package com.daniil.shevtsov.detective.core.navigation

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.daniil.shevtsov.detective.R
import com.daniil.shevtsov.detective.application.DetectiveApplication
import com.daniil.shevtsov.detective.databinding.FragmentMainBinding
import com.daniil.shevtsov.detective.feature.game.presentation.FoodItemCard
import com.daniil.shevtsov.detective.feature.game.presentation.LongPressDraggable
import com.daniil.shevtsov.detective.feature.game.presentation.PersonListContainer
import com.google.accompanist.insets.ProvideWindowInsets
import javax.inject.Inject

class ScreenHostFragment : Fragment(R.layout.fragment_main) {

    private val binding by viewBinding(FragmentMainBinding::bind)

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: ScreenHostViewModel by viewModels { viewModelFactory }
//    private val detectiveImperativeShell: DetectiveImperativeShell by viewModels { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (context.applicationContext as DetectiveApplication)
            .appComponent
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            composeView.setContent {
                ProvideWindowInsets {
                    LongPressDraggable(modifier = Modifier.fillMaxSize()) {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(horizontal = 10.dp)
                        ) {
                            items(items = foodList) { food ->
                                FoodItemCard(foodItem = food)
                            }
                        }
                        PersonListContainer()
                    }
//                    ScreenHostComposable(viewModel = viewModel)
//                    DetectiveScreenComposable(imperativeShell = detectiveImperativeShell)
                }
            }
        }
    }
}

data class FoodItem(val id: Int, val name: String, val price: Double)

val foodList = listOf(
    FoodItem(1, "Pizza", 20.0),
    FoodItem(2, "French toast", 10.05),
    FoodItem(3, "Chocolate cake", 12.99),
)

data class Person(val id: Int, val name: String)

val persons = listOf(
    Person(1, "Jhone"),
    Person(2, "Eyle"),
    Person(3, "Tommy"),
)
