package ec.edu.puce.githubclient.ui.theme.screens

import androidx.compose.runtime.Composable
import ec.edu.puce.githubclient.ui.theme.compose.RepoItem

@Composable
fun RepoList() {
    Column {
        RepoItem(
            name = "Repositorio de Android",
            description = "Repositorio creado para pruebas",
            avatarUrl = "https://avatars.githubusercontent.com/u/1?v=4",
            lenguaje = "Kotlin"
        )
        RepoItem(
            name = "Repositorio de Android",
            description = "Repositorio creado para pruebas",
            avatarUrl = "https://avatars.githubusercontent.com/u/1?v=4",
            lenguaje = "Kotlin"
        )
        RepoItem(
            name = "Repositorio de Android",
            description = "Repositorio creado para pruebas",
            avatarUrl = "https://avatars.githubusercontent.com/u/1?v=4",
            lenguaje = "Kotlin"
        )
    }
}