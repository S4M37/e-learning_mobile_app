package tn.ppp.gl3.e_learning.Widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by S4M37 on 01/05/2016.
 */
public class NetworkImageView extends ImageView {
    private String imageURL;

    public NetworkImageView(Context context) {
        super(context);
    }

    public NetworkImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NetworkImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public NetworkImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setImageUrl(String imageURL) {
        this.imageURL = imageURL;
        Picasso.with(getContext()).load(imageURL).resize(400, 400).into(this);
    }
}
