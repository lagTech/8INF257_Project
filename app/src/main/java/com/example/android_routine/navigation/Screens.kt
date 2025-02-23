sealed class Screen(val route: String) {
    object Home : Screen("home")
    object AllTasks : Screen("allTasks")
    object AllCategories : Screen("allCategories")
    // Add TaskDetail with parameter
    object TaskDetail : Screen("task_detail/{taskId}") {
        fun createRoute(taskId: Int) = "task_detail/$taskId"
    }
}