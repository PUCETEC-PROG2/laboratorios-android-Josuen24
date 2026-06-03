package ec.edu.puce.githubclient.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ec.edu.puce.githubclient.models.Repository
import ec.edu.puce.githubclient.ui.components.RepoItem
import ec.edu.puce.githubclient.viewmodels.RepoListViewModel

@Composable
fun RepoList(
    modifier: Modifier = Modifier,
    viewModel: RepoListViewModel = viewModel(),
    onNavigateToForm: () -> Unit
) {
    val repos by viewModel.repos.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMsg by viewModel.errorMsg.collectAsState()

    var repoToDelete by remember { mutableStateOf<Repository?>(null) }
    var repoToEdit by remember { mutableStateOf<Repository?>(null) }
    var editName by remember { mutableStateOf("") }
    var editDescription by remember { mutableStateOf("") }


    repoToDelete?.let { repo ->
        AlertDialog(
            onDismissRequest = { repoToDelete = null },
            title = { Text("Eliminar repositorio") },
            text = { Text("¿Estás seguro de que deseas eliminar \"${repo.name}\"? Esta acción no se puede deshacer.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.deleteRepo(repo.owner.login, repo.name)
                        repoToDelete = null
                    }
                ) {
                    Text("Eliminar", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { repoToDelete = null }) {
                    Text("Cancelar")
                }
            }
        )
    }


    repoToEdit?.let { repo ->
        AlertDialog(
            onDismissRequest = { repoToEdit = null },
            title = { Text("Editar repositorio") },
            text = {
                Column {
                    OutlinedTextField(
                        value = editName,
                        onValueChange = { editName = it },
                        label = { Text("Nombre") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = editDescription,
                        onValueChange = { editDescription = it },
                        label = { Text("Descripción") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.editRepo(repo.owner.login, repo.name, editName, editDescription)
                        repoToEdit = null
                    },
                    enabled = editName.isNotBlank()
                ) {
                    Icon(Icons.Default.Send, contentDescription = null)
                    Spacer(Modifier.width(4.dp))
                    Text("Guardar")
                }
            },
            dismissButton = {
                TextButton(onClick = { repoToEdit = null }) {
                    Text("Cancelar")
                }
            }
        )
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToForm,
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Agregar")
            }
        }
    ) { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            errorMsg?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp)
                )
            }

            if (!isLoading && errorMsg.isNullOrBlank()) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(repos, key = { it.id }) { repo ->
                        RepoItem(
                            repository = repo,
                            onEdit = {
                                editName = it.name
                                editDescription = it.description ?: ""
                                repoToEdit = it
                            },
                            onDelete = { repoToDelete = it }
                        )
                    }
                }
            }
        }
    }
}