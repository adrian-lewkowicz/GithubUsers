package com.example.github.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.github.database.entities.GithubUserEntity
import com.example.github.viewmodels.UsersViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UsersActivity : ComponentActivity() {
    private val usersViewModel: UsersViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UserListScreen(viewModel = usersViewModel)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListScreen(viewModel: UsersViewModel) {
    val users = viewModel.getUsers().observeAsState(emptyList())

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("GitHub Users") })
        }
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier.padding(16.dp)
        ) {
            items(users.value) { user ->
                UserItem(user = user)

            }
        }
    }
}

@Composable
fun UserItem(user: GithubUserEntity) {
    Row(modifier = Modifier.padding(8.dp)) {
        Image(
            painter = rememberAsyncImagePainter(model = user.avatarUrl),
            contentDescription = null,
            modifier = Modifier.size(50.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            user.login?.let { Text(text = it, style = MaterialTheme.typography.headlineMedium) }
            user.url?.let { Text(text = it, style = MaterialTheme.typography.bodyMedium) }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewUserItem() {
    UserItem(user = GithubUserEntity(id = 1, login = "octocat", avatarUrl = "https://github.com/images/error/octocat_happy.gif", url = "https://github.com/octocat"))
}