package ebusiness.myapp.NewsFeed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.parse.ParseObject;

import java.util.List;

import ebusiness.myapp.R;

/**
 * Created by Fabian on 24.10.2014.
 */
public class NewsAdapter extends ArrayAdapter<ParseObject>{
    protected Context mContext;
    protected List<ParseObject> mNews;

    public NewsAdapter(Context context, List<ParseObject> news){
        super(context, R.layout.newscustomlayout, news);
        mContext = context;
        mNews = news;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        ViewHolder holder;

        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.newscustomlayout, null);
            holder = new ViewHolder();
            holder.usernameNews = (TextView) convertView
                    .findViewById(R.id.usernameNews);
            holder.commentNews = (TextView) convertView
                    .findViewById(R.id.news);

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        ParseObject newsObject = mNews.get(position);

        //title
        String username = newsObject.getString("user");
        holder.usernameNews.setText(username);

        //comment
        String comment = newsObject.getString("newStatus");
        holder.commentNews.setText((comment));

        return convertView;
    }

    public static class ViewHolder{
        //ImageView
        TextView usernameNews;
        TextView commentNews;
    }
}
