package com.example.pokedex.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.pokedex.R
import com.example.pokedex.databinding.EmptyViewBinding

class EmptyInfoView  @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr){
    private lateinit var binding: EmptyViewBinding

    init {
        binding = EmptyViewBinding.inflate( LayoutInflater.from(context), this, true)
        attrs.let {
            val typedArray = context.theme.obtainStyledAttributes(
                    attrs,
                    R.styleable.EmptyInfoView,
                    defStyleAttr,
                    defStyleRes
            )
            binding.emptyInfoText.text = typedArray.getString(R.styleable.EmptyInfoView_infoText)
            typedArray.recycle()
        }
    }
}