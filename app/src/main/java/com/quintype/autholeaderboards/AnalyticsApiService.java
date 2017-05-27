package com.quintype.autholeaderboards;


import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by AKSHAY on 02-10-2015.
 */
public interface AnalyticsApiService {


    public static final String PUBLISHER_ID = "publisher-id";
    public static final String FILTERS = "filters";
    public static final String COUNT = "top-count";
    public static final String PATH_FACT = "fact";

    @GET("/api/v1/analytics/story/top/author/{" + PATH_FACT + "}")
    Call<AuthorResponse> topAuthors(@Path(PATH_FACT) String fact, @Query
            (PUBLISHER_ID) String publisherId, @Query(FILTERS) String filters, @Query(COUNT)
                                                String count);

    @GET("/api/v1/analytics/story/top/author/{" + PATH_FACT + "}")
    Observable<AuthorResponse> topAuthorsRx(@Path(PATH_FACT) String fact, @Query
            (PUBLISHER_ID) String publisherId, @Query(FILTERS) String filters, @Query(COUNT)
            String count);


}