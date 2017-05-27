package com.quintype.autholeaderboards.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.quintype.autholeaderboards.R;
import com.quintype.autholeaderboards.models.Story;
import com.quintype.autholeaderboards.models.StoryResult;
import com.quintype.autholeaderboards.utils.Constants;
import com.quintype.autholeaderboards.utils.Utilities;
import com.sasank.roundedhorizontalprogress.RoundedHorizontalProgressBar;

/**
 * Created by akshaykoul on 27/05/17.
 */

public class StoryHolder extends RecyclerView.ViewHolder {

    TextView headline;
    TextView viewCount;
    ImageView heroImage;
    RoundedHorizontalProgressBar progressView;
    RequestManager glideRequestManager;
    private boolean progressStarted = true;

    public StoryHolder(View itemView) {
        super(itemView);
        glideRequestManager = Glide.with(itemView.getContext());
    }

    public static StoryHolder create(View view) {
        StoryHolder trackHolder = new StoryHolder(view);
        trackHolder.headline = (TextView) view.findViewById(R.id.headline);
        trackHolder.heroImage = (ImageView) view.findViewById(R.id.hero_image);
        trackHolder.viewCount = (TextView) view.findViewById(R.id.view_count);
        trackHolder.progressView = (RoundedHorizontalProgressBar) view.findViewById(R.id
                .view_count_progress);
        return trackHolder;
    }

    public void bind(final Story story, StoryResult storyResult, int maxViews) {
        headline.setText(story.headline());
        if (storyResult != null) {
            viewCount.setText(String.format(Constants.VIEV_FORMAT, storyResult.getCount()));
            progressView.setMax(maxViews);
            progressView.setProgress(storyResult.getCount());
        }


        heroImage.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver
                .OnPreDrawListener() {
            public boolean onPreDraw() {
                boolean isGif = story.heroImageS3Key() != null && story.heroImageS3Key().endsWith
                        (".gif");
                heroImage.getViewTreeObserver().removeOnPreDrawListener(this);
                String imageUrl = Utilities.buildImageUrlWH(story.heroImageS3Key(), heroImage
                                .getWidth(), heroImage.getHeight(), "not found", isGif, true,
                        "quintype-01.imgix.net");
                glideRequestManager.load(imageUrl).error(R.drawable
                        .person_placeholder).into(heroImage);
                return true;
            }
        });
    }
}
