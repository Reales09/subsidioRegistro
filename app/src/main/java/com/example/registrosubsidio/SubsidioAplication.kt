package com.example.registrosubsidio

import android.app.Application
import com.example.registrosubsidio.data.SubsidioRoomDatabase

class SubsidioAplication : Application() {

    val database: SubsidioRoomDatabase by lazy { SubsidioRoomDatabase.getDatabase(this) }
}