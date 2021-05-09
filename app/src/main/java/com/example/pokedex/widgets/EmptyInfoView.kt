package com.example.pokedex.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.pokedex.R
import com.example.pokedex.databinding.FragmentEmptyStateBinding

class EmptyInfoView  @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr){
    private var binding: FragmentEmptyStateBinding =
        FragmentEmptyStateBinding.inflate( LayoutInflater.from(context), this, true)

    init {
        attrs.let {
            val typedArray = context.theme.obtainStyledAttributes(
                    attrs,
                    R.styleable.EmptyInfoView,
                    defStyleAttr,
                    defStyleRes
            )
            binding.title.text = typedArray.getString(R.styleable.EmptyInfoView_title)
            binding.subtitle.text = typedArray.getString(R.styleable.EmptyInfoView_subtitle)
            binding.button.text = typedArray.getString(R.styleable.EmptyInfoView_button)
            typedArray.recycle()
        }
    }
}