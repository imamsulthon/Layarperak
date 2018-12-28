package com.tothon.layarperak.service;

import com.tothon.layarperak.model.Genre;
import com.tothon.layarperak.model.Movie;
import com.tothon.layarperak.model.Person;
import com.tothon.layarperak.model.Television;
import com.tothon.layarperak.model.response.CreditResponse;
import com.tothon.layarperak.model.response.GenreResponse;
import com.tothon.layarperak.model.response.ImagesResponse;
import com.tothon.layarperak.model.response.MovieResponse;
import com.tothon.layarperak.model.response.PeopleResponse;
import com.tothon.layarperak.model.response.PersonMoviesResponse;
import com.tothon.layarperak.model.response.ReviewsResponse;
import com.tothon.layarperak.model.response.SearchResponse;
import com.tothon.layarperak.model.response.TaggedImageResponse;
import com.tothon.layarperak.model.response.TelevisionResponse;
import com.tothon.layarperak.model.response.TrailerResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitAPI {

    String BASE_URL = "https://api.themoviedb.org/3/";

    // region Image URL BASE
    String POSTER_BASE_URL_SMALL = "http://image.tmdb.org/t/p/w342";
    String POSTER_BASE_URL_MEDIUM = "http://image.tmdb.org/t/p/w500";
    String POSTER_BASE_URL_LARGE = "http://image.tmdb.org/t/p/w780";
    String POSTER_BASE_URL_ORIGINAL = "http://image.tmdb.org/t/p/original";
    String BACKDROP_BASE_URL_SMALL = "http://image.tmdb.org/t/p/w300";
    String BACKDROP_BASE_URL_MEDIUM = "http://image.tmdb.org/t/p/w780";
    String BACKDROP_BASE_URL_LARGE = "http://image.tmdb.org/t/p/w1280";
    String BACKDROP_BASE_URL_ORIGINAL = "http://image.tmdb.org/t/p/original";
    //endregion

    // region Movie
    @GET("movie/{type}")
    Call<MovieResponse> getMovies
            (@Path("type") String TYPE,
             @Query("api_key") String API_KEY,
             @Query("language") String LANGUAGE,
             @Query("page") int PAGE);

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
    //endregion

    //region Person
    @GET("person/popular")
    Call<PeopleResponse> getPopularPerson
            (@Query("api_key") String API_KEY,
            @Query("page") int page);

    @GET("person/{person_id}")
    Call<Person> getPersonDetails
            (@Path("person_id") int PERSON_ID,
             @Query("api_key") String API_KEY);

    @GET("person/{person_id}/movie_credits")
    Call<PersonMoviesResponse> getPersonCredits
            (@Path("person_id") int PERSON_ID,
             @Query("api_key") String API_KEY);

    @GET("person/{person_id}/images")
    Call<ImagesResponse> getPersonImages
            (@Path("person_id") int PERSON_ID,
             @Query("api_key") String API_KEY);
    //endregion

    //region Television
    @GET("tv/{tv_id}")
    Call<Television> getTelevisionDetails
    (@Path("tv_id") int TV_ID,
     @Query("api_key") String api_key);

    @GET("tv/{type}")
    Call<TelevisionResponse> getTelevision
            (@Path("type") String type,
             @Query("api_key") String api_key,
             @Query("page") int page);

    @GET("tv/{tv_id}/similar")
    Call<TelevisionResponse> getSimilarTelevision
            (@Path("tv_id") int id,
             @Query("api_key") String api_key);
    // endregion

    // region Trending
    @GET("trending/movie/{time_window}")
    Call<MovieResponse> getTrendingMovies
            (@Path("time_window") String timeWindow,
             @Query("api_key") String API_KEY);

    @GET("trending/tv/{time_window}")
    Call<TelevisionResponse> getTrendingTelevision
            (@Path("time_window") String timeWindow,
             @Query("api_key") String api_key);

    @GET("trending/person/{time_window}")
    Call<PeopleResponse> getTrendingPeople
            (@Path("time_window") String timeWindow,
             @Query("api_key") String api_key);
    // endregion

    // region Media
    @GET("{type}/{id}/images")
    Call<ImagesResponse> getImages
            (@Path("type") String type,
             @Path("id") int id,
             @Query("api_key") String api_key);

    @GET("{type}/{id}/tagged_images")
    Call<TaggedImageResponse> getTaggedImage(
            @Path("type") String type,
            @Path("id") int id,
            @Query("api_key") String ap_key);

    @GET("{type}/{id}/videos")
    Call<TrailerResponse> getTrailers(
            @Path("type") String type,
            @Path("id") int id,
            @Query("api_key") String api_key,
            @Query("language") String language);

    @GET("{type}/{id}/reviews")
    Call<ReviewsResponse> getReviews
            (@Path("type") String type,
             @Path("id") int id,
             @Query("api_key") String api_key,
             @Query("language") String language);
    //endregion

    //region Search
    @GET("search/movie")
    Call<MovieResponse> getMovieSearch
            (@Query("query") String query,
             @Query("api_key") String api_key);

    @GET("search/{media_type}")
    Call<SearchResponse> getSearchResult
            (@Path("media_type") String mediaType,
             @Query("query") String query,
             @Query("api_key") String apiKey);
    //endregion

    //region Discover
    @GET("discover/movie")
    Call<MovieResponse> getMoviesByGenre(
            @Query("with_genres") String genreId,
            @Query("api_key") String api_key,
            @Query("language") String language,
            @Query("page") int page);

    @GET("discover/tv")
    Call<TelevisionResponse> getTelevisionsByGenre(
            @Query("with_genres") String genreId,
            @Query("api_key") String api_key,
            @Query("language") String language,
            @Query("page") int page);

    @GET("genre/{media_type}/list")
    Call<GenreResponse> getGenres(
            @Path("media_type") String mediaType,
            @Query("api_key") String api_key);
    //endregion

}
