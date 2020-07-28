package com.mohamed.moviesapp.di.modules

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class GlideModuleGlobal {

    @Provides
    @Singleton
    fun provideprovideGlideInstance(@ApplicationContext context: Context): RequestManager {
        return return Glide.with(context)
    }

}