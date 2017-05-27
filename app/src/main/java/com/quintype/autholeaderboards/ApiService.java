package com.quintype.autholeaderboards;


import com.quintype.autholeaderboards.models.BulkStoryList;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by akshaykoul on 26/05/17.
 */
public interface ApiService {


    String QUERY_PARAM_KEY_LIMIT = "limit";
    String QUERY_PARAM_KEY_OFFSET = "offset";
    static String DEFAULT_FIELDS = "id,hero-image-s3-key,headline,author-name,story-content-id," +
            "hero-image-metadata,slug,last-published-at";

    @GET("api/author/{author_id}")
    Observable<Author> getAuthorRx(@Path("author_id") String authorId);

    @GET("api/author/{author_id}")
    Call<Author> getAuthor(@Path("author_id") String authorId);

    @GET("api/authors/")
    Observable<List<Author>> getAllAuthorsRx(@Query(QUERY_PARAM_KEY_OFFSET) String offset,
                                             @Query(QUERY_PARAM_KEY_LIMIT) String limit);

    @GET("api/authors/")
    Call<List<Author>> getAllAuthors(@Query(QUERY_PARAM_KEY_OFFSET) String offset,
                                     @Query(QUERY_PARAM_KEY_LIMIT) String limit);

    @GET("api/v1/stories?fields=" + DEFAULT_FIELDS+"&limit=10")
    Call<BulkStoryList> getBulkStoryDetails(@Query("story-order") String storyContentIDs);

    @GET("api/v1/stories?fields=" + DEFAULT_FIELDS+"&limit=10")
    Observable<BulkStoryList> getBulkStoryDetailsRx(@Query("story-order") String storyContentIDs);

}
