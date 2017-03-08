package awstreams.serry.zadfreshapplication.adapters;

import android.content.Context;
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

public class ReposAdapter extends RecyclerView.Adapter<ReposAdapter.RepositoryHolder> {

    private boolean isMoreDataAvailable = true;
    private List<Repository> repositoryList;
    private Context mContext;

    public ReposAdapter(List<Repository> repositoryList, Context mContext) {
        this.repositoryList = repositoryList;
        this.mContext = mContext;
    }

    @Override
    public ReposAdapter.RepositoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.repo_item, null);
        ReposAdapter.RepositoryHolder productHolder = new ReposAdapter.RepositoryHolder(view);
        return productHolder;
    }

    @Override
    public void onBindViewHolder(ReposAdapter.RepositoryHolder holder, int position) {
        Repository repository = repositoryList.get(position);
        holder.tvName.setText(repository.getName());
        holder.tvDescription.setText(repository.getDescription());
        holder.tvUsername.setText(repository.getOwner().getLogin());
        if (repository.getFork().equals("true")) {
            holder.lContainer.setBackgroundColor(mContext.getResources().getColor(R.color.white));
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
        @BindView(R.id.container)
        RelativeLayout lContainer;

        public RepositoryHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
