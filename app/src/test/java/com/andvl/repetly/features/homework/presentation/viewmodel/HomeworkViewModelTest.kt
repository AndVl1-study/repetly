import app.cash.turbine.test
import com.andvl.repetly.common.data.models.Id
import com.andvl.repetly.common.data.models.lesson.Attachment
import com.andvl.repetly.features.homework.data.models.HomeworkItem
import com.andvl.repetly.features.homework.data.models.HomeworkUser
import com.andvl.repetly.features.homework.data.repository.HomeworkRepository
import com.andvl.repetly.features.homework.presentation.viewmodel.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.*

@OptIn(ExperimentalCoroutinesApi::class)
class HomeworkViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
    
    private lateinit var viewModel: HomeworkViewModel
    private lateinit var homeworkRepository: HomeworkRepository
    private var testHomeworkId: Id = Id("")
    
    @Before
    fun setup() {
        homeworkRepository = mock()
        viewModel = HomeworkViewModel(homeworkRepository)
        testHomeworkId = Id("test_id")
    }
    
    @Test
    fun `when getting homework successfully should emit success state`() = runTest {
        // Arrange
        val homework = HomeworkItem(
            title = "Math Homework",
            description = "Solve equations",
            author = HomeworkUser(
                id = Id("author_id"),
                name = "John",
                surname = "Doe",
                photoSrc = null
            ),
            images = listOf(
                Attachment.Image("test_url", "test_description")
            ),
            files = emptyList()
        )
        
        doReturn(homework).`when`(homeworkRepository).getHomework(testHomeworkId)
        
        // Act & Assert
        viewModel.state.test {
            viewModel.getHomework(testHomeworkId)
            
            assert(awaitItem() is HomeworkUiState.Loading)
            val successState = awaitItem()
            assert(successState is HomeworkUiState.Success)
            assert((successState as HomeworkUiState.Success).state.homework == homework)
            
            cancelAndIgnoreRemainingEvents()
        }
    }
    
    @Test
    fun `when getting homework fails should emit error state`() = runTest {
        // Arrange
        doThrow(RuntimeException("Network error"))
            .`when`(homeworkRepository)
            .getHomework(testHomeworkId)
        
        // Act & Assert
        viewModel.state.test {
            viewModel.getHomework(testHomeworkId)
            
            assert(awaitItem() is HomeworkUiState.Loading)
            val errorState = awaitItem()
            assert(errorState is HomeworkUiState.Error)
            
            cancelAndIgnoreRemainingEvents()
        }
    }
    
    @Test
    fun `when updating answer text should update state`() = runTest {
        // Arrange
        val homework = HomeworkItem(
            title = "Math Homework",
            description = "Solve equations",
            author = HomeworkUser(
                id = Id("author_id"),
                name = "John",
                surname = "Doe",
                photoSrc = null
            ),
            images = emptyList(),
            files = emptyList()
        )
        
        doReturn(homework).`when`(homeworkRepository).getHomework(testHomeworkId)
        
        // Act & Assert
        viewModel.getHomework(testHomeworkId) // Сначала загружаем домашнее задание
        viewModel.state.test {
            skipItems(2) // Пропускаем Loading и Success состояния от getHomework
            
            viewModel.onAction(HomeworkAction.UpdateAnswerText("New answer"))
            
            val updatedState = awaitItem()
            assert(updatedState is HomeworkUiState.Success)
            assert((updatedState as HomeworkUiState.Success).state.answerText == "New answer")
            
            cancelAndIgnoreRemainingEvents()
        }
    }
    
    @Test
    fun `when sending message should clear answer text`() = runTest {
        // Arrange
        val homework = HomeworkItem(
            title = "Math Homework",
            description = "Solve equations",
            author = HomeworkUser(
                id = Id("author_id"),
                name = "John",
                surname = "Doe",
                photoSrc = null
            ),
            images = emptyList(),
            files = emptyList()
        )
        
        doReturn(homework).`when`(homeworkRepository).getHomework(testHomeworkId)
        doReturn(Unit).`when`(homeworkRepository).sendHomeworkMessage(eq(testHomeworkId), any())
        
        // Act & Assert
        viewModel.getHomework(testHomeworkId) // Сначала загружаем домашнее задание
        
        viewModel.state.test {
            skipItems(2) // Пропускаем Loading и Success состояния от getHomework
            
            viewModel.onAction(HomeworkAction.UpdateAnswerText("Test message"))
            awaitItem() // Ждем обновление состояния после ввода текста
            
            viewModel.onAction(HomeworkAction.SendMessage)
            
            val updatedState = awaitItem()
            assert(updatedState is HomeworkUiState.Success)
            assert((updatedState as HomeworkUiState.Success).state.answerText.isEmpty())
            
            verify(homeworkRepository).sendHomeworkMessage(
                lessonId = testHomeworkId,
                message = "Test message"
            )
            
            cancelAndIgnoreRemainingEvents()
        }
    }
} 
