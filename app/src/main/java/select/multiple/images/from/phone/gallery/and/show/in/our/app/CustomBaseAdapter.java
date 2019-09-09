package select.multiple.images.from.phone.gallery.and.show.in.our.app;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import java.util.ArrayList;

public class CustomBaseAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<ImageUriModel> itemModelList;
    private LayoutInflater layoutInflater;

    public CustomBaseAdapter(Context context, ArrayList<ImageUriModel> itemModels) {
        this.context = context;
        this.itemModelList = itemModels;
        layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return (null != itemModelList ? itemModelList.size() : 0);
    }

    @Override
    public Object getItem(int position) {
        return (null != itemModelList ? itemModelList.get(position) : null);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ItemViewHolder itemViewHolder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.grid_item, parent, false);
              /*
            OR
            convertView = layoutInflater.inflate(R.layout.layout_list_item,null);
             */
            itemViewHolder = new ItemViewHolder(convertView);
            convertView.setTag(itemViewHolder);
        } else {
            itemViewHolder = (ItemViewHolder) convertView.getTag();
        }

        ImageUriModel rowItem = (ImageUriModel) getItem(position);
        ((ItemViewHolder) itemViewHolder).setData(rowItem);
        return convertView;
    }

    private class ItemViewHolder
    {
        ImageView itemImageView;

        public ItemViewHolder(View item)
        {
            itemImageView = item.findViewById(R.id.image_view);
        }

        public void setData(ImageUriModel itemPosition)
        {
            itemImageView.setImageURI(itemPosition.getUri());
        }
    }
}
