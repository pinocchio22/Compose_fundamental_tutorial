package com.example.compose_fundamental_tutorial

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.example.compose_fundamental_tutorial.ui.theme.Compose_fundamental_tutorialTheme
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
                    ButtonsContainer()
                }
            }
        }
    }
}

// arrangement 요소를 어떤식으로 배열할지, Row, Column 같은 요소들이 들어가는 컨테이너 성격의 Composable에서 요소의 아이템을 정렬할 때 사용
// alignment gravity 느낌
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

    val pressStatusTitle = if (isPressed) "버튼 누르는 중" else "버튼 안 누르는 중"
    val pressStatusTitle2 = if (isPressed2) "버튼 누르는 중" else "버튼 안 누르는 중"

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
            Log.d("TAG", "버튼 1 클릭")
        }) {
            Text(text = "버튼 1")
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
                Log.d("TAG", "버튼 2 클릭")
            }) {
            Text(text = "버튼 2")
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
                Log.d("TAG", "버튼 3 클릭")
            }) {
            Text(text = "버튼 3")
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
                Log.d("TAG", "버튼 4 클릭")
            }) {
            Text(text = "버튼 4")
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
                Log.d("TAG", "버튼 5 클릭")
            }) {
            Text(text = "버튼 5")
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
                Log.d("TAG", "버튼 6 클릭")
            }) {
            Text(text = "버튼 6")
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
        Text(text = "1번 문장입니다. $name",
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
                if (it.contains("풍부")) {
                    withStyle(style = SpanStyle(color = Color.Blue)) {
                        append("$it ")
                    }
                } else {
                    append("$it ")
                }
            }
        })

        ClickableText(text = AnnotatedString("Click~"), onClick =  {
            Log.d("TAG", "클릭")
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

    // color = null이면 랜덤
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Compose_fundamental_tutorialTheme {
//        Container()
//        BoxContainer()
//        BoxWithConstaraintContainer()
//        TextContainer()
//        ShapeContainer()
        ButtonsContainer()
    }
}