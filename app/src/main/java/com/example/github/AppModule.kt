package com.example.github

import android.content.Context
import androidx.room.Room
import com.example.github.database.AppDatabase
import com.example.github.database.dao.UsersDao
import com.example.github.remote.data.ApiUtils
import com.example.github.remote.data.GithubApi
import com.example.github.repository.GithubUserRepository
import com.example.github.repository.GithubUserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext

import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext applicationContext: Context) : AppDatabase{
        return Room.databaseBuilder(applicationContext,
            AppDatabase::class.java, "users_db").build()
    }

    @Provides
    fun provideUsersDao(db: AppDatabase): UsersDao{
        return db.usersDao()
    }

    @Provides
    fun provideRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl(ApiUtils.API_GitHub_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideApiService(retrofit: Retrofit): GithubApi {
        return retrofit.create(GithubApi::class.java)
    }

    @Provides
    @Singleton
    fun provideGithubUserRepository(usersDao: UsersDao, githubApi: GithubApi):GithubUserRepository{
        return GithubUserRepositoryImpl(usersDao, githubApi)
    }
}