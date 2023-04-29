package com.daniil.shevtsov.detective.core.navigation

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.daniil.shevtsov.detective.R
import com.daniil.shevtsov.detective.application.DetectiveApplication
import com.daniil.shevtsov.detective.databinding.FragmentMainBinding
import com.daniil.shevtsov.detective.feature.main.view.ScreenHostComposable
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
                    ScreenHostComposable(viewModel = viewModel)
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
