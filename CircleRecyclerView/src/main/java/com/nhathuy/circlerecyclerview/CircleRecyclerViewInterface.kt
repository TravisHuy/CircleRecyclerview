package com.nhathuy.circlerecyclerview

interface CircleRecyclerViewInterface {
    fun setCenterPosition(x:Float,y:Float)
    fun toggleVisibility()
    fun expand()
    fun collapse()
    var currentRotation : Float
    var radius : Float
    var isExpanded : Boolean
}