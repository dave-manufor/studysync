package com.example.studysyncapp.presentation.classroom.details.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.studysyncapp.R
import com.example.studysyncapp.domain.model.file.File
import com.example.studysyncapp.domain.model.file.getName
import com.example.studysyncapp.presentation.utils.ElevatedCard
import com.example.studysyncapp.presentation.utils.ElevatedCardTitle

@Composable
fun ClassroomFilesView(files: List<File>, onFileClick: (File) -> Unit){
    LazyColumn(
        modifier = Modifier.fillMaxSize(1f),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
        horizontalAlignment = Alignment.Start,
    ) {
        items(files) { file ->
            ElevatedCard(onClick = { onFileClick(file) }) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start)) {
                    Icon(painter = painterResource(R.drawable.ic_pdf), contentDescription = "File Icon", modifier = Modifier.size(32.dp), tint = Color.Unspecified)
                    ElevatedCardTitle(text = file.getName() ?: "")
                }
            }
        }
    }
}