package presentation.screen.form

sealed class FormScreenState(val viewData: FormViewData) {
    class Initial(data: FormViewData = FormViewData()) : FormScreenState(data)
    class FillingForm(data: FormViewData) : FormScreenState(data)
    class Success(data: FormViewData) : FormScreenState(data)
    class Error(data: FormViewData) : FormScreenState(data)

    fun clone(viewData: FormViewData) = when (this) {
        is Initial -> Initial(viewData)
        is FillingForm -> FillingForm(viewData)
        is Success -> Success(viewData)
        is Error -> Error(viewData)
    }
}