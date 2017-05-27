package com.quintype.autholeaderboards.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.quintype.autholeaderboards.Author;
import com.quintype.autholeaderboards.AuthorResult;
import com.quintype.autholeaderboards.R;
import com.quintype.autholeaderboards.utils.RoundedCornersTransformation;
import com.quintype.autholeaderboards.utils.Utilities;
import com.sasank.roundedhorizontalprogress.RoundedHorizontalProgressBar;

/**
 * Created by akshaykoul on 27/05/17.
 */

public class AuthorHolder extends RecyclerView.ViewHolder {

    TextView authorTitle;
    TextView viewCount;
    ImageView authorImage;
    RoundedHorizontalProgressBar progressView;
    RequestManager glideRequestManager;
    private boolean progressStarted = true;

    public AuthorHolder(View itemView) {
        super(itemView);
        glideRequestManager = Glide.with(itemView.getContext());
    }

    public static AuthorHolder create(View view) {
        AuthorHolder trackHolder = new AuthorHolder(view);
        trackHolder.authorTitle = (TextView) view.findViewById(R.id.author_name);
        trackHolder.authorImage = (ImageView) view.findViewById(R.id.author_image);
        trackHolder.viewCount = (TextView) view.findViewById(R.id.view_count);
        trackHolder.progressView = (RoundedHorizontalProgressBar) view.findViewById(R.id
                .view_count_progress);
        return trackHolder;
    }

    public void bind(Author author, AuthorResult authorResult, int maxViews) {
        authorTitle.setText(author.name());
        if (authorResult != null) {
            viewCount.setText(String.format("%s Views", authorResult.getCount()));
            progressView.setMax(maxViews);
            progressView.setProgress(authorResult.getCount());
        }

        glideRequestManager.load(author.avatarUrl()).bitmapTransform(new
                RoundedCornersTransformation(authorImage.getContext(),
                Utilities.dpToPx(authorImage.getResources(), 5), 0)).error(R.drawable
                .person_placeholder).into(authorImage);
    }
}
