package com.example.android_routine.data.model

data class CategoryVM(
    val id: Int = -1,
    val name: String = ""
) {
    companion object {
        fun fromEntity(category: Category): CategoryVM {
            return CategoryVM(
                id = category.id ?: -1,
                name = category.name
            )
        }
    }
}

fun CategoryVM.toEntity(): Category {
    return Category(
        id = if (this.id == -1) null else this.id,
        name = this.name
    )
}
