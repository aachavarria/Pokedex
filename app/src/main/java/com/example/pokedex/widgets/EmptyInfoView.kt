package com.example.pokedex.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.pokedex.R
import com.example.pokedex.databinding.FragmentEmptyStateBinding
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class EmptyInfoView  @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr){
    private var binding: FragmentEmptyStateBinding =
        FragmentEmptyStateBinding.inflate( LayoutInflater.from(context), this)

    private val clicksAcceptor = PublishSubject.create<Unit>()

    val itemClicked: Observable<Unit> = clicksAcceptor.hide()

    init {
        attrs.let {
            val typedArray = context.theme.obtainStyledAttributes(
                    attrs,
                    R.styleable.EmptyInfoView,
                    defStyleAttr,
                    defStyleRes
            )
            val subtitle = typedArray.getString(R.styleable.EmptyInfoView_subtitle)
            val button = typedArray.getString(R.styleable.EmptyInfoView_button)

            binding.title.text = typedArray.getString(R.styleable.EmptyInfoView_title)
            subtitle?.let {
                binding.subtitle.text = it
                binding.subtitle.visibility = View.VISIBLE
            }
            button?.let {
                binding.button.text = it
                binding.button.visibility = View.VISIBLE
                binding.button.setOnClickListener {
                    clicksAcceptor.onNext(Unit)
                }
            }

            typedArray.recycle()
        }
    }
}