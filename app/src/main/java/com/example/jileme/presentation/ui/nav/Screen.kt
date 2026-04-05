package com.example.jileme.presentation.ui.nav

sealed class Screen(val route: String) {
    data object ModuleJie : Screen("module_jie")
    data object ModuleChou : Screen("module_chou")
    data object ModuleLa : Screen("module_la")
    data object Profile : Screen("profile")
}
