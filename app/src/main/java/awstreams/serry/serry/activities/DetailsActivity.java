package awstreams.serry.serry.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;

import com.bumptech.glide.Glide;

import awstreams.serry.serry.R;
import awstreams.serry.serry.helpers.Constants;
import awstreams.serry.serry.models.Place;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Serry on 7/8/2017.
 */
public class DetailsActivity extends AppCompatActivity {

    @BindView(R.id.tvDescription)
    AppCompatTextView tvDescription;
    @BindView(R.id.tvPrice)
    AppCompatTextView tvPrice;
    @BindView(R.id.ivProduct)
    AppCompatImageView ivProduct;
    private Place place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        place = (Place) getIntent().getExtras().getSerializable(Constants.KEY_INTENT);
        if (place != null) {
            setViews(place);
        }
    }

    private void setViews(Place place) {
        if (place != null) {
            tvDescription.setText(place.getProductDescription());
            tvPrice.setText(place.getPrice());
            Glide.with(this).load(place.getImage().getUrl()).error(R.drawable.placeholder).into(ivProduct);
        }
    }
}
