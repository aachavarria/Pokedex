package com.example.pokedex.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pokedex.R

class TrainerFragment : Fragment(R.layout.fragment_trainer){
    companion object {
        fun newInstance(): TrainerFragment = TrainerFragment()
    }
}