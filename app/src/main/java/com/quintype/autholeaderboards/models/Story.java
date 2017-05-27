package com.quintype.autholeaderboards.models;

import com.google.gson.annotations.SerializedName;

import static android.R.attr.version;

/**
 * An object instance representing Story
 *
 * @author Imran imran@quintype.com
 * @author Madhu madhu@quintype.com
 */
public class Story {


    public static final String INVALID_ID = "-1";
    public static final Story INVALID_STORY = new Story();

    static {
        INVALID_STORY.id = INVALID_ID;
    }

    @SerializedName("id")
    protected String id;
    @SerializedName("headline")
    private String headline;
    @SerializedName("subheadline")
    private String subHeadLine;
    @SerializedName("story-content-id")
    private String storyContentId;
    @SerializedName("slug")
    private String slug;
    @SerializedName("last-published-at")
    private long lastPublishedAt;
    @SerializedName("hero-image-s3-key")
    private String heroImageS3Key;
    @SerializedName("author-name")
    private String authorName;
    @SerializedName("hero-image-metadata")
    private ImageMetaData heroImageMeta;

    @Override
    public String toString() {
        return "Story{" +
                "id='" + id + '\'' +
                ", headline='" + headline + '\'' +
                ", subHeadLine='" + subHeadLine + '\'' +
                ", storyContentId='" + storyContentId + '\'' +
                ", slug='" + slug + '\'' +
                ", lastPublishedAt=" + lastPublishedAt +
                ", heroImageS3Key='" + heroImageS3Key + '\'' +
                ", authorName='" + authorName + '\'' +
                ", version='" + version + '\'' +
                ", heroImageMeta=" + heroImageMeta +
                '}';
    }

    /**
     * @return String headline
     */
    public String headline() {
        return headline;
    }

    /**
     * @return String subHeadLine
     */
    public String subHeadLine() {
        return subHeadLine;
    }

    /**
     * @return String content id
     */
    public String contentId() {
        return storyContentId;
    }

    /**
     * @return String slug
     */
    public String slug() {
        return slug;
    }

    /**
     * @return {@link Long} last published time in millis
     */
    public long lastPublishedAt() {
        return lastPublishedAt;
    }

    /**
     * @return String hero-image s3 key
     */
    public String heroImageS3Key() {
        return heroImageS3Key;
    }

    /**
     * @return String author name
     */
    public String authorName() {
        return authorName;
    }

    /**
     * @return {@link ImageMetaData}
     */
    public ImageMetaData heroImageMeta() {
        return heroImageMeta;
    }


    public Story() {
    }

    public Story(String id, String slug, String heroImageS3Key, String headline) {
        this.id = id;
        this.slug = slug;
        this.heroImageS3Key = heroImageS3Key;
        this.headline = headline;
    }

}

