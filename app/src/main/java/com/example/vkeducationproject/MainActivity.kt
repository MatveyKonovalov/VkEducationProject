package com.example.vkeducationproject

import android.content.Intent
import android.content.Intent.*
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            Welcome()
        }
    }
}

@Composable
fun Welcome(){
    val userMessage = rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current
    val colorErrorUnf = remember { mutableStateOf(Color(0xffeeeeee)) }
    val colorErrorF = remember{mutableStateOf(Color.White)}
    val messageError = remember { mutableStateOf("Введите номер вашего друга или сообщение") }

    // Уведомление об ошибке
    fun errorInput(){
        // Обработка некорректного ввода
        userMessage.value = ""
        messageError.value = "Некорректный ввод"
        colorErrorUnf.value = Color.Red
        colorErrorF.value = Color.Red
    }

    // Меняем как было, если ввод стал корректным
    fun fixInput(){
        colorErrorUnf.value = Color.White
        colorErrorF.value = Color.White
    }

    // Вывод виджетов на экран
    Column(
        modifier = Modifier.fillMaxSize().padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        OutlinedTextField(
            value = userMessage.value,
            onValueChange = {userMessage.value = it},
            textStyle = TextStyle(fontSize = 20.sp, textAlign = TextAlign.Center),
            modifier= Modifier
                .clip(RoundedCornerShape(3))
                .padding(top=30.dp)
                .fillMaxWidth(0.8f),
            placeholder = {Text(text=messageError.value
                , fontSize = 20.sp
                , textAlign = TextAlign.Center)},
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = colorErrorUnf.value,
                unfocusedTextColor = Color(0xff888888),
                focusedContainerColor = colorErrorF.value,
                focusedTextColor = Color.Black,
                errorSupportingTextColor = Color.Red
            )
        )

        Button(
            modifier = Modifier.padding(top=10.dp).fillMaxWidth(0.8f),
            onClick={
            val intent = Intent(context, SecondActivity::class.java)
            intent.putExtra("user_message", userMessage.value)
            context.startActivity(intent) },
            ) {Text("Перейти на вторую Activity", fontSize = 20.sp) }

        Button(
            modifier = Modifier.padding(top=10.dp).fillMaxWidth(0.8f),
            onClick={
                if (checkCorrectNum(userMessage.value)){
                    fixInput()

                    // Делаем Uri(tel:...) номера, чтобы система поняла, что это телефон
                    val num = Uri.parse("tel:${userMessage.value.trim()}")

                    // Запускаем звонилку
                    val intent = Intent(ACTION_DIAL, num)
                    try{
                        context.startActivity(intent)
                    }
                    catch (e: Exception){
                        Toast.makeText(context, "Ошибка: ${e.message}",
                            Toast.LENGTH_SHORT).show()
                    }

                }
                else{
                    errorInput()
                }
                    },
        ) {Text("Позвонить другу", fontSize = 20.sp, textAlign = TextAlign.Center) }

        Button(
            modifier = Modifier.padding(top=10.dp).fillMaxWidth(0.8f),
            onClick = {
                // Если ввод пустой
                if (userMessage.value.length > 0){
                    fixInput()

                    val message = Intent(ACTION_SEND)
                    message.type = "text/plain"
                    message.putExtra(EXTRA_TEXT, userMessage.value)
                    try{
                        context.startActivity(message)
                    }
                    catch(e: Exception){
                        Toast.makeText(context, "Ошибка: ${e.message}",
                                Toast.LENGTH_SHORT).show()
                    }
                }
                else{
                    errorInput()
                }

            }
        ){
            Text("Поделиться текстом", fontSize = 20.sp, textAlign = TextAlign.Center)
        }

    }

}


fun checkCorrectNum(num: String): Boolean{
    // Проверка корректности для российских номеров
    var onlyDigits = ""
    num.forEach {
        if (it.isDigit()) onlyDigits += it
        else if(it.isLetter()){
            return false
        } }
    // Номер состоит из 11-ти цифр и начинается либо с 7, либо с 8
    if (onlyDigits.length == 11 &&
        (onlyDigits.getOrNull(0) == '8' || onlyDigits.getOrNull(0) == '7')){
        return true
    }
    return false
}