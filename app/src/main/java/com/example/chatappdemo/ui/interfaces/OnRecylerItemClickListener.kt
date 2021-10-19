package com.example.chatappdemo.ui.interfaces

/*
* Recycler view interface for item click
* */
interface OnRecylerItemClickListener<T> {
    fun onViewItemClicked(obj: T, pos: Int)



}