package com.example.vkeducationproject

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*

class SecondActivity: ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContent{
            val undefined = "<Пустой ввод>"
            var userInput = intent.getStringExtra("user_message")
            if (userInput == ""){
                userInput = undefined
            }
            ShowUser(userInput ?: undefined )
        }

    }
}

@Composable
fun ShowUser(userInput: String){
    val context = LocalContext.current
    Column(
        modifier = Modifier.padding(30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text="User input: $userInput",
            fontSize = 20.sp,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16))
                .border(2.dp, Color.Black, RoundedCornerShape(16))
                .padding(10.dp)
        )
        Button(
            modifier = Modifier.padding(top=10.dp).fillMaxWidth(0.8f),
            onClick = {
                val message = Intent(context, MainActivity::class.java)
                context.startActivity(message)
            }
        ){
            Text("Вернуться назад", fontSize = 20.sp, textAlign = TextAlign.Center)
        }
    }
}