package presentation.di

import org.koin.dsl.module
import presentation.screen.form.FormMapper
import presentation.screen.form.FormViewModel

val presentationModule = module {
    factory { FormViewModel(
        validateProjectNameUseCase = get(),
        validatePackageNameUseCase = get(),
        validateTemplateUrlUseCase = get(),
        cloneGitRepositoryUseCase = get(),
        mapper = get()
    ) }

    single { FormMapper() }
}