package com.example.myapplication.model

import com.example.myapplication.NavigationDestination

sealed class ButtonAction {
    object CloseButton : ButtonAction()
    object FavoriteButton : ButtonAction()
    data class NavigateButton(val navigateTo: NavigationDestination) : ButtonAction()
}
