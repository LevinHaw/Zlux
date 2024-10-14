-keep class com.submission.core.data.datasource.MovieRepository { *; }

# Keep Resource classes and their members
-keep class com.submission.core.data.datasource.Resource$Error { *; }
-keep class com.submission.core.data.datasource.Resource$Loading { *; }
-keep class com.submission.core.data.datasource.Resource$Success { *; }
-keep class com.submission.core.data.datasource.Resource { *; }

# Keep DataSource classes and their members
-keep class com.submission.core.data.datasource.local.LocalDataSource { *; }
-keep class com.submission.core.data.datasource.remote.RemoteDataSource { *; }
-keep class com.submission.core.data.datasource.remote.retrofit.ApiService { *; }
-keep class com.submission.core.data.datasource.remote.response.DetailPopularMovieResponse { *; }
-keep class com.submission.core.data.datasource.remote.response.ListPopularMovieResponse { *; }
-keep class com.submission.core.data.datasource.remote.response.ResultsItem { *; }

# Keep DI modules and their providers
-keep class com.submission.core.di.** { *; }
-keep class com.submission.core.di.DatabaseModule { *; }
-keep class com.submission.core.di.NetworkModule { *; }
-keep class com.submission.core.di.RepositoryModule { *; }

# Keep domain classes and their members
-keep class com.submission.core.domain.model.Movie { *; }
-keep class com.submission.core.domain.usecase.MovieInteractor { *; }
-keep class com.submission.core.domain.usecase.MovieUseCase { *; }

# Keep UI classes and their members
-keep class com.submission.core.presentation.MovieAdapter { *; }
-keep class com.submission.core.presentation.MovieAdapter$OnItemClickCallback { *;}

# Keep utility classes
-keep class com.submission.core.utils.AppExecutors { *; }
-keep class com.submission.core.utils.DarkMode { *; }

-dontwarn java.lang.invoke.StringConcatFactory

# Prevent proguard from stripping interface information from TypeAdapter, TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * extends com.google.gson.TypeAdapter
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer



