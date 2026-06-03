package ec.edu.puce.githubclient.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ec.edu.puce.githubclient.models.Repository
import ec.edu.puce.githubclient.models.RepositoryPayload
import ec.edu.puce.githubclient.services.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RepoListViewModel : ViewModel() {

    private val _repos = MutableStateFlow<List<Repository>>(emptyList())
    val repos: StateFlow<List<Repository>> = _repos.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMsg = MutableStateFlow<String?>(null)
    val errorMsg: StateFlow<String?> = _errorMsg.asStateFlow()

    init { fetchRepos() }

    fun fetchRepos() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMsg.value = null
            try {
                _repos.value = RetrofitClient.apiService.getRepositories()
            } catch (e: Exception) {
                _errorMsg.value = "Error al cargar repositorios: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteRepo(owner: String, repo: String) {
        viewModelScope.launch {
            try {
                RetrofitClient.apiService.deleteRepository(owner, repo)
                _repos.value = _repos.value.filter { it.name != repo }
            } catch (e: Exception) {
                _errorMsg.value = "Error al eliminar: ${e.localizedMessage}"
            }
        }
    }

    fun editRepo(owner: String, repo: String, newName: String, newDescription: String) {
        viewModelScope.launch {
            try {
                val updated = RetrofitClient.apiService.updateRepository(
                    owner, repo, RepositoryPayload(newName, newDescription)
                )
                _repos.value = _repos.value.map { if (it.name == repo) updated else it }
            } catch (e: Exception) {
                _errorMsg.value = "Error al editar: ${e.localizedMessage}"
            }
        }
    }
}