package com.example.studysyncapp.presentation.utils

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.studysyncapp.R
import com.example.studysyncapp.navigation.NavBarItemsList
import com.example.studysyncapp.ui.theme.AccentBlue
import com.example.studysyncapp.ui.theme.DarkBlue
import com.example.studysyncapp.ui.theme.NeutralLight
import com.example.studysyncapp.ui.theme.UiVariables
import com.example.studysyncapp.utils.getFileNameFromUri
import com.example.studysyncapp.utils.getFormat
import com.example.studysyncapp.utils.toHexString
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.TimePickerDefaults
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun Heading1(text: String) {
    Text(
        text = text,
        fontSize = 24.sp,
        fontWeight = FontWeight(800),
        color = Color.Black
    )
}

@Composable
fun Heading2(text: String) {
    Text(
        text = text,
        fontSize = 20.sp,
        fontWeight = FontWeight(500),
        color = Color.Black
    )
}

@Composable
fun Heading3(text: String) {
    Text(
        text = text,
        fontSize = 16.sp,
        fontWeight = FontWeight(800),
        color = Color.Black
    )
}

@Composable
fun Heading4(text: String, color: Color = Color.Black,textAlign: TextAlign = TextAlign.Center){
    Text(text = text,
        style = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight(700),
            color = color,
            textAlign = textAlign,
        ),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun BodyText(text: String){
    Text(
        text = text,
        fontSize = 12.sp,
        fontWeight = FontWeight(400),
        color = Color.Black,
        lineHeight = 16.sp
    )
}

@Composable
fun ErrorText(text: String){
    Text(
        text = text,
        color = Color.Red,
        fontSize = 12.sp,
        fontWeight = FontWeight(400)
    )
}

@Composable
fun ElevatedCard(modifier: Modifier = Modifier, onClick: () -> Unit = {}, content: @Composable () -> Unit){
    Column(modifier = modifier
        .fillMaxWidth()
        .shadow(
            elevation = 2.dp,
            shape = RoundedCornerShape(size = 12.dp),
            ambientColor = Color(0x0A000000)
        )
        .background(color = Color.White, shape = RoundedCornerShape(size = 12.dp))
        .clickable { onClick() }
        .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
    ){
        content()
    }
}

@Composable
fun ElevatedCardCircle(color: Color,size: Dp = 12.dp, borderWidth: Dp = 3.dp, modifier: Modifier = Modifier){
    Box(modifier = modifier
        .background(color = Color.Transparent, shape = CircleShape)
        .border(width = borderWidth, color = color, shape = CircleShape)
        .size(size))
}

@Composable
fun ElevatedCardText(text: String, maxLines: Int = 1, color: Color = Color(0xFF8F9BB3), modifier: Modifier = Modifier){
    Text(
        text = text,
        style = TextStyle(
            fontSize = 12.sp,
            lineHeight = 14.sp,
            fontWeight = FontWeight(500),
            color = color,
            letterSpacing = 0.75.sp,
        ),
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier
    )
}

@Composable
fun ElevatedCardLink(text: String, uri: String, maxLines: Int = 1, color: Color = AccentBlue, modifier: Modifier = Modifier){
    val uriHandler = LocalUriHandler.current
    Text(
        text = text,
        style = TextStyle(
            fontSize = 12.sp,
            lineHeight = 14.sp,
            fontWeight = FontWeight(500),
            color = color,
            letterSpacing = 0.75.sp,
            textDecoration = TextDecoration.Underline
        ),
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier.clickable { uriHandler.openUri(uri) }
    )
}

@Composable
fun ElevatedCardTitle(text: String, maxLines: Int = 1, color: Color = Color.Black, modifier: Modifier = Modifier){
    Text(
        text = text,
        style = TextStyle(
            fontSize = 16.sp,
            lineHeight = 19.sp,
            fontWeight = FontWeight(600),
            color = color,
        ),
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier.fillMaxWidth()
    )
}

@Composable
fun TitleDescriptionBar(title: String, description: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
        horizontalAlignment = Alignment.Start,
    ) {
        Heading3(text = title)
        BodyText(text = description)
    }
}

@Composable
fun FormColumn(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(UiVariables.FormSpacing, Alignment.Top),
        horizontalAlignment = Alignment.Start,
    ) {content()}
}

@Composable
fun FormRow(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(
            UiVariables.FormSpacing,
            alignment = Alignment.Start
        )
    ){ content() }
}

@Composable
fun FormTextField(value: String = "", label: String, onValueChange: (String) -> Unit, modifier: Modifier = Modifier, enabled: Boolean = true) {
    OutlinedTextField(
        value = value,
        maxLines = 1,
        enabled = enabled,
        onValueChange = onValueChange,
        label = { Text(text = label, fontSize = 12.sp, fontWeight = FontWeight(700)) },
        shape = RoundedCornerShape(size = 12.dp),
        modifier = modifier
            .fillMaxWidth(),
        keyboardOptions = KeyboardOptions.Default.copy(
            capitalization = KeyboardCapitalization.Sentences
        )
    )
}

@Composable
fun FormTextArea(value: String = "", label: String, onValueChange: (String) -> Unit, modifier: Modifier = Modifier, enabled: Boolean = true) {
    OutlinedTextField(
        value = value,
        enabled = enabled,
        onValueChange = onValueChange,
        label = { Text(text = label, fontSize = 12.sp, fontWeight = FontWeight(700)) },
        shape = RoundedCornerShape(size = 12.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(144.dp),
        maxLines = 5,
        keyboardOptions = KeyboardOptions.Default.copy(
             capitalization = KeyboardCapitalization.Sentences
        )
    )
}

@Composable
fun FormPasswordField(value: String = "", label: String, onValueChange: (String) -> Unit, modifier: Modifier = Modifier, enabled: Boolean = true) {
    var passwordVisible by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = value,
        maxLines = 1,
        enabled = enabled,
        onValueChange = onValueChange,
        label = { Text(text = label, fontSize = 12.sp, fontWeight = FontWeight(700)) },
        shape = RoundedCornerShape(size = 12.dp),
        modifier = modifier
            .fillMaxWidth(),
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val image = if (passwordVisible) {
                R.drawable.ic_visibility_on
            } else {
                R.drawable.ic_visibility_off
            }
            val description = if(passwordVisible) "Hide Password" else "Show Password"
            IconButton (onClick = { passwordVisible = !passwordVisible }) {
                Icon(painter = painterResource(id = image), description)
            }
        }
    )
}

@Composable
fun FormColorField(color: Color = DarkBlue, label: String, onValueChange: (String) -> Unit, modifier: Modifier = Modifier, enabled: Boolean = true) {
    var currColor by remember { mutableStateOf(color) }
    var openDialog by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
        OutlinedTextField(
            interactionSource = interactionSource,
            value = currColor.toHexString(),
            enabled = enabled,
            readOnly = true,
            onValueChange = {},
            label = { Text(text = label, fontSize = 12.sp, fontWeight = FontWeight(700)) },
            shape = RoundedCornerShape(size = 12.dp),
            modifier = modifier
                .fillMaxWidth().clickable {
                    openDialog = true
                    Log.d("MYTAG", "Field Clicked")
                },
            trailingIcon = {
                Box(modifier = Modifier
                    .background(color = currColor, shape = RoundedCornerShape(4.dp))
                    .size(24.dp))
            }
        )

    if(interactionSource.collectIsPressedAsState().value){
        openDialog = true
    }

        Log.d("MYTAG", "Color: $currColor")
        Log.d("MYTAG", "Open Dialog: $openDialog")
        if(openDialog){
            ColorPickerDialog(onDismiss = {openDialog = false}, onConfirm = {selectedColor ->
                currColor = selectedColor
                onValueChange(currColor.toHexString())
                openDialog = false
            })
        }
}

@Composable
fun FormDatePicker(date: LocalDate,label: String, onValueChange: (LocalDate) -> Unit, modifier: Modifier = Modifier, enabled: Boolean = true){
    val dateDialogState = rememberMaterialDialogState()
    val interactionSource = remember { MutableInteractionSource() }
    OutlinedTextField(
        interactionSource = interactionSource,
        value = date.getFormat("d MMM, yyyy"),
        enabled = enabled,
        readOnly = true,
        onValueChange = {},
        label = { Text(text = label, fontSize = 12.sp, fontWeight = FontWeight(700)) },
        shape = RoundedCornerShape(size = 12.dp),
        modifier = modifier
            .fillMaxWidth(),
//        trailingIcon = {
//            Icon(imageVector = Icons.Default.DateRange, contentDescription = "Select Date", tint = NeutralLight)
//        }
    )

    if(interactionSource.collectIsPressedAsState().value){
        dateDialogState.show()
    }

    MaterialDialog(dialogState = dateDialogState, buttons = {
        positiveButton(text = "Ok", textStyle = TextStyle(color = DarkBlue))
        negativeButton(text = "Cancel", textStyle = TextStyle(color = DarkBlue))
    }) {
        datepicker(
            initialDate = date,
            title = "Select Date",
            colors = DatePickerDefaults.colors(
                headerBackgroundColor = DarkBlue,
                dateActiveBackgroundColor = AccentBlue,
                dateActiveTextColor = Color.White,
            ),
            allowedDateValidator = {
                it.isAfter(LocalDate.now()) or it.isEqual(LocalDate.now())
            }
        ){onValueChange(it)}
    }
}

@Composable
fun FormTimePicker(time: LocalTime, label: String, onValueChange: (LocalTime) -> Unit, modifier: Modifier = Modifier, enabled: Boolean = true){
    val timeDialogState = rememberMaterialDialogState()
    val interactionSource = remember { MutableInteractionSource() }
    OutlinedTextField(
        interactionSource = interactionSource,
        value = "${"${time.hour.let{if (it < 13)  it else it - 12}}".padStart(2, '0')}:${"${time.minute}".padStart(2, '0')} ${time.hour.let { if(it < 12) "AM" else "PM"}}".uppercase(),
        enabled = enabled,
        readOnly = true,
        onValueChange = {},
        label = { Text(text = label, fontSize = 12.sp, fontWeight = FontWeight(700)) },
        shape = RoundedCornerShape(size = 12.dp),
        modifier = modifier
            .fillMaxWidth(),
//        trailingIcon = {
//            Icon(imageVector = Icons.Default.Create, contentDescription = "Select Date", tint = NeutralLight)
//        }
    )

    if(interactionSource.collectIsPressedAsState().value){
        timeDialogState.show()
    }

    MaterialDialog(dialogState = timeDialogState, buttons = {
        positiveButton(text = "Ok", textStyle = TextStyle(color = DarkBlue))
        negativeButton(text = "Cancel", textStyle = TextStyle(color = DarkBlue))
    }) {
        timepicker(
            initialTime = time,
            title = "Select Date",
            colors = TimePickerDefaults.colors(
            ),
        ){onValueChange(it)}
    }
}

@Composable
fun FormDateTimePicker(date: LocalDate, time: LocalTime, dateLabel: String, timeLabel: String, onValueChange: (LocalDate, LocalTime) -> Unit, modifier: Modifier = Modifier, enabled: Boolean = true){
    Row(modifier = modifier.fillMaxWidth()) {
        FormDatePicker(date = date, label = dateLabel, onValueChange = {date ->
            onValueChange(date, time)
        }, enabled = enabled, modifier = Modifier.weight(0.55f))
        Spacer(modifier = Modifier.width(16.dp))
        FormTimePicker(time = time, label = timeLabel, onValueChange = {time ->
            onValueChange(date, time)
        }, enabled = enabled, modifier = Modifier.weight(0.45f))
    }
}

@Composable
fun <T> FormDropdown(options: List<T>, getValueText: (T) -> String, value: T, label: String, onValueChange: (T) -> Unit, modifier: Modifier = Modifier, enabled: Boolean = true){
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(value) }
    val interactionSource = remember { MutableInteractionSource() }

    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = getValueText(selectedOption),
            enabled = enabled,
            readOnly = true,
            onValueChange = {},
            shape = RoundedCornerShape(size = 12.dp),
            interactionSource = interactionSource,
            label = { Text(text = label, fontSize = 12.sp, fontWeight = FontWeight(700)) },
            modifier = modifier.fillMaxWidth(),
            trailingIcon = {
                Icon(imageVector = if(expanded) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowDown, contentDescription = "Select Date", tint = NeutralLight)
            },
            maxLines = 1,
        )

        if(interactionSource.collectIsPressedAsState().value){
            expanded = !expanded
        }

        if(expanded){
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }){
                options.forEach { option ->
                    DropdownMenuItem(text = { Text(text = getValueText(option)) }, onClick = {
                        selectedOption = option
                        onValueChange(option)
                        expanded = false
                    })
                }
            }
        }
    }
}

@Composable
fun FormFilePicker(value: Uri? = null, label: String, onValueChange: (Uri?) -> Unit, modifier: Modifier = Modifier, enabled: Boolean = true){
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.OpenDocument(), onResult = {
        result -> onValueChange(result)
    })
    Row(
        modifier = modifier.fillMaxWidth().wrapContentHeight(),
        horizontalArrangement = Arrangement.spacedBy(UiVariables.FormSpacing, Alignment.Start),
        verticalAlignment = Alignment.Bottom
    ) {
        OutlinedTextField(
            value = value?.getFileNameFromUri(context) ?: "",
            enabled = enabled,
            readOnly = true,
            onValueChange = {},
            label = { Text(text = label, fontSize = 12.sp, fontWeight = FontWeight(700)) },
            shape = RoundedCornerShape(size = 12.dp),
            maxLines = 1,
            modifier = modifier
                .fillMaxWidth().weight(0.7f),
        )
        IconButton(
            modifier = Modifier.background(color = AccentBlue, shape = RoundedCornerShape(12.dp)).height(OutlinedTextFieldDefaults.MinHeight).aspectRatio(1f),
            onClick = {launcher.launch(arrayOf("application/pdf"))}
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Icon(painter = painterResource(id = R.drawable.ic_folder), contentDescription = "Select File", tint = Color.White, modifier = Modifier.size(24.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FormFilePickerPreview(){
    FormFilePicker(
        onValueChange = {},
        value = null,
        label = "File Picker",
    )
}

@Composable
fun FormTwoWayToggle(trueLabel: String, falseLabel:String, value: Boolean, onValueChange: (Boolean) -> Unit, modifier: Modifier = Modifier, enabled: Boolean = true){
    Box(modifier = modifier.fillMaxWidth().border(width = 1.dp, color = NeutralLight, shape = RoundedCornerShape(12.dp)).height(48.dp).padding(horizontal = 16.dp, vertical = 8.dp).background(color = Color.Transparent, shape = RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center,) {
        Text(text = falseLabel, fontSize = 12.sp, fontWeight = FontWeight(700), modifier = Modifier.align(Alignment.CenterStart).alpha(if (value) 0.5f else 1f).clickable { onValueChange(false) })
        Switch(
            onCheckedChange = onValueChange,
            checked = value,
            modifier = modifier.align(Alignment.Center),
            enabled = enabled,
            colors = SwitchDefaults.colors(
                checkedTrackColor = AccentBlue,
                checkedThumbColor = Color.White
            ),
        )
        Text(text = trueLabel, fontSize = 12.sp, fontWeight = FontWeight(700), modifier = Modifier.align(Alignment.CenterEnd).alpha(if (value) 1f else 0.5f).clickable { onValueChange(true) })
    }
}

//@Preview(showBackground = true)
//@Composable
//fun FormTwoWayTogglePreview(){
//    FormTwoWayToggle("True", "False", true, {})
//}

@Composable
fun ColorPickerDialog(onDismiss: () -> Unit, onConfirm: (Color) -> Unit) {
    Dialog(onDismissRequest = {onDismiss()}, properties = DialogProperties(
        usePlatformDefaultWidth = false,
    )
    ) {
        Surface(modifier = Modifier
            .fillMaxWidth(0.9f)
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(12.dp),
                ambientColor = Color(0x0A000000)
            )
            .background(color = Color.White, shape = RoundedCornerShape(12.dp))
            .padding(UiVariables.ScreenPadding)){
            Column( modifier = Modifier.background(color = Color.White)) {
                Row {
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close", modifier = Modifier
                        .clickable { onDismiss() }
                        .size(24.dp))
                }
                Spacer(modifier = Modifier.height(24.dp))
                LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 32.dp), verticalArrangement = Arrangement.spacedBy(24.dp), horizontalArrangement = Arrangement.spacedBy(24.dp)){
                    items(SelectableColors){
                        Box(modifier = Modifier
                            .background(color = it, shape = RoundedCornerShape(4.dp))
                            .size(32.dp)
                            .clickable { onConfirm(it) })
                    }
                }
            }
        }
    }
}

@Composable
fun DefaultButton(text: String, onClick: () -> Unit,textColor: Color = Color.White, backgroundColor: Color = AccentBlue, modifier: Modifier = Modifier, enabled: Boolean = true) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(size = 12.dp),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
        modifier = modifier
            .fillMaxWidth()
    ){
        Text(text = text,
            fontSize = 12.sp,
            fontWeight = FontWeight(600),
            color = textColor,
            )
    }
}

@Composable
fun LinkText(text: String, onClick: () -> Unit) {
    TextButton(onClick = onClick, contentPadding = PaddingValues(0.dp)) {
        Text(
            text = text,
            fontSize = 12.sp,
            fontWeight = FontWeight(600),
            color = AccentBlue,
        )
    }
}

@Composable
fun NavBar(navController: NavController){
    NavigationBar(containerColor = DarkBlue, contentColor = Color.White) {
        var selectedIndex by remember { mutableIntStateOf(0) }
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
        Log.d("MYTAG", "NavBar: $currentRoute")
        NavBarItemsList.forEachIndexed { index, navBarItem ->
            NavigationBarItem(
                selected = currentRoute?.split("/")?.get(0)?.lowercase() == navBarItem.route.toString().lowercase(),
                onClick = {
                    Log.d("MYTAG", "Clicked Route: ${navBarItem.route}")
                    selectedIndex = index
                    navController.navigate(navBarItem.route)
                },
                icon = {
                    Icon(painter = painterResource(id = if (index == selectedIndex) navBarItem.selectedIcon else navBarItem.unselectedIcon), contentDescription = navBarItem.title)
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = AccentBlue,
                    selectedTextColor = AccentBlue,
                    unselectedIconColor = Color.White,
                    unselectedTextColor = Color.White,
                    indicatorColor = Color.Transparent
                ),
                modifier = Modifier.background(color = DarkBlue, shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp, bottomStart = 0.dp, bottomEnd = 0.dp))
            )
        }
    }
}