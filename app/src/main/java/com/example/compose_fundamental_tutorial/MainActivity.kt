package com.example.compose_fundamental_tutorial

import android.os.Bundle
import android.util.Log
import android.widget.CheckBox
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.example.compose_fundamental_tutorial.ui.theme.Compose_fundamental_tutorialTheme
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Compose_fundamental_tutorialTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
//                    Container()
//                    BoxContainer()
//                    TextContainer()
//                    ShapeContainer()
//                    ButtonsContainer()
//                    CheckBoxContainer()
//                    MySnackBar()
                    TextFieldTest()
                }
            }
        }
    }
}

@Composable
fun TextFieldTest() {

    var userInput by remember { mutableStateOf(TextFieldValue()) }
    var numberInput by remember { mutableStateOf(TextFieldValue()) }
    var emailInput by remember { mutableStateOf(TextFieldValue()) }
    var pwInput by remember { mutableStateOf(TextFieldValue()) }

    var pwShow = remember { mutableStateOf(false) }

    val passwordResource: (Boolean) -> Int = {
        if (it) R.drawable.baseline_visibility_24 else R.drawable.baseline_visibility_off_24
    }

    Column(
        Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = userInput,
            singleLine = false,
            maxLines = 2,
            onValueChange = { newValue -> userInput = newValue } ,
            label = { Text(text = "????????? ??????") },
            placeholder = { Text(text = "????????? ?????????") }
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = numberInput,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            onValueChange = { newValue -> numberInput = newValue } ,
            label = { Text(text = "????????????") },
            placeholder = { Text(text = "010-xxxx-xxxx") }
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = emailInput,
            singleLine = true,
            leadingIcon = { Icon(imageVector = Icons.Default.Email , contentDescription = null) },
            trailingIcon = { IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.CheckCircle , contentDescription = null) }
                           },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            onValueChange = { newValue -> emailInput = newValue } ,
            label = { Text(text = "email") },
            placeholder = { Text(text = "xxx@xxx.xxx") }
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = pwInput,
            singleLine = true,
            leadingIcon = { Icon(imageVector = Icons.Default.Person , contentDescription = null) },
            trailingIcon = { IconButton(onClick = {
                Log.d("TAG", "???????????? visible ?????? ??????")
                pwShow.value = !pwShow.value
            }) {
                Icon(painter = painterResource(id = passwordResource(pwShow.value)), null) }
            },
            visualTransformation = if (pwShow.value) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            onValueChange = { newValue -> pwInput = newValue } ,
            label = { Text(text = "pass word") },
            placeholder = { Text(text = "??????????????? ??????????????????") }
        )
    }
}

@Composable
fun MySnackBar() {

    val snackbarHostState = remember { SnackbarHostState() }

    val coroutineScope = rememberCoroutineScope()

    val buttonTitle: (SnackbarData?) -> String = { snackbarData ->
        if (snackbarData != null) "????????? ?????????" else "????????? ????????????"
    }

    val buttonColor: (SnackbarData?) -> Color = { snackbarData ->
        if (snackbarData != null) Color.Green else Color.Red
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            colors = ButtonDefaults.buttonColors(
                backgroundColor = buttonColor(snackbarHostState.currentSnackbarData),
                contentColor = Color.White
            ),
            onClick = {
            Log.d("TAG", "????????? ??????")
                if(snackbarHostState.currentSnackbarData != null) {
                    Log.d("TAG", "????????? ??????")
                    snackbarHostState.currentSnackbarData?.dismiss()
                    return@Button
                }

            coroutineScope.launch {
                snackbarHostState.showSnackbar(
                    "?????? ??????",
                    "??????",
                    SnackbarDuration.Short
                ).let {
                    when (it) {
                        SnackbarResult.Dismissed -> Log.d("TAG", "????????? ??????")
                        SnackbarResult.ActionPerformed -> Log.d("TAG", "????????? ?????? ?????? ??????")
                    }
                }
            }   //coroutineScope
        }) {
            Text(text = buttonTitle(snackbarHostState.currentSnackbarData))
        }
        // ???????????? ???????????? ??????
        SnackbarHost(hostState = snackbarHostState, modifier = Modifier.align(Alignment.BottomCenter))
    }
}

// arrangement ????????? ??????????????? ????????????, Row, Column ?????? ???????????? ???????????? ???????????? ????????? Composable?????? ????????? ???????????? ????????? ??? ??????
// alignment gravity ??????
@Composable
fun Container() {
//    Row(
//        modifier = Modifier
//            .background(Color.White)
//            .fillMaxSize(),
//        horizontalArrangement = Arrangement.SpaceBetween,
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        DummyBox()
//        DummyBox()
//        DummyBox()
//    }

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.End
    ) {
        DummyBox()
        DummyBox()
        DummyBox()
    }
}

@Composable
fun BoxContainer() {
    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        DummyBox(Modifier.size(200.dp), Color.Green)
        DummyBox(Modifier.size(150.dp), Color.Yellow)
        DummyBox(color =  Color.Blue)
    }
}

@Composable
fun ButtonsContainer() {

    val buttonBorderGradient = Brush.horizontalGradient(listOf(Color.Yellow, Color.Red))

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val interactionSource2 = remember { MutableInteractionSource() }
    val isPressed2 by interactionSource2.collectIsPressedAsState()

    val pressStatusTitle = if (isPressed) "?????? ????????? ???" else "?????? ??? ????????? ???"
    val pressStatusTitle2 = if (isPressed2) "?????? ????????? ???" else "?????? ??? ????????? ???"

    val pressedBtnRadius2 = if (isPressed2) 0.dp else 20.dp
    val pressedBtnRadius2withAnim: Dp by animateDpAsState(
        if (isPressed2) 0.dp else 20.dp
    )

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            elevation = ButtonDefaults.elevation(
                defaultElevation = 10.dp,
                pressedElevation = 0.dp,
                disabledElevation = 0.dp
            ),
            enabled = true,
            onClick = {
            Log.d("TAG", "?????? 1 ??????")
        }) {
            Text(text = "?????? 1")
        }

        Button(
            elevation = ButtonDefaults.elevation(
                defaultElevation = 10.dp,
                pressedElevation = 0.dp,
                disabledElevation = 0.dp
            ),
            enabled = true,
            shape = CircleShape,
            onClick = {
                Log.d("TAG", "?????? 2 ??????")
            }) {
            Text(text = "?????? 2")
        }

        Button(
            elevation = ButtonDefaults.elevation(
                defaultElevation = 10.dp,
                pressedElevation = 0.dp,
                disabledElevation = 0.dp
            ),
            enabled = true,
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(4.dp, Color.Yellow),
            contentPadding = PaddingValues(horizontal = 30.dp, vertical = 10.dp),
            onClick = {
                Log.d("TAG", "?????? 3 ??????")
            }) {
            Text(text = "?????? 3")
        }

        Button(
            elevation = ButtonDefaults.elevation(
                defaultElevation = 10.dp,
                pressedElevation = 0.dp,
                disabledElevation = 0.dp
            ),
            enabled = true,
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(4.dp, buttonBorderGradient),
            onClick = {
                Log.d("TAG", "?????? 4 ??????")
            }) {
            Text(text = "?????? 4")
        }

        Button(
            elevation = ButtonDefaults.elevation(
                defaultElevation = 10.dp,
                pressedElevation = 0.dp,
                disabledElevation = 0.dp
            ),
            enabled = true,
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(4.dp, buttonBorderGradient),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Blue,
                disabledBackgroundColor = Color.Gray
            ),
            interactionSource = interactionSource,
            onClick = {
                Log.d("TAG", "?????? 5 ??????")
            }) {
            Text(text = "?????? 5")
        }

        Text(text = pressStatusTitle)

        Button(
            elevation = ButtonDefaults.elevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.dp,
                disabledElevation = 0.dp
            ),
            enabled = true,
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(4.dp, buttonBorderGradient),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Blue,
                disabledBackgroundColor = Color.Gray
            ),
            interactionSource = interactionSource2,
            modifier = Modifier.drawColoredShadow(
                color = Color.Red,
                alpha = 0.5f,
                borderRadius = 10.dp,
                shadowRadius = pressedBtnRadius2withAnim,
                offsetY = 0.dp,
                offsetX = 0.dp
            ),
            onClick = {
                Log.d("TAG", "?????? 6 ??????")
            }) {
            Text(text = "?????? 6")
        }

        Text(text = pressStatusTitle2)
    }

}

@Composable
fun BoxWithConstaraintContainer() {
    BoxWithConstraints(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
        propagateMinConstraints = false
    ) {
        Column() {
            BoxWithConstaraintItem(modifier = Modifier
                .size(200.dp)
                .background(Color.Yellow))
            BoxWithConstaraintItem(modifier = Modifier
                .size(300.dp)
                .background(Color.Green))
        }
//        DummyBox(Modifier.size(200.dp), Color.Green)
//        DummyBox(Modifier.size(150.dp), Color.Yellow)
//        DummyBox(color =  Color.Blue)
    }
}

@Composable
fun BoxWithConstaraintItem(modifier: Modifier) {
    BoxWithConstraints(
        modifier = modifier,
        contentAlignment = Alignment.Center,
        propagateMinConstraints = false
    ) {
        if (this.minWidth > 200.dp) {
            Text(text = "Big Box")
        } else {
            Text(text = "Small Box")
        }
    }
}

@Composable
fun TextContainer() {
    val name = "p2glet"
    var words = stringResource(id = R.string.dummy_short_text)
    var wordArr = words.split(" ")

    val scrollState = rememberScrollState()

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .verticalScroll(scrollState)
    ) {
        Text(text = "1??? ???????????????. $name",
            style = TextStyle(
                textAlign = TextAlign.Start
            ),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Yellow)
        )

        Text(text = stringResource(id = R.string.dummy_short_text),
            style = TextStyle(
                textAlign = TextAlign.Justify,
                textDecoration = TextDecoration.Underline
            ),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Yellow)
        )

        Text(text = stringResource(id = R.string.dummy_long_text),
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            style = TextStyle(
                textAlign = TextAlign.End,
                textDecoration = TextDecoration.combine(
                    listOf(
                        TextDecoration.Underline,
                        TextDecoration.LineThrough
                    )
                )
            ),
            fontWeight = FontWeight.ExtraBold,
            fontSize = 20.sp,
            fontFamily = FontFamily.Monospace,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Yellow)
        )

        Text(text = stringResource(id = R.string.dummy_short_text),
            style = TextStyle(
                textAlign = TextAlign.Start,
                fontFamily = FontFamily(Font(R.font.dalseodarling, weight = FontWeight.ExtraBold)),
                lineHeight = 20.sp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Yellow)
        )

        Text(text = buildAnnotatedString {
            wordArr.forEach {
                if (it.contains("??????")) {
                    withStyle(style = SpanStyle(color = Color.Blue)) {
                        append("$it ")
                    }
                } else {
                    append("$it ")
                }
            }
        })

        ClickableText(text = AnnotatedString("Click~"), onClick =  {
            Log.d("TAG", "??????")
        })

        Text(text = stringResource(id = R.string.dummy_long_text),
        style = TextStyle(lineHeight = 20.sp))
    }
}

@Composable
fun ShapeContainer() {

    var polySides by remember { mutableStateOf(3) }

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        DummyBox(Modifier.clip(RectangleShape))
        DummyBox(Modifier.clip(CircleShape))
        DummyBox(Modifier.clip(RoundedCornerShape(20.dp)))
        DummyBox(Modifier.clip(CutCornerShape(20.dp)))
        DummyBox(Modifier.clip(TriangleShape()))
        DummyBox(Modifier.clip(PolyShape(polySides,150f)))

        Text(text = "$polySides")
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = {
                polySides += 1
            }) {
                Text(text = "poly + 1")
            }
            Button(onClick = {
                polySides = 3
            }) {
                Text(text = "poly reset")
            }
        }
    }
}

@Composable
fun DummyBox(modifier: Modifier = Modifier, color: Color ?= null) {

    val red = Random.nextInt(256)
    val green = Random.nextInt(256)
    val blue = Random.nextInt(256)

    // color = null?????? ??????
    val randomColor = color ?: Color(red, green, blue)
    Box(modifier = modifier
        .size(100.dp)
        .background(randomColor))
}

class TriangleShape() : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            moveTo(size.width / 2f, 0f)
            lineTo(size.width, size.height)
            lineTo(0f, size.height)
            close()
        }
        return Outline.Generic(path = path)
    }
}

class PolyShape(val sides: Int, val radius: Float) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(path = Path().apply {this.polygon(sides, radius, size.center)})
    }
}

fun Path.polygon(sides: Int, radius: Float, center: Offset) {
    val angle = 2.0 * Math.PI / sides
    moveTo(
        center.x + (radius * cos(0.0)).toFloat(),
        center.y + (radius * sin(0.0)).toFloat()
    )
    for (i in 1 until sides) {
        lineTo(
            center.x + (radius * cos(angle * i)).toFloat(),
            center.y + (radius * sin(angle * i)).toFloat()
        )
    }
    close()
}

@Composable
fun CheckBoxContainer() {
    val checkedStatusForFirst = remember { mutableStateOf(false) }
    val checkedStatusForSecond = remember { mutableStateOf(false) }
    val checkedStatusForThird = remember { mutableStateOf(false) }
//    val checkedStatusForFourth = remember { mutableStateOf(false) }

    val checkedStatesArray = listOf(checkedStatusForFirst, checkedStatusForSecond, checkedStatusForThird)

    val AllBoxChecked: (Boolean) -> Unit = { isAll ->
        Log.d("TAG", "$isAll")
        checkedStatesArray.forEach { it.value = isAll }
    }

    val checkedStatusForFourth: Boolean = checkedStatesArray.all { it.value }

//    var checkedStatusForSecond by remember { mutableStateOf(false) }
//    val (checkedStatusForThird, setCheckedStatusForThird) = remember { mutableStateOf(false) }
//    val (checkedStatusForFourth, setCheckedStatusForFourth) = remember { mutableStateOf(false) }

    Column(
        Modifier
            .background(Color.White)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        CheckBoxWithTitle("1??? ??????", checkedStatusForFirst)
        CheckBoxWithTitle("2??? ??????", checkedStatusForSecond)
        CheckBoxWithTitle("3??? ??????", checkedStatusForThird)

//        Checkbox(
//            checked = checkedStatusForSecond,
//            onCheckedChange = {
//            Log.d("TAG", "$it")
//            checkedStatusForSecond = it
//        })
//
//        Checkbox(
//            checked = checkedStatusForThird,
//            onCheckedChange = {
//            Log.d("TAG", "$it")
//            setCheckedStatusForThird.invoke(it)
//        })

        Spacer(Modifier.height(10.dp))
        CheckBoxAll("?????? ???????", checkedStatusForFourth, AllBoxChecked)
        Spacer(Modifier.height(10.dp))
        MyCustomCheckBox("????????? ????????????", false)
        MyCustomCheckBox("????????? ???????????? ??????", true)
//        Checkbox(
//            enabled = true,
//            checked = checkedStatusForFourth,
//            colors = CheckboxDefaults.colors(
//                checkedColor = Color.Yellow,
//                uncheckedColor = Color.Red,
//                checkmarkColor = Color.Blue,
//                disabledColor = Color.Gray
//            ),
//            onCheckedChange = {
//            Log.d("TAG", "$it")
//            setCheckedStatusForFourth.invoke(it)
//        })

    }
}

@Composable
fun CheckBoxWithTitle(title: String, state: MutableState<Boolean>) {
    Row(
        Modifier
            .background(Color.White)
            .padding(horizontal = 30.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Checkbox(
            checked = state.value,
            onCheckedChange = {
                Log.d("TAG", "$it")
                state.value = it
            })
        Text(text = title)
    }
}

@Composable
fun CheckBoxAll(title: String,
                isChecked: Boolean,
                allBoxChecked: (Boolean) -> Unit) {
    Row(
        Modifier
            .background(Color.White)
            .padding(horizontal = 30.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Checkbox(
            checked = isChecked,
            colors = CheckboxDefaults.colors(
                checkedColor = Color.Yellow,
                uncheckedColor = Color.Red,
                checkmarkColor = Color.Blue,
                disabledColor = Color.Gray
            ),
            onCheckedChange = {
                Log.d("TAG", "$it")
                allBoxChecked(it)
            })
        Text(text = title)
    }
}

@Composable
fun MyCustomCheckBox(title: String, withRipple: Boolean = false) {

    var (isChecked, setisChecked) = remember { mutableStateOf(false) }

    var togglePainter = if (isChecked) R.drawable.ic_checked else R.drawable.ic_unchecked

    var checkedInfoString = if (isChecked) "?????? ??????" else "?????? ??????"

    var rippleEffect = if (withRipple) {
        rememberRipple(
            radius = 30.dp,
            bounded = false,
            color = Color.Blue
        )
    } else null

    Row(
        Modifier
            .background(Color.White)
            .padding(horizontal = 30.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(60.dp)
                .clickable(
                    indication = rippleEffect,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    Log.d("TAG", "????????? ???????????????.")
                    setisChecked.invoke(!isChecked)
                }) {
            Image(
                painter = painterResource(id = togglePainter),
                contentDescription = null,

            )
        }

//        Checkbox(
//            checked = isChecked,
//            onCheckedChange = {
//                Log.d("TAG", "$it")
//                setisChecked.invoke(it)
//            })
        Text(text = "$title / $checkedInfoString")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Compose_fundamental_tutorialTheme {
//        Container()
//        BoxContainer()
//        BoxWithConstaraintContainer()
//        TextContainer()
//        ShapeContainer()
//        ButtonsContainer()
//        CheckBoxContainer()
//        MySnackBar()
        TextFieldTest()
    }
}