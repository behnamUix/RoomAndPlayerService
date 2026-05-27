package com.behnamuix.myapplication04.ui.navigation.screens.post

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Camera
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Textsms
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.behnamuix.myapplication04.utils.dateTimeFormat
import com.behnamuix.myapplication04.viewModel.permission.PermissionManagerViewModel
import com.behnamuix.myapplication04.viewModel.permission.PermissionState
import com.behnamuix.myapplication04.viewModel.ui.post.PostAddViewModel
import org.koin.androidx.compose.koinViewModel
import java.util.Calendar

@Composable
fun PostAddSc(
    navController: NavController,
    addVm: PostAddViewModel = koinViewModel(),
    permVm: PermissionManagerViewModel = koinViewModel(),

) {


/*    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it)
                permVm.checkPerm(android.Manifest.permission.CAMERA)

        }
    val state by permVm.state.collectAsState()
    LaunchedEffect(Unit) {
        permVm.checkPerm(android.Manifest.permission.CAMERA)

    }
    when (state) {
        PermissionState.GRANTED -> {

        }

        PermissionState.NEED_REQ -> {
            cameraLauncher.launch(android.Manifest.permission.CAMERA)

        }

        PermissionState.LOADING -> {

        }

        else -> {}
    }*/

    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
        uri?.let {

            context.contentResolver.takePersistableUriPermission(
                it,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )

            addVm.imageUri.value = it.toString()
        }
    }

    val isFormValid =
        !addVm.imageUri.value.isNullOrEmpty() &&
                addVm.caption.value.isNotBlank()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Spacer(Modifier.height(24.dp))

            // 📸 Image Picker
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = CircleShape
                    )
                    .clickable { launcher.launch(arrayOf("image/*")) },
                contentAlignment = Alignment.Center
            ) {

                if (!addVm.imageUri.value.isNullOrEmpty()) {

                    AsyncImage(
                        model = addVm.imageUri.value,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

                } else {
                    Icon(
                        imageVector = Icons.Default.AddAPhoto,
                        contentDescription = null,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            // 🧾 Card Form
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {

                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    MyTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = addVm.username,
                        label = "username",
                        leadingIcon = {
                            Icon(Icons.Default.Person, null)
                        }
                    )
                    MyTextField(
                        modifier = Modifier.fillMaxWidth(),

                        value = addVm.caption,
                        label = "Write a caption...",
                        leadingIcon = {
                            Icon(Icons.Default.Textsms, null)
                        }
                    )


                    HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)

                    PickDateTimeComp(addVm)

                    Spacer(Modifier.height(8.dp))

                    Button(
                        colors = ButtonDefaults.buttonColors(Color.Transparent),
                        onClick = {
                            addVm.addPost()
                            navController.popBackStack()
                        },
                        enabled = isFormValid,
                        modifier = Modifier
                            .background(
                                brush = Brush.linearGradient(
                                    colors =
                                        listOf(
                                            MaterialTheme.colorScheme.primary,
                                            MaterialTheme.colorScheme.secondary,
                                            MaterialTheme.colorScheme.tertiary,
                                        )
                                )
                            )
                            .fillMaxWidth()
                            .height(52.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("Publish Post")
                    }
                }
            }
        }
    }
}

@Composable
fun PickDateTimeComp(addVm: PostAddViewModel) {

    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val state = addVm.state.collectAsState()
    LaunchedEffect(Unit) {
        if (state.value == true) {
            Toast.makeText(context, "net connected", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "net disconnected!", Toast.LENGTH_SHORT).show()

        }
    }

    OutlinedButton(
        onClick = {
            DatePickerDialog(
                context,
                { _, year, month, day ->
                    TimePickerDialog(
                        context,
                        { _, hour, minute ->

                            calendar.set(year, month, day, hour, minute)

                            addVm.createdAt.value =
                                dateTimeFormat(calendar.timeInMillis)

                        },
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        true
                    ).show()
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            Icon(
                imageVector = Icons.Default.AccessTime,
                contentDescription = ""
            )
            Text("Select Date & Time")
        }
    }
}

@Composable
fun MyTextField(
    modifier: Modifier = Modifier,
    value: MutableState<String>,
    label: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    leadingIcon: @Composable (() -> Unit)? = null
) {

    OutlinedTextField(
        value = value.value,
        onValueChange = { value.value = it },
        label = { Text(label) },
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        leadingIcon = leadingIcon
    )

}