package com.sarah.objectives.utils

object Constants {

    object NETWORK_CONSTANTS{

        const val BASE_URL = "https://jsonplaceholder.typicode.com/"

        const val BLOG_ENDPOINT = "api/blogs/all/1"
        const val PROJECTS_ENDPOINT = "api/projects/all/1"
        const val SERVICES_ENDPOINT = "api/services"
        const val unAuthorizeAccessMessage ="Full Authentication Required"
        const val PHOTOS = "photos"
        const val POSTS = "posts"
    }

    object DATABASE {
        const val VERSION = 1
        const val DATABASE_NAME = "objectives_db"
    }

    object PREFERENCES {
        const val FILENAME = "OBJECTIVE_PREFERENCES"
        const val ON_BOARDING_FINISHED = "on_boarding_finished"
        const val TOKEN = "token"
        const val USER_DETAILS = "user_info"
        const val IS_LOGGED_IN = "is_logged_in"
    }

    object WORKER{
        const val TAG_POST = "worker_blog_tag"
        const val TAG_PHOTOS = "worker_project_tag"
    }

    const val UNKNOWN_VIEWMODEL = "Unknown View Model.Please add your view model in factory"
    const val STARTING_PAGE = 1
}