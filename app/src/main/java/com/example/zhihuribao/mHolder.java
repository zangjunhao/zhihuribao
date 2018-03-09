package com.example.zhihuribao;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.zhihuribao.Listener;
/**
 * Created by 67698 on 2018/3/2.
 */
//文章的列表内容
public class mHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView articleTitle;
    public ImageView articleImage;
    public Listener.acitemclick itemClickListener;
    public mHolder(View itemView) {
        super(itemView);
        articleTitle = (TextView) itemView.findViewById(R.id.actitle);
        articleImage = (ImageView) itemView.findViewById(R.id.acimage);
        articleTitle.setOnClickListener(this);
        articleImage.setOnClickListener(this);
    }

    public void setItemClickListener(Listener.acitemclick itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        if (itemClickListener != null) {
            itemClickListener.OnItemClickListener(getAdapterPosition());
        }
    }
}
