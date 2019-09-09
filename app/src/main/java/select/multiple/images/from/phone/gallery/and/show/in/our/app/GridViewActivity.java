package select.multiple.images.from.phone.gallery.and.show.in.our.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import java.util.ArrayList;
import android.content.ClipData;
import android.net.Uri;

public class GridViewActivity extends AppCompatActivity {

    private int PICK_IMAGE_MULTIPLE = 1;
    private Context context = GridViewActivity.this;
    private GridView gridview;
    private ArrayList<ImageUriModel> itemModelArrayList = new ArrayList<ImageUriModel>();
    private CustomBaseAdapter customBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);
        initView();
        initObject();
        generateItemsList();
    }

    private void initView() {
        gridview = findViewById(R.id.grid_view);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Toast toast = Toast.makeText(getApplicationContext(), "Item " + (position + 1) + ": " + itemModelArrayList.get(position).getUri(), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
            }
        });
    }

    private void initObject() {
        itemModelArrayList = new ArrayList<>();
    }

    private void generateItemsList()
    {
        customBaseAdapter = new CustomBaseAdapter(context, itemModelArrayList);
        gridview.setAdapter(customBaseAdapter);
    }

    //******* main menu **********
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_open:
                Intent intent = new Intent();
                intent.setType("image/*");
                /*
                Note: the EXTRA_ALLOW_MULTIPLE option is only available in Android API 18 and higher.
                 */
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2)
                {
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                }
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_MULTIPLE);
                /*
                Intent i = new Intent(Action.ACTION_MULTIPLE_PICK);
                startActivityForResult(i, 200);
                 */
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    //******* main menu end **********
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_MULTIPLE)
        {
            itemModelArrayList.clear();
            if(resultCode == Activity.RESULT_OK)
            {
                if(data.getClipData() != null)
                {
                    ClipData clipData = data.getClipData();
                    int count = clipData.getItemCount();

                    for(int i = 0; i < count; i++)
                    {
                        ClipData.Item item = clipData.getItemAt(i);
                        Uri imageUri = item.getUri();
                        itemModelArrayList.add(new ImageUriModel(imageUri));
                        /*try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }*/
                    }
                    customBaseAdapter.notifyDataSetChanged();
                }
                else if(data.getData() != null)
                {
                    itemModelArrayList.clear();
                    Uri imageUri = data.getData();
                    itemModelArrayList.add(new ImageUriModel(imageUri));
                    customBaseAdapter.notifyDataSetChanged();
                }
            }
        }
    }
}
