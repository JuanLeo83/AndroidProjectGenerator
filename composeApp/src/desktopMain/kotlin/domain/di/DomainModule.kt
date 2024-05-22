package domain.di

import domain.usecase.CloneGitRepositoryUseCase
import domain.usecase.DeleteGitFolderUseCase
import domain.usecase.ReplaceApplicationFileNameUseCase
import domain.usecase.ReplacePackageFolderNameUseCase
import domain.usecase.ReplacePackageNameUseCase
import domain.usecase.ValidatePackageNameUseCase
import domain.usecase.ValidateProjectNameUseCase
import domain.usecase.ValidateTemplateUrlUseCase
import org.koin.dsl.module

val domainModule = module {
    single { CloneGitRepositoryUseCase(get(), get()) }
    single { DeleteGitFolderUseCase(get(), get()) }
    single { ReplacePackageNameUseCase(get(), get()) }
    single { ReplacePackageFolderNameUseCase(get(), get())}
    single { ReplaceApplicationFileNameUseCase(get()) }
    single { ValidatePackageNameUseCase() }
    single { ValidateProjectNameUseCase() }
    single { ValidateTemplateUrlUseCase() }
}