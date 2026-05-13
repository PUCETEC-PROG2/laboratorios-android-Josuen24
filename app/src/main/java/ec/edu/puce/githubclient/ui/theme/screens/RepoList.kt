package ec.edu.puce.githubclient.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ec.edu.puce.githubclient.ui.theme.compose.RepoItem

@Preview(showBackground = true)
@Composable
fun RepoList(
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
    ) {

        RepoItem(
            name = "Repositorio",
            description = "Repositorio creado para desarrollo móvil",
            avatarUrl = "https://avatars.githubusercontent.com/u/1?v=4",
            lenguaje = "Kotlin"
        )

        RepoItem(
            name = "Repositorio ",
            description = "Repositorio creado para desarrollo móvil",
            avatarUrl = "https://avatars.githubusercontent.com/u/1?v=4",
            lenguaje = "Kotlin"
        )

        RepoItem(
            name = "Repositorio",
            description = "Repositorio creado para desarrollo móvil",
            avatarUrl = "https://avatars.githubusercontent.com/u/1?v=4",
            lenguaje = "Kotlin"
        )

        RepoItem(
            name = "Repositorio ",
            description = "Repositorio creado para desarrollo móvil",
            avatarUrl = "https://avatars.githubusercontent.com/u/1?v=4",
            lenguaje = "Kotlin"
        )
    }
}