package com.tothon.layarperak.service;

import com.tothon.layarperak.model.Movie;
import com.tothon.layarperak.model.response.CreditResponse;
import com.tothon.layarperak.model.response.ImagesResponse;
import com.tothon.layarperak.model.response.MovieResponse;
import com.tothon.layarperak.model.response.ReviewsResponse;
import com.tothon.layarperak.model.response.TrailerResponse;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitAPI {

    String BASE_URL = "https://api.themoviedb.org/3/";

    String POSTER_BASE_URL_SMALL = "http://image.tmdb.org/t/p/w342";
    String POSTER_BASE_URL_MEDIUM = "http://image.tmdb.org/t/p/w500";
    String POSTER_BASE_URL_LARGE = "http://image.tmdb.org/t/p/w780";
    String BACKDROP_BASE_URL = "http://image.tmdb.org/t/p/w780";

    @GET("movie/{type}")
    Call<MovieResponse> getMovies(@Path("type") String TYPE,
                                  @Query("api_key") String API_KEY,
                                  @Query("language") String LANGUAGE,
                                  @Query("page") int PAGE);
//
//    @GET("ic_search/movie")
//    Call<TMDBResponse> searchMovies(@Query("api_key") String API_KEY, @Query("language") String LANGUAGE, @Query("page") int PAGE, @Query("query") String QUERY);
//
    @GET("movie/{movie_id}/videos")
    Call<TrailerResponse> getTrailers(
            @Path("movie_id") int MOVIE_ID,
            @Query("api_key") String API_KEY,
            @Query("language") String LANGUAGE);

    @GET("movie/{movie_id}/reviews")
    Call<ReviewsResponse> getReviews
            (@Path("movie_id") int MOVIE_ID,
             @Query("api_key") String API_KEY,
             @Query("language") String LANGUAGE);

    @GET("movie/{movie_id}/credits")
    Call<CreditResponse> getCredits
            (@Path("movie_id") int MOVIE_ID,
             @Query("api_key") String API_KEY);

    @GET("movie/{movie_id}")
    Call<Movie> getDetails
            (@Path("movie_id") int MOVIE_ID,
             @Query("api_key") String API_KEY,
             @Query("language") String language);

    @GET("movie/{movie_id}/similar")
    Call<MovieResponse> getSimilarMovies
            (@Path("movie_id") int MOVIE_ID,
             @Query("api_key") String API_KEY);

    @GET("{type}/{id}/images")
    Call<ImagesResponse> getImages
            (@Path("type") String type,
             @Path("id") int id,
             @Query("api_key") String API_KEY);

//    @GET("trending/{media_type}/{time_window}")
//    Call<TMDBResponse> getTrending(@Path("media_type") String mediaType, @Path("time_window") String time_window, @Query("api_key") String API_KEY);
//
//    @GET("person/{person_id}")
//    Call<Person> getPersonDetails(@Path("person_id") int PERSON_ID, @Query("api_key") String APIK_KEY,  @Query("language") String LANGUAGE);

}
