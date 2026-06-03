package ec.edu.puce.githubclient.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.compose.foundation.layout.offset
import androidx.compose.ui.platform.LocalDensity
import coil.compose.AsyncImage
import ec.edu.puce.githubclient.models.Repository
import kotlin.math.roundToInt

@Composable
fun RepoItem(
    repository: Repository,
    onEdit: (Repository) -> Unit,
    onDelete: (Repository) -> Unit
) {
    var offsetX by remember { mutableFloatStateOf(0f) }
    val density = LocalDensity.current
    val maxOffset = with(density) { 100.dp.toPx() }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        // Botón Editar a la izquierda
        IconButton(
            onClick = { onEdit(repository) },
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 8.dp)
                .background(
                    MaterialTheme.colorScheme.primaryContainer,
                    shape = MaterialTheme.shapes.medium
                )
                .size(80.dp)
                .zIndex(0f)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Editar",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text("Editar", style = MaterialTheme.typography.labelSmall)
            }
        }


        IconButton(
            onClick = { onDelete(repository) },
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 8.dp)
                .background(
                    MaterialTheme.colorScheme.errorContainer,
                    shape = MaterialTheme.shapes.medium
                )
                .size(80.dp)
                .zIndex(0f)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar",
                    tint = MaterialTheme.colorScheme.error
                )
                Text("Eliminar", style = MaterialTheme.typography.labelSmall)
            }
        }


        Card(
            modifier = Modifier
                .fillMaxWidth()
                .zIndex(1f)
                .offset { IntOffset(offsetX.roundToInt(), 0) }
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onDragEnd = {
                            offsetX = when {
                                offsetX > maxOffset / 2 -> maxOffset
                                offsetX < -maxOffset / 2 -> -maxOffset
                                else -> 0f
                            }
                        }
                    ) { _, dragAmount ->
                        offsetX = (offsetX + dragAmount).coerceIn(-maxOffset, maxOffset)
                    }
                }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 16.dp)
            ) {
                AsyncImage(
                    model = repository.owner.avatarUrl,
                    contentDescription = "Imagen de ${repository.name}",
                    modifier = Modifier.size(68.dp),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = repository.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    if (!repository.description.isNullOrBlank()) {
                        Text(
                            text = repository.description,
                            style = MaterialTheme.typography.bodySmall,
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    if (!repository.language.isNullOrBlank()) {
                        Text(
                            text = repository.language,
                            style = MaterialTheme.typography.labelSmall,
                        )
                    }
                }
            }
        }
    }
}