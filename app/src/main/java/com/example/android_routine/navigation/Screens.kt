sealed class Screen(val route: String) {
    object Home : Screen("home")
    object AllTasks : Screen("allTasks")
    object AddTask : Screen("addTask")
    object AllCategories : Screen("allCategories")

    object TaskDetail : Screen("task_detail/{taskId}") {
        fun createRoute(taskId: Int) = "task_detail/$taskId"
    }
}