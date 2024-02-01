
import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.nytchallenge.data.TaskDao
import com.example.nytchallenge.data.TaskEntity
import com.example.nytchallenge.data.TasksDatabase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.model.MultipleFailureException.assertEmpty

@RunWith(AndroidJUnit4::class)
class TaskDaoTest {
    private lateinit var taskDao: TaskDao
    private lateinit var tasksDatabase: TasksDatabase

    //declare tasks to be used in tests
    private var task1 = TaskEntity(1, "Task 1", false)
    private var task2 = TaskEntity(2, "Task 2", false)

    //initialize db and dao
    @Before
    @Throws(Exception::class)
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        tasksDatabase = Room.inMemoryDatabaseBuilder(context, TasksDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        taskDao = tasksDatabase.taskDao()
    }
    //utility function to insert 1 task into db
    private suspend fun addOneTaskToDb() {
        taskDao.insert(task1)
    }
    // utility function to insert 2 tasks into db
    private suspend fun addTwoTasksToDb() {
        taskDao.insert(task1)
        taskDao.insert(task2)
    }

    //test to insert task into db
    @Test
    @Throws(Exception::class)
    fun daoInsert_insertsTaskIntoDB() = runBlocking {
        addOneTaskToDb()
        //get task from db
        val allTasks = taskDao.getAllTasks().first()
        //assert that task is in db
        assertEquals(allTasks[0], task1)
    }

    //test all tasks are returned from db
    @Test
    @Throws(Exception::class)
    fun daoGetAllTasks_returnsAllTasksFromDB() = runBlocking {
        addTwoTasksToDb()
        val allTasks = taskDao.getAllTasks().first()
        assertEquals(allTasks[0], task1)
        assertEquals(allTasks[1], task2)
    }

    //test delete all tasks
    @Test
    @Throws(Exception::class)
    fun daoDeleteTasks_deletesAllTasksFromDB() = runBlocking {
        addTwoTasksToDb()
        taskDao.delete(task1)
        taskDao.delete(task2)
        val allTasks = taskDao.getAllTasks().first()
        assertTrue(allTasks.isEmpty())
    }

    //Test get item from db
    @Test
    @Throws(Exception::class)
    fun daoGetTask_returnsTaskFromDB() = runBlocking {
        addOneTaskToDb()
        val task = taskDao.getTask(1).first()
        assertEquals(task, task1)
    }

    //close db after tests
    @After
    @Throws(Exception::class)
    fun closeDb() {
        tasksDatabase.close()
    }

}