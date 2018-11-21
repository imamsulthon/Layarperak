package com.tothon.layarperak.service;

import com.tothon.layarperak.config.Configuration;
import com.tothon.layarperak.model.response.MovieDetailsResponse;
import com.tothon.layarperak.model.response.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitAPI {

    String POSTER_BASE_URL = "http://image.tmdb.org/t/p/w342";
    String BACKDROP_BASE_URL = "http://image.tmdb.org/t/p/w780";
    String BASE_URL = "https://api.themoviedb.org/3/";
    String TMDB_API_TOKEN = Configuration.TMDB_API_KEY;

    @GET("movie/{type}")
    Call<MovieResponse> getMovies(@Path("type") String TYPE,
                                  @Query("api_key") String API_KEY,
                                  @Query("language") String LANGUAGE,
                                  @Query("page") int PAGE);
//
//    @GET("search/movie")
//    Call<TMDBResponse> searchMovies(@Query("api_key") String API_KEY, @Query("language") String LANGUAGE, @Query("page") int PAGE, @Query("query") String QUERY);
//
//    @GET("movie/{movie_id}/videos")
//    Call<TMDBTrailerResponse> getTrailers(@Path("movie_id") int MOVIE_ID, @Query("api_key") String API_KEY, @Query("language") String LANGUAGE);
//
//    @GET("movie/{movie_id}/reviews")
//    Call<TMDBReviewResponse> getReviews(@Path("movie_id") int MOVIE_ID, @Query("api_key") String API_KEY, @Query("language") String LANGUAGE);
//
//    @GET("movie/{movie_id}/credits")
//    Call<TMDBCreditsResponse> getCredits(@Path("movie_id") int MOVIE_ID, @Query("api_key") String API_KEY);
//
    @GET("movie/{movie_id}")
    Call<MovieDetailsResponse> getDetails(@Path("movie_id") int MOVIE_ID,
                                          @Query("api_key") String API_KEY,
                                          @Query("language") String LANGUAGE);
//
//    @GET("movie/{movie_id}/similar")
//    Call<TMDBResponse> getSimilarMovies(@Path("movie_id") int MOVIE_ID, @Query("api_key") String API_KEY, @Query("language") String LANGUAGE);
//
//    @GET("trending/{media_type}/{time_window}")
//    Call<TMDBResponse> getTrending(@Path("media_type") String mediaType, @Path("time_window") String time_window, @Query("api_key") String API_KEY);
//
//    @GET("person/{person_id}")
//    Call<Person> getPersonDetails(@Path("person_id") int PERSON_ID, @Query("api_key") String APIK_KEY,  @Query("language") String LANGUAGE);

}
