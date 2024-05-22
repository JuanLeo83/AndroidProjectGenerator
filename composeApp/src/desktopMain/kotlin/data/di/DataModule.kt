package data.di

import data.repository.FileRepositoryImpl
import data.repository.GitRepositoryImpl
import data.source.local.FileSystemSource
import data.source.local.GitSource
import domain.repository.FileRepository
import domain.repository.GitRepository
import org.koin.dsl.module

val dataModule = module {
    single<GitRepository> { GitRepositoryImpl(get()) }
    single<FileRepository> { FileRepositoryImpl(get()) }

    single { GitSource() }
    single { FileSystemSource() }
}