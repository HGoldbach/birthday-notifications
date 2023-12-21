package com.example.birthdayapp.utils



abstract class TextFieldErrorType(val text: String) {

    object None : TextFieldErrorType(
        text = ""
    )

    object EmptyField : TextFieldErrorType(
        text = "Textfield Empty"
    )

}

enum class TextFieldType {
    Default, Name, Date
}