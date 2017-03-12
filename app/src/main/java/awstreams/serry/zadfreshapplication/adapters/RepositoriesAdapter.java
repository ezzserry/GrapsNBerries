package awstreams.serry.zadfreshapplication.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import awstreams.serry.zadfreshapplication.R;
import awstreams.serry.zadfreshapplication.models.Repository;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by PC on 3/8/2017.
 */

public class RepositoriesAdapter extends RecyclerView.Adapter<RepositoriesAdapter.RepositoryHolder> {

    private List<Repository> repositoryList;
    private Context mContext;

    public RepositoriesAdapter(List<Repository> repositoryList, Context mContext) {
        this.repositoryList = repositoryList;
        this.mContext = mContext;
    }

    @Override
    public RepositoriesAdapter.RepositoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.repo_item, null);
        RepositoriesAdapter.RepositoryHolder productHolder = new RepositoriesAdapter.RepositoryHolder(view);
        return productHolder;
    }

    @Override
    public void onBindViewHolder(RepositoriesAdapter.RepositoryHolder holder, int position) {
        Repository repository = repositoryList.get(position);
        if (repository.getId() != null) {
            holder.tvName.setText(repository.getName());
            holder.tvDescription.setText(repository.getDescription());
            holder.tvUsername.setText(repository.getOwner().getLogin());
            if (repository.getFork().equals("true")) {
                holder.cardView.setBackgroundColor(Color.WHITE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return repositoryList.size();
    }

    public class RepositoryHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_repo_desc)
        TextView tvDescription;
        @BindView(R.id.tv_repo_name)
        TextView tvName;
        @BindView(R.id.tv_owner_username)
        TextView tvUsername;
        @BindView(R.id.cv)
        CardView cardView;

        public RepositoryHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
