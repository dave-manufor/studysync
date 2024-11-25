package com.example.studysyncapp.presentation.classroom.details

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.studysyncapp.R
import com.example.studysyncapp.data.remote.FilesApi
import com.example.studysyncapp.domain.model.Assignment
import com.example.studysyncapp.domain.model.Course
import com.example.studysyncapp.domain.model.file.File
import com.example.studysyncapp.domain.model.file.FileType
import com.example.studysyncapp.presentation.utils.DefaultButton
import com.example.studysyncapp.presentation.utils.FormColorField
import com.example.studysyncapp.presentation.utils.FormColumn
import com.example.studysyncapp.presentation.utils.FormDropdown
import com.example.studysyncapp.presentation.utils.FormFilePicker
import com.example.studysyncapp.presentation.utils.FormTextArea
import com.example.studysyncapp.presentation.utils.FormTextField
import com.example.studysyncapp.presentation.utils.FormTwoWayToggle
import com.example.studysyncapp.presentation.utils.Heading4
import com.example.studysyncapp.ui.theme.AccentBlue
import com.example.studysyncapp.ui.theme.OffWhite
import com.example.studysyncapp.ui.theme.UiVariables
import com.example.studysyncapp.utils.toByteArray
import com.example.studysyncapp.utils.toColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@Composable
fun UploadFileDialog(classroomId: String?, courses: List<Course>, assignments: List<Assignment>, onDismiss: () -> Unit, onConfirm: (isAssignment:Boolean, course:Course?, assignment:Assignment?, file: File) -> Unit){
    val context = LocalContext.current
    var isUploading by remember { mutableStateOf(false) }
    var uploadProgress by remember { mutableFloatStateOf(0f) }
    var name by remember { mutableStateOf("") }
    var isAssignment by remember { mutableStateOf(false) }
    var course by remember { mutableStateOf<Course?>(null) }
    var assignment by remember { mutableStateOf<Assignment?>(null) }
    var fileUri by remember { mutableStateOf<Uri?>(null) }
    var file by remember { mutableStateOf<File?>(null) }
    Dialog(onDismissRequest = {onDismiss()}, properties = DialogProperties(
        usePlatformDefaultWidth = false,
    )
    ) {
        Surface(modifier = Modifier.fillMaxSize().background(color = OffWhite).padding(UiVariables.ScreenPadding)) {
            Column(modifier = Modifier.fillMaxWidth().background(color = OffWhite), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top) {
                Row(modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.clickable(indication = null, interactionSource = null) { onDismiss() }){
                        Icon(painter = painterResource(R.drawable.ic_left_arrow), contentDescription = "Go Back", tint = AccentBlue, modifier = Modifier.size(24.dp))
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Heading4("Upload File")
                    Spacer(modifier = Modifier.weight(1f))
                    Spacer(modifier = Modifier.width(24.dp))
                }
                FormColumn {
                    FormTextField(value = name, label = "File Name", onValueChange = {name = it})
                    FormTwoWayToggle(
                        trueLabel = "Assignment",
                        falseLabel = "Course",
                        value = isAssignment,
                        onValueChange = {isAssignment = it},
                    )
                    FormDropdown(
                        options = if(isAssignment) assignments else courses,
                        getValueText = {
                            if(it == null){
                                ""
                            }else{
                                if(isAssignment){
                                    (it as Assignment).name
                                }else{
                                    (it as Course).name
                                }
                            }
                        },
                        value = if(isAssignment) assignment else course,
                        label = if(isAssignment) "Assignment" else "Course",
                        onValueChange = if(isAssignment) {it -> assignment = it as Assignment} else {it -> course = it as Course},
                    )
                    FormFilePicker(
                        value = fileUri,
                        label = "Select File",
                        onValueChange = {fileUri = it},
                    )
                }
                Spacer(modifier = Modifier.fillMaxWidth().height(24.dp))
                DefaultButton(text = if(isUploading) "Uploading... $uploadProgress%" else "Upload File",
                    enabled = (
                            name.isNotBlank() &&
                                    ((!isAssignment && course != null) ||
                                            (isAssignment && assignment != null)) &&
                                    fileUri != null
                            ),
                    onClick = {
                    if(fileUri != null){
                        isUploading = true
                        val byteArray = fileUri?.toByteArray(context)
                        byteArray?.let {
                            CoroutineScope(Dispatchers.IO).launch {
                                val fileApi = FilesApi()
                                async {
                                    fileApi.uploadFile(
                                        fileName = "$name.pdf",
                                        byteArray = byteArray,
                                        type = if(isAssignment) FileType.ASSIGNMENT else FileType.COURSE,
                                        onProgressChange = {uploadProgress = it},
                                        onSuccess = {file = it},
                                        onError = {Log.e("MYTAG", "Error: $it")},
                                        classroomId = classroomId
                                    )
                                }.await().let {
                                    isUploading = false
                                    file?.let {
                                        onConfirm(isAssignment, course, assignment, file!!)
                                    }
                                }
                            }
                        }
                    }
                })
            }
        }
    }
}