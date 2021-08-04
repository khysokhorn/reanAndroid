package com.sokhorn.degger.car

import com.sokhorn.degger.MainActivity
import dagger.Component

@Component
interface AppComponent {
    fun getCar(): Car
    fun injection(mainActivity: MainActivity)
}