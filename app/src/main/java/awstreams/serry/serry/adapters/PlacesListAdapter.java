package awstreams.serry.serry.adapters;

import android.content.Context;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import awstreams.serry.serry.R;
import awstreams.serry.serry.interfaces.OnPlaceClickListener;
import awstreams.serry.serry.models.Place;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Serry on 7/8/2017.
 */

public class PlacesListAdapter extends RecyclerView.Adapter<PlacesListAdapter.PlaceHolder> {

    private List<Place> placeList;
    private Context mContext;
    OnPlaceClickListener onPlaceClickListener;

    public PlacesListAdapter(List<Place> placeList, Context mContext) {
        this.placeList = placeList;
        this.mContext = mContext;
        onPlaceClickListener = (OnPlaceClickListener) mContext;

    }

    @Override
    public PlacesListAdapter.PlaceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_place, parent, false);
        PlacesListAdapter.PlaceHolder placeHolder = new PlacesListAdapter.PlaceHolder(view);
        return placeHolder;
    }

    @Override
    public void onBindViewHolder(final PlacesListAdapter.PlaceHolder holder, final int position) {
        final Place newPlace = placeList.get(position);
        holder.tvDescription.setText(newPlace.getProductDescription());
        holder.tvPrice.setText(newPlace.getPrice());
        Picasso.with(mContext).load(newPlace.getImage().getUrl()).into(holder.ivPlace, new Callback() {
            @Override
            public void onSuccess() {
                FrameLayout.LayoutParams sParams;
                sParams = new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        Integer.parseInt(newPlace.getImage().getHeight()));
                holder.ivPlace.setLayoutParams(sParams);
                holder.pbProductItem.setVisibility(View.GONE);
                holder.ivPlace.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError() {
                FrameLayout.LayoutParams eParams;
                eParams = new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        Integer.parseInt(newPlace.getImage().getHeight()));
                holder.ivPlace.setLayoutParams(eParams);
                holder.pbProductItem.setVisibility(View.GONE);
                holder.ivPlace.setVisibility(View.VISIBLE);
                holder.ivPlace.setImageResource(R.drawable.placeholder);
            }
        });

        holder.lContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onPlaceClickListener != null) {
                    Place clickedPlace = placeList.get(position);
                    onPlaceClickListener.onPlaceClick(clickedPlace);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return placeList.size();
    }

    public class PlaceHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvDescription)
        AppCompatTextView tvDescription;
        @BindView(R.id.tvPrice)
        AppCompatTextView tvPrice;
        @BindView(R.id.ivPlace)
        AppCompatImageView ivPlace;
        @BindView(R.id.pbProductItem)
        ProgressBar pbProductItem;
        @BindView(R.id.container)
        LinearLayout lContainer;

        public PlaceHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
