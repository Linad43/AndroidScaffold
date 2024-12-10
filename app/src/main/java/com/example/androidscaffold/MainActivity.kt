package com.example.androidscaffold

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.androidscaffold.ui.theme.PurpleGrey80
import com.example.androidscaffold.ui.theme.Violet
import com.example.androidscaffold.ui.theme.VioletLight
import kotlin.system.exitProcess

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Scaff()
//            MainFun()
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Preview(showBackground = true)
@Composable
fun Scaff() {
    val mContext = LocalContext.current
    val textInput = rememberSaveable() { mutableStateOf("") }
    val arr = rememberSaveable { mutableListOf<String>() }
    Scaffold(
        topBar = @Composable {
            TopBar(arr, mContext)
        },
        bottomBar = {
            BottomBar(arr, mContext)
        },
        floatingActionButton = {
            FAB(textInput, arr, mContext)
        }
    ) { paddingValues ->
        InnerScaff(paddingValues, textInput, arr)
    }
}

@Composable
private fun InnerScaff(
    paddingValues: PaddingValues,
    textInput: MutableState<String>,
    arr: MutableList<String>,
) {
    val openDialog = remember { mutableStateOf(false) }
    var removeElement = rememberSaveable() { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PurpleGrey80)
            .padding(paddingValues)
    )
    {
        TextField(
            value = textInput.value,
            textStyle = TextStyle(fontSize = 24.sp),
            onValueChange = {
                textInput.value = it
            },
            modifier = Modifier
                .padding(10.dp)
                .border(2.dp, Color.Black)
                .fillMaxWidth()
        )
        LazyColumn(
            Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            items(arr) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(3.dp)
                        .background(Color.White, RoundedCornerShape(50.dp))
                ) {
                    Text(
                        text = it,
                        modifier = Modifier
                            .padding(start = 20.dp)
                    )
                    Column(
                        horizontalAlignment = Alignment.End,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        IconButton(
                            onClick = {
//                                arr.remove(it)
                                removeElement.value = it
                                openDialog.value = true
                            }
                        ) {
                            Icon(
                                Icons.Filled.Delete,
                                contentDescription = "",
                                modifier = Modifier
                                    .size(40.dp)
                            )
                        }
                    }
                }
            }
        }
    }
    if (openDialog.value) {
        DialogRemove(
            element = removeElement.value,
            onDismissRequest = { openDialog.value = false },
            onConfirmation = { element ->
                openDialog.value = false
                arr.remove(element)
            }
        )
    }
}

@Composable
private fun DialogRemove(
    element: String,
    onDismissRequest: () -> Unit,
    onConfirmation: (element: String) -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .padding(20.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Image(
                    painter = painterResource(R.drawable.stop),
                    contentDescription = "Delete",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .height(150.dp)
                )
                Text(
                    text = "Подтвердите удвление",
                    modifier = Modifier
                        .padding(10.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = { onDismissRequest() },
                        modifier = Modifier
                            .padding(10.dp)
                    ) {
                        Text("Отмена")
                    }
                    Button(
                        onClick = { onConfirmation(
                            element
                        ) },
                        modifier = Modifier
                            .padding(10.dp)
                    ) {
                        Text("Подтвердить")
                    }
                }
            }
        }
    }
}

@Composable
private fun FAB(
    textInput: MutableState<String>,
    arr: MutableList<String>,
    mContext: Context,
) {
    FloatingActionButton(
        content = {
            Icon(Icons.Filled.Add, contentDescription = "Add")
        },
        onClick = {
            if (textInput.value != "") {
                arr.add(textInput.value)
                textInput.value = ""
            } else {
                Toast.makeText(
                    mContext,
                    "Поле ввода не может быть пустым",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    )
}

@Composable
private fun BottomBar(arr: MutableList<String>, mContext: Context) {
    BottomAppBar(
        containerColor = VioletLight,
        contentColor = Color.Black,
    ) {
        Icon(
            Icons.Filled.Send,
            contentDescription = "Send",
            modifier = Modifier
                .padding(20.dp)
                .clickable {
                    if (arr.isNotEmpty()) {
                        Toast
                            .makeText(
                                mContext,
                                "Сообщение отправлено ${arr.last()}",
                                Toast.LENGTH_SHORT
                            )
                            .show()
                    }
                }
        )
        Spacer(
            modifier = Modifier
                .weight(1F)
        )
        Icon(
            Icons.Filled.Create,
            contentDescription = "Edit",
            modifier = Modifier
                .padding(20.dp)
                .clickable {
                    if (arr.isNotEmpty()) {
                        Toast
                            .makeText(
                                mContext,
                                "Контакт отредактирован ${arr.last()}",
                                Toast.LENGTH_SHORT
                            )
                            .show()
                    }
                }
        )
    }
}

@ExperimentalMaterial3Api
@Composable
private fun TopBar(arr: MutableList<String>, mContext: Context) {
    TopAppBar(
        colors = topAppBarColors(
            containerColor = Violet,
            titleContentColor = Color.White
        ),
        title = {
            Row {
                Icon(
                    Icons.Filled.Menu,
                    contentDescription = "Menu",
                    modifier = Modifier
                        .padding(end = 10.dp, start = 5.dp)
                )
                Text(text = stringResource(R.string.app_name))
                Spacer(
                    modifier = Modifier
                        .weight(1F)
                )
                Icon(
                    Icons.Filled.Phone,
                    contentDescription = "Phone",
                    modifier = Modifier
                        .padding(end = 10.dp, start = 5.dp)
                        .clickable {
                            if (arr.isNotEmpty()) {
                                Toast
                                    .makeText(
                                        mContext,
                                        "Звонок совершен ${arr.last()}",
                                        Toast.LENGTH_SHORT
                                    )
                                    .show()
                            }
                        }
                )
                Icon(
                    Icons.Filled.Close,
                    contentDescription = "Exit",
                    modifier = Modifier
                        .padding(end = 10.dp, start = 5.dp)
                        .clickable {
                            exitProcess(-1)
                        }
                )
            }
        }
    )
}