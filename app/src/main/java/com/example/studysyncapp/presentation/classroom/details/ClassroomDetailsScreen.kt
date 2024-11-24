package com.example.studysyncapp.presentation.classroom.details

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.studysyncapp.R
import com.example.studysyncapp.core.FileType
import com.example.studysyncapp.data.remote.FilesApi
import com.example.studysyncapp.presentation.utils.DefaultButton
import com.example.studysyncapp.presentation.utils.FormFilePicker
import com.example.studysyncapp.presentation.utils.Heading4
import com.example.studysyncapp.ui.theme.AccentBlue
import com.example.studysyncapp.ui.theme.Neutral
import com.example.studysyncapp.ui.theme.NeutralDark
import com.example.studysyncapp.ui.theme.UiVariables
import com.example.studysyncapp.utils.toByteArray
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun ClassroomDetailsScreen(id: String, onNavigateToClassroomsList: () -> Unit){
    val classroomDetailsViewModel: ClassroomDetailsViewModel = viewModel(factory = ClassroomDetailsViewModelFactory(id))
    val state by classroomDetailsViewModel.state.collectAsState()
    Log.d("MYTAG", state.toString())
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val pagerState = rememberPagerState {
        TabNavList.size
    }
    LaunchedEffect(selectedTabIndex) {
        pagerState.animateScrollToPage(selectedTabIndex)
    }
    LaunchedEffect(pagerState.currentPage) {
        selectedTabIndex = pagerState.currentPage
    }
    Column(modifier = Modifier.fillMaxSize().padding(UiVariables.ScreenPadding)) {
            Row(modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                Box(modifier = Modifier.clickable(indication = null, interactionSource = null) { onNavigateToClassroomsList() }){
                    Icon(painter = painterResource(R.drawable.ic_left_arrow), contentDescription = "Go Back", tint = AccentBlue, modifier = Modifier.size(24.dp))
                }
                Spacer(modifier = Modifier.size(24.dp))
                Heading4(state.classroom?.name ?: "")
                Spacer(modifier = Modifier.size(24.dp))
                if(state.isEditable){
                    Box(modifier = Modifier.size(24.dp).clickable(indication = null, interactionSource = null) { onNavigateToClassroomsList() }){
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Add Item", tint = AccentBlue, modifier = Modifier.size(24.dp))
                    }
                }else{
                    Spacer(modifier = Modifier.size(24.dp))
                }
            }
//        Spacer
//        Tab Navigation
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally){
            ScrollableTabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = Color.Transparent,
                divider = {},
                indicator = {
                    tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                            color = AccentBlue
                        )
                },
                edgePadding = 0.dp,) {
                TabNavList.forEachIndexed { index, tabItem ->
                    val isSelected = selectedTabIndex == index
                    Tab(selected = isSelected, onClick = {selectedTabIndex = index},
                        selectedContentColor = AccentBlue,
                        unselectedContentColor = NeutralDark,
                        modifier = Modifier.padding(12.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Icon(
                                painter = painterResource(if (isSelected) tabItem.selectedIcon else tabItem.unselectedIcon),
                                contentDescription = tabItem.title,
                                tint = if (isSelected) AccentBlue else Neutral,
                                modifier = Modifier.size(20.dp)
                            )
                            Heading4(text = tabItem.title, color = if (isSelected) AccentBlue else Neutral)
                        }
                    }
                }
            }
        }
//        Divider
//        HorizontalDivider(thickness = 1.dp, color = NeutralLight, modifier = Modifier.fillMaxWidth())
//        Active Component
        var fileUri by remember { mutableStateOf<Uri?>(null) }
        val context = LocalContext.current
        HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize().weight(1f)){
            index ->
           when(index){
               0 -> {
                   Column {
                       FormFilePicker(
                           value = fileUri,
                           label = "Select File",
                           onValueChange = {it -> fileUri = it},
                       )
                       DefaultButton(text = "Upload", onClick = {
                           if(fileUri != null){
                               val byteArray = fileUri?.toByteArray(context)
                               byteArray?.let {
                                   CoroutineScope(Dispatchers.IO).launch {
                                       val fileApi = FilesApi()
                                       fileApi.uploadFile(
                                           fileName = "Test File - 1",
                                           byteArray = byteArray,
                                           type = FileType.ASSIGNMENT,
                                           onProgressChange = {it -> Log.d("MYTAG", "Progress: $it%")},
                                           onSuccess = {Log.d("MYTAG", "Upload Complete")},
                                           onError = {Log.e("MYTAG", "Error: $it")},
                                           classroomId = id
                                       )
                                   }
                               }
                           }
                       })
                   }
               }
               1 -> {}
               2 -> {}
               3 -> {}
           }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ClassroomDetailsScreenPreview(){
    ClassroomDetailsScreen(id = "123", onNavigateToClassroomsList = {})
}

