package com.behnamuix.myapplication04.ui.navigation.screens

import android.Manifest
import android.content.Intent
import android.os.Build
import android.transition.CircularPropagation
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.windowInsetsEndWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Comment
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.MusicNote
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material.icons.rounded.Upload
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.behnamuix.myapplication04.data.local.db.model.Comment
import com.behnamuix.myapplication04.data.local.db.model.Post
import com.behnamuix.myapplication04.notification.viewModel.NotifViewModel
import com.behnamuix.myapplication04.ui.navigation.Screens
import com.behnamuix.myapplication04.viewModel.music.MusicViewModel
import com.behnamuix.myapplication04.viewModel.music.MusicViewModel.PlayerState
import com.behnamuix.myapplication04.viewModel.permission.PermissionManagerViewModel
import com.behnamuix.myapplication04.viewModel.permission.PermissionState
import com.behnamuix.myapplication04.viewModel.ui.comment.CommentAddViewModel
import com.behnamuix.myapplication04.viewModel.ui.comment.CommentListViewModel
import com.behnamuix.myapplication04.viewModel.ui.post.PostFeedViewModel
import org.koin.androidx.compose.koinViewModel
import kotlin.random.Random

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostFeedSc(
    navController: NavHostController,
    listVm: PostFeedViewModel = koinViewModel(),
    commentAddVm: CommentAddViewModel = koinViewModel(),
    commentListVm: CommentListViewModel = koinViewModel(),
    notifVm: NotifViewModel = koinViewModel(),
    permVm: PermissionManagerViewModel = koinViewModel(),
    musicVm: MusicViewModel = koinViewModel()
) {
    val list by listVm.posts.collectAsState()
    val state = permVm.state.collectAsState()


    val notifLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                permVm.checkPerm(Manifest.permission.POST_NOTIFICATIONS)
            }
        }

    LaunchedEffect(Unit) {

        permVm.checkPerm(Manifest.permission.POST_NOTIFICATIONS)
        when (state.value) {
            PermissionState.GRANTED -> {}

            PermissionState.NEED_REQ -> {
                notifLauncher.launch(
                    Manifest.permission.POST_NOTIFICATIONS
                )
            }

            PermissionState.LOADING -> {
                notifLauncher.launch(
                    Manifest.permission.POST_NOTIFICATIONS
                )
            }

        }
        listVm.loadPost()
        commentListVm.loadComment()
        commentAddVm.send.collect {
            if (it) {
                commentListVm.loadComment()
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {

        // 🔥 Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Posts",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,

                )

            MusicPlayerComp(musicVm)

            IconButton(onClick = {
                navController.navigate(Screens.PostAdd.route)
            }) {
                Icon(
                    modifier = Modifier.size(32.dp),
                    imageVector = Icons.Rounded.Upload,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )

            }
        }


        Spacer(Modifier.height(12.dp))

        if (list.isEmpty()) {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No posts yet 😴")
            }

        } else {

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(list.sortedBy { post -> post.username }) { post ->
                    PostItem(
                        post,
                        listVm,
                        commentListVm,
                        commentAddVm,
                        notifVm
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostItem(
    post: Post,
    listVm: PostFeedViewModel,
    commentListVm: CommentListViewModel,
    commentAddVm: CommentAddViewModel,
    getVm: NotifViewModel,

    ) {
    val comments = commentListVm.comments.collectAsState()
    val bottomSheet = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    val context = LocalContext.current


    Card(

        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 8.dp),
        shape = RoundedCornerShape(28.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {

        Column {

            // 👤 Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(
                            brush = Brush.horizontalGradient(
                                colors =
                                    listOf(
                                        MaterialTheme.colorScheme.primary,
                                        MaterialTheme.colorScheme.secondary,
                                        MaterialTheme.colorScheme.tertiary,
                                    )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }

                Spacer(Modifier.width(10.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = post.username,
                        style = MaterialTheme.typography.titleSmall
                    )

                    Text(
                        text = post.createdAt,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                IconButton(
                    onClick = {
                        listVm.removePost(post)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.DeleteOutline,
                        contentDescription = null
                    )
                }
            }

            // 🖼 Post Image
            AsyncImage(
                model = post.imageUri.toUri(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(320.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(Modifier.height(6.dp))

            // ❤️ Actions
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(onClick = {
                    //getVm.sendNotifLike()
                    getVm.startWorker()

                }) {
                    Icon(
                        imageVector = Icons.Rounded.FavoriteBorder,
                        contentDescription = null
                    )
                }

                IconButton(onClick = {
                    showBottomSheet = true
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.Comment,
                        contentDescription = null
                    )
                }


                IconButton(onClick = {
                    val intent = Intent(Intent.ACTION_SEND).apply {
                        putExtra(
                            Intent.EXTRA_TEXT,
                            post.caption
                        )
                    }

                    context.startActivity(
                        Intent.createChooser(intent, "Share Post")
                    )
                    getVm.sendNotifShare()
                }) {
                    Icon(
                        imageVector = Icons.Rounded.Share,
                        contentDescription = null
                    )
                }
                Spacer(modifier = Modifier.weight(1f))


            }

            // ❤️ Likes
            Text(
                text = "${Random.nextInt(5, 20)} likes",
                modifier = Modifier.padding(horizontal = 16.dp),
                style = MaterialTheme.typography.labelLarge
            )

            Spacer(Modifier.height(4.dp))

            // ✍ Caption
            Row(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {

                Text(
                    text = post.username,
                    style = MaterialTheme.typography.labelLarge
                )
                Spacer(Modifier.width(4.dp))

                Text(
                    text = post.caption,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(Modifier.height(12.dp))
        }
        if (showBottomSheet) {
            ModalBottomSheet(
                modifier = Modifier.fillMaxSize(),
                sheetState = bottomSheet,
                onDismissRequest =
                    { showBottomSheet = false }) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Box(Modifier.fillMaxSize()) {
                        Text(
                            "Comments", Modifier.fillMaxWidth(),
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Center
                        )
                        if (comments.value.isEmpty()) {
                            Box(
                                Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("no comment yet")
                            }
                        } else {
                            LazyColumn(modifier = Modifier.height(400.dp)) {
                                items(comments.value) {
                                    CommentItem(it, post)
                                }
                            }
                        }


                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
                            Row(
                                modifier = Modifier.padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically,

                                ) {
                                OutlinedTextField(
                                    modifier = Modifier.weight(1f),
                                    value = commentAddVm.msg.value,
                                    onValueChange = {
                                        commentAddVm.msg.value = it
                                    },
                                    placeholder = {

                                        Text("enter your comment...")


                                    }

                                )
                                TextButton(
                                    modifier = Modifier.weight(0.2f),
                                    onClick = {
                                        commentAddVm.username.value = post.username
                                        commentAddVm.addComment()
                                        commentAddVm.msg.value = ""
                                    }
                                ) { Text("send") }

                            }
                        }
                    }


                }

            }
        }
    }
}

@Composable
fun CommentItem(item: Comment, post: Post) {

    Row(modifier = Modifier.padding(8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        AsyncImage(
            modifier = Modifier
                .clip(CircleShape)
                .size(48.dp),
            model = post.imageUri,
            contentScale = ContentScale.Crop,
            contentDescription = ""
        )
        Column {
            Text(item.username)
            Text(item.msg)
            TextButton({}) {
                Text("Reply")
            }
        }
        Spacer(Modifier.weight(1f))
        IconButton(onClick = { }) {
            Icon(
                imageVector = Icons.Rounded.FavoriteBorder,
                contentDescription = null
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun MusicPlayerComp(musicVm: MusicViewModel) {

    val isLoading by musicVm.playerState.collectAsState()
    var showProgress by remember { mutableStateOf(false) }

    Card(
        onClick = {
            musicVm.toggleMusic()
        },
        modifier = Modifier
            .padding(horizontal = 6.dp)
            .fillMaxWidth(0.7f),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.onPrimary)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier

                    .padding(end = 16.dp)
                    .animateContentSize(),

                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                IconButton(onClick = {

                }) {
                    Icon(
                        modifier = Modifier.size(18.dp),
                        imageVector =
                            when (isLoading) {
                                PlayerState.None -> {
                                    Icons.Rounded.MusicNote
                                }

                                PlayerState.Stoped -> {
                                    Icons.Rounded.PlayArrow
                                }

                                PlayerState.Playing -> {
                                    Icons.Rounded.Pause
                                }
                            },

                        contentDescription = null
                    )
                }
                Text(
                    text =
                        when (isLoading) {
                            PlayerState.None -> {
                                "tap on this😉"

                            }

                            PlayerState.Stoped -> {
                                "stoped"

                            }

                            PlayerState.Playing -> {
                                "playing(${musicVm.duraton.value})"
                            }
                        }
                )
                showProgress = when (isLoading) {
                    PlayerState.None -> {
                        false
                    }

                    PlayerState.Playing -> {
                        true
                    }

                    PlayerState.Stoped -> {
                        false
                    }
                }
                Spacer(Modifier.width(8.dp))
                if (showProgress) {
                    CircularProgressIndicator(modifier = Modifier.size(18.dp), strokeWidth = 2.dp)
                }


            }
            Log.d("LOG", musicVm.pose.toFloat().toString())
            LinearProgressIndicator(
                progress = { musicVm.pose.toFloat() },
                modifier = Modifier.fillMaxWidth(),
                color = ProgressIndicatorDefaults.linearColor,
                trackColor = ProgressIndicatorDefaults.linearTrackColor,
                strokeCap = ProgressIndicatorDefaults.LinearStrokeCap,
            )

        }
    }

}